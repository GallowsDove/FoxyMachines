package me.gallowsdove.foxymachines;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import lombok.SneakyThrows;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.commands.KillallCommand;
import me.gallowsdove.foxymachines.commands.QuestCommand;
import me.gallowsdove.foxymachines.commands.SacrificialAltarCommand;
import me.gallowsdove.foxymachines.commands.SummonCommand;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.implementation.tools.BerryBushTrimmer;
import me.gallowsdove.foxymachines.listeners.*;
import me.gallowsdove.foxymachines.tasks.GhostBlockTask;
import me.gallowsdove.foxymachines.tasks.MobTicker;
import me.gallowsdove.foxymachines.tasks.QuestTicker;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoxyMachines extends AbstractAddon {
    private static FoxyMachines instance;

    public String folderPath;

    @SneakyThrows
    @Override
    public void enable() {

        instance = this;

        registerListener(new ChunkLoaderListener(), new BoostedRailListener(), new BerryBushListener(), new ForcefieldListener(),
                new RemoteControllerListener(), new SacrificialAltarListener(), new SwordListener(), new PoseidonsFishingRodListener(),
                new ArmorListener(), new BowListener());
        
        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        this.folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        BerryBushTrimmer.loadTrimmedBlocks();
        ForcefieldDome.loadDomeLocations();
        runSync(() -> ForcefieldDome.INSTANCE.setupDomes());
        scheduleRepeatingAsync(new QuestTicker(), 10, 240);
        scheduleRepeatingSync(new GhostBlockTask(), 100);
        if (getConfig().getBoolean("custom-mobs")) {
            scheduleRepeatingSync(new MobTicker(), 2);
        }
    }

    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 8991);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "GallowsDove/FoxyMachines/master";
    }

    @Override
    protected List<AbstractCommand> setupSubCommands() {
        ArrayList<AbstractCommand> commands = new ArrayList<>(Arrays.asList(new QuestCommand(), new SacrificialAltarCommand()));
        if (getConfig().getBoolean("custom-mobs")) {
            commands.add(new SummonCommand());
            commands.add(new KillallCommand());
        }
        return commands;
    }

    @SneakyThrows
    @Override
    public void disable() {
        BerryBushTrimmer.saveTrimmedBlocks();
        ForcefieldDome.saveDomeLocations();
        if (getConfig().getBoolean("custom-mobs")) {
            CustomBoss.removeBossBars();
        }
    }

    @Override
    public String getAutoUpdatePath() {
        return "auto-update";
    }

    @Nonnull
    public static FoxyMachines getInstance() {
        return instance;
    }
}
