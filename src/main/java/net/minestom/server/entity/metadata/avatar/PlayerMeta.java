// Package declaration for this file
package net.minestom.server.entity.metadata.avatar;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class PlayerMeta extends AvatarMeta {
    // Start of a method/block
    public PlayerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public float getAdditionalHearts() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Player.ADDITIONAL_HEARTS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAdditionalHearts(float value) {
        // Calls a method
        metadata.set(MetadataDef.Player.ADDITIONAL_HEARTS, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getScore() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Player.SCORE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setScore(int value) {
        // Calls a method
        metadata.set(MetadataDef.Player.SCORE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Integer getLeftShoulderEntityData() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Player.LEFT_SHOULDER_ENTITY_DATA);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftShoulderEntityData(@Nullable Integer value) {
        // Calls a method
        metadata.set(MetadataDef.Player.LEFT_SHOULDER_ENTITY_DATA, value);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Integer getRightShoulderEntityData() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Player.RIGHT_SHOULDER_ENTITY_DATA);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightShoulderEntityData(@Nullable Integer value) {
        // Calls a method
        metadata.set(MetadataDef.Player.RIGHT_SHOULDER_ENTITY_DATA, value);
    // End of a block/expression
    }

// End of a block/expression
}
