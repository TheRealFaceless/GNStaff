package dev.faceless.gnstaff.listeners;

import dev.faceless.gnstaff.menus.MainMenu;
import dev.faceless.gnstaff.menus.punishmentlist.bannedmembers.BannedMainMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.Keys;
import dev.faceless.gnstaff.utilities.moderation.MuteManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;

public class StaffTypePlayerNameListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(PlayerChatEvent event) {

        if(MuteManager.getManager().isMuted(event.getPlayer())){
            if(event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING)){
                event.getPlayer().getPersistentDataContainer().remove(Keys.SEARCHING);
            }
            if(event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING_BANNED)){
                event.getPlayer().getPersistentDataContainer().remove(Keys.SEARCHING_BANNED);
            }
            return;
        }

        if (!event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING) && !event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING_BANNED))
            return;
        Player player = event.getPlayer();

        event.setCancelled(true);
        String message = event.getMessage();
        message = ChatColor.stripColor(message);
        message = MiniMessage.miniMessage().stripTags(message).strip();
        final String name = message;

        if (event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING)) {
            ArrayList<Player> players = new ArrayList<>();
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter((p) -> p.getName().startsWith(name))
                    .forEach(players::add);

            if (players.isEmpty()) {
                player.sendMessage(ChatUtils.format("<red>Player not found"));
                player.getPersistentDataContainer().remove(Keys.SEARCHING);
                return;
            }
            new MainMenu(player, players).open();
            player.getPersistentDataContainer().remove(Keys.SEARCHING);
        } else if (event.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING_BANNED)) {
            ArrayList<OfflinePlayer> bannedPlayers = new ArrayList<>();
            Bukkit.getBannedPlayers()
                    .stream()
                    .filter((p) -> p.getName().startsWith(name))
                    .forEach(bannedPlayers::add);

            if (bannedPlayers.isEmpty()) {
                player.sendMessage(ChatUtils.format("<red>Player not found"));
                player.getPersistentDataContainer().remove(Keys.SEARCHING_BANNED);
                return;
            }
            new BannedMainMenu(player, bannedPlayers).open();
            player.getPersistentDataContainer().remove(Keys.SEARCHING_BANNED);
        }

    }
}
