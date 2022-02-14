package ru.sliva.fakegui.wrapper;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;

public enum InventoryType {

    // Generic chests
    GENERIC_9x1(0, 9),
    GENERIC_9x2(1, 18),
    GENERIC_9x3(2, 27),
    GENERIC_9x4(3, 36),
    GENERIC_9x5(4, 45),
    GENERIC_9x6(5, 54),
    GENERIC_3x3(6, 9),
    // Tools
    ANVIL(7, 3),
    BEACON(8, 1),
    BLAST_FURNACE(9, 3),
    BREWING_STAND(10, 5),
    CRAFTING(11, 10),
    ENCHANTMENT(12, 2),
    FURNACE(13, 3),
    GRINDSTONE(14, 3),
    HOPPER(15, 5),
    LECTERN(16, 1),
    LOOM(17, 4),
    MERCHANT(18, 3),
    SHULKER_BOX(19, 27),
    SMITHING(20, 3),
    SMOKER(21, 3),
    CARTOGRAPHY(22, 3),
    STONECUTTER(23, 2);

    private final int index;
    private final int slots;

    InventoryType(int index, int slots) {
        this.index = index;
        this.slots = slots;
    }

    public int getIndex() {
        return index;
    }

    public int getSlots() {
        return slots;
    }

    public @NotNull String getRealName() {
        String name = name().toLowerCase(Locale.ROOT);
        if(name.contains("generic")) {
            name = "chest_" + name.split("_")[1];
        }
        name = name.replace("_", " ");
        return name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1);
    }

    public static InventoryType getByIndex(int index) {
        return Arrays.stream(values()).filter(inventoryType -> inventoryType.getIndex() == index).findFirst().orElse(InventoryType.GENERIC_9x3);
    }
}
