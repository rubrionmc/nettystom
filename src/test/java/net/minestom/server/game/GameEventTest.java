// Package declaration for this file
package net.minestom.server.game;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Type declaration (class/interface/enum/record)
public class GameEventTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void load() {
        // Calls a method
        assertNotNull(GameEventImpl.REGISTRY);
    // End of a block/expression
    }
// End of a block/expression
}
