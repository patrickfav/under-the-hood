package at.favre.lib.hood.noop;

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
 * A no-op implementation
 */
class PageNoop implements Page {
    private final Config placeholderConfig = Config.newBuilder().build();
    private final String placeHolderTitle = String.valueOf("");
    private final ViewTemplate<?> placeViewTemplate = new ViewTemplateNoop();

    @Override
    public String getTitle() {
        return placeHolderTitle;
    }

    @Override
    public List<PageEntry> getEntries() {
        return Collections.emptyList();
    }

    @Override
    public ViewTemplate<?> getTemplateForViewType(int viewType) {
        return placeViewTemplate;
    }

    @Override
    public void add(@Nullable PageEntry<?> pageEntry) {
        //no-op
    }

    @Override
    public void add(@Nullable List<PageEntry<?>> entries) {
        //no-op
    }

    @Override
    public void add(@NonNull Section section) {
        //no-op
    }

    @Override
    public void refreshData() {
        //no-op
    }

    @Override
    public void logPage() {
        //no-op
    }

    @Override
    public void removeEntries() {
        //no-op
    }

    @NonNull
    @Override
    public Config getConfig() {
        return placeholderConfig;
    }
}
