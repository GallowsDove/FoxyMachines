package me.gallowsdove.foxymachines.implementation.mobs;

import me.gallowsdove.foxymachines.abstracts.CustomMob;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Collection;

public class Helldog extends CustomMob {

    public Helldog() {
        super("HELLDOG", "Helldog", EntityType.WOLF, 30);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        Wolf wolf = (Wolf) spawned;

        wolf.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999999, 1, false, false));
        wolf.setFireTicks(999999999);
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(26);
        wolf.setRemoveWhenFarAway(true);
        wolf.setAngry(true);
        wolf.setCollarColor(DyeColor.RED);
    }

    @Override
    public void onMobTick(@Nonnull LivingEntity entity, int tick) {
        Wolf helldog = (Wolf) entity;

        entity.setFireTicks(999999999);
        Collection<Entity> entities = helldog.getWorld().getNearbyEntities(helldog.getLocation(), 1.54, 1.54, 1.54);

        for (Entity player : entities) {
            if (player instanceof Player && ((Player) player).getGameMode() == GameMode.SURVIVAL) {
                if (tick % 10 == 0)
                    helldog.attack(player);
            }
        }

        if (tick % 20 == 0) {
            entities = helldog.getWorld().getNearbyEntities(helldog.getLocation(), 16, 16, 16);

            for (Entity player : entities) {
                if (player instanceof Player && ((Player) player).getGameMode() == GameMode.SURVIVAL) {
                    helldog.setTarget((LivingEntity) player);
                }
            }
        }
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) {
        if (!e.isCancelled()) {
            Utils.dealDamageBypassingArmor((LivingEntity) e.getEntity(), (e.getDamage() - e.getFinalDamage()) * 0.2);
        }
    }

    @Override
    protected void onTarget(@Nonnull EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player)) {
            e.setCancelled(true);
        }
    }
}
