package dev.faceless.gnstaff.menus.punishmentlist.bannedmembers;

import dev.faceless.gnstaff.menus.punishmentlist.PunishmentListMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.Keys;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
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

import java.util.List;
import java.util.UUID;

public class BannedMainMenu extends PaginatedMenu {

    public List<OfflinePlayer> bannedPlayers;

    public BannedMainMenu(Player player, List<OfflinePlayer> bannedPlayers) {
        super(player);
        this.bannedPlayers = bannedPlayers;
    }

    @Override
    public String getMenuName() {
        return "Banned Members";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        this.addMenuBorder();
        if (!bannedPlayers.isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= bannedPlayers.size()) break;
                if (bannedPlayers.get(index) != null) {
                    OfflinePlayer p = bannedPlayers.get(index);
                    ItemStack playerHead = getHead(p);
                    ItemMeta playerHeadMeta = playerHead.getItemMeta();
                    playerHeadMeta.setDisplayName(ChatUtils.formatLegacy("&d&l" + p.getName()));
                    playerHeadMeta.getPersistentDataContainer().set(Keys.UUID, PersistentDataType.STRING, p.getUniqueId().toString());
                    playerHead.setItemMeta(playerHeadMeta);
                    inventory.addItem(playerHead);
                }
            }
        }
    }

    public void addMenuBorder() {
        MenuUtils.addBorders(inventory, fillerGlass);
        inventory.setItem(48, ItemCreator.create(Material.RED_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&c&lPrevious Page"), Keys.PREVIOUS_PAGE));
        inventory.setItem(49, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&4&lBack"), Keys.CLOSE));
        inventory.setItem(50, ItemCreator.create(Material.GREEN_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&a&lNext Page"), Keys.NEXT_PAGE));
        inventory.setItem(53, ItemCreator.create(Material.OAK_SIGN, ChatUtils.formatLegacy("&a&lSearch")));
    }

    public static ItemStack getHead(OfflinePlayer player) {
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
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(targetUUID));

                BannedPlayerMenu bannedPlayerMenu = new BannedPlayerMenu(player);
                SoundUtil.UI_CLICK(staff);
                bannedPlayerMenu.open(staff);
            }

            case BARRIER -> {
                new PunishmentListMenu().open(staff);
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
                    if (!((index + 1) >= bannedPlayers.size())) {
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
                staff.getPersistentDataContainer().set(Keys.SEARCHING_BANNED, PersistentDataType.STRING, "");
                staff.sendMessage(ChatUtils.format("<red>Type player name in chat."));
            }
        }

    }
}
