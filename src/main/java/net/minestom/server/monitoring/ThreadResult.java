// Package declaration for this file
package net.minestom.server.monitoring;

// Type declaration (class/interface/enum/record)
public class ThreadResult {

    // Code statement
    private final double cpuPercentage;
    // Code statement
    private final double userPercentage;
    // Code statement
    private final double waitedPercentage;
    // Code statement
    private final double blockedPercentage;

    // Code statement
    protected ThreadResult(double cpuPercentage,
                           // Code statement
                           double userPercentage,
                           // Code statement
                           double waitedPercentage,
                           // Start of a method/block
                           double blockedPercentage) {
        // Access to the current/parent object
        this.cpuPercentage = cpuPercentage;
        // Access to the current/parent object
        this.userPercentage = userPercentage;
        // Access to the current/parent object
        this.waitedPercentage = waitedPercentage;
        // Access to the current/parent object
        this.blockedPercentage = blockedPercentage;
    // End of a block/expression
    }

    // Start of a method/block
    public double getCpuPercentage() {
        // Returns a value to the caller
        return cpuPercentage;
    // End of a block/expression
    }

    // Start of a method/block
    public double getUserPercentage() {
        // Returns a value to the caller
        return userPercentage;
    // End of a block/expression
    }

    // Start of a method/block
    public double getWaitedPercentage() {
        // Returns a value to the caller
        return waitedPercentage;
    // End of a block/expression
    }

    // Start of a method/block
    public double getBlockedPercentage() {
        // Returns a value to the caller
        return blockedPercentage;
    // End of a block/expression
    }
// End of a block/expression
}
