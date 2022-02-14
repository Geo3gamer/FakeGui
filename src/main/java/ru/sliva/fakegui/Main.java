package ru.sliva.fakegui;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import ru.sliva.fakegui.menu.MenuManager;

public final class Main extends JavaPlugin {

    private ProtocolManager protocolManager;
    private MenuManager manager;
    private BukkitTask task;

    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();

        manager = new MenuManager(this);
        protocolManager.addPacketListener(manager);
        Bukkit.getPluginManager().registerEvents(manager, this);
        task = Bukkit.getScheduler().runTaskTimer(this, manager, 0, 20);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(task.getTaskId());
        protocolManager.removePacketListener(manager);
        HandlerList.unregisterAll(manager);
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
