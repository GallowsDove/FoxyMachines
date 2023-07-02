package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof HumanEntity entity) {
            ItemStack item = entity.getInventory().getLeggings();

            if (SlimefunUtils.isItemSimilar(item, Items.FIERY_LEGGINGS, false, false)) {
                e.getDamager().setFireTicks(100);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFallDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof HumanEntity entity) {
            ItemStack item = entity.getInventory().getBoots();

            if (SlimefunUtils.isItemSimilar(item, Items.LIGHT_BOOTS, false, false)) {
                e.setCancelled(true);
            }
        }
    }
}
