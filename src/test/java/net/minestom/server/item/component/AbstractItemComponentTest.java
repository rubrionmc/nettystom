// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

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
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.TestInstance;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Annotation pour l'élément suivant
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// Déclaration de type (classe/interface/enum/record)
public abstract class AbstractItemComponentTest<T> {

    // Appelle une méthode
    protected abstract DataComponent<T> component();

    // Appelle une méthode
    protected abstract List<Map.Entry<String, T>> directReadWriteEntries();

    // Début d'une méthode/d'un bloc
    private Stream<Arguments> directReadWriteMethodSource() {
        // Renvoie une valeur à l'appelant
        return directReadWriteEntries().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest(name = "{0}")
    // Annotation pour l'élément suivant
    @MethodSource("directReadWriteMethodSource")
    // Début d'une méthode/d'un bloc
    public void directReadWriteTest(String testName, T entry, Env env) {
        // Appelle une méthode
        var coder = new RegistryTranscoder<>(Transcoder.NBT, env.process());
        // Embranchement : vérifie une condition
        if (component().isSerialized()) {
            // Appelle une méthode
            var written1 = assertOk(component().encode(coder, entry));

            // Appelle une méthode
            var read = assertOk(component().decode(coder, written1));
            // Appelle une méthode
            assertEquals(entry, read);

            // Appelle une méthode
            var written2 = assertOk(component().encode(coder, read));
            // Appelle une méthode
            assertEquals(written1, written2);
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (component().isSynced()) {
            // Appelle une méthode
            var written1 = NetworkBuffer.makeArray(b -> component().write(b, entry), MinecraftServer.process());

            // Appelle une méthode
            var buffer = NetworkBuffer.wrap(written1, 0, written1.length, MinecraftServer.process());
            // Appelle une méthode
            var read = component().read(buffer);
            // Appelle une méthode
            assertEquals(entry, read);

            // Appelle une méthode
            var written2 = NetworkBuffer.makeArray(b -> component().write(b, entry), MinecraftServer.process());
            // Appelle une méthode
            assertArrayEquals(written1, written2);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
