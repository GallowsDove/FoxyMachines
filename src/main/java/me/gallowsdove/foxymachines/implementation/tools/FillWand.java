package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.abstracts.AbstractWand;
import me.gallowsdove.foxymachines.utils.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FillWand extends AbstractWand {
    public FillWand() {
        super(Items.FILL_WAND, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.AQUATIC_NETHERITE_INGOT, Items.WIRELESS_TRANSMITTER, Items.AQUATIC_NETHERITE_INGOT,
                Items.DEMONIC_PLATE, SlimefunItems.PROGRAMMABLE_ANDROID_2, Items.DEMONIC_PLATE,
                Items.SWEETENED_SWEET_INGOT, Items.WIRELESS_TRANSMITTER, Items.SWEETENED_SWEET_INGOT
        });
    }

    @Override
    protected boolean isRemoving() {return false;}

    @Override
    protected float getCostPerBBlock() {
        return 0.4F;
    }

    @Override
    protected List<Location> getLocations(@Nonnull Player player) {
        ArrayList<Location> locs = new ArrayList<>();
        PersistentDataContainer container = player.getPersistentDataContainer();
        SimpleLocation loc1 = SimpleLocation.fromPersistentStorage(container, "primary_position");
        SimpleLocation loc2 = SimpleLocation.fromPersistentStorage(container, "secondary_position");

        if (loc1 == null || loc2 == null || !loc1.getWorldUUID().equals(loc2.getWorldUUID())) {
            player.sendMessage(ChatColor.RED + "Please select both locations using Position Selector!");
            return locs;
        }

        if (loc1.getX() < loc2.getX()) {
            int tmp = loc1.getX();
            loc1.setX(loc2.getX());
            loc2.setX(tmp);
        }

        if (loc1.getY() < loc2.getY()) {
            int tmp = loc1.getY();
            loc1.setY(loc2.getY());
            loc2.setY(tmp);
        }

        if (loc1.getZ() < loc2.getZ()) {
            int tmp = loc1.getZ();
            loc1.setZ(loc2.getZ());
            loc2.setZ(tmp);
        }

        if ((loc1.getX() - loc2.getX()) * (loc1.getY() - loc2.getY()) * (loc1.getZ() - loc2.getZ()) > 4096) {
            player.sendMessage(ChatColor.RED + "Selected area is too big!");
            return locs;
        }

        World world = Bukkit.getWorld(UUID.fromString(loc1.getWorldUUID()));

        if (world == null) {
            player.sendMessage(ChatColor.RED + "Please select both locations using Position Selector!");
            return locs;
        }

        for (int x = loc2.getX(); x <= loc1.getX(); x++) {
            for (int y = loc2.getY(); y <= loc1.getY(); y++) {
                for (int z = loc2.getZ(); z <= loc1.getZ(); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (Slimefun.getProtectionManager().hasPermission(player, block, Interaction.PLACE_BLOCK) && block.getType().isAir()) {
                        locs.add(block.getLocation());
                    }
                }
            }
        }
        if (locs.size() == 0) {
            player.sendMessage(ChatColor.RED + "No valid locations found given the selected points!");
        }

        return locs;
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 1000;
    }
}
