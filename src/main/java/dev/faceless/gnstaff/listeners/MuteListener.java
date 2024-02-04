package dev.faceless.gnstaff.listeners;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.moderation.MuteManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class MuteListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (MuteManager.getManager().isMuted(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatUtils.format("<red>You are currently muted for " + MuteManager.getManager().getMuteDuration(player)));
        }
    }
}
