package at.favre.lib.hood.views;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
    private List<PageEntry> entries = new ArrayList<>();
    private Map<Integer, ViewTemplate<?>> templateMap = new HashMap<>();
    private Config config;

    public Page() {
        this(new Config(true));
    }

    public Page(Config config) {
        this.config = config;
    }

    public List<PageEntry> getEntries() {
        return entries;
    }

    Map<Integer, ViewTemplate<?>> getTemplateMap() {
        return templateMap;
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

    public void addEntry(PageEntry<?> pageEntry) {
        entries.add(pageEntry);
        templateMap.put(pageEntry.getViewTemplate().getViewType(), pageEntry.getViewTemplate());
    }

    public void addEntries(List<PageEntry<?>> entries) {
        for (PageEntry<?> entry : entries) {
            addEntry(entry);
        }
    }

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
