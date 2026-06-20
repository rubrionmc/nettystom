// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class MetadataIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void registeredTypesRoundTripDefaultEntries(Env env) {
        // Calls a method
        final Registries registries = env.process();
        // Loop: repeats a block
        for (int id = 0; id < Metadata.typeCount(); id++) {
            // Calls a method
            final Metadata.Type<?> type = Metadata.typeById(id);
            // Calls a method
            assertNotNull(type, "Missing metadata type definition for id " + id);
            // Calls a method
            final Metadata.Entry<?> entry = defaultEntry(type);
            // Calls a method
            final byte[] bytes = NetworkBuffer.makeArray(Metadata.Entry.SERIALIZER, entry, registries);
            // Calls a method
            final NetworkBuffer buffer = NetworkBuffer.wrap(bytes, 0, bytes.length, registries);

            // Calls a method
            final Metadata.Entry<?> result = Metadata.Entry.SERIALIZER.read(buffer);

            // Calls a method
            assertEquals(entry.type(), result.type(), "Wrong metadata type after round-trip for id " + id);
            // Branch: checks a condition
            if (entry.value() instanceof float[] expected && result.value() instanceof float[] actual) {
                // Calls a method
                assertArrayEquals(expected, actual, "Wrong metadata value after round-trip for id " + id);
            // Alternative branch of the condition
            } else {
                // Calls a method
                assertEquals(entry.value(), result.value(), "Wrong metadata value after round-trip for id " + id);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> Metadata.Entry<T> defaultEntry(Metadata.Type<T> type) {
        // Returns a value to the caller
        return type.entry(type.defaultValue());
    // End of a block/expression
    }
// End of a block/expression
}
