package me.gallowsdove.foxymachines.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;

public class Utils {
    private Utils() {}

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

    public static Collection<Player> getNearbyPlayersInSurvival(Location location, double radius) {
        return getNearbyPlayersInSurvival(location, radius, radius, radius);
    }

    public static Collection<Player> getNearbyPlayersInSurvival(Location location, double x, double y, double z) {
        World world = location.getWorld();
        if (world == null) {
            return new HashSet<>();
        }

        Collection<Player> players = new HashSet<>();
        for (Entity entity : world.getNearbyEntities(location, x, y, z)) {
            if (entity instanceof Player player && player.getGameMode() == GameMode.SURVIVAL) {
                players.add(player);
            }
        }
        return players;
    }
}
