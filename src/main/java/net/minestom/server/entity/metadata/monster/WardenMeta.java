// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class WardenMeta extends MonsterMeta {
    // Start of a method/block
    public WardenMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getAngerLevel() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Warden.ANGER_LEVEL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAngerLevel(int value) {
        // Calls a method
        metadata.set(MetadataDef.Warden.ANGER_LEVEL, value);
    // End of a block/expression
    }

// End of a block/expression
}
