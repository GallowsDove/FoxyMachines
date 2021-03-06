package me.gallowsdove.foxymachines;

import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.gallowsdove.foxymachines.commands.SacrificialAltarCommand;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
    private static FoxyMachines instance;

    public String folderPath;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("foxymachines", this, "GallowsDove/FoxyMachines/master", getFile());

        CommandManager.setup("foxymachines", "foxymachines.info", "/fm, /foxy", new SacrificialAltarCommand());

        Metrics metrics = PluginUtils.setupMetrics(10568);

        getServer().getPluginManager().registerEvents(new ChunkLoaderListener(), this);
        getServer().getPluginManager().registerEvents(new BoostedRailListener(), this);
        getServer().getPluginManager().registerEvents(new BerryBushListener(), this);
        getServer().getPluginManager().registerEvents(new ForcefieldListener(), this);
        getServer().getPluginManager().registerEvents(new RemoteControllerListener(), this);
        getServer().getPluginManager().registerEvents(new SacrificialAltarListener(), this);
        getServer().getPluginManager().registerEvents(new SwordListener(), this);
        getServer().getPluginManager().registerEvents(new PoseidonsFishingRodListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);

        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        this.folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        ForcefieldDome.loadDomeLocations();
        Bukkit.getScheduler().runTask(this, () -> ForcefieldDome.INSTANCE.setupDomes());
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        ForcefieldDome.saveDomeLocations();
    }

    @Nonnull
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/GallowsDove/FoxyMachines/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nonnull
    public static FoxyMachines getInstance() {
        return instance;
    }
}
