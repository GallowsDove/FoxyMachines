package me.gallowsdove.foxymachines.implementation.materials;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


public class ReinforcedString extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public ReinforcedString() {
        super(Items.category, Items.REINFORCED_STRING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING),
                SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT,
                new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING)
        });
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> e.cancel();
    }
}
