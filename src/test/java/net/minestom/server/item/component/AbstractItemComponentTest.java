// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.TestInstance;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Annotation for the following element
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Type declaration (class/interface/enum/record)
public abstract class AbstractItemComponentTest<T> {

    // Calls a method
    protected abstract DataComponent<T> component();

    // Calls a method
    protected abstract List<Map.Entry<String, T>> directReadWriteEntries();

    // Start of a method/block
    private Stream<Arguments> directReadWriteMethodSource() {
        // Returns a value to the caller
        return directReadWriteEntries().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest(name = "{0}")
    // Annotation for the following element
    @MethodSource("directReadWriteMethodSource")
    // Start of a method/block
    public void directReadWriteTest(String testName, T entry, Env env) {
        // Calls a method
        var coder = new RegistryTranscoder<>(Transcoder.NBT, env.process());
        // Branch: checks a condition
        if (component().isSerialized()) {
            // Calls a method
            var written1 = assertOk(component().encode(coder, entry));

            // Calls a method
            var read = assertOk(component().decode(coder, written1));
            // Calls a method
            assertEquals(entry, read);

            // Calls a method
            var written2 = assertOk(component().encode(coder, read));
            // Calls a method
            assertEquals(written1, written2);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (component().isSynced()) {
            // Calls a method
            var written1 = NetworkBuffer.makeArray(b -> component().write(b, entry), MinecraftServer.process());

            // Calls a method
            var buffer = NetworkBuffer.wrap(written1, 0, written1.length, MinecraftServer.process());
            // Calls a method
            var read = component().read(buffer);
            // Calls a method
            assertEquals(entry, read);

            // Calls a method
            var written2 = NetworkBuffer.makeArray(b -> component().write(b, entry), MinecraftServer.process());
            // Calls a method
            assertArrayEquals(written1, written2);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
