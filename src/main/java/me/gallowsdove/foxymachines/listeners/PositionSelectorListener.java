package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.implementation.tools.PositionSelector;
import me.gallowsdove.foxymachines.utils.SimpleLocation;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PositionSelectorListener implements Listener {
    @EventHandler
    private void onLeftClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.hasBlock()) {
            Player player = e.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (SlimefunUtils.isItemSimilar(item, Items.POSITION_SELECTOR, false, false) &&
                    ((Rechargeable) SlimefunItem.getByItem(item)).removeItemCharge(item, PositionSelector.COST) ) {
                Block block = e.getClickedBlock();
                SimpleLocation loc = new SimpleLocation(block, "primary_position");
                loc.storePersistently(player.getPersistentDataContainer());
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Primary position set to " + loc);
            }
        }
    }
}
