package me.gallowsdove.foxymachines.listeners;

import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.implementation.weapons.CelestialSword;
import me.gallowsdove.foxymachines.implementation.weapons.CursedSword;
import me.gallowsdove.foxymachines.implementation.weapons.OnHitWeapon;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.concurrent.ThreadLocalRandom;

public class SwordListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent event) {
        // If it's not a possible cause
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            return;
        }

        // If the attacker is not a HumanEntity, someone who can't use the item, return
        // Or if the attacked entity is not a living entity, someone who can't be attacked, return
        if (!(event.getDamager() instanceof HumanEntity humanoid) || !(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        ItemStack item = humanoid.getInventory().getItemInMainHand();
        if (SlimefunItem.getByItem(item) instanceof OnHitWeapon onHitWeapon) {
            onHitWeapon.onHit(random, event, humanoid, entity);
        }
    }

    @EventHandler
    private void onSwordKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            return;
        }

        Player p = e.getEntity().getKiller();
        if (!QuestUtils.hasActiveQuest(p) || !QuestUtils.isQuestEntity(p, e.getEntity())) {
            return;
        }

        QuestUtils.nextQuestLine(p);
        PlayerInventory inventory = p.getInventory();
        SlimefunItem sfItem = SlimefunItem.getByItem(inventory.getItemInMainHand());

        if (sfItem instanceof CursedSword) {
            inventory.addItem(new SlimefunItemStack(Items.CURSED_SHARD, 1));
            p.sendMessage(ChatColor.RED + "The Cursed Sword is pleased.");
            Scheduler.run(20, () -> QuestUtils.sendQuestLine(p, Items.CURSED_SWORD));
        } else if (sfItem instanceof CelestialSword) {
            inventory.addItem(new SlimefunItemStack(Items.CELESTIAL_SHARD, 1));
            p.sendMessage(ChatColor.YELLOW + "The Celestial Sword is pleased.");
            Scheduler.run(20, () -> QuestUtils.sendQuestLine(p, Items.CELESTIAL_SWORD));
        }
    }
}
