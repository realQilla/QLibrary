package net.qilla.qlibrary.menu.socket;

import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.qilla.qlibrary.util.sound.QSound;
import org.bukkit.inventory.ItemStack;

public interface Slot {

    public Slot modifyDisplayName(Component displayName);

    public Slot modifyLore(ItemLore lore);

    public ItemStack getItem();

    public QSound getAppearSound();

    public QSound getClickSound();
}