package me.gallowsdove.foxymachines.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import me.gallowsdove.foxymachines.FoxyMachines;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

@AllArgsConstructor
@EqualsAndHashCode
public class SimpleLocation {

    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;
    @Getter @Setter
    private int z;
    @Getter
    private final String worldUUID;
    @Getter
    private final String prefix;

    @Nonnull
    public Block toBlock() {
        return Bukkit.getServer().getWorld(UUID.fromString(this.worldUUID)).getBlockAt(this.x, this.y, this.z);
    }

    public SimpleLocation(@Nonnull Block b, @Nonnull String prefix) {
        this(b.getLocation(), prefix);
    }

    public SimpleLocation(@Nonnull Location loc, @Nonnull String prefix) {
        this.worldUUID = loc.getWorld().getUID().toString();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
        this.prefix = prefix;
    }

    public void storePersistently(@Nonnull PersistentDataContainer container) {
        container.set(getXKey(this.prefix), PersistentDataType.INTEGER, this.x);
        container.set(getYKey(this.prefix), PersistentDataType.INTEGER, this.y);
        container.set(getZKey(this.prefix), PersistentDataType.INTEGER, this.z);
        container.set(getWorldKey(this.prefix), PersistentDataType.STRING, this.worldUUID);
    }

    @Nullable
    public static SimpleLocation fromPersistentStorage(@Nonnull PersistentDataContainer container, @Nonnull String prefix) {
        if (container.has(getWorldKey(prefix), PersistentDataType.STRING)) {
            return new SimpleLocation(container.get(getXKey(prefix), PersistentDataType.INTEGER), container.get(getYKey(prefix), PersistentDataType.INTEGER),
                    container.get(getZKey(prefix), PersistentDataType.INTEGER), container.get(getWorldKey(prefix), PersistentDataType.STRING), prefix);
        } else {
            return null;
        }
    }

    private static NamespacedKey getWorldKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_world");
    }

    private static NamespacedKey getXKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_x");
    }

    private static NamespacedKey getYKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_y");
    }

    private static NamespacedKey getZKey(@Nonnull String prefix) {
        return new NamespacedKey(FoxyMachines.getInstance(), prefix + "_z");
    }

    public String toString() {
        return "X: " + this.x + " Y: " + this.y + " Z: " + this.z;
    }
}
