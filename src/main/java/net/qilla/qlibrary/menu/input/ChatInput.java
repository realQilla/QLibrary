package net.qilla.qlibrary.menu.input;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ChatInput extends PlayerInput {

    private final Component chatMessage;

    public ChatInput(@NotNull Plugin plugin, @NotNull Player player, @NotNull Component chatMessage) {
        super(plugin, player);
        this.chatMessage = chatMessage;
    }

    @Override
    public void init(Consumer<String> onComplete) {
        this.openMenu();
        CompletableFuture.supplyAsync(super::awaitResponse, super.getExecutor())
                .thenAccept(onComplete);
    }

    public void openMenu() {
        Bukkit.getScheduler().runTask(super.getPlugin(), () -> {
            super.getQPlayer().closeInventory();
            super.getQPlayer().sendMessage(chatMessage);
        });
    }
}