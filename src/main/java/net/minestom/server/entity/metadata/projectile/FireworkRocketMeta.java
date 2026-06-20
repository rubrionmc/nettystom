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
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class FireworkRocketMeta extends EntityMeta implements ProjectileMeta {
    // Code statement
    private Entity shooter;

    // Start of a method/block
    public FireworkRocketMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getFireworkInfo() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.FireworkRocketEntity.ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFireworkInfo(ItemStack value) {
        // Calls a method
        metadata.set(MetadataDef.FireworkRocketEntity.ITEM, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Integer getShooterEntityId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.FireworkRocketEntity.SHOOTER_ENTITY_ID);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setShooterEntityId(@Nullable Integer value) {
        // Calls a method
        metadata.set(MetadataDef.FireworkRocketEntity.SHOOTER_ENTITY_ID, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getShooter() {
        // Returns a value to the caller
        return this.shooter;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setShooter(@Nullable Entity value) {
        // Access to the current/parent object
        this.shooter = value;
        // Calls a method
        Integer entityID = value == null ? null : value.getEntityId();
        // Calls a method
        setShooterEntityId(entityID);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isShotAtAngle() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.FireworkRocketEntity.IS_SHOT_AT_ANGLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShotAtAngle(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.FireworkRocketEntity.IS_SHOT_AT_ANGLE, value);
    // End of a block/expression
    }

// End of a block/expression
}
