package net.qilla.qlibrary.player;

/**
 * Cooldowns types to be used by the QLibrary
 */

public enum CooldownType {
    OPEN_MENU(250),
    MENU_CLICK(100),
    MENU_ROTATE(100),
    GET_ITEM(100);

    final long cooldown;

    CooldownType(long millis) {
        this.cooldown = millis;
    }

    public long getMillis() {
        return this.cooldown;
    }
}
