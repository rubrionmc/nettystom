// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class EndermanMeta extends MonsterMeta {
    // Start of a method/block
    public EndermanMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Block getCarriedBlock() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Enderman.CARRIED_BLOCK);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCarriedBlock(@Nullable Block value) {
        // Calls a method
        metadata.set(MetadataDef.Enderman.CARRIED_BLOCK, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isScreaming() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Enderman.IS_SCREAMING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setScreaming(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Enderman.IS_SCREAMING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isStaring() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Enderman.IS_STARING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setStaring(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Enderman.IS_STARING, value);
    // End of a block/expression
    }

// End of a block/expression
}
