package me.gallowsdove.foxymachines;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;
import me.gallowsdove.foxymachines.implementation.machines.ImprovementForge;
import me.gallowsdove.foxymachines.implementation.machines.PotionMixer;
import me.gallowsdove.foxymachines.implementation.machines.ElectricGoldRefinery;


public final class Items{

  public static Category tools = new Category(
    new NamespacedKey(FoxyMachines.getInstance(), "foxy_tools"),
    new CustomItem(Material.SHEARS, "&4Foxy Tools", "", "&a> Click to open"));
  public static Category weapons = new Category(
    new NamespacedKey(FoxyMachines.getInstance(), "foxy_weapons"),
    new CustomItem(Material.BOW, "&4Foxy Weapons", "", "&a> Click to open"));
  public static Category materials = new Category(
    new NamespacedKey(FoxyMachines.getInstance(), "foxy_materials"),
    new CustomItem(Material.STRING, "&4Foxy Materials", "", "&a> Click to open"));
  public static Category machines = new Category(
    new NamespacedKey(FoxyMachines.getInstance(), "foxy_machines"),
    new CustomItem(Material.SMITHING_TABLE, "&4Foxy Machines", "", "&a> Click to open"));

  public static final SlimefunItemStack ELECTRIC_WIND_STAFF = new SlimefunItemStack(
    "ELECTRIC_WIND_STAFF",
    Material.BLAZE_ROD,
    "&bElectric Wind Staff",
    "",
    "&7Ride on the wind.",
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
    "&7Create inferno.",
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
    "&7Fire, fire, fire!",
    "",
    "&c&o&8\u21E8 &e\u26A1 &70 / 200 J"
    );
  static {
    ELECTRIC_FIRE_STAFF_II.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
    ItemMeta meta = ELECTRIC_FIRE_STAFF_II.getItemMeta();
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    ELECTRIC_FIRE_STAFF_II.setItemMeta(meta);
  }

  public static final SlimefunItemStack HEALING_BOW = new SlimefunItemStack(
    "HEALING_BOW",
    Material.BOW,
    "&4Healing Bow",
    "Healing II",
    "",
    "&8Finally a support weapon."
  );

  public static final SlimefunItemStack REINFORCED_STRING = new SlimefunItemStack(
   "REINFORCED_STRING",
   Material.STRING,
   "&bReinforced String"
   );

  public static final SlimefunItemStack IMPROVEMENT_FORGE = new SlimefunItemStack(
    "IMPROVEMENT_FORGE",
    Material.SMITHING_TABLE,
    "&bImprovement Forge",
    "",
    "Used to improve Slimefun tools, weapons and armor.",
    "",
    LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
    LoreBuilder.powerPerSecond(ImprovementForge.ENERGY_CONSUMPTION)
  );

  public static final SlimefunItemStack IMPROVEMENT_CORE = new SlimefunItemStack(
    "IMPROVEMENT_CORE",
    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFmZjJlYjQ5OGU1YzZhMDQ0ODRmMGM5Zjc4NWI0NDg0NzlhYjIxM2RmOTVlYzkxMTc2YTMwOGExMmFkZDcwIn19fQ==",
    "&aImprovement Core",
    "",
    "Combine it with a tool in Improvement Forge to improve it."
  );

  public static final SlimefunItemStack POTION_MIXER = new SlimefunItemStack(
    "POTION_MIXER",
    Material.BREWING_STAND	,
    "&bPotion Mixer",
    "",
    "Used to mix potions.",
    "",
    LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
    LoreBuilder.powerPerSecond(PotionMixer.ENERGY_CONSUMPTION)
  );

  public static final SlimefunItemStack ELECTRIC_GOLD_REFINERY = new SlimefunItemStack(
    "ELECTRIC_GOLD_REFINERY",
    Material.GOLD_BLOCK	,
    "&bElectric Gold Refinery",
    "",
    "&7Refines gold dust to gold ingots.",
    "",
    LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
    LoreBuilder.powerPerSecond(ElectricGoldRefinery.ENERGY_CONSUMPTION)
  );

};
