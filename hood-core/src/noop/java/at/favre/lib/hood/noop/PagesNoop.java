package at.favre.lib.hood.noop;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;

/**
 * A no-op implementation
 */
public class PagesNoop implements Pages {
    private final PageNoop placeholderPage = new PageNoop();
    private final Config placeholderConfig = Config.newBuilder().build();

    @Override
    public Page addNewPage() {
        return placeholderPage;
    }

    @Override
    public Page addNewPage(String title) {
        return placeholderPage;
    }

    @NonNull
    @Override
    public Page getFirstPage() {
        return placeholderPage;
    }

    @Nullable
    @Override
    public Page getPage(int index) {
        return placeholderPage;
    }

    @Override
    public List<Page> getAll() {
        return Collections.emptyList();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void refreshData() {
        //no-op
    }

    @Override
    public void log(String message) {
        //no-op
    }

    @Override
    public void logPages() {
        //no-op
    }

    @Override
    public StringBuilder getLogString() {
        return new StringBuilder();
    }

    @NonNull
    @Override
    public Config getConfig() {
        return placeholderConfig;
    }

    @Override
    public Map<String, String> createDataMap() {
        return Collections.emptyMap();
    }
}
