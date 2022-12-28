package me.gallowsdove.foxymachines.implementation.tools;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.SimpleLocation;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class PositionSelector extends SlimefunItem implements NotPlaceable, Rechargeable {
    public static final float COST = 4F;

    public PositionSelector() {
        super(Items.TOOLS_ITEM_GROUP, Items.POSITION_SELECTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.SWEET_INGOT, Items.WIRELESS_TRANSMITTER, Items.SWEET_INGOT,
                SlimefunItems.CORINTHIAN_BRONZE_INGOT, SlimefunItems.BIG_CAPACITOR, SlimefunItems.CORINTHIAN_BRONZE_INGOT,
                Items.SWEET_INGOT, SlimefunItems.CORINTHIAN_BRONZE_INGOT, Items.SWEET_INGOT
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onUse(), onToolUse());
    }

    @Override
    public float getMaxItemCharge(ItemStack itemStack) {
        return 200;
    }

    @Nonnull
    protected ItemUseHandler onUse() {
        return e -> {
            if (e.getClickedBlock().isPresent() && removeItemCharge(e.getItem(), COST)) {
                Block block = e.getClickedBlock().get();
                Player player = e.getPlayer();

                SimpleLocation loc = new SimpleLocation(block, "secondary_position");
                loc.storePersistently(player.getPersistentDataContainer());
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Secondary position set to " + loc);
            }
        };
    }

    @Nonnull
    private ToolUseHandler onToolUse() {
        return (e, tool, fortune, drops) -> e.setCancelled(true);
    }
}
