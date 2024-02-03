package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.GNStaff;
import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.moderation.ModerationAction;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerMenu extends Menu {

    public PlayerMenu(Player player) {
        super(27, Component.text("Manage " + player.getName()));

        setItem(10, ItemCreator.create(Material.ORANGE_CONCRETE, ChatUtils.formatLegacy("&6Kick")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.KICK, player, this);
            reasonMenu.open(staff);
        }));

        setItem(12, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cBan")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.BAN, player, this);
            reasonMenu.open(staff);
        }));
        setItem(14, ItemCreator.create(Material.RED_CONCRETE, ChatUtils.formatLegacy("&cIp Ban")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.BAN_IP, player, this);
            reasonMenu.open(staff);
        }));

        setItem(16, ItemCreator.create(Material.YELLOW_CONCRETE, ChatUtils.formatLegacy("&eWarn")), ((staff, event) -> {
            ReasonMenu reasonMenu = new ReasonMenu(ModerationAction.WARN, player, this);
            reasonMenu.open(staff);
        }));

        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), (staff, event)->
                new MainMenu(player).open());

        for (int i = 0; i < 27; i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, ItemCreator.create(Material.GRAY_STAINED_GLASS_PANE, " "));
            }
        }
    }
}

