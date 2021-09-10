package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


public class ElectricWindStaff extends SlimefunItem implements Rechargeable {

    private static final float COST = 0.75F;

    public ElectricWindStaff() {
        super(Items.ITEM_GROUP, Items.ELECTRIC_WIND_STAFF, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, SlimefunItems.POWER_CRYSTAL, null,
                SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.STAFF_WIND, SlimefunItems.ELECTRIC_MOTOR,
                null, SlimefunItems.MEDIUM_CAPACITOR, null
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onUse());
    }

    @Override
    public float getMaxItemCharge(@Nonnull ItemStack item) {
        return 100;
    }

    @Nonnull
    protected ItemUseHandler onUse() {
        return e -> {
            Player p = e.getPlayer();
            ItemStack item = e.getItem();

            if (removeItemCharge(item, COST)) {
                p.setVelocity(p.getEyeLocation().getDirection().multiply(4));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 1);
                p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
                p.setFallDistance(0F);
            }
        };
    }
}
