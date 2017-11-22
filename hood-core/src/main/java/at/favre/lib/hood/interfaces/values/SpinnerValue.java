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

import java.util.Collection;

/**
 * A spinner (dropdown list) value, which has the list of all values and a changeable-value
 *
 * @param <T> type of the while list
 * @param <E> type of the changeable value
 */
public interface SpinnerValue<T extends Collection, E> extends ChangeableValue<E, E>, ValueSet<T> {
}
