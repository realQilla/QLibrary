package net.qilla.qlibrary.items;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.function.Consumer;

public class ItemFactory {

    public static ItemStack getCleanItem(Material material, int amount) {
        ItemStack cleanItem = wipeData(ItemStack.of(material, amount));
        cleanItem.setData(DataComponentTypes.ITEM_MODEL, material.getKey());

        return cleanItem;
    }

    public static ItemStack getCleanItem(Material material, Material displayMaterial, int amount) {
        ItemStack cleanItem = wipeData(ItemStack.of(material, amount));
        cleanItem.setData(DataComponentTypes.ITEM_MODEL, displayMaterial.getKey());
        return cleanItem;
    }

    public static ItemStack builder(Material material, Consumer<ItemStack> builder) {
        ItemStack itemStack = getCleanItem(material, 1);
        builder.accept(itemStack);
        return itemStack;
    }

    public static <T> ItemStack applyData(@NotNull ItemStack itemStack, Map<DataComponentType.Valued<T>, T> dataMap) {
        dataMap.forEach(itemStack::setData);
        return itemStack;
    }

    private static ItemStack wipeData(ItemStack itemStack) {
        itemStack.getDataTypes().forEach(itemStack::unsetData);
        return itemStack;
    }
}