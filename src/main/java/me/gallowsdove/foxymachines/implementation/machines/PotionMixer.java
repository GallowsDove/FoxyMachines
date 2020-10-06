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



public class PotionMixer extends SlimefunItem implements InventoryBlock, EnergyNetComponent {
  private static final int[] BORDER = {3, 4, 5, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44};
  private static final int[] BORDER_IN = {0, 1, 2, 6, 7, 8, 9, 11, 12, 14, 15, 17, 18, 19, 20, 24, 25, 26};
  private static final int[] BORDER_OUT = {21, 22, 23, 30, 32, 39, 40, 41};
  public static final int ENERGY_CONSUMPTION = 28;
  public static final int CAPACITY = 128;

  public static Map<Block, MachineRecipe> processing = new HashMap<>();
  public static Map<Block, Integer> progress = new HashMap<>();

  protected final List<MachineRecipe> recipes = new ArrayList<>();

  public PotionMixer() {
    super(Items.machines, Items.IMPROVEMENT_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      null, null, null,
      null, null, null,
      null, null, null
    });


    new BlockMenuPreset(getID(), "&6Potion Mixer") {

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
  }

  private Comparator<Integer> compareSlots(DirtyChestMenu menu) {
    return Comparator.comparingInt(slot -> menu.getItemInSlot(slot).getAmount());
  }

  @Override
  public int[] getInputSlots() {
    return new int[] { 10, 16 };
  }

  @Override
  public int[] getOutputSlots() {
    return new int[] {31};
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
    return "POTION_MIXER";
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
    return new ItemStack(Material.GOLDEN_HOE);
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
       
          ItemStack com = item.clone();
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
      .addItem(i, new SlimefunItemStack("_UI_INPUT_SLOT", Material.CYAN_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
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
