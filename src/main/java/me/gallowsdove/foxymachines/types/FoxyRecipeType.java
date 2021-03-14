package me.gallowsdove.foxymachines.types;

import me.gallowsdove.foxymachines.FoxyMachines;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class FoxyRecipeType {
    private static final CustomItem QUEST_ITEM = new CustomItem(new CustomItem(Material.MOJANG_BANNER_PATTERN, "&6Quest Reward", "", "&e&oGet this by completing the quest with the sword.",
            "&e&oUse &c/foxy quest &e&oto view your current quest."));
    static {
        ItemMeta meta = QUEST_ITEM.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        QUEST_ITEM.setItemMeta(meta);
    }

    public static RecipeType SACRIFICIAL_ALTAR = new RecipeType(new NamespacedKey(FoxyMachines.getInstance(), "sacrificial_altar"),
            new CustomItem(Material.POLISHED_BLACKSTONE_BRICKS, "&cSacrificial Altar", "", "&e&oSacrifice the mob in the Sacrificial Altar",
                    "&e&oUse &c/foxy altar &e&oto view the multiblock."));
    public static RecipeType FISHING = new RecipeType(new NamespacedKey(FoxyMachines.getInstance(), "fishing"),
            new CustomItem(Material.FISHING_ROD, "&bFishing", "", "&e&oGet this as a fishing loot."));
    public static RecipeType QUEST = new RecipeType(new NamespacedKey(FoxyMachines.getInstance(), "quest"), QUEST_ITEM);
    public static RecipeType CUSTOM_MOB_DROP = new RecipeType(new NamespacedKey(FoxyMachines.getInstance(), "mob_drop"),
            new CustomItem(Material.DIAMOND_SWORD, "&aUnique Mob Drop", "", "&e&oObtained by killing the specified mob."));
}
