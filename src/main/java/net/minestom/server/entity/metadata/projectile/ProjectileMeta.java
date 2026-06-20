// Package declaration for this file
package net.minestom.server.entity.metadata.projectile;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public interface ProjectileMeta {

    // Annotation for the following element
    @Nullable
    // Calls a method
    Entity getShooter();

    // Calls a method
    void setShooter(@Nullable Entity shooter);

// End of a block/expression
}
