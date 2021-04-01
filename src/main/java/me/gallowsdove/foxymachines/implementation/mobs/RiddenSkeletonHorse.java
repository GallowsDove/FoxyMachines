package me.gallowsdove.foxymachines.implementation.mobs;

import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class RiddenSkeletonHorse extends CustomMob {
    private static final Set<DamageCause> RESISTANCES = Set.of(new DamageCause[] {
            DamageCause.CRAMMING, DamageCause.POISON, DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION});

    public RiddenSkeletonHorse() {
        super("SKELETON_HORSE", "Skeleton Horse", EntityType.SKELETON_HORSE, 2048);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        spawned.setCustomName("");
        spawned.setCustomNameVisible(false);
        spawned.setRemoveWhenFarAway(false);
        spawned.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(50);
        spawned.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 4, false, false));
    }

    @Override
    protected void onHit(@Nonnull EntityDamageEvent e) {
        if (RESISTANCES.contains(e.getCause())) {
            e.setCancelled(true);
        }

        SkeletonHorse horse = (SkeletonHorse) e.getEntity();

        if (!e.isCancelled()) {
            for (Entity entity : horse.getPassengers()) {
                if (entity instanceof LivingEntity) {
                    LivingEntity passenger = (LivingEntity) entity;
                    CustomMob mob = RiddenSkeletonHorse.getByEntity(entity);
                    if (mob instanceof CustomBoss) {
                        CustomBoss boss = (CustomBoss) mob;
                        double finalHealth = horse.getHealth() + passenger.getHealth() - e.getFinalDamage();
                        if (finalHealth > 0) {
                            boss.updateBossBar(passenger, finalHealth / (passenger.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() +
                                    horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();

        List<Entity> passengers = e.getEntity().getPassengers();

        for (Entity passenger: passengers) {
            if (passenger instanceof LivingEntity) {
                ((LivingEntity) passenger).setHealth(0);
            }
        }
    }
}
