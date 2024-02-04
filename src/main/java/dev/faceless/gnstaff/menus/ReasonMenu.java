package dev.faceless.gnstaff.menus;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.ItemCreator;
import dev.faceless.gnstaff.utilities.SoundUtil;
import dev.faceless.gnstaff.utilities.menu.Menu;
import dev.faceless.gnstaff.utilities.menu.MenuUtils;
import dev.faceless.gnstaff.utilities.moderation.ModerationAction;
import dev.faceless.gnstaff.utilities.moderation.Reason;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ReasonMenu extends Menu {
    public ReasonMenu(ModerationAction action, Player player, Menu lastMenu) {
        super(27, Component.text("Reason"));
        setOpenAction(SoundUtil::MENU_OPEN);

        Reason[] reasons = Reason.values();

        for (int i = 0; i < reasons.length; i++) {
            Reason reason = reasons[i];

            setItem(i + 9, ItemCreator.create(Material.GREEN_CONCRETE, ChatUtils.formatLegacy("&f" + reason.getName())), (staff, event) -> {
                if (action == ModerationAction.BAN || action == ModerationAction.BAN_IP || action == ModerationAction.MUTE) {
                    DurationMenu durationMenu = new DurationMenu(player, action, reason, this);
                    durationMenu.open(staff);
                } else {
                    ConfirmationMenu confirmationMenu = new ConfirmationMenu(player, action, reason, null, this);
                    confirmationMenu.open(staff);
                }
            });
        }
        setItem(getInventory().getSize() - 1, ItemCreator.create(Material.BARRIER, ChatUtils.formatLegacy("&cBack")), (staff, event) -> lastMenu.open(staff));
        MenuUtils.fill(getInventory(), Material.GRAY_STAINED_GLASS_PANE);
    }
}
