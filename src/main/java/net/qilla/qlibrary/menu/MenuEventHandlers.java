package net.qilla.qlibrary.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Represents an event listener implementation that handles menu-related
 * events. Register this class as an event listener to properly handle
 * menu related actions.
 * Listens to: InventoryClickEvent, InventoryInteractEvent, InventoryOpenEvent, InventoryCloseEvent
 */

public final class MenuEventHandlers implements Listener {

    private static final MenuEventHandlers INSTANCE = new MenuEventHandlers();

    private MenuEventHandlers() {
    }

    public static MenuEventHandlers initiateEventHandlers() {
        return INSTANCE;
    }

    @EventHandler
    private void inventoryClickEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        InventoryHolder holder = event.getClickedInventory().getHolder();

        if(holder instanceof StaticMenu menu) menu.playerClickMenu(event);
    }

    @EventHandler
    private void onInventoryInteract(InventoryInteractEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if(holder instanceof StaticMenu menu) menu.playerInteractMenu(event);
    }

    @EventHandler
    private void inventoryOpenEvent(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if(holder instanceof StaticMenu menu) menu.playerOpenMenu(event);
    }

    @EventHandler
    private void inventoryCloseEvent(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if(holder instanceof StaticMenu menu) menu.playerCloseMenu(event);
    }
}