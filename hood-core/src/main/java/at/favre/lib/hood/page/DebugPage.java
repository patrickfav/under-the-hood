package at.favre.lib.hood.page;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.LinkedList;
import java.util.List;

/**
 * The default implementation of the debug view page. Use factory to create instance.
 */
public class DebugPage implements Page {
    private List<PageEntry> entries = new LinkedList<>();
    private SparseArray<ViewTemplate<?>> templateMap = new SparseArray<>();
    private final Pages pages;
    private final String title;

    /**
     * Use this factory to create a instance of {@link DebugPage}
     */
    public static class Factory {
        public static DebugPage create(Pages pages, String title) {
            return new DebugPage(pages, title);
        }
    }

    private DebugPage(Pages pages, String title) {
        this.pages = pages;
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<PageEntry> getEntries() {
        return entries;
    }

    @Override
    public ViewTemplate<?> getTemplateForViewType(int viewType) {
        return templateMap.get(viewType);
    }

    @Override
    public void add(@Nullable PageEntry<?> pageEntry) {
        if (pageEntry != null) {
            entries.add(pageEntry);
            templateMap.put(pageEntry.getViewTemplate().getViewType(), pageEntry.getViewTemplate());
        }
    }

    @Override
    public void add(List<PageEntry<?>> entries) {
        for (PageEntry<?> entry : entries) {
            add(entry);
        }
    }

    @Override
    public void add(@NonNull Section section) {
        add(section.asEntryList());
    }

    @Override
    public void removeEntries() {
        entries.clear();
        templateMap.clear();
    }

    @NonNull
    @Override
    public Config getConfig() {
        return pages.getConfig();
    }

    @Override
    public void refreshData() {
        for (PageEntry entry : entries) {
            entry.refresh();
        }
    }

    @Override
    public void logPage() {
        pages.log(getDebugDataAsString());
    }

    @Override
    public String toString() {
        return getDebugDataAsString();
    }

    private String getDebugDataAsString() {
        StringBuilder sb = new StringBuilder();
        for (PageEntry pageEntry : entries) {
            String log = pageEntry.toLogString();
            if (log != null) {
                sb.append(pageEntry.toLogString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DebugPage debugPage = (DebugPage) o;

        if (entries != null ? !entries.equals(debugPage.entries) : debugPage.entries != null)
            return false;
        if (templateMap != null ? !templateMap.equals(debugPage.templateMap) : debugPage.templateMap != null)
            return false;
        return pages != null ? pages.equals(debugPage.pages) : debugPage.pages == null;

    }

    @Override
    public int hashCode() {
        int result = entries != null ? entries.hashCode() : 0;
        result = 31 * result + (templateMap != null ? templateMap.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        return result;
    }
}
