// Package declaration for this file
package net.minestom.server.entity.metadata.monster.raider;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.monster.MonsterMeta;

// Type declaration (class/interface/enum/record)
public class RaiderMeta extends MonsterMeta {
    // Start of a method/block
    protected RaiderMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCelebrating() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Raider.IS_CELEBRATING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCelebrating(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Raider.IS_CELEBRATING, value);
    // End of a block/expression
    }

// End of a block/expression
}
