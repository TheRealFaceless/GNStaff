package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.menus.punishmentlist.PunishmentListMenu;
import dev.faceless.gnstaff.menus.punishmentlist.bannedmembers.BannedMainMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.Keys;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.PaginatedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenu extends PaginatedMenu {

    public ArrayList<Player> onlinePlayers;

    public MainMenu(Player player, ArrayList<Player> onlinePlayers) {
        super(player);
        this.onlinePlayers = onlinePlayers;
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
