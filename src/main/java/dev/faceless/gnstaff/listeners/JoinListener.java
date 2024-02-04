package dev.faceless.gnstaff.listeners;

import dev.faceless.gnstaff.utilities.Keys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(e.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING)){
            e.getPlayer().getPersistentDataContainer().remove(Keys.SEARCHING);
        }
        if(e.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING_MUTED)){
            e.getPlayer().getPersistentDataContainer().remove(Keys.SEARCHING_MUTED);
        }
        if(e.getPlayer().getPersistentDataContainer().has(Keys.SEARCHING_BANNED)){
            e.getPlayer().getPersistentDataContainer().remove(Keys.SEARCHING_BANNED);
        }
    }
}