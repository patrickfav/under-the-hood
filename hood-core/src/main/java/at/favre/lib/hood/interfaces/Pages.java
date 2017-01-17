package at.favre.lib.hood.interfaces;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface Pages {

    /**
     * Adds a new page to the internal list with default title
     *
     * @return the newly created page
     */
    Page addNewPage();

    /**
     * Adds a new page to the internal list with given title
     * @param title of the page
     * @return the newly created page
     */
    Page addNewPage(String title);

    /**
     * Same as {@link #getPage(int)} with parameter 0, but throws exception,
     * ie. making it null safe.
     *
     * @return the first page
     * @throws IllegalStateException if no page was added
     */
    @NonNull
    Page getFirstPage();

    /**
     * Retrieves a page for the specific index
     *
     * @param index of the requested page (beginning with 0)
     * @return the page if one was found, null otherwise
     */
    @Nullable
    Page getPage(int index);

    /**
     * Returns the actual reference, not a copy.
     * @return all contained pages
     */
    List<Page> getAll();

    /**
     * The count of contained pages
     * @return size
     */
    int size();

    /**
     * Calls {@link PageEntry#refresh()} on every entry.
     *
     * @param refreshAlsoExpensiveValues if true will also refresh {@link at.favre.lib.hood.interfaces.values.DynamicValue.Async} type values
     */
    void refreshData(boolean refreshAlsoExpensiveValues);

    /**
     * The default implementation of what should happen when a component wants to log something.
     * You can set e.g. tag, formatting, etc.
     */
    void log(String message);

    /**
     * Logs the whole page data
     */
    void logPages();

    /**
     * The config should be passed to this class
     *
     * @return the currently set config
     */
    @NonNull
    Config getConfig();
}
