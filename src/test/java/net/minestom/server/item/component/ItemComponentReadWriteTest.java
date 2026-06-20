// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import com.google.gson.Gson;
// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.io.InputStreamReader;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ItemComponentReadWriteTest {
    // Appelle une méthode
    private static final Gson GSON = new Gson();

    // Instruction de code
    private static final Transcoder<JsonElement> CODER;

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
        // Appelle une méthode
        CODER = new RegistryTranscoder<>(Transcoder.JSON, MinecraftServer.process());
    // Fin d'un bloc/d'une expression
    }

    // This test will go through all of the default components present on vanilla items and make sure that we are
    // capable of reading/writing them correctly. This will help to find cases where fields have changed in case
    // they are otherwise missed.
    // Notably this does not test every component because they are not all used in vanilla, let alone on default items.
    //
    // Additional entries can be added by appending them to the following list:
    // Affecte une valeur
    private static final Map<String, JsonElement> EXTRA_CASES = Map.ofEntries(
            // Instruction de code
            entry("minecraft:glider", new JsonObject())
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testReadWrite() throws IOException {
        // Appelle une méthode
        var componentEntries = new ArrayList<>(EXTRA_CASES.entrySet());
        // Gestion des exceptions
        try (InputStream is = ItemComponentReadWriteTest.class.getResourceAsStream("/item.json")) {
            // Appelle une méthode
            Check.notNull(is, "items.json not found");

            // Appelle une méthode
            var object = GSON.fromJson(new InputStreamReader(is), JsonObject.class);
            // Boucle : répète un bloc
            for (var itemEntry : object.entrySet()) {
                // Boucle : répète un bloc
                for (var componentEntry : itemEntry.getValue().getAsJsonObject().getAsJsonObject("components").entrySet()) {
                    // Appelle une méthode
                    componentEntries.add(entry(componentEntry.getKey(), componentEntry.getValue()));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        assertAll(componentEntries.stream().map(entry -> () -> {
            // Appelle une méthode
            var component = DataComponent.fromKey(entry.getKey());
            // Appelle une méthode
            assertNotNull(component, "Component not found: " + entry.getKey());
            //noinspection unchecked
            // Appelle une méthode
            readWriteTestImpl((DataComponent<Object>) component, entry.getValue());
        // Instruction de code
        }));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void readWriteTestImpl(DataComponent<Object> component, JsonElement input) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            var value = assertOk(component.decode(CODER, input));
            // Appelle une méthode
            var actual = assertOk(component.encode(CODER, value));
            // This is pretty cursed but we need to serialize and reparse because the JsonPrimitive number implementation changes
            // When reading from a string it has LazilyParsedNumber which is NOT equal to `new JsonPrimitive(1)` for example.
            // Appelle une méthode
            var actualParsed = GSON.fromJson(actual.toString(), JsonElement.class);
            // Appelle une méthode
            var inputParsed = GSON.fromJson(input.toString(), JsonElement.class);

            // Need to rewrite because adventure formats slightly different from vanilla.
            // Instruction de code
            assertEquals(inputParsed, actualParsed, () -> "\n--- " + component.name() + " (NBT) ---\n" +
                    // Instruction de code
                    "EXP: " + input + "\n" +
                    // Appelle une méthode
                    "ACT: " + actualParsed.toString());

            // Embranchement : vérifie une condition
            if (component.isSynced()) {
                // Appelle une méthode
                var buffer = NetworkBuffer.resizableBuffer(MinecraftServer.process());
                // Appelle une méthode
                component.write(buffer, value);
                // Appelle une méthode
                var comp2 = component.read(buffer);
                // Appelle une méthode
                var expected2 = assertOk(component.encode(CODER, comp2));
                // Instruction de code
                assertEquals(expected2, actual, () -> "\n--- " + component.name() + " (NETWORK) ---\n" +
                        // Instruction de code
                        "EXP: " + expected2 + "\n" +
                        // Instruction de code
                        "ACT: " + actual);
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } catch (AssertionError | Exception e) {
            // Lève une exception
            throw new AssertionError(component.name() + " failed on \"" + input + "\"", e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertEqualsJson(JsonElement expected, JsonElement actual) {

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

