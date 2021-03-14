package me.gallowsdove.foxymachines;

import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.commands.QuestCommand;
import me.gallowsdove.foxymachines.commands.SacrificialAltarCommand;
import me.gallowsdove.foxymachines.commands.SummonCommand;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.listeners.*;
import me.gallowsdove.foxymachines.tickers.MobTicker;
import me.gallowsdove.foxymachines.tickers.QuestTicker;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
    private static FoxyMachines instance;

    public String folderPath;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("foxymachines", this, "GallowsDove/FoxyMachines/master", getFile());

        CommandManager.setup("foxymachines", "foxymachines.admin", "/fm, /foxy",
                new SacrificialAltarCommand(), new QuestCommand(), new SummonCommand());

        Metrics metrics = PluginUtils.setupMetrics(10568);

        PluginUtils.registerListener(new ChunkLoaderListener());
        PluginUtils.registerListener(new BoostedRailListener());
        PluginUtils.registerListener(new BerryBushListener());
        PluginUtils.registerListener(new ForcefieldListener());
        PluginUtils.registerListener(new RemoteControllerListener());
        PluginUtils.registerListener(new SacrificialAltarListener());
        PluginUtils.registerListener(new SwordListener());
        PluginUtils.registerListener(new PoseidonsFishingRodListener());
        PluginUtils.registerListener(new ArmorListener());

        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        this.folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        ForcefieldDome.loadDomeLocations();
        PluginUtils.runSync(() -> ForcefieldDome.INSTANCE.setupDomes());
        PluginUtils.scheduleRepeatingSync(new QuestTicker(), 10, 240);
        PluginUtils.scheduleRepeatingSync(new MobTicker(), 5);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        ForcefieldDome.saveDomeLocations();
        CustomBoss.removeBossBars();
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
