package net.qilla.qlibrary.menu.input;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerDataRegistry;
import net.qilla.qlibrary.player.QPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.*;
import java.util.function.Consumer;

public abstract class PlayerInput {

    private static final int SEC_TIMEOUT = 60;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Plugin plugin;
    private final QPlayer player;

    public PlayerInput(@NotNull Plugin plugin, @NotNull Player player) {
        Preconditions.checkNotNull(executorService, "Executor Service cannot be null");
        Preconditions.checkNotNull(player, "DPlayer cannot be null");
        this.plugin = plugin;
        this.player = (QPlayer) player;
    }

    public String awaitResponse() {
        try {
            return PlayerDataRegistry.getInstance().getPlayerData(player.getUniqueId()).requestInput().get(SEC_TIMEOUT, TimeUnit.SECONDS);
        } catch(TimeoutException e) {
            return "";
        } catch(ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ExecutorService getExecutor() {
        return this.executorService;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public QPlayer getQPlayer() {
        return this.player;
    }

    public abstract void init(Consumer<String> onComplete);
}