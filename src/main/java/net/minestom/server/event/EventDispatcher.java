// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;

// Type declaration (class/interface/enum/record)
public final class EventDispatcher {

    // Start of a method/block
    public static void call(Event event) {
        // Calls a method
        MinecraftServer.getGlobalEventHandler().call(event);
    // End of a block/expression
    }

    // Start of a method/block
    public static <E extends Event> ListenerHandle<E> getHandle(Class<E> handleType) {
        // Returns a value to the caller
        return MinecraftServer.getGlobalEventHandler().getHandle(handleType);
    // End of a block/expression
    }

    // Start of a method/block
    public static void callCancellable(CancellableEvent event, Runnable successCallback) {
        // Calls a method
        MinecraftServer.getGlobalEventHandler().callCancellable(event, successCallback);
    // End of a block/expression
    }
// End of a block/expression
}
