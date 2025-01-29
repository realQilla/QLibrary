package net.qilla.qlibrary.menu;

import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.menu.socket.QSocket;
import net.qilla.qlibrary.menu.socket.Socket;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public interface StaticMenu extends InventoryHolder {

    void finalizeMenu();

    void open(boolean toHistory);

    void close();

    void handleClick(@NotNull InventoryClickEvent event);

    boolean returnMenu();

    @NotNull Socket addSocket(@NotNull Socket socket);

    @NotNull Socket addSocket(@NotNull Socket socket, int delayMillis);

    @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets);

    @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets, int millis);

    @Nullable Socket removeSocket(int index);

    void inventoryClickEvent(@NotNull InventoryClickEvent event);

    void inventoryOpenEvent(InventoryOpenEvent event);

    void inventoryCloseEvent(InventoryCloseEvent event);

    @NotNull Socket returnSocket();

    @NotNull Plugin getPlugin();

    @NotNull EnhancedPlayer getPlayer();

    @NotNull PlayerData<? extends EnhancedPlayer> getPlayerData();

    @NotNull Inventory getInventory();

    @NotNull List<Integer> getTotalIndexes();

    void refreshSockets();

    @NotNull Socket menuSocket();

    @NotNull StaticConfig staticConfig();
}