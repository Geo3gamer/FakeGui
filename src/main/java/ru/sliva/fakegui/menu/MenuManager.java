package ru.sliva.fakegui.menu;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.AdventureComponentConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.fakegui.CustomMenu;
import ru.sliva.fakegui.Main;
import ru.sliva.fakegui.event.ClickEvent;
import ru.sliva.fakegui.wrapper.ClickType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class MenuManager extends PacketAdapter implements Listener, Runnable {

    private final List<Menu> menus = new ArrayList<>();
    private final Main plugin;

    private static MenuManager instance;

    public MenuManager(Main plugin) {
        super(plugin, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CLOSE_WINDOW);
        this.plugin = plugin;
        instance = this;
    }

    public static MenuManager getInstance() {
        return instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Menu menu = new CustomMenu(player);
        menu.open();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Menu menu = getMenuByPlayer(player);
        untrackMenu(menu);
    }

    @Override
    public void onPacketReceiving(@NotNull PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();
        if(event.getPacketType() == PacketType.Play.Client.WINDOW_CLICK) {
            int windowID = packet.getIntegers().read(0);
            int slot = packet.getIntegers().read(2);
            int button = packet.getIntegers().read(3);
            ClickType.Mode mode = packet.getEnumModifier(ClickType.Mode.class, 4).read(0);
            ItemStack clicked = packet.getItemModifier().read(0);
            Menu menu = getMenuByWindow(windowID);
            if(menu != null) {
                event.setReadOnly(false);
                event.setCancelled(true);
                if(slot >= 0 && slot < menu.getSize()) {
                    setSlot(player, menu.getWindowID(), menu.getItem(slot), slot);
                    setSlot(player, -1, new ItemStack(Material.AIR), -1);
                    if(mode == ClickType.Mode.SWAP || mode == ClickType.Mode.QUICK_MOVE) {
                        updateInventory(player);
                    }
                    ClickType clickType = ClickType.getClickType(button, mode);
                    ClickEvent clickEvent = new ClickEvent(player, slot, clickType, clicked);
                    menu.onClick(clickEvent);
                }
            }
        } else if(event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW) {
            int windowID = packet.getIntegers().read(0);
            Menu menu = getMenuByWindow(windowID);
            if(menu != null) {
                menu.onClose();
                untrackMenu(menu);
                updateInventory(player);
            }
        }
    }

    private Menu getMenuByWindow(int windowID) {
        return menus.stream().filter(menu -> menu.getWindowID() == windowID).findFirst().orElse(null);
    }

    private Menu getMenuByPlayer(@NotNull Player player) {
        return menus.stream().filter(menu -> menu.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public void openMenu(@NotNull Player player, @NotNull Menu menu) {
        PacketContainer open = new PacketContainer(PacketType.Play.Server.OPEN_WINDOW);
        open.getIntegers().write(0, menu.getWindowID()).write(1, menu.getType().getIndex());
        open.getChatComponents().write(0, AdventureComponentConverter.fromComponent(menu.getTitle()));
        try {
            plugin.getProtocolManager().sendServerPacket(player, open);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        trackMenu(menu);
    }

    public void closeMenu(@NotNull Player player, @NotNull Menu menu) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.CLOSE_WINDOW);
        packet.getIntegers().write(0, menu.getWindowID());
        try {
            plugin.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        untrackMenu(menu);
    }

    public void setSlot(@NotNull Player player, int windowID, @NotNull ItemStack item, int slot) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SET_SLOT);
        packet.getIntegers().write(0, windowID)
                .write(1, 1)
                .write(2, slot);
        packet.getItemModifier().write(0, item);
        try {
            plugin.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
        Bukkit.getScheduler().runTask(plugin, player::updateInventory);
    }

    @Override
    public void run() {
        for(Menu menu : menus) {
            menu.clear();
            menu.setItems();
            PacketContainer windowItems = new PacketContainer(PacketType.Play.Server.WINDOW_ITEMS);
            windowItems.getIntegers().write(0, menu.getWindowID())
                    .write(1, 1);
            windowItems.getItemListModifier().write(0, menu.getItemList());
            windowItems.getItemModifier().write(0, new ItemStack(Material.AIR));
            try {
                plugin.getProtocolManager().sendServerPacket(menu.getPlayer(), windowItems);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
