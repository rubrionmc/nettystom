// Package declaration for this file
package net.minestom.server.entity.metadata.monster.raider;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class WitchMeta extends RaiderMeta {
    // Start of a method/block
    public WitchMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isDrinkingPotion() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Witch.IS_DRINKING_POTION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDrinkingPotion(boolean value) {
        // Access to the current/parent object
        super.metadata.set(MetadataDef.Witch.IS_DRINKING_POTION, value);
    // End of a block/expression
    }

// End of a block/expression
}
