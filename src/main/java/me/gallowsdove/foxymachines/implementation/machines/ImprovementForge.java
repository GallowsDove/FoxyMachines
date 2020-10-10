package me.gallowsdove.foxymachines.implementation.machines;

import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.AdvancedMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;



public class ImprovementForge extends SlimefunItem implements InventoryBlock, EnergyNetComponent {
  private static final int[] BORDER = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44 };
  private static final int[] BORDER_IN = { 9, 10, 11, 12, 18, 21, 27, 28, 29, 30 };
  private static final int[] BORDER_OUT = { 14, 15, 16, 17, 23, 26, 32, 33, 34, 35 };
  public static final int ENERGY_CONSUMPTION = 128;
  public static final int CAPACITY = 512;

  public static Map<Block, MachineRecipe> processing = new HashMap<>();
  public static Map<Block, Integer> progress = new HashMap<>();

  protected final List<MachineRecipe> recipes = new ArrayList<>();

  public static final Material tools[][] = {
    {Material.WOODEN_SWORD, Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE, Material.WOODEN_AXE, Material.WOODEN_HOE, Material.WOODEN_SHOVEL, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET},
    {Material.STONE_SWORD, Material.STONE_SHOVEL, Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_SHOVEL, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET},
    {Material.IRON_SWORD, Material.IRON_SHOVEL, Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_SHOVEL, Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_HELMET},
    {Material.GOLDEN_SWORD, Material.GOLDEN_SHOVEL, Material.GOLDEN_PICKAXE, Material.GOLDEN_AXE, Material.GOLDEN_HOE, Material.GOLDEN_SHOVEL, Material.GOLDEN_BOOTS, Material.GOLDEN_LEGGINGS, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_HELMET},
    {Material.DIAMOND_SWORD, Material.DIAMOND_SHOVEL, Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_SHOVEL, Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET},
    {Material.NETHERITE_SWORD, Material.NETHERITE_SHOVEL, Material.NETHERITE_PICKAXE, Material.NETHERITE_AXE, Material.NETHERITE_HOE, Material.NETHERITE_SHOVEL, Material.NETHERITE_BOOTS, Material.NETHERITE_LEGGINGS, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_HELMET}
  };

  public ImprovementForge() {
    super(Items.machines, Items.IMPROVEMENT_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      SlimefunItems.CARBONADO, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.CARBONADO,
      SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.AUTO_ENCHANTER, SlimefunItems.ELECTRIC_MOTOR,
      SlimefunItems.REINFORCED_PLATE, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.REINFORCED_PLATE
    });


    new BlockMenuPreset(getID(), "&6Improvement Forge") {

      @Override
      public void init() {
        constructMenu(this);
      }

      public boolean canOpen(Block b, Player p) {
        return p.hasPermission("slimefun.inventory.bypass")
          || SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(),
          ProtectableAction.ACCESS_INVENTORIES
        );
      }

      @Override
      public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return new int[0];
      }

      @Override
      public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
          return getOutputSlots();
        }

        List<Integer> slots = new ArrayList<>();
        for (int slot : getInputSlots()) {
          if (menu.getItemInSlot(slot) != null) {
            slots.add(slot);
          }
        }

        Collections.sort(slots, compareSlots(menu));

        int[] array = new int[slots.size()];

        for (int i = 0; i < slots.size(); i++) {
          array[i] = slots.get(i);
        }

        return array;
      }
    };
    
    registerBlockHandler(getID(), (p, b, stack, reason) -> {
      BlockMenu inv = BlockStorage.getInventory(b);
      
      if (inv != null) {
        inv.dropItems(b.getLocation(), getOutputSlots());
        inv.dropItems(b.getLocation(), getInputSlots());
      }

      return true;
    });
  }

  @Override
  public int[] getInputSlots() {
    return new int[] { 19, 20 };
  }

  @Override
  public int[] getOutputSlots() {
    return new int[] {24, 25};
  }

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

  public String getMachineIdentifier() {
    return "IMPROVEMENT_FORGE";
  }

  public List<MachineRecipe> getMachineRecipes() {
    return recipes;
  }

  public List<ItemStack> getDisplayRecipes() {
    List<ItemStack> displayRecipes = new ArrayList<>(recipes.size() * 2);

    for (MachineRecipe recipe : recipes) {
      if (recipe.getInput().length != 1) continue;

      displayRecipes.add(recipe.getInput()[0]);
      displayRecipes.add(recipe.getOutput()[0]);
    }

    return displayRecipes;
  }

  public ItemStack getProgressBar() {
    return new ItemStack(Material.GOLDEN_CHESTPLATE);
  }

  public MachineRecipe getProcessing(Block b) {
    return processing.get(b);
  }

  public boolean isProcessing(Block b) {
    return getProcessing(b) != null;
  }

  public void registerRecipe(MachineRecipe recipe) {
    recipe.setTicks(recipe.getTicks());
    recipes.add(recipe);
  }

  public void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
    registerRecipe(new MachineRecipe(seconds, input, output));
  }

  public void registerRecipe(int seconds, ItemStack input, ItemStack output) {
    registerRecipe(new MachineRecipe(seconds, new ItemStack[] { input }, new ItemStack[] { output }));
  }

  @Override
  public void preRegister() {
    addItemHandler(new BlockTicker() {

      @Override
      public void tick(Block b, SlimefunItem sf, Config data) {
        ImprovementForge.this.tick(b);
      }

      @Override
      public boolean isSynchronized() {
        return false;
      }
    });
  }

  protected void tick(Block b) {
    BlockMenu inv = BlockStorage.getInventory(b);

    if (isProcessing(b)) {
      int timeleft = progress.get(b);

      if (timeleft > 0) {
        ChestMenuUtils.updateProgressbar(inv, 22, timeleft, processing.get(b).getTicks(), getProgressBar());

        if (isChargeable()) {
          if (getCharge(b.getLocation()) < getEnergyConsumption()) {
            return;
          }

          removeCharge(b.getLocation(), getEnergyConsumption());
        }
        progress.put(b, timeleft - 1);
      }
      else {
        inv.replaceExistingItem(22, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "));

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

  protected MachineRecipe findNextRecipe(BlockMenu menu) {
    for (int slot : getInputSlots()) {
      ItemStack improvementCore = menu.getItemInSlot(slot == getInputSlots()[0] ? getInputSlots()[1] : getInputSlots()[0]);
      ItemStack item = menu.getItemInSlot(slot);

      if (item != null) {
        if (SlimefunUtils.isItemSimilar(improvementCore, Items.IMPROVEMENT_CORE, true, false)) {

          int tier = -1;
          int index = -1;

          Material type = item.getType();

          for (int i = 0; i < tools.length - 1; i++) {
            for (int j = 0; j < tools[0].length; j++) {
              if (tools[i][j] == item.getType()) {
                tier = i;
                index = j;
              }
            }
          }

          if (tier == -1) {
            return null;
          }

          ItemStack improvedItem = item.clone();
          improvedItem.setType(tools[tier+1][index]);

          if (!menu.fits(improvedItem, getOutputSlots())) {
            return null;
          }

          for (int inputSlot : getInputSlots()) {
            menu.consumeItem(inputSlot);
          }

          return new MachineRecipe(60, new ItemStack[] { improvementCore, item }, new ItemStack[] { improvedItem });
        }

        break;
      }
    }

    return null;
  }

  protected void constructMenu(BlockMenuPreset preset) {
    for (int i : BORDER) {
      preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
    }

    for (int i : BORDER_IN) {
      preset.addItem(i, new SlimefunItemStack("_UI_INPUT_SLOT", Material.CYAN_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
    }

    for (int i : BORDER_OUT) {
      preset.addItem(i, new SlimefunItemStack("_UI_OUTPUT_SLOT", Material.ORANGE_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
    }

    preset.addItem(22, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());

    for (int i : getOutputSlots()) {
      preset.addMenuClickHandler(i, new AdvancedMenuClickHandler() {

        @Override
        public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
          return false;
        }

        @Override
        public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
          return cursor == null || cursor.getType() == null || cursor.getType() == Material.AIR;
        }
      });
    }
  }
}
