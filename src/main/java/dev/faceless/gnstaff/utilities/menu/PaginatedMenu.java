package dev.faceless.gnstaff.utilities.menu;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu implements InventoryHolder {
    protected Player player;
    protected Inventory inventory;
    protected ItemStack fillerGlass = ItemCreator.createNameless(Material.GRAY_STAINED_GLASS_PANE);
    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(Player player) {
        this.player = player;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public abstract void setMenuItems();

    public abstract void handleMenu(InventoryClickEvent e);

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        player.openInventory(inventory);
    }

    public void addMenuBorder() {
        MenuUtils.addBorders(inventory, fillerGlass);
        inventory.setItem(48, ItemCreator.create(Material.RED_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&c&lPrevious Page"), Keys.PREVIOUS_PAGE));
        inventory.setItem(49, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&4&lClose"), Keys.CLOSE));
        inventory.setItem(50, ItemCreator.create(Material.GREEN_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&a&lNext Page"), Keys.NEXT_PAGE));
        inventory.setItem(53, ItemCreator.create(Material.OAK_SIGN, ChatUtils.formatLegacy("&a&lSearch")));
    }
}
