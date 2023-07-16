package me.gallowsdove.foxymachines.implementation.weapons;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ThreadLocalRandom;

public class Elucidator extends OnHitWeapon {
    public Elucidator(boolean customMobs) {
        super(Items.WEAPONS_AND_ARMORS_ITEM_GROUP, Items.ELUCIDATOR, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                Items.EQUANIMOUS_GEM, Items.CURSED_SWORD, Items.EQUANIMOUS_GEM,
                Items.BUCKET_OF_BLOOD, customMobs ? Items.PIXIE_QUEEN_HEART : Items.DEMONIC_PLATE, Items.BUCKET_OF_BLOOD,
                Items.EQUANIMOUS_GEM, Items.CELESTIAL_SWORD, Items.EQUANIMOUS_GEM
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onHit(ThreadLocalRandom random, EntityDamageByEntityEvent event, HumanEntity humanoid, LivingEntity entity) {
        // Damage III
        event.setDamage(event.getDamage() * 2);

        // Armor Ignorance I
        Utils.dealDamageBypassingArmor(entity, (event.getDamage() - event.getFinalDamage()) * 0.05);

        // Life Steal II && Overheal
        double health = humanoid.getHealth() + 1.5D;
        double maxHealth = humanoid.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if (health > maxHealth) {
            humanoid.setHealth(maxHealth);
            if (humanoid.getAbsorptionAmount() < 12) {
                humanoid.setAbsorptionAmount(Math.min(humanoid.getAbsorptionAmount() + (health - maxHealth) / 2, 12));
            }
        } else {
            humanoid.setHealth(health);
        }
    }
}
