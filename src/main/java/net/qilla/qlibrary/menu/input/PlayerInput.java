package net.qilla.qlibrary.menu.input;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.player.EnhancedPlayer;
import net.qilla.qlibrary.player.QEnhancedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.*;
import java.util.function.Consumer;

public abstract class PlayerInput {

    private static final int SEC_TIMEOUT = 60;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Plugin plugin;
    private final PlayerData playerData;

    public PlayerInput(@NotNull Plugin plugin, PlayerData playerData) {
        Preconditions.checkNotNull(playerData, "PlayerData cannot be null");
        this.plugin = plugin;
        this.playerData = playerData;
    }

    public String awaitResponse() {
        try {
            return playerData.requestInput().get(SEC_TIMEOUT, TimeUnit.SECONDS);
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

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public abstract void init(Consumer<String> onComplete);
}