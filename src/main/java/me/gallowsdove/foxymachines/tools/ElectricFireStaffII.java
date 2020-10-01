package me.gallowsdove.foxymachines.tools;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LargeFireball;
import org.bukkit.Bukkit;
import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;


public class ElectricFireStaffII extends SlimefunItem implements Rechargeable {

  private static final float COST = 1.5F;

  public ElectricFireStaffII() {
    super(Items.tools, Items.ELECTRIC_FIRE_STAFF_II, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      SlimefunItems.CARBONADO, SlimefunItems.POWER_CRYSTAL, SlimefunItems.CARBONADO,
      SlimefunItems.HEATING_COIL, Items.ELECTRIC_FIRE_STAFF, SlimefunItems.HEATING_COIL,
      SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.REINFORCED_ALLOY_INGOT
    });
  }

  @Override
  public float getMaxItemCharge(ItemStack item) {
    return 200;
  }

  protected ItemUseHandler getItemUseHandler() {
    return e -> {
      Player p = e.getPlayer();
      ItemStack item = e.getItem();

      if (removeItemCharge(item, COST)) {
        LargeFireball fireball = p.launchProjectile(LargeFireball.class);
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
