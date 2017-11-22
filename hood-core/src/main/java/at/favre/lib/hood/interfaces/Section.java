/*
 *  Copyright 2016 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
         *
         * @param entries
         * @return this changed section
         */
        HeaderSection add(List<PageEntry<?>> entries);

        /**
         * Adds the content (as retrieved with {@link #asEntryList()}) to this section
         *
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
         *
         * @param errorMessage
         * @return this section
         */
        HeaderSection setErrorMessage(String errorMessage);

        /**
         * Get the error message set by {@link #setErrorMessage(String)}
         *
         * @return error message
         */
        String getErrorMessage();
    }
}
