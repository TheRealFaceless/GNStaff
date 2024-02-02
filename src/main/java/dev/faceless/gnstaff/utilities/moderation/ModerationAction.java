package dev.faceless.gnstaff.utilities.moderation;

public enum ModerationAction {
    KICK("Kicked"),
    BAN("Banned"),
    BAN_IP("Ip banned"),
    WARN("Warned");

    private final String verb;

    ModerationAction(String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return verb;
    }
}

