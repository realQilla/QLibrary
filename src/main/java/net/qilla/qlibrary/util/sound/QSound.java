package net.qilla.qlibrary.util.sound;

import com.google.common.base.Preconditions;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;

/**
 * Represents configurable settings for playing a sound to or around a player.
 * This class encapsulates details such as the sound to play, its volume, pitch,
 * category, and the type of playback.
 */

public final class QSound {

    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final SoundCategory category;
    private final PlayType playType;

    private QSound(@NotNull Sound sound, float volume, float pitch, @NotNull SoundCategory category, @NotNull PlayType playType) {
        Preconditions.checkNotNull(sound, "Sound cannot be null");
        Preconditions.checkNotNull(category, "SoundCategory cannot be null");
        Preconditions.checkNotNull(playType, "PlayType cannot be null");
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.category = category;
        this.playType = playType;
    }

    public static QSound of(@NotNull Sound sound, float volume, float pitch, @NotNull SoundCategory soundCategory, @NotNull PlayType playType) {
        return new QSound(sound, volume, pitch, soundCategory, playType);
    }

    public static QSound of(@NotNull Sound sound, float volume, float pitch, @NotNull PlayType plaType) {
        return new QSound(sound, volume, pitch, SoundCategory.MASTER, plaType);
    }

    public Sound getSound() {
        return this.sound;
    }

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public SoundCategory getCategory() {
        return this.category;
    }

    public PlayType getPlayType() {
        return this.playType;
    }
}
