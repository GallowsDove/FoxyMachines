package me.gallowsdove.foxymachines.implementation.mobs;

import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.NumberConversions;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class HeadlessHorseman extends CustomBoss {

    public static class AttackPattern {
        public static final short LIGHTNING = 0;
        public static final short SHOOT = 1;
        public static final short SUMMON = 2;
    }

    private static final NamespacedKey PATTERN_KEY = new NamespacedKey(FoxyMachines.getInstance(), "pattern");

    public HeadlessHorseman() {
        super("HEADLESS_HORSEMAN", ChatColor.RED + "Headless Horseman", EntityType.SKELETON, 1,
                DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION, DamageCause.THORNS);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        super.onSpawn(spawned);

        spawned.setInvisible(true);

        CustomMob mob = CustomMob.getById("SKELETON_HORSE");
        SkeletonHorse horse = (SkeletonHorse) mob.spawn(spawned.getLocation());
        horse.addPassenger(spawned);

        EntityEquipment equipment = spawned.getEquipment();
        equipment.setArmorContents(new ItemStack[] { new ItemStack(Material.NETHERITE_BOOTS), new ItemStack(Material.NETHERITE_LEGGINGS),
                new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.CARVED_PUMPKIN) });

        spawned.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(28);
        spawned.getPersistentDataContainer().set(PATTERN_KEY, PersistentDataType.SHORT, AttackPattern.LIGHTNING);
    }

    @Nonnull
    @Override
    protected BossBarStyle getBossBarStyle() {
        return new BossBarStyle("Headless Horseman", BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
    }

    @Override
    protected void onTarget(@Nonnull EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player)) {
            e.setCancelled(true);
        }
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            Utils.dealDamageBypassingArmor((LivingEntity) e.getEntity(), (e.getDamage() - e.getFinalDamage()) * 0.12);
        }
    }

    @Override
    public void onBossPattern(@Nonnull LivingEntity mob) {
        super.onBossPattern(mob);

        short pattern = (short) ThreadLocalRandom.current().nextInt(7);
        if (pattern < 2) {
            pattern = AttackPattern.LIGHTNING;
        } else if (pattern < 5) {
            pattern = AttackPattern.SHOOT;
            if (mob.getVehicle() instanceof LivingEntity vehicle) {
                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 100));
            }
        } else {
            pattern = AttackPattern.SUMMON;
        }

        mob.getPersistentDataContainer().set(PATTERN_KEY, PersistentDataType.SHORT, pattern);
    }

    @Override
    public void onBossDamaged(@Nonnull EntityDamageEvent e) {
        super.onBossDamaged(e);

        Skeleton headlessHorseman = (Skeleton) e.getEntity();
        if (headlessHorseman.isInsideVehicle()) {
            e.setCancelled(true);
        }
    }

    @Override
    public void onMobTick(@Nonnull LivingEntity entity, int tick) {
        super.onMobTick(entity, tick);

        Skeleton headlessHorseman = (Skeleton) entity;
        short pattern = entity.getPersistentDataContainer().get(PATTERN_KEY, PersistentDataType.SHORT);

        if ((tick + 4) % 5 == 0) {
            Collection<Player> players = Utils.getNearbyPlayersInSurvival(headlessHorseman.getLocation(), 30, 20, 30);
            for (Player player : players) {
                headlessHorseman.setTarget(player);
            }
        }

        if (pattern == AttackPattern.SUMMON && tick == 25) {
            spawnHelldogs(headlessHorseman.getLocation());
            return;
        }

        final Entity target = headlessHorseman.getTarget();
        if (!(target instanceof Player player)) {
            return;
        }

        if (tick % 5 == 0 && pattern == AttackPattern.SHOOT) {
            Arrow arrow = entity.launchProjectile(Arrow.class);
            arrow.setDamage(22);
            arrow.setColor(Color.RED);
            arrow.setGlowing(true);
            arrow.setSilent(true);
            arrow.setGravity(false);
            try {
                // TODO: Find out why this sometimes throws an error
                arrow.setVelocity(target.getLocation().toVector().subtract(headlessHorseman.getLocation().toVector()).normalize().multiply(1.64));
            } catch (IllegalArgumentException ignored) {}
            return;
        }

        if (tick % 8 == 0 && pattern == AttackPattern.LIGHTNING) {
            Location playerLocation = player.getLocation().clone();
            Location horsemanLocation = headlessHorseman.getLocation();

            if (playerLocation.distanceSquared(horsemanLocation) >= Math.pow(26, 2)) {
                return;
            }

            Scheduler.run(4, () -> {
                playerLocation.getWorld().strikeLightningEffect(playerLocation);
                if (!player.isValid()) {
                    return;
                }

                Location newLocation = player.getLocation();
                if (NumberConversions.square(playerLocation.getX() - newLocation.getX())
                        + NumberConversions.square(playerLocation.getY() - newLocation.getY()) >= Math.pow(0.72, 2)) {
                    return;
                }

                EntityDamageEvent e = new EntityDamageEvent(player, DamageCause.CUSTOM, 12);
                Bukkit.getServer().getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                    player.damage(12.4);
                    Utils.dealDamageBypassingArmor(player, 1.72);
                }
            });
        }
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        super.onDeath(e);

        e.getDrops().clear();
        Location loc = e.getEntity().getLocation();
        loc.getWorld().dropItemNaturally(loc, new SlimefunItemStack(Items.VILE_PUMPKIN, 1));
        loc.getWorld().spawn(loc, ExperienceOrb.class).setExperience(2000 + ThreadLocalRandom.current().nextInt(800));
    }

    private void spawnHelldogs(Location loc) {
        CustomMob helldog = CustomMob.getById("HELLDOG");
        if (helldog == null) {
            FoxyMachines.getInstance().getLogger().warning("Could not spawn Helldogs! Please report this to the github!");
            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 3; i++) {
            helldog.spawn(new Location(loc.getWorld(), loc.getX() + random.nextDouble(-1, 1),
                    loc.getY() + random.nextDouble(0.6, 1.2), loc.getZ() + random.nextDouble(-1, 1)));
        }

        for (int i = 0; i < 20; i++) {
            loc.getWorld().spawnParticle(Particle.FLAME, loc, 1, random.nextDouble(-1.5, 1.5),
                    random.nextDouble(-1.2, 2.4), random.nextDouble(-1.5, 1.5), 0);
        }
    }
}