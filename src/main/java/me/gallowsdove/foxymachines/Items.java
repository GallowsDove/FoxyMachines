package me.gallowsdove.foxymachines;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;


public final class Items{

  public static Category tools = new Category(
    new NamespacedKey(FoxyMachines.getInstance(), "foxy_tools"),
    new CustomItem(Material.SHEARS, "&4Foxy Tools", "", "&a> Click to open"));

    public static final SlimefunItemStack ELECTRIC_WIND_STAFF = new SlimefunItemStack(
       "ELECTRIC_WIND_STAFF",
       Material.BLAZE_ROD,
       "&bElectric Wind Staff",
       "",
       "&8\u21E8 &7Ride on the wind.",
       "",
       "&c&o&8\u21E8 &e\u26A1 &70 / 100 J"
   );
   static {
     ELECTRIC_WIND_STAFF.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
     ItemMeta meta = ELECTRIC_WIND_STAFF.getItemMeta();
     meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
     ELECTRIC_WIND_STAFF.setItemMeta(meta);
   }

   public static final SlimefunItemStack ELECTRIC_FIRE_STAFF = new SlimefunItemStack(
      "ELECTRIC_FIRE_STAFF",
      Material.BLAZE_ROD,
      "&4Electric Fire Staff",
      "",
      "&8\u21E8 &7Create inferno.",
      "",
      "&c&o&8\u21E8 &e\u26A1 &70 / 100 J"
  );
  static {
    ELECTRIC_FIRE_STAFF.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
    ItemMeta meta = ELECTRIC_FIRE_STAFF.getItemMeta();
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    ELECTRIC_FIRE_STAFF.setItemMeta(meta);
  }

  public static final SlimefunItemStack ELECTRIC_FIRE_STAFF_II = new SlimefunItemStack(
     "ELECTRIC_FIRE_STAFF_II",
     Material.BLAZE_ROD,
     "&4Electric Fire Staff &7- &eII",
     "",
     "&8\u21E8 &7Fire, fire, fire!",
     "",
     "&c&o&8\u21E8 &e\u26A1 &70 / 200 J"
 );
 static {
   ELECTRIC_FIRE_STAFF_II.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
   ItemMeta meta = ELECTRIC_FIRE_STAFF_II.getItemMeta();
   meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
   ELECTRIC_FIRE_STAFF_II.setItemMeta(meta);
 }


};
