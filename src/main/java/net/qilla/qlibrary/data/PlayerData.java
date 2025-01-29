package net.qilla.qlibrary.data;

import net.qilla.qlibrary.menu.QStaticMenu;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.player.EnhancedPlayer;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * General player data class for information such as,
 * player cooldowns, latest input, etc.
 */

public interface PlayerData<T extends EnhancedPlayer> {

    /**
     * Returns the EnhancedPlayer related to this data
     * @return Returns the player
     */

    @NotNull T getPlayer();

    /**
     * Gets the remaining cooldown for a cooldown type in milliseconds.
     *
     * @param type The cooldown to lookup
     *
     * @return Returns the remaining time in milliseconds
     */

    long getCooldown(@NotNull CooldownType type);

    /**
     * Returns true if a cooldown is currently active for the specified type.
     *
     * @param type The cooldown to check for
     *
     * @return Returns true if a cooldown is still active
     */

    boolean hasCooldown(@NotNull CooldownType type);

    /**
     * Sets a cooldown for a specified type.
     *
     * @param type   The cooldown type to set
     * @param millis The cooldown length in milliseconds
     */

    void setCooldown(@NotNull CooldownType type, long millis);

    /**
     * Sets a cooldown for a specified type using the type's default cooldown length assigned to the enum.
     *
     * @param type The cooldown type to set
     */

    void setCooldown(@NotNull CooldownType type);

    /**
     * Initiates a fresh menu history queue Note: Only necessary for the first opened menu
     *
     * @param menu The menu that is initially opened
     */

    void newMenu(@NotNull QStaticMenu menu);

    /**
     * Returns a nullable optional of the last opened menu, if there is no last menu an optional containing null will be returned.
     *
     * @return The last opened menu
     */

    Optional<QStaticMenu> popFromHistory();

    /**
     * Adds a menu to the history that can be grabbed again using the method popFromHistory.
     *
     * @param menu The menu to add to the queue
     */

    void pushToHistory(@NotNull QStaticMenu menu);

    /**
     * Clears the entirety of any existing menu history.
     */

    void resetMenuHistory();

    /**
     * Assigns a new future to the future field that can be fulfilled through chat, menu, anvil menu, etc.
     *
     * @return Returns a completable future that can be waited for.
     */

    CompletableFuture<String> requestInput();

    /**
     * Fills the future input field if one exists, otherwise it does nothing.
     *
     * @param input The string to assign the future to.
     *
     * @return Returns true if the operation was successful
     */

    boolean fulfillInput(@NotNull String input);
}