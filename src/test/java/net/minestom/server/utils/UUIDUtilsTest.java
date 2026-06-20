// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
class UUIDUtilsTest {
    // Calls a method
    private static final UUID TEST_UUID = UUID.fromString("d2ac7139-76a6-435b-b659-7852d34dd7a3");
    // Assigns a value
    private static final int[] TEST_INT_ARRAY = new int[]{
            // Code statement
            0xd2ac7139,
            // Code statement
            0x76a6435b,
            // Code statement
            0xb6597852,
            // Code statement
            0xd34dd7a3
    // End of a block/expression
    };

    // Annotation for the following element
    @Test
    // Start of a method/block
    void isUuid() {
        // Calls a method
        assertTrue(UUIDUtils.isUuid("d2ac7139-76a6-435b-b659-7852d34dd7a3"));
        // Calls a method
        assertFalse(UUIDUtils.isUuid("This is not a UUID"));
        // Calls a method
        assertFalse(UUIDUtils.isUuid("d2acL139-76a6-435b-b659-7852d34dd7a3"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void uuidToIntArray() {
        // Calls a method
        assertArrayEquals(TEST_INT_ARRAY, UUIDUtils.uuidToIntArray(TEST_UUID));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void intArrayToUuid() {
        // Calls a method
        assertEquals(TEST_UUID, UUIDUtils.intArrayToUuid(TEST_INT_ARRAY));
    // End of a block/expression
    }
// End of a block/expression
}