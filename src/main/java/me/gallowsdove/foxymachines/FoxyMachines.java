package me.gallowsdove.foxymachines;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
  private static FoxyMachines instance;

  @Override
  public void onEnable() {
    instance = this;
    // Read something from your config.yml
    Config cfg = new Config(this);
    ItemSetup.INSTANCE.init();
  }

  @Override
  public void onDisable() {
    // Logic for disabling the plugin...
  }

  @Override
  public String getBugTrackerURL() {
    // You can return a link to your Bug Tracker instead of null here
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
