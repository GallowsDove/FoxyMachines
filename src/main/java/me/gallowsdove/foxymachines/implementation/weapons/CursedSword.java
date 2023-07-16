package me.gallowsdove.foxymachines.implementation.weapons;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CursedSword extends OnHitWeapon {
    public static final List<PotionEffect> EFFECTS = List.of(
            new PotionEffect(PotionEffectType.SLOW, 80, 1, false, false),
            new PotionEffect(PotionEffectType.BLINDNESS, 80, 20, false, false),
            new PotionEffect(PotionEffectType.CONFUSION, 100, 3, false, false),
            new PotionEffect(PotionEffectType.WITHER, 80, 1, false, false));

    public CursedSword() {
        super(Items.WEAPONS_AND_ARMORS_ITEM_GROUP, Items.CURSED_SWORD, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                Items.BLOOD, Items.CURSED_RABBIT_PAW, Items.BLOOD,
                Items.MAGIC_LUMP_5, new ItemStack(Material.NETHERITE_SWORD), Items.MAGIC_LUMP_5,
                Items.BLOOD, Items.BLOOD_INFUSED_SKULL, Items.BLOOD
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onHit(ThreadLocalRandom random, EntityDamageByEntityEvent event, HumanEntity humanoid, LivingEntity entity) {
        // Life Steal I
        double health = humanoid.getHealth() + 1.25D;
        double maxHealth = humanoid.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        humanoid.setHealth(Math.min(health, maxHealth));

        // Armor Ignorance I
        double damage = event.getDamage();
        double finalDamage = event.getFinalDamage();
        Utils.dealDamageBypassingArmor(entity, (damage - finalDamage) * 0.05);

        // Increased Damage
        event.setDamage(damage * 1.4);

        // Negative Effects
        entity.addPotionEffects(EFFECTS);

        // Particle Effects
        for (int i = 0; i < 10; i++) {
            entity.getWorld().spawnParticle(Particle.SQUID_INK, entity.getLocation(), 1,
                    random.nextDouble(-1, 1), random.nextDouble(1.6, 2), random.nextDouble(-1, 1), 0);
        }

        // Low Chance of Afflicting the User
        if (random.nextInt(1000) < 25) {
            int result = random.nextInt(100);
            if (result < 20) {
                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 10, false, false));
            } else if (result < 40) {
                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 10, false, false));
            } else if (result < 60) {
                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 2, false, false));
            } else if (result < 80) {
                humanoid.damage(event.getDamage() / 2);
            } else {
                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 2, false, false));
            }
        }
    }
}
