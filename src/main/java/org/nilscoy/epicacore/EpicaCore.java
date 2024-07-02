package org.nilscoy.epicacore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import  org.jetbrains.annotations.NotNull;
import org.jnbt.NBTConstants;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

public final class EpicaCore extends JavaPlugin implements Listener {

    private static EpicaCore instance;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Hello!");
        getServer().getPluginManager().registerEvents(this, this);

//        File file = new File(getDataFolder()+File.separator+"PlayerData", user+".yml");
//        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        instance = this;
//        int version = 14;
//
//        getCommand("core").setExecutor(new CommandExecutor() {
//            @Override
//            public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//
//                if (strings.length == 0 || strings[0].equalsIgnoreCase("help")) {
//                    commandSender.sendMessage("Commands:\n- /core reload\n- /core nix_lox\n \nBuild version: "+version);
//                    return true;
//                }
//                if (strings[0].equalsIgnoreCase("reload")) {
//                    reloadConfig();
//                    commandSender.sendMessage("EpicaCore reloaded!");
//                    return true;
//                }
//                if (strings[0].equalsIgnoreCase("nix_lox")) {
//                    getServer().getPlayer("N1XER__").setHealth(0.0D);
//                    commandSender.sendMessage("nix is lox!");
//                    return true;
//                }
//                return true;
//            }
//        });
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Goodbye!");
    }

    public static EpicaCore getInstance() {
        return instance;
    }

    @EventHandler
    public void onInventoryClickEvent(@NotNull InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        int slot = event.getSlot();
        // getServer().broadcastMessage(String.valueOf(cursor.getType().name().split("_", 0)[0]));

        if (cursor.getType().name() != "AIR" && clicked.getType().name() != "AIR") {
            if (Objects.requireNonNull(cursor.getItemMeta()).getDisplayName().contains("Облик") && Objects.requireNonNull(clicked.getItemMeta()).getLore() == null) {
                if (cursor.getType().name().split("_", 0).length == 2) {
                    if (cursor.getType().name().split("_", 0)[1].equals(clicked.getType().name().split("_", 0)[1]) && !Objects.equals(cursor.getType().name().split("_", 0)[0], "EPICFIGHT")) {
                        event.setCancelled(true);
                        event.setCursor(new ItemStack(Material.AIR));

                        ItemStack upd_item = clicked;
                        ItemMeta item_meta = upd_item.getItemMeta();
                        item_meta.setCustomModelData(cursor.getItemMeta().getCustomModelData());
                        item_meta.setLore(Arrays.asList(ChatColor.WHITE + "Наложен облик:" + ChatColor.WHITE + cursor.getItemMeta().getDisplayName().substring(7)));

                        upd_item.setItemMeta(item_meta);
                        clicked = upd_item;
                        // inventory.setItem(slot, upd_item);
                    }
                } else if (cursor.getType().name().split("_", 0).length == 3) {
                    if (cursor.getType().name().split("_", 0)[2].equals(clicked.getType().name().split("_", 0)[2]) && cursor.getType().name().split("_", 0)[0].equals("EPICFIGHT")) {
                        event.setCancelled(true);
                        event.setCursor(new ItemStack(Material.AIR));

                        ItemStack upd_item = clicked;
                        ItemMeta item_meta = upd_item.getItemMeta();
                        item_meta.setCustomModelData(cursor.getItemMeta().getCustomModelData());
                        item_meta.setLore(Arrays.asList(ChatColor.WHITE + "Наложен облик:" + ChatColor.WHITE + cursor.getItemMeta().getDisplayName().substring(7)));

                        upd_item.setItemMeta(item_meta);
                        clicked = upd_item;
                    }
                }
            }
        }
    }
    @EventHandler
    public void onBlockClick(@NotNull PlayerInteractEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        // getServer().broadcastMessage(item.getType().name());
        if (event.getAction().name() == "RIGHT_CLICK_BLOCK") {
            if (event.getClickedBlock().getType().name() == "GRINDSTONE") {
                // event.setCancelled(true);
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().getLore().get(0).contains("облик")) {
                        ItemMeta item_meta = item.getItemMeta();
                        item_meta.setLore(null);
                        item_meta.setCustomModelData(0);
                        item.setItemMeta(item_meta);
                        event.getPlayer().getInventory().setItemInMainHand(item);
                    }
                }
            }
        }
        if (event.getAction().name() == "RIGHT_CLICK_BLOCK") {
            if (event.getClickedBlock().getType().name() == "CAULDRON") {
                if (item.getType().name() != "AIR") {
                    String[] materials = {
                            "Лепесток тысячелистника",
                            "Корень горькоцвета",
                            "Семена полыни",
                            "Лист наперстянки",
                            "Хаустония",
                            "Лесные ягоды"
                    };
                    String[] reagents = {
                            "Ягодная настойка",
                            "Мутниковая вода",
                            "Тертые растения",
                    };
                    if (Arrays.asList(materials).contains(item.getType().name())) {
                        ItemStack potion = new ItemStack(Material.POTION, 1);
                        potion = addEffect(potion, PotionEffectType.HEAL);
                    }
                }
            }
        }
    }

    public static ItemStack addEffect(ItemStack potion, PotionEffectType effect) {
        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        PotionEffect new_effect = new PotionEffect(effect, 1, 6);
        potionmeta.addCustomEffect(new_effect, true);
        potion.setItemMeta(potionmeta);
        return potion;
    }
}
