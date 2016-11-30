package at.favre.lib.hood.page;


import android.support.annotation.Nullable;

public interface PageEntry<T> {

    T getValue();

    ViewTemplate<T> getViewTemplate();

    @Nullable String toLogString();

    void refresh();
}
