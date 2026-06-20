// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an object which is regularly saved into a snapshot.
 * <p>
 * Implementations must be identity-based.
 */
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public interface Snapshotable {

    /**
     * Updates the currently cached snapshot if required.
     * The updater can be used to retrieve references to other snapshots while avoiding circular dependency.
     * Be careful to do not store {@code updater} anywhere as its data will change when building requested references.
     * <p>
     * This method is not thread-safe, and targeted at internal use
     * since its execution rely on safe-points (e.g. end of ticks)
     *
     * @param updater the snapshot updater/context
     * @return the updated snapshot
     */
    // Start of a method/block
    default Snapshot updateSnapshot(SnapshotUpdater updater) {
        // Throws an exception
        throw new UnsupportedOperationException("Snapshot is not supported for this object");
    // End of a block/expression
    }
// End of a block/expression
}
