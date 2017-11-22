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
 * Wrapper of a spinner element containing a name used in UI and an id
 */
public interface SpinnerElement {

    /**
     * Stable id used for identifying this element
     *
     * @return unique id
     */
    String getId();

    /**
     * Human readable label used in UI
     *
     * @return name
     */
    String getName();

    class Default implements SpinnerElement {
        private final String id;
        private final String name;

        public Default(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Default aDefault = (Default) o;

            if (id != null ? !id.equals(aDefault.id) : aDefault.id != null) return false;
            return name != null ? name.equals(aDefault.name) : aDefault.name == null;

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "DefaultSpinnerElement{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
