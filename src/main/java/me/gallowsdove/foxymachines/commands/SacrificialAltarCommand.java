package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class SacrificialAltarCommand extends SubCommand {
    public SacrificialAltarCommand() {
        super("altar", "Gives you a link to the Sacrificial Altar", "foxymachines.info");
    }

    @Override
    protected void execute(@Nonnull CommandSender commandSender, @Nonnull String[] args) {
        if (!(commandSender instanceof Player p)) {
            return;
        }

        p.sendMessage(ChatColor.LIGHT_PURPLE + "https://youtu.be/KbwCCpzq3O0");
    }

    @Override
    protected void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) { }
}
