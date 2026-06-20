// Package declaration for this file
package net.minestom.server.entity.metadata.golem;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class IronGolemMeta extends AbstractGolemMeta {
    // Start of a method/block
    public IronGolemMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isPlayerCreated() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IronGolem.IS_PLAYER_CREATED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPlayerCreated(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IronGolem.IS_PLAYER_CREATED, value);
    // End of a block/expression
    }

// End of a block/expression
}
