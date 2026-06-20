// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.TameableAnimalMeta;

// Type declaration (class/interface/enum/record)
public class AbstractNautilusMeta extends TameableAnimalMeta {
    // Start of a method/block
    public AbstractNautilusMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isDashing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractNautilus.DASH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDashing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractNautilus.DASH, value);
    // End of a block/expression
    }

// End of a block/expression
}
