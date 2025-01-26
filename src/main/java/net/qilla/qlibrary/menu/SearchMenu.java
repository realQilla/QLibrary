package net.qilla.qlibrary.menu;

import net.qilla.qlibrary.menu.socket.QSocket;
import org.jetbrains.annotations.NotNull;

public interface SearchMenu<T>{

    boolean searchFor();

    boolean resetSearch();

    boolean matchSearchCriteria(T item, String search);

    @NotNull QSocket searchSocket();

    @NotNull QSocket resetSearchSocket();

    @NotNull String getString(T item);

    @NotNull SearchConfig searchConfig();
}
