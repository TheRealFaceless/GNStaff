package dev.faceless.gnstaff.commands;

import dev.faceless.gnstaff.GNStaff;
import dev.faceless.gnstaff.menus.MainMenu;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MenuSubCommand extends SubCommand {
    public MenuSubCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        new MainMenu(GNStaff.getPlayerMenuUtility(player)).open();
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("menu");
        }
        return completions;
    }

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Opens the main menu for managing players on the server";
    }

    @Override
    public String getSyntax() {
        return "/gnstaff menu";
    }

    @Override
    public String getPermission() {
        return "op";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("m");
        return aliases;
    }
}
