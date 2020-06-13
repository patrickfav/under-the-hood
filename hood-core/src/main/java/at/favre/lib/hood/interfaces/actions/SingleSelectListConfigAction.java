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

package at.favre.lib.hood.interfaces.actions;

import androidx.annotation.Nullable;

import java.util.List;

import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

/**
 * Defines the ui label and list elements (as well as how changing it will be handled
 */
public class SingleSelectListConfigAction {
    public final String label;
    public final SpinnerValue<List<SpinnerElement>, SpinnerElement> changeableValue;

    public SingleSelectListConfigAction(@Nullable String label, SpinnerValue<List<SpinnerElement>, SpinnerElement> value) {
        this.label = label;
        this.changeableValue = value;
    }
}
