package me.gallowsdove.foxymachines.utils;

import org.bukkit.entity.LivingEntity;

public class Utils {
    public static void dealDamageBypassingArmor(LivingEntity entity, double damage) {
        if (damage >= 0) {
            if (entity.getAbsorptionAmount() >= 0) {
                if (entity.getAbsorptionAmount() - damage > 0) {
                    entity.setAbsorptionAmount(entity.getAbsorptionAmount() - damage);
                    damage = 0;
                } else {
                    entity.setAbsorptionAmount(0);
                    damage = damage - entity.getAbsorptionAmount();
                }
            }
            if (damage > 0) {
                if (entity.getHealth() - damage >= 0) {
                    entity.setHealth(entity.getHealth() - damage);
                } else {
                    entity.setHealth(0);
                }
            }
        }
    }
}
