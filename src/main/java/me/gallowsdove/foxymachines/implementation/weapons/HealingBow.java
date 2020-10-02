package me.gallowsdove.foxymachines.implementation.weapons;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.entity.Entity;
import org.bukkit.entity.AbstractArrow;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.core.handlers.BowShootHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.weapons.SlimefunBow;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;

public class HealingBow extends SlimefunBow {

  public HealingBow() {
    super(Items.weapons, Items.HEALING_BOW, new ItemStack[] {
      null, SlimefunItems.SYNTHETIC_DIAMOND, Items.REINFORCED_STRING,
      SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.ESSENCE_OF_AFTERLIFE, Items.REINFORCED_STRING,
      null, SlimefunItems.SYNTHETIC_DIAMOND, Items.REINFORCED_STRING
    });
  }

  @Override
  public BowShootHandler onShoot() {
    return (e, n) -> {
      n.getWorld().playEffect(n.getLocation(), Effect.STEP_SOUND, Material.BRAIN_CORAL);
      n.getWorld().playEffect(n.getEyeLocation(), Effect.STEP_SOUND, Material.BRAIN_CORAL);
      e.getDamager().remove();
      e.setCancelled(true);
      n.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, (int)Math.floor(e.getDamage()/5)));
    };
  }
  /*
  public BowReleaseHandler getBowReleaseHandler() {
    return (e) -> {
      if (e.getProjectile() instanceof AbstractArrow) {
        AbstractArrow a = (AbstractArrow) e.getProjectile();
        a.setDamage(0);
        a.setKnockbackStrength(0);
        e.setProjectile(a);
      }
    };
  }

  @Override
  public void preRegister() {
    super.preRegister();

    addItemHandler(getBowReleaseHandler());
  }
  */
}
