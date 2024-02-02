package dev.faceless.gnstaff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {

    public static void sendMessage(Player player, String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component component = miniMessage.deserialize(message);
        player.sendMessage(component);
    }

    public static void sendLegacyMessage(Player player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        player.sendMessage(message);
    }

    public static Component format(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }

    public static String formatLegacy(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendConsoleMessage(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component component = miniMessage.deserialize(message);
        Bukkit.getConsoleSender().sendMessage(component);
    }

    public static void sendLegacyConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
