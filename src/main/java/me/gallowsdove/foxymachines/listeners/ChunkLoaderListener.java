package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class ChunkLoaderListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onChunkLoaderPlace(@Nonnull BlockPlaceEvent e) {
        if(e.getBlock().getType() != Material.BEACON) {
            return;
        }

        ItemStack item = e.getItemInHand();
        Player p = e.getPlayer();

        if (!SlimefunUtils.isItemSimilar(item, Items.CHUNK_LOADER, true, false)) {
            return;
        }

        Block b = e.getBlockPlaced();
        if (b.getChunk().isForceLoaded()) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.LIGHT_PURPLE + "该区块已加载!");
            return;
        }

        NamespacedKey key = new NamespacedKey(FoxyMachines.getInstance(), "chunkloaders");

        int i = 1;
        if (p.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
            i = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) + 1;
        }
        if (!p.hasPermission("foxymachines.bypass-chunk-loader-limit")) {
            Config cfg = new Config(FoxyMachines.getInstance());
            int max = cfg.getInt("max-chunk-loaders");
            if(max != 0 && max < i) {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "已达到区块加载器最大数量限制: " + max);
                e.setCancelled(true);
                return;
            }
        }

        if (Slimefun.getGPSNetwork().getNetworkComplexity(p.getUniqueId()) < 7500*i) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + "你需要更高的GPS网络复杂度来放置更多的区块加载器");
            e.setCancelled(true);
            return;
        }

        p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);
        b.getChunk().setForceLoaded(true);
        BlockStorage.addBlockInfo(b, "owner", p.getUniqueId().toString());
    }
}

