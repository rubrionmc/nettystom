// Package declaration for this file
package net.minestom.server.entity.metadata.avatar;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.network.player.ResolvableProfile;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class MannequinMeta extends AvatarMeta {
    // Start of a method/block
    public MannequinMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ResolvableProfile getProfile() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.PROFILE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setProfile(ResolvableProfile value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.PROFILE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isImmovable() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IMMOVABLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setImmovable(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IMMOVABLE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Component getDescription() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.DESCRIPTION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDescription(@Nullable Component value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.DESCRIPTION, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCapeEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_CAPE_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCapeEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IS_CAPE_ENABLED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isJacketEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_JACKET_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setJacketEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IS_JACKET_ENABLED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isLeftSleeveEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_LEFT_SLEEVE_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setLeftSleeveEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IS_LEFT_SLEEVE_ENABLED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isRightSleeveEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_RIGHT_SLEEVE_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setRightSleeveEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IS_RIGHT_SLEEVE_ENABLED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isLeftLegEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_LEFT_PANTS_LEG_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setLeftLegEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IS_LEFT_PANTS_LEG_ENABLED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isRightLegEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_RIGHT_PANTS_LEG_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setRightLegEnabled(boolean value) {
        // Calls a method
        metadata.get(MetadataDef.Mannequin.IS_RIGHT_PANTS_LEG_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isHatEnabled() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.IS_HAT_ENABLED);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setHatEnabled(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.IS_HAT_ENABLED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte getDisplayedSkinParts() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Mannequin.DISPLAYED_MODEL_PARTS_FLAGS);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setDisplayedSkinParts(byte skinDisplayByte) {
        // Calls a method
        metadata.set(MetadataDef.Mannequin.DISPLAYED_MODEL_PARTS_FLAGS, skinDisplayByte);
    // End of a block/expression
    }
// End of a block/expression
}
