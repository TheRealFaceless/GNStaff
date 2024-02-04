package dev.faceless.gnstaff.configuration;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final Map<String, Config> configs = new HashMap<>();
    private static ConfigManager manager;

    public Config getConfig(String id) {
        return this.configs.getOrDefault(id, null);
    }

    public static ConfigManager getManager() {
        return (manager == null) ? (manager = new ConfigManager()) : manager;
    }

    public void register(JavaPlugin plugin) {
        register("muted-config", "mutes.yml", plugin);
    }

    public void register(String id, String folderName, String filename, JavaPlugin plugin) {
        this.configs.putIfAbsent(id, new Config(folderName, filename, plugin));
    }

    public void register(String id, String filename, JavaPlugin plugin) {
        this.configs.putIfAbsent(id, new Config(filename, plugin));
    }

    public void reloadConfigs() {
        for (Map.Entry<String, Config> entry : this.configs.entrySet()) {
            Config config = entry.getValue();
            config.loadConfig();
        }
    }

    public void saveConfigs() {
        for (Map.Entry<String, Config> entry : this.configs.entrySet()) {
            Config config = entry.getValue();
            config.saveConfig();
        }
    }
}