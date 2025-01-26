package net.qilla.qlibrary.menu.socket;

import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.sound.MenuSound;
import org.bukkit.Material;
import java.util.List;

public class Slots {

    public static final QSlot MISSING = QSlot.of(slot -> slot
            .material(Material.BARRIER)
            .displayName(MiniMessage.miniMessage().deserialize("<red>⚠ Missing Item"))
    );

    public static final QSlot RETURN = QSlot.of(slot -> slot
            .material(Material.BELL)
            .displayName(MiniMessage.miniMessage().deserialize("<red>Return"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to return to the previous menu")
            )))
            .clickSound(MenuSound.RETURN_MENU)
    );

    public static final QSlot EMPTY_MODULAR_Q_SLOT = QSlot.of(slot -> slot
            .material(Material.PALE_OAK_BUTTON)
            .hideTooltip(true)
    );

    public static final QSlot FILLER = QSlot.of(slot -> slot
            .hideTooltip(true)
            .material(null)
    );

    public static final QSlot PREVIOUS = QSlot.of(slot -> slot
            .material(Material.ARROW)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Previous"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to shift this menu backwards")
            )))
            .clickSound(MenuSound.MENU_ROTATE_PREVIOUS)
    );

    public static final QSlot NEXT = QSlot.of(slot -> slot
            .material(Material.SPECTRAL_ARROW)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Next"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to shift this menu forwards")
            )))
            .clickSound(MenuSound.MENU_ROTATE_NEXT)
    );

    public static final QSlot SEARCH = QSlot.of(slot -> slot
            .material(Material.OAK_SIGN)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Search"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to search")
            )))
            .clickSound(MenuSound.MENU_CLICK_ITEM)
    );

    public static final QSlot RESET_SEARCH = QSlot.of(builder2 -> builder2
            .material(Material.BARRIER)
            .displayName(MiniMessage.miniMessage().deserialize("<red>Reset Search"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to reset your search query")
            )))
            .clickSound(MenuSound.RESET)
    );
}
