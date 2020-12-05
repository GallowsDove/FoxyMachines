package me.gallowsdove.foxymachines.listeners;

import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import java.util.UUID;

public class ChunkLoaderBreakListener implements Listener {
  @EventHandler
  public void onChunkLoaderBreak(BlockBreakEvent e) {
    Block b = e.getBlock();
    if (b.getType() != Material.END_ROD) {
      return;
    }

    if (BlockStorage.getLocationInfo(b.getLocation(), "chunk_loader") != "true") {
      return;
    }

    NamespacedKey key = new NamespacedKey(FoxyMachines.getInstance(), "chunkloaders");
    Player p = Bukkit.getPlayer(UUID.fromString(BlockStorage.getLocationInfo(b.getLocation(), "owner")));
    Integer i = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) - 1;
    p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);

    b.getChunk().setForceLoaded(false);
    BlockStorage.clearBlockInfo(b);
  }
}
