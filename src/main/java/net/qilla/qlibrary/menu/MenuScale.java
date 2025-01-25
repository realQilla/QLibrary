package net.qilla.qlibrary.menu;

/**
 * Menu scale enum for easy configuration of menus
 */

public enum MenuScale {

    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    private final int size;

    MenuScale(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
