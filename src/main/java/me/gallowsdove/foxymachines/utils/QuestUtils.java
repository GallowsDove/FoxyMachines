package me.gallowsdove.foxymachines.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestUtils {
    public static NamespacedKey KEY = new NamespacedKey(FoxyMachines.getInstance(), "quest");

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

    @ParametersAreNonnullByDefault
    public static void sendQuestLine(Player p, SlimefunItemStack item) {
        PersistentDataContainer container = p.getPersistentDataContainer();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int id;

        if (container.has(KEY, PersistentDataType.INTEGER)) {
            id = container.get(KEY, PersistentDataType.INTEGER);
        } else {
            id = random.nextInt(52);
            container.set(KEY, PersistentDataType.INTEGER, id);
        }

        if (item == Items.CURSED_SWORD) {
            int i = random.nextInt(CURSED_LINES.size());
            Line line = CURSED_LINES.get(i);
            p.sendMessage(ChatColor.RED + line.getFirstHalf() + toString(id) + line.getSecondHalf());
        } else if (item == Items.CELESTIAL_SWORD) {
            int i = random.nextInt(CELESTIAL_LINES.size());
            Line line = CELESTIAL_LINES.get(i);
            p.sendMessage(ChatColor.YELLOW + line.getFirstHalf() + toString(id) + line.getSecondHalf());
        }
    }

    public static EntityType toEntityType(int id) {
        Validate.isTrue(id <= 61, "Entity ID can't be greater than 61");

        switch (id) {
            case 0:
                return EntityType.BAT;
            case 1:
                return EntityType.BEE;
            case 2:
                return EntityType.BLAZE;
            case 3:
                return EntityType.CAT;
            case 4:
                return EntityType.CHICKEN;
            case 5:
                return EntityType.CAVE_SPIDER;
            case 6:
                return EntityType.COD;
            case 7:
                return EntityType.COW;
            case 8:
                return EntityType.CREEPER;
            case 9:
                return EntityType.DOLPHIN;
            case 10:
                return EntityType.DONKEY;
            case 11:
                return EntityType.DROWNED;
            case 12:
                return EntityType.ENDERMAN;
            case 13:
                return EntityType.FOX;
            case 14:
                return EntityType.GHAST;
            case 15:
                return EntityType.GUARDIAN;
            case 16:
                return EntityType.HOGLIN;
            case 17:
                return EntityType.HUSK;
            case 18:
                return EntityType.HORSE;
            case 19:
                return EntityType.IRON_GOLEM;
            case 20:
                return EntityType.LLAMA;
            case 21:
                return EntityType.MAGMA_CUBE;
            case 22:
                return EntityType.MUSHROOM_COW;
            case 23:
                return EntityType.OCELOT;
            case 24:
                return EntityType.PANDA;
            case 25:
                return EntityType.PARROT;
            case 26:
                return EntityType.PHANTOM;
            case 27:
                return EntityType.PIG;
            case 28:
                return EntityType.PIGLIN;
            case 29:
                return EntityType.PIGLIN_BRUTE;
            case 30:
                return EntityType.PILLAGER;
            case 31:
                return EntityType.POLAR_BEAR;
            case 32:
                return EntityType.PUFFERFISH;
            case 33:
                return EntityType.RABBIT;
            case 34:
                return EntityType.SALMON;
            case 35:
                return EntityType.SHEEP;
            case 36:
                return EntityType.SILVERFISH;
            case 37:
                return EntityType.SKELETON;
            case 38:
                return EntityType.SLIME;
            case 39:
                return EntityType.SNOWMAN;
            case 40:
                return EntityType.SPIDER;
            case 41:
                return EntityType.SQUID;
            case 42:
                return EntityType.STRAY;
            case 43:
                return EntityType.STRIDER;
            case 44:
                return EntityType.TURTLE;
            case 45:
                return EntityType.TROPICAL_FISH;
            case 46:
                return EntityType.WITCH;
            case 47:
                return EntityType.WITHER_SKELETON;
            case 48:
                return EntityType.WOLF;
            case 49:
                return EntityType.ZOGLIN;
            case 50:
                return EntityType.ZOMBIE;
            case 51:
                return EntityType.ZOMBIFIED_PIGLIN;
        }
        return EntityType.FOX;
    }

    public static String toString(int id) {
        Validate.isTrue(id <= 61, "Entity ID can't be greater than 61");

        switch (id) {
            case 0:
                return "Bat";
            case 1:
                return "Bee";
            case 2:
                return "Blaze";
            case 3:
                return "Cat";
            case 4:
                return "Chicken";
            case 5:
                return "Cave Spider";
            case 6:
                return "Cod";
            case 7:
                return "Cow";
            case 8:
                return "Creeper";
            case 9:
                return "Dolphin";
            case 10:
                return "Donkey";
            case 11:
                return "Drowned";
            case 12:
                return "Enderman";
            case 13:
                return "Fox";
            case 14:
                return "Ghast";
            case 15:
                return "Guardian";
            case 16:
                return "Hoglin";
            case 17:
                return "Husk";
            case 18:
                return "Horse";
            case 19:
                return "Iron Golem";
            case 20:
                return "Llama";
            case 21:
                return "Magma Cube";
            case 22:
                return "Mushroom Cow";
            case 23:
                return "Ocelot";
            case 24:
                return "Panda";
            case 25:
                return "Parrot";
            case 26:
                return "Phantom";
            case 27:
                return "Pig";
            case 28:
                return "Piglin";
            case 29:
                return "Piglin Brute";
            case 30:
                return "Pillager";
            case 31:
                return "Polar Bear";
            case 32:
                return "Pufferfish";
            case 33:
                return "Rabbit";
            case 34:
                return "Salmon";
            case 35:
                return "Sheep";
            case 36:
                return "Silverfish";
            case 37:
                return "Skeleton";
            case 38:
                return "Slime";
            case 39:
                return "Snowman";
            case 40:
                return "Spider";
            case 41:
                return "Squid";
            case 42:
                return "Stray";
            case 43:
                return "Strider";
            case 44:
                return "Turtle";
            case 45:
                return "Tropical Fish";
            case 46:
                return "Witch";
            case 47:
                return "Wither Skeleton";
            case 48:
                return "Wolf";
            case 49:
                return "Zoglin";
            case 50:
                return "Zombie";
            case 51:
                return "Zombified Piglin";
        }
        return "Fox";
    }
}

@AllArgsConstructor
class Line {
    @Getter
    private final String firstHalf;
    @Getter
    private final String secondHalf;
}
