package dev.faceless.gnstaff.menus.punishmentlist.bannedmembers;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

public class PardonConfirmationMenu extends Menu {
    public PardonConfirmationMenu(OfflinePlayer player, Menu lastMenu) {
        super(9, Component.text("Confirmation Menu"));
        setOpenAction((SoundUtil::MENU_OPEN));
        setCloseAction(SoundUtil::MENU_CLOSE);

        setItem(3, ItemCreator.create(Material.RED_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&cCancel")),
                ((staff, event) -> lastMenu.open(staff)));


        setItem(5, ItemCreator.create(Material.GREEN_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&aConfirm")), ((staff, event) -> {
            Bukkit.getBanList(BanList.Type.NAME).pardon(player.getName());
            staff.closeInventory();
            staff.sendMessage(Component.text(ChatUtils.formatLegacy("&a[GNStaff] " + player.getName() + " was pardoned successfully!")));
        }));

        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);

    }

}
