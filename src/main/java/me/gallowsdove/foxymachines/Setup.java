package me.gallowsdove.foxymachines;

import me.gallowsdove.foxymachines.tools.ElectricWindStaff;
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
  }
}
