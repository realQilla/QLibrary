package net.qilla.qlibrary.menu;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.menu.socket.*;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.IntStream;

public abstract class QStaticMenu implements StaticMenu {

    private final Plugin plugin;
    private final Inventory inventory;
    private final StaticConfig staticConfig;
    private final EnhancedPlayer enhancedPlayer;
    private final PlayerData<EnhancedPlayer> playerData;
    private final Map<Integer, Socket> socketHolder = new HashMap<>();
    private final List<Integer> totalIndexes = IntStream.range(0, staticConfig().menuSize().getSize()).boxed().toList();

    public QStaticMenu(@NotNull Plugin plugin, @NotNull PlayerData<EnhancedPlayer> playerData) {
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
    public void close() {
        enhancedPlayer.closeInventory();
    }

    @Override
    public void handleClick(@NotNull InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "InventoryClickEvent cannot be null");
        Socket socket = socketHolder.get(event.getSlot());
        if(socket != null) {
            socket.onClick(enhancedPlayer, event, playerData);
        }
    }

    @Override
    public boolean returnMenu() {
        Optional<QStaticMenu> optional = playerData.popFromHistory();
        if(optional.isEmpty()) {
            this.close();
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
    public @Nullable Socket removeSocket(int index) {
        inventory.setItem(index, Slots.FILLER.getItem());
        return socketHolder.remove(index);
    }

    @Override
    public void inventoryClickEvent(@NotNull InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "InventoryClickEvent cannot be null");

        event.setCancelled(true);
        if(event.getClickedInventory().getHolder() instanceof QStaticMenu) {
            this.handleClick(event);
        }
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
    public @NotNull EnhancedPlayer getPlayer() {
        return this.enhancedPlayer;
    }

    @Override
    public @NotNull PlayerData getPlayerData() {
        return this.playerData;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public @NotNull List<Integer> getTotalIndexes() {
        return this.totalIndexes;
    }

    @Override
    public void inventoryOpenEvent(InventoryOpenEvent event) {
        //Optionally to be overwritten
    }

    @Override
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        //Optionally to be overwritten
    }
}