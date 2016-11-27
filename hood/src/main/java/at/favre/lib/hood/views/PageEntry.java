package at.favre.lib.hood.views;


import android.support.annotation.Nullable;

public interface PageEntry<T> {

    T getValue();

    ViewTemplate<T> getViewTemplate();

    boolean isStaticContent();

    @Nullable String toLogString();
}
