package me.gallowsdove.foxymachines.tools;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Effect;
import org.bukkit.Sound;
import me.gallowsdove.foxymachines.items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;


public class ElectricWindStaff extends SlimefunItem implements Rechargeable {

  private static final float COST = 0.5F;

  public ElectricWindStaff() {
    super(Items.foxy_tools, Items.ELECTRIC_WIND_STAFF, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      null, null, null,
      null, SlimefunItems.SMALL_CAPACITOR, null,
      null, null, null
    });

  @Override
  public float getMaxItemCharge(ItemStack item) {
    return 50;
  }

  @Override
  public ItemUseHandler getItemHandler() {
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
