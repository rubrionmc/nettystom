// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.server.entity.GameMode;

// Type declaration (class/interface/enum/record)
public sealed interface PlayerSnapshot extends EntitySnapshot
        // Start of a method/block
        permits SnapshotImpl.Player {
    // Calls a method
    String username();

    // Calls a method
    GameMode gameMode();
// End of a block/expression
}
