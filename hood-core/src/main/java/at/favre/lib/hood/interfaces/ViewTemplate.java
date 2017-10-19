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


import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Used by the internal adapter as abstraction to a RecyclerView.ViewHolder
 * @param <T>
 */
public interface ViewTemplate<T> {

    /**
     * Similar to RecyclerView.Adapter#getItemViewType return a view-type to be able
     * to recognize which views can be recycled. Free range is up to (excluding) 2^16 (65536).
     * @return an int unique to the same type (see {@link ViewTypes} on
     * what build-in view-types are already defined.
     */
    int getViewType();

    /**
     * Construct the view that represents the entry from scratch.
     * @param parent
     * @param inflater
     * @return non-null inflated view
     */
    View constructView(ViewGroup parent, LayoutInflater inflater);

    /**
     * Given a non-null view of the same view-type, update the view with the given value.
     * @param value used to fill the view
     * @param view the recycled view
     */
    void setContent(T value,@NonNull View view);

    /**
     * Given a non-null view of the same view-type, update the view's background to facilitate
     * a "zebra" effect (even-odd coloring)
     * @param view a non-null view of the correct type
     * @param zebraColor the highlighted "zebra" color as defined by the view
     * @param isOdd to easy identify which view has a highlighted zebra background and which not (ie. isOdd has highlighted background)
     */
    void decorateViewWithZebra(@NonNull View view, @ColorInt int zebraColor, boolean isOdd);
}
