package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SacrificialAltarListener implements Listener {
    @EventHandler
    private void onDeath(EntityDeathEvent e) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        LivingEntity entity = e.getEntity();
        if (findAltar(entity.getLocation().getBlock()) != null) {
            switch (entity.getType()) {
                case RABBIT:
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new SlimefunItemStack(Items.CURSED_RABBIT_PAW, 1));
                    break;
                case PLAYER:
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new SlimefunItemStack(Items.HUMAN_SKULL, 1));
                    break;
                case WITHER_SKELETON:
                    if (random.nextInt(100) < 75) {
                        if (random.nextInt(100) < 33) {
                            entity.getWorld().dropItemNaturally(entity.getLocation(), new SlimefunItemStack(Items.UNHOLY_WITHER_SKELETON_BONE, 2));
                        } else {
                            entity.getWorld().dropItemNaturally(entity.getLocation(), new SlimefunItemStack(Items.UNHOLY_WITHER_SKELETON_BONE, 1));
                        }
                    }
                    break;
                default:
                    break;
            }

            if (random.nextInt(100) < 25) {
                if (random.nextInt(100) < 25) {
                    entity.getWorld().dropItem(entity.getLocation(), new SlimefunItemStack(Items.BLOOD, 2));
                } else {
                    entity.getWorld().dropItem(entity.getLocation(), new SlimefunItemStack(Items.BLOOD, 1));
                }
            }

            entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
            entity.getWorld().spawnParticle(Particle.SOUL, entity.getLocation(), 50);
        }
    }

    @EventHandler
    private void onSoulTorchDrop(BlockDropItemEvent e) {
        List<Item> array = e.getItems();
        if (!array.isEmpty()) {
            if (SlimefunUtils.isItemSimilar(array.get(0).getItemStack(), Items.SACRIFICIAL_ALTAR_SOUL_TORCH, false, false)) {
                BlockStorage.clearBlockInfo(e.getBlock());
            }
        }
    }

    @Nullable
    private Block findAltar(@Nonnull Block b) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = b.getRelative(x, y, z);

                    if (block.getType() == Material.POLISHED_BLACKSTONE_PRESSURE_PLATE && BlockStorage.getLocationInfo(block.getLocation(), "id") != null &&
                            BlockStorage.getLocationInfo(block.getLocation(), "id").equals("SACRIFICIAL_ALTAR_BLACKSTONE_PRESSURE_PLATE")) {
                        return block;
                    }
                }
            }
        }

        return null;
    }
}
