package me.gallowsdove.foxymachines.implementation.materials;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;



public class ImprovementCore extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

  public ImprovementCore() {
    super(Items.materials, Items.IMPROVEMENT_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      SlimefunItems.SYNTHETIC_DIAMOND, Items.REINFORCED_STRING, SlimefunItems.SYNTHETIC_DIAMOND,
      Items.REINFORCED_STRING, SlimefunItems.BLISTERING_INGOT_3, Items.REINFORCED_STRING,
      SlimefunItems.SYNTHETIC_DIAMOND, Items.REINFORCED_STRING, SlimefunItems.SYNTHETIC_DIAMOND
    });
  }

  @Override
  public ItemUseHandler getItemHandler() {
    return e -> {
      e.cancel();
    };
  }
}
