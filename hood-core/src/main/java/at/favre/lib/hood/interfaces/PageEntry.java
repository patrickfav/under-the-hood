package at.favre.lib.hood.interfaces;


import android.support.annotation.Nullable;

/**
 * One entry in a {@link Page} containing a generic value.
 * It defines its value and how it is drawn in the UI (see {@link ViewTemplate}
 *
 * @param <T>
 */
public interface PageEntry<T> {

    /**
     * @return the value represented by this entry
     */
    T getValue();

    /**
     * Creates a new ViewTemplate object
     *
     * @return the view-template that defines how this view will be drawn
     */
    ViewTemplate<T> createViewTemplate();

    /**
     * Similar to RecyclerView.Adapter#getItemViewType return a view-type to be able
     * to recognize which views can be recycled. Free range is up to (excluding) 2^16 (65536).
     *
     * See {@link ViewTemplate#getViewType()}
     *
     * @return an int unique to the same type (see {@link ViewTypes} on
     * what build-in view-types are already defined.
     */
    int getViewType();

    /**
     * If this entry does not need to log its content (e.g. if it just represents a "spacer") may
     * return null. Otherwise a short log string representing the content of this entry.
     *
     * @return a string used to log to console or null
     */
    @Nullable
    String toLogString();

    /**
     * Disables any output when trying to log. {@link #toLogString()} will always
     * return null after calling this method.
     */
    void disableLogging();

    /**
     * This should refresh the UI content. Will be called by the framework.
     */
    void refresh();
}
