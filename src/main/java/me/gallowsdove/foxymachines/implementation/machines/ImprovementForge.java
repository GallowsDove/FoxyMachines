package me.gallowsdove.foxymachines.implementation.machines;

import me.gallowsdove.foxymachines.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class ImprovementForge extends SlimefunItem implements InventoryBlock, EnergyNetComponent {
  private static final int[] BORDER = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44 };
  private static final int[] BORDER_IN = { 9, 10, 11, 12, 18, 21, 27, 28, 29, 30 };
  private static final int[] BORDER_OUT = { 14, 15, 16, 17, 23, 26, 32, 33, 34, 35 };
  public static final int ENERGY_CONSUMPTION = 64;
  public static final int CAPACITY = 512;

  public ImprovementForge() {
    super(Items.machines, Items.IMPROVEMENT_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
      null, null, null,
      null, null, null,
      null, null, null
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
  }

  private Comparator<Integer> compareSlots(DirtyChestMenu menu) {
    return Comparator.comparingInt(slot -> menu.getItemInSlot(slot).getAmount());
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
}
