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

import androidx.annotation.Nullable;

/**
 * One entry in a {@link Page} containing a generic value.
 * It defines its value and how it is drawn in the UI (see {@link ViewTemplate}
 *
 * @param <T>
 */
public interface PageEntry<T> {

    /**
     * @return the value represented by this entry
     */
    T getValue();

    /**
     * Creates a new ViewTemplate object
     *
     * @return the view-template that defines how this view will be drawn
     */
    ViewTemplate<T> createViewTemplate();

    /**
     * Similar to RecyclerView.Adapter#getItemViewType return a view-type to be able
     * to recognize which views can be recycled. Free range is up to (excluding) 2^16 (65536).
     * <p>
     * See {@link ViewTemplate#getViewType()}
     *
     * @return an int unique to the same type (see {@link ViewTypes} on
     * what build-in view-types are already defined.
     */
    int getViewType();

    /**
     * If this entry does not need to log its content (e.g. if it just represents a "spacer") may
     * return null. Otherwise a short log string representing the content of this entry.
     *
     * @return a string used to log to console or null
     */
    @Nullable
    String toLogString();

    /**
     * Disables any output when trying to log. {@link #toLogString()} will always
     * return null after calling this method.
     */
    void disableLogging();

    /**
     * This should refresh the UI content. Will be called by the framework.
     */
    void refresh();
}
