// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import com.google.gson.Gson;
// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.minestom.data.MinestomData;
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
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.io.InputStreamReader;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ItemComponentReadWriteTest {
    // Calls a method
    private static final Gson GSON = new Gson();

    // Code statement
    private static final Transcoder<JsonElement> CODER;

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
        // Calls a method
        CODER = new RegistryTranscoder<>(Transcoder.JSON, MinecraftServer.process());
    // End of a block/expression
    }

    // This test will go through all of the default components present on vanilla items and make sure that we are
    // capable of reading/writing them correctly. This will help to find cases where fields have changed in case
    // they are otherwise missed.
    // Notably this does not test every component because they are not all used in vanilla, let alone on default items.
    //
    // Additional entries can be added by appending them to the following list:
    // Assigns a value
    private static final Map<String, JsonElement> EXTRA_CASES = Map.ofEntries(
            // Code statement
            entry("minecraft:glider", new JsonObject())
    // End of a block/expression
    );

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testReadWrite() throws IOException {
        // Calls a method
        var componentEntries = new ArrayList<>(EXTRA_CASES.entrySet());
        // Exception handling
        try (InputStream is = MinestomData.resource("item.json")) {
            // Calls a method
            Objects.requireNonNull(is, "item.json not found");

            // Calls a method
            var object = GSON.fromJson(new InputStreamReader(is), JsonObject.class);
            // Loop: repeats a block
            for (var itemEntry : object.entrySet()) {
                // Loop: repeats a block
                for (var componentEntry : itemEntry.getValue().getAsJsonObject().getAsJsonObject("components").entrySet()) {
                    // Calls a method
                    componentEntries.add(entry(componentEntry.getKey(), componentEntry.getValue()));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        assertAll(componentEntries.stream().map(entry -> () -> {
            // Calls a method
            var component = DataComponent.fromKey(entry.getKey());
            // Calls a method
            assertNotNull(component, "Component not found: " + entry.getKey());
            //noinspection unchecked
            // Calls a method
            readWriteTestImpl((DataComponent<Object>) component, entry.getValue());
        // Code statement
        }));
    // End of a block/expression
    }

    // Start of a method/block
    private static void readWriteTestImpl(DataComponent<Object> component, JsonElement input) {
        // Exception handling
        try {
            // Calls a method
            var value = assertOk(component.decode(CODER, input));
            // Calls a method
            var actual = assertOk(component.encode(CODER, value));
            // This is pretty cursed but we need to serialize and reparse because the JsonPrimitive number implementation changes
            // When reading from a string it has LazilyParsedNumber which is NOT equal to `new JsonPrimitive(1)` for example.
            // Calls a method
            var actualParsed = GSON.fromJson(actual.toString(), JsonElement.class);
            // Calls a method
            var inputParsed = GSON.fromJson(input.toString(), JsonElement.class);

            //TODO(26.1) see If this is a problem.
            // Branch: checks a condition
            if (actualParsed.isJsonObject() && actualParsed.getAsJsonObject().has("count"))
                // Calls a method
                actualParsed.getAsJsonObject().remove("count");

            // Need to rewrite because adventure formats slightly different from vanilla.
            // Code statement
            assertEquals(inputParsed, actualParsed, () -> "\n--- " + component.name() + " (NBT) ---\n" +
                    // Code statement
                    "EXP: " + input + "\n" +
                    // Code statement
                    "ACT: " + actualParsed);

            // Branch: checks a condition
            if (component.isSynced()) {
                // Calls a method
                var buffer = NetworkBuffer.resizableBuffer(MinecraftServer.process());
                // Calls a method
                component.write(buffer, value);
                // Calls a method
                var comp2 = component.read(buffer);
                // Calls a method
                var expected2 = assertOk(component.encode(CODER, comp2));
                // Code statement
                assertEquals(expected2, actual, () -> "\n--- " + component.name() + " (NETWORK) ---\n" +
                        // Code statement
                        "EXP: " + expected2 + "\n" +
                        // Code statement
                        "ACT: " + actual);
            // End of a block/expression
            }
        // Start of a method/block
        } catch (AssertionError | Exception e) {
            // Throws an exception
            throw new AssertionError(component.name() + " failed on \"" + input + "\"", e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertEqualsJson(JsonElement expected, JsonElement actual) {

    // End of a block/expression
    }
// End of a block/expression
}

