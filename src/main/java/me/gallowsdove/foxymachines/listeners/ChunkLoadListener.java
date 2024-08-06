package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import me.gallowsdove.foxymachines.implementation.materials.GhostBlock;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ChunkLoadListener implements Listener {
    private static final Set<ChunkPosition> SCANNED_CHUNKS = new HashSet<>();

    @EventHandler
    public void onChunkLoad(@Nonnull ChunkLoadEvent e) {
        Chunk chunk = e.getChunk();
        ChunkPosition chunkPosition = new ChunkPosition(chunk);
        if (SCANNED_CHUNKS.contains(chunkPosition)) {
            return;
        }
        SCANNED_CHUNKS.add(chunkPosition);

        for (Entity entity : chunk.getEntities()) {
            CustomMob customMob = CustomMob.getByEntity(entity);
            if (customMob != null) {
                customMob.cacheEntity(entity);
            }

            if (entity instanceof FallingBlock && GhostBlock.isGhostBlock(entity)) {
                GhostBlock.BLOCK_CACHE.add(entity.getUniqueId());
            }
        }
    }
}
