// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.animal.AnimalMeta;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public class TameableAnimalMeta extends AnimalMeta {
    // Start of a method/block
    protected TameableAnimalMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSitting() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TameableAnimal.IS_SITTING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSitting(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TameableAnimal.IS_SITTING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isTamed() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TameableAnimal.IS_TAMED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTamed(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TameableAnimal.IS_TAMED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public UUID getOwner() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TameableAnimal.OWNER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setOwner(@Nullable UUID value) {
        // Calls a method
        metadata.set(MetadataDef.TameableAnimal.OWNER, value);
    // End of a block/expression
    }

// End of a block/expression
}
