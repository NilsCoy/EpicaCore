package org.nilscoy.epicacore;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import  org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public final class EpicaCore extends JavaPlugin implements Listener {

    private static EpicaCore instance;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Hello!");
        getServer().getPluginManager().registerEvents(this, this);

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
        assert cursor != null;
        if ((!event.getClick().equals(ClickType.DROP) || !event.getClick().equals(ClickType.CONTROL_DROP)) && cursor != null && clicked != null) {
            assert clicked != null;
            if (cursor.getType() != Material.AIR && clicked.getType() != Material.AIR) {
            if (Objects.requireNonNull(cursor.getItemMeta().getDisplayName()).contains("Облик") && Objects.requireNonNull(clicked.getItemMeta()).getLore() == null) {
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
        }}
    }

    @EventHandler
    public void onBlockClick(@NotNull PlayerInteractEvent event) throws IOException {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        String player = event.getPlayer().getName();
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
        // сделать чтоб варилось по координатоам
        if (event.getHand() == EquipmentSlot.HAND && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WATER_CAULDRON) {
            // getServer().broadcastMessage("1");
            if (!event.getPlayer().getGameMode().name().equals("CREATIVE")) {
                // getServer().broadcastMessage("2");
                event.setCancelled(true);
                if (item.getType().name() != "AIR") {
                    String[] ingredients = {
                            "Лепесток тысячелистника",
                            "Корень горькоцвета",
                            "Семена полыни",
                            "Лист наперстянки",
                            "Хаустония",
                            "Лесные ягоды",
                            "Ягодная настойка",
                            "Мутниковая вода",
                            "Тертые растения",
                    };

                    if (item.getType() == Material.GLASS_BOTTLE) {
                        item.setAmount(item.getAmount()-1);
                        event.getPlayer().getInventory().addItem(createPotion(getUserDataCouldron(player), player));
                        // event.getPlayer().getInventory().addItem(createPotion(getUserDataCouldron(player).toArray(new String[getUserDataCouldron(player).size()])));
                        // getServer().broadcastMessage("Create new potion");
                        List<String> data = new ArrayList<String>();
                        setUserDataCouldron(player, data);
                    }
                    else if (item.getItemMeta().hasDisplayName()) {
                        if (Arrays.asList(ingredients).contains(item.getItemMeta().getDisplayName().substring(2))) {
                            addUserDataCouldron(player, item.getItemMeta().getDisplayName().substring(2));
                            item.setAmount(item.getAmount()-1);
                            // getServer().broadcastMessage(item.getItemMeta().getDisplayName().substring(2));
                        }
                    }
                }
            }
        }
    }

    public ItemStack createPotion(List<String> items, String user_name) {
        String[] positive = {
                "REGENERATION",
                "SPEED",
                "FAST_DIGGING",
                "INCREASE_DAMAGE",
                "HEAL",
                "LUCK",
                "SLOW_FALLING",
                "INVISIBILITY",
                "NIGHT_VISION",
                "SATURATION",
        };
        String[] negative = {
                "POISON",
                "SLOW",
                "SLOW_DIGGING",
                "WEAKNESS",
                "HARM",
                "UNLUCK",
                "LEVITATION",
                "GLOWING",
                "BLINDNESS",
                "HUNGER",
        };

        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta potion_meta = (PotionMeta) potion.getItemMeta();
        List<String> name_data = new ArrayList<String>();
        List<Integer> duraction_data = new ArrayList<Integer>();
        List<Integer> level_data = new ArrayList<Integer>();
        // Сделать чтоб составлялось из 2 реагентов
        for (String item : items) {
            File file = new File(getDataFolder() + File.separator + "Ingredients", item + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String effect : config.getKeys(false)) {
                name_data.add((String) config.getConfigurationSection(effect).get("Name"));
                duraction_data.add((int) config.getConfigurationSection(effect).get("Duraction"));
                level_data.add((int) config.getConfigurationSection(effect).get("Level")+1);
            }
        }
        List<String> new_name_data = new ArrayList<String>();
        List<Integer> new_duraction_data = new ArrayList<Integer>();
        List<Integer> new_level_data = new ArrayList<Integer>();
        for (int x = 0; x < name_data.size(); x++) {
            if (!new_name_data.contains(name_data.get(x))) {
                int count_effect = 1;
                for (int y = 0; y < name_data.size(); y++) {
                    if (Objects.equals(name_data.get(x), name_data.get(y)) && y > x && Objects.equals(level_data.get(x), level_data.get(y))) {
                        count_effect += 1;
                    }
                }
                if (count_effect == 1) {
                    new_name_data.add(name_data.get(x));
                    new_duraction_data.add(duraction_data.get(x));
                    new_level_data.add(level_data.get(x));
                } else {
                    new_name_data.add(name_data.get(x));
                    new_duraction_data.add((int) (duraction_data.get(x) * 0.5 * count_effect));
                    new_level_data.add((int) (level_data.get(x) + count_effect/3));
                }
            }
        }
        for (int x = 0; x < new_name_data.size(); x++) {
            boolean remove_effect = false;
            for (int y = 0; y < new_name_data.size(); y++) {
                if (Arrays.asList(positive).contains(new_name_data.get(x)) && y > x) {
                    int index = Arrays.asList(positive).indexOf(new_name_data.get(x));
                    if (Objects.equals(Arrays.asList(negative).get(index), new_name_data.get(y))) {
                        if (new_level_data.get(x) > new_level_data.get(y)) {
                            new_level_data.set(x, new_level_data.get(x) - new_level_data.get(y)-1);
                            new_name_data.set(y, "");
                            break;
                        } else if (new_level_data.get(x) < new_level_data.get(y)) {
                            getServer().broadcastMessage(new_level_data.get(y) + " " + new_level_data.get(x));
                            new_level_data.set(y, new_level_data.get(y) - new_level_data.get(x));
                            remove_effect = true;
                            break;
                        }
                        else {
                            new_name_data.set(y, "");
                            remove_effect = true;
                            break;
                        }
                    }
                }
            }
            if (!remove_effect) {
                if (!new_name_data.get(x).equals("")) {
                    PotionEffect new_effect = new PotionEffect(PotionEffectType.getByName(new_name_data.get(x)), new_duraction_data.get(x), new_level_data.get(x)-1);
                    potion_meta.addCustomEffect(new_effect, true);
                }
            }
        }

        potion_meta.setDisplayName(ChatColor.AQUA + "Зелье " + user_name);
        potion_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        potion_meta.setColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(1, 255), ThreadLocalRandom.current().nextInt(1, 255), ThreadLocalRandom.current().nextInt(1, 255)));
        potion.setItemMeta(potion_meta);
        return potion;
    }

    public List<String> getUserDataCouldron(String user_name) throws IOException {

        File file = new File(getDataFolder()+File.separator+"PlayerData", user_name+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            file.createNewFile();
        }

        List<String> data = (ArrayList<String>) config.get("Input-Couldron");
        return data;
    }

    public void setUserDataCouldron(String user_name, List<String> data) throws IOException {

        File file = new File(getDataFolder()+File.separator+"PlayerData", user_name+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            file.createNewFile();
        }

        config.set("Input-Couldron", data);
        config.save(file);
    }

    public void addUserDataCouldron(String user_name, String line) throws IOException {

        File file = new File(getDataFolder()+File.separator+"PlayerData", user_name+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            file.createNewFile();
        }
        if (config.contains("Input-Couldron")) {
            List<String> data = (ArrayList<String>) config.get("Input-Couldron");
            data.add(line);
            config.set("Input-Couldron", data);
            config.save(file);
        }
        else {
            List<String> data = new ArrayList<String>();
            data.add(line);
            config.set("Input-Couldron", data);
            config.save(file);
        }
    }
}
