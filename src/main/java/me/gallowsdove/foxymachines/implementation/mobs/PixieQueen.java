package me.gallowsdove.foxymachines.implementation.mobs;

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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class PixieQueen extends CustomBoss {

    public static class AttackPattern {
        public static short CHARGE = 0;
        public static short SHOOT = 1;
        public static short SUMMON = 2;
        public static short IDLE = 3;
    }

    private static final NamespacedKey PATTERN_KEY = new NamespacedKey(FoxyMachines.getInstance(), "pattern");

    public PixieQueen() {
        super("PIXIE_QUEEN", ChatColor.GREEN + "Pixie Queen", EntityType.VEX, 800,
                DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION, DamageCause.THORNS);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        super.onSpawn(spawned);

        spawned.setGlowing(true);

        spawned.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(28);
        spawned.getPersistentDataContainer().set(PATTERN_KEY, PersistentDataType.SHORT, AttackPattern.CHARGE);
    }

    @Nonnull
    @Override
    protected BossBarStyle getBossBarStyle() {
        return new BossBarStyle("Pixie Queen", BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
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
            pattern = AttackPattern.CHARGE;
        } else if (pattern < 4) {
            pattern = AttackPattern.SHOOT;
            mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 100));
        } else if (pattern < 6) {
            pattern = AttackPattern.SUMMON;
        } else {
            pattern = AttackPattern.IDLE;
        }

        mob.getPersistentDataContainer().set(PATTERN_KEY, PersistentDataType.SHORT, pattern);
    }

    @Override
    public void onMobTick(@Nonnull LivingEntity entity, int tick) {
        Vex pixieQueen = (Vex) entity;

        short pattern = entity.getPersistentDataContainer().get(PATTERN_KEY, PersistentDataType.SHORT);

        if (pattern == AttackPattern.CHARGE) {
            Collection<Entity> entities = pixieQueen.getWorld().getNearbyEntities(pixieQueen.getLocation(), 1.6, 1.6, 1.6);

            if (tick % 10 == 0) {
                for (Entity player : entities) {
                    if (player instanceof Player && ((Player) player).getGameMode() == GameMode.SURVIVAL) {
                        pixieQueen.attack(player);
                    }
                }
            }

            entities = pixieQueen.getWorld().getNearbyEntities(pixieQueen.getLocation(), 10, 10, 10);

            for (Entity player : entities) {
                if (player instanceof Player && ((Player) player).getGameMode() == GameMode.SURVIVAL) {
                    pixieQueen.setTarget((LivingEntity) player);
                    pixieQueen.setCharging(false);
                    if ((tick + 2) % 3 == 0) {
                        // TODO find out why this sometimes produces error
                        try {
                            pixieQueen.setVelocity(player.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize().multiply(0.32));
                        } catch (IllegalArgumentException e) { }
                    }
                }
            }
        } else if (pattern == AttackPattern.SHOOT) {
            pixieQueen.setCharging(false);
            if (pixieQueen.getTarget() != null) {
                if (tick % 5 == 0) {
                    Arrow arrow = entity.launchProjectile(Arrow.class);
                    arrow.setDamage(24);
                    arrow.setColor(Color.LIME);
                    arrow.setGlowing(true);
                    arrow.setSilent(true);
                    arrow.setGravity(false);
                    arrow.setVelocity(pixieQueen.getTarget().getLocation().toVector().subtract(pixieQueen.getLocation().toVector()).normalize().multiply(1.42));
                }
            }
        } else if (pattern == AttackPattern.SUMMON) {
            if (tick == 25) {
                summonPixieSwarm(pixieQueen.getLocation());
            }
        }
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        super.onDeath(e);

        e.getDrops().clear();
        Location loc = e.getEntity().getLocation();
        loc.getWorld().dropItemNaturally(loc, new SlimefunItemStack(Items.PIXIE_QUEEN_HEART, 1));
        loc.getWorld().spawn(loc, ExperienceOrb.class).setExperience(1400 + ThreadLocalRandom.current().nextInt(600));
    }

    private void summonPixieSwarm(Location loc) {
        CustomMob mob = CustomMob.getByID("PIXIE");

        assert mob != null;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < random.nextInt(2) + 3; i++) {
            mob.spawn(new Location(loc.getWorld(), loc.getX() + random.nextDouble(-2, 2),
                    loc.getY() + random.nextDouble(1.2, 2.4), loc.getZ() + random.nextDouble(-2, 2)));
        }

        for (int i = 0; i < 10; i++) {
            loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 1, random.nextDouble(-1.5, 1.5),
                    random.nextDouble(-1.2, 2.4), random.nextDouble(-1.5, 1.5), 0);
        }
    }
}