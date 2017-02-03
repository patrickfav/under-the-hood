package at.favre.lib.hood.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import timber.log.Timber;

/**
 * Default implementation of a the {@link Pages} interface. Can only be created with given
 * factory {@link DebugPages.Factory}
 */
public class DebugPages implements Pages {
    public final static String DEFAULT_TITLE = "<not set>";
    private final List<Page> pages = new ArrayList<>();
    private final Config config;

    /**
     * Use this factory to create instance of this object
     */
    public static class Factory {
        public static Pages create(@NonNull Config config) {
            return new DebugPages(config);
        }

        public static Pages createImmutableCopy(@NonNull Pages page) {
            return new UnmodifiablePages(page);
        }
    }

    private DebugPages(Config config) {
        this.config = config;
    }

    @Override
    public Page addNewPage() {
        return addNewPage(DEFAULT_TITLE);
    }

    @Override
    public Page addNewPage(String title) {
        Page p = DebugPage.Factory.create(this, title);
        pages.add(p);
        return p;
    }

    @NonNull
    @Override
    public Page getFirstPage() {
        if (pages.size() < 1) {
            throw new IllegalStateException("no pages added - add with addNewPage() first");
        }
        return pages.get(0);
    }

    @Nullable
    @Override
    public Page getPage(int index) {
        return pages.get(index);
    }

    @Override
    @NonNull
    public List<Page> getAll() {
        return pages;
    }

    @Override
    public int size() {
        return pages.size();
    }

    @Override
    public void refreshData() {
        for (Page page : pages) {
            page.refreshData();
        }
    }

    @Override
    public void log(String message) {
        Timber.tag(config.logTag).w(message);
    }

    @Override
    public void logPages() {
        for (Page page : pages) {
            page.logPage();
        }
    }

    @Override
    public StringBuilder getLogString() {
        StringBuilder sb = new StringBuilder();
        for (Page page : pages) {
            sb.append(DebugPage.getDebugDataAsString(page.getEntries(), page.getTitle())).append("\n");
        }
        return sb;
    }

    @NonNull
    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DebugPages pages1 = (DebugPages) o;

        if (pages != null ? !pages.equals(pages1.pages) : pages1.pages != null) return false;
        return config != null ? config.equals(pages1.config) : pages1.config == null;

    }

    @Override
    public int hashCode() {
        int result = pages != null ? pages.hashCode() : 0;
        result = 31 * result + (config != null ? config.hashCode() : 0);
        return result;
    }
}
