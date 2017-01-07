package at.favre.lib.hood.noop;

import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Section;


/**
 * A no-op implementation
 */
class SectionNoop implements Section.ModifiableHeaderSection {
    private final String placeErrorMsg = String.valueOf("");

    @Override
    public List<PageEntry<?>> asEntryList() {
        return Collections.emptyList();
    }

    @Override
    public HeaderSection removeHeader() {
        return this;
    }

    @Override
    public HeaderSection omitErrorMessage() {
        return this;
    }

    @Override
    public HeaderSection add(PageEntry<?> entry) {
        return this;
    }

    @Override
    public HeaderSection add(List<PageEntry<?>> entries) {
        return this;
    }

    @Override
    public HeaderSection add(Section section) {
        return this;
    }

    @Override
    public HeaderSection setErrorMessage(String errorMessage) {
        return this;
    }

    @Override
    public String getErrorMessage() {
        return placeErrorMsg;
    }

    @Override
    public HeaderSection setHeader(String header) {
        return this;
    }
}
