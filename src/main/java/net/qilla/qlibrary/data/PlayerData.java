package net.qilla.qlibrary.data;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.menu.StaticMenu;
import net.qilla.qlibrary.player.CooldownType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * General player data class for information such as,
 * player cooldowns, latest input, etc.
 */

public class PlayerData {

    private final EnumMap<CooldownType, Long> cooldownMap = new EnumMap<>(CooldownType.class);
    private final Deque<StaticMenu> menuHistory = new ArrayDeque<>();
    private CompletableFuture<String> inputFuture;

    public PlayerData() {
    }

    /**
     * Gets the remaining cooldown for a cooldown type in milliseconds.
     * @param type The cooldown to lookup
     * @return Returns the remaining time in milliseconds
     */

    public long getCooldown(@NotNull CooldownType type) {
        Preconditions.checkNotNull(type, "CooldownType cannot be null");

        if(!cooldownMap.containsKey(type)) return 0L;

        long time = System.currentTimeMillis() - cooldownMap.get(type);
        return Math.max(0L, time);
    }

    /**
     * Returns true if a cooldown is currently active for the specified type.
     * @param type The cooldown to check for
     * @return Returns true if a cooldown is still active
     */

    public boolean hasCooldown(@NotNull CooldownType type) {
        Preconditions.checkNotNull(type, "CooldownType cannot be null");

        return getCooldown(type) > 0L;
    }

    /**
     * Sets a cooldown for a specified type.
     * @param type The cooldown type to set
     * @param millis The cooldown length in milliseconds
     */

    public void setCooldown(@NotNull CooldownType type, long millis) {
        Preconditions.checkNotNull(type, "CooldownType cannot be null");
        Preconditions.checkArgument(millis >= 0L, "Millis must be greater than or equal to zero");

        cooldownMap.put(type, System.currentTimeMillis() + millis);
    }

    /**
     * Sets a cooldown for a specified type using the type's
     * default cooldown length assigned to the enum.
     * @param type The cooldown type to set
     */

    public void setCooldown(@NotNull CooldownType type) {
        this.setCooldown(type, type.getMillis());
    }

    /**
     * Initiates a fresh menu history queue
     * Note: Only necessary for the first opened menu
     * @param menu The menu that is initially opened
     */

    public void newMenu(@NotNull StaticMenu menu) {
        Preconditions.checkNotNull(menu, "Menu cannot be null");

        this.resetMenuHistory();
        menu.open(true);
    }

    /**
     * Returns a nullable optional of the last opened menu,
     * if there is no last menu an optional containing null
     * will be returned.
     * @return The last opened menu
     */

    public Optional<StaticMenu> popFromHistory() {
        if(!menuHistory.isEmpty()) {
            menuHistory.pop();
        }
        return Optional.ofNullable(menuHistory.peek());
    }

    /**
     * Adds a menu to the history that can be grabbed again
     * using the method popFromHistory.
     * @param menu The menu to add to the queue
     */

    public void pushToHistory(@NotNull StaticMenu menu) {
        Preconditions.checkNotNull(menu, "Menu cannot be null");

        menuHistory.push(menu);
    }

    /**
     * Clears the entirety of any existing menu history.
     */

    public void resetMenuHistory() {
        menuHistory.clear();
    }

    /**
     * Assigns a new future to the future field that can be fulfilled
     * through chat, menu, anvil menu, etc.
     * @return Returns a completable future that can be waited for.
     */

    public CompletableFuture<String> requestInput() {
        if(inputFuture != null && !inputFuture.isDone()) return inputFuture;
        return inputFuture = new CompletableFuture<>();
    }

    /**
     * Fills the future input field if one exists, otherwise it does nothing.
     * @param input The string to assign the future to.
     * @return Returns true if the operation was successful
     */

    public boolean fulfillInput(@NotNull String input) {
        Preconditions.checkNotNull(input, "Input cannot be null");

        if(inputFuture == null || inputFuture.isDone()) return false;

        this.inputFuture.complete(input);
        inputFuture = null;
        return true;
    }
}
