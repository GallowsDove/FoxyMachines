package me.gallowsdove.foxymachines.listeners;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class BoostedRailListener implements Listener {
    @EventHandler
    private void onRailUse(VehicleMoveEvent e) {
        if (e.getVehicle() instanceof Minecart) {
            Minecart cart = (Minecart) e.getVehicle();
            Block b = cart.getLocation().getBlock();
            if (BlockStorage.getLocationInfo(b.getLocation(), "boosted") != null) {
                cart.setMaxSpeed(1d);
            } else {
                cart.setMaxSpeed(.4d);
            }
        }
    }
}
