package me.gallowsdove.foxymachines.implementation.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.AdvancedMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;


public class PotionMixer extends SlimefunItem implements EnergyNetComponent {
    private static final int[] BORDER = {3, 4, 5, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44};
    private static final int[] BORDER_IN = {0, 1, 2, 6, 7, 8, 9, 11, 12, 14, 15, 17, 18, 19, 20, 24, 25, 26};
    private static final int[] BORDER_OUT = {21, 22, 23, 30, 32, 39, 40, 41};
    public static final int ENERGY_CONSUMPTION = 28;
    public static final int CAPACITY = 128;

    public static Map<Block, MachineRecipe> processing = new HashMap<>();
    public static Map<Block, Integer> progress = new HashMap<>();

    public PotionMixer() {
        super(Items.category, Items.POTION_MIXER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.CARBONADO, SlimefunItems.GOLD_24K, SlimefunItems.CARBONADO,
                SlimefunItems.ELECTRIC_MOTOR, new ItemStack(Material.BREWING_STAND), SlimefunItems.ELECTRIC_MOTOR,
                SlimefunItems.GOLD_24K, SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.GOLD_24K
        });


        new BlockMenuPreset(getId(), "&6Potion Mixer") {

            @Override
            public void init() {
                constructMenu(this);
            }

            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(),
                        ProtectableAction.INTERACT_BLOCK
                );
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(@Nonnull ItemTransportFlow flow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return getOutputSlots();
                }

                List<Integer> slots = new ArrayList<>();
                for (int slot : getInputSlots()) {
                    if (menu.getItemInSlot(slot) != null) {
                        slots.add(slot);
                    }
                }

                slots.sort(compareSlots(menu));

                int[] array = new int[slots.size()];

                for (int i = 0; i < slots.size(); i++) {
                    array[i] = slots.get(i);
                }

