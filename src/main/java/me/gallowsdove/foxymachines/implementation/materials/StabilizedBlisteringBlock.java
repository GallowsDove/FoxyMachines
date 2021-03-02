package me.gallowsdove.foxymachines.implementation.materials;

import org.bukkit.inventory.ItemStack;
import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;

import javax.annotation.Nonnull;


public class StabilizedBlisteringBlock extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public StabilizedBlisteringBlock() {
        super(Items.category, Items.STABILIZED_BLISTERING_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT,SlimefunItems.HARDENED_METAL_INGOT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.HARDENED_METAL_INGOT
        });
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> e.cancel();
    }
}
