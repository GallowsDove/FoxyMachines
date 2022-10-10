package me.gallowsdove.foxymachines.listeners;

import me.gallowsdove.foxymachines.implementation.materials.GhostBlock;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.persistence.PersistentDataType;

public class GhostBlockListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onHitByFishingRod(PlayerFishEvent e) {
        if (e.getCaught() instanceof FallingBlock b &&
                b.getPersistentDataContainer().has(GhostBlock.KEY, PersistentDataType.STRING)) {
            e.setCancelled(true);
        }
    }
}
