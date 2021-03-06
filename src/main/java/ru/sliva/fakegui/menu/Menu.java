package ru.sliva.fakegui.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
    private final UUID playerID;

    private static final Random random = new Random();

    public Menu(@NotNull InventoryType type, @NotNull Component title, @NotNull UUID playerID) {
        this.type = type;
        this.title = title;
        this.items = new ItemStack[getSize()];
        this.playerID = playerID;
    }

    public Menu(@NotNull InventoryType type, @NotNull Component title, @NotNull OfflinePlayer player) {
        this(type, title, player.getUniqueId());
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerID);
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
        MenuManager.getInstance().openMenu(getPlayer(), this);
    }

    public final void close() {
        MenuManager.getInstance().closeMenu(getPlayer(), this);
    }

    public abstract void onClick(@NotNull ClickEvent event);

    public void onClose() {}

    public abstract void setItems();
}
