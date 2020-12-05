package me.gallowsdove.foxymachines.implementation.materials;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;



public class ReinforcedString extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

  public ReinforcedString() {
    super(Items.category, Items.REINFORCED_STRING, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING),
      SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT,
      new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING)
    });
  }

  @Override
  public ItemUseHandler getItemHandler() {
    return e -> {
      e.cancel();
    };
  }
}
