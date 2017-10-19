package at.favre.lib.hood.internal;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.ViewTemplate;
import at.favre.lib.hood.internal.entries.ConfigBoolEntry;
import at.favre.lib.hood.internal.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.internal.entries.KeyValueEntry;

/**
 * The default implementation of the debug view page. Use factory to create instance.
 */
class DebugPage implements Page {
    private final List<PageEntry> entries = new LinkedList<>();
    private final SparseArray<ViewTemplate<?>> templateMap = new SparseArray<>();
    private final Pages pages;
    private final String title;
    private boolean loggingDisabled = false;

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
    @NonNull
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
            ViewTemplate<?> template = pageEntry.createViewTemplate();
            if (templateMap.indexOfKey(template.getViewType()) < 0) {
                templateMap.put(template.getViewType(), template);
            }

            if (loggingDisabled) {
                pageEntry.disableLogging();
            }
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

    @Override
    public void disableLogging() {
        loggingDisabled = true;
        for (PageEntry entry : entries) {
            entry.disableLogging();
        }
    }

    @NonNull
    @Override
    public Config getConfig() {
        return pages.getConfig();
    }

    @Override
    public Map<String, String> createDataMap() {
        Map<String, String> map = new LinkedHashMap<>();
        for (PageEntry entry : entries) {
            if (entry instanceof KeyValueEntry) {
                Object value = ((KeyValueEntry) entry).getValue().getValue();
                CharSequence key = ((KeyValueEntry) entry).getValue().getKey();
                if (!(key == null && value == null)) {
                    map.put(key != null ? key.toString() : "null", value != null ? value.toString() : "null");
                }
            }
            if (entry instanceof ConfigBoolEntry) {
                if (((ConfigBoolEntry) entry).getValue().label != null) {
                    map.put(((ConfigBoolEntry) entry).getValue().label,
                            String.valueOf(((ConfigBoolEntry) entry).getValue().changeableValue.getValue()));
                }
            }
            if (entry instanceof ConfigSpinnerEntry) {
                map.put(((ConfigSpinnerEntry) entry).getValue().label,
                        String.valueOf(((ConfigSpinnerEntry) entry).getValue().changeableValue.getValue()));
            }
        }
        return map;
    }

    @Override
    public void refreshData() {
        for (PageEntry entry : entries) {
            entry.refresh();
        }
    }

    @Override
    public void logPage() {
        if (!loggingDisabled) {
            pages.log(getDebugDataAsString(entries, title));
        }
    }

    @Override
    public String toString() {
        return getDebugDataAsString(entries, title);
    }

    static String getDebugDataAsString(List<PageEntry> entries, String title) {
        StringBuilder sb = new StringBuilder();

        if (title != null && !title.trim().isEmpty() && !DebugPages.DEFAULT_TITLE.equals(title)) {
            sb.append("# ").append(title).append("\n");
        }

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
        return title != null ? title.equals(debugPage.title) : debugPage.title == null;

    }

    @Override
    public int hashCode() {
        int result = entries != null ? entries.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
