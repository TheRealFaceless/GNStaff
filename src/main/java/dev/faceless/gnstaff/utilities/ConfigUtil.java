package dev.faceless.gnstaff.utilities;

import dev.faceless.gnstaff.configuration.Config;
import dev.faceless.gnstaff.configuration.ConfigManager;

public class ConfigUtil {
    public static Config MUTES = ConfigManager.getManager().getConfig("muted-config");
}
