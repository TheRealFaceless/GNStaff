package dev.faceless.gnstaff.utilities.moderation;

public enum Reason {
    HACKING("Hacking"),
    SPAM("Spamming"),
    DISCRIMINATION("Discrimination"),
    TOXICITY("Toxicity"),
    GRIEFING("Griefing"),
    ADVERTISING("Advertising"),
    HARASSMENT("Harassment"),
    EXPLOITING("Exploiting"),
    OFFENSIVE_LANGUAGE("Offensive Language");

    private final String message;

    Reason(String message) {
        this.message = message;
    }

    public String getName(){
        return message;
    }
}
