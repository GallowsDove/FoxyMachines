package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class CustomMobSpawnEgg extends SimpleSlimefunItem<ItemUseHandler> {

    private final String id;

    @ParametersAreNonnullByDefault
    public CustomMobSpawnEgg(SubItemGroup itemGroup, String id, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        this.id = id;
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();
            Player player = e.getPlayer();
            Location location = player.getLocation();
            if (!Slimefun.getProtectionManager().hasPermission(player, location, Interaction.ATTACK_PLAYER)) {
                return;
            }

            ItemStack item = e.getItem();
            item.setAmount(item.getAmount() - 1);

            CustomMob mob = CustomMob.getById(this.id);
            if (mob != null) {
                mob.spawn(e.getPlayer().getLocation());
            }
        };
    }
}
