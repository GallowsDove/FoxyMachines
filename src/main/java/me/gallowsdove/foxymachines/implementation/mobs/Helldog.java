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

import javax.annotation.Nonnull;
import java.util.Collection;

public class Helldog extends CustomMob {

    private int tick = 0;

    public Helldog() {
        super("HELLDOG", "Helldog", EntityType.WOLF, 52);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        Wolf wolf = (Wolf) spawned;

        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(28);
        wolf.setRemoveWhenFarAway(true);
        wolf.setAngry(true);
        wolf.setCollarColor(DyeColor.RED);
    }

    @Override
    public void onUniqueTick() {
        this.tick++;
        if (this.tick == 100) {
            this.tick = 0;
        }
    }

    @Override
    public void onMobTick(@Nonnull LivingEntity entity) {
        Wolf helldog = (Wolf) entity;

        Collection<Entity> entities = helldog.getWorld().getNearbyEntities(helldog.getLocation(), 1.54, 1.54, 1.54);

        for (Entity player : entities) {
            if (player instanceof Player && ((Player) player).getGameMode() == GameMode.SURVIVAL) {
                if (this.tick % 10 == 0)
                    helldog.attack(player);
            }
        }

        if (this.tick % 20 == 0) {
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

    @Override
    protected int getSpawnChance() {
        return 0;
    }
}
