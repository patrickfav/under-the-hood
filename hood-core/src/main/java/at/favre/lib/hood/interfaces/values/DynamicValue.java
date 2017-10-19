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
 * Used for a value that might change (e.g. connectivity status).
 * Calling {@link #getValue()} should always return the current value.
 *
 * @param <T>
 */
public interface DynamicValue<T> {
    /**
     * @return the current value
     */
    T getValue();


    /**
     * Used for static values in APIs where dynamic and static values are supported
     *
     * @param <T>
     */
    class DefaultStaticValue<T> implements DynamicValue<T> {
        private final T value;

        public DefaultStaticValue(T value) {
            this.value = value;
        }

        @Override
        public T getValue() {
            return value;
        }
    }

    /**
     * Marker interface that tells the logic to fetch the value async in the background
     *
     * @param <T>
     */
    interface Async<T> extends DynamicValue<T> {

    }
}
