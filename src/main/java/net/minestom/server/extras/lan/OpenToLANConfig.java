// Package declaration for this file
package net.minestom.server.extras.lan;

// Import of a required class
import net.minestom.server.event.server.ServerListPingEvent;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.Objects;

/**
 * Configuration for opening the server to LAN.
 *
 * @see OpenToLAN#open(OpenToLANConfig)
 */
// Type declaration (class/interface/enum/record)
public class OpenToLANConfig {
    // Code statement
    int port;
    // Code statement
    Duration delayBetweenPings, delayBetweenEvent;

    /**
     * Creates a new config with the port set to random and the delay between pings set
     * to 1.5 seconds and the delay between event calls set to 30 seconds.
     */
    // Start of a method/block
    public OpenToLANConfig() {
        // Access to the current/parent object
        this.port = 0;
        // Access to the current/parent object
        this.delayBetweenPings = Duration.of(1500, TimeUnit.MILLISECOND);
        // Access to the current/parent object
        this.delayBetweenEvent = Duration.of(30, TimeUnit.SECOND);
    // End of a block/expression
    }

    /**
     * Sets the port used to send pings from. Use {@code 0} to pick a random free port.
     *
     * @param port the port
     * @return {@code this}, for chaining
     */
    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public OpenToLANConfig port(int port) {
        // Access to the current/parent object
        this.port = port;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Sets the delay between outgoing pings.
     *
     * @param delay the delay
     * @return {@code this}, for chaining
     */
    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public OpenToLANConfig pingDelay(Duration delay) {
        // Access to the current/parent object
        this.delayBetweenPings = Objects.requireNonNull(delay, "delay");
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Sets the delay between calls of {@link ServerListPingEvent}.
     *
     * @param delay the delay
     * @return {@code this}, for chaining
     */
    // Annotation for the following element
    @Contract("_ -> this")
    // Start of a method/block
    public OpenToLANConfig eventCallDelay(Duration delay) {
        // Access to the current/parent object
        this.delayBetweenEvent = Objects.requireNonNull(delay, "delay");
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }
// End of a block/expression
}
