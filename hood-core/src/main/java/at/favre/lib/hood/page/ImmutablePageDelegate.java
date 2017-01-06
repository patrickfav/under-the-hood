package at.favre.lib.hood.page;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.ViewTemplate;

/**
 * Immutable delegate for a {@link Page}. Used to prevent changes.
 */
class ImmutablePageDelegate implements Page {
    private final Page page;
    private final List<PageEntry> unmodifiableEntries;

    ImmutablePageDelegate(Page page) {
        this.page = page;
        this.unmodifiableEntries = Collections.unmodifiableList(page.getEntries());
    }

    public String getTitle() {
        return page.getTitle();
    }

    public List<PageEntry> getEntries() {
        return unmodifiableEntries;
    }

    public ViewTemplate<?> getTemplateForViewType(int viewType) {
        return page.getTemplateForViewType(viewType);
    }

    public void add(@Nullable PageEntry<?> pageEntry) {
        throw new UnsupportedOperationException("cannot add page anymore");
    }

    public void add(@Nullable List<PageEntry<?>> entries) {
        throw new UnsupportedOperationException("cannot add page anymore");
    }

    public void add(@NonNull Section section) {
        throw new UnsupportedOperationException("cannot add page anymore");
    }

    public void refreshData() {
        page.refreshData();
    }

    public void logPage() {
        page.logPage();
    }

    public void removeEntries() {
        throw new UnsupportedOperationException("cannot remove pages anymore");
    }

    @NonNull
    public Config getConfig() {
        return page.getConfig();
    }
}
