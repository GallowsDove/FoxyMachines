package me.gallowsdove.foxymachines.listeners;

import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.api.gps.GPSNetwork;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import java.util.UUID;

public class ChunkLoaderPlaceListener implements Listener {
  @EventHandler
  public void onChunkLoaderPlace(BlockPlaceEvent e) {
    ItemStack item = 	e.getItemInHand();
    Player p = e.getPlayer();
    if (!SlimefunUtils.isItemSimilar(item, Items.CHUNK_LOADER, true, false)) {
      return;
    }
    Block b = e.getBlockPlaced();
    if (b.getChunk().isForceLoaded()) {
      e.setCancelled(true);
      p.sendMessage(ChatColor.LIGHT_PURPLE + "This chunk is already loaded!");
      return;
    }
    NamespacedKey key = new NamespacedKey(FoxyMachines.getInstance(), "chunkloaders");

    Integer i = 1;
    if (p.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
      i = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) + 1;
    }


    if (SlimefunPlugin.getGPSNetwork().getNetworkComplexity(p.getUniqueId()) < 7500*i) {
      p.sendMessage(ChatColor.LIGHT_PURPLE + "Get more GPS Network Complexity to place more Chunk Loaders.");
      e.setCancelled(true);
      return;
    }

    p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);
    b.getChunk().setForceLoaded(true);
    BlockStorage.addBlockInfo(b, "owner", p.getUniqueId().toString());
  }
}
