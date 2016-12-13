package at.favre.lib.hood.page;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.values.DynamicValue;

/**
 * The default implementation of the debug view page. Use factory to create instance.
 */
public class DebugPage implements Page {
    private List<PageEntry> entries = new ArrayList<>();
    private Map<Integer, ViewTemplate<?>> templateMap = new HashMap<>();
    private final String logTag;

    /**
     * Use this factory to create a instance of {@link DebugPage}
     */
    public static class Factory {
        public static DebugPage create(Config config) {
            return new DebugPage(config.logTag);
        }
    }

    private DebugPage(String logTag) {
        this.logTag = logTag;
    }

    @Override
    public List<PageEntry> getEntries() {
        return entries;
    }

    @Override
    public ViewTemplate<?> getTemplateForViewType(int viewType) {
        return templateMap.get(viewType);
    }

    public void addProperty(CharSequence key, DynamicValue<String> value) {
        add(new KeyValueEntry(key, value, false));
    }

    public void addProperty(CharSequence key, String value) {
        add(new KeyValueEntry(key, value));
    }

    public void addTitle(CharSequence title) {
        add(new HeaderEntry(title));
    }

    public void addAction(@Nullable ActionEntry.Action action) {
        if (action != null) {
            add(new ActionEntry(action));
        }
    }

    public void addAction(@Nullable ActionEntry.Action action1, @Nullable ActionEntry.Action action2) {
        if (action1 == null && action2 != null) {
            addAction(action2);
        } else if (action1 != null && action2 == null) {
            addAction(action1);
        } else if (action1 != null && action2 != null) {
            add(new ActionEntry(action1, action2));
        }
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

    @Override
    public void refreshData() {
        for (PageEntry entry : entries) {
            entry.refresh();
        }
    }

    @Override
    public void log(String message) {
        Log.w(logTag, message);
    }

    @Override
    public void logPage() {
        log(getDebugDataAsString());
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

}
