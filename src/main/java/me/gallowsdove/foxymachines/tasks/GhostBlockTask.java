package me.gallowsdove.foxymachines.tasks;

import me.gallowsdove.foxymachines.implementation.materials.GhostBlock;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class GhostBlockTask extends BukkitRunnable {
    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (FallingBlock block : world.getEntitiesByClass(FallingBlock.class)) {
                if (block.getPersistentDataContainer().has(GhostBlock.KEY, PersistentDataType.STRING)) {
                    block.setTicksLived(1);
                }
            }
        }
    }
}
