package me.gallowsdove.foxymachines.utils;

import dev.aurelium.auraskills.api.AuraSkillsBukkitProvider;
import dev.aurelium.auraskills.api.region.Regions;
import org.bukkit.block.Block;

public class AuraSkillsCompat {
    public static void addPlacedBlock(Block block) {
        Regions regions = AuraSkillsBukkitProvider.getInstance().getRegions();
        if (regions != null) {
            regions.addPlacedBlock(block);
        }
    }
}
