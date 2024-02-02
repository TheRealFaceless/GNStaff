package dev.faceless.gnstaff.utilities.moderation;

import dev.faceless.gnstaff.utilities.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.time.Duration;

public class ModerationManager{

    private static ModerationManager manager;

    public static ModerationManager getManager() {
        return manager == null ? new ModerationManager() : manager;
    }

    public void handle(Player staff, Player player, ModerationAction action, Reason reason, Duration duration) {
        switch (action) {
            case KICK -> {
                kickPlayer(staff, player, action, reason);
                staff.closeInventory();
            }
            case BAN -> {
                ban(staff, player, action, reason, duration);
                staff.closeInventory();
            }
            case WARN -> {
                warnPlayer(staff, player, action, reason);
                staff.closeInventory();
            }
            case BAN_IP -> {
                banIp(staff, player, action, reason, duration);
                staff.closeInventory();
            }
        }
    }

    private void kickPlayer(Player staff, Player player, ModerationAction action, Reason reason) {
        player.kick(ChatUtils.format(
                "<red>You have been kicked by " +staff.getName()+ " for "
                    + reason.getName()
                + "\n <aqua>Join the discord to appeal or complain if you feel like you've been wrongfully kicked"
                + "\n <aqua><u>https://discord.gg/pTQBqd8SVM"));
    }

    private void warnPlayer(Player staff, Player player, ModerationAction action, Reason reason) {
        ChatUtils.sendLegacyMessage(player, "&4You have been warned by " +staff.getName()+ " for " +"&8&l["+ reason.getName()+"]");
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1,1);
    }

    private void ban(Player staff, Player player, ModerationAction action, Reason reason, Duration duration) {
        String s = duration == null ? "Permanently" : "Temporarily";
        player.ban(ChatUtils.formatLegacy(
                "&c" +s+ " banned by " +staff.getName()+ " for "
                        + reason.getName()
                        + "\n &bJoin the discord to appeal or complain if you feel like you've been wrongfully banned"
                        + "\n &b&nhttps://discord.gg/pTQBqd8SVM"), duration, null);
    }

    private void banIp(Player staff, Player player, ModerationAction action, Reason reason, Duration duration) {
        String s = duration == null ? "Permanently" : "Temporarily";
        player.banIp(ChatUtils.formatLegacy(
                "&c" +s+ " ip banned by " +staff.getName()+ " for "
                        + reason.getName()
                        + "\n &bJoin the discord to appeal or complain if you feel like you've been wrongfully banned"
                        + "\n &b&nhttps://discord.gg/pTQBqd8SVM"), duration, null, true);
    }
}
