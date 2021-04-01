package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class SacrificialAltarCommand extends AbstractCommand {
    public SacrificialAltarCommand() {
        super("altar", "Gives you a link to the Sacrificial Altar", "foxymachines.info");
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 1) {
            return;
        }

        Player p = (Player) commandSender;

        p.sendMessage(ChatColor.LIGHT_PURPLE + "https://youtu.be/KbwCCpzq3O0");
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) { }
}
