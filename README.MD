FakeGui
=======
Tool for creating custom GUIs using packets.

Example usage:
```java
package ru.sliva.fakegui;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.fakegui.event.ClickEvent;
import ru.sliva.fakegui.menu.Menu;
import ru.sliva.fakegui.wrapper.ClickType;
import ru.sliva.fakegui.wrapper.InventoryType;

public class CustomMenu extends Menu {

    public CustomMenu(@NotNull Player player) {
        super(InventoryType.GENERIC_9x3, Component.text("Test"), player);
    }

    @Override
    public void onClick(@NotNull ClickEvent event) {
        Player player = event.getPlayer();
        int slot = event.getSlot();
        ClickType clickType = event.getClickType();
        if(slot == 0 && clickType == ClickType.LEFT) {
            player.sendMessage(Component.text("It works"));
        }
    }

    @Override
    public void setItems() {
        setItem(0, new ItemStack(Material.ANDESITE));
        setItem(getSize() - 1, new ItemStack(Material.COAL));
    }
}
```
```java
Menu menu = new CustomMenu(player);
menu.open();
```