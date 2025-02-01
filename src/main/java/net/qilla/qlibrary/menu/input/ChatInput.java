package net.qilla.qlibrary.menu.input;

import net.kyori.adventure.text.Component;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class ChatInput extends PlayerInput {

    private final List<Component> text;

    public ChatInput(@NotNull Plugin plugin, @NotNull PlayerData<? extends EnhancedPlayer> playerData, @NotNull List<Component> text) {
        super(plugin, playerData);
        this.text = text;
    }

    @Override
    public void init(Consumer<String> onComplete) {
        this.openMenu();
        CompletableFuture.supplyAsync(super::awaitResponse, super.getExecutor())
                .thenAccept(onComplete);
    }

    public void openMenu() {
        EnhancedPlayer player = super.getPlayerData().getPlayer();

        Bukkit.getScheduler().runTask(super.getPlugin(), () -> {
            player.closeInventory();
            text.forEach(player::sendMessage);
        });
    }
}