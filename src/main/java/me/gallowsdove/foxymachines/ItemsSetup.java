package me.gallowsdove.foxymachines;

import me.gallowsdove.foxymachines.tools.ElectricWindStaff;

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
