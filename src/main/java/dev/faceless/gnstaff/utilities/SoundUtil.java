package dev.faceless.gnstaff.utilities;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void UI_CLICK(Player player){
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
    }

    public static void UI_BACK(Player player){
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }

    public static void MENU_OPEN(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }

    public static void MENU_CLOSE(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }

    public static void BAN_HAMMER(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
    }



    public static void playSound(Player player, Sound sound){
        player.playSound(player.getLocation(), sound, 1, 1);
    }
}
