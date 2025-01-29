package net.qilla.qlibrary.menu.input;

import net.kyori.adventure.text.Component;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class ChatInput extends PlayerInput {

    private final Component chatMessage;

    public ChatInput(@NotNull Plugin plugin, @NotNull PlayerData<EnhancedPlayer> playerData, @NotNull Component chatMessage) {
        super(plugin, playerData);
        this.chatMessage = chatMessage;
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
            player.sendMessage(chatMessage);
        });
    }
}