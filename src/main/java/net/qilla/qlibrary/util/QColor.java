package net.qilla.qlibrary.util;

import net.qilla.qlibrary.items.MaterialRepresentation;
import org.bukkit.Material;

public enum QColor implements MaterialRepresentation {
    BLACK(0x000000, "Black", Material.BLACK_CONCRETE),
    DARK_BLUE(0x0000AA, "Dark Blue", Material.BLUE_CONCRETE),
    DARK_GREEN(0x00AA00, "Green", Material.GREEN_CONCRETE),
    DARK_AQUA(0x00AAAA, "Cyan", Material.CYAN_CONCRETE),
    DARK_RED(0xAA0000, "Maroon", Material.RED_CONCRETE),
    DARK_PURPLE(0xAA00AA, "Purple", Material.PURPLE_CONCRETE),
    GOLD(0xFFAA00, "Gold", Material.ORANGE_CONCRETE),
    GRAY(0xAAAAAA, "Gray", Material.LIGHT_GRAY_CONCRETE),
    DARK_GRAY(0x555555, "Dark Gray", Material.GRAY_CONCRETE),
    BLUE(0x5555FF, "Blue", Material.BLUE_CONCRETE_POWDER),
    GREEN(0x55FF55, "Lime", Material.LIME_CONCRETE),
    AQUA(0x55FFFF, "Cyan", Material.LIGHT_BLUE_CONCRETE),
    RED(0xFF5555 , "Red", Material.RED_CONCRETE_POWDER),
    LIGHT_PURPLE(0xFF55FF, "Magenta", Material.MAGENTA_CONCRETE),
    YELLOW(0xFFFF55, "Yellow", Material.YELLOW_CONCRETE),
    WHITE(0xFFFFFF, "White", Material.WHITE_CONCRETE),
    GOLD_COIN(0xDDD605, "Gold Coin", Material.GOLD_NUGGET),
    MATERIAL_QUARTS(0xE3D4D1, "Quartz", Material.QUARTZ),
    MATERIAL_IRON(0xCECACA, "Iron Ingot", Material.IRON_INGOT),
    MATERIAL_NETHERITE(0x443A3B, "Netherite Ingot", Material.NETHERITE_INGOT),
    MATERIAL_REDSTONE(0x971607, "Redstone", Material.REDSTONE),
    MATERIAL_COPPER(0xB4684D, "Copper Ingot", Material.COPPER_INGOT),
    MATERIAL_GOLD(0xDEB12D, "Gold Ingot", Material.GOLD_INGOT),
    MATERIAL_EMERALD(0x47A036, "Emerald", Material.EMERALD),
    MATERIAL_DIAMOND(0x2CBAA8, "Diamond", Material.DIAMOND),
    MATERIAL_LAPIS(0x21497B, "Lapis Lazuli", Material.LAPIS_LAZULI),
    AMETHYST(0x9A5CC6, "Amethyst", Material.AMETHYST_SHARD),
    RESIN(0xEB7114, "Resin", Material.RESIN_CLUMP);

    private final int color;
    private final String name;
    private final Material representation;

    QColor(int color, String name, Material representation) {
        this.color = color;
        this.name = name;
        this.representation = representation;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    @Override
    public Material getRepresentation() {
        return representation;
    }
}
