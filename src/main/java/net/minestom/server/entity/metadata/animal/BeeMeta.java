// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class BeeMeta extends AnimalMeta {
    // Start of a method/block
    public BeeMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRolling() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Bee.IS_ROLLING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRolling(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Bee.IS_ROLLING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasStung() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Bee.HAS_STUNG);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasStung(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Bee.HAS_STUNG, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasNectar() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Bee.HAS_NECTAR);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasNectar(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Bee.HAS_NECTAR, value);
    // End of a block/expression
    }

    // Start of a method/block
    public long getAngerEndTime() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Bee.ANGER_END_TIME);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAngerEndTime(long value) {
        // Calls a method
        metadata.set(MetadataDef.Bee.ANGER_END_TIME, value);
    // End of a block/expression
    }

// End of a block/expression
}
