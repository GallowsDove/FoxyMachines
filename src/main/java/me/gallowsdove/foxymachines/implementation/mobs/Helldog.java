package me.gallowsdove.foxymachines.implementation.mobs;

import me.gallowsdove.foxymachines.abstracts.CustomMob;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.List;

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

        List<Player> players = null;
        if (tick % 20 == 0) {
            players = Utils.getNearbyPlayersInSurvival(helldog.getLocation(), 16);
            helldog.setTarget(players.isEmpty() ? null : players.get(0));
        }

        if (tick % 10 == 0) {
            if (players == null) {
                for (Player player : Utils.getNearbyPlayersInSurvival(helldog.getLocation(), 1.54)) {
                    helldog.attack(player);
                }
                return;
            }

            for (Player player : players) {
                if (Utils.isWithinBox(helldog.getLocation(), player.getLocation(), 1.54)) {
                    helldog.attack(player);
                }
            }
        }


    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent event) {
        super.onDeath(event);

        event.getDrops().clear();
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent event) {
        if (!event.isCancelled()) {
            Utils.dealDamageBypassingArmor((LivingEntity) event.getEntity(), (event.getDamage() - event.getFinalDamage()) * 0.2);
        }
    }

    @Override
    protected void onTarget(@Nonnull EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player)) {
            event.setCancelled(true);
        }
    }
}
