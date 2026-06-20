// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class ChestedHorseMeta extends AbstractHorseMeta {
    // Start of a method/block
    protected ChestedHorseMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasChest() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ChestedHorse.HAS_CHEST);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasChest(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ChestedHorse.HAS_CHEST, value);
    // End of a block/expression
    }

// End of a block/expression
}
