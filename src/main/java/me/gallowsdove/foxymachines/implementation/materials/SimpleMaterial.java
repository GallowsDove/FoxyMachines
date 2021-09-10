package me.gallowsdove.foxymachines.implementation.materials;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class SimpleMaterial extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {
    @ParametersAreNonnullByDefault
    public SimpleMaterial(SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int amount) {
        super(Items.ITEM_GROUP, item, type, recipe, new SlimefunItemStack(item, amount));
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return PlayerRightClickEvent::cancel;
    }
}
