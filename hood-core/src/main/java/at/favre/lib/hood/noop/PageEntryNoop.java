package at.favre.lib.hood.noop;

import android.support.annotation.Nullable;

import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.ViewTemplate;

/**
 * A no-op implementation
 */
class PageEntryNoop implements PageEntry<String> {
    private final String placeHolderTitle = String.valueOf("");
    private final ViewTemplate<String> placeViewTemplate = new ViewTemplateNoop();

    @Override
    public String getValue() {
        return placeHolderTitle;
    }

    @Override
    public ViewTemplate<String> createViewTemplate() {
        return placeViewTemplate;
    }

    @Nullable
    @Override
    public String toLogString() {
        return null;
    }

    @Override
    public void refresh() {
        //no-op
    }
}
