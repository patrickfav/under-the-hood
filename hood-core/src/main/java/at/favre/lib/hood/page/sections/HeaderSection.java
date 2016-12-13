package at.favre.lib.hood.page.sections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.Section;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.TextMessageEntry;


public class HeaderSection implements Section {
    public static final HeaderSection EMPTY = new HeaderSection();

    private String header;
    private String errorMessage;
    private List<PageEntry<?>> entries;

    private HeaderSection() {
        this((String) null);
    }

    public HeaderSection(List<PageEntry<?>> entries) {
        this(null, entries);
    }

    public HeaderSection(String header) {
        this(header, new ArrayList<PageEntry<?>>());
    }

    public HeaderSection(String header, List<PageEntry<?>> entries) {
        this.header = header;
        this.entries = entries;
    }

    public HeaderSection removeHeader() {
        header = null;
        return this;
    }

    public HeaderSection ommitErrorMessage() {
        errorMessage = null;
        return this;
    }

    public HeaderSection add(PageEntry<?> entry) {
        this.entries.add(entry);
        return this;
    }

    public HeaderSection add(List<PageEntry<?>> entries) {
        this.entries.addAll(entries);
        return this;
    }

    public HeaderSection setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public List<PageEntry<?>> asEntryList() {
        if (entries.isEmpty() && errorMessage == null) {
            return Collections.emptyList();
        }

        List<PageEntry<?>> entriesOut = new ArrayList<>(entries.size() + 1);
        if (header != null) {
            entriesOut.add(new HeaderEntry(header));
        }
        if (errorMessage != null) {
            entriesOut.add(new TextMessageEntry(errorMessage));
        }

        entriesOut.addAll(entries);

        return entriesOut;
    }
}
