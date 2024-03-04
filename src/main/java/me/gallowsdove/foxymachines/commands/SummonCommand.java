package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.SubCommand;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public final class SummonCommand extends SubCommand {

    public SummonCommand() {
        super("summon", "Summons a custom mob", "foxymachines.admin");
    }

    @Override
    protected void execute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Usage: /foxy summon <MOB_ID>");
            return;
        }

        CustomMob mob = CustomMob.getById(args[0]);

        if (mob != null) {
            mob.spawn(player.getLocation());
        }
    }


    @Override
    protected void complete(@Nonnull CommandSender sender, @Nonnull String[] args, @Nonnull List<String> tabs) {
        tabs.addAll(CustomMob.MOBS.keySet());
    }
}