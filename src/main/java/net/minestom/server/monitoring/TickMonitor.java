// Package declaration for this file
package net.minestom.server.monitoring;

// Type declaration (class/interface/enum/record)
public class TickMonitor {

    // Code statement
    private final double tickTime;
    // Code statement
    private final double acquisitionTime;

    // Start of a method/block
    public TickMonitor(double tickTime, double acquisitionTime) {
        // Access to the current/parent object
        this.tickTime = tickTime;
        // Access to the current/parent object
        this.acquisitionTime = acquisitionTime;
    // End of a block/expression
    }

    // Start of a method/block
    public double getTickTime() {
        // Returns a value to the caller
        return tickTime;
    // End of a block/expression
    }

    // Start of a method/block
    public double getAcquisitionTime() {
        // Returns a value to the caller
        return acquisitionTime;
    // End of a block/expression
    }
// End of a block/expression
}
