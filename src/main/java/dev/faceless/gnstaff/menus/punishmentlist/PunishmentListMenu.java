package dev.faceless.gnstaff.menus.punishmentlist;

import dev.faceless.gnstaff.menus.MainMenu;
import dev.faceless.gnstaff.menus.punishmentlist.bannedmembers.BannedMainMenu;
import dev.faceless.gnstaff.menus.punishmentlist.mutedmembers.MutedMainMenu;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
import dev.faceless.gnstaff.utilities.moderation.MuteManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class PunishmentListMenu extends Menu {
    public PunishmentListMenu() {
        super(27, Component.text("Punished Members Lists"));
        setOpenAction((SoundUtil::MENU_OPEN));
        setCloseAction(SoundUtil::MENU_CLOSE);

        setItem(12, ItemCreator.create(Material.WRITABLE_BOOK, ChatUtils.formatLegacy("&cBanned Players")), ((staff, event) -> {
            List<OfflinePlayer> bannedPlayers = Bukkit.getBannedPlayers().stream().toList();
            new BannedMainMenu(staff, bannedPlayers).open();
        }));

        setItem(14, ItemCreator.create(Material.WRITABLE_BOOK, ChatUtils.formatLegacy("&cMuted Players")), ((staff, event) -> {
            List<OfflinePlayer> mutedPlayers = new ArrayList<>(MuteManager.getManager().getMutedPlayers());
            new MutedMainMenu(staff, mutedPlayers).open();
        }));

        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), ((staff, event) -> {
            new MainMenu(staff, new ArrayList<>(Bukkit.getOnlinePlayers())).open();
        }));

        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
    }
}
