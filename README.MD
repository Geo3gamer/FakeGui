FakeGui
=======
Tool for creating custom GUIs using packets.

Maven:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.Geo3gamer</groupId>
    <artifactId>FakeGui</artifactId>
    <version>b1.7.3-SNAPSHOT</version>
</dependency>
```

Example usage:

```java
package ru.sliva.fakegui;

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
        super(InventoryType.GENERIC_9x3, "Test", player);
    }

    @Override
    public void onClick(@NotNull ClickEvent event) {
        Player player = event.getPlayer();
        int slot = event.getSlot();
        ClickType clickType = event.getClickType();
        if (slot == 0 && clickType == ClickType.LEFT) {
            player.sendMessage("It works");
        }
    }

    @Override
    public void setItems() {
        setItem(0, new ItemStack(Material.STONE));
        setItem(getSize() - 1, new ItemStack(Material.COAL));
    }
}
```

```java
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Menu menu = new CustomMenu(player);
        menu.open();
    }
```