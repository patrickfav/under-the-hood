package at.favre.lib.hood.interfaces;

import java.util.List;

/**
 * A section is a bunch of {@link PageEntry} with some additional business logic (e.g. adding/removing header)
 * for convenience and to easily handle groups of page entries. A section may also implement some error handling.
 */
public interface Section {

    /**
     * @return content of this section as list
     */
    List<PageEntry<?>> asEntryList();

    /**
     * Simple extension permitting builder-like remove of header and error messages
     */
    interface HeaderSection extends Section {

        /**
         * @return this section without the header element
         */
        HeaderSection removeHeader();

        /**
         * Suppresses a possible shown error message in UI
         */
        HeaderSection omitErrorMessage();
    }

    /**
     * Extension of {@link HeaderSection} adding mutators
     */
    interface ModifiableHeaderSection extends HeaderSection {
        /**
         * Adds an page entry to this section
         *
         * @param entry
         * @return this section
         */
        HeaderSection add(PageEntry<?> entry);

        /**
         * Adds a list of page entries to this section
         * @param entries
         * @return this changed section
         */
        HeaderSection add(List<PageEntry<?>> entries);

        /**
         * Adds the content (as retrieved with {@link #asEntryList()}) to this section
         * @param section
         * @return this section
         */
        HeaderSection add(Section section);

        /**
         * Sets an header element to this section, overwritting an possible existing one
         *
         * @param header
         * @return this section
         */
        HeaderSection setHeader(String header);

        /**
         * Sets an error message that will be shown in UI
         * @param errorMessage
         * @return this section
         */
        HeaderSection setErrorMessage(String errorMessage);

        /**
         * Get the error message set by {@link #setErrorMessage(String)}
         * @return error message
         */
        String getErrorMessage();
    }
}
