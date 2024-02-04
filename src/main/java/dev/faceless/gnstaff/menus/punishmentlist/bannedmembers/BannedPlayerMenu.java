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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BannedPlayerMenu extends Menu {

    public BannedPlayerMenu(OfflinePlayer player) {
        super(27, Component.text("Manage " + player.getName()));
        setOpenAction((SoundUtil::MENU_OPEN));
        setCloseAction(SoundUtil::MENU_CLOSE);

        setItem(13, ItemCreator.create(Material.GREEN_CONCRETE, ChatUtils.formatLegacy("&aPardon")), ((staff, event) -> {
            PardonConfirmationMenu pardonConfirmationMenu = new PardonConfirmationMenu(player, this);
            pardonConfirmationMenu.open(staff);
        }));

        List<Component> reasonLore = new ArrayList<>();
        String reason = getReason(Bukkit.getBanList(BanList.Type.NAME).getBanEntry(player.getName()).getReason());
        reasonLore.add(Component.text(ChatUtils.formatLegacy("&6" + reason)));
        setItem(15, ItemCreator.create(Material.BOOK, Component.text(ChatUtils.formatLegacy("&eReason:")), reasonLore));

        List<Component> createdLore = new ArrayList<>();
        Date created = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(player.getName()).getCreated();
        createdLore.add(Component.text(ChatUtils.formatLegacy("&6" + created)));
        setItem(11, ItemCreator.create(Material.BOOK, Component.text(ChatUtils.formatLegacy("&eDate Punished:")), createdLore));

        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), ((staff, event) -> {
            List<OfflinePlayer> bannedPlayers = Bukkit.getBannedPlayers().stream().toList();
            new BannedMainMenu(staff, bannedPlayers).open();
        }));

        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
    }

    private String getReason(String reason) {
        if (reason.contains("Hacking")) {
            return "Hacking";
        } else if (reason.contains("Spamming")) {
            return "Spamming";
        } else if (reason.contains("Discrimination")) {
            return "Discrimination";
        } else if (reason.contains("Toxicity")) {
            return "Toxicity";
        } else if (reason.contains("Griefing")) {
            return "Griefing";
        } else if (reason.contains("Advertising")) {
            return "Advertising";
        } else if (reason.contains("Harassment")) {
            return "Harassment";
        } else if (reason.contains("Exploiting")) {
            return "Exploiting";
        } else if (reason.contains("Offensive Language")) {
            return "Offensive Language";
        }
        return "(Unknown)";
    }
}

