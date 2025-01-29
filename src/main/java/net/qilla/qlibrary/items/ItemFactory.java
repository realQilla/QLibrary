package net.qilla.qlibrary.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemFactory {

    public static ItemStack getCleanItem(Material material, int amount) {
        ItemStack cleanItem = ItemStack.of(material, amount);
        cleanItem.getDataTypes().forEach(cleanItem::unsetData);
        cleanItem.setData(DataComponentTypes.ITEM_MODEL, material.getKey());

        return cleanItem;
    }

    public static ItemStack getCleanFakeItem(Material material, int amount) {
        ItemStack cleanItem = ItemStack.of(Material.STICK, amount);
        cleanItem.getDataTypes().forEach(cleanItem::unsetData);
        cleanItem.setData(DataComponentTypes.ITEM_MODEL, material.getKey());
        return cleanItem;
    }
}
