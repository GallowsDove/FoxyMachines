package me.gallowsdove.foxymachines.items;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.Material;


public final class Items{
  Category tools = new Category(
    new NamespacedKey(this, "foxy_tools"),
    new CustomItem(Material.SCISSORS, "&4Foxy Tools", "", "&a> Click to open"));

    public static final SlimefunItemStack ELECTRIC_WIND_STAFF = new SlimefunItemStack(
       "ELECTRIC_WIND_STAFF",
       Material.SCISSORS,
       "&9Electric Wind Staff",
       "",
       "&8\u21E8 &7Electric version of the wind staff.",
       "",
       "&c&o&8\u21E8 &e\u26A1 &70 / 50 J"
   );

};
