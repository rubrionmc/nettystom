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
// Import of a required class
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class FishingHookMeta extends EntityMeta implements ObjectDataProvider {
    // Code statement
    private Entity hooked;
    // Code statement
    private Entity owner;

    // Start of a method/block
    public FishingHookMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getHookedEntityId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.FishingHook.HOOKED);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setHookedEntityId(int value) {
        // Calls a method
        metadata.set(MetadataDef.FishingHook.HOOKED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getHookedEntity() {
        // Returns a value to the caller
        return this.hooked;
    // End of a block/expression
    }

    // Start of a method/block
    public void setHookedEntity(@Nullable Entity value) {
        // Access to the current/parent object
        this.hooked = value;
        // Calls a method
        int entityID = value == null ? 0 : value.getEntityId() + 1;
        // Calls a method
        setHookedEntityId(entityID);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCatchable() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.FishingHook.IS_CATCHABLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCatchable(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.FishingHook.IS_CATCHABLE, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getOwnerEntity() {
        // Returns a value to the caller
        return owner;
    // End of a block/expression
    }

    // Start of a method/block
    public void setOwnerEntity(@Nullable Entity value) {
        // Access to the current/parent object
        this.owner = value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getObjectData() {
        // Returns a value to the caller
        return owner != null ? owner.getEntityId() : 0;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresVelocityPacketAtSpawn() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
