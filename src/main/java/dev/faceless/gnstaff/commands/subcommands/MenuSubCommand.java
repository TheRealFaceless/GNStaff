package dev.faceless.gnstaff.commands.subcommands;

import dev.faceless.gnstaff.commands.SubCommand;
import dev.faceless.gnstaff.menus.MainMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.Keys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MenuSubCommand extends SubCommand {
    public MenuSubCommand(JavaPlugin plugin) {
        super(plugin);
        setName("menu");
        setDescription("Opens the main menu for managing players on the server");
        setSyntax("/gnstaff menu");
        setAliases(List.of("m"));
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(player.getPersistentDataContainer().has(Keys.SEARCHING)) {
            player.sendMessage(ChatUtils.format("<red>Please provide a player name."));
        }
        new MainMenu(player, new ArrayList<>(Bukkit.getOnlinePlayers())).open();
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("menu");
        }
        return completions;
    }

}
