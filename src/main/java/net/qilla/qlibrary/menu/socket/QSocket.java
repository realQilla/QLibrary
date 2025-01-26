package net.qilla.qlibrary.menu.socket;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.data.QPlayerData;
import net.qilla.qlibrary.menu.ClickAction;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public class QSocket implements Socket{

    private final int index;
    private final Slot slot;
    private final ClickAction clickAction;
    private final CooldownType cooldownType;

    public QSocket(int index, @NotNull Slot slot) {
        Preconditions.checkNotNull(slot, "Slot cannot be null");
        Preconditions.checkArgument(index > 0 && index < 54, "Index must be in valid bounds");

        this.index = index;
        this.slot = slot;
        this.clickAction = null;
        this.cooldownType = null;
    }

    public QSocket(int index, Slot slot, @Nullable ClickAction clickAction, @Nullable CooldownType cooldownType) {
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

    public void onClick(@NotNull EnhancedPlayer enhancedPlayer, @NotNull InventoryClickEvent event, @NotNull PlayerData playerData) {
        Preconditions.checkNotNull(enhancedPlayer, "Player cannot be null");
        Preconditions.checkNotNull(event, "Event cannot be null");
        Preconditions.checkNotNull(playerData, "PlayerData cannot be null");

        if(clickAction == null) return;
        if(playerData.hasCooldown(cooldownType)) return;
        if(clickAction.onClick(event)) {
            playerData.setCooldown(cooldownType);
            enhancedPlayer.playSound(slot.getClickSound(), true);
        }
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        Socket socket = (Socket) object;
        return index == socket.index() && slot.equals(socket.slot());
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(index);
        result = 31 * result + slot.hashCode();
        return result;
    }
}