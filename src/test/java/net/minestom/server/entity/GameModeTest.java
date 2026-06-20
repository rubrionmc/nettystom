// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class GameModeTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void toId() {
        // Calls a method
        assertEquals(GameMode.SURVIVAL.ordinal(), 0);
        // Calls a method
        assertEquals(GameMode.CREATIVE.ordinal(), 1);
        // Calls a method
        assertEquals(GameMode.ADVENTURE.ordinal(), 2);
        // Calls a method
        assertEquals(GameMode.SPECTATOR.ordinal(), 3);
    // End of a block/expression
    }
// End of a block/expression
}
