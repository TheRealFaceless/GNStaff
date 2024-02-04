package dev.faceless.gnstaff.menus.punishmentlist.mutedmembers;

import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.moderation.MuteManager;
import net.kyori.adventure.text.Component;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MutedPlayerMenu extends Menu {

    public MutedPlayerMenu(OfflinePlayer player) {
        super(27, Component.text("Manage " + player.getName()));
        setOpenAction((SoundUtil::MENU_OPEN));
        setCloseAction(SoundUtil::MENU_CLOSE);

        setItem(13, ItemCreator.create(Material.GREEN_CONCRETE, ChatUtils.formatLegacy("&aUnmute")), ((staff, event) -> {
            UnmuteConfirmationMenu unmuteConfirmationMenu = new UnmuteConfirmationMenu(player, this);
            unmuteConfirmationMenu.open(staff);
        }));

        List<Component> reasonLore = new ArrayList<>();
        String reason = MuteManager.getManager().getMuteReason((Player) player).getName();
        reasonLore.add(Component.text(ChatUtils.formatLegacy("&6" + reason)));
        setItem(15, ItemCreator.create(Material.BOOK, Component.text(ChatUtils.formatLegacy("&eReason:")), reasonLore));

        List<Component> createdLore = new ArrayList<>();
        String created = MuteManager.getManager().getMuteDuration((Player) player);
        createdLore.add(Component.text(ChatUtils.formatLegacy("&6" + created)));
        setItem(11, ItemCreator.create(Material.BOOK, Component.text(ChatUtils.formatLegacy("&eDuration:")), createdLore));

        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), ((staff, event) -> {
            List<OfflinePlayer> mutedPlayers = new ArrayList<>(MuteManager.getManager().getMutedPlayers());
            new MutedMainMenu(staff, mutedPlayers).open();
        }));

        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
    }

}
