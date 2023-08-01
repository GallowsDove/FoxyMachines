package me.gallowsdove.foxymachines.implementation.weapons;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ThreadLocalRandom;

public class CelestialSword extends OnHitWeapon {
    public CelestialSword() {
        super(Items.WEAPONS_AND_ARMORS_ITEM_GROUP, Items.CELESTIAL_SWORD, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                Items.MAGIC_LUMP_5, Items.POSEIDONS_BLESSING, Items.MAGIC_LUMP_5,
                Items.PURE_BONE_DUST, new ItemStack(Material.NETHERITE_SWORD), Items.PURE_BONE_DUST,
                Items.MAGIC_LUMP_5, Items.POSEIDONS_BLESSING, Items.MAGIC_LUMP_5
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onHit(EntityDamageByEntityEvent event, HumanEntity humanoid, LivingEntity entity) {
        // Armor Penetration V
        Utils.dealDamageBypassingArmor(entity, (event.getDamage() - event.getFinalDamage()) * 0.16);

        // Divine Smite II
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (random.nextInt(100) < 15) {
            entity.getWorld().strikeLightningEffect(entity.getLocation());
            entity.damage(8);
        }

        entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 0, false, false));
    }
}
