package me.gallowsdove.foxymachines.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import me.gallowsdove.foxymachines.utils.Utils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SwordListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            if (e.getDamager() instanceof HumanEntity) {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                HumanEntity humanoid = (HumanEntity) e.getDamager();
                ItemStack item = humanoid.getInventory().getItemInMainHand();

                if (e.getEntity() instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) e.getEntity();

                    if (SlimefunUtils.isItemSimilar(item, Items.CURSED_SWORD, false, false)) {
                        ArrayList<PotionEffect> effects = new ArrayList<>();

                        effects.add(new PotionEffect(PotionEffectType.SLOW, 80, 1, false, false));
                        effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 80, 20, false, false));
                        effects.add(new PotionEffect(PotionEffectType.CONFUSION, 100, 3, false, false));
                        effects.add(new PotionEffect(PotionEffectType.WITHER, 80, 1, false, false));

                        double health = humanoid.getHealth() + 1.25D;
                        double maxHealth = humanoid.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        humanoid.setHealth(Math.min(health, maxHealth));

                        Utils.dealDamageBypassingArmor(entity, (e.getDamage() - e.getFinalDamage()) * 0.05);

                        entity.addPotionEffects(effects);

                        e.setDamage(e.getDamage() * 1.4);

                        for (int i = 0; i < 10; i++) {
                            entity.getWorld().spawnParticle(Particle.SQUID_INK, entity.getLocation(), 1,
                                    random.nextDouble(-1, 1), random.nextDouble(1.6, 2), random.nextDouble(-1, 1), 0);
                        }

                        if (random.nextInt(1000) < 25) {
                            int result = random.nextInt(100);
                            if (result < 20) {
                                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 10, false, false));
                            } else if (result < 40) {
                                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 10, false, false));
                            } else if (result < 60) {
                                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 2, false, false));
                            } else if (result < 80) {
                                humanoid.damage(e.getDamage() / 2);
                            } else {
                                humanoid.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 2, false, false));
                            }
                        }
                    } else if (SlimefunUtils.isItemSimilar(item, Items.CELESTIAL_SWORD, false, false)) {

                        Utils.dealDamageBypassingArmor(entity, (e.getDamage() - e.getFinalDamage()) * 0.16);

                        if (random.nextInt(100) < 15) {
                            entity.getWorld().strikeLightningEffect(entity.getLocation());
                            entity.damage(8);
                        }

                        entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 0, false, false));

                    } else if (SlimefunUtils.isItemSimilar(item, Items.ELUCIDATOR, false, false)) {

                        e.setDamage(e.getDamage() * 2);

                        Utils.dealDamageBypassingArmor(entity, e.getDamage() - e.getFinalDamage() * 0.06);

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
            }
        }
    }

    @EventHandler
    private void onSwordKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();

            PlayerInventory inventory = p.getInventory();
            PersistentDataContainer container = p.getPersistentDataContainer();

            if (!container.has(QuestUtils.KEY, PersistentDataType.INTEGER)) {
                return;
            }

            int id = container.get(QuestUtils.KEY, PersistentDataType.INTEGER);

            if (e.getEntity().getType() == QuestUtils.toEntityType(id)) {
                if (SlimefunUtils.isItemSimilar(inventory.getItemInMainHand(), Items.CURSED_SWORD, false, false)) {
                    inventory.addItem(new SlimefunItemStack(Items.CURSED_SHARD, 1));
                    p.sendMessage(ChatColor.RED + "The Cursed Sword is pleased.");
                    container.set(QuestUtils.KEY, PersistentDataType.INTEGER, ThreadLocalRandom.current().nextInt(52));
                } else if (SlimefunUtils.isItemSimilar(inventory.getItemInMainHand(), Items.CELESTIAL_SWORD, false, false)) {
                    inventory.addItem(new SlimefunItemStack(Items.CELESTIAL_SHARD, 1));
                    p.sendMessage(ChatColor.YELLOW + "The Celestial Sword is pleased.");
                    container.set(QuestUtils.KEY, PersistentDataType.INTEGER, ThreadLocalRandom.current().nextInt(52));
                }
            }
        }
    }
}