                return array;
            }
        };
    }

    private Comparator<Integer> compareSlots(DirtyChestMenu menu) {
        return Comparator.comparingInt(slot -> menu.getItemInSlot(slot).getAmount());
    }

    public int[] getInputSlots() {
        return new int[] { 10, 16 };
    }

    public int[] getOutputSlots() {
        return new int[] {31};
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    public int getEnergyConsumption() {
        return ENERGY_CONSUMPTION;
    }

    @Nonnull
    public ItemStack getProgressBar() {
        return new ItemStack(Material.GOLDEN_HOE);
    }

    @Nullable
    public MachineRecipe getProcessing(@Nonnull Block b) {
        return processing.get(b);
    }

    public boolean isProcessing(@Nonnull Block b) {
        return getProcessing(b) != null;
    }

    @Nonnull
    private BlockBreakHandler onBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                Block b = e.getBlock();
                BlockMenu inv = BlockStorage.getInventory(b);

                if (inv != null) {
                    inv.dropItems(b.getLocation(), getOutputSlots());
                    inv.dropItems(b.getLocation(), getInputSlots());
                }
            }
        };
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public void tick(@Nonnull Block b, SlimefunItem sf, Config data) {
                PotionMixer.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return false;
            }
        }, onBreak());
    }

    protected void tick(@Nonnull Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);

        if (isProcessing(b)) {
            int timeleft = progress.get(b);

            if (timeleft > 0) {
                ChestMenuUtils.updateProgressbar(inv, 13, timeleft, processing.get(b).getTicks(), getProgressBar());

                if (isChargeable()) {
                    if (getCharge(b.getLocation()) < getEnergyConsumption()) {
                        return;
                    }

                    removeCharge(b.getLocation(), getEnergyConsumption());
                }
                progress.put(b, timeleft - 1);
            }
            else {
                inv.replaceExistingItem(13, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "));

                for (ItemStack output : processing.get(b).getOutput()) {
                    inv.pushItem(output.clone(), getOutputSlots());
                }

                progress.remove(b);
                processing.remove(b);
            }
        }
        else {
            MachineRecipe next = findNextRecipe(inv);

            if (next != null) {
                processing.put(b, next);
                progress.put(b, next.getTicks());
            }
        }
    }

    @Nonnull
    protected PotionEffect[] getCustomEffectsFromBaseData(PotionData potionData, boolean lingering) {
        PotionType type = potionData.getType();
        boolean extended = potionData.isExtended();
        boolean upgraded = potionData.isUpgraded();
        int d = 1;
        if (lingering){
            d = 4;
        }
        switch (type) {
            case FIRE_RESISTANCE:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 8*60*20/d, 0)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3*60*20/d, 0)};
                }
            case INSTANT_DAMAGE:
                if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.HARM, 0, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.HARM, 0, 0)};
                }
            case INSTANT_HEAL:
                if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.HEAL, 0, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.HEAL, 0, 0)};
                }
            case INVISIBILITY:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.INVISIBILITY, 8*60*20/d, 0)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.INVISIBILITY, 3*60*20/d, 0)};
                }
            case JUMP:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.JUMP, 8*60*20/d, 0)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.JUMP, 90*20/d, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.JUMP, 3*60*20/d, 0)};
                }
            case LUCK:
                return new PotionEffect[] {new PotionEffect(PotionEffectType.LUCK, 5*60*20/d, 0)};
            case NIGHT_VISION:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.NIGHT_VISION, 8*60*20/d, 0)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.NIGHT_VISION, 3*60*20/d, 0)};
                }
            case POISON:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.POISON, 45*20/d, 0)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.POISON, 21*20/d, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.POISON, 90*20/d, 0)};
                }
            case REGEN:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.REGENERATION, 45*20/d, 0)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.REGENERATION, 22*20/d, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.REGENERATION, 90*20/d, 0)};
                }
            case SLOW_FALLING:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW_FALLING, 4*60*20/d, 0)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW_FALLING, 90*20/d, 0)};
                }
            case SLOWNESS:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 4*60*20/d, 0)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 20*20/d, 3)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 90*20/d, 0)};
                }
            case SPEED:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SPEED, 8*60*20/d, 0)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SPEED, 90*20/d, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SPEED, 3*60*20/d, 0)};
                }
            case STRENGTH:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 8*60*20/d, 0)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 90*20/d, 1)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3*60*20/d, 0)};
                }
            case TURTLE_MASTER:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 40*20/d, 3), new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40*20/d, 2)};
                } else if (upgraded) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 20*20/d, 5), new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*20/d, 3)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 20*20/d, 3), new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*20/d, 2)};
                }
            case WATER_BREATHING:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.WATER_BREATHING, 8*60*20/d, 0)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.WATER_BREATHING, 3*60*20/d, 0)};
                }
            case WEAKNESS:
                if (extended) {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.WEAKNESS, 4*60*20/d, 0)};
                } else {
                    return new PotionEffect[] {new PotionEffect(PotionEffectType.WEAKNESS, 90*20/d, 0)};
                }
        }
        return new PotionEffect[] {};
    }

    @Nullable
    protected MachineRecipe findNextRecipe(@Nonnull BlockMenu menu) {
        int[] slots = getInputSlots();
        ItemStack potion1 = menu.getItemInSlot(slots[0]);
        ItemStack potion2 = menu.getItemInSlot(slots[1]);

        if (potion1 != null && potion2 != null) {

            if ((potion1.getType() == Material.POTION && potion2.getType() == Material.POTION) ||
                    (potion1.getType() == Material.SPLASH_POTION && potion2.getType() == Material.SPLASH_POTION) ||
                    (potion1.getType() == Material.LINGERING_POTION && potion2.getType() == Material.LINGERING_POTION)){

                boolean lingering = false;
                if (potion1.getType() == Material.LINGERING_POTION) {
                    lingering = true;
                }
                ItemStack potion = potion1.clone();
                potion.setAmount(1);

                PotionMeta potionMeta = (PotionMeta) potion1.getItemMeta();
                PotionMeta potion2Meta = (PotionMeta) potion2.getItemMeta();

                assert potionMeta != null : "Potion Meta shouldn't be null. Please report this on github";
                assert potion2Meta != null : "Potion Meta shouldn't be null. Please report this on github";

                List<PotionEffect> potion1Effects = new ArrayList<>(potionMeta.getCustomEffects());
                List<PotionEffect> potion2Effects = potion2Meta.getCustomEffects();


                for (int i = 0; i < potion2Effects.size(); i++) {
                    for (int j = 0; j < potion1Effects.size(); j++) {
                        if (potion1Effects.get(j).getType() == potion2Effects.get(i).getType()) {
                            if (potion1Effects.get(j).getAmplifier() > potion2Effects.get(i).getAmplifier()) {
                                potion2Effects.set(i, potion1Effects.get(j));
                            }
                            potion1Effects.remove(j);
                            break;
                        }
                    }
                    potionMeta.addCustomEffect(potion2Effects.get(i), true);
                }

                for (PotionEffect effect : getCustomEffectsFromBaseData(potionMeta.getBasePotionData(), lingering)) {
                    potionMeta.addCustomEffect(effect, false);
                }

                for (PotionEffect effect : getCustomEffectsFromBaseData(potion2Meta.getBasePotionData(), lingering)) {
                    for (PotionEffect effect2 : potionMeta.getCustomEffects()) {
                        if (effect.getType() == effect2.getType()) {
                            if (effect.getAmplifier() > effect2.getAmplifier()) {
                                potionMeta.addCustomEffect(effect, true);
                                break;
                            }
                        }
                    }
                    potionMeta.addCustomEffect(effect, false);
                }

                List<String> lore = new ArrayList<>() {{
                    add("Not usable in Brewing Stand");
                }};
                potionMeta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE, false, false));
                switch(potion1.getType()){
                    case POTION:
                        potionMeta.setDisplayName(ChatColor.AQUA + "Combined Potion");
                        break;
                    case LINGERING_POTION:
                        lore.add(ChatColor.RED + "The time shown is incorrect due to a Minecraft");
                        lore.add(ChatColor.RED + "bug, multiply it by 4 to get the real time.");
                        potionMeta.setDisplayName(ChatColor.AQUA + "Combined Lingering Potion");
                        break;
                    case SPLASH_POTION:
                        potionMeta.setDisplayName(ChatColor.AQUA + "Combined Splash Potion");
                        break;
                }
                potionMeta.setLore(lore);
                potionMeta.setColor(Color.AQUA);

                potion.setItemMeta(potionMeta);


                if (!menu.fits(potion, getOutputSlots())) {
                    return null;
                }

                for (int inputSlot : getInputSlots()) {
                    menu.consumeItem(inputSlot);
                }

                return new MachineRecipe((1+potionMeta.getCustomEffects().size())*12, new ItemStack[] { potion1, potion2 }, new ItemStack[] { potion });
            }
        }

        return null;
    }

    protected void constructMenu(@Nonnull BlockMenuPreset preset) {
        for (int i : BORDER) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : BORDER_IN) {
            preset.addItem(i, new SlimefunItemStack("_UI_INPUT_SLOT", Material.CYAN_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : BORDER_OUT) {
            preset.addItem(i, new SlimefunItemStack("_UI_OUTPUT_SLOT", Material.ORANGE_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(13, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());

        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new AdvancedMenuClickHandler() {

                @Override
                public boolean onClick(@Nonnull Player p, int slot, @Nonnull ItemStack cursor, @Nonnull ClickAction action) {
                    return false;
                }

                @Override
                public boolean onClick(@Nonnull InventoryClickEvent e, @Nonnull Player p, int slot, @Nullable ItemStack cursor, @Nonnull ClickAction action) {
                    return cursor == null || cursor.getType() == Material.AIR;
                }
            });
        }
    }
}
