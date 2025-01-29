package net.qilla.qlibrary.data;

import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface PlayerDataRegistry<T extends PlayerData<? extends EnhancedPlayer>> {

    @NotNull T getData(@NotNull Player player);

    @Nullable
    T getData(@NotNull UUID uuid);

    @Nullable
    T setData(@NotNull UUID uuid, @NotNull T playerData);

    boolean hasData(@NotNull UUID uuid);

    @Nullable T clearData(@NotNull UUID uuid);
}