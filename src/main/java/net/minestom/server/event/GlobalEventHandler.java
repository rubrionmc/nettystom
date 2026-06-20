// Package declaration for this file
package net.minestom.server.event;

/**
 * Object containing all the global event listeners.
 */
// Type declaration (class/interface/enum/record)
public final class GlobalEventHandler extends EventNodeImpl<Event> {
    // Start of a method/block
    public GlobalEventHandler() {
        // Access to the current/parent object
        super("global", EventFilter.ALL, null);
    // End of a block/expression
    }
// End of a block/expression
}
