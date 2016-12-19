package at.favre.lib.hood.interfaces;


import android.support.annotation.Nullable;

/**
 * One entry in a {@link Page} containing a generic value.
 * It defines its value and how it is drawn in the UI (see {@link ViewTemplate}
 * @param <T>
 */
public interface PageEntry<T> {

    /**
     * @return the value represented by this entry
     */
    T getValue();

    /**
     * @return the view-template that defines how this view will be drawn
     */
    ViewTemplate<T> getViewTemplate();

    /**
     * If this entry does not need to log its content (e.g. if it just represents a "spacer") may
     * return null. Otherwise a short log string representing the content of this entry.
     * @return a string used to log to console or null
     */
    @Nullable String toLogString();

    /**
     * This should refresh the UI content. Will be called by the framework.
     */
    void refresh();
}
