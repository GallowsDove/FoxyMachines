package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import javax.annotation.Nonnull;

public class BerryBushListener implements Listener {
    @EventHandler
    private void onBerryBushDamage(@Nonnull EntityDamageByBlockEvent e) {
        Block b = e.getDamager();


        if ( b != null && b.getType() == Material.SWEET_BERRY_BUSH) {
            if (BlockStorage.getLocationInfo(b.getLocation(), "trimmed") != null) {
                e.setCancelled(true);
            }
        }
    }

    // TODO improve, why does this even work
    @EventHandler
    private void onBushBreak(@Nonnull BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.SWEET_BERRY_BUSH && BlockStorage.getLocationInfo(b.getLocation(), "trimmed") != null) {
            BlockStorage.addBlockInfo(b.getLocation(), "trimmed", null);
            BlockStorage.clearBlockInfo(b.getLocation());
        }
    }

    @EventHandler
    private void onSheepShear(@Nonnull PlayerShearEntityEvent e) {
        if (SlimefunUtils.isItemSimilar(e.getItem(), Items.BERRY_BUSH_TRIMMER.getItem().getItem(), true)) {
            e.setCancelled(true);
        }
    }
}
