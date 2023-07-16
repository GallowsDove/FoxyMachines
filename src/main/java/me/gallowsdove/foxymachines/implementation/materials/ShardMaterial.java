package me.gallowsdove.foxymachines.implementation.materials;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.gallowsdove.foxymachines.listeners.SacrificialAltarListener;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ShardMaterial extends SimpleSlimefunItem<ItemDropHandler> {
    private final ChatColor color;
    public ShardMaterial(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ChatColor color) {
        super(itemGroup, item, recipeType, recipe);

        this.color = color;
    }

    @Nonnull
    @Override
    public ItemDropHandler getItemHandler() {
        return (e, p, item) -> {
            if (!isItem(item.getItemStack())) {
                return false;
            }

            if (!QuestUtils.hasActiveQuest(p)) {
                p.sendMessage(this.color + "You have no active quest!");
                return true;
            }

            Slimefun.runSync(() -> {
                if (SacrificialAltarListener.findAltar(item.getLocation().getBlock()) == null) {
                    return;
                }

                p.sendMessage(this.color + "Reset active quest!");
                QuestUtils.resetQuestLine(p);
                SacrificialAltarListener.particleAnimation(item.getLocation());

                if (item.getItemStack().getAmount() == 1) {
                    item.remove();
                } else {
                    ItemStack i = item.getItemStack();
                    i.setAmount(i.getAmount() - 1);
                    item.setItemStack(i);
                }
            }, 20L);

            return true;
        };
    }
}
