// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player tries to pick an entity (middle-click).
 */
// Type declaration (class/interface/enum/record)
public class PlayerPickEntityEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;

    // Code statement
    private final Entity entityTarget;
    // Code statement
    private final boolean includeData;

    // Code statement
    public PlayerPickEntityEvent(Player player, @Nullable Entity entityTarget,
                                 // Start of a method/block
                                 boolean includeData) {
        // Access to the current/parent object
        this.player = player;

        // Access to the current/parent object
        this.entityTarget = entityTarget;
        // Access to the current/parent object
        this.includeData = includeData;
    // End of a block/expression
    }

    /**
     * Gets the entity which was picked. May be null if the entity is not known by the server (eg spawned with packets).
     *
     * @return the entity which was picked
     */
    // Start of a method/block
    public @Nullable Entity getTarget() {
        // Returns a value to the caller
        return entityTarget;
    // End of a block/expression
    }

    /**
     * Get if the entity data should be included in the result (control middle-click).
     *
     * @return if the entity data should be included.
     */
    // Start of a method/block
    public boolean isIncludeData() {
        // Returns a value to the caller
        return this.includeData;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }
// End of a block/expression
}
