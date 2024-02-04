package dev.faceless.gnstaff.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private File configFile;
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFolder;

    public Config(String folderName, String filename, JavaPlugin plugin) {
        this.plugin = plugin;
        init(folderName, filename);
    }

    public Config(String filename, JavaPlugin plugin) {
        this.plugin = plugin;
        init(null, filename);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public File getConfigFolder() {
        return this.configFolder;
    }

    private void init(String folderName, String filename) {
        if (!this.plugin.getDataFolder().exists())
            this.plugin.getDataFolder().mkdir();
        File dataFolder = this.plugin.getDataFolder();
        if (folderName == null) {
            if (!filename.toLowerCase().endsWith(".yml"))
                filename = filename.concat(".yml");
            this.configFile = new File(dataFolder, filename);
            if (!this.configFile.exists())
                try {
                    this.configFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("Failed to create Configuration File: " + this.configFile, e);
                }
            this.configFolder = dataFolder;
        } else {
            File configFolder = new File(dataFolder, folderName);
            if (!configFolder.exists())
                configFolder.mkdir();
            if (!filename.toLowerCase().endsWith(".yml"))
                filename = filename.trim().concat(".yml");
            this.configFile = new File(configFolder, filename);
            if (!this.configFile.exists())
                try {
                    this.configFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("Failed to create Configuration File: " + this.configFile, e);
                }
            this.configFolder = configFolder;
        }
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void saveConfig() {
        try {
            FileWriter writer = new FileWriter(this.configFile);
            try {
                this.config.save(this.configFile);
                writer.close();
            } catch (Throwable throwable) {
                try {
                    writer.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {
        try {
            this.config.load(this.configFile);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            throw new RuntimeException("Failed to save configuration: " + this.configFile, e);
        }
    }
}