package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
import dev.faceless.gnstaff.utilities.moderation.ModerationAction;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerMenu extends Menu {
    public PlayerMenu(Player player) {
        super(27, Component.text("Manage " + player.getName()));
        setOpenAction(SoundUtil::MENU_OPEN);

        setItem(9, ItemCreator.create(Material.YELLOW_CONCRETE, ChatUtils.formatLegacy("&eWarn")), (staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.WARN, player, this);
            reasonMenu.open(staff);
        });
        setItem(10, ItemCreator.create(Material.YELLOW_CONCRETE, ChatUtils.formatLegacy("&eKick")), (staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.KICK, player, this);
            reasonMenu.open(staff);
        });
        setItem(11, ItemCreator.create(Material.ORANGE_CONCRETE, ChatUtils.formatLegacy("&6Mute")), (staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.MUTE, player, this);
            reasonMenu.open(staff);
        });
        setItem(12, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cBan")), (staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.BAN, player, this);
            reasonMenu.open(staff);
        });
        setItem(13, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&4Ip Ban")), (staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.BAN_IP, player, this);
            reasonMenu.open(staff);
        });
        setItem(17, ItemCreator.create(Material.PURPLE_CONCRETE, ChatUtils.formatLegacy("&5Tp")), (staff, event) -> {
            TPConfirmationMenu confirmationMenu = new TPConfirmationMenu(player, this);
            confirmationMenu.open(staff);
        });

        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")),
                (staff, event) -> {
                    new MainMenu(staff, new ArrayList<>(Bukkit.getOnlinePlayers())).open();
                    SoundUtil.UI_CLICK(staff);
                });

        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
    }
}
