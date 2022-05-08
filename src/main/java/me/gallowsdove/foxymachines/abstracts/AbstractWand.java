package me.gallowsdove.foxymachines.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public abstract class AbstractWand extends SlimefunItem implements NotPlaceable, Rechargeable {
    public AbstractWand(SlimefunItemStack item, RecipeType recipeType, ItemStack [] recipe) {
        super(Items.TOOLS_ITEM_GROUP, item, recipeType, recipe);
    }

    private static final NamespacedKey MATERIAL_KEY = new NamespacedKey(FoxyMachines.getInstance(), "wand_material");

    public static final Set<Material> BLACKLISTED = Set.of(Material.BARRIER, Material.SPAWNER, Material.COMMAND_BLOCK,
            Material.STRUCTURE_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.JIGSAW);

    public static final Set<Material> WHITELISTED = Set.of(Material.GLASS, Material.ACACIA_LEAVES, Material.AZALEA_LEAVES,
            Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES, Material.FLOWERING_AZALEA_LEAVES, Material.JUNGLE_LEAVES,
            Material.OAK_LEAVES, Material.SPRUCE_LEAVES, Material.GRAY_STAINED_GLASS, Material.BLACK_STAINED_GLASS,
            Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.GREEN_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS,
            Material.ORANGE_STAINED_GLASS, Material.PINK_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.RED_STAINED_GLASS,
            Material.WHITE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS);

    protected abstract float getCostPerBBlock();

    protected abstract boolean isRemoving();

    @Override
    public void preRegister() {
        addItemHandler(onUse());
    }

    @Nonnull
    protected ItemUseHandler onUse() {
        return e -> {
            Player player = e.getPlayer();
            ItemStack itemInInventory = player.getInventory().getItemInMainHand();
            ItemMeta meta = itemInInventory.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();

            if (player.isSneaking()) {
                if (!isRemoving() && e.getClickedBlock().isPresent()) {

                    Material material = e.getClickedBlock().get().getType();

                    if ((material.isBlock() && material.isSolid() && material.isOccluding() && !AbstractWand.BLACKLISTED.contains(material)) ||
                    AbstractWand.WHITELISTED.contains(material)) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Material set to: " + material);
                        container.set(AbstractWand.MATERIAL_KEY, PersistentDataType.STRING, material.toString());
                        List<String> lore = this.getItem().getItemMeta().getLore();
                        lore.set(lore.size() - 2, ChatColor.GRAY + "Material: " + ChatColor.YELLOW + material);
                        meta.setLore(lore);
                        itemInInventory.setItemMeta(meta);
                        setItemCharge(itemInInventory, getItemCharge(itemInInventory)); // To update it in lore
                    }
                }
            } else {
                if (isRemoving() && !container.has(MATERIAL_KEY, PersistentDataType.STRING)) {
                    container.set(MATERIAL_KEY, PersistentDataType.STRING, Material.AIR.toString());
                }

                List<Location> locs = getLocations(player);

                if (locs.size() == 0) {
                    return;
                }

                Inventory inventory = player.getInventory();
                if (!container.has(MATERIAL_KEY, PersistentDataType.STRING)) {
                    player.sendMessage(ChatColor.RED + "Select a building material with Shift + Right Click!");
                    return;
                }
                Material material = Material.getMaterial(container.get(MATERIAL_KEY, PersistentDataType.STRING));

                ItemStack blocks = new ItemStack(material, locs.size());

                if (isRemoving() || inventory.containsAtLeast(blocks, locs.size())) {
                    if (removeItemCharge(e.getItem(),getCostPerBBlock() * locs.size())) {
                        inventory.removeItem(blocks);
                        for (Location loc : locs) {
                            Bukkit.getScheduler().runTask(FoxyMachines.getInstance(), () -> loc.getBlock().setType(material));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Your item doesn't have enough energy for that!");
                        player.sendMessage(ChatColor.RED + "Energy needed: " + getCostPerBBlock() * locs.size());
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "There aren't enough materials in your inventory!");
                    player.sendMessage(ChatColor.RED + "Current items: " + Utils.countItemInInventory(inventory, blocks) + " Needed: " + locs.size());
                }
            }
        };
    }

    protected abstract List<Location> getLocations(@Nonnull Player player);
}
