package me.gallowsdove.foxymachines.utils;

import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public final class EmptySphereBlocks {
    private EmptySphereBlocks() {}

    public static ArrayList<Block> get(@Nonnull Block target, int radius) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (int y = -radius; y <= radius; y++) {
            int ypow = y * y;
            for (int x = -radius; x <= radius; x++) {
                int xpow = x * x;
                for (int z = -radius; z <= radius; z++) {
                    if (Math.floor(Math.sqrt(xpow + ypow + (z * z))) == radius) {
                        final Block b = target.getWorld().getBlockAt(x + target.getX(), y + target.getY(), z + target.getZ());
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }
}
