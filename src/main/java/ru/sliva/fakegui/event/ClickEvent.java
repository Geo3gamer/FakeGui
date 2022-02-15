package ru.sliva.fakegui.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.fakegui.wrapper.ClickType;

public final class ClickEvent {

    private final Player player;
    private final int slot;
    private final ClickType type;
    private final ItemStack clickedItem;

    public ClickEvent(@NotNull Player player, int slot, @NotNull ClickType type, @NotNull ItemStack clickedItem) {
        this.player = player;
        this.slot = slot;
        this.type = type;
        this.clickedItem = clickedItem;
    }

    /**
     * Returns the player
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the slot
     *
     * @return The slot.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Returns the type of click that was performed
     *
     * @return The type of click.
     */
    public ClickType getClickType() {
        return type;
    }

    /**
     * Get the item that was clicked on.
     *
     * @return The item that was clicked.
     */
    public ItemStack getClickedItem() {
        return clickedItem;
    }
}
