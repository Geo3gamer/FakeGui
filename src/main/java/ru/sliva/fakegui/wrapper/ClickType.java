package ru.sliva.fakegui.wrapper;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public enum ClickType {

    LEFT(Mode.PICKUP, 0),
    RIGHT(Mode.PICKUP, 1),

    SHIFT_LEFT(Mode.QUICK_MOVE, 0),
    SHIFT_RIGHT(Mode.QUICK_MOVE, 1),

    NUMBER_KEY_1(Mode.SWAP, 0),
    NUMBER_KEY_2(Mode.SWAP, 1),
    NUMBER_KEY_3(Mode.SWAP, 2),
    NUMBER_KEY_4(Mode.SWAP, 3),
    NUMBER_KEY_5(Mode.SWAP, 4),
    NUMBER_KEY_6(Mode.SWAP, 5),
    NUMBER_KEY_7(Mode.SWAP, 6),
    NUMBER_KEY_8(Mode.SWAP, 7),
    NUMBER_KEY_9(Mode.SWAP, 8),
    SWAP_KEY(Mode.SWAP, 40),

    MIDDLE(Mode.CLONE, 2),

    DROP_KEY(Mode.THROW, 0),
    CONTROL_DROP_KEY(Mode.THROW, 1),

    STARTING_LEFT_DRAG(Mode.QUICK_CRAFT, 0),
    STARTING_RIGHT_DRAG(Mode.QUICK_CRAFT, 4),
    STARTING_MIDDLE_DRAG(Mode.QUICK_CRAFT, 8),
    ADD_SLOT_LEFT_DRAG(Mode.QUICK_CRAFT, 1),
    ADD_SLOT_RIGHT_DRAG(Mode.QUICK_CRAFT, 5),
    ADD_SLOT_MIDDLE_DRAG(Mode.QUICK_CRAFT, 9),
    ENDING_LEFT_DRAG(Mode.QUICK_CRAFT, 2),
    ENDING_RIGHT_DRAG(Mode.QUICK_CRAFT, 6),
    ENDING_MIDDLE_DRAG(Mode.QUICK_CRAFT, 10),

    DOUBLE(Mode.PICKUP_ALL, 0);

    private final int button;
    private final Mode mode;

    ClickType(@NotNull Mode mode, int button) {
        this.button = button;
        this.mode = mode;
    }

    public int getButton() {
        return button;
    }

    public Mode getMode() {
        return mode;
    }

    public static ClickType getClickType(int button, Mode mode) {
        return Arrays.stream(values()).filter(clickType -> clickType.getButton() == button && clickType.getMode() == mode).findFirst().orElse(ClickType.LEFT);
    }

    public enum Mode {
        PICKUP, QUICK_MOVE, SWAP, CLONE, THROW, QUICK_CRAFT, PICKUP_ALL
    }
}
