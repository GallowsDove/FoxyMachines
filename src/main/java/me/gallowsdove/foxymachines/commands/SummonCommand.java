package me.gallowsdove.foxymachines.commands;

import io.github.mooy1.infinitylib.commands.SubCommand;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public final class SummonCommand extends SubCommand {

    public SummonCommand() {
        super("summon", "Summons a custom mob", "foxymachines.admin");
    }

    @Override
    public void execute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length != 2) {
            return;
        }

        CustomMob mob = CustomMob.getByID(strings[1]);

        if (mob != null) {
            mob.spawn(p.getLocation());
        }
    }


    @Override
    public void complete(@Nonnull CommandSender commandSender, @Nonnull String[] args, @Nonnull List<String> tabs) {
        tabs.addAll(CustomMob.MOBS.keySet());
    }
}