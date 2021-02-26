package me.gallowsdove.foxymachines.listeners;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class BerryBushListener implements Listener {
    @EventHandler
    private void onBerryBushDamage(EntityDamageByBlockEvent e) {
        Block b = e.getDamager();


        if ( b != null && b.getType() == Material.SWEET_BERRY_BUSH) {
            if (BlockStorage.getLocationInfo(b.getLocation(), "trimmed") == "true") {
                e.setCancelled(true);
            }
        }
    }

    // TODO improve, why does this even work
    @EventHandler
    private void onBushBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.SWEET_BERRY_BUSH && BlockStorage.getLocationInfo(b.getLocation(), "trimmed") != null) {
            BlockStorage.addBlockInfo(b.getLocation(), "trimmed", null);
            BlockStorage.clearBlockInfo(b.getLocation());
        }
    }
}
