package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

public class SimpleConsumable extends SimpleSlimefunItem<ItemUseHandler> {

    private final PotionEffect[] effects;

    @ParametersAreNonnullByDefault
    public SimpleConsumable(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, PotionEffect[] effects, int amount) {
        super(Items.CATEGORY, item, recipeType, recipe, new SlimefunItemStack(item, amount));
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
                ItemStack item;

                if (e.getHand() == EquipmentSlot.HAND) {
                    item = p.getInventory().getItemInMainHand();
                } else {
                    item = p.getInventory().getItemInOffHand();
                }

                if (item.getAmount() == 1) {
                    p.getInventory().setItemInMainHand(null);
                } else {
                    item.setAmount(item.getAmount() - 1);
                }

                double health = p.getHealth();
                p.addPotionEffects(Arrays.asList(effects));
                p.setHealth(health);
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
            }
        };
    }
}
