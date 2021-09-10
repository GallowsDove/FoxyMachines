package me.gallowsdove.foxymachines;

import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.metrics.bukkit.Metrics;
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

public class FoxyMachines extends AbstractAddon {
    private static FoxyMachines instance;

    public String folderPath;

    public FoxyMachines() {
        super ("GallowsDove", "FoxyMachines", "master", "auto-update");
    }

    @Override
    @SneakyThrows
    public void enable() {

        instance = this;

        Events.registerListener(new ChunkLoaderListener());
        Events.registerListener(new BoostedRailListener());
        Events.registerListener(new BerryBushListener());
        Events.registerListener(new ForcefieldListener());
        Events.registerListener(new RemoteControllerListener());
        Events.registerListener(new SacrificialAltarListener());
        Events.registerListener(new SwordListener());
        Events.registerListener(new PoseidonsFishingRodListener());
        Events.registerListener(new ArmorListener());
        Events.registerListener(new BowListener());
        
        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        this.folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        BerryBushTrimmer.loadTrimmedBlocks();
        ForcefieldDome.loadDomeLocations();
        Scheduler.run(() -> ForcefieldDome.INSTANCE.setupDomes());
        Scheduler.repeat(240, 10, new QuestTicker());
        Scheduler.repeat(100, new GhostBlockTask());
        if (getConfig().getBoolean("custom-mobs")) {
            Scheduler.repeat(2, new MobTicker());
        }

        new Metrics(this, 10568);

        getAddonCommand().addSub(new KillallCommand()).addSub((new QuestCommand())).
                addSub(new SacrificialAltarCommand()).addSub(new SummonCommand());
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

    @Nonnull
    public static FoxyMachines getInstance() {
        return instance;
    }
}
