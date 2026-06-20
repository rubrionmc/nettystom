// Package declaration for this file
package net.minestom.server.entity.metadata.projectile;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;

// Type declaration (class/interface/enum/record)
public class AbstractArrowMeta extends EntityMeta {
    // Start of a method/block
    protected AbstractArrowMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCritical() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractArrow.IS_CRITICAL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCritical(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractArrow.IS_CRITICAL, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isNoClip() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractArrow.IS_NO_CLIP);
    // End of a block/expression
    }

    // Start of a method/block
    public void setNoClip(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractArrow.IS_NO_CLIP, value);
    // End of a block/expression
    }

    // Start of a method/block
    public byte getPiercingLevel() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractArrow.PIERCING_LEVEL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPiercingLevel(byte value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractArrow.PIERCING_LEVEL, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isInGround() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractArrow.IN_GROUND);
    // End of a block/expression
    }

    // Start of a method/block
    public void setInGround(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractArrow.IN_GROUND, value);
    // End of a block/expression
    }

// End of a block/expression
}
