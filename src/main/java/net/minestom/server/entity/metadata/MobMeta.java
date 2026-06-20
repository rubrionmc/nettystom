// Package declaration for this file
package net.minestom.server.entity.metadata;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class MobMeta extends LivingEntityMeta {
    // Start of a method/block
    protected MobMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isNoAi() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mob.NO_AI);
    // End of a block/expression
    }

    // Start of a method/block
    public void setNoAi(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mob.NO_AI, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLeftHanded() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mob.IS_LEFT_HANDED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftHanded(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mob.IS_LEFT_HANDED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isAggressive() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mob.IS_AGGRESSIVE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAggressive(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mob.IS_AGGRESSIVE, value);
    // End of a block/expression
    }

// End of a block/expression
}
