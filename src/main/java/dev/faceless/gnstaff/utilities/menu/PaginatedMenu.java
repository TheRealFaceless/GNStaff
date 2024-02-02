package dev.faceless.gnstaff.utilities.menu;

import dev.faceless.gnstaff.utilities.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class PaginatedMenu implements InventoryHolder {
    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;
    protected ItemStack fillerGlass = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
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
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public void addMenuBorder() {
        for (int i = 0; i < 9; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillerGlass);
            }
        }
        inventory.setItem(9, fillerGlass);
        inventory.setItem(18, fillerGlass);
        inventory.setItem(27, fillerGlass);
        inventory.setItem(36, fillerGlass);
        inventory.setItem(17, fillerGlass);
        inventory.setItem(26, fillerGlass);
        inventory.setItem(35, fillerGlass);
        inventory.setItem(44, fillerGlass);
        for (int i = 45; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillerGlass);
            }
        }
        inventory.setItem(48, makeItem(Material.RED_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&c&lLeft")));
        inventory.setItem(49, makeItem(Material.BARRIER, ChatUtils.formatLegacy("&4&lClose")));
        inventory.setItem(50, makeItem(Material.GREEN_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&a&lRight")));
    }
}
