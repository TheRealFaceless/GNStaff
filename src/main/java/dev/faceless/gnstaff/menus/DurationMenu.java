package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.moderation.ModerationAction;
import dev.faceless.gnstaff.utilities.moderation.Reason;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;

public class DurationMenu extends Menu {
    public DurationMenu(Player player, ModerationAction action, Reason reason, Menu lastMenu) {
        super(27, Component.text("Duration"));

        setItem(9, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f30 MINUTES")), (staff, event)-> {
            Duration duration = Duration.ofMinutes(30);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(10, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f1 HOUR")), (staff, event)-> {
            Duration duration = Duration.ofHours(1);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(11, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f12 HOURS")), (staff, event)-> {
            Duration duration = Duration.ofHours(12);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(12, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f1 DAY")), (staff, event)-> {
            Duration duration = Duration.ofDays(1);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(13, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f1 WEEK")), (staff, event)-> {
            Duration duration = Duration.ofDays(7);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(14, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f1 MONTH")), (staff, event)-> {
            Duration duration = Duration.ofDays(30);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(15, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f3 MONTHS")), (staff, event)-> {
            Duration duration = Duration.ofDays(90);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(16, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f6 MONTHS")), (staff, event)-> {
            Duration duration = Duration.ofDays(180);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });

        setItem(17, ItemCreator.create(Material.CLOCK, ChatUtils.formatLegacy("&f1 YEAR")), (staff, event)-> {
            Duration duration = Duration.ofDays(365);
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, duration, this);
            menu.open(staff);
        });
        setItem(18, ItemCreator.create(Material.ANVIL, ChatUtils.formatLegacy("&4PERMANENT")), (staff, event)-> {
            ConfirmationMenu menu = new ConfirmationMenu(player, action, reason, null, this);
            menu.open(staff);
        });
        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), (staff, event)->
                lastMenu.open(staff));

        for (int i = 0; i < 27; i++) {
            if (getInventory().getItem(i) == null) {
                setItem(i, ItemCreator.create(Material.GRAY_STAINED_GLASS_PANE, " "));
            }
        }
    }
}
