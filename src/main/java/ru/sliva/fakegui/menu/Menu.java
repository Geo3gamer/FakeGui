package ru.sliva.fakegui.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.fakegui.event.ClickEvent;
import ru.sliva.fakegui.wrapper.InventoryType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Menu {

    private final InventoryType type;
    private final String title;
    private final ItemStack[] items;
    private int windowID;
    private final Player player;

    private static final Random random = new Random();

    public Menu(@NotNull InventoryType type, @NotNull String title, @NotNull Player player) {
        this.type = type;
        this.title = title;
        this.items = new ItemStack[getSize()];
        this.player = player;
    }

    /**
     * Returns the player object
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the type of the inventory
     *
     * @return The type of the inventory.
     */
    public final InventoryType getType() {
        return type;
    }

    /**
     * Return the number of slots in the inventory
     *
     * @return The number of slots in the inventory.
     */
    public final int getSize() {
        return type.getSlots();
    }

    /**
     * Set the item in the given slot to the given item.
     *
     * @param slot The slot to set the item in.
     * @param item The item to set.
     */
    public final void setItem(int slot, @NotNull ItemStack item) {
        if (slot >= 0 && getSize() >= slot) {
            items[slot] = item;
        }
    }

    /**
     * Returns the items in the inventory
     *
     * @return An array of ItemStack objects.
     */
    public ItemStack[] getItems() {
        return items;
    }

    /**
     * Returns a list of all the items in the inventory
     *
     * @return A list of ItemStacks.
     */
    public List<ItemStack> getItemList() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            list.add(getItem(i));
        }
        return list;
    }

    /**
     * Get the item in the specified slot.
     *
     * @param slot The slot number of the item you want to get.
     * @return The item in the slot.
     */
    public ItemStack getItem(int slot) {
        return items[slot];
    }

    /**
     * Clear the items
     */
    public void clear() {
        Arrays.fill(items, null);
    }

    /**
     * It returns the title of the menu.
     *
     * @return The title of the menu.
     */
    public final String getTitle() {
        return title;
    }

    /**
     * Returns the window ID of the window that this object is associated with
     *
     * @return The window ID.
     */
    public final int getWindowID() {
        return windowID;
    }

    /**
     * This function opens the menu
     */
    public final void open() {
        windowID = random.nextInt(26) + 101;
        MenuManager.getInstance().openMenu(player, this);
    }

    /**
     * It closes the menu
     */
    public final void close() {
        MenuManager.getInstance().closeMenu(player, this);
    }

    /**
     * The onClick function is called when the user clicks on the button
     *
     * @param event The event that triggered the click.
     */
    public abstract void onClick(@NotNull ClickEvent event);

    /**
     * This function is called when the user closes the window
     */
    public void onClose() {}

    /**
     * This function sets the items in the list
     */
    public abstract void setItems();
}
