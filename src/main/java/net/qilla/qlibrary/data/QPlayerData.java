package net.qilla.qlibrary.data;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.menu.QStaticMenu;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class QPlayerData implements PlayerData{

    private final EnhancedPlayer player;
    private final EnumMap<CooldownType, Long> cooldownMap = new EnumMap<>(CooldownType.class);
    private final Deque<QStaticMenu> menuHistory = new ArrayDeque<>();
    private CompletableFuture<String> inputFuture;

    public QPlayerData(@NotNull EnhancedPlayer player) {
        Preconditions.checkNotNull(player, "Player cannot be null");

        this.player = player;
    }

    @Override
    public @NotNull EnhancedPlayer getPlayer() {
        return this.player;
    }

    @Override
    public synchronized long getCooldown(@NotNull CooldownType type) {
        Preconditions.checkNotNull(type, "CooldownType cannot be null");

        long time = cooldownMap.getOrDefault(type, 0L) - System.currentTimeMillis();
        if(time <= 0L) cooldownMap.remove(type);

        return time;
    }

    @Override
    public synchronized boolean hasCooldown(@NotNull CooldownType type) {
        Preconditions.checkNotNull(type, "CooldownType cannot be null");

        return this.getCooldown(type) > 0L;
    }

    @Override
    public synchronized void setCooldown(@NotNull CooldownType type, long millis) {
        Preconditions.checkNotNull(type, "CooldownType cannot be null");

        cooldownMap.put(type, System.currentTimeMillis() + Math.max(0L, millis));
    }

    @Override
    public synchronized void setCooldown(@NotNull CooldownType type) {
        this.setCooldown(type, type.getMillis());
    }

    @Override
    public void newMenu(@NotNull QStaticMenu menu) {
        Preconditions.checkNotNull(menu, "Menu cannot be null");

        this.resetMenuHistory();
        menu.open(true);
    }

    @Override
    public Optional<QStaticMenu> popFromHistory() {
        if(!menuHistory.isEmpty()) {
            menuHistory.pop();
        }
        return Optional.ofNullable(menuHistory.peek());
    }

    @Override
    public void pushToHistory(@NotNull QStaticMenu menu) {
        Preconditions.checkNotNull(menu, "Menu cannot be null");

        menuHistory.push(menu);
    }

    @Override
    public void resetMenuHistory() {
        menuHistory.clear();
    }

    @Override
    public CompletableFuture<String> requestInput() {
        if(inputFuture != null && !inputFuture.isDone()) return inputFuture;
        return inputFuture = new CompletableFuture<>();
    }

    @Override
    public boolean fulfillInput(@NotNull String input) {
        Preconditions.checkNotNull(input, "Input cannot be null");

        if(inputFuture == null || inputFuture.isDone()) return false;

        this.inputFuture.complete(input);
        inputFuture = null;
        return true;
    }
}
