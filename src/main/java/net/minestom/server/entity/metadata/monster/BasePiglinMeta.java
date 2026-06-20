// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class BasePiglinMeta extends MonsterMeta {
    // Start of a method/block
    protected BasePiglinMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isImmuneToZombification() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.BasePiglin.IMMUNE_ZOMBIFICATION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setImmuneToZombification(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.BasePiglin.IMMUNE_ZOMBIFICATION, value);
    // End of a block/expression
    }

// End of a block/expression
}
