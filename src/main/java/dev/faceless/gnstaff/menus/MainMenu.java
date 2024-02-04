package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.menus.punishmentlist.PunishmentListMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.HeadUtils;
import dev.faceless.gnstaff.utilities.Keys;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class MainMenu extends PaginatedMenu {
    private ArrayList<Player> players;

    public MainMenu(Player player, ArrayList<Player> onlinePlayers) {
        super(player);
        this.players = onlinePlayers;
    }

    @Override
    public String getMenuName() {
        return "Player Manager";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        if (!players.isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= players.size()) break;
                if (players.get(index) != null) {
                    Player p = players.get(index);
                    ItemStack playerHead = HeadUtils.getHead(p);
                    ItemMeta playerHeadMeta = playerHead.getItemMeta();
                    playerHeadMeta.setDisplayName(ChatUtils.formatLegacy("&d&l" + p.getDisplayName()));
                    playerHeadMeta.getPersistentDataContainer().set(Keys.UUID, PersistentDataType.STRING, p.getUniqueId().toString());
                    playerHead.setItemMeta(playerHeadMeta);
                    inventory.addItem(playerHead);
                }
            }
        }
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player staff = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD -> {
                ItemStack playerHead = e.getCurrentItem();
                ItemMeta playerHeadMeta = playerHead.getItemMeta();
                if (!playerHeadMeta.getPersistentDataContainer().has(Keys.UUID, PersistentDataType.STRING)) return;
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
                    goPreviousPage(staff);
                    SoundUtil.UI_CLICK(staff);
                } else if (itemMeta.getPersistentDataContainer().has(Keys.NEXT_PAGE)) {
                    goNextPage(staff, players);
                    SoundUtil.UI_CLICK(staff);
                }
            }

            case OAK_SIGN -> {
                staff.closeInventory();
                staff.getPersistentDataContainer().set(Keys.SEARCHING, PersistentDataType.STRING, "");
                staff.sendMessage(ChatUtils.format("<red>Type player name in chat."));
            }

            case WRITABLE_BOOK -> {
                new PunishmentListMenu().open(staff);
            }
        }
    }
}