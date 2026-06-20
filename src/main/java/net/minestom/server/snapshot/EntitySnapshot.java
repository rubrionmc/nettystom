// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public sealed interface EntitySnapshot extends Snapshot, TagReadable
        // Start of a method/block
        permits PlayerSnapshot, SnapshotImpl.Entity {
    // Calls a method
    EntityType type();

    // Calls a method
    UUID uuid();

    // Calls a method
    int id();

    // Calls a method
    Pos position();

    // Calls a method
    Vec velocity();

    // Calls a method
    InstanceSnapshot instance();

    // Calls a method
    ChunkSnapshot chunk();

    // Calls a method
    Collection<PlayerSnapshot> viewers();

    // Calls a method
    Collection<EntitySnapshot> passengers();

    // Annotation for the following element
    @Nullable EntitySnapshot vehicle();
// End of a block/expression
}
