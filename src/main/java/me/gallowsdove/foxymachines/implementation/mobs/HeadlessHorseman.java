package me.gallowsdove.foxymachines.implementation.mobs;

import io.github.mooy1.infinitylib.core.PluginUtils;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import me.gallowsdove.foxymachines.utils.Utils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
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

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class HeadlessHorseman extends CustomBoss {

    public static class AttackPattern {
        public static short LIGHTNING = 0;
        public static short SHOOT = 1;
        public static short SUMMON = 2;
    }

    private static final NamespacedKey PATTERN_KEY = new NamespacedKey(FoxyMachines.getInstance(), "pattern");

    private int tick = 0;

    public HeadlessHorseman() {
        super("HEADLESS_HORSEMAN", ChatColor.RED + "Headless Horseman", EntityType.SKELETON, 1,
                DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION, DamageCause.THORNS);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        super.onSpawn(spawned);

        spawned.setInvisible(true);

        CustomMob mob = CustomMob.getByID("SKELETON_HORSE");
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
    public void onUniqueTick() {
        this.tick++;
        if (this.tick == 100) {
            this.tick = 0;
        }
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
            if (mob.isInsideVehicle() && mob.getVehicle() instanceof LivingEntity) {
                ((LivingEntity) mob.getVehicle()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 100));
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
    public void onMobTick(@Nonnull LivingEntity entity) {
        Skeleton headlessHorseman = (Skeleton) entity;

        short pattern = entity.getPersistentDataContainer().get(PATTERN_KEY, PersistentDataType.SHORT);

        if ((this.tick + 4) % 5 == 0) {
            Collection<Entity> entities = headlessHorseman.getWorld().getNearbyEntities(headlessHorseman.getLocation(), 30, 20, 30);

            for (Entity player : entities) {
                if (player instanceof Player && ((Player) player).getGameMode() == GameMode.SURVIVAL) {
                    headlessHorseman.setTarget((LivingEntity) player);
                }
            }
        }

        if (pattern == AttackPattern.SHOOT) {
            if (headlessHorseman.getTarget() != null) {
                Player target = (Player) headlessHorseman.getTarget();
                if (this.tick % 5 == 0) {
                    Arrow arrow = entity.launchProjectile(Arrow.class);
                    arrow.setDamage(22);
                    arrow.setColor(Color.RED);
                    arrow.setGlowing(true);
                    arrow.setSilent(true);
                    arrow.setGravity(false);
                    try {
                        arrow.setVelocity(target.getLocation().toVector().subtract(headlessHorseman.getLocation().toVector()).normalize().multiply(1.64));
                    } catch (IllegalArgumentException e) { }
                }
            }
        } else if (pattern == AttackPattern.LIGHTNING) {
            if (headlessHorseman.getTarget() != null) {
                if (this.tick % 8 == 0) {
                    Player player = (Player) headlessHorseman.getTarget();
                    Location loc = player.getLocation().clone();
                    Location l = headlessHorseman.getLocation();

                    if (Math.sqrt((loc.getX() - l.getX()) * (loc.getX() - l.getX()) +
                            (loc.getY() - l.getY()) * (loc.getY() - l.getY()) +
                            (loc.getZ() - l.getZ()) * (loc.getZ() - l.getZ())) < 26) {

                        PluginUtils.runSync(() -> {
                            loc.getWorld().strikeLightningEffect(loc);
                            if (player.isValid()) {
                                Location playerLoc = player.getLocation();
                                if (Math.sqrt((loc.getX() - playerLoc.getX()) * (loc.getX() - playerLoc.getX()) +
                                        (loc.getY() - playerLoc.getY()) * (loc.getY() - playerLoc.getY())) < 0.8) {
                                    EntityDamageEvent e = new EntityDamageEvent(player, DamageCause.CUSTOM, 12);
                                    Bukkit.getServer().getPluginManager().callEvent(e);
                                    if (!e.isCancelled()) {
                                        player.damage(15.6);
                                        Utils.dealDamageBypassingArmor(player, 1.64);
                                    }
                                }
                            }
                        }, 4);
                    }
                }
            }
        } else if (pattern == AttackPattern.SUMMON) {
            if (tick == 25) {
                spawnHelldogs(headlessHorseman.getLocation());
            }
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

    @Override
    protected int getSpawnChance() {
        return 0;
    }

    private void spawnHelldogs(Location loc) {
        CustomMob mob = CustomMob.getByID("HELLDOG");

        assert mob != null;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < random.nextInt(2) + 3; i++) {
            mob.spawn(new Location(loc.getWorld(), loc.getX() + random.nextDouble(-1, 1),
                    loc.getY() + random.nextDouble(0.6, 1.2), loc.getZ() + random.nextDouble(-1, 1)));
        }

        for (int i = 0; i < 20; i++) {
            loc.getWorld().spawnParticle(Particle.FLAME, loc, 1, random.nextDouble(-1.5, 1.5),
                    random.nextDouble(-1.2, 2.4), random.nextDouble(-1.5, 1.5), 0);
        }
    }
}