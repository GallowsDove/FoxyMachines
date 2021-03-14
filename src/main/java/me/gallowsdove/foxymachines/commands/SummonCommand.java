package me.gallowsdove.foxymachines.commands;


import io.github.mooy1.infinitylib.command.AbstractCommand;
import me.gallowsdove.foxymachines.abstracts.CustomMob;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class SummonCommand extends AbstractCommand {

    public SummonCommand() {
        super("summon", "Summons a custom mob", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 2) {
            return;
        }

        Player p = (Player) commandSender;
        CustomMob mob = CustomMob.getByID(strings[1]);

        if (mob != null) {
            mob.spawn(p.getLocation());
        }
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        List<String> ids = new ArrayList<>();
        if (strings.length == 2) {
            ids.addAll(CustomMob.MOBS.keySet());
        }
        return ids;
    }
}