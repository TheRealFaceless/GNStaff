package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.moderation.ModerationAction;
import dev.faceless.gnstaff.utilities.moderation.ModerationManager;
import dev.faceless.gnstaff.utilities.moderation.Reason;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;

public class ConfirmationMenu extends Menu {

    public ConfirmationMenu(Player player, ModerationAction action, Reason reason, Duration duration, Menu lastMenu) {
        super(9, Component.text("Confirmation Menu"));

        setItem(3, ItemCreator.create(Material.RED_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&cCancel")), ((staff, event) -> lastMenu.open(staff)));

        setItem(5, ItemCreator.create(Material.GREEN_STAINED_GLASS_PANE, ChatUtils.formatLegacy("&aConfirm")), ((staff, event) ->
                ModerationManager.getManager().handle(staff, player, action, reason, duration)));

        for (int i = 0; i < 9; i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, ItemCreator.create(Material.GRAY_STAINED_GLASS_PANE, " "));
            }
        }

    }

}
