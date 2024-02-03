package dev.faceless.gnstaff.utilities.menu;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuUtils {
    public static void addBorders(Inventory inventory, Material mat) {
        ItemStack borderItem = new ItemStack(mat);
        int size = inventory.getSize();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, borderItem);
        }
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, borderItem);
        }
        for (int i = 1; i < size / 9 - 1; i++) {
            int leftIndex = i * 9;
            int rightIndex = (i + 1) * 9 - 1;
            inventory.setItem(leftIndex, borderItem);
            inventory.setItem(rightIndex, borderItem);
        }
    }

    public static void addBorders(Inventory inventory, ItemStack borderItem) {
        int size = inventory.getSize();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, borderItem);
        }
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, borderItem);
        }
        for (int i = 1; i < size / 9 - 1; i++) {
            int leftIndex = i * 9;
            int rightIndex = (i + 1) * 9 - 1;
            inventory.setItem(leftIndex, borderItem);
            inventory.setItem(rightIndex, borderItem);
        }
    }
}
