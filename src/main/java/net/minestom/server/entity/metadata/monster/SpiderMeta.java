// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class SpiderMeta extends MonsterMeta {
    // Start of a method/block
    public SpiderMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isClimbing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Spider.IS_CLIMBING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setClimbing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Spider.IS_CLIMBING, value);
    // End of a block/expression
    }

// End of a block/expression
}
