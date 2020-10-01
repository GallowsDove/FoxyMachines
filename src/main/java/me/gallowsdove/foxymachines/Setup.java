package me.gallowsdove.foxymachines;

import me.gallowsdove.foxymachines.implementation.tools.ElectricWindStaff;
import me.gallowsdove.foxymachines.implementation.tools.ElectricFireStaff;
import me.gallowsdove.foxymachines.implementation.tools.ElectricFireStaffII;
import me.gallowsdove.foxymachines.implementation.weapons.HealingBow;
import me.gallowsdove.foxymachines.implementation.materials.ReinforcedString;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import org.bukkit.NamespacedKey;


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
      6669668, "Harder, Better, Stronger", 18)
      .addItems(Items.REINFORCED_STRING)
      .register();
  }
}
