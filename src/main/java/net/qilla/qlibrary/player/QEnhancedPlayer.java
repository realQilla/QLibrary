package net.qilla.qlibrary.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.qilla.qlibrary.util.sound.PlayType;
import net.qilla.qlibrary.util.sound.QSound;
import net.qilla.qlibrary.util.tools.RandomUtil;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QEnhancedPlayer extends CraftPlayer implements EnhancedPlayer {

    public QEnhancedPlayer(CraftPlayer craftPlayer) {
        super((CraftServer) craftPlayer.getServer(), craftPlayer.getHandle());
    }

    @Override
    public void sendPacket(Packet<?> packet) {
        this.getHandle().connection.send(packet);
    }

    public void broadcastPacket(Packet<?> packet) {
        this.getHandle().serverLevel().getChunkSource().broadcastAndSend(this.getHandle(), packet);
    }

    @Override
    public @NotNull ServerPlayer getHandle() {
        return super.getHandle();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        super.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    @Override
    public void sendMessage(@NotNull Component component) {
        super.sendMessage(component);
    }

    @Override
    public void sendActionBar(@NotNull String message) {
        super.sendActionBar(MiniMessage.miniMessage().deserialize(message));
    }

    @Override
    public void sendActionBar(@NotNull Component component) {
        super.sendActionBar(component);
    }

    @Override
    public void playSound(@Nullable QSound qSound, boolean randomPitch) {
        if(qSound == null) return;
        float pitch = qSound.getPitch();
        this.playSound(qSound.getSound(), qSound.getVolume(),
                randomPitch ? RandomUtil.between(Math.max(0, pitch - 0.25f), Math.min(2, pitch + 0.25f)) : pitch,
                qSound.getCategory(), qSound.getPlayType());
    }

    @Override
    public void playSound(@NotNull Sound sound, float volume, float pitch, @NotNull SoundCategory category, @Nullable PlayType playType) {
        switch(playType) {
            case PlayType.BROADCAST_CUR_LOC -> super.getWorld().playSound(super.getLocation(), sound, category, volume, pitch);
            case PlayType.PLAYER_CUR_LOC -> super.playSound(super.getLocation(), sound, volume, pitch);
            case PlayType.PLAYER -> super.playSound(this, sound, volume, pitch);
            case null -> super.getWorld().playSound(super.getLocation(), sound, category, volume, pitch);
        }
    }
}