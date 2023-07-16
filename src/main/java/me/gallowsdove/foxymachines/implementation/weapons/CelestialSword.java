package me.gallowsdove.foxymachines.implementation.weapons;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ThreadLocalRandom;

public class CelestialSword extends OnHitWeapon {
    public CelestialSword(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onHit(ThreadLocalRandom random, EntityDamageByEntityEvent event, HumanEntity humanoid, LivingEntity entity) {
        // Armor Ignorance III
        Utils.dealDamageBypassingArmor(entity, (event.getDamage() - event.getFinalDamage()) * 0.16);

        // Divine Smite II
        if (random.nextInt(100) < 15) {
            entity.getWorld().strikeLightningEffect(entity.getLocation());
            entity.damage(8);
        }

        entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 0, false, false));
    }
}
