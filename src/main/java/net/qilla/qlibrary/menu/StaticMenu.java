package net.qilla.qlibrary.menu;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.data.PlayerDataRegistry;
import net.qilla.qlibrary.menu.socket.Slot;
import net.qilla.qlibrary.menu.socket.Slots;
import net.qilla.qlibrary.menu.socket.Socket;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.QPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.IntStream;

public abstract class StaticMenu implements InventoryHolder {

    private final Plugin plugin;
    private final Inventory inventory;
    private final StaticConfig staticConfig;
    private final QPlayer qPlayer;
    private final PlayerData playerData;
    private final Map<Integer, Socket> socketHolder = new HashMap<>();
    private final List<Integer> totalIndexes = IntStream.range(0, staticConfig().menuSize().getSize()).boxed().toList();

    protected StaticMenu(@NotNull Plugin plugin, @NotNull Player player) {
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        Preconditions.checkNotNull(player, "Player cannot be null");

        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(this, staticConfig().menuSize().getSize(), staticConfig().title());
        this.staticConfig = staticConfig();
        this.qPlayer = (QPlayer) player;
        this.playerData = PlayerDataRegistry.getInstance().getPlayerData(player.getUniqueId());

        totalIndexes.forEach(index -> inventory.setItem(index, Slots.FILLER.getItem()));
        this.addSocket(menuSocket());
        this.addSocket(returnSocket());
    }

    public void finalizeMenu() {
        totalIndexes.stream()
                .filter(index -> !socketHolder.containsKey(index))
                .forEach(index -> inventory.setItem(index, Slots.FILLER.getItem()));
    }

    public void open(boolean toHistory) {
        qPlayer.openInventory(inventory);
        if(toHistory) playerData.pushToHistory(this);
    }

    public void close() {
        qPlayer.closeInventory();
    }

    public void handleClick(@NotNull InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "InventoryClickEvent cannot be null");
        Socket socket = socketHolder.get(event.getSlot());
        if(socket != null) {
            socket.onClick(qPlayer, event);
        }
    }

    public boolean returnMenu() {
        Optional<StaticMenu> optional = playerData.popFromHistory();
        if(optional.isEmpty()) {
            this.close();
            return true;
        }
        StaticMenu menu = optional.get();
        menu.refreshSockets();
        menu.open(false);
        return true;
    }

    public @NotNull Socket addSocket(@NotNull Socket socket) {
        Preconditions.checkNotNull(socket, "Socket cannot be null");
        Slot slot = socket.slot();

        socketHolder.put(socket.index(), socket);
        inventory.setItem(socket.index(), slot.getItem());
        return socket;
    }

    public @NotNull Socket addSocket(@NotNull Socket socket, int delayMillis) {
        Preconditions.checkNotNull(socket, "Socket cannot be null");
        Slot slot = socket.slot();

        Runnable runnable = () -> {
            addSocket(socket);
            inventory.setItem(socket.index(), slot.getItem());
            qPlayer.playSound(slot.getAppearSound(), true);
        };

        if(delayMillis > 0) {
            Bukkit.getScheduler().runTaskLater(plugin, runnable, delayMillis / 50);
        } else runnable.run();
        return socket;
    }

    public @NotNull List<Socket> addSocket(@NotNull List<Socket> sockets) {
        sockets.forEach(this::addSocket);
        return sockets;
    }

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

    public @Nullable Socket removeSocket(int index) {
        inventory.setItem(index, Slots.FILLER.getItem());
        return socketHolder.remove(index);
    }

    public void inventoryClickEvent(@NotNull InventoryClickEvent event) {
        Preconditions.checkNotNull(event, "InventoryClickEvent cannot be null");

        event.setCancelled(true);
        if(event.getClickedInventory().getHolder() instanceof StaticMenu) {
            this.handleClick(event);
        }
    }

    public void inventoryOpenEvent(InventoryOpenEvent event) {
    }

    public void inventoryCloseEvent(InventoryCloseEvent event) {
    }

    private @NotNull Socket returnSocket() {
        return new Socket(staticConfig.returnIndex(), Slots.RETURN, event -> {
            ClickType clickType = event.getClick();
            if(clickType.isLeftClick()) {
                return this.returnMenu();
            } else return false;
        }, CooldownType.OPEN_MENU);
    }

    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    public @NotNull QPlayer getDPlayer() {
        return this.qPlayer;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public @NotNull List<Integer> getTotalIndexes() {
        return this.totalIndexes;
    }

    public abstract void refreshSockets();
    public abstract @NotNull Socket menuSocket();
    public abstract @NotNull StaticConfig staticConfig();
}