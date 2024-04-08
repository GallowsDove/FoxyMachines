package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.mooy1.infinitylib.core.AddonConfig;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class UnbreakableRune extends SimpleSlimefunItem<ItemDropHandler> {

    private static final Map<String, Set<String>> BLACKLIST = new HashMap<>();
    private static final double RANGE = 1.5;

    public static void init() {
        if (!BLACKLIST.isEmpty()) {
            FoxyMachines.log(Level.WARNING, "Attempted to initialize UnbreakableRune after already initialized!");
            return;
        }

        AddonConfig config = FoxyMachines.getInstance().getConfig();
        ConfigurationSection blacklist = config.getConfigurationSection("unbreakable-rune-blacklist");
        if (blacklist == null) {
            return;
        }

        for (String addon : blacklist.getKeys(false)) {
            BLACKLIST.put(addon, new HashSet<>(blacklist.getStringList(addon)));
        }
    }

    public UnbreakableRune() {
        super(Items.TOOLS_ITEM_GROUP, Items.UNBREAKABLE_RUNE, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                Items.DAMIENIUM, Items.MAGIC_LUMP_5, Items.DAMIENIUM,
                SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.LIGHTNING_RUNE , SlimefunItems.ESSENCE_OF_AFTERLIFE,
                Items.DAMIENIUM, Items.MAGIC_LUMP_5, Items.DAMIENIUM
        });
    }

    @Nonnull
    @Override
    public ItemDropHandler getItemHandler() {
        return (e, p, item) -> {
            if (isItem(item.getItemStack())) {

                if (!SlimefunUtils.canPlayerUseItem(p, Items.UNBREAKABLE_RUNE , true)) {
                    return true;
                }

                Scheduler.run(20, () -> activate(p, item));

                return true;
            }
            return false;
        };
    }

    private void activate(@Nonnull Player p, @Nonnull Item rune) {
        if (!rune.isValid()) {
            return;
        }

        Location l = rune.getLocation();
        Collection<Entity> entities = l.getWorld().getNearbyEntities(l, RANGE, RANGE, RANGE, entity -> findCompatibleItem(p, entity));
        Optional<Entity> optional = entities.stream().findFirst();

        if (optional.isPresent()) {
            Item item = (Item) optional.get();
            ItemStack itemStack = item.getItemStack();

            if (itemStack.getAmount() == 1) {
                l.getWorld().strikeLightningEffect(l);

                Scheduler.run(10, () -> {
                    if (rune.isValid() && item.isValid() && itemStack.getAmount() == 1) {

                        l.getWorld().createExplosion(l, 0);
                        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 0.3F, 1);

                        item.remove();
                        rune.remove();

                        setUnbreakable(itemStack);
                        l.getWorld().dropItemNaturally(l, itemStack);

                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Your item is now Unbreakable.");
                    } else {
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Your item could not be made Unbreakable");
                    }
                });
            } else {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Your item could not be made Unbreakable");
            }
        }
    }

    private boolean findCompatibleItem(Player player, Entity entity) {
        if (entity instanceof Item item) {
            ItemStack itemStack = item.getItemStack();
            return !isUnbreakable(itemStack) && !isItem(itemStack) && !isDisallowed(player, itemStack);
        }

        return false;
    }

    public static void setUnbreakable(@Nullable ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {

            if (!isUnbreakable(item) && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();

                meta.setUnbreakable(true);
                item.setItemMeta(meta);
            }
        }
    }

    public static boolean isUnbreakable(@Nullable ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.hasItemMeta()) {
                return item.getItemMeta().isUnbreakable();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isDisallowed(Player player, ItemStack itemStack) {
        final SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if (slimefunItem == null) {
            return false;
        }

        final String id = slimefunItem.getId();
        final String addon = slimefunItem.getAddon().getName();
        if (BLACKLIST.containsKey(addon) && BLACKLIST.get(addon).contains(id)) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "You can't make this item unbreakable!");
            return true;
        }
        return false;
    }
}