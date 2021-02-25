package me.gallowsdove.foxymachines;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.gallowsdove.foxymachines.listeners.BoostedRailListener;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.plugin.java.JavaPlugin;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
    private static FoxyMachines instance;

    @Override
    public void onEnable() {
        instance = this;
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "GallowsDove/FoxyMachines/master/").start();
        }

        getServer().getPluginManager().registerEvents(new BoostedRailListener(), this);

        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/GallowsDove/FoxyMachines/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static FoxyMachines getInstance() {
        return instance;
    }

}
