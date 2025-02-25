package net.qilla.qlibrary.menu;

import com.google.common.base.Preconditions;
import net.qilla.qlibrary.data.PlayerData;
import net.qilla.qlibrary.menu.socket.Slots;
import net.qilla.qlibrary.menu.socket.QSocket;
import net.qilla.qlibrary.menu.socket.Socket;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.util.sound.QSounds;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class QSearchMenu<T> extends QDynamicMenu<T> implements SearchMenu<T> {

    private List<T> localPopulation;

    public QSearchMenu(@NotNull Plugin plugin, @NotNull PlayerData<?> playerData, @NotNull List<T> itemPopulation) {
        super(plugin, playerData, itemPopulation);
        Preconditions.checkNotNull(itemPopulation, "Item Population cannot be null");
        this.localPopulation = new ArrayList<>(itemPopulation);

        super.addSocket(searchSocket());
    }

    @Override
    public void populateModular() {
        int fromIndex = Math.min(super.getShiftIndex(), localPopulation.size());
        int toIndex = Math.min(fromIndex + dynamicConfig().dynamicIndexes().size(), this.localPopulation.size());
        List<T> shiftedList = new ArrayList<>(localPopulation).subList(fromIndex, toIndex);

        Iterator<Integer> iterator = dynamicConfig().dynamicIndexes().iterator();
        List<Socket> socketList = new ArrayList<>();

        shiftedList.forEach(item -> {
            if(iterator.hasNext()) {
                int index = iterator.next();
                Socket socket = createSocket(index, item);
                if(socket != null) socketList.add(socket);
                else super.addSocket(new QSocket(index, Slots.EMPTY_MODULAR));
            }
        });
        super.addSocket(socketList);
        iterator.forEachRemaining(index -> super.addSocket(new QSocket(index, Slots.EMPTY_MODULAR)));
    }

    @Override
    public boolean searchFor() {
        List<String> signText = List.of("^^^^^^^^^^^^^^^", "Keywords to", "narrow search");
        super.requestSignInput(signText, result -> {
            if(!result.isBlank()) {
                localPopulation = getItemPopulation().stream()
                        .filter(item -> matchSearchCriteria(item, result))
                        .toList();
                try {
                    super.setShiftIndex(0);
                    this.refreshSockets();
                    super.addSocket(resetSearchSocket());
                    getPlayer().playSound(QSounds.Menu.SIGN_INPUT, true);
                } catch(NumberFormatException ignored) {
                }
            }
            super.open(false);
        });
        return true;
    }

    @Override
    public boolean rotateNext(@NotNull InventoryClickEvent event, int amount) {
        ClickType clickType = event.getClick();

        if(clickType.isShiftClick() && clickType.isLeftClick()) {
            for(int i = 0; i < super.getDynamicSlots().size() / amount; i++) {
                if(super.setShiftIndex(super.getShiftIndex() + amount) + super.getDynamicSlots().size() > localPopulation.size()) {
                    if((super.getShiftIndex() + super.getDynamicSlots().size()) % localPopulation.size() >= amount) super.setShiftIndex(super.getShiftIndex() - amount);
                    break;
                }
            }
        } else if(clickType.isLeftClick()) super.setShiftIndex(super.getShiftIndex() + amount);
        else return false;

        this.refreshSockets();
        return true;
    }

    @Override
    public boolean rotatePrevious(@NotNull InventoryClickEvent event, int amount) {
        ClickType clickType = event.getClick();
        if(clickType.isShiftClick() && clickType.isLeftClick()) {
            for(int i = 0; i < super.getDynamicSlots().size() / amount; i++) {
                if(super.setShiftIndex(super.getShiftIndex() - amount) < 0) break;
            }
        } else if(clickType.isLeftClick()) super.setShiftIndex(super.getShiftIndex() - amount);
        else return false;

        this.refreshSockets();
        return true;
    }

    @Override
    public void refreshSockets() {
        if(super.getShiftIndex() + super.getDynamicSlots().size() < localPopulation.size()) super.addSocket(nextSocket());
        else super.removeSocket(super.nextSocket().index());
        if(super.getShiftIndex() > 0) super.addSocket(super.previousSocket());
        else super.removeSocket(super.previousSocket().index());
        if(super.getShiftIndex() < 0) super.setShiftIndex(0);

        this.populateModular();
    }

    @Override
    public boolean resetSearch() {
        localPopulation = new ArrayList<>(super.getItemPopulation());
        super.removeSocket(searchConfig().resetSearchIndex());
        super.setShiftIndex(0);
        this.refreshSockets();
        return true;
    }

    @Override
    public boolean matchSearchCriteria(@NotNull T item, @NotNull String search) {
        return getString(item).toLowerCase().contains(search.toLowerCase());
    }

    @Override
    public @NotNull QSocket searchSocket() {
        return new QSocket(searchConfig().searchIndex(), Slots.SEARCH, event -> {
            ClickType clickType = event.getClick();
            if(!clickType.isLeftClick()) return false;
            return this.searchFor();
        }, CooldownType.MENU_CLICK);
    }

    @Override
    public @NotNull QSocket resetSearchSocket() {
        return new QSocket(searchConfig().resetSearchIndex(), Slots.RESET_SEARCH, event -> {
            ClickType clickType = event.getClick();
            if(!clickType.isLeftClick()) return false;
            return this.resetSearch();
        }, CooldownType.MENU_CLICK);
    }
}