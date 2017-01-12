package at.favre.lib.hood.interfaces;

import java.util.List;

/**
 * A section is a bunch of {@link PageEntry} with some additional business logic (e.g. adding/removing header)
 */
public interface Section {

    /**
     * @return content of this section as list
     */
    List<PageEntry<?>> asEntryList();

    interface HeaderSection extends Section {
        HeaderSection removeHeader();

        HeaderSection omitErrorMessage();
    }

    interface ModifiableHeaderSection extends HeaderSection {
        HeaderSection add(PageEntry<?> entry);

        HeaderSection add(List<PageEntry<?>> entries);

        HeaderSection add(Section section);

        HeaderSection setErrorMessage(String errorMessage);

        String getErrorMessage();

        HeaderSection setHeader(String header);
    }
}
