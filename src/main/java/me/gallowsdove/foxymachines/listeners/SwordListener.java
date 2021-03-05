package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SwordListener implements Listener {
    @EventHandler
    private void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof HumanEntity) {
            HumanEntity humanoid = (HumanEntity) e.getDamager();
            ItemStack item = ((HumanEntity) (e.getDamager())).getInventory().getItemInMainHand();

            if (SlimefunUtils.isItemSimilar(item, Items.CURSED_SWORD, false, false)) {
                if (e.getEntity() instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) e.getEntity();

                    ArrayList<PotionEffect> effects = new ArrayList<>();

                    effects.add(new PotionEffect(PotionEffectType.SLOW, 80, 1, false, false));
                    effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 80, 2, false, false));
                    effects.add(new PotionEffect(PotionEffectType.CONFUSION, 100, 3, false, false));
                    effects.add(new PotionEffect(PotionEffectType.WITHER, 80, 1, false, false));

                    double health = humanoid.getHealth() + 1.0D;
                    double maxHealth = humanoid.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    humanoid.setHealth(Math.min(health, maxHealth));

                    entity.addPotionEffects(effects);
                }
            } else if (SlimefunUtils.isItemSimilar(item, Items.CELESTIAL_SWORD, false, false)) {
                if (e.getEntity() instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) e.getEntity();

                    double damageDiff = (e.getDamage() - e.getFinalDamage()) * 0.2;
                    if (damageDiff >= 0) {

                        EntityDamageEvent event = new EntityDamageEvent(entity, DamageCause.ENTITY_ATTACK, damageDiff);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if (!e.isCancelled()) {
                            if (entity.getAbsorptionAmount() >= 0) {
                                if (entity.getAbsorptionAmount() - damageDiff > 0) {
                                    entity.setAbsorptionAmount(entity.getAbsorptionAmount() - damageDiff);
                                    damageDiff = 0;
                                } else {
                                    entity.setAbsorptionAmount(0);
                                    damageDiff = damageDiff - entity.getAbsorptionAmount();
                                }
                            }
                            if (damageDiff > 0) {
                                if (entity.getHealth() - damageDiff >= 0) {
                                    entity.setHealth(entity.getHealth() - damageDiff);
                                } else {
                                    entity.setHealth(0);
                                }
                            }
                        }
                    }

                    if (ThreadLocalRandom.current().nextInt(100) < 15) {
                        entity.getWorld().strikeLightningEffect(entity.getLocation());
                        entity.damage(10);
                    }
                }
            }
        }
    }
}
