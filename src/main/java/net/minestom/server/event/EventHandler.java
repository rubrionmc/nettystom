// Package declaration for this file
package net.minestom.server.event;

/**
 * Represents an element which can have {@link Event} listeners assigned to it.
 */
// Type declaration (class/interface/enum/record)
public interface EventHandler<T extends Event> {
    // Calls a method
    EventNode<T> eventNode();
// End of a block/expression
}
