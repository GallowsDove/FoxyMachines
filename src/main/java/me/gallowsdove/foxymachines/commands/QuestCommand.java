package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.SubCommand;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class QuestCommand extends SubCommand {
    public QuestCommand() {
        super("quest", "Prints your current quest.", "foxymachines.info");
    }

    @Override
    protected void execute(@Nonnull CommandSender commandSender, @Nonnull String[] args) {
        if (!(commandSender instanceof Player p) ) {
            return;
        }

        if (args.length != 0) {
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Usage: /foxy quest");
            return;
        }

        if (SlimefunUtils.isItemSimilar(p.getInventory().getItemInMainHand(), Items.CURSED_SWORD, false, false)) {
            QuestUtils.sendQuestLine(p, Items.CURSED_SWORD);
        } else if (SlimefunUtils.isItemSimilar(p.getInventory().getItemInMainHand(), Items.CELESTIAL_SWORD, false, false)) {
            QuestUtils.sendQuestLine(p, Items.CELESTIAL_SWORD);
        } else {
            p.sendMessage(ChatColor.LIGHT_PURPLE + "You need to be holding the " + ChatColor.RED + "Cursed Sword" +
                    ChatColor.LIGHT_PURPLE + " or the " + ChatColor.YELLOW + "Celestial Sword" + ChatColor.LIGHT_PURPLE + " to view your quest.");
        }
    }

    @Override
    protected void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) { }
}