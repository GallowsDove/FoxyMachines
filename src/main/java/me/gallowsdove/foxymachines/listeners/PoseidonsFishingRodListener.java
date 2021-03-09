package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PoseidonsFishingRodListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPlayerFish(PlayerFishEvent e) {
        if (e.getCaught() instanceof Item) {
            Item item = (Item) e.getCaught();
            if (SlimefunUtils.isItemSimilar(e.getPlayer().getInventory().getItemInMainHand(), Items.POSEIDONS_FISHING_ROD, false, false)) {
                if (ThreadLocalRandom.current().nextInt(100) < 8) {
                    item.setItemStack(new SlimefunItemStack(Items.POSEIDONS_BLESSING, 1));
                }
            }
        }
    }
}
