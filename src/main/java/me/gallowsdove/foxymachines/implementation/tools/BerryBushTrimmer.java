package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class BerryBushTrimmer extends SlimefunItem {
    public BerryBushTrimmer() {
        super(Items.category, Items.BERRY_BUSH_TRIMMER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.DAMASCUS_STEEL_INGOT,
                new ItemStack(Material.STICK), SlimefunItems.DAMASCUS_STEEL_INGOT, null
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onItemUse(), onToolUse());
    }

    private ToolUseHandler onToolUse() {
        return (e, tool, fortune, drops) -> e.setCancelled(true);
    }

    private ItemUseHandler onItemUse() {
        return e -> {
            if (e.getClickedBlock().isPresent() && e.getClickedBlock().get().getType() == Material.SWEET_BERRY_BUSH) {
                Block b = e.getClickedBlock().get();
                Player p = e.getPlayer();

                if (BlockStorage.getLocationInfo(b.getLocation(), "trimmed") == null) {
                    BlockStorage.addBlockInfo(b, "trimmed", "true");

                    ItemStack shears = e.getItem();
                    ItemMeta shearsMeta = e.getItem().getItemMeta();
                    int damage = ((Damageable) shearsMeta).getDamage() + 4;

                    if (damage > Material.SHEARS.getMaxDurability()) {
                        shears = null;
                    } else {
                        ((Damageable) shearsMeta).setDamage(damage);
                        shears.setItemMeta(shearsMeta);
                    }

                    p.getInventory().setItemInMainHand(shears);
                } else {
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "This berry bush is already trimmed!");
                }
            }

            e.cancel();
        };
    }
}
