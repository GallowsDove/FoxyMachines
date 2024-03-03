package me.gallowsdove.foxymachines.abstracts;

import io.github.mooy1.infinitylib.common.Events;
import lombok.Getter;
import me.gallowsdove.foxymachines.FoxyMachines;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public abstract class CustomMob {

    public static final Map<String, CustomMob> MOBS = new HashMap<>();
    public static final Map<CustomMob, Set<UUID>> MOB_CACHE = new HashMap<>();

    @Nullable
    public static CustomMob getById(@Nonnull String id) {
        return MOBS.get(id);
    }

    @Nullable
    public static CustomMob getByEntity(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, CustomMob.KEY);
        return id == null ? null : getById(id);
    }

    private static final NamespacedKey KEY = new NamespacedKey(FoxyMachines.getInstance(), "mob");

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Getter
    @Nonnull
    private final EntityType type;
    private final int health;

    @ParametersAreNonnullByDefault
    protected CustomMob(String id, String name, EntityType type, int health) {
        Validate.notNull(this.id = id);
        Validate.notNull(this.name = ChatColors.color(name));
        Validate.notNull(this.type = type);
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");
        Validate.isTrue((this.health = health) > 0);
        Validate.notNull(getSpawnOffset());

        MOBS.put(id, this);
    }

    @Nonnull
    public final LivingEntity spawn(@Nonnull Location loc) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, this.type);
        PersistentDataAPI.setString(entity, KEY, this.id);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.health);
        entity.setHealth(this.health);
        entity.setCustomName(this.name);
        entity.setCustomNameVisible(true);

        entity.setRemoveWhenFarAway(true);

        onSpawn(entity);
        cacheEntity(entity);

        return entity;
    }

    public void onUniqueTick(int tick) { }

    protected void onSpawn(@Nonnull LivingEntity spawned) { }

    public void onMobTick(@Nonnull LivingEntity mob, int tick) { }

    protected void onHit(@Nonnull EntityDamageEvent event) { }

    protected void onAttack(@Nonnull EntityDamageByEntityEvent event) { }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent event) { }

    protected void onTarget(@Nonnull EntityTargetEvent event) { }

    @OverridingMethodsMustInvokeSuper
    protected void onDeath(@Nonnull EntityDeathEvent event) {
        uncacheEntity(event.getEntity());
    }

    protected void onCastSpell(@Nonnull EntitySpellCastEvent event) { }

    protected void onDamage(@Nonnull EntityDamageEvent event) { }

    protected Vector getSpawnOffset() {
        return new Vector();
    }

    public void cacheEntity(@Nonnull Entity entity) {
        Set<UUID> entities = MOB_CACHE.getOrDefault(this, new HashSet<>());
        entities.add(entity.getUniqueId());
        MOB_CACHE.put(this, entities);
    }

    public void uncacheEntity(@Nonnull Entity entity) {
        Set<UUID> entities = MOB_CACHE.getOrDefault(this, new HashSet<>());
        entities.remove(entity.getUniqueId());
        MOB_CACHE.put(this, entities);
    }

    static {
        Events.registerListener(new Listener() {

            @EventHandler
            public void onTarget(@Nonnull EntityTargetEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getEntity());
                if (customMob != null) {
                    customMob.onTarget(event);
                }
            }

            @EventHandler
            public void onInteract(@Nonnull PlayerInteractEntityEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getRightClicked());
                if (customMob != null) {
                    customMob.onInteract(event);
                }
            }

            @EventHandler
            public void onHit(@Nonnull EntityDamageByEntityEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getDamager());
                if (customMob != null) {
                    customMob.onAttack(event);
                }
            }

            @EventHandler
            public void onDamaged(@Nonnull EntityDamageEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getEntity());
                if (customMob != null) {
                    customMob.onHit(event);
                }
            }

            @EventHandler
            public void onDeath(@Nonnull EntityDeathEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getEntity());
                if (customMob != null) {
                    customMob.onDeath(event);
                }
            }

            @EventHandler
            public void onSpellCast(@Nonnull EntitySpellCastEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getEntity());
                if (customMob != null) {
                    customMob.onCastSpell(event);
                }
            }

            @EventHandler
            public void onDamage(@Nonnull EntityDamageEvent event) {
                CustomMob customMob = CustomMob.getByEntity(event.getEntity());
                if (customMob != null) {
                    customMob.onDamage(event);
                }
            }

            @EventHandler(ignoreCancelled = true)
            private void onNametagEvent(PlayerInteractEntityEvent event) {
                ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

                if (item.getType() == Material.NAME_TAG && CustomMob.getByEntity(event.getRightClicked()) != null) {
                    event.setCancelled(true);
                }
            }

            @EventHandler(ignoreCancelled = true)
            private void onCombust(EntityCombustEvent event) {
                if (CustomMob.getByEntity(event.getEntity()) != null) {
                    event.setCancelled(true);
                }
            }
        });
    }

}