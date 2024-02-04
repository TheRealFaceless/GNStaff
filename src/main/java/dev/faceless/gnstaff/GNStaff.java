package dev.faceless.gnstaff;

import dev.faceless.gnstaff.commands.BaseCommand;
import dev.faceless.gnstaff.configuration.ConfigManager;
import dev.faceless.gnstaff.listeners.*;
import dev.faceless.gnstaff.utilities.moderation.MuteManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GNStaff extends JavaPlugin {
    private static GNStaff plugin;

    public static GNStaff getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        registerCommands();
        registerListeners();
        ConfigManager.getManager().register(this);
        MuteManager.getManager().loadMuteData();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PaginatedMenuListener(), this);
        getServer().getPluginManager().registerEvents(new StaffTypePlayerNameListener(), this);
        getServer().getPluginManager().registerEvents(new MuteListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

    }

    public void registerCommands() {
        getCommand("gnstaff").setExecutor(new BaseCommand(this));
    }
}