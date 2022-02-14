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

    public Player getPlayer() {
        return player;
    }

    public int getSlot() {
        return slot;
    }

    public ClickType getClickType() {
        return type;
    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }
}
