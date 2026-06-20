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

// Type declaration (class/interface/enum/record)
public class ExperienceOrbMeta extends EntityMeta {

    // Start of a method/block
    public ExperienceOrbMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getValue() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ExperienceOrb.VALUE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setValue(int value) {
        // Calls a method
        metadata.set(MetadataDef.ExperienceOrb.VALUE, value);
    // End of a block/expression
    }
// End of a block/expression
}
