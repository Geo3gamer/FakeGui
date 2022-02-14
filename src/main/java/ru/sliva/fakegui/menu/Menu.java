package ru.sliva.fakegui.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.fakegui.event.ClickEvent;
import ru.sliva.fakegui.wrapper.InventoryType;

import java.util.*;

public abstract class Menu {

    private final InventoryType type;
    private final Component title;
    private final ItemStack[] items;
    private int windowID;
    private final Player player;

    private static final Random random = new Random();

    public Menu(@NotNull InventoryType type, @NotNull Component title, @NotNull Player player) {
        this.type = type;
        this.title = title;
        this.items = new ItemStack[getSize()];
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public final InventoryType getType() {
        return type;
    }

    public final int getSize() {
        return type.getSlots();
    }

    public final void setItem(int slot, @NotNull ItemStack item) {
        if (slot >= 0 && getSize() >= slot) {
            items[slot] = item;
        }
    }

    public ItemStack[] getItems() {
        return items;
    }

    public List<ItemStack> getItemList() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            list.add(getItem(i));
        }
        return list;
    }

    public ItemStack getItem(int slot) {
        ItemStack item = items[slot];
        if (item == null) {
            item = new ItemStack(Material.AIR);
        }
        return item;
    }

    public void clear() {
        Arrays.fill(items, null);
    }

    public final Component getTitle() {
        return title;
    }

    public final int getWindowID() {
        return windowID;
    }

    public final void open() {
        windowID = random.nextInt(26) + 101;
        MenuManager.getInstance().openMenu(player, this);
    }

    public final void close() {
        MenuManager.getInstance().closeMenu(player, this);
    }

    public abstract void onClick(@NotNull ClickEvent event);

    public void onClose() {}

    public abstract void setItems();
}
