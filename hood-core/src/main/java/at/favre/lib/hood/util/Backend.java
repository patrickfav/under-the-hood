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
