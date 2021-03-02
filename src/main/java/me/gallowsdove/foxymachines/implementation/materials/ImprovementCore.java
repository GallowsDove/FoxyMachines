package me.gallowsdove.foxymachines.implementation.materials;

import org.bukkit.inventory.ItemStack;
import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;

import javax.annotation.Nonnull;


public class ImprovementCore extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public ImprovementCore() {
        super(Items.category, Items.IMPROVEMENT_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.DAMASCUS_STEEL_INGOT, Items.REINFORCED_STRING, SlimefunItems.DAMASCUS_STEEL_INGOT,
                Items.REINFORCED_STRING, SlimefunItems.DAMASCUS_STEEL_INGOT, Items.REINFORCED_STRING,
                SlimefunItems.DAMASCUS_STEEL_INGOT, Items.REINFORCED_STRING, SlimefunItems.DAMASCUS_STEEL_INGOT
        });
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> e.cancel();
    }
}
