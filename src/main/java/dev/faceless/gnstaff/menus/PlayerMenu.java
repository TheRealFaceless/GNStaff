package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.GNStaff;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.moderation.ModerationAction;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerMenu extends Menu {

    public PlayerMenu(Player player) {
        super(27, Component.text(player.getName()));

        setItem(0, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cKick")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.KICK, player, this);
            reasonMenu.open(staff);
        }));

        setItem(1, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cBan")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.BAN, player, this);
            reasonMenu.open(staff);
        }));
        setItem(2, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cIp Ban")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.BAN_IP, player, this);
            reasonMenu.open(staff);
        }));

        setItem(3, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cWarn")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.WARN, player, this);
            reasonMenu.open(staff);
        }));

        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), (staff, event)->
                new MainMenu(GNStaff.getPlayerMenuUtility(player)).open());
    }
}

