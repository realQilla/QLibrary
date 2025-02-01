package net.qilla.qlibrary.menu;

import net.kyori.adventure.text.Component;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.menu.input.CompleteInput;
import net.qilla.qlibrary.menu.socket.Socket;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface StaticMenu extends InventoryHolder {

    void requestSignInput(List<String> text, CompleteInput then);

    void requestChatInput(List<Component> text, CompleteInput then);

    void finalizeMenu();

    void open(boolean toHistory);

    boolean returnMenu();

    @NotNull Socket addSocket(@NotNull Socket socket);

    @NotNull Socket addSocket(@NotNull Socket socket, int delayMillis);

    @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets);

    @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets, int millis);

    @NotNull Map<Integer, Socket> getSockets();

    @Nullable Socket removeSocket(int index);

    void clearSockets();

    void playerClickMenu(InventoryClickEvent event);

    void playerInteractMenu(InventoryInteractEvent event);

    void playerOpenMenu(InventoryOpenEvent event);

    void playerCloseMenu(InventoryCloseEvent event);

    @NotNull Socket returnSocket();

    @NotNull Plugin getPlugin();

    @NotNull PlayerData<? extends @NotNull EnhancedPlayer> getPlayerData();

    @NotNull EnhancedPlayer getPlayer();

    @NotNull Inventory getInventory();

    @NotNull List<Integer> getTotalIndexes();

    void refreshSockets();

    @NotNull Socket menuSocket();

    @NotNull StaticConfig staticConfig();

    void shutdown();
}