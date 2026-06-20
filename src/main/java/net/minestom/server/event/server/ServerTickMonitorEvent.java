// Package declaration for this file
package net.minestom.server.event.server;

// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.monitoring.TickMonitor;

// Type declaration (class/interface/enum/record)
public final class ServerTickMonitorEvent implements Event {
    // Code statement
    private final TickMonitor tickMonitor;

    // Start of a method/block
    public ServerTickMonitorEvent(TickMonitor tickMonitor) {
        // Access to the current/parent object
        this.tickMonitor = tickMonitor;
    // End of a block/expression
    }

    // Start of a method/block
    public TickMonitor getTickMonitor() {
        // Returns a value to the caller
        return tickMonitor;
    // End of a block/expression
    }
// End of a block/expression
}
