package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityInteractHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.implementation.materials.GhostBlock;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class GhostBlockRemover extends SlimefunItem {

    public GhostBlockRemover() {
        super(Items.TOOLS_ITEM_GROUP, Items.GHOST_BLOCK_REMOVER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.DAMIENIUM, Items.DAMIENIUM, Items.DAMIENIUM,
                Items.SWEET_INGOT, SlimefunItems.BASIC_CIRCUIT_BOARD, Items.SWEET_INGOT,
                Items.DAMIENIUM, Items.DAMIENIUM, Items.DAMIENIUM
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onInteract());
    }

    @Nonnull
    protected EntityInteractHandler onInteract() {
        return (e, itemStack, b) -> {
            if (e.getRightClicked() instanceof FallingBlock block &&
                    block.getPersistentDataContainer().has(GhostBlock.KEY, PersistentDataType.STRING)) {
                Material material = block.getBlockData().getMaterial();
                SlimefunItemStack stack = new SlimefunItemStack(
                        "GHOST_BLOCK_" + material.name().toUpperCase(),
                        material,
                        "Ghost Block: &6" + StringUtils.capitalize(material.name().replace("_", " ").toLowerCase()),
                        "",
                        "&7An intangible block.");

                block.getWorld().dropItemNaturally(block.getLocation(), stack);
                block.remove();
            }
        };
    }
}
