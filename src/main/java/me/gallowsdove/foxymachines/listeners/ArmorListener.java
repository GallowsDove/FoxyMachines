package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof HumanEntity) {
            ItemStack item = ((HumanEntity) (e.getEntity())).getInventory().getLeggings();

            if (SlimefunUtils.isItemSimilar(item, Items.FIERY_LEGGINGS, false, false)) {
                e.getDamager().setFireTicks(100);
            }
        }
    }
}
