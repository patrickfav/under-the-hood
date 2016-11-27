package at.favre.lib.hood.views;

import java.util.List;

public interface Page {
    List<PageEntry> getEntries();

    ViewTemplate<?> getTemplateForViewType(int viewType);

    void addEntry(PageEntry<?> pageEntry);

    void addEntries(List<PageEntry<?>> entries);

    void removeEntries();
}
