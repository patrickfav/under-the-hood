package at.favre.lib.hood.page;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;

/**
 * Used to prevent changes to the pages object. Is a simple delegate.
 */
class UnmodifiablePages implements Pages {
    private final Pages debugPages;
    private final List<Page> unmodifiablePages;

    UnmodifiablePages(Pages pages) {
        this.debugPages = pages;
        this.unmodifiablePages = createImmutablePagesList(pages);
    }

    private List<Page> createImmutablePagesList(Pages pages) {
        List<Page> copy = new ArrayList<>(pages.size());
        for (Page page : pages.getAll()) {
            copy.add(new ImmutablePageDelegate(page));
        }
        return Collections.unmodifiableList(copy);
    }

    public Page addNewPage() {
        throw new UnsupportedOperationException("cannot change page in use");
    }

    public Page addNewPage(String title) {
        throw new UnsupportedOperationException("cannot change page in use");
    }

    @NonNull
    public Page getFirstPage() {
        return debugPages.getFirstPage();
    }

    @Nullable
    public Page getPage(int index) {
        return debugPages.getPage(index);
    }

    public List<Page> getAll() {
        return unmodifiablePages;
    }

    public int size() {
        return debugPages.size();
    }

    public void refreshData(boolean refreshAlsoExpensiveValues) {
        debugPages.refreshData(refreshAlsoExpensiveValues);
    }

    public void log(String message) {
        debugPages.log(message);
    }

    public void logPages() {
        debugPages.logPages();
    }

    @NonNull
    public Config getConfig() {
        return debugPages.getConfig();
    }
}
