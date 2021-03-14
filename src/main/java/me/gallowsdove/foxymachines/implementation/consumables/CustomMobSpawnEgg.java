package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
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

    @ParametersAreNonnullByDefault
    public CustomMobSpawnEgg(String id, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(Items.category, item, recipeType, recipe);
        this.id = id;
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
                    ItemStack item = p.getInventory().getItemInMainHand();
                    if (item.getAmount() == 1) {
                        p.getInventory().setItemInMainHand(null);
                    } else {
                        item.setAmount(item.getAmount() - 1);
                    }
                    CustomMob.getByID(id).spawn(e.getPlayer().getLocation());
                }
            }
        };
    }
}
