package me.gallowsdove.foxymachines.implementation.multiblock;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class SacrificialAltarPressurePlate extends SlimefunItem {
    public SacrificialAltarPressurePlate() {
        super(Items.ALTAR_ITEM_GROUP, Items.SACRIFICIAL_ALTAR_BLACKSTONE_PRESSURE_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.GHAST_TEAR), SlimefunItems.MAGIC_LUMP_3, new ItemStack(Material.GHAST_TEAR),
                SlimefunItems.MAGIC_LUMP_3, new ItemStack(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE), SlimefunItems.MAGIC_LUMP_3,
                new ItemStack(Material.GHAST_TEAR), SlimefunItems.MAGIC_LUMP_3, new ItemStack(Material.GHAST_TEAR)
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onPlace(), onUse(), onBreak());
    }

    private BlockPlaceHandler onPlace() {
        return new BlockPlaceHandler(false) {

            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                Block b = e.getBlockPlaced();
                if (isComplete(b)) {
                    BlockStorage.addBlockInfo(b, "complete", "true");
                    e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "The Sacrificial Altar has been activated.");
                } else {
                    BlockStorage.addBlockInfo(b, "complete", "false");
                    e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Finish your Altar and click this block again to activate it.");
                }
            }
        };
    }

    private BlockUseHandler onUse() {
        return e -> {
            Block b = e.getClickedBlock().get();
            if (BlockStorage.getLocationInfo(b.getLocation(), "complete").equals("false")) {
                if (isComplete(b)) {
                    BlockStorage.addBlockInfo(b, "complete", "true");
                    e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "The Sacrificial Altar has been activated.");
                } else {
                    BlockStorage.addBlockInfo(b, "complete", "false");
                    e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "The Altar is not finished!");
                }
            }

            e.cancel();
        };
    }

    private BlockBreakHandler onBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                BlockStorage.addBlockInfo(e.getBlock(), "complete", null);
                BlockStorage.clearBlockInfo(e.getBlock());
                e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "The Altar has been broken!");
            }
        };
    }

    private boolean isComplete(@Nonnull Block b) {

        if (b.getRelative(1, 1, 1).getType() != Material.POLISHED_BLACKSTONE_BRICK_STAIRS || !isAltarPiece(b.getRelative(1, 1, 1)) ||
                b.getRelative(-1, 1, 1).getType() != Material.POLISHED_BLACKSTONE_BRICK_STAIRS || !isAltarPiece(b.getRelative(-1, 1, 1)) ||
                b.getRelative(1, 1, -1).getType() != Material.POLISHED_BLACKSTONE_BRICK_STAIRS || !isAltarPiece(b.getRelative(1, 1, -1)) ||
            b.getRelative(-1, 1, -1).getType() != Material.POLISHED_BLACKSTONE_BRICK_STAIRS || !isAltarPiece(b.getRelative(-1, 1, -1))) {
            return false;
        }

        if (b.getRelative(1, 0, 1).getType() != Material.POLISHED_BLACKSTONE_BRICKS || !isAltarPiece(b.getRelative(1, 0, 1)) ||
                b.getRelative(-1, 0, 1).getType() != Material.POLISHED_BLACKSTONE_BRICKS || !isAltarPiece(b.getRelative(-1, 0, 1)) ||
                b.getRelative(1, 0, -1).getType() != Material.POLISHED_BLACKSTONE_BRICKS || !isAltarPiece(b.getRelative(1, 0, -1)) ||
                b.getRelative(-1, 0, -1).getType() != Material.POLISHED_BLACKSTONE_BRICKS || !isAltarPiece(b.getRelative(-1, 0, -1))) {
            return false;
        }

        if (b.getRelative(0, 1, 1).getType() != Material.SOUL_TORCH || !isAltarPiece(b.getRelative(0, 1, 1)) ||
                b.getRelative(0, 1, -1).getType() != Material.SOUL_TORCH || !isAltarPiece(b.getRelative(0, 1, -1)) ||
                b.getRelative(1, 1, 0).getType() != Material.SOUL_TORCH || !isAltarPiece(b.getRelative(1, 1, 0)) ||
                b.getRelative(-1, 1, 0).getType() != Material.SOUL_TORCH || !isAltarPiece(b.getRelative(-1, 1, 0))) {
            return false;
        }

        if (b.getRelative(0, 0, 1).getType() != Material.POLISHED_BLACKSTONE_BRICK_WALL || !isAltarPiece(b.getRelative(0, 0, 1)) ||
                b.getRelative(0, 0, -1).getType() != Material.POLISHED_BLACKSTONE_BRICK_WALL || !isAltarPiece(b.getRelative(0, 0, -1)) ||
                b.getRelative(1, 0, 0).getType() != Material.POLISHED_BLACKSTONE_BRICK_WALL || !isAltarPiece(b.getRelative(1, 0, 0)) ||
                b.getRelative(-1, 0, 0).getType() != Material.POLISHED_BLACKSTONE_BRICK_WALL || !isAltarPiece(b.getRelative(-1, 0, 0))) {
            return false;
        }

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (b.getRelative(x, -1, z).getType() != Material.POLISHED_BLACKSTONE_BRICKS || !isAltarPiece(b.getRelative(x, -1, z))) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isAltarPiece(@Nonnull Block b) {
        if (BlockStorage.getLocationInfo(b.getLocation(), "id") == null) {
            return false;
        }

        return switch (BlockStorage.getLocationInfo(b.getLocation(), "id")) {
            case "SACRIFICIAL_ALTAR_BLACKSTONE_BRICKS", "SACRIFICIAL_ALTAR_BLACKSTONE_BRICK_WALL", "SACRIFICIAL_ALTAR_BLACKSTONE_BRICK_STAIRS", "SACRIFICIAL_ALTAR_SOUL_TORCH" -> true;
            default -> false;
        };
    }
}