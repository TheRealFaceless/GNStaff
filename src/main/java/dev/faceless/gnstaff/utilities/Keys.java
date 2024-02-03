package dev.faceless.gnstaff.utilities;

import dev.faceless.gnstaff.GNStaff;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
    private static JavaPlugin plugin;

    static {
        plugin = GNStaff.getPlugin();
    }

    public static final NamespacedKey UUID = new NamespacedKey(GNStaff.getPlugin(), "uuid");
    public static final NamespacedKey NEXT_PAGE = new NamespacedKey(plugin, "next_page");
    public static final NamespacedKey PREVIOUS_PAGE = new NamespacedKey(plugin, "previous_page");
    public static final NamespacedKey CLOSE = new NamespacedKey(plugin, "close");

    public static final NamespacedKey SEARCHING = new NamespacedKey(plugin, "searching_for_player");
}
