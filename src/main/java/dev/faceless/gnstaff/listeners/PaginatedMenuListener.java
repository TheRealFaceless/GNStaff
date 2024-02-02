package dev.faceless.gnstaff.listeners;

import dev.faceless.gnstaff.utilities.menu.PaginatedMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class PaginatedMenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof PaginatedMenu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            PaginatedMenu paginatedMenu = (PaginatedMenu) holder;
            paginatedMenu.handleMenu(e);
        }
    }
}
