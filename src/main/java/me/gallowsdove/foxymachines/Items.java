package me.gallowsdove.foxymachines;

import io.github.mooy1.infinitylib.machines.MachineLore;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ColoredFireworkStar;
import me.gallowsdove.foxymachines.implementation.machines.ElectricGoldRefinery;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.implementation.machines.ImprovementForge;
import me.gallowsdove.foxymachines.implementation.machines.PotionMixer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items{

    // Item groups
    public static NestedItemGroup MAIN_ITEM_GROUP = new NestedItemGroup(
            new NamespacedKey(FoxyMachines.getInstance(), "foxy_machines"),
            new CustomItemStack(Material.SHEARS, "&4Foxy Machines")
    );

    public static SubItemGroup MATERIALS_ITEM_GROUP = new SubItemGroup(
            new NamespacedKey(FoxyMachines.getInstance(), "materials"),
            MAIN_ITEM_GROUP,
            new CustomItemStack(Material.GOLD_INGOT, "&bMaterials")
    );

    public static SubItemGroup MACHINES_ITEM_GROUP = new SubItemGroup(
            new NamespacedKey(FoxyMachines.getInstance(), "machines"),
            MAIN_ITEM_GROUP,
            new CustomItemStack(Material.BEACON, "&aMachines")
    );

    public static SubItemGroup TOOLS_ITEM_GROUP = new SubItemGroup(
            new NamespacedKey(FoxyMachines.getInstance(), "tools"),
            MAIN_ITEM_GROUP,
            new CustomItemStack(Material.BLAZE_ROD, "&eTools")
    );

    public static SubItemGroup WEAPONS_AND_ARMORS_ITEM_GROUP = new SubItemGroup(
            new NamespacedKey(FoxyMachines.getInstance(), "weapons_and_armors"),
            MAIN_ITEM_GROUP,
            new CustomItemStack(Material.NETHERITE_SWORD, "&aWeapons and Armors")
    );

    public static SubItemGroup ALTAR_ITEM_GROUP = new SubItemGroup(
        new NamespacedKey(FoxyMachines.getInstance(), "sacrificial_altars"),
        MAIN_ITEM_GROUP,
        new CustomItemStack(Material.POLISHED_BLACKSTONE_BRICKS, "&4Sacrificial Altar")
    );

    public static SubItemGroup BOSSES_ITEM_GROUP = new SubItemGroup(
        new NamespacedKey(FoxyMachines.getInstance(), "bosses"),
        MAIN_ITEM_GROUP,
        new CustomItemStack(Material.DRAGON_HEAD, "&cBosses")
    );

    public static SubItemGroup GHOST_BLOCKS_ITEM_GROUP = new SubItemGroup(
            new NamespacedKey(FoxyMachines.getInstance(), "ghost_blocks"),
            MAIN_ITEM_GROUP,
            new CustomItemStack(Material.GLASS, "&5Ghost Blocks")
    );

    // Items
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
            "&cHealing II",
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
            "&7Used to improve Slimefun tools, weapons and armor.",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.powerPerSecond(ImprovementForge.ENERGY_CONSUMPTION)
    );

    public static final SlimefunItemStack IMPROVEMENT_CORE = new SlimefunItemStack(
            "IMPROVEMENT_CORE",
            "faff2eb498e5c6a04484f0c9f785b448479ab213df95ec91176a308a12add70",
            "&aImprovement Core",
            "",
            "&7Combine it with a tool in Improvement Forge to improve it."
    );

    public static final SlimefunItemStack POTION_MIXER = new SlimefunItemStack(
            "POTION_MIXER",
            Material.BREWING_STAND	,
            "&bPotion Mixer",
            "",
            "&7Used to mix potions.",
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

    public static final SlimefunItemStack CHUNK_LOADER = new SlimefunItemStack(
            "CHUNK_LOADER",
            Material.BEACON,
            "&bChunk Loader",
            "",
            "&7Keeps chunk loaded."
    );

    public static final SlimefunItemStack STABILIZED_BLISTERING_BLOCK = new SlimefunItemStack(
            "STABILIZED_BLISTERING_BLOCK",
            Material.SNOW_BLOCK,
            "&bStabilized Blistering Block",
            "",
            "&7A stable material."
    );

    public static final SlimefunItemStack BOOSTED_RAIL = new SlimefunItemStack(
            "BOOSTED_RAIL",
            Material.RAIL,
            "&fBoosted Rail",
            "",
            "&7Supports 2.5x the speed."
    );

    public static final SlimefunItemStack BOOSTED_ACTIVATOR_RAIL = new SlimefunItemStack(
            "BOOSTED_ACTIVATOR_RAIL",
            Material.ACTIVATOR_RAIL,
            "&fBoosted Activator Rail",
            "",
            "&7Supports 2.5x the speed."
    );

    public static final SlimefunItemStack BOOSTED_DETECTOR_RAIL = new SlimefunItemStack(
            "BOOSTED_DETECTOR_RAIL",
            Material.DETECTOR_RAIL,
            "&fBoosted Detector Rail",
            "",
            "&7Supports 2.5x the speed."
    );

    public static final SlimefunItemStack BOOSTED_POWERED_RAIL = new SlimefunItemStack(
            "BOOSTED_POWERED_RAIL",
            Material.POWERED_RAIL,
            "&fBoosted Powered Rail",
            "",
            "&7Supports 2.5x the speed."
    );

    public static final SlimefunItemStack BERRY_BUSH_TRIMMER = new SlimefunItemStack(
            "BERRY_BUSH_TRIMMER",
            Material.SHEARS,
            "&eSweet Berry Bush Trimmer",
            "",
            "&7Removes thorns from sweet berry bushes."
    );

    public static final SlimefunItemStack FORCEFIELD_DOME = new SlimefunItemStack(
            "FORCEFIELD_DOME",
            Material.OBSERVER,
            "&4Forcefield Dome",
            "",
            "&7When powered, creates a protective barrier",
            "&7with a 32-block radius.",
            "&7Will revert once unpowered or broken",
            "",
            MachineLore.energyPerSecond(ForcefieldDome.ENERGY_CONSUMPTION)
    );

    public static final SlimefunItemStack REMOTE_CONTROLLER = new SlimefunItemStack(
            "REMOTE_CONTROLLER",
            Material.NAME_TAG,
            "&cRemote Controller",
            "",
            "&7Allows you to control your Forcefield Dome from distance-",
            "&7Bind it using Shift + Right Click.",
            "",
            "&c&o&8\u21E8 &e\u26A1 &70 / 1000 J"
    );

    public static final SlimefunItemStack FORCEFIELD_ENGINE = new SlimefunItemStack(
            "FORCEFIELD_ENGINE",
            Material.STRUCTURE_BLOCK,
            "&fForcefield Engine"
    );

    public static final SlimefunItemStack FORCEFIELD_STABILIZER = new SlimefunItemStack(
            "FORCEFIELD_STABILIZER",
            Material.STRUCTURE_VOID,
            "&fForcefield Stabilizer"
    );

    public static final SlimefunItemStack WIRELESS_TRANSMITTER = new SlimefunItemStack(
            "WIRELESS_TRANSMITTER",
            Material.REPEATER,
            "&fWireless Transmitter"
    );

    public static final SlimefunItemStack DEMONIC_INGOT = new SlimefunItemStack(
            "DEMONIC_INGOT",
            Material.GOLD_INGOT,
            "&cDemonic Ingot"
    );

    public static final SlimefunItemStack DEMONIC_PLATE = new SlimefunItemStack(
            "DEMONIC_PLATE",
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            "&cDemonic Plate"
    );

    public static final SlimefunItemStack AQUATIC_NETHERITE_INGOT = new SlimefunItemStack(
            "AQUATIC_NETHERITE_INGOT",
            Material.NETHERITE_INGOT,
            "&bAquatic Netherite Ingot"
    );

    public static final SlimefunItemStack DAMIENIUM = new SlimefunItemStack(
            "DAMIENIUM",
            Material.GOLD_INGOT,
            "&aDamienium"
    );

    public static final SlimefunItemStack SWEET_INGOT = new SlimefunItemStack(
            "SWEET_INGOT",
            Material.GOLD_INGOT,
            "&eSweet Ingot"
    );

    public static final SlimefunItemStack SWEETENED_SWEET_INGOT = new SlimefunItemStack(
            "SWEETENED_SWEET_INGOT",
            Material.GOLD_INGOT,
            "&eSweetened Sweet Ingot"
    );

    public static final SlimefunItemStack SACRIFICIAL_ALTAR_BLACKSTONE_BRICKS = new SlimefunItemStack(
            "SACRIFICIAL_ALTAR_BLACKSTONE_BRICKS",
            Material.POLISHED_BLACKSTONE_BRICKS,
            "&fSacrificial Blackstone Bricks",
            "",
            "&7Used as a part of Sacrificial Altar."
    );

    public static final SlimefunItemStack SACRIFICIAL_ALTAR_BLACKSTONE_BRICK_WALL = new SlimefunItemStack(
            "SACRIFICIAL_ALTAR_BLACKSTONE_BRICK_WALL",
            Material.POLISHED_BLACKSTONE_BRICK_WALL,
            "&fSacrificial Blackstone Brick Wall",
            "",
            "&7Used as a part of Sacrificial Altar."
    );

    public static final SlimefunItemStack SACRIFICIAL_ALTAR_BLACKSTONE_BRICK_STAIRS = new SlimefunItemStack(
            "SACRIFICIAL_ALTAR_BLACKSTONE_BRICK_STAIRS",
            Material.POLISHED_BLACKSTONE_BRICK_STAIRS,
            "&fSacrificial Blackstone Brick Stairs",
            "",
            "&7Used as a part of Sacrificial Altar."
    );

    public static final SlimefunItemStack SACRIFICIAL_ALTAR_SOUL_TORCH = new SlimefunItemStack(
            "SACRIFICIAL_ALTAR_SOUL_TORCH",
            Material.SOUL_TORCH,
            "&fSacrificial Soul Torch",
            "",
            "&7Used as a part of Sacrificial Altar."
    );

    public static final SlimefunItemStack SACRIFICIAL_ALTAR_BLACKSTONE_PRESSURE_PLATE = new SlimefunItemStack(
            "SACRIFICIAL_ALTAR_BLACKSTONE_PRESSURE_PLATE",
            Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
            "&fSacrificial Mat",
            "",
            "&7Used as a part of Sacrificial Altar."
    );

    public static final SlimefunItemStack CURSED_RABBIT_PAW = new SlimefunItemStack(
            "CURSED_RABBIT_PAW",
            Material.RABBIT_FOOT,
            "&cCursed Rabbit Paw"
    );

    public static final SlimefunItemStack HUMAN_SKULL = new SlimefunItemStack(
            "HUMAN_SKULL",
            Material.SKELETON_SKULL,
            "&cHuman Skull"
    );

    public static final SlimefunItemStack BLOOD_INFUSED_SKULL = new SlimefunItemStack(
            "BLOOD_INFUSED_SKULL",
            "daa4e2294df370b9a50cb924cdda78f740b0fbaf5a687106178505c80a79addc",
            "&cBlood Infused Skull"
    );

    public static final SlimefunItemStack BLOOD = new SlimefunItemStack(
            "BLOOD",
            Material.REDSTONE,
            "&cBlood"
    );

    public static final SlimefunItemStack UNHOLY_WITHER_SKELETON_BONE = new SlimefunItemStack(
            "UNHOLY_WITHER_SKELETON_BONE",
            Material.BONE,
            "&cUnholy Wither Skeleton Bone"
    );

    public static final SlimefunItemStack PURIFIED_BONE = new SlimefunItemStack(
            "PURIFIED_BONE",
            Material.BONE,
            "&bPurified Bone"
    );

    public static final SlimefunItemStack PURE_BONE_DUST = new SlimefunItemStack(
            "PURE_BONE_DUST",
            Material.BONE_MEAL,
            "&bPure Bone Dust"
    );

    public static final SlimefunItemStack BUCKET_OF_BLOOD = new SlimefunItemStack(
            "BUCKET_OF_BLOOD",
            Material.LAVA_BUCKET,
            "&cBucket of Blood"
    );

    public static final SlimefunItemStack POSEIDONS_FISHING_ROD = new SlimefunItemStack(
            "POSEIDONS_FISHING_ROD",
            Material.FISHING_ROD,
            "&bPoseidon's Fishing Rod",
            "&7Poseidon's Blessing",
            "",
            "&7Can catch special items."
    );
    static {
        POSEIDONS_FISHING_ROD.addUnsafeEnchantment(Enchantment.LUCK, 5);
        POSEIDONS_FISHING_ROD.addUnsafeEnchantment(Enchantment.LURE, 3);
    }

    public static final SlimefunItemStack POSEIDONS_BLESSING = new SlimefunItemStack(
            "POSEIDONS_BLESSING",
            Material.HEART_OF_THE_SEA,
            "&bPoseidon's Blessing"
    );

    public static final SlimefunItemStack CURSED_SWORD = new SlimefunItemStack(
            "CURSED_SWORD",
            Material.NETHERITE_SWORD,
            "&cCursed Sword",
            "&7Life Steal I",
            "",
            "&7Confuses enemies. Increases damage.",
            "&7Can negatively affect wielder."
    );

    public static final SlimefunItemStack CELESTIAL_SWORD = new SlimefunItemStack(
            "CELESTIAL_SWORD",
            Material.NETHERITE_SWORD,
            "&eCelestial Sword",
            "&7Divine Smite II",
            "",
            "&7Ignores 20% of Resistances."
    );

    public static final SlimefunItemStack ELUCIDATOR = new SlimefunItemStack(
            "ELUCIDATOR",
            Material.NETHERITE_SWORD,
            "&bElucidator",
            "&7Damage III",
            "&7Life Steal II",
            "&7Overheal"
    );
    static {
        ELUCIDATOR.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        ELUCIDATOR.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5);
    }

    public static final SlimefunItemStack MAGIC_LUMP_4 = new SlimefunItemStack(
            "MAGIC_LUMP_4",
            Material.GOLD_NUGGET,
            "&6Magical Lump &7- &eIV",
            "",
            "&c&oTier: IV");

    public static final SlimefunItemStack MAGIC_LUMP_5 = new SlimefunItemStack(
            "MAGIC_LUMP_5",
            Material.GOLD_NUGGET,
            "&6Magical Lump &7- &eV",
            "",
            "&c&oTier: V");

    public static final SlimefunItemStack AQUATIC_HELMET = new SlimefunItemStack(
            "AQUATIC_HELMET",
            Material.NETHERITE_HELMET,
            "&bAquatic Helmet",
            "&7Water Breathing",
            "&7Darkvision"
    );
    static {
        AQUATIC_HELMET.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        AQUATIC_HELMET.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
        AQUATIC_HELMET.addUnsafeEnchantment(Enchantment.OXYGEN, 5);
        AQUATIC_HELMET.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 5);
    }


    public static final SlimefunItemStack RESISTANT_CHESTPLATE = new SlimefunItemStack(
            "RESISTANT_CHESTPLATE",
            Material.NETHERITE_CHESTPLATE,
            "&aResistant Chestplate",
            "&7Resistance I",
            "&7Regeneration I"
    );
    static {
        RESISTANT_CHESTPLATE.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
    }

    public static final SlimefunItemStack FIERY_LEGGINGS = new SlimefunItemStack(
            "FIERY_LEGGINGS",
            Material.NETHERITE_LEGGINGS,
            "&cFiery Leggings",
            "&7Fire Aura II"
    );
    static {
        FIERY_LEGGINGS.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        FIERY_LEGGINGS.addUnsafeEnchantment(Enchantment.THORNS, 6);
    }

    public static final SlimefunItemStack LIGHT_BOOTS = new SlimefunItemStack(
            "LIGHT_BOOTS",
            Material.NETHERITE_BOOTS,
            "&eLight Boots",
            "&7Jump I",
            "&7Speed I",
            "&7Lightweight"
    );
    static {
        LIGHT_BOOTS.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        LIGHT_BOOTS.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);
        LIGHT_BOOTS.addUnsafeEnchantment(Enchantment.SOUL_SPEED, 5);
    }

    public static final SlimefunItemStack AQUATIC_HELMET_FRAME = new SlimefunItemStack(
            "HELMET_FRAME",
            Material.CHAINMAIL_HELMET,
            "&fAquatic Helmet Frame",
            "",
            "&7Crafting Material"
    );

    public static final SlimefunItemStack RESISTANT_CHESTPLATE_FRAME = new SlimefunItemStack(
            "RESISTANT_CHESTPLATE_FRAME",
            Material.CHAINMAIL_CHESTPLATE,
            "&fResistant Chestplate Frame",
            "",
            "&7Crafting Material"
    );

    public static final SlimefunItemStack FIERY_LEGGINGS_FRAME = new SlimefunItemStack(
            "FIERY_LEGGINGS_FRAME",
            Material.CHAINMAIL_LEGGINGS,
            "&fFiery Leggings Frame",
            "",
            "&7Crafting Material"
    );

    public static final SlimefunItemStack LIGHT_BOOTS_FRAME = new SlimefunItemStack(
            "LIGHT_BOOTS_FRAME",
            Material.CHAINMAIL_BOOTS,
            "&fLight Boots Frame",
            "",
            "&7Crafting Material"
    );

    public static final SlimefunItemStack CURSED_SHARD = new SlimefunItemStack(
            "CURSED_SHARD",
            Material.NETHERITE_SCRAP,
            "&cCursed Shard"
    );

    public static final SlimefunItemStack CELESTIAL_SHARD = new SlimefunItemStack(
            "CELESTIAL_SHARD",
            Material.PRISMARINE_SHARD,
            "&eCelestial Shard"
    );

    public static final SlimefunItemStack EQUANIMOUS_GEM = new SlimefunItemStack(
            "EQUANIMOUS_GEM",
            Material.EMERALD,
            "&aEquanimous Gem"
    );

    public static final SlimefunItemStack POLAR_FOX_HIDE = new SlimefunItemStack(
            "POLAR_FOX_HIDE",
            Material.SNOWBALL,
            "&fPolar Fox Hide"
    );

    public static final SlimefunItemStack MAGMA_ESSENCE = new SlimefunItemStack(
            "MAGMA_ESSENCE",
            Material.MAGMA_CREAM,
            "&cMagma Essence"
    );

    public static final SlimefunItemStack TROPICAL_FISH_SCALE = new SlimefunItemStack(
            "TROPICAL_FISH_SCALE",
            Material.TROPICAL_FISH_SPAWN_EGG,
            "&bTropical Fish Scale"
    );

    public static final SlimefunItemStack PARROT_FEATHER = new SlimefunItemStack(
            "PARROT_FEATHER",
            Material.FEATHER,
            "&aParrot Feather"
    );

    public static final SlimefunItemStack UNBREAKABLE_RUNE = new SlimefunItemStack(
            "UNBREAKABLE_RUNE",
            new ColoredFireworkStar(
                    Color.fromRGB(0, 188, 0),
                    "&7Ancient Rune &8&l[&2&lUnbreakable&8&l]",
                    "",
                    "&eDrop this rune onto a dropped item to",
                    "&emake it &2unbreakable"
            ));

    public static final SlimefunItemStack PIXIE_QUEEN_SPAWN_EGG = new SlimefunItemStack(
            "PIXIE_QUEEN_SPAWN_EGG",
            Material.CREEPER_SPAWN_EGG,
            "&aPixie Queen Spawn Egg"
    );

    public static final SlimefunItemStack HEADLESS_HORSEMAN_SPAWN_EGG = new SlimefunItemStack(
            "HEADLESS_HORSEMAN_SPAWN_EGG",
            Material.SPIDER_SPAWN_EGG,
            "&cHeadless Horseman Spawn Egg"
    );

    public static final SlimefunItemStack PIXIE_QUEEN_HEART = new SlimefunItemStack(
            "PIXIE_QUEEN_HEART",
            Material.FERMENTED_SPIDER_EYE,
            "&4Pixie Queen Heart"
    );

    public static final SlimefunItemStack PIXIE_DUST = new SlimefunItemStack(
            "PIXIE_DUST",
            Material.SUGAR,
            "&ePixie Dust",
            "",
            "&7Strength IV"
    );

    public static final SlimefunItemStack VILE_PUMPKIN = new SlimefunItemStack(
            "VILE_PUMPKIN",
            Material.CARVED_PUMPKIN,
            "&cVile Pumpkin"
    );

    public static final SlimefunItemStack VILE_SEEDS = new SlimefunItemStack(
            "VILE_SEEDS",
            Material.MELON_SEEDS,
            "&cVile Seeds",
            "",
            "&7Health Boost V"
    );

    public static final SlimefunItemStack ACRI_ARCUM = new SlimefunItemStack(
            "ACRI_ARCUM",
            Material.BOW,
            "&eAcri Arcum",
            "",
            "&7Damage III",
            "&7Armor Piercing II"
    );
    static {
        ACRI_ARCUM.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 7);
        ACRI_ARCUM.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
    }

    public static final SlimefunItemStack GHOST_BLOCK_REMOVER = new SlimefunItemStack(
            "GHOST_BLOCK_REMOVER",
            Material.CLOCK,
            "&eGhost Block Remover",
            "",
            "&7Right click to remove a Ghost Block."
    );

    public static final SlimefunItemStack POSITION_SELECTOR = new SlimefunItemStack(
            "POSITION_SELECTOR",
            Material.STICK,
            "&ePosition Selector",
            "",
            "&7Left click a block to select primary position.",
            "&7Right click a block to select secondary position.",
            "",
            "&c&o&8\u21E8 &e\u26A1 &70 / 200 J"
    );

    public static final SlimefunItemStack FILL_WAND = new SlimefunItemStack(
            "FILL_WAND",
            Material.BLAZE_ROD,
            "&eFill Wand",
            "",
            "&7Select corner points with Position Selector.",
            "&7Shift right click to select material.",
            "&7Right click to fill an area.",
            "",
            "&7Material: &eNone",
            "&c&o&8\u21E8 &e\u26A1 &70 / 1000 J"
    );

    public static final SlimefunItemStack SPONGE_WAND = new SlimefunItemStack(
            "SPONGE_WAND",
            Material.BLAZE_ROD,
            "&eSponge Wand",
            "",
            "&7Select corner points with Position Selector.",
            "&7Right click to remove Water and Lava.",
            "",
            "&c&o&8\u21E8 &e\u26A1 &70 / 2000 J"
    );

    public static final SlimefunItemStack NUCLEAR_SALT = new SlimefunItemStack(
            "NUCLEAR_SALT",
            Material.LIME_DYE,
            "&aNuclear Salt",
            "",
            LoreBuilder.radioactive(Radioactivity.VERY_HIGH)
    );

    public static final SlimefunItemStack COMPRESSED_SPONGE = new SlimefunItemStack(
            "COMPRESSED_SPONGE",
            Material.WET_SPONGE,
            "&fCompressed Sponge"
    );
}
