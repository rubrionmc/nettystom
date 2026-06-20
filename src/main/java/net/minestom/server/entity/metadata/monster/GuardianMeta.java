// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class GuardianMeta extends MonsterMeta {
    // Code statement
    private @Nullable Entity target;

    // Start of a method/block
    public GuardianMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRetractingSpikes() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Guardian.IS_RETRACTING_SPIKES);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRetractingSpikes(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Guardian.IS_RETRACTING_SPIKES, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getTargetEntityId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Guardian.TARGET_EID);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setTargetEntityId(int value) {
        // Calls a method
        metadata.set(MetadataDef.Guardian.TARGET_EID, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Entity getTarget() {
        // Returns a value to the caller
        return this.target;
    // End of a block/expression
    }

    // Start of a method/block
    public void setTarget(@Nullable Entity target) {
        // Access to the current/parent object
        this.target = target;
        // Calls a method
        setTargetEntityId(target == null ? 0 : target.getEntityId());
    // End of a block/expression
    }
// End of a block/expression
}
