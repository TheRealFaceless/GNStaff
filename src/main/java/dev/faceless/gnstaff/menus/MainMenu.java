package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.GNStaff;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.Keys;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.PaginatedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class MainMenu extends PaginatedMenu {
    private String search;

    public MainMenu(Player player, String search) {
        super(player);
        this.search = search;
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

        if (!player.getPersistentDataContainer().has(new NamespacedKey(GNStaff.getPlugin(), "is-searching"), PersistentDataType.BOOLEAN))
            return;

        boolean isSearching = player.getPersistentDataContainer().get(new NamespacedKey(GNStaff.getPlugin(), "is-searching"), PersistentDataType.BOOLEAN);

        ArrayList<Player> onlinePlayers = new ArrayList<>(getServer().getOnlinePlayers());
        if (isSearching) {
            ArrayList<Player> searchedPlayers = new ArrayList<>();
            for (Player player : onlinePlayers) {
                String playerName = player.getName();
                String playerDisplayName = player.displayName().toString();
                if (playerName.contains(search) || playerDisplayName.contains(search)) {
                    searchedPlayers.add(player);
                }
            }
            if (!searchedPlayers.isEmpty()) {
                for (int i = 0; i < getMaxItemsPerPage(); i++) {
                    index = getMaxItemsPerPage() * page + i;
                    if (index >= searchedPlayers.size()) break;
                    if (searchedPlayers.get(index) != null) {
                        Player p = searchedPlayers.get(index);
                        ItemStack playerHead = getHead(p);
                        ItemMeta playerHeadMeta = playerHead.getItemMeta();
                        playerHeadMeta.setDisplayName(ChatUtils.formatLegacy("&d&l" + p.getDisplayName()));
                        playerHeadMeta.getPersistentDataContainer().set(Keys.UUID, PersistentDataType.STRING, p.getUniqueId().toString());
                        playerHead.setItemMeta(playerHeadMeta);
                        inventory.addItem(playerHead);
                    }
                }
            }
        } else {
            if (!onlinePlayers.isEmpty()) {
                for (int i = 0; i < getMaxItemsPerPage(); i++) {
                    index = getMaxItemsPerPage() * page + i;
                    if (index >= onlinePlayers.size()) break;
                    if (onlinePlayers.get(index) != null) {
                        Player p = onlinePlayers.get(index);
                        ItemStack playerHead = getHead(p);
                        ItemMeta playerHeadMeta = playerHead.getItemMeta();
                        playerHeadMeta.setDisplayName(ChatUtils.formatLegacy("&d&l" + p.getDisplayName()));
                        playerHeadMeta.getPersistentDataContainer().set(Keys.UUID, PersistentDataType.STRING, p.getUniqueId().toString());
                        playerHead.setItemMeta(playerHeadMeta);
                        inventory.addItem(playerHead);
                    }
                }
            }
        }
    }

    public static ItemStack getHead(Player player) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
        playerHeadMeta.setOwner(player.getName());
        playerHead.setItemMeta(playerHeadMeta);
        return playerHead;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player staff = (Player) e.getWhoClicked();
        ArrayList<Player> onlinePlayers = new ArrayList<>(getServer().getOnlinePlayers());
        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD -> {
                ItemStack playerHead = e.getCurrentItem();
                ItemMeta playerHeadMeta = playerHead.getItemMeta();

                if (!playerHeadMeta.getPersistentDataContainer().has(Keys.UUID, PersistentDataType.STRING)) {
                    return;
                }

                String targetUUID = playerHeadMeta.getPersistentDataContainer().get(Keys.UUID, PersistentDataType.STRING);
                Player player = Bukkit.getPlayer(UUID.fromString(targetUUID));
                if (player == null) return;

                PlayerMenu playerMenu = new PlayerMenu(player);
                SoundUtil.UI_CLICK(staff);
                playerMenu.open(staff);
            }

            case BARRIER -> {
                SoundUtil.UI_BACK(staff);
                staff.closeInventory();
            }

            case RED_STAINED_GLASS_PANE, GREEN_STAINED_GLASS_PANE -> {
                ItemMeta itemMeta = e.getCurrentItem().getItemMeta();

                if (itemMeta.getPersistentDataContainer().has(Keys.PREVIOUS_PAGE)) {
                    if (page == 0) {
                        staff.sendMessage(Component.text("[GN Staff] You are already on the first page!", NamedTextColor.RED));
                    } else {
                        page--;
                        super.open();
                    }
                    SoundUtil.UI_CLICK(staff);
                } else if (itemMeta.getPersistentDataContainer().has(Keys.NEXT_PAGE)) {
                    if (!((index + 1) >= onlinePlayers.size())) {
                        page++;
                        super.open();
                    } else {
                        staff.sendMessage(Component.text("[GN Staff] You are already on the last page!", NamedTextColor.RED));
                    }
                    SoundUtil.UI_CLICK(staff);
                }
            }

            case OAK_SIGN -> {
                player.closeInventory();
                // PDC Logic
            }
        }

    }
}
