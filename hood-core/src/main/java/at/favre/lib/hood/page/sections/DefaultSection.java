package at.favre.lib.hood.page.sections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.page.PageEntry;
import at.favre.lib.hood.page.Section;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.TextMessageEntry;


/**
 * The default implementation of the {@link Section} interface. Has a header, which can be removed
 * easily and can set a errorMessage for easier debugging. Has additional logic for empty content
 * (e.g. will act if it is empty if there is only a header but no entries).
 * <p>
 * Most setters use the builder pattern for easy configuration.
 */
public class DefaultSection implements Section {
    /**
     * Use this for empty sections (where you cannot return null)
     */
    public static final DefaultSection EMPTY_SECTION = new DefaultSection();

    private String header;
    private String errorMessage;
    private List<PageEntry<?>> entries;

    private DefaultSection() {
        this((String) null);
    }

    public DefaultSection(List<PageEntry<?>> entries) {
        this(null, entries);
    }

    public DefaultSection(String header) {
        this(header, new ArrayList<PageEntry<?>>());
    }

    public DefaultSection(String header, List<PageEntry<?>> entries) {
        this.header = header;
        this.entries = entries;
    }

    public DefaultSection removeHeader() {
        header = null;
        return this;
    }

    public DefaultSection ommitErrorMessage() {
        errorMessage = null;
        return this;
    }

    public DefaultSection add(PageEntry<?> entry) {
        this.entries.add(entry);
        return this;
    }

    public DefaultSection add(List<PageEntry<?>> entries) {
        this.entries.addAll(entries);
        return this;
    }

    public DefaultSection add(Section section) {
        this.entries.addAll(section.asEntryList());
        return this;
    }

    /**
     * Error message when there was a problem in creating the entries for the section
     * to signal the user what was went wrong
     *
     * @param errorMessage
     * @return itself
     */
    public DefaultSection setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public DefaultSection setHeader(String header) {
        this.header = header;
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
