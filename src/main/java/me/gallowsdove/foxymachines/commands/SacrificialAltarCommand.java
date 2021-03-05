package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.command.AbstractCommand;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.*;

public class SacrificialAltarCommand extends AbstractCommand {
    public SacrificialAltarCommand() {
        super("altar", "Gives you a link to the Sacrificial Altar", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 1) {
            return;
        }

        Player p = (Player) commandSender;

        p.sendMessage(ChatColor.LIGHT_PURPLE + "https://youtu.be/KbwCCpzq3O0");
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        return new ArrayList<String>() ;
    }
}
