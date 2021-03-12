package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.utils.SimpleLocation;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ForcefieldListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerBreak(@Nonnull BlockBreakEvent e) {
        Block b = e.getBlock();

        e.getPlayer().sendMessage("you broke a block");

        if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
            e.getPlayer().sendMessage("you broke a barrier block");
            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
            BlockStorage.addBlockInfo(b, "forcefield", null);
            BlockStorage.clearBlockInfo(b);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onExplosionBreak(@Nonnull BlockExplodeEvent e) {
        Block b = e.getBlock();

        if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
            BlockStorage.addBlockInfo(b, "forcefield", null);
            BlockStorage.clearBlockInfo(b);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onBurnBreak(@Nonnull BlockBurnEvent e) {
        Block b = e.getBlock();

        if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
            BlockStorage.addBlockInfo(b, "forcefield", null);
            BlockStorage.clearBlockInfo(b);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onLeavesDecay(@Nonnull LeavesDecayEvent e) {
        Block b = e.getBlock();

        if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
            BlockStorage.addBlockInfo(b, "forcefield", null);
            BlockStorage.clearBlockInfo(b);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onFadeBreak(@Nonnull BlockFadeEvent e) {
        Block b = e.getBlock();

        if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
            BlockStorage.addBlockInfo(b, "forcefield", null);
            BlockStorage.clearBlockInfo(b);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onBlockDropEvent(@Nonnull EntityChangeBlockEvent e) {
        if (!(e.getEntity() instanceof FallingBlock)) {
            return;
        }

        Block b = e.getBlock();

        if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
            BlockStorage.addBlockInfo(b, "forcefield", null);
            BlockStorage.clearBlockInfo(b);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onBlocksBreakByExplosiveToolEvent(@Nonnull ExplosiveToolBreakBlocksEvent e) {
        for (Block b : e.getBlocks()) {
            if (BlockStorage.getLocationInfo(b.getLocation(), "forcefield") != null) {
                Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> b.setType(Material.BARRIER));
                BlockStorage.addBlockInfo(b, "forcefield", null);
                BlockStorage.clearBlockInfo(b);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
        if (e.getCause() == TeleportCause.ENDER_PEARL || e.getCause() == TeleportCause.CHORUS_FRUIT) {
            Location l = e.getTo();
            for (SimpleLocation loc: ForcefieldDome.domeLocations) {
                if (e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UUID.fromString(loc.getWorldUUID()))) {
                    int xdif = (int) (l.getX() - loc.getX());
                    int ydif = (int) (l.getY() - loc.getY());
                    int zdif = (int) (l.getZ() - loc.getZ());
                    if (Math.floor(Math.sqrt((xdif * xdif) + (ydif * ydif) + (zdif * zdif))) <= 32) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "You can't teleport to a dome!");
                        break;
                    }
                }
            }
        }
    }
}
