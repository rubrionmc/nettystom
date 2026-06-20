// Package declaration for this file
package net.minestom.server.network.debug;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class DebugSubscriptionTest {

    // Annotation for the following element
    @Test
    // Code statement
    void testLookup() { // Bug when first introduced when the `DebugSubscriptions` was not loaded.
        // Code statement
        assertNotNull(DebugSubscription.fromId(0)); // Possible case where we returned null and provoke a NPE.
        // Calls a method
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> assertNull(DebugSubscription.fromId(-1)));
    // End of a block/expression
    }
// End of a block/expression
}
