package me.gallowsdove.foxymachines.implementation.mobs;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PixieQueen extends CustomBoss {

    public static class AttackPattern {
        public static final short CHARGE = 0;
        public static final short SHOOT = 1;
        public static final short SUMMON = 2;
        public static final short IDLE = 3;
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
    protected void onTarget(@Nonnull EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player)) {
            event.setCancelled(true);
        }
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent event) {
        if (!event.isCancelled()) {
            Utils.dealDamageBypassingArmor((LivingEntity) event.getEntity(), (event.getDamage() - event.getFinalDamage()) * 0.12);
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

        PersistentDataAPI.setShort(mob, PATTERN_KEY, pattern);
    }

    @Override
    public void onMobTick(@Nonnull LivingEntity entity, int tick) {
        super.onMobTick(entity, tick);

        Vex pixieQueen = (Vex) entity;
        short pattern = PersistentDataAPI.getShort(entity, PATTERN_KEY);

        if (pattern == AttackPattern.SUMMON && tick == 25) {
            summonPixieSwarm(pixieQueen.getLocation());
            return;
        }

        if (pattern == AttackPattern.CHARGE) {
            Location location = pixieQueen.getLocation();
            if (tick % 10 == 0) {
                List<Player> players = Utils.getNearbyPlayersInSurvival(location, 1.6);
                for (Player player : players) {
                    pixieQueen.attack(player);
                }
            }

            Player player = Utils.getNearbyPlayerInSurvival(location, 10);
            pixieQueen.setCharging(false);
            pixieQueen.setTarget(player);

            if (player == null) {
                return;
            }

            if ((tick + 2) % 3 == 0) {
                try {
                    // TODO: Find out why this sometimes throws an error
                    pixieQueen.setVelocity(player.getLocation().toVector().subtract(location.toVector()).normalize().multiply(0.32));
                } catch (IllegalArgumentException ignored) { }
            }
            return;
        }

        if (pattern == AttackPattern.SHOOT) {
            pixieQueen.setCharging(false);
            if (tick % 5 == 0 && pixieQueen.getTarget() != null) {
                Arrow arrow = entity.launchProjectile(Arrow.class);
                arrow.setDamage(24);
                arrow.setColor(Color.LIME);
                arrow.setGlowing(true);
                arrow.setSilent(true);
                arrow.setGravity(false);
                try {
                    // TODO: Find out why this sometimes throws an error
                    arrow.setVelocity(pixieQueen.getTarget().getLocation().toVector().subtract(pixieQueen.getLocation().toVector()).normalize().multiply(1.42));
                } catch (IllegalArgumentException ignored) {}
            }
        }
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent event) {
        super.onDeath(event);

        event.getDrops().clear();
        Location loc = event.getEntity().getLocation();
        loc.getWorld().dropItemNaturally(loc, new SlimefunItemStack(Items.PIXIE_QUEEN_HEART, 1));
        loc.getWorld().spawn(loc, ExperienceOrb.class).setExperience(1400 + ThreadLocalRandom.current().nextInt(600));
    }

    private void summonPixieSwarm(Location location) {
        CustomMob mob = CustomMob.getById("PIXIE");
        if (mob == null) {
            FoxyMachines.getInstance().getLogger().warning("Could not spawn Pixies! Please report this to the github!");
            return;
        }


        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(2) + 3; i++) {
            mob.spawn(new Location(location.getWorld(), location.getX() + random.nextDouble(-2, 2),
                    location.getY() + random.nextDouble(1.2, 2.4), location.getZ() + random.nextDouble(-2, 2)));
        }

        for (int i = 0; i < 10; i++) {
            location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 1, random.nextDouble(-1.5, 1.5),
                    random.nextDouble(-1.2, 2.4), random.nextDouble(-1.5, 1.5), 0);
        }
    }
}