package ru.sliva.fakegui.menu;

import net.minecraft.server.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.packet.PacketReceivedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.fakegui.Main;
import ru.sliva.fakegui.event.ClickEvent;
import ru.sliva.fakegui.wrapper.ClickType;

import java.util.ArrayList;
import java.util.List;

public final class MenuManager implements Listener, Runnable {

    private final List<Menu> menus = new ArrayList<>();
    private final Main plugin;

    private static MenuManager instance;

    public MenuManager(Main plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static MenuManager getInstance() {
        return instance;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Menu menu = getMenuByPlayer(player);
        untrackMenu(menu);
    }

    @EventHandler
    public void onPacketReceiving(@NotNull PacketReceivedEvent event) {
        Packet packet = event.getPacket();
        Player player = event.getPlayer();
        if(packet instanceof Packet102WindowClick) {
            Packet102WindowClick windowClick = (Packet102WindowClick) packet;
            int slot = windowClick.b;
            int rightClick = windowClick.c;
            boolean isShift = windowClick.f;
            ItemStack clicked = new CraftItemStack(windowClick.e);
            Menu menu = getMenuByPlayer(player);
            if(menu != null) {
                event.setCancelled(true);
                if(slot >= 0 && slot < menu.getSize()) {
                    sendSetSlot(player, menu.getWindowID(), slot, menu.getItem(slot));
                    sendSetSlot(player, -1, -1, null);
                    ClickType clickType = ClickType.getClickType(rightClick, isShift);
                    if(clickType.isShift()) {
                        updateInventory(player);
                    }
                    ClickEvent clickEvent = new ClickEvent(player, slot, clickType, clicked);
                    menu.onClick(clickEvent);
                }
            }
        } else if(packet instanceof Packet101CloseWindow) {
            Menu menu = getMenuByPlayer(player);
            if(menu != null) {
                menu.onClose();
                untrackMenu(menu);
                updateInventory(player);
            }
        }
    }

    private Menu getMenuByPlayer(@NotNull Player player) {
        return menus.stream().filter(menu -> menu.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public void openMenu(@NotNull Player player, @NotNull Menu menu) {
        sendWindowOpen(player, menu.getWindowID(), menu.getType().getContainerType().getIndex(), menu.getTitle(), menu.getType().getSlots());
        trackMenu(menu);
    }

    public void closeMenu(@NotNull Player player, @NotNull Menu menu) {
        sendCloseWindow(player, menu.getWindowID());
        untrackMenu(menu);
    }

    private void trackMenu(@NotNull Menu menu) {
        if(!menus.contains(menu)) {
            menus.add(menu);
        }
    }

    private void untrackMenu(@NotNull Menu menu) {
        menus.remove(menu);
    }

    private void updateInventory(@NotNull Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, player::updateInventory);
    }

    @Override
    public void run() {
        for(Menu menu : menus) {
            menu.clear();
            menu.setItems();
            sendWindowItems(menu.getPlayer(), menu.getWindowID(), menu.getItemList());
        }
    }

    private void sendSetSlot(@NotNull Player player, int windowID, int slot, ItemStack item) {
        net.minecraft.server.ItemStack slotData = asNMSCopy(item);
        Packet103SetSlot packet = new Packet103SetSlot(windowID, slot, slotData);
        sendPacket(player, packet);
    }

    private void sendCloseWindow(@NotNull Player player, int windowID) {
        Packet101CloseWindow packet = new Packet101CloseWindow(windowID);
        sendPacket(player, packet);
    }

    private void sendWindowOpen(@NotNull Player player, int windowID, int inventoryType, String title, int slots) {
        Packet100OpenWindow packet = new Packet100OpenWindow(windowID, inventoryType, title, slots);
        sendPacket(player, packet);
    }

    private void sendWindowItems(@NotNull Player player, int windowID, List<ItemStack> items) {
        List<net.minecraft.server.ItemStack> slotData = asNMSCopy(items);
        Packet104WindowItems packet = new Packet104WindowItems(windowID, slotData);
        sendPacket(player, packet);
    }

    private void sendPacket(@NotNull Player player, @NotNull Packet packet) {
        ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(packet);
    }

    private net.minecraft.server.ItemStack asNMSCopy(ItemStack itemStack) {
        return itemStack == null ? null : new net.minecraft.server.ItemStack(itemStack.getTypeId(), itemStack.getAmount(), itemStack.getDurability());
    }

    private @NotNull List<net.minecraft.server.ItemStack> asNMSCopy(@NotNull List<ItemStack> items) {
        List<net.minecraft.server.ItemStack> list = new ArrayList<>();
        for(ItemStack item : items) {
            list.add(asNMSCopy(item));
        }
        return list;
    }
}
