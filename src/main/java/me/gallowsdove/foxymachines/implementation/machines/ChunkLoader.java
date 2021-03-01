package me.gallowsdove.foxymachines.implementation.machines;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;


public class ChunkLoader extends SlimefunItem {
    public ChunkLoader() {
        super(Items.category, Items.CHUNK_LOADER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.REINFORCED_STRING, Items.STABILIZED_BLISTERING_BLOCK, Items.REINFORCED_STRING,
                SlimefunItems.ENRICHED_NETHER_ICE, Items.STABILIZED_BLISTERING_BLOCK, SlimefunItems.ENRICHED_NETHER_ICE,
                Items.REINFORCED_STRING, Items.STABILIZED_BLISTERING_BLOCK, Items.REINFORCED_STRING
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onBreak(), onPlace());
    }

    private BlockBreakHandler onBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                Block b = e.getBlock();
                NamespacedKey key = new NamespacedKey(FoxyMachines.getInstance(), "chunkloaders");
                Player p = Bukkit.getPlayer(UUID.fromString(BlockStorage.getLocationInfo(b.getLocation(), "owner")));
                Integer i = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) - 1;
                p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);

                b.getChunk().setForceLoaded(false);
                BlockStorage.clearBlockInfo(b);
            }
        };
    }

    private BlockPlaceHandler onPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Player p = e.getPlayer();
                Block b = e.getBlockPlaced();

                NamespacedKey key = new NamespacedKey(FoxyMachines.getInstance(), "chunkloaders");

                int i = 1;
                if (p.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                    i = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) + 1;
                }

                p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);
                b.getChunk().setForceLoaded(true);
                BlockStorage.addBlockInfo(b, "owner", p.getUniqueId().toString());
            }
        };
    }
}
