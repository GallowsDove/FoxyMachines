package me.gallowsdove.foxymachines.tasks;

import me.gallowsdove.foxymachines.implementation.materials.GhostBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class GhostBlockTask extends BukkitRunnable {
    @Override
    public void run() {
        for (UUID uuid : new HashSet<>(GhostBlock.BLOCK_CACHE)) {
            Entity entity = Bukkit.getEntity(uuid);
            if (!(entity instanceof FallingBlock)) {
                GhostBlock.BLOCK_CACHE.remove(uuid);
                continue;
            }

            entity.setTicksLived(1);
        }
    }
}
