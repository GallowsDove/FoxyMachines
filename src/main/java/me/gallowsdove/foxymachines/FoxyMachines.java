package me.gallowsdove.foxymachines;

import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import lombok.SneakyThrows;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
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
    public void onEnable() {
        super.onEnable();

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
        scheduleRepeatingAsync(new GhostBlockTask(), 100);
        if (getConfig().getBoolean("custom-mobs")) {
            scheduleRepeatingSync(new MobTicker(), 2);
        }
    }

    @Override
    protected int getMetricsID() {
        return 10568;
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "GallowsDove/FoxyMachines/master";
    }

    @Override
    protected List<AbstractCommand> getSubCommands() {
        ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>(Arrays.asList(new QuestCommand(), new SacrificialAltarCommand()));
        if (getConfig().getBoolean("custom-mobs")) {
            commands.add(new SummonCommand());
        }
        return commands;
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        BerryBushTrimmer.saveTrimmedBlocks();
        ForcefieldDome.saveDomeLocations();
        CustomBoss.removeBossBars();
    }



    @Nonnull
    public static FoxyMachines getInstance() {
        return instance;
    }
}
