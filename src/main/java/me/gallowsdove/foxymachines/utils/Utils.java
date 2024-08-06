package me.gallowsdove.foxymachines.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;

public class Utils {
    private Utils() {}

    public static boolean isAuraSkillsLoaded() {
        return Bukkit.getPluginManager().isPluginEnabled("AuraSkills");
    }

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

    public static int countItemInInventory(@Nonnull Inventory inventory, @Nonnull ItemStack itemStack) {
        int amount = 0;
        for (ItemStack item : inventory.getContents()) {
            if (itemStack.isSimilar(item)) {
                amount = amount + item.getAmount();
            }
        }

        return amount;
    }
}
