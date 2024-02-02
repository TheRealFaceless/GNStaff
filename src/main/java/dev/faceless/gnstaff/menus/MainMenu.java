package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.GNStaff;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.menu.PaginatedMenu;
import dev.faceless.gnstaff.utilities.menu.PlayerMenuUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class MainMenu extends PaginatedMenu {
    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Staff Player Manager";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        ArrayList<Player> onlinePlayers = new ArrayList<>(getServer().getOnlinePlayers());
        if (!onlinePlayers.isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= onlinePlayers.size()) break;
                if (onlinePlayers.get(index) != null) {
                    Player p = onlinePlayers.get(index);
                    ItemStack playerHead = getHead(p);
                    ItemMeta playerHeadMeta = playerHead.getItemMeta();
                    playerHeadMeta.setDisplayName(ChatUtils.formatLegacy("&d&l" + p.getDisplayName()));
                    playerHeadMeta.getPersistentDataContainer().set(new NamespacedKey(GNStaff.getPlugin(), "uuid"), PersistentDataType.STRING, p.getUniqueId().toString());
                    playerHead.setItemMeta(playerHeadMeta);
                    inventory.addItem(playerHead);
                }
            }
        }
    }

    private ItemStack getHead(Player player) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
        playerHeadMeta.setOwner(player.getName());
        playerHead.setItemMeta(playerHeadMeta);
        return playerHead;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ArrayList<Player> onlinePlayers = new ArrayList<>(getServer().getOnlinePlayers());
        if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {

            // Open player manage menu

        } else if (e.getCurrentItem().getType() == Material.BARRIER) {
            p.closeInventory();
        } else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE || e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtils.formatLegacy("&c&lLeft"))) {
                if (page == 0) {
                    p.sendMessage(Component.text("[GN Staff] You are already on the first page!", NamedTextColor.RED));
                } else {
                    page--;
                    super.open();
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtils.formatLegacy("&a&lRight"))) {
                if (!((index + 1) >= onlinePlayers.size())) {
                    page++;
                    super.open();
                } else {
                    p.sendMessage(Component.text("[GN Staff] You are already on the last page!", NamedTextColor.RED));
                }
            }
        }
    }
}
