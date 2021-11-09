package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.SubCommand;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class KillallCommand extends SubCommand {
    public KillallCommand() {
        super("killall", "击杀FoxyMachines召唤的所有自定义生物", "foxymachines.admin");
    }

    @Override
    protected void execute(@Nonnull CommandSender commandSender, @Nonnull String[] args) {
        if (!(commandSender instanceof Player p)) {
            return;
        }

        if (args.length != 0) {
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "使用方法: /foxy killall");
            return;
        }

        for (LivingEntity entity : p.getWorld().getLivingEntities()) {
            CustomMob mob = CustomMob.getByEntity(entity);
            if (mob != null) {
                entity.remove();
            }
        }

        CustomBoss.removeBossBars();
    }

    @Override
    protected void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) { }
}