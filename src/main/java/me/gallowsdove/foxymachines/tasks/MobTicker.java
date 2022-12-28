package me.gallowsdove.foxymachines.tasks;

import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

public class MobTicker implements Runnable{
    int tick = 0;

    @Override
    public void run() {
        for (CustomMob mob : CustomMob.MOBS.values()) {
            mob.onUniqueTick(tick);
        }

        for (World world: Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                CustomMob mob = CustomMob.getByEntity(entity);
                if (mob != null) {
                    mob.onMobTick(entity, tick);
                    if (mob instanceof CustomBoss customBoss && tick == 100) {
                        customBoss.onBossPattern(entity);
                    }
                }
            }
        }
        if (tick == 100) {
            tick = 0;
        }
        tick++;
    }
}
