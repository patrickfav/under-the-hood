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

import at.favre.lib.hood.interfaces.values.SpinnerElement;

/**
 * Default implementation for a Backend - can be used with {@link at.favre.lib.hood.util.defaults.DefaultConfigActions} as backend
 * spinner element.
 */
public class Backend extends SpinnerElement.Default {

    private final String url;
    private final int port;

    public Backend(int id, String url, int port) {
        super(String.valueOf(id), url + ":" + port);
        this.url = url;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Backend{" +
                "id=" + getId() +
                ", url='" + url + '\'' +
                ", port=" + port +
                "}";
    }
}
