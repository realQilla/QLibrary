package net.qilla.qlibrary.menu.socket;

import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.util.QSounds;
import org.bukkit.Material;
import java.util.List;

public class Slots {

    public static final Slot MISSING = Slot.of(slot -> slot
            .material(Material.BARRIER)
            .displayName(MiniMessage.miniMessage().deserialize("<red>Missing Item"))
            .lore(ItemLore.lore(List.of(
                    MiniMessage.miniMessage().deserialize("<!italic><gray>This item is missing"),
                    MiniMessage.miniMessage().deserialize("<!italic><gray>from the menu!")
            )))
    );

    public static final Slot RETURN = Slot.of(slot -> slot
            .material(Material.BELL)
            .displayName(MiniMessage.miniMessage().deserialize("<red>Return"))
            .lore(ItemLore.lore(List.of(
                    MiniMessage.miniMessage().deserialize("<!italic><gray><key:key.mouse.left> to return to your"),
                    MiniMessage.miniMessage().deserialize("<!italic><gray>previously accessed menu")
            )))
            .clickSound(QSounds.RETURN_MENU)
    );

    public static final Slot EMPTY_MODULAR_SLOT = Slot.of(slot -> slot
            .material(Material.PALE_OAK_BUTTON)
            .hideTooltip(true)
    );

    public static final Slot FILLER = Slot.of(slot -> slot
            .hideTooltip(true)
            .material(null)
    );

    public static final Slot PREVIOUS = Slot.of(slot -> slot
            .material(Material.ARROW)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Previous"))
            .lore(ItemLore.lore(List.of(
                    MiniMessage.miniMessage().deserialize("<!italic><gray><key:key.mouse.left> to shift the menu backwards")
            )))
            .clickSound(QSounds.MENU_ROTATE_PREVIOUS)
    );

    public static final Slot NEXT = Slot.of(slot -> slot
            .material(Material.SPECTRAL_ARROW)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Next"))
            .lore(ItemLore.lore(List.of(
                    MiniMessage.miniMessage().deserialize("<!italic><gray><key:key.mouse.left> to shift the menu forwards")
            )))
            .clickSound(QSounds.MENU_ROTATE_NEXT)
    );

    public static final Slot SEARCH = Slot.of(slot -> slot
            .material(Material.OAK_SIGN)
            .displayName(MiniMessage.miniMessage().deserialize("<white>Search"))
            .lore(ItemLore.lore(List.of(
                    MiniMessage.miniMessage().deserialize("<!italic><gray><key:key.mouse.left> to search for"),
                    MiniMessage.miniMessage().deserialize("<!italic><gray>something more specific")
            )))
            .clickSound(QSounds.MENU_CLICK_ITEM)
    );

    public static final Slot RESET_SEARCH = Slot.of(builder2 -> builder2
            .material(Material.BARRIER)
            .displayName(MiniMessage.miniMessage().deserialize("<red>Reset Search"))
            .lore(ItemLore.lore(List.of(MiniMessage.miniMessage().deserialize("<!italic><gray>Resets your currently searched term")
            )))
            .clickSound(QSounds.RESET)
    );
}
