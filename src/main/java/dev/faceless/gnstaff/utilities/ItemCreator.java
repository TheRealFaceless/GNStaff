package dev.faceless.gnstaff.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemCreator {
    public static ItemStack create(Material mat) {
        return new ItemStack(mat);
    }

    public static ItemStack createNameless(Material mat) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(""));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, Component name){
        ItemStack item = create(mat);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.displayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, String name){
        ItemStack item = create(mat);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, Component name, NamespacedKey key) {
        ItemStack itemStack = create(mat);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        if (key != null) {
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "default");
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material mat, String name, NamespacedKey key) {
        ItemStack itemStack = create(mat);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        if (key != null) {
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "default");
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material mat, Component name, List<Component> lore){
        ItemStack item = create(mat, name);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, Component name, List<Component> lore, boolean hideFlags){
        ItemStack item = create(mat, name, lore);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        if(hideFlags) meta.addItemFlags(ItemFlag.values());
        else meta.removeItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, Component name, List<Component> lore, boolean hideFlags, boolean isGlowing){
        ItemStack item = create(mat, name, lore, hideFlags);
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        if(isGlowing){
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return item;
    }
}
