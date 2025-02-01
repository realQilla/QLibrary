package net.qilla.qlibrary.menu;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.menu.input.ChatInput;
import net.qilla.qlibrary.menu.input.CompleteInput;
import net.qilla.qlibrary.menu.input.SignInput;
import net.qilla.qlibrary.menu.socket.*;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class QStaticMenu implements StaticMenu, Listener {

    private final Plugin plugin;
    private final Inventory inventory;
    private final StaticConfig staticConfig;
    private final EnhancedPlayer enhancedPlayer;
    private final PlayerData<? extends EnhancedPlayer> playerData;
    private final Map<Integer, Socket> socketHolder = new HashMap<>();
    private final List<Integer> totalIndexes = IntStream.range(0, staticConfig().menuSize().getSize()).boxed().toList();

    public QStaticMenu(@NotNull Plugin plugin, @NotNull PlayerData<?> playerData) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(playerData, "PlayerData cannot be null");

        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(this, staticConfig().menuSize().getSize(), staticConfig().title());
        this.staticConfig = staticConfig();
        this.playerData = playerData;
        this.enhancedPlayer = playerData.getPlayer();

        totalIndexes.forEach(index -> inventory.setItem(index, Slots.FILLER.getItem()));
        this.addSocket(menuSocket());
        this.addSocket(returnSocket());
    }

    @Override
    public void requestSignInput(List<String> text, CompleteInput then) {
        new SignInput(plugin, playerData, text).init(result -> {
            Bukkit.getScheduler().runTask(plugin, () -> then.run(result));
        });
    }

    @Override
    public void requestChatInput(List<Component> text, CompleteInput then) {
        new ChatInput(plugin, playerData, text).init(result -> {
            Bukkit.getScheduler().runTask(plugin, () -> then.run(result));
        });
    }

    @Override
    public void finalizeMenu() {
        totalIndexes.stream()
                .filter(index -> !socketHolder.containsKey(index))
                .forEach(index -> inventory.setItem(index, Slots.FILLER.getItem()));
    }

    @Override
    public void open(boolean toHistory) {
        enhancedPlayer.openInventory(inventory);
        if(toHistory) playerData.pushToHistory(this);
    }

    @Override
    public boolean returnMenu() {
        Optional<QStaticMenu> optional = playerData.popFromHistory();
        if(optional.isEmpty()) {
            this.inventory.close();
            return true;
        }
        QStaticMenu menu = optional.get();
        menu.refreshSockets();
        menu.open(false);
        return true;
    }

    @Override
    public @NotNull Socket addSocket(@NotNull Socket socket) {
        Preconditions.checkNotNull(socket, "Socket cannot be null");
        Slot slot = socket.slot();

        socketHolder.put(socket.index(), socket);
        inventory.setItem(socket.index(), slot.getItem());
        return socket;
    }

    @Override
    public @NotNull Socket addSocket(@NotNull Socket socket, int millis) {
        Preconditions.checkNotNull(socket, "Socket cannot be null");
        Slot slot = socket.slot();

        Runnable runnable = () -> {
            addSocket(socket);
            inventory.setItem(socket.index(), slot.getItem());
            enhancedPlayer.playSound(slot.getAppearSound(), true);
        };

        if(millis > 0) Bukkit.getScheduler().runTaskLater(plugin, runnable, millis / 50);
        else runnable.run();
        return socket;
    }

    @Override
    public @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets) {
        sockets.forEach(this::addSocket);
        return sockets;
    }

    @Override
    public @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets, int delayMillis) {
        Preconditions.checkNotNull(sockets, "Socket list cannot be null");

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            sockets.forEach(socket -> {
                try {
                    Thread.sleep(delayMillis);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Bukkit.getScheduler().runTask(plugin, () -> addSocket(socket, 0));
            });
        });
        return sockets;
    }

    @Override
    public @NotNull Map<Integer, Socket> getSockets() {
        return Collections.unmodifiableMap(socketHolder);
    }

    @Override
    public @Nullable Socket removeSocket(int index) {
        if(!socketHolder.containsKey(index)) return null;
        inventory.setItem(index, Slots.FILLER.getItem());
        return socketHolder.remove(index);
    }

    @Override
    public void clearSockets() {
        socketHolder.clear();
        this.finalizeMenu();
    }

    @Override
    public @NotNull Socket returnSocket() {
        return new QSocket(staticConfig.returnIndex(), Slots.RETURN, event -> {
            ClickType clickType = event.getClick();
            if(clickType.isLeftClick()) {
                return this.returnMenu();
            } else return false;
        }, CooldownType.OPEN_MENU);
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public @NotNull PlayerData<? extends EnhancedPlayer> getPlayerData() {
        return this.playerData;
    }

    @Override
    public @NotNull EnhancedPlayer getPlayer() {
        return this.enhancedPlayer;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public @NotNull List<Integer> getTotalIndexes() {
        return this.totalIndexes;
    }

    @Override
    public void shutdown() {
        this.clearSockets();
        this.inventory.close();
    }

    @Override
    public void playerClickMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        Socket socket = socketHolder.get(event.getSlot());
        if(socket != null) socket.onClick(enhancedPlayer, event, playerData);
    }

    @Override
    public void playerInteractMenu(InventoryInteractEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void playerOpenMenu(InventoryOpenEvent event) {
    }

    @Override
    public void playerCloseMenu(InventoryCloseEvent event) {
    }
}