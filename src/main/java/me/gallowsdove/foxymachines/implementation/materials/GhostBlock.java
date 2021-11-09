package me.gallowsdove.foxymachines.implementation.materials;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Set;

public class GhostBlock extends SlimefunItem {

    public static final NamespacedKey KEY = new NamespacedKey(FoxyMachines.getInstance(), "ghost_block");

    public static final Set<Material> EXCLUDED = Set.of(Material.BARRIER, Material.SPAWNER, Material.COMMAND_BLOCK,
            Material.STRUCTURE_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.JIGSAW);

    @Nonnull
    private final Material material;

    public GhostBlock(SlimefunItemStack item) {
        super(Items.GHOST_BLOCKS_ITEM_GROUP, item, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.BILLON_INGOT, SlimefunItems.BILLON_INGOT, SlimefunItems.BILLON_INGOT,
                SlimefunItems.MAGICAL_GLASS, new ItemStack(item.getType()), SlimefunItems.MAGICAL_GLASS,
                SlimefunItems.BILLON_INGOT, SlimefunItems.BILLON_INGOT, SlimefunItems.BILLON_INGOT
        });

        this.material = item.getType();
    }

    @Override
    public void preRegister() {
        addItemHandler(onUse());
    }

    @Nonnull
    private ItemUseHandler onUse() {
        return new ItemUseHandler() {
            @Override
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();
                if (e.getClickedBlock().isPresent()) {

                    Player p = e.getPlayer();
                    Block b = e.getClickedBlock().get().getRelative(e.getClickedFace());

                    if (b.getWorld().getNearbyEntities(b.getLocation().add(0.5, 0, 0.5), 0.01, 0.01, 0.01).isEmpty()) {
                        if (Slimefun.getProtectionManager().hasPermission(p, b, Interaction.PLACE_BLOCK)) {
                            FallingBlock block = b.getWorld().spawnFallingBlock(b.getLocation().add(0.5, 0, 0.5), material.createBlockData());
                            block.setVelocity(new Vector(0, 0, 0));
                            block.setGravity(false);
                            block.setDropItem(false);
                            block.setPersistent(true);
                            block.setInvulnerable(true);
                            block.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, "true");


                            ItemStack item = e.getInteractEvent().getItem();

                            item.setAmount(item.getAmount() - 1);
                        } else {
                            p.sendMessage(ChatColor.LIGHT_PURPLE + "你没有权限在这里放置幽灵方块!");
                        }
                    }
                }
            }
        };
    }
}
