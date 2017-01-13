package at.favre.lib.hood.interfaces.actions;

/**
 * Used for listener or similar to stop
 */
public interface ManagerControl {
    /**
     * Start the current listener or continuous action
     */
    void start();

    /**
     * Stops the current listener or continuous action
     */
    void stop();
}
