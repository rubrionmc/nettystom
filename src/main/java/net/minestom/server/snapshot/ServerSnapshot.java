

// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Collection;

/**
 * Represents the complete state of the server at a given moment.
 */
// Type declaration (class/interface/enum/record)
public sealed interface ServerSnapshot extends Snapshot
        // Start of a method/block
        permits SnapshotImpl.Server {
    // Calls a method
    Collection<InstanceSnapshot> instances();

    // Calls a method
    Collection<EntitySnapshot> entities();

    // Annotation for the following element
    @UnknownNullability EntitySnapshot entity(int id);

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    static ServerSnapshot update() {
        // Returns a value to the caller
        return SnapshotUpdater.update(MinecraftServer.process());
    // End of a block/expression
    }
// End of a block/expression
}
