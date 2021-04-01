package me.gallowsdove.foxymachines.implementation.consumables;

import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

public class UnbreakableRune extends SimpleSlimefunItem<ItemDropHandler> {

    private static final double RANGE = 1.5;


    public UnbreakableRune() {
        super(Items.CATEGORY, Items.UNBREAKABLE_RUNE, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
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

                FoxyMachines.getInstance().runSync(() -> activate(p, item), 20);

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
        Collection<Entity> entities = l.getWorld().getNearbyEntities(l, RANGE, RANGE, RANGE, this::findCompatibleItem);
        Optional<Entity> optional = entities.stream().findFirst();

        if (optional.isPresent()) {
            Item item = (Item) optional.get();
            ItemStack itemStack = item.getItemStack();

            if (itemStack.getAmount() == 1) {
                l.getWorld().strikeLightningEffect(l);

                FoxyMachines.getInstance().runSync(() -> {
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
                }, 10);
            } else {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Your item could not be made Unbreakable");
            }
        }
    }

    private boolean findCompatibleItem(Entity entity) {
        if (entity instanceof Item) {
            Item item = (Item) entity;

            return !isUnbreakable(item.getItemStack()) && !isItem(item.getItemStack());
        }

        return false;
    }

    public static void setUnbreakable(@Nullable ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {

            if (!isUnbreakable(item)) {
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
}