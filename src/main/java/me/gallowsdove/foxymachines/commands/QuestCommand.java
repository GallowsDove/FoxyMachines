package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.command.AbstractCommand;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.QuestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class QuestCommand extends AbstractCommand {
    public QuestCommand() {
        super("quest", "Prints your current quest.", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 1) {
            return;
        }

        Player p = (Player) commandSender;

        if (SlimefunUtils.isItemSimilar(p.getInventory().getItemInMainHand(), Items.CURSED_SWORD, false, false)) {
            QuestUtils.sendQuestLine(p, Items.CURSED_SWORD);
        } else if (SlimefunUtils.isItemSimilar(p.getInventory().getItemInMainHand(), Items.CELESTIAL_SWORD, false, false)) {
            QuestUtils.sendQuestLine(p, Items.CELESTIAL_SWORD);
        } else {
            p.sendMessage(ChatColor.LIGHT_PURPLE + "You need to be holding the " + ChatColor.RED + "Chaos Sword" +
                    ChatColor.LIGHT_PURPLE + " or the " + ChatColor.YELLOW + "Celestial Sword" + ChatColor.LIGHT_PURPLE + " to view your quest.");
        }
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        return new ArrayList<String>() ;
    }
}