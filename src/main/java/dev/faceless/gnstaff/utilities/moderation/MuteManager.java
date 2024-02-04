package dev.faceless.gnstaff.utilities.moderation;

import dev.faceless.gnstaff.configuration.Config;
import dev.faceless.gnstaff.utilities.ConfigUtil;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MuteManager {
    private final Map<UUID, Instant> muteMap = new HashMap<>();
    private final FileConfiguration config = ConfigUtil.MUTES.getConfig();
    private final Config configuration = ConfigUtil.MUTES;

    private static MuteManager manager;

    private MuteManager() {
        queryAndDelete();
    }

    public static MuteManager getManager() {
        return (manager == null) ? (manager = new MuteManager()) : manager;
    }

    public void mutePlayer(Player player, Duration duration, Reason reason) {
        Instant expirationTime = duration.equals(Duration.ofSeconds(Long.MAX_VALUE)) ?
                Instant.ofEpochMilli(Long.MAX_VALUE) : Instant.now().plus(duration);

        config.set(player.getUniqueId() + ".duration", expirationTime.toEpochMilli());
        config.set(player.getUniqueId() + ".reason", reason.toString());
        muteMap.put(player.getUniqueId(), expirationTime);
        configuration.saveConfig();
    }

    public void unmutePlayer(Player player) {
        config.set(player.getUniqueId().toString(), null);
        muteMap.remove(player.getUniqueId());
        configuration.saveConfig();
    }

    public boolean isMuted(Player player) {
        if (muteMap.containsKey(player.getUniqueId())) {
            Instant expirationTime = Instant.ofEpochMilli(config.getLong(player.getUniqueId() + ".duration"));
            return Instant.now().isBefore(expirationTime);
        }
        return false;
    }

    public void loadMuteData() {
        for (String key : config.getKeys(false)) {
            UUID playerUUID = UUID.fromString(key);
            long expirationTimeMillis = config.getLong(key + ".duration");
            Instant expirationTime = Instant.ofEpochMilli(expirationTimeMillis);
            if (Instant.now().isBefore(expirationTime))
                muteMap.put(playerUUID, expirationTime);
        }
    }

    public Collection<Player> getMutedPlayers() {
        List<Player> mutedPlayers = new ArrayList<>();
        for (UUID playerUUID : muteMap.keySet()) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null && player.isOnline())
                mutedPlayers.add(player);
        }
        return mutedPlayers;
    }

    public Reason getMuteReason(Player player) {
        if (config.contains(player.getUniqueId() + ".reason")) {
            String reasonString = config.getString(player.getUniqueId() + ".reason");
            return Reason.valueOf(reasonString);
        }
        return null;
    }

    public String getMuteDuration(Player player) {
        if (muteMap.containsKey(player.getUniqueId())) {
            Instant expirationTime = Instant.ofEpochMilli(config.getLong(player.getUniqueId() + ".duration"));
            Duration remainingDuration = Duration.between(Instant.now(), expirationTime);

            long years = remainingDuration.toDays() / 365L;
            long days = remainingDuration.toDays() % 365L;
            long hours = remainingDuration.toHours() % 24L;
            long minutes = remainingDuration.toMinutes() % 60L;
            long seconds = remainingDuration.getSeconds() % 60L;

            StringBuilder durationString = new StringBuilder();

            if (years > 0L)
                durationString.append(years).append(" years, ");
            if (days > 0L)
                durationString.append(days).append(" days, ");
            if (hours > 0L)
                durationString.append(hours).append(" hours, ");
            if (minutes > 0L)
                durationString.append(minutes).append(" minutes, ");
            if (seconds > 0L || durationString.isEmpty())
                durationString.append(seconds).append(" seconds");
            return durationString.toString();
        }
        return "";
    }

    public void queryAndDelete() {
        Set<UUID> playersToRemove = new HashSet<>();
        for (Map.Entry<UUID, Instant> entry : muteMap.entrySet()) {
            UUID playerUUID = entry.getKey();
            Instant expirationTime = entry.getValue();
            if (Instant.now().isAfter(expirationTime))
                playersToRemove.add(playerUUID);
        }
        for (UUID playerUUID : playersToRemove) {
            muteMap.remove(playerUUID);
            config.set(playerUUID.toString(), null);
        }
        configuration.saveConfig();
    }
}
