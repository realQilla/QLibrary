package net.qilla.qlibrary.menu.socket;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.data.PlayerDataRegistry;
import net.qilla.qlibrary.menu.ClickAction;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.QPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public class Socket {

    private final int index;
    private final Slot slot;
    private final ClickAction clickAction;
    private final CooldownType cooldownType;

    public Socket(int index, @NotNull Slot slot) {
        Preconditions.checkNotNull(slot, "Slot cannot be null");
        this.index = index;
        this.slot = slot;
        this.clickAction = null;
        this.cooldownType = null;
    }

    public Socket(int index, Slot slot, @Nullable ClickAction clickAction, @Nullable CooldownType cooldownType) {
        Preconditions.checkNotNull(slot, "Slot cannot be null");
        this.index = index;
        this.slot = slot;
        this.clickAction = clickAction;
        this.cooldownType = cooldownType;
    }

    public int index() {
        return index;
    }

    public Slot slot() {
        return slot;
    }

    public void onClick(@NotNull QPlayer qPlayer, @NotNull InventoryClickEvent event) {
        Preconditions.checkNotNull(qPlayer, "QPlayer cannot be null");
        Preconditions.checkNotNull(event, "Inventory click event cannot be null");
        if(clickAction == null) return;
        PlayerData playerData = PlayerDataRegistry.getInstance().getPlayerData(qPlayer.getUniqueId());
        if(playerData.hasCooldown(cooldownType)) return;
        if(clickAction.onClick(event)) {
            playerData.setCooldown(cooldownType);
            qPlayer.playSound(slot.getClickSound(), true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Socket socket = (Socket) o;
        return index == socket.index && slot.equals(socket.slot);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(index);
        result = 31 * result + slot.hashCode();
        return result;
    }
}