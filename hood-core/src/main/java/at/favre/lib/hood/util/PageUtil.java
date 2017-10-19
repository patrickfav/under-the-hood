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

package at.favre.lib.hood.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.values.DynamicValue;

/**
 * Convenience methods for modifying pages. Most of them are null-safe.
 */
public class PageUtil {

    public void addProperty(@NonNull Page page, CharSequence key, DynamicValue<String> value) {
        page.add(Hood.get().createPropertyEntry(key, value, false));
    }

    public static void addProperty(@NonNull Page page, CharSequence key, String value) {
        page.add(Hood.get().createPropertyEntry(key, value));
    }

    public static void addHeader(@NonNull Page page, CharSequence title) {
        page.add(Hood.get().createHeaderEntry(title));
    }

    public static void addAction(@NonNull Page page, @Nullable ButtonDefinition action) {
        if (action != null) {
            page.add(Hood.get().createActionEntry(action));
        }
    }

    public static void addAction(@NonNull Page page, @Nullable ButtonDefinition action1, @Nullable ButtonDefinition action2) {
        if (action1 == null && action2 != null) {
            addAction(page, action2);
        } else if (action1 != null && action2 == null) {
            addAction(page, action1);
        } else if (action1 != null && action2 != null) {
            page.add(Hood.get().createActionEntry(action1, action2));
        }
    }
}
