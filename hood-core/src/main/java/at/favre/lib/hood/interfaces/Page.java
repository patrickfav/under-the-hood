package at.favre.lib.hood.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Represents the model of a full page containing all entries. This can be used to render all entries.
 */
public interface Page {

    /**
     * A title for a page can be set.
     *
     * @return title as shown in ui
     */
    String getTitle();

    /**
     * @return list of all defined entries
     */
    @NonNull
    List<PageEntry> getEntries();

    /**
     * Gets the view-template for the {@link ViewTemplate#getViewType()}. Information
     * is gathered from the entries list.
     *
     * @param viewType see {@link ViewTemplate#getViewType()}
     * @return map of view-types and its corresponded templates
     */
    ViewTemplate<?> getTemplateForViewType(int viewType);

    /**
     * Adds a single page entry
     *
     * @param pageEntry might be null, in which case it won't be added
     */
    void add(@Nullable PageEntry<?> pageEntry);

    /**
     * Adds a list of entries
     *
     * @param entries might be null, in which case it won't be added
     */
    void add(@Nullable List<PageEntry<?>> entries);

    /**
     * Adds a whole section to the page
     *
     * @param section
     */
    void add(@NonNull Section section);

    /**
     * Calls {@link PageEntry#refresh()} on every entry.
     */
    void refreshData();

    /**
     * Logs the whole page data
     */
    void logPage();

    /**
     * Clears all the entries attached to this view
     */
    void removeEntries();

    /**
     * Disables any output or action when trying to log this page. Will disable logging on
     * all attached page entries and every entry that gets added later. Affects calls like
     * {@link #logPage()} and {@link PageEntry#toLogString()}
     */
    void disableLogging();

    /**
     * The config should be passed to this class
     *
     * @return the currently set config
     */
    @NonNull
    Config getConfig();

    /**
     * Creates a flat (no sections) map all data elements
     * that can be represented in a key/value manner
     *
     * @return data map
     */
    Map<String, String> createDataMap();
}
