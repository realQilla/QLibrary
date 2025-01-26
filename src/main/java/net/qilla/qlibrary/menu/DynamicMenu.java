package net.qilla.qlibrary.menu;

import net.qilla.qlibrary.menu.socket.Socket;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface DynamicMenu<T> {

    void populateModular();

    boolean rotateNext(InventoryClickEvent event, int amount);

    boolean rotatePrevious(InventoryClickEvent event, int amount);

    @NotNull Collection<T> getItemPopulation();

    int getShiftIndex();

    int setShiftIndex(int shiftIndex);

    @NotNull List<Integer> getDynamicSlots();

    @NotNull Socket nextSocket();

    @NotNull Socket previousSocket();

    @NotNull DynamicConfig dynamicConfig();

    @Nullable Socket createSocket(int index, T item);
}