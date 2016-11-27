package at.favre.lib.hood.views;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebugViewPage implements Page {
    private List<PageEntry> entries = new ArrayList<>();
    private Map<Integer, ViewTemplate<?>> templateMap = new HashMap<>();
    private Config config;

    public DebugViewPage() {
        this(new Config(true));
    }

    public DebugViewPage(Config config) {
        this.config = config;
    }

    @Override
    public List<PageEntry> getEntries() {
        return entries;
    }

    @Override
    public ViewTemplate<?> getTemplateForViewType(int viewType) {
        return templateMap.get(viewType);
    }

    Config getConfig() {
        return config;
    }

    public void addProperty(CharSequence key, String value) {
        addProperty(key, value, true);
    }

    public void addProperty(CharSequence key, String value, boolean staticContent) {
        addEntry(new KeyValueEntry(key, value, staticContent));
    }

    public void addTitle(CharSequence title) {
        addEntry(new HeaderEntry(title));
    }

    public void addAction(ActionEntry.Action action) {
        addEntry(new ActionEntry(action));
    }

    public void addAction(ActionEntry.Action action1, ActionEntry.Action action2) {
        addEntry(new ActionEntry(action1, action2));
    }

    @Override
    public void addEntry(PageEntry<?> pageEntry) {
        entries.add(pageEntry);
        templateMap.put(pageEntry.getViewTemplate().getViewType(), pageEntry.getViewTemplate());
    }

    @Override
    public void addEntries(List<PageEntry<?>> entries) {
        for (PageEntry<?> entry : entries) {
            addEntry(entry);
        }
    }

    @Override
    public void removeEntries() {
        entries.clear();
        templateMap.clear();
    }

    public static class Config {
        public final boolean showZebra;

        public Config(boolean showZebra) {
            this.showZebra = showZebra;
        }
    }
}
