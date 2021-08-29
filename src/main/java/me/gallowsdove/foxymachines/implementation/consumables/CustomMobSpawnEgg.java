package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class CustomMobSpawnEgg extends SimpleSlimefunItem<ItemUseHandler> {

    String id;
    SlimefunItemStack slimefunItem;

    @ParametersAreNonnullByDefault
    public CustomMobSpawnEgg(String id, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(Items.CATEGORY, item, recipeType, recipe);
        this.id = id;
        this.slimefunItem = item;
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return new ItemUseHandler() {
            @Override
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();
                Player p = e.getPlayer();
                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), p.getLocation(), ProtectableAction.ATTACK_PLAYER)) {
                    ItemStack item = SlimefunUtils.isItemSimilar(p.getInventory().getItemInMainHand(), slimefunItem, false, false) ? p.getInventory().getItemInMainHand() : p.getInventory().getItemInOffHand();
                    item.setAmount(item.getAmount() - 1);
                    CustomMob.getByID(id).spawn(e.getPlayer().getLocation());
                }
            }
        };
    }
}
