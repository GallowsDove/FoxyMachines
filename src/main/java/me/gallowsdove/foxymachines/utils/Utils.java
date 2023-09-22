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
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean isWithinBox(Location centerLocation, Location location, double radius) {
        return isWithinBox(centerLocation, location, radius, radius, radius);
    }

    public static boolean isWithinBox(Location centerLocation, Location location, double x, double y, double z) {
        return Math.abs(centerLocation.getX() - location.getX()) <= x
                && Math.abs(centerLocation.getY() - location.getY()) <= y
                && Math.abs(centerLocation.getZ() - location.getZ()) <= z;
    }

    public static List<Player> getNearbyPlayersInSurvival(Location location, double radius) {
        return getNearbyPlayersInSurvival(location, radius, radius, radius);
    }

    public static List<Player> getNearbyPlayersInSurvival(Location location, double x, double y, double z) {
        World world = location.getWorld();
        if (world == null) {
            return new ArrayList<>();
        }

        List<Player> players = new ArrayList<>();
        for (Entity entity : world.getNearbyEntities(location, x, y, z)) {
            if (entity instanceof Player player && player.getGameMode() == GameMode.SURVIVAL) {
                players.add(player);
            }
        }
        return players;
    }

    public static Player getNearbyPlayerInSurvival(Location location, double radius) {
        return getNearbyPlayerInSurvival(location, radius, radius, radius);
    }

    public static @Nullable Player getNearbyPlayerInSurvival(Location location, double x, double y, double z) {
        World world = location.getWorld();
        if (world == null) {
            return null;
        }

        for (Entity entity : world.getNearbyEntities(location, x, y, z)) {
            if (entity instanceof Player player && player.getGameMode() == GameMode.SURVIVAL) {
                return player;
            }
        }
        return null;
    }
}
