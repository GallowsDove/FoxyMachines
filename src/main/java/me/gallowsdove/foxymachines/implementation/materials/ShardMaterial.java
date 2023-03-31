package me.gallowsdove.foxymachines.implementation.materials;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.gallowsdove.foxymachines.listeners.SacrificialAltarListener;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ShardMaterial extends SimpleSlimefunItem<ItemDropHandler> {
    public ShardMaterial(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public ItemDropHandler getItemHandler() {
        return (e, p, item) -> {
            if (!isItem(item.getItemStack())) {
                return false;
            }

            if (SacrificialAltarListener.findAltar(item.getLocation().getBlock()) == null) {
                return false;
            }

            if (!QuestUtils.hasActiveQuest(p)) {
                return false;
            }

            Slimefun.runSync(() -> {
                SacrificialAltarListener.particleAnimation(item.getLocation());
                item.remove();
            }, 20L);

            return true;
        };
    }
}
