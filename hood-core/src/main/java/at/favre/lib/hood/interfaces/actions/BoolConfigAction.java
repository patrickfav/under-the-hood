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

import at.favre.lib.hood.interfaces.values.ChangeableValue;

/**
 * The abstraction of the switch containing a label (as shown in ui) and a changeable value
 * representing the boolean switch value. Default implementation see DefaultConfigActions
 */
public class BoolConfigAction {
    public final String label;
    public final ChangeableValue<Boolean, Boolean> changeableValue;

    public BoolConfigAction(String label, ChangeableValue<Boolean, Boolean> boolValue) {
        this.label = label;
        this.changeableValue = boolValue;
    }
}
