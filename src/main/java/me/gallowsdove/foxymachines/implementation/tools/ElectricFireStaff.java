package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;


public class ElectricFireStaff extends SlimefunItem implements Rechargeable {

    private static final float COST = 0.75F;

    public ElectricFireStaff() {
        super(Items.category, Items.ELECTRIC_FIRE_STAFF, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.LAVA_CRYSTAL, SlimefunItems.POWER_CRYSTAL, SlimefunItems.LAVA_CRYSTAL,
                SlimefunItems.HEATING_COIL, SlimefunItems.STAFF_FIRE, SlimefunItems.HEATING_COIL,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.REINFORCED_ALLOY_INGOT
        });
    }

    @Override
    public float getMaxItemCharge(ItemStack item) {
        return 100;
    }

    protected ItemUseHandler getItemUseHandler() {
        return e -> {
            Player p = e.getPlayer();
            ItemStack item = e.getItem();

            if (removeItemCharge(item, COST)) {
                SmallFireball fireball = p.launchProjectile(SmallFireball.class);
                fireball.setVelocity(fireball.getVelocity().multiply(50));
            }
        };
    }


    @Override
    public void preRegister() {
        super.preRegister();

        addItemHandler(getItemUseHandler());
    }
}
