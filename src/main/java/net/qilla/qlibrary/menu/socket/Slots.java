package net.qilla.qlibrary.menu.socket;

import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.sound.QSounds;
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
            .clickSound(QSounds.Menu.RETURN_MENU)
    );

    public static final QSlot EMPTY_MODULAR = QSlot.of(slot -> slot
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
            .clickSound(QSounds.Menu.MENU_ROTATE_PREVIOUS)
    );

    public static final QSlot NEXT = QSlot.of(slot -> slot
            .material(Material.SPECTRAL_ARROW)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Next"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to shift this menu forwards")
            )))
            .clickSound(QSounds.Menu.MENU_ROTATE_NEXT)
    );

    public static final QSlot SEARCH = QSlot.of(slot -> slot
            .material(Material.OAK_SIGN)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Search"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to search")
            )))
            .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
    );

    public static final QSlot RESET_SEARCH = QSlot.of(builder2 -> builder2
            .material(Material.BARRIER)
            .displayName(MiniMessage.miniMessage().deserialize("<red>Reset Search"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to reset your search query")
            )))
            .clickSound(QSounds.Menu.RESET)
    );

    public static final Slot SAVED_CHANGES = QSlot.of(builder -> builder
            .material(Material.SLIME_BALL)
            .displayName(MiniMessage.miniMessage().deserialize("<green><bold>SAVE</bold> to Config"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to save any made changes.")
            )))
            .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
    );

    public static final Slot RELOADED_CHANGES = QSlot.of(builder -> builder
            .material(Material.SNOWBALL)
            .displayName(MiniMessage.miniMessage().deserialize("<aqua><bold>RELOAD</bold> from Config"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to load the config, undoing any unsaved changes.")
            )))
            .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
    );

    public static final Slot CLEAR_SAVED = QSlot.of(builder -> builder
            .material(Material.FIRE_CHARGE)
            .displayName(MiniMessage.miniMessage().deserialize("<red><bold>CLEAR</bold> Config"))
            .lore(ItemLore.lore(List.of(
                    Component.empty(),
                    MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>① <key:key.mouse.left></gold> to clear any stored data.")
            )))
            .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
    );
}
