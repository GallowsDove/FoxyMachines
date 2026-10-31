package me.gallowsdove.foxymachines;

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon;
import me.gallowsdove.foxymachines.abstracts.AbstractWand;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.implementation.consumables.UnbreakableRune;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.implementation.tools.BerryBushTrimmer;
import me.gallowsdove.foxymachines.listeners.*;
import me.gallowsdove.foxymachines.tasks.GhostBlockTask;
import me.gallowsdove.foxymachines.tasks.MobTicker;
import me.gallowsdove.foxymachines.tasks.QuestTicker;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
    private static FoxyMachines instance;

    public String folderPath;

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/GallowsDove/FoxyMachines/issues";
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Register listeners through Bukkit instead of the removed InfinityLib event helper.
        Bukkit.getPluginManager().registerEvents(new ChunkLoadListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkLoaderListener(), this);
        Bukkit.getPluginManager().registerEvents(new SlimeWorldCompatListener(), this);
        Bukkit.getPluginManager().registerEvents(new BoostedRailListener(), this);
        Bukkit.getPluginManager().registerEvents(new BerryBushListener(), this);
        Bukkit.getPluginManager().registerEvents(new ForcefieldListener(), this);
        Bukkit.getPluginManager().registerEvents(new GhostBlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new RemoteControllerListener(), this);
        Bukkit.getPluginManager().registerEvents(new SacrificialAltarListener(), this);
        Bukkit.getPluginManager().registerEvents(new SwordListener(), this);
        Bukkit.getPluginManager().registerEvents(new PoseidonsFishingRodListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorListener(), this);
        Bukkit.getPluginManager().registerEvents(new BowListener(), this);
        Bukkit.getPluginManager().registerEvents(new PositionSelectorListener(), this);

        QuestUtils.init();
        AbstractWand.init();
        UnbreakableRune.init();
        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        BerryBushTrimmer.loadTrimmedBlocks();
        ForcefieldDome.loadDomeLocations();

        Bukkit.getScheduler().runTask(this, () -> ForcefieldDome.INSTANCE.setupDomes());
        Bukkit.getScheduler().runTaskTimer(this, new QuestTicker(), 240L, 10L);
        Bukkit.getScheduler().runTaskTimer(this, new GhostBlockTask(), 100L, 100L);

        if (getConfig().getBoolean("custom-mobs")) {
            Bukkit.getScheduler().runTaskTimer(this, new MobTicker(), 2L, 2L);
        }

        getLogger().info("FoxyMachines enabled.");
    }

    @Override
    public void onDisable() {
        BerryBushTrimmer.saveTrimmedBlocks();
        ForcefieldDome.saveDomeLocations();

        if (getConfig().getBoolean("custom-mobs")) {
            CustomBoss.removeBossBars();
        }

        Bukkit.getScheduler().cancelTasks(this);
        instance = null;
    }

    public static FoxyMachines getInstance() {
        return instance;
    }
}
