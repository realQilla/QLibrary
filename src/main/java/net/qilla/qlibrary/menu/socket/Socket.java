package net.qilla.qlibrary.menu.socket;

import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface Socket {

    int index();

    Slot slot();

    void onClick(@NotNull EnhancedPlayer enhancedPlayer, @NotNull InventoryClickEvent event, @NotNull PlayerData playerData);
}