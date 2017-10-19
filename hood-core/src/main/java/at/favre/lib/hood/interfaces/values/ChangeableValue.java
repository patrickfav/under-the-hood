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

package at.favre.lib.hood.interfaces.values;

/**
 * As en enhancement to {@link DynamicValue} a value that can also be changed (e.g. like a value in
 * {@link android.content.SharedPreferences}. Returned value and change value might be of different
 * types (e.g value is the whole Object, change-value just the id).
 *
 * @param <T> value
 * @param <E> change-value
 */
public interface ChangeableValue<T, E> extends DynamicValue<T> {
    /**
     * Given a value change the internal status. Will affect what {@link #getValue()}
     * returns.
     *
     * @param value to change to
     */
    void onChange(E value);
}
