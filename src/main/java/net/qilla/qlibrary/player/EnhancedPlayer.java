package net.qilla.qlibrary.player;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.qilla.qlibrary.util.sound.PlayType;
import net.qilla.qlibrary.util.sound.QSound;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Simple CraftPlayer wrapper with minor enhancements
 * and utility methods for easier development
 */

public interface EnhancedPlayer extends Player {

        /**
         * Sends a packet to the player
         * @param packet
         */

        void sendPacket(Packet<?> packet);

        /**
         * Broadcasts a packet globally
         * @param packet
         */

        void broadcastPacket(Packet<?> packet);

        /**
         * Gets the server player handle
         * @return Returns the player's handle
         */

        @NotNull ServerPlayer getHandle();

        /**
         * Sends a message using the minimessage format
         * @param message Message to be displayed
         */

        void sendMessage(@NotNull String message);

        /**
         * Sends a message using a component
         * @param component a message
         */

        void sendMessage(@NotNull Component component);

        /**
         * Sends a message using the minimessage format
         * @param message The message to send
         */

        void sendActionBar(@NotNull String message);

        /**
         * Sends a message using a component
         * @param component a message
         */

        void sendActionBar(@NotNull Component component);

        /**
         * Plays a sound with custom properties set utilizing a QSound object
         * @param qSound
         * @param randomPitch
         */

        void playSound(@Nullable QSound qSound, boolean randomPitch);

        /**
         * Plays a sound with custom properties that can each be specified.
         * @param sound
         * @param volume
         * @param pitch
         * @param category
         * @param playType
         */

        void playSound(@NotNull Sound sound, float volume, float pitch, @NotNull SoundCategory category, @Nullable PlayType playType);
}
