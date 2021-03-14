package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.implementation.tools.BerryBushTrimmer;
import me.gallowsdove.foxymachines.utils.SimpleLocation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import javax.annotation.Nonnull;

public class BerryBushListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    private void onBerryBushDamage(@Nonnull EntityDamageByBlockEvent e) {
        Block b = e.getDamager();


        if ( b != null && b.getType() == Material.SWEET_BERRY_BUSH) {
            if (BerryBushTrimmer.TRIMMED_BLOCKS.contains(new SimpleLocation(b))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBushBreak(@Nonnull BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.SWEET_BERRY_BUSH) {
            BerryBushTrimmer.TRIMMED_BLOCKS.remove(new SimpleLocation(b));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onSheepShear(@Nonnull PlayerShearEntityEvent e) {
        if (SlimefunUtils.isItemSimilar(e.getItem(), Items.BERRY_BUSH_TRIMMER.getItem().getItem(), true)) {
            e.setCancelled(true);
        }
    }
}
