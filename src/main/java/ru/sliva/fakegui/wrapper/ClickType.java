package ru.sliva.fakegui.wrapper;

import java.util.Arrays;

public enum ClickType {

    LEFT(0, false),
    RIGHT(1, false),

    SHIFT_LEFT(0, true),
    SHIFT_RIGHT(1, true);

    private final int rightClick;
    private final boolean isShift;

    ClickType(int rightClick, boolean isShift) {
        this.rightClick = rightClick;
        this.isShift = isShift;
    }

    public int getRightClick() {
        return rightClick;
    }

    public boolean isShift() {
        return isShift;
    }

    public static ClickType getClickType(int rightClick, boolean isShift) {
        return Arrays.stream(values()).filter(clickType -> clickType.getRightClick() == rightClick && clickType.isShift() == isShift).findFirst().orElse(ClickType.LEFT);
    }
}
