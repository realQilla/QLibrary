package net.qilla.qlibrary.menu;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ClickAction {

    boolean onClick(InventoryClickEvent event);
}