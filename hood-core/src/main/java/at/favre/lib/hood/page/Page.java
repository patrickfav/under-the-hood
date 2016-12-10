package at.favre.lib.hood.page;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Represents the model of a full page containing all entries. This can be used to render all entries.
 */
public interface Page {
    /**
     * @return list of all defined entries
     */
    List<PageEntry> getEntries();

    /**
     * Gets the view-template for the {@link ViewTemplate#getViewType()}. Information
     * is gathered from the entries list.
     * @param viewType see {@link ViewTemplate#getViewType()}
     * @return map of view-types and its corresponded templates
     */
    ViewTemplate<?> getTemplateForViewType(int viewType);

    /**
     * Adds a single page entry
     * @param pageEntry might be null, in which case it won't be added
     */
    void addEntry(@Nullable PageEntry<?> pageEntry);

    /**
     * Adds a list of entries
     * @param entries might be null, in which case it won't be added
     */
    void addEntries(@Nullable List<PageEntry<?>> entries);

    /**
     * Calls {@link PageEntry#refresh()} on every entry.
     */
    void refreshData();

    /**
     * The default implementation of what should happen when a component wants to log something.
     * You can set e.g. tag, formatting, etc.
     */
    void log(String message);

    /**
     * Logs the whole page data
     */
    void logPage();

    /**
     * Clears all the entries
     */
    void removeEntries();
}
