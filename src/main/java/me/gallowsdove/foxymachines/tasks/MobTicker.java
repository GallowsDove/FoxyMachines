package me.gallowsdove.foxymachines.tasks;

import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MobTicker implements Runnable {
    int tick = 0;

    @Override
    public void run() {
        for (CustomMob mob : CustomMob.MOBS.values()) {
            mob.onUniqueTick(tick);
        }

        for (Map.Entry<CustomMob, Set<UUID>> entry : CustomMob.MOB_CACHE.entrySet()) {
            CustomMob customMob = entry.getKey();
            Set<UUID> entities = entry.getValue();
            Set<UUID> newEntities = new HashSet<>();
            for (UUID uuid : new HashSet<>(entities)) {
                Entity entity = Bukkit.getEntity(uuid);
                if (!(entity instanceof LivingEntity livingEntity)) {
                    continue;
                }

                newEntities.add(uuid);
                customMob.onMobTick(livingEntity, tick);
            }
            entry.setValue(newEntities);
        }

        if (tick == 100) {
            tick = 0;
        }
        tick++;
    }
}
