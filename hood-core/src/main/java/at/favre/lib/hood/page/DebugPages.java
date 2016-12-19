package at.favre.lib.hood.page;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;

public class DebugPages implements Pages {
    private List<Page> pages = new ArrayList<>();
    private at.favre.lib.hood.interfaces.Config config;

    public static class Factory {
        public static Pages create(at.favre.lib.hood.interfaces.Config config) {
            return new DebugPages(config);
        }
    }

    private DebugPages(at.favre.lib.hood.interfaces.Config config) {
        this.config = config;
    }

    @Override
    public Page addNewPage() {
        Page p = DebugPage.Factory.create(this, "<not set>");
        pages.add(p);
        return p;
    }

    @Override
    public Page addNewPage(String title) {
        Page p = DebugPage.Factory.create(this, title);
        pages.add(p);
        return p;
    }

    @Override
    public Page getFirstPage() {
        if (pages.size() < 1) {
            throw new IllegalStateException("no pages added - add with addNewPage() first");
        }
        return pages.get(0);
    }

    @Override
    public Page getPage(int index) {
        return pages.get(index);
    }

    @Override
    public List<Page> getAll() {
        return pages;
    }

    @Override
    public void refreshData() {
        for (Page page : pages) {
            page.refreshData();
        }
    }

    @Override
    public void log(String message) {
        Log.w(config.logTag, message);
    }

    @Override
    public void logPages() {
        for (Page page : pages) {
            page.logPage();
        }
    }

    @NonNull
    @Override
    public at.favre.lib.hood.interfaces.Config getConfig() {
        return config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DebugPages that = (DebugPages) o;

        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        return config != null ? config.equals(that.config) : that.config == null;

    }

    @Override
    public int hashCode() {
        int result = pages != null ? pages.hashCode() : 0;
        result = 31 * result + (config != null ? config.hashCode() : 0);
        return result;
    }
}
