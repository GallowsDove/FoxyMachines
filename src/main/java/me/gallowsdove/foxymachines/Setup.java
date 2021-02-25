package me.gallowsdove.foxymachines;

import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.implementation.machines.*;
import me.gallowsdove.foxymachines.implementation.materials.ImprovementCore;
import me.gallowsdove.foxymachines.implementation.materials.ReinforcedString;
import me.gallowsdove.foxymachines.implementation.materials.StabilizedBlisteringBlock;
import me.gallowsdove.foxymachines.implementation.tools.ElectricFireStaff;
import me.gallowsdove.foxymachines.implementation.tools.ElectricFireStaffII;
import me.gallowsdove.foxymachines.implementation.tools.ElectricWindStaff;
import me.gallowsdove.foxymachines.implementation.weapons.HealingBow;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;


final class ItemSetup {
    static final ItemSetup INSTANCE = new ItemSetup();
    private boolean initialised;

    private ItemSetup() {}

    public void init() {
        if (initialised) return;

        initialised = true;

        new ElectricWindStaff().register(FoxyMachines.getInstance());
        new ElectricFireStaff().register(FoxyMachines.getInstance());
        new ElectricFireStaffII().register(FoxyMachines.getInstance());
        new HealingBow().register(FoxyMachines.getInstance());
        new ReinforcedString().register(FoxyMachines.getInstance());
        new ImprovementCore().register(FoxyMachines.getInstance());
        new StabilizedBlisteringBlock().register(FoxyMachines.getInstance());
        new ImprovementForge().register(FoxyMachines.getInstance());
        new PotionMixer().register(FoxyMachines.getInstance());
        new ElectricGoldRefinery().register(FoxyMachines.getInstance());
        new ChunkLoader().register(FoxyMachines.getInstance());
        new BoostedRail(Items.BOOSTED_RAIL, new ItemStack[] {
                SlimefunItems.LEAD_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.LEAD_INGOT,
                SlimefunItems.LEAD_INGOT, new ItemStack(Material.STICK), SlimefunItems.LEAD_INGOT,
                SlimefunItems.LEAD_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.LEAD_INGOT}, 4)
        .register(FoxyMachines.getInstance());
        new BoostedRail(Items.BOOSTED_ACTIVATOR_RAIL, new ItemStack[] {
                SlimefunItems.BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRASS_INGOT, new ItemStack(Material.REPEATER), SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.BRASS_INGOT}, 4)
        .register(FoxyMachines.getInstance());
        new BoostedRail(Items.BOOSTED_DETECTOR_RAIL, new ItemStack[] {
                SlimefunItems.BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRASS_INGOT, new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE), SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.BRASS_INGOT}, 4)
                .register(FoxyMachines.getInstance());
        new BoostedRail(Items.BOOSTED_POWERED_RAIL, new ItemStack[] {
                SlimefunItems.GOLD_24K, SlimefunItems.COPPER_WIRE, SlimefunItems.GOLD_24K,
                SlimefunItems.GOLD_24K, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.GOLD_24K,
                SlimefunItems.GOLD_24K, SlimefunItems.COPPER_WIRE, SlimefunItems.GOLD_24K}, 4)
                .register(FoxyMachines.getInstance());
    }
}

final class ResearchSetup {
    static final ResearchSetup INSTANCE = new ResearchSetup();
    private boolean initialised;

    private ResearchSetup() {}

    public void init() {
        if (initialised) return;

        initialised = true;

        new Research(new NamespacedKey(FoxyMachines.getInstance(), "electric_wind_staff"),
                6669666, "On the wind with the power of electricity", 22)
                .addItems(Items.ELECTRIC_WIND_STAFF)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "electric_fire_staffs"),
                6669667, "Create inferno", 34)
                .addItems(Items.ELECTRIC_FIRE_STAFF, Items.ELECTRIC_FIRE_STAFF_II)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "healing_bow"),
                6669668, "Support", 30)
                .addItems(Items.HEALING_BOW)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "reinforced_string"),
                6669669, "Harder, Better, Stronger", 18)
                .addItems(Items.REINFORCED_STRING)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "improvement_forge"),
                6669670, "Beyond imaginations", 48)
                .addItems(Items.IMPROVEMENT_FORGE)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "improvement_core"),
                6669671, "Up 1", 28)
                .addItems(Items.IMPROVEMENT_CORE)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "potion_mixer"),
                6669672, "Brewing like never before", 28)
                .addItems(Items.POTION_MIXER)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "electric_gold_refinery"),
                6669673, "Get rid of the smelteries", 28)
                .addItems(Items.ELECTRIC_GOLD_REFINERY)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "chunk_loader"),
                6669674, "Quite a useful utility", 52)
                .addItems(Items.CHUNK_LOADER)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "stabilized_blistering_block"),
                6669675, "What a material", 26)
                .addItems(Items.STABILIZED_BLISTERING_BLOCK)
                .register();
        new Research(new NamespacedKey(FoxyMachines.getInstance(), "boosted_rail"),
                6669676, "Faster, Better, Stronger", 16)
                .addItems(Items.BOOSTED_RAIL, Items.BOOSTED_ACTIVATOR_RAIL, Items.BOOSTED_DETECTOR_RAIL, Items.BOOSTED_POWERED_RAIL)
                .register();
    }
}
