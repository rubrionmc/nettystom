// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.LivingEntityMeta;

// Type declaration (class/interface/enum/record)
public class ArmorStandMeta extends LivingEntityMeta {
    // Start of a method/block
    public ArmorStandMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSmall() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.IS_SMALL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSmall(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.IS_SMALL, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasArms() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.HAS_ARMS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasArms(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.HAS_ARMS, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasNoBasePlate() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.HAS_NO_BASE_PLATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasNoBasePlate(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.HAS_NO_BASE_PLATE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isMarker() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.IS_MARKER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setMarker(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.IS_MARKER, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getHeadRotation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.HEAD_ROTATION).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setHeadRotation(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.HEAD_ROTATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getBodyRotation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.BODY_ROTATION).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setBodyRotation(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.BODY_ROTATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getLeftArmRotation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.LEFT_ARM_ROTATION).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftArmRotation(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.LEFT_ARM_ROTATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getRightArmRotation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.RIGHT_ARM_ROTATION).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightArmRotation(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.RIGHT_ARM_ROTATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getLeftLegRotation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.LEFT_LEG_ROTATION).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftLegRotation(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.LEFT_LEG_ROTATION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Vec getRightLegRotation() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ArmorStand.RIGHT_LEG_ROTATION).asVec();
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightLegRotation(Vec value) {
        // Calls a method
        metadata.set(MetadataDef.ArmorStand.RIGHT_LEG_ROTATION, value);
    // End of a block/expression
    }

// End of a block/expression
}
