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

    /**
     * Returns 1 if the right mouse button was clicked, 0 if the left mouse button was clicked.
     *
     * @return The number
     */
    public int getRightClick() {
        return rightClick;
    }

    /**
     * Returns true if the shift key is pressed
     *
     * @return A boolean value.
     */
    public boolean isShift() {
        return isShift;
    }

    /**
     * Given a right click and shift status, return the corresponding click type
     *
     * @param rightClick 1 if the right mouse button was clicked, 0 if the left mouse button was clicked.
     * @param isShift true if the shift key is pressed, false otherwise
     * @return The first click type that matches the right click and shift parameters.
     */
    public static ClickType getClickType(int rightClick, boolean isShift) {
        return Arrays.stream(values()).filter(clickType -> clickType.getRightClick() == rightClick && clickType.isShift() == isShift).findFirst().orElse(ClickType.LEFT);
    }
}
