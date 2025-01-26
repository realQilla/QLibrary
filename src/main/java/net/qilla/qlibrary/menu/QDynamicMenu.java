package net.qilla.qlibrary.menu;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.menu.socket.Slots;
import net.qilla.qlibrary.menu.socket.QSocket;
import net.qilla.qlibrary.menu.socket.Socket;
import net.qilla.qlibrary.player.CooldownType;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class QDynamicMenu<T> extends QStaticMenu implements DynamicMenu<T> {

    private final List<Integer> dynamicSlots;
    private final Collection<T> itemPopulation;
    private int shiftIndex;

    protected QDynamicMenu(@NotNull Plugin plugin, @NotNull PlayerData playerData, @NotNull Collection<T> itemPopulation) {
        super(plugin, playerData);
        Preconditions.checkNotNull(itemPopulation, "Collection cannot be null");
        this.itemPopulation = itemPopulation;
        this.dynamicSlots = dynamicConfig().dynamicIndexes();
        this.shiftIndex = 0;

        if(itemPopulation.size() > dynamicSlots.size()) super.addSocket(nextSocket());
    }

    @Override
    public void finalizeMenu() {
        getTotalIndexes().stream()
                .filter(index -> !dynamicSlots.contains(index))
                .forEach(index -> super.getInventory().addItem(Slots.FILLER.getItem()));
    }

    @Override
    public void populateModular() {
        int fromIndex = Math.min(this.shiftIndex, this.itemPopulation.size());
        int toIndex = Math.min(fromIndex + dynamicSlots.size(), this.itemPopulation.size());
        List<T> shiftedList = new ArrayList<>(this.itemPopulation).subList(fromIndex, toIndex);

        Iterator<Integer> iterator = dynamicSlots.iterator();
        List<Socket> socketList = new ArrayList<>();

        for(T item : shiftedList) {
            if(iterator.hasNext()) {
                int index = iterator.next();
                Socket socket = createSocket(index, item);
                if(socket != null) socketList.add(socket);
                else super.addSocket(new QSocket(index, Slots.EMPTY_MODULAR_Q_SLOT));
            }
        }
        super.addSocket(socketList);
        iterator.forEachRemaining(index -> super.addSocket(new QSocket(index, Slots.EMPTY_MODULAR_Q_SLOT)));
    }

    @Override
    public boolean rotateNext(InventoryClickEvent event, int amount) {
        ClickType clickType = event.getClick();

        if(clickType.isShiftClick() && clickType.isLeftClick()) {
            for(int i = 0; i < dynamicSlots.size() / amount; i++) {
                if((shiftIndex += amount) + dynamicSlots.size() > itemPopulation.size()) {
                    if((shiftIndex + dynamicSlots.size()) % itemPopulation.size() >= amount) shiftIndex -= amount;
                    break;
                }
            }
        } else if(clickType.isLeftClick()) shiftIndex += amount;
        else return false;

        this.refreshSockets();
        return true;
    }

    @Override
    public boolean rotatePrevious(InventoryClickEvent event, int amount) {
        ClickType clickType = event.getClick();
        if(clickType.isShiftClick() && clickType.isLeftClick()) {
            for(int i = 0; i < dynamicSlots.size() / amount; i++) {
                if((shiftIndex -= amount) < 0) break;
            }
        }
        else if(clickType.isLeftClick()) shiftIndex -= amount;
        else return false;

        this.refreshSockets();
        return true;
    }

    @Override
    public void refreshSockets() {
        if(shiftIndex + dynamicSlots.size() < itemPopulation.size()) super.addSocket(nextSocket());
        else super.removeSocket(nextSocket().index());
        if(shiftIndex > 0) super.addSocket(previousSocket());
        else super.removeSocket(previousSocket().index());
        if(shiftIndex < 0) shiftIndex = 0;

        this.populateModular();
    }

    @Override
    public @NotNull Collection<T> getItemPopulation() {
        return itemPopulation;
    }

    @Override
    public int getShiftIndex() {
        return shiftIndex;
    }

    @Override
    public int setShiftIndex(int shiftIndex) {
        return this.shiftIndex = shiftIndex;
    }

    @Override
    public @NotNull List<Integer> getDynamicSlots() {
        return dynamicSlots;
    }

    @Override
    public @NotNull QSocket nextSocket() {
        return new QSocket(dynamicConfig().nextIndex(), Slots.NEXT, event -> {
            ClickType clickType = event.getClick();
            if(clickType.isLeftClick()) {
                return this.rotateNext(event, dynamicConfig().shiftAmount());
            } else return false;
        }, CooldownType.MENU_ROTATE);
    }

    @Override
    public @NotNull QSocket previousSocket() {
        return new QSocket(dynamicConfig().previousIndex(), Slots.PREVIOUS, event -> {
            ClickType clickType = event.getClick();
            if(clickType.isLeftClick()) {
                return this.rotatePrevious(event, dynamicConfig().shiftAmount());
            } else return false;
    }, CooldownType.MENU_ROTATE);
    }
}