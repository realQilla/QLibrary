package net.qilla.qlibrary.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.qilla.qlibrary.util.PlayType;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.tools.RandomUtil;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Simple CraftPlayer wrapper with minor enhancements
 * and utility methods for easier development
 */

public final class QPlayer extends CraftPlayer {
    public QPlayer(CraftServer server, ServerPlayer entity) {
        super(server, entity);
    }

    /**
     * Sends a packet to the player
     * @param packet
     */

    public void sendPacket(Packet<?> packet) {
        this.getHandle().connection.send(packet);
    }

    /**
     * Broadcasts a packet globally
     * @param packet
     */

    public void broadcastPacket(Packet<?> packet) {
        this.getHandle().serverLevel().getChunkSource().broadcastAndSend(this.getHandle(), packet);
    }

    /**
     * Sends a message using the minimessage format
     * @param message Message to be displayed
     */

    public void sendMessage(@NotNull String message) {
        super.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    /**
     * Sends a message using a component
     * @param component a message
     */

    public void sendMessage(@NotNull Component component) {
        super.sendMessage(component);
    }

    /**
     * Sends a message using the minimessage format
     * @param message The message to send
     */

    public void sendActionBar(@NotNull String message) {
        super.sendActionBar(MiniMessage.miniMessage().deserialize(message));
    }

    /**
     * Sends a message using a component
     * @param component a message
     */

    public void sendActionBar(@NotNull Component component) {
        super.sendActionBar(component);
    }

    /**
     * Plays a sound with custom properties set utilizing a QSound object
     * @param qSound
     * @param randomPitch
     */

    public void playSound(@Nullable QSound qSound, boolean randomPitch) {
        if(qSound == null) return;
        float pitch = qSound.getPitch();
        this.playSound(qSound.getSound(), qSound.getVolume(),
                randomPitch ? RandomUtil.between(Math.max(0, pitch - 0.25f), Math.min(2, pitch + 0.25f)) : pitch,
                qSound.getCategory(), qSound.getPlayType());
    }

    /**
     * Plays a sound with custom properties that can each be specified.
     * @param sound
     * @param volume
     * @param pitch
     * @param category
     * @param playType
     */

    public void playSound(@NotNull Sound sound, float volume, float pitch, @NotNull SoundCategory category, @Nullable PlayType playType) {
        switch(playType) {
            case PlayType.BROADCAST_CUR_LOC -> super.getWorld().playSound(super.getLocation(), sound, category, volume, pitch);
            case PlayType.PLAYER_CUR_LOC -> super.playSound(super.getLocation(), sound, volume, pitch);
            case PlayType.PLAYER -> super.playSound(this, sound, volume, pitch);
            case null -> super.getWorld().playSound(super.getLocation(), sound, category, volume, pitch);
        }
    }
}