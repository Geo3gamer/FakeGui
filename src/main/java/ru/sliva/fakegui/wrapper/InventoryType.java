package ru.sliva.fakegui.wrapper;

import java.util.Arrays;

public enum InventoryType {

    // Generic chests
    GENERIC_9x1(ContainerType.CHEST, 9),
    GENERIC_9x2(ContainerType.CHEST, 18),
    GENERIC_9x3(ContainerType.CHEST, 27),
    GENERIC_9x4(ContainerType.CHEST, 36),
    GENERIC_9x5(ContainerType.CHEST, 45),
    GENERIC_9x6(ContainerType.CHEST, 54),
    // Tools
    WORKBENCH(ContainerType.WORKBENCH, 9),
    FURNACE(ContainerType.FURNACE, 3),
    DISPENSER(ContainerType.DISPENSER, 9);

    private final ContainerType type;
    private final int slots;

    InventoryType(ContainerType type, int slots) {
        this.type = type;
        this.slots = slots;
    }

    public ContainerType getContainerType() {
        return type;
    }

    public int getSlots() {
        return slots;
    }

    public static InventoryType getInventoryType(int windowType, int slots) {
        return Arrays.stream(values()).filter(inventoryType -> inventoryType.getContainerType().getIndex() == windowType && inventoryType.getSlots() == slots).findFirst().orElse(CHEST);
    }

    public enum ContainerType {
        CHEST(0), WORKBENCH(1), FURNACE(2), DISPENSER(3);

        private final int index;

        ContainerType(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
}
