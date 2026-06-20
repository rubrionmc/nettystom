// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Triggered when we receive a custom click packet from the client during the <b>configuration</b> state.
 *
 * @see PlayerCustomClickEvent
 */
// Type declaration (class/interface/enum/record)
public class PlayerConfigCustomClickEvent implements PlayerEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Key key;
    // Code statement
    private final BinaryTag payload;

    // Start of a method/block
    public PlayerConfigCustomClickEvent(Player player, Key key, @Nullable BinaryTag payload) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.key = key;
        // Access to the current/parent object
        this.payload = payload;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return this.player;
    // End of a block/expression
    }

    // Start of a method/block
    public Key getKey() {
        // Returns a value to the caller
        return this.key;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable BinaryTag getPayload() {
        // Returns a value to the caller
        return this.payload;
    // End of a block/expression
    }
// End of a block/expression
}
