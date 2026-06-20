// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class HangingMeta extends EntityMeta implements ObjectDataProvider {

    // Start of a method/block
    protected HangingMeta(@Nullable Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Direction getDirection() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Hanging.DIRECTION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDirection(Direction direction) {
        // Calls a method
        metadata.set(MetadataDef.Hanging.DIRECTION, direction);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getObjectData() {
        // Returns a value to the caller
        return getDirection().ordinal();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresVelocityPacketAtSpawn() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
