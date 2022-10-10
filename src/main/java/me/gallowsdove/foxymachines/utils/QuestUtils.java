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
            p.sendMessage(ChatColor.RED + line.firstHalf() + toString(id) + line.secondHalf());
        } else if (item == Items.CELESTIAL_SWORD) {
            int i = random.nextInt(CELESTIAL_LINES.size());
            Line line = CELESTIAL_LINES.get(i);
            p.sendMessage(ChatColor.YELLOW + line.firstHalf() + toString(id) + line.secondHalf());
        }
    }

    public static EntityType toEntityType(int id) {
        Validate.isTrue(id <= 51, "Entity ID can't be greater than 61");

        return switch (id) {
            case 0 -> EntityType.BAT;
            case 1 -> EntityType.BEE;
            case 2 -> EntityType.BLAZE;
            case 3 -> EntityType.CAT;
            case 4 -> EntityType.CHICKEN;
            case 5 -> EntityType.CAVE_SPIDER;
            case 6 -> EntityType.COD;
            case 7 -> EntityType.COW;
            case 8 -> EntityType.CREEPER;
            case 9 -> EntityType.DOLPHIN;
            case 10 -> EntityType.DONKEY;
            case 11 -> EntityType.DROWNED;
            case 12 -> EntityType.ENDERMAN;
            case 13 -> EntityType.FOX;
            case 14 -> EntityType.GHAST;
            case 15 -> EntityType.GUARDIAN;
            case 16 -> EntityType.HOGLIN;
            case 17 -> EntityType.HUSK;
            case 18 -> EntityType.HORSE;
            case 19 -> EntityType.IRON_GOLEM;
            case 20 -> EntityType.LLAMA;
            case 21 -> EntityType.MAGMA_CUBE;
            case 22 -> EntityType.MUSHROOM_COW;
            case 23 -> EntityType.OCELOT;
            case 24 -> EntityType.PANDA;
            case 25 -> EntityType.PARROT;
            case 26 -> EntityType.PHANTOM;
            case 27 -> EntityType.PIG;
            case 28 -> EntityType.PIGLIN;
            case 29 -> EntityType.PIGLIN_BRUTE;
            case 30 -> EntityType.PILLAGER;
            case 31 -> EntityType.POLAR_BEAR;
            case 32 -> EntityType.PUFFERFISH;
            case 33 -> EntityType.RABBIT;
            case 34 -> EntityType.SALMON;
            case 35 -> EntityType.SHEEP;
            case 36 -> EntityType.SILVERFISH;
            case 37 -> EntityType.SKELETON;
            case 38 -> EntityType.SLIME;
            case 39 -> EntityType.SNOWMAN;
            case 40 -> EntityType.SPIDER;
            case 41 -> EntityType.SQUID;
            case 42 -> EntityType.STRAY;
            case 43 -> EntityType.STRIDER;
            case 44 -> EntityType.TURTLE;
            case 45 -> EntityType.TROPICAL_FISH;
            case 46 -> EntityType.WITCH;
            case 47 -> EntityType.WITHER_SKELETON;
            case 48 -> EntityType.WOLF;
            case 49 -> EntityType.ZOGLIN;
            case 50 -> EntityType.ZOMBIE;
            case 51 -> EntityType.ZOMBIFIED_PIGLIN;
            default -> EntityType.FOX;
        };
    }

    public static String toString(int id) {
        Validate.isTrue(id <= 51, "Entity ID can't be greater than 61");

        return switch (id) {
            case 0 -> "Bat";
            case 1 -> "Bee";
            case 2 -> "Blaze";
            case 3 -> "Cat";
            case 4 -> "Chicken";
            case 5 -> "Cave Spider";
            case 6 -> "Cod";
            case 7 -> "Cow";
            case 8 -> "Creeper";
            case 9 -> "Dolphin";
            case 10 -> "Donkey";
            case 11 -> "Drowned";
            case 12 -> "Enderman";
            case 13 -> "Fox";
            case 14 -> "Ghast";
            case 15 -> "Guardian";
            case 16 -> "Hoglin";
            case 17 -> "Husk";
            case 18 -> "Horse";
            case 19 -> "Iron Golem";
            case 20 -> "Llama";
            case 21 -> "Magma Cube";
            case 22 -> "Mushroom Cow";
            case 23 -> "Ocelot";
            case 24 -> "Panda";
            case 25 -> "Parrot";
            case 26 -> "Phantom";
            case 27 -> "Pig";
            case 28 -> "Piglin";
            case 29 -> "Piglin Brute";
            case 30 -> "Pillager";
            case 31 -> "Polar Bear";
            case 32 -> "Pufferfish";
            case 33 -> "Rabbit";
            case 34 -> "Salmon";
            case 35 -> "Sheep";
            case 36 -> "Silverfish";
            case 37 -> "Skeleton";
            case 38 -> "Slime";
            case 39 -> "Snowman";
            case 40 -> "Spider";
            case 41 -> "Squid";
            case 42 -> "Stray";
            case 43 -> "Strider";
            case 44 -> "Turtle";
            case 45 -> "Tropical Fish";
            case 46 -> "Witch";
            case 47 -> "Wither Skeleton";
            case 48 -> "Wolf";
            case 49 -> "Zoglin";
            case 50 -> "Zombie";
            case 51 -> "Zombified Piglin";
            default -> "Fox";
        };
    }
}

@AllArgsConstructor
record Line(@Getter String firstHalf, @Getter String secondHalf) { }
