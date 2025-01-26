package net.qilla.qlibrary.util.sound;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;

public class MenuSound {

    public static final QSound RETURN_MENU = QSound.of(Sound.BLOCK_NOTE_BLOCK_BELL, 0.25f, 1f, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_ITEM_APPEAR = QSound.of(Sound.ENTITY_CHICKEN_EGG, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_CLAIM_ITEM = QSound.of(Sound.ENTITY_HORSE_SADDLE, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound GET_ITEM = QSound.of(Sound.ENTITY_HORSE_SADDLE, 0.25f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound SIGN_INPUT = QSound.of(Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound RESET = QSound.of(Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_CLICK_ITEM = QSound.of(Sound.BLOCK_DECORATED_POT_INSERT, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_GET_ITEM = QSound.of(Sound.ITEM_BUNDLE_REMOVE_ONE, 1f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_INPUT_ITEM = QSound.of(Sound.ITEM_BUNDLE_INSERT, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound ITEM_DELETE = QSound.of(Sound.BLOCK_LAVA_POP, 0.5f, 1, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_ROTATE_NEXT = QSound.of(Sound.ENTITY_BREEZE_JUMP, 0.25f, 1f, SoundCategory.PLAYERS, PlayType.PLAYER);
    public static final QSound MENU_ROTATE_PREVIOUS = QSound.of(Sound.ENTITY_BREEZE_LAND, 0.75f, 1.75f, SoundCategory.PLAYERS, PlayType.PLAYER);
}
