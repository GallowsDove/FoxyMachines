package me.gallowsdove.foxymachines.listeners;

import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.util.Map;

public class SlimeWorldCompatListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        reapplyChunkLoaders(e.getWorld());
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        clearChunkLoaders(e.getWorld());
    }

    private void reapplyChunkLoaders(World world) {
        Map<Location, ?> storage = BlockStorage.getRawStorage(world);
        if (storage == null) return;

        for (Location loc : storage.keySet()) {
            try {
                String id = BlockStorage.checkID(loc);
                if (id != null && id.equals("CHUNK_LOADER")) {
                    loc.getChunk().setForceLoaded(true);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void clearChunkLoaders(World world) {
        Map<Location, ?> storage = BlockStorage.getRawStorage(world);
        if (storage == null) return;

        for (Location loc : storage.keySet()) {
            try {
                String id = BlockStorage.checkID(loc);
                if (id != null && id.equals("CHUNK_LOADER")) {
                    loc.getChunk().setForceLoaded(false);
                }
            } catch (Exception ignored) {
            }
        }
    }

}
