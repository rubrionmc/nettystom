// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.RelativeFlags;
// Import of a required class
import net.minestom.server.event.trait.EntityEvent;
// Import of a required class
import net.minestom.server.utils.position.PositionUtils;
// Import of a required class
import org.intellij.lang.annotations.MagicConstant;

/**
 * Called with {@link Entity#teleport(Pos)} and its overloads.
 */
// Type declaration (class/interface/enum/record)
public class EntityTeleportEvent implements EntityEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final Pos teleportPosition;
    // Code statement
    private final int relativeFlags;

    // Assigns a value
    public EntityTeleportEvent(Entity entity, Pos teleportPosition, @MagicConstant(flagsFromClass = RelativeFlags.class) int relativeFlags) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.teleportPosition = teleportPosition;
        // Access to the current/parent object
        this.relativeFlags = relativeFlags;
    // End of a block/expression
    }

    /**
     * @return The {@link Entity} that teleported.
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }

    /**
     * @return The position that the {@link Entity} is about to teleport to. This is an absolute position.
     */
    // Start of a method/block
    public Pos getNewPosition() {
        // Returns a value to the caller
        return PositionUtils.getPositionWithRelativeFlags(this.getEntity().getPosition(), getTeleportPosition(), relativeFlags);
    // End of a block/expression
    }

    /**
     * @return The position that the {@link Entity} is about to teleport to. This may be (partially) relative depending on the flags.
     */
    // Start of a method/block
    public Pos getTeleportPosition() {
        // Returns a value to the caller
        return teleportPosition;
    // End of a block/expression
    }

    /**
     * @return The flags that determine which fields of the position are relative.
     */
    // Annotation for the following element
    @MagicConstant(flagsFromClass = RelativeFlags.class)
    // Start of a method/block
    public int getRelativeFlags() {
        // Returns a value to the caller
        return relativeFlags;
    // End of a block/expression
    }
// End of a block/expression
}
