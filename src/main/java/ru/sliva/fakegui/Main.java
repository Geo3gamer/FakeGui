package ru.sliva.fakegui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sliva.fakegui.menu.MenuManager;

public final class Main extends JavaPlugin {

    private int taskID;

    @Override
    public void onEnable() {
        MenuManager manager = new MenuManager(this);
        Bukkit.getPluginManager().registerEvents(manager, this);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, manager, 0, 20);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
