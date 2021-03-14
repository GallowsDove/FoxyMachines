package me.gallowsdove.foxymachines.abstracts;

import me.gallowsdove.foxymachines.FoxyMachines;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomBoss extends CustomMob {

    private static final NamespacedKey KEY = new NamespacedKey(FoxyMachines.getInstance(), "boss");

    private static final Map<LivingEntity, BossBar> instances = new HashMap<>();

    public CustomBoss(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int health) {
        super(id, name, type, health);
    }

    protected static final class BossBarStyle {
        private final String name;
        private final BarColor color;
        private final BarStyle style;
        private final BarFlag[] flags;

        public BossBarStyle(String name, BarColor color, BarStyle style, BarFlag... flags) {
            this.name = name;
            this.color = color;
            this.style = style;
            this.flags = flags;
        }
    }

    @Nonnull
    protected abstract BossBarStyle getBossBarStyle();

    protected int getBossBarDistance() {
        return 30;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onSpawn(@Nonnull LivingEntity spawned) {
        BossBarStyle style = getBossBarStyle();
        BossBar bossbar = Bukkit.createBossBar(KEY, style.name, style.color, style.style, style.flags);
        bossbar.setVisible(true);
        bossbar.setProgress(1.0);

        spawned.setRemoveWhenFarAway(false);

        instances.put(spawned, bossbar);
    }

    @Override
    public final void onHit(@Nonnull EntityDamageEvent e) {
        this.onBossDamaged(e);

        if (!e.isCancelled() && e.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            BossBar bossbar = getBossBarForEntity(entity);

            double finalHealth = entity.getHealth() - e.getFinalDamage();
            if (finalHealth > 0) {
                bossbar.setProgress(finalHealth / entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            }
        }
    }

    protected void onBossDamaged(@Nonnull EntityDamageEvent e) { }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onDeath(@Nonnull EntityDeathEvent e) {
        BossBar bossbar = getBossBarForEntity(e.getEntity());
        bossbar.setVisible(false);
        bossbar.removeAll();
    }

    @OverridingMethodsMustInvokeSuper
    public void onBossPattern(@Nonnull LivingEntity mob) {
        Location l = mob.getLocation();
        long dist = (long) getBossBarDistance() * getBossBarDistance();

        BossBar bossbar = getBossBarForEntity(mob);

        List<Player> players = bossbar.getPlayers();

        for (Player player : mob.getWorld().getPlayers()) {
            double distSquared = l.distanceSquared(player.getLocation());

            if (distSquared <= dist && !players.contains(player)) {
                bossbar.addPlayer(player);
            } else if (distSquared > dist && players.contains(player)) {
                bossbar.removePlayer(player);
            }
        }
    }

    @Nonnull
    protected final BossBar getBossBarForEntity(LivingEntity entity) {
        if (instances.containsKey(entity)) {
            return instances.get(entity);
        }

        BossBarStyle style = getBossBarStyle();
        BossBar bossbar = Bukkit.createBossBar(KEY, style.name, style.color, style.style, style.flags);
        bossbar.setVisible(true);
        bossbar.setProgress(entity.getHealth() / entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        instances.put(entity, bossbar);
        return bossbar;
    }

    public static void removeBossBars() {
        for (BossBar bossbar: instances.values()) {
            bossbar.setVisible(false);
            bossbar.removeAll();
        }
    }
}