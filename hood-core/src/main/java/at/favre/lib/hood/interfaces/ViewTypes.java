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

/**
 * All pre-defined view-types used for {@link ViewTemplate#getViewType()} in {@link at.favre.lib.hood.internal}
 */
public final class ViewTypes {

    public static final int VIEWTYPE_HEADER = 1 << 16;
    public static final int VIEWTYPE_KEYVALUE = 1 << 16 + 1;
    public static final int VIEWTYPE_ACTION = 1 << 16 + 2;
    public static final int VIEWTYPE_ACTION_DOUBLE = 1 << 16 + 3;
    public static final int VIEWTYPE_CONFIG_BOOL = 1 << 16 + 4;
    public static final int VIEWTYPE_CONFIG_SPINNER = 1 << 16 + 5;
    public static final int VIEWTYPE_KEYVALUE_MULTILINE = 1 << 16 + 6;
    public static final int VIEWTYPE_MESSAGE = 1 << 16 + 7;
    public static final int VIEWTYPE_SPACER = 1 << 16 + 8;

}
