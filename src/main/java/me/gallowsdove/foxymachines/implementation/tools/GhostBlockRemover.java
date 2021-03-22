package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.core.handlers.EntityInteractHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.implementation.materials.GhostBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class GhostBlockRemover extends SlimefunItem {

    public GhostBlockRemover() {
        super(Items.CATEGORY, Items.GHOST_BLOCK_REMOVER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.DAMIENIUM, Items.DAMIENIUM, Items.DAMIENIUM,
                Items.SWEET_INGOT, SlimefunItems.BASIC_CIRCUIT_BOARD, Items.SWEET_INGOT,
                Items.DAMIENIUM, Items.DAMIENIUM, Items.DAMIENIUM
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onInteract());
    }

    @Nonnull
    protected EntityInteractHandler onInteract() {
        return new EntityInteractHandler() {
            @Override
            public void onInteract(PlayerInteractEntityEvent e, ItemStack itemStack, boolean b) {
                Player p = e.getPlayer();
                if (e.getRightClicked() instanceof FallingBlock) {
                    FallingBlock block = (FallingBlock) e.getRightClicked();

                    if (block.getPersistentDataContainer().has(GhostBlock.KEY, PersistentDataType.STRING)) {
                        Material material = block.getBlockData().getMaterial();
                        SlimefunItemStack stack = new SlimefunItemStack(
                                "GHOST_BLOCK_" + material.name().toUpperCase(),
                                material,
                                "Ghost Block: &6" + StringUtils.capitalize(material.name().replace("_", " ").toLowerCase()),
                                "",
                                "&7An intangible block.");

                        block.getWorld().dropItemNaturally(block.getLocation(), stack);
                        block.remove();
                    }
                }
            }
        };
    }
}
