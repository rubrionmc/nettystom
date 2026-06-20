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
public class WitherMeta extends MonsterMeta {
    // Code statement
    private @Nullable Entity centerHead;
    // Code statement
    private @Nullable Entity leftHead;
    // Code statement
    private @Nullable Entity rightHead;

    // Start of a method/block
    public WitherMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getCenterHeadEntityId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wither.CENTER_HEAD_TARGET);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setCenterHeadEntityId(int value) {
        // Calls a method
        metadata.set(MetadataDef.Wither.CENTER_HEAD_TARGET, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getCenterHead() {
        // Returns a value to the caller
        return this.centerHead;
    // End of a block/expression
    }

    // Start of a method/block
    public void setCenterHead(@Nullable Entity value) {
        // Access to the current/parent object
        this.centerHead = value;
        // Calls a method
        setCenterHeadEntityId(value == null ? 0 : value.getEntityId());
    // End of a block/expression
    }

    // Start of a method/block
    public int getLeftHeadEntityId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wither.LEFT_HEAD_TARGET);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setLeftHeadEntityId(int value) {
        // Calls a method
        metadata.set(MetadataDef.Wither.LEFT_HEAD_TARGET, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getLeftHead() {
        // Returns a value to the caller
        return this.leftHead;
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftHead(@Nullable Entity value) {
        // Access to the current/parent object
        this.leftHead = value;
        // Calls a method
        setLeftHeadEntityId(value == null ? 0 : value.getEntityId());
    // End of a block/expression
    }

    // Start of a method/block
    public int getRightHeadEntityId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wither.RIGHT_HEAD_TARGET);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setRightHeadEntityId(int value) {
        // Calls a method
        metadata.set(MetadataDef.Wither.RIGHT_HEAD_TARGET, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getRightHead() {
        // Returns a value to the caller
        return this.rightHead;
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightHead(@Nullable Entity value) {
        // Access to the current/parent object
        this.rightHead = value;
        // Calls a method
        setRightHeadEntityId(value == null ? 0 : value.getEntityId());
    // End of a block/expression
    }

    // Start of a method/block
    public int getInvulnerableTime() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wither.INVULNERABLE_TIME);
    // End of a block/expression
    }

    // Start of a method/block
    public void setInvulnerableTime(int value) {
        // Calls a method
        metadata.set(MetadataDef.Wither.INVULNERABLE_TIME, value);
    // End of a block/expression
    }

// End of a block/expression
}
