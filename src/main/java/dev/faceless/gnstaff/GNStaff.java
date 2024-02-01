package dev.faceless.gnstaff;

import dev.faceless.gnstaff.commands.BaseCommand;
import dev.faceless.gnstaff.listeners.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public class GNStaff extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();

    }

    @Override
    public void onDisable() {

    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public void registerCommands() {
        getCommand("gnstaff").setExecutor(new BaseCommand(this));
    }
}
