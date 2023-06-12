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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class CustomMob {

    public static final Map<String, CustomMob> MOBS = new HashMap<>();

    @Nullable
    public static CustomMob getByID(@Nonnull String id) {
        return MOBS.get(id);
    }

    @Nullable
    public static CustomMob getByEntity(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, CustomMob.KEY);
        return id == null ? null : getByID(id);
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

    public CustomMob(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int health) {
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

        return entity;
    }

    public void onUniqueTick(int tick) { }

    protected void onSpawn(@Nonnull LivingEntity spawned) { }

    public void onMobTick(@Nonnull LivingEntity mob, int tick) { }

    protected void onHit(@Nonnull EntityDamageEvent e) { }

    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) { }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    protected void onTarget(@Nonnull EntityTargetEvent e) { }

    protected void onDeath(@Nonnull EntityDeathEvent e) { }

    protected void onCastSpell(EntitySpellCastEvent e) { }

    protected void onDamage(EntityDamageEvent e) { }

    protected Vector getSpawnOffset() {
        return new Vector();
    }

    static {
        Events.registerListener(new Listener() {

            @EventHandler
            public void onTarget(@Nonnull EntityTargetEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getEntity());
                if (customMob != null) {
                    customMob.onTarget(e);
                }
            }

            @EventHandler
            public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getRightClicked());
                if (customMob != null) {
                    customMob.onInteract(e);
                }
            }

            @EventHandler
            public void onHit(@Nonnull EntityDamageByEntityEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getDamager());
                if (customMob != null) {
                    customMob.onAttack(e);
                }
            }

            @EventHandler
            public void onDamaged(@Nonnull EntityDamageEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getEntity());
                if (customMob != null) {
                    customMob.onHit(e);
                }
            }

            @EventHandler
            public void onDeath(@Nonnull EntityDeathEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getEntity());
                if (customMob != null) {
                    customMob.onDeath(e);
                }
            }

            @EventHandler
            public void onSpellCast(@Nonnull EntitySpellCastEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getEntity());
                if (customMob != null) {
                    customMob.onCastSpell(e);
                }
            }

            @EventHandler
            public void onDamage(@Nonnull EntityDamageEvent e) {
                CustomMob customMob = CustomMob.getByEntity(e.getEntity());
                if (customMob != null) {
                    customMob.onDamage(e);
                }
            }

            @EventHandler(ignoreCancelled = true)
            private void onNametagEvent(PlayerInteractEntityEvent e) {
                ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

                if (item.getType() == Material.NAME_TAG) {
                    if (CustomMob.getByEntity(e.getRightClicked()) != null) {
                        e.setCancelled(true);
                    }
                }
            }

            @EventHandler(ignoreCancelled = true)
            private void onCombust(EntityCombustEvent e) {
                if (CustomMob.getByEntity(e.getEntity()) != null) {
                    e.setCancelled(true);
                }
            }
        });
    }

}