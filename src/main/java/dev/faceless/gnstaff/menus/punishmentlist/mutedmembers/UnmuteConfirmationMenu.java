package dev.faceless.gnstaff.menus.punishmentlist.mutedmembers;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
import dev.faceless.gnstaff.utilities.moderation.MuteManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UnmuteConfirmationMenu extends Menu {
    public UnmuteConfirmationMenu(OfflinePlayer player, Menu lastMenu) {
        super(9, Component.text("Confirmation Menu"));
        setOpenAction((SoundUtil::MENU_OPEN));

        setItem(3, ItemCreator.create(Material.RED_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&cCancel")),
                ((staff, event) -> lastMenu.open(staff)));
        setItem(5, ItemCreator.create(Material.GREEN_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&aConfirm")), ((staff, event) -> {
            MuteManager.getManager().unmutePlayer((Player) player);
            staff.closeInventory();
            staff.sendMessage(Component.text(ChatUtils.formatLegacy("&a[GNStaff] " + player.getName() + " was unmuted successfully!")));
        }));

        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
    }
}
