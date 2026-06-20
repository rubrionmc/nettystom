// Package declaration for this file
package net.minestom.server.entity.metadata.item;

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
import net.minestom.server.entity.metadata.projectile.ProjectileMeta;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class FireballMeta extends EntityMeta implements ObjectDataProvider, ProjectileMeta {
    // Code statement
    private Entity shooter;

    // Start of a method/block
    public FireballMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getItem() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fireball.ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setItem(ItemStack value) {
        // Calls a method
        metadata.set(MetadataDef.Fireball.ITEM, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getShooter() {
        // Returns a value to the caller
        return shooter;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setShooter(@Nullable Entity shooter) {
        // Access to the current/parent object
        this.shooter = shooter;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getObjectData() {
        // Returns a value to the caller
        return this.shooter == null ? 0 : this.shooter.getEntityId();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresVelocityPacketAtSpawn() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

// End of a block/expression
}
