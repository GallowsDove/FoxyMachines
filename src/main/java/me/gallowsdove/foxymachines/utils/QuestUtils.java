package me.gallowsdove.foxymachines.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import lombok.Getter;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class QuestUtils {
    private QuestUtils() {}

    public static final NamespacedKey KEY = new NamespacedKey(FoxyMachines.getInstance(), "quest");

    private static final List<EntityType> QUEST_MOBS = new ArrayList<>();
    private static final List<Line> CURSED_LINES = List.of(
            new Line("I would love to kill a ", ", so tasty!"),
            new Line("Give me a ", ", now!"),
            new Line("Surely you can help me slay a ", "."),
            new Line("I want blood....  ", " blood."),
            new Line("I need a ", " liver."),
            new Line("I've heard that ", " blood is tasty..."),
            new Line("", " heart, hmmm..."),
            new Line("I would slay God himself for some ", " flesh."),
            new Line("I could be devouring a ", " whole day."),
            new Line("I've been waiting for too long. Too long or a day to kill a ", "."),
            new Line("", "'s blood shall be spilled"),
            new Line("My curse shall devour ", "'s soul"));
    private static final List<Line> CELESTIAL_LINES = List.of(
            new Line("I love all beings... except ", ", I hate those."),
            new Line("All life must be in balance, what's why I need to kill a ", "."),
            new Line("I am celestial, but I am also a sword. Now get me a ", "."),
            new Line("I'm sorry, but please get me some ", ". No questions."),
            new Line("Celestial sword requires a celestial sacrifice. A ", "."),
            new Line("My next victim should be ", ", just as God intended."),
            new Line("And the next in line is ... ", "!"),
            new Line("The God wants a ", " dead."),
            new Line("For God and honour, go slay a ", "."),
            new Line("Go, get that ", "! For justice!"),
            new Line("The stars have aligned. I can clearly see the ", " that shall die by my blade"));


    public static void init() {
        if (!QUEST_MOBS.isEmpty()) {
            FoxyMachines.log(Level.WARNING, "Attempted to initialize QuestUtils after already initialized!");
            return;
        }

        for (String questMob : FoxyMachines.getInstance().getConfig().getStringList("quest-mobs")) {
            try {
                QuestUtils.QUEST_MOBS.add(EntityType.valueOf(questMob));
            } catch (IllegalArgumentException ignored) {
                FoxyMachines.log(Level.WARNING, "Invalid Entity Type in \"quest-mobs\": " + questMob);
            }
        }
    }

    @ParametersAreNonnullByDefault
    public static boolean hasActiveQuest(Player p) {
        return p.getPersistentDataContainer().has(KEY, PersistentDataType.INTEGER);
    }

    @ParametersAreNonnullByDefault
    public static boolean isQuestEntity(Player p, LivingEntity e) {
        return hasActiveQuest(p) && toEntityType(p, getQuestLine(p)) == e.getType();
    }

    @ParametersAreNonnullByDefault
    public static int getQuestLine(Player p) {
        PersistentDataContainer container = p.getPersistentDataContainer();
        int id;

        if (container.has(KEY, PersistentDataType.INTEGER)) {
            id = container.get(KEY, PersistentDataType.INTEGER);
        } else {
            id = nextQuestLine(p);
        }

        return id;
    }

    @ParametersAreNonnullByDefault
    public static int nextQuestLine(Player p) {
        int id = ThreadLocalRandom.current().nextInt(QUEST_MOBS.size());
        p.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, id);
        return id;
    }

    @ParametersAreNonnullByDefault
    public static void sendQuestLine(Player p, SlimefunItemStack item) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int id = getQuestLine(p);

        if (item == Items.CURSED_SWORD) {
            int i = random.nextInt(CURSED_LINES.size());
            Line line = CURSED_LINES.get(i);
            p.sendMessage(ChatColor.RED + line.firstHalf() + toString(p, id) + line.secondHalf());
        } else if (item == Items.CELESTIAL_SWORD) {
            int i = random.nextInt(CELESTIAL_LINES.size());
            Line line = CELESTIAL_LINES.get(i);
            p.sendMessage(ChatColor.YELLOW + line.firstHalf() + toString(p, id) + line.secondHalf());
        }
    }

    public static EntityType toEntityType(Player p, int id) {
        if (id >= QUEST_MOBS.size()) {
            id = nextQuestLine(p);
        }

        return QUEST_MOBS.get(id);
    }

    public static String toString(Player p, int id) {
        if (id >= QUEST_MOBS.size()) {
            id = nextQuestLine(p);
        }

        return ChatUtils.humanize(QUEST_MOBS.get(id).name().toLowerCase());
    }

    @ParametersAreNonnullByDefault
    public static void resetQuestLine(Player p) {
        PersistentDataContainer container = p.getPersistentDataContainer();

        if (container.has(KEY, PersistentDataType.INTEGER)) {
            container.remove(KEY);
        }
    }
}

record Line(@Getter String firstHalf, @Getter String secondHalf) { }
