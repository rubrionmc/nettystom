// Package declaration for this file
package net.minestom.server.entity.metadata.avatar;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MainHand;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.LivingEntityMeta;

// Type declaration (class/interface/enum/record)
public class AvatarMeta extends LivingEntityMeta {

    // Start of a method/block
    protected AvatarMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }
    
    // Start of a method/block
    public MainHand getMainHand() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.MAIN_HAND);
    // End of a block/expression
    }

    // Start of a method/block
    public void setMainHand(MainHand value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.MAIN_HAND, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCapeEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_CAPE_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCapeEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_CAPE_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isJacketEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_JACKET_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setJacketEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_JACKET_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLeftSleeveEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_LEFT_SLEEVE_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftSleeveEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_LEFT_SLEEVE_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRightSleeveEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_RIGHT_SLEEVE_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightSleeveEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_RIGHT_SLEEVE_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLeftLegEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_LEFT_PANTS_LEG_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftLegEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_LEFT_PANTS_LEG_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRightLegEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_RIGHT_PANTS_LEG_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightLegEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_RIGHT_PANTS_LEG_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHatEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.IS_HAT_ENABLED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHatEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.IS_HAT_ENABLED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public byte getDisplayedSkinParts() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Avatar.DISPLAYED_MODEL_PARTS_FLAGS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDisplayedSkinParts(byte skinDisplayByte) {
        // Calls a method
        metadata.set(MetadataDef.Avatar.DISPLAYED_MODEL_PARTS_FLAGS, skinDisplayByte);
    // End of a block/expression
    }

// End of a block/expression
}
