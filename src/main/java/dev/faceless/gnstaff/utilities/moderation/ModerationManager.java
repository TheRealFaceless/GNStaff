package dev.faceless.gnstaff.utilities.moderation;

import dev.faceless.gnstaff.utilities.ChatUtils;
import dev.faceless.gnstaff.utilities.SoundUtil;
import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModerationManager {
    private static ModerationManager manager;

    public static ModerationManager getManager() {
        return (manager == null) ? new ModerationManager() : manager;
    }

    public void handle(Player staff, Player player, ModerationAction action, Reason reason, Duration duration) {
        try {
            switch (action) {
                case KICK:
                    kickPlayer(staff, player, action, reason);
                    staff.closeInventory();
                    break;
                case BAN:
                    ban(staff, player, action, reason, duration);
                    staff.closeInventory();
                    break;
                case WARN:
                    warnPlayer(staff, player, action, reason);
                    staff.closeInventory();
                    break;
                case BAN_IP:
                    banIp(staff, player, action, reason, duration);
                    staff.closeInventory();
                    break;
                case MUTE:
                    MuteManager.getManager().mutePlayer(player, duration, reason);
                    player.sendMessage(ChatUtils.format("<red>You have been Muted by " + staff.getName() + " for " + reason.getName()));
                    staff.closeInventory();
                    break;
            }
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.format("<dark_red>NullPointerException at ModerationManager, Report to Devs!!!"));
            e.printStackTrace();
        }
    }

    private void kickPlayer(Player staff, Player player, ModerationAction action, Reason reason) {
        player.kick(ChatUtils.format("<red>You have been kicked by "
                + staff.getName() + " for " + reason.getName()
                + "\n <aqua>Join the discord to appeal or complain if you feel like you've been wrongfully kicked"
                + "\n <aqua><u>https://discord.gg/pTQBqd8SVM"));
    }

    private void warnPlayer(Player staff, Player player, ModerationAction action, Reason reason) {
        ChatUtils.sendLegacyMessage(player, "&4You have been warned by " + staff.getName() + " for &8&l[" + reason.getName() + "]");
        SoundUtil.BAN_HAMMER(player);
    }

    private void ban(Player staff, Player player, ModerationAction action, Reason reason, Duration duration) {
        String s = (duration == null) ? "Permanently" : "Temporarily";
        player.ban(ChatUtils.formatLegacy("&c" + s + " banned by " + staff
                .getName() + " for " + reason.getName()
                + "\n &bJoin the discord to appeal or complain if you feel like you've been wrongfully banned"
                + "\n &b&nhttps://discord.gg/pTQBqd8SVM"), duration, null);
    }

    private void banIp(Player staff, Player player, ModerationAction action, Reason reason, Duration duration) {
        String s = (duration == null) ? "Permanently" : "Temporarily";
        player.banIp(ChatUtils.formatLegacy("&c" + s + " ip banned by " + staff
                .getName() + " for " + reason.getName()
                + "\n &bJoin the discord to appeal or complain if you feel like you've been wrongfully banned"
                + "\n &b&nhttps://discord.gg/pTQBqd8SVM"), duration, null, true);
    }
}