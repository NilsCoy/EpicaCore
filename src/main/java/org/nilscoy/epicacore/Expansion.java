package org.nilscoy.epicacore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class Expansion extends PlaceholderExpansion {
    private final EpicaCore plugin; // The instance is created in the constructor and won't be modified, so it can be final

    public Expansion(EpicaCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "epica";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return null;
        }
        if (params.contains("array_") && !params.contains("len")) {
            String array_name = params.split("_")[1];
            int id = Integer.parseInt(params.split("_")[2]);
            String serverDataArray = "Error";
            try {
                serverDataArray = plugin.getServerDataArray(array_name, id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return serverDataArray;
        }
        if (params.contains("len_array_")) {
            String array_name = params.split("_")[2];
            int getServerDataArrayLength = -1;
            try {
                getServerDataArrayLength = plugin.getServerDataArrayLength(array_name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return String.valueOf(getServerDataArrayLength);
        }
        //return super.onRequest(player, params);
        return null;
    }
}
