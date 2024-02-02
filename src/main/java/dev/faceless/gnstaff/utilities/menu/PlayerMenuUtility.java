package dev.faceless.gnstaff.utilities.menu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;
    private Player playerToManage;

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public Player getPlayerToManage() {
        return playerToManage;
    }

    public void setPlayerToManage(Player playerToManage) {
        this.playerToManage = playerToManage;
    }
}