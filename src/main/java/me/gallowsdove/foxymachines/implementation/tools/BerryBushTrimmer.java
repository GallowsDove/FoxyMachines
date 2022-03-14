package me.gallowsdove.foxymachines.implementation.tools;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.gallowsdove.foxymachines.FoxyMachines;
import me.gallowsdove.foxymachines.Items;
import me.gallowsdove.foxymachines.utils.SimpleLocation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;

public class BerryBushTrimmer extends SlimefunItem {
    public static HashSet<SimpleLocation> TRIMMED_BLOCKS = new HashSet<>();

    public BerryBushTrimmer() {
        super(Items.TOOLS_ITEM_GROUP, Items.BERRY_BUSH_TRIMMER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.DAMASCUS_STEEL_INGOT,
                new ItemStack(Material.STICK), SlimefunItems.DAMASCUS_STEEL_INGOT, null
        });
    }

    @Override
    public void preRegister() {
        addItemHandler(onUse(), onToolUse());
    }

    @Nonnull
    private ToolUseHandler onToolUse() {
        return (e, tool, fortune, drops) -> e.setCancelled(true);
    }

    @Nonnull
    protected ItemUseHandler onUse() {
        return e -> {
            if (e.getClickedBlock().isPresent() && e.getClickedBlock().get().getType() == Material.SWEET_BERRY_BUSH) {
                Block b = e.getClickedBlock().get();
                Player p = e.getPlayer();

                if (TRIMMED_BLOCKS.add(new SimpleLocation(b, "trimmed"))) {
                    ItemStack shears = e.getItem();
                    ItemMeta shearsMeta = e.getItem().getItemMeta();
                    int damage = ((Damageable) shearsMeta).getDamage() + 4;

                    if (damage > Material.SHEARS.getMaxDurability()) {
                        shears = null;
                    } else {
                        ((Damageable) shearsMeta).setDamage(damage);
                        shears.setItemMeta(shearsMeta);
                    }

                    p.getInventory().setItemInMainHand(shears);
                } else {
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "This berry bush is already trimmed!");
                }
            }

            e.cancel();
        };
    }

    public static void saveTrimmedBlocks() throws IOException {
        Gson gson = new Gson();

        String pluginFolder = FoxyMachines.getInstance().folderPath;

        File file = new File(pluginFolder + File.separator + "trimmeddata");
        File filePath = new File(pluginFolder);

        filePath.mkdirs();
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        writer.write(gson.toJson(TRIMMED_BLOCKS));
        writer.close();
    }

    public static void loadTrimmedBlocks() throws IOException {
        Gson gson = new Gson();

        File file = new File(FoxyMachines.getInstance().folderPath + "trimmeddata");
        File filePath = new File(FoxyMachines.getInstance().folderPath);

        filePath.mkdirs();
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String json = reader.readLine();
        reader.close();

        Type type = new TypeToken<HashSet<SimpleLocation>>() {}.getType();
        TRIMMED_BLOCKS = gson.fromJson(json, type);

        if (TRIMMED_BLOCKS == null) {
            TRIMMED_BLOCKS = new HashSet<>();
        }
    }
}
