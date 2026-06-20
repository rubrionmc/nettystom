// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import org.junit.jupiter.api.Disabled;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotNull;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class PlayerSkinTest {

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void validName() {
        // Calls a method
        var skin = PlayerSkin.fromUsername("jeb_");
        // Calls a method
        assertNotNull(skin);
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidName() {
        // Calls a method
        var skin = PlayerSkin.fromUsername("jfdsa84vvcxadubasdfcvn");
        // Calls a method
        assertNull(skin);
    // End of a block/expression
    }
// End of a block/expression
}
