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


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

public interface Pages {

    /**
     * Adds a new page to the internal list with default title
     *
     * @return the newly created page
     */
    Page addNewPage();

    /**
     * Adds a new page to the internal list with given title
     *
     * @param title of the page
     * @return the newly created page
     */
    Page addNewPage(String title);

    /**
     * Same as {@link #getPage(int)} with parameter 0, but throws exception,
     * ie. making it null safe.
     *
     * @return the first page
     * @throws IllegalStateException if no page was added
     */
    @NonNull
    Page getFirstPage();

    /**
     * Retrieves a page for the specific index
     *
     * @param index of the requested page (beginning with 0)
     * @return the page if one was found, null otherwise
     */
    @Nullable
    Page getPage(int index);

    /**
     * Returns the actual reference, not a copy.
     *
     * @return all contained pages
     */
    @NonNull
    List<Page> getAll();

    /**
     * The count of contained pages
     *
     * @return size
     */
    int size();

    /**
     * Calls {@link PageEntry#refresh()} on every entry.
     */
    void refreshData();

    /**
     * The default implementation of what should happen when a component wants to log something.
     * You can set e.g. tag, formatting, etc.
     */
    void log(String message);

    /**
     * Logs the whole page data
     */
    void logPages();

    /**
     * Same as {@link #logPages()} but returns the string that would be logged instead
     *
     * @return the log of the page as {@link StringBuilder}
     */
    StringBuilder getLogString();

    /**
     * The config should be passed to this class
     *
     * @return the currently set config
     */
    @NonNull
    Config getConfig();

    /**
     * Creates a flat (no pages and sections) map all data elements
     * that can be represented in a key/value manner
     *
     * @return data map
     */
    Map<String, String> createDataMap();
}
