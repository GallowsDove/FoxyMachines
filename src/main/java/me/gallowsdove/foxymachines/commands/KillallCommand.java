package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.SubCommand;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class KillallCommand extends SubCommand {
    public KillallCommand() {
        super("killall", "Kills all Custom Mobs from FoxyMachines in your current World.", "foxymachines.admin");
    }

    @Override
    protected void execute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        if (args.length != 0) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Usage: /foxy killall");
            return;
        }

        int i = 0;
        for (Map.Entry<CustomMob, Set<UUID>> entry : CustomMob.MOB_CACHE.entrySet()) {
            Set<UUID> entities = entry.getValue();
            Set<UUID> newEntities = new HashSet<>();
            for (UUID uuid : new HashSet<>(entities)) {
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null && entity.getWorld().equals(player.getWorld())) {
                    i++;
                    entity.remove();
                    continue;
                }
                newEntities.add(uuid);
            }
            entry.setValue(newEntities);
        }

        CustomBoss.removeBossBars();

        player.sendMessage("Killed %s Entities".formatted(i));
    }

    @Override
    protected void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) { }
}