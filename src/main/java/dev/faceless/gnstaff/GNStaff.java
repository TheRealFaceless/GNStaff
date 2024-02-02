package dev.faceless.gnstaff;

import dev.faceless.gnstaff.commands.BaseCommand;
import dev.faceless.gnstaff.listeners.MenuListener;
import dev.faceless.gnstaff.listeners.PaginatedMenuListener;
import dev.faceless.gnstaff.utilities.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class GNStaff extends JavaPlugin {
    private static GNStaff plugin;
    private static HashMap<Player, PlayerMenuUtility> playerMenuUtilities = new HashMap<>();

    public static GNStaff getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PaginatedMenuListener(), this);
    }

    public void registerCommands() {
        getCommand("gnstaff").setExecutor(new BaseCommand(this));
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        return playerMenuUtilities.computeIfAbsent(p, PlayerMenuUtility::new);
    }
}
