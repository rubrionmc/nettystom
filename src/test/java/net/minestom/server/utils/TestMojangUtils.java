// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.utils.mojang.MojangUtils;
// Import of a required class
import org.junit.jupiter.api.Disabled;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TestMojangUtils {
    // Calls a method
    private final UUID JEB_UUID = UUID.fromString("853c80ef-3c37-49fd-aa49-938b674adae6");

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testValidNameWorks() {
        // Calls a method
        var result = MojangUtils.fromUsername("jeb_");
        // Calls a method
        assertNotNull(result);
        // Calls a method
        assertEquals("jeb_", result.get("name").getAsString());
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInvalidNameReturnsNull() {
        // Assigns a value
        var result = MojangUtils.fromUsername("jfdsa84vvcxadubasdfcvn"); // Longer than 16, always invalid
        // Calls a method
        assertNull(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testValidUuidWorks() {
        // Calls a method
        var result = MojangUtils.fromUuid(JEB_UUID.toString());
        // Calls a method
        assertNotNull(result);
        // Calls a method
        assertEquals("jeb_", result.get("name").getAsString());
        // Calls a method
        assertEquals("853c80ef3c3749fdaa49938b674adae6", result.get("id").getAsString());
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInvalidUuidReturnsNull() {
        // Assigns a value
        var result = MojangUtils.fromUuid("853c80ef3c3749fdaa49938b674adae6a"); // Longer than 32, always invalid
        // Calls a method
        assertNull(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNonExistentUuidReturnsNull() {
        // Calls a method
        var result = MojangUtils.fromUuid("00000000-0000-0000-0000-000000000000");
        // Calls a method
        assertNull(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testValidUUIDWorks() {
        // Calls a method
        var result = MojangUtils.fromUuid(JEB_UUID);
        // Calls a method
        assertNotNull(result);
        // Calls a method
        assertEquals("jeb_", result.get("name").getAsString());
        // Calls a method
        assertEquals("853c80ef3c3749fdaa49938b674adae6", result.get("id").getAsString());
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGetValidNameWorks() throws IOException {
        // Calls a method
        assertEquals(JEB_UUID, MojangUtils.getUUID("jeb_"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGetValidUUIDWorks() throws IOException {
        // Calls a method
        assertEquals("jeb_", MojangUtils.getUsername(JEB_UUID));
    // End of a block/expression
    }

    // Annotation for the following element
    @Disabled
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGetInvalidNameThrows() {
        // Code statement
        assertThrows(IOException.class, () -> MojangUtils.getUUID("a")); // Too short
    // End of a block/expression
    }
// End of a block/expression
}
