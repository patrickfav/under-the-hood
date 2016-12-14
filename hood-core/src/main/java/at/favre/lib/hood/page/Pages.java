package at.favre.lib.hood.page;


import android.support.annotation.NonNull;

import java.util.List;

public interface Pages {

    Page addNewPage();

    Page addNewPage(String title);

    Page getFirstPage();

    Page getPage(int index);

    List<Page> getAll();

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
    void logPages();

    /**
     * The config should be passed to this class
     *
     * @return the currently set config
     */
    @NonNull
    Config getConfig();
}
