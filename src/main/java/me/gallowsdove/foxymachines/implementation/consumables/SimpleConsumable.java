package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

public class SimpleConsumable extends SimpleSlimefunItem<ItemUseHandler> {

    private final PotionEffect[] effects;

    @ParametersAreNonnullByDefault
    public SimpleConsumable(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, PotionEffect[] effects, int amount) {
        super(Items.ITEM_GROUP, item, recipeType, recipe, new SlimefunItemStack(item, amount));
        this.effects = effects;
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return new ItemUseHandler() {
            @Override
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();
                Player p = e.getPlayer();

                ItemStack item = e.getInteractEvent().getItem();

                item.setAmount(item.getAmount() - 1);

                double health = p.getHealth();
                p.addPotionEffects(Arrays.asList(effects));
                p.setHealth(health);
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
            }
        };
    }
}
