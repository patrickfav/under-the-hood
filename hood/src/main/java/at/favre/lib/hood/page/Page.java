package at.favre.lib.hood.page;

import java.util.List;

public interface Page {
    List<PageEntry> getEntries();

    ViewTemplate<?> getTemplateForViewType(int viewType);

    void addEntry(PageEntry<?> pageEntry);

    void addEntries(List<PageEntry<?>> entries);

    void refreshData();

    void log(String tag);

    void removeEntries();
}
