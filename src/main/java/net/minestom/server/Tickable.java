// Package declaration for this file
package net.minestom.server;

/**
 * Represents an element which is ticked at a regular interval.
 */
// Type declaration (class/interface/enum/record)
public interface Tickable {

    /**
     * Ticks this element.
     *
     * @param time the time of the tick in milliseconds
     */
    // Calls a method
    void tick(long time);
// End of a block/expression
}
