package me.gallowsdove.foxymachines.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import me.gallowsdove.foxymachines.FoxyMachines;
import org.bukkit.Bukkit;
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

    private static final NamespacedKey WORLD_KEY = new NamespacedKey(FoxyMachines.getInstance(), "forcefield_world");
    private static final NamespacedKey X_KEY = new NamespacedKey(FoxyMachines.getInstance(), "forcefield_x");
    private static final NamespacedKey Y_KEY = new NamespacedKey(FoxyMachines.getInstance(), "forcefield_y");
    private static final NamespacedKey Z_KEY = new NamespacedKey(FoxyMachines.getInstance(), "forcefield_z");

    @Getter
    private final int x;
    @Getter
    private final int y;
    @Getter
    private final int z;
    @Getter
    private final String worldUUID;

    @Nonnull
    public Block toBlock() {
        return Bukkit.getServer().getWorld(UUID.fromString(this.worldUUID)).getBlockAt(this.x, this.y, this.z);
    }

    public void storePersistently(@Nonnull PersistentDataContainer container) {
        container.set(X_KEY, PersistentDataType.INTEGER, this.x);
        container.set(Y_KEY, PersistentDataType.INTEGER, this.y);
        container.set(Z_KEY, PersistentDataType.INTEGER, this.z);
        container.set(WORLD_KEY, PersistentDataType.STRING, this.worldUUID);
    }

    @Nullable
    public static SimpleLocation fromPersistentStorage(@Nonnull PersistentDataContainer container) {
        if (container.has(WORLD_KEY, PersistentDataType.STRING)) {
            return new SimpleLocation(container.get(X_KEY, PersistentDataType.INTEGER), container.get(Y_KEY, PersistentDataType.INTEGER),
                    container.get(Z_KEY, PersistentDataType.INTEGER), container.get(WORLD_KEY, PersistentDataType.STRING));
        } else {
            return null;
        }
    }
}
