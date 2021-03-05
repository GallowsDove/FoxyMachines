package me.gallowsdove.foxymachines.types;

import me.gallowsdove.foxymachines.FoxyMachines;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class FoxyRecipeType {
    public static RecipeType SACRIFICIAL_ALTAR = new RecipeType(new NamespacedKey(FoxyMachines.getInstance(), "sacrificial_altar"),
            new CustomItem(Material.POLISHED_BLACKSTONE_BRICKS, "&cSacrificial Altar", "", "&e&oSacrifice the mob in the Sacrificial Altar",
                    "&e&oUse &c/foxy altar &e&oto view the multiblock."));
    public static RecipeType FISHING = new RecipeType(new NamespacedKey(FoxyMachines.getInstance(), "fishing"),
            new CustomItem(Material.FISHING_ROD, "&bFishing", "", "&e&oGet this as a fishing loot."));
}
