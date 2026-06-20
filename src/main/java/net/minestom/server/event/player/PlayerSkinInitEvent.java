// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerSkin;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Called at the player connection to initialize his skin.
 */
// Type declaration (class/interface/enum/record)
public class PlayerSkinInitEvent implements PlayerEvent {

    // Code statement
    private final Player player;
    // Code statement
    private PlayerSkin skin;

    // Start of a method/block
    public PlayerSkinInitEvent(Player player, @Nullable PlayerSkin currentSkin) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.skin = currentSkin;
    // End of a block/expression
    }

    /**
     * Gets the spawning skin of the player.
     *
     * @return the player skin, or null if not any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public PlayerSkin getSkin() {
        // Returns a value to the caller
        return skin;
    // End of a block/expression
    }

    /**
     * Sets the spawning skin of the player.
     *
     * @param skin the new player skin
     */
    // Start of a method/block
    public void setSkin(@Nullable PlayerSkin skin) {
        // Access to the current/parent object
        this.skin = skin;
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
