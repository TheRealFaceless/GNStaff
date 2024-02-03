package dev.faceless.gnstaff.listeners;

import dev.faceless.gnstaff.menus.MainMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.Keys;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;

public class StaffTypePlayerNameListener implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if(!event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING)) return;
        Player player = event.getPlayer();

        event.setCancelled(true);
        String message = event.getMessage();
        message = ChatColor.stripColor(message);
        message = MiniMessage.miniMessage().stripTags(message).strip();
        final String name = message;

        ArrayList<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers()
                .stream()
                .filter((p)-> player.getName().startsWith(name))
                .forEach(players::add);

        if(players.isEmpty()){
            player.sendMessage(ChatUtils.format("<red>Player not found"));
            player.getPersistentDataContainer().remove(Keys.SEARCHING);
            return;
        }
        new MainMenu(player, players).open();
        player.getPersistentDataContainer().remove(Keys.SEARCHING);

    }
}
