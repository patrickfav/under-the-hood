package at.favre.lib.hood.internal;


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
 * Will throw exception when mutators are called.
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
            copy.add(new UnmodifiablePageDelegate(page));
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

    public void refreshData() {
        debugPages.refreshData();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnmodifiablePages that = (UnmodifiablePages) o;

        if (debugPages != null ? !debugPages.equals(that.debugPages) : that.debugPages != null)
            return false;
        return unmodifiablePages != null ? unmodifiablePages.equals(that.unmodifiablePages) : that.unmodifiablePages == null;

    }

    @Override
    public int hashCode() {
        int result = debugPages != null ? debugPages.hashCode() : 0;
        result = 31 * result + (unmodifiablePages != null ? unmodifiablePages.hashCode() : 0);
        return result;
    }
}
