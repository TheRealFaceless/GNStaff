package dev.faceless.gnstaff;

import dev.faceless.gnstaff.commands.BaseCommand;
import dev.faceless.gnstaff.listeners.MenuListener;
import dev.faceless.gnstaff.listeners.PaginatedMenuListener;
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
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PaginatedMenuListener(), this);
    }

    public void registerCommands() {
        getCommand("gnstaff").setExecutor(new BaseCommand(this));
    }
}
