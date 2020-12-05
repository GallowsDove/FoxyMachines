package me.gallowsdove.foxymachines.implementation.machines;

import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;


public class ChunkLoader extends SlimefunItem {
  public ChunkLoader() {
    super(Items.category, Items.CHUNK_LOADER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      Items.REINFORCED_STRING, Items.STABILIZED_BLISTERING_BLOCK, Items.REINFORCED_STRING,
      SlimefunItems.ENRICHED_NETHER_ICE, Items.STABILIZED_BLISTERING_BLOCK, SlimefunItems.ENRICHED_NETHER_ICE,
      Items.REINFORCED_STRING, Items.STABILIZED_BLISTERING_BLOCK, Items.REINFORCED_STRING
    });
  }
}
