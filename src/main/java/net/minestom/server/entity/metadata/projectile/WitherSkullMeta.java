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
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class WitherSkullMeta extends EntityMeta implements ObjectDataProvider, ProjectileMeta {
    // Code statement
    private @Nullable Entity shooter;

    // Start of a method/block
    public WitherSkullMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isInvulnerable() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.WitherSkull.IS_INVULNERABLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setInvulnerable(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.WitherSkull.IS_INVULNERABLE, value);
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
