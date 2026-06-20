// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class MetadataIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void registeredTypesRoundTripDefaultEntries(Env env) {
        // Appelle une méthode
        final Registries registries = env.process();
        // Boucle : répète un bloc
        for (int id = 0; id < Metadata.typeCount(); id++) {
            // Appelle une méthode
            final Metadata.Type<?> type = Metadata.typeById(id);
            // Appelle une méthode
            assertNotNull(type, "Missing metadata type definition for id " + id);
            // Appelle une méthode
            final Metadata.Entry<?> entry = defaultEntry(type);
            // Appelle une méthode
            final byte[] bytes = NetworkBuffer.makeArray(Metadata.Entry.SERIALIZER, entry, registries);
            // Appelle une méthode
            final NetworkBuffer buffer = NetworkBuffer.wrap(bytes, 0, bytes.length, registries);

            // Appelle une méthode
            final Metadata.Entry<?> result = Metadata.Entry.SERIALIZER.read(buffer);

            // Appelle une méthode
            assertEquals(entry.type(), result.type(), "Wrong metadata type after round-trip for id " + id);
            // Embranchement : vérifie une condition
            if (entry.value() instanceof float[] expected && result.value() instanceof float[] actual) {
                // Appelle une méthode
                assertArrayEquals(expected, actual, "Wrong metadata value after round-trip for id " + id);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                assertEquals(entry.value(), result.value(), "Wrong metadata value after round-trip for id " + id);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> Metadata.Entry<T> defaultEntry(Metadata.Type<T> type) {
        // Renvoie une valeur à l'appelant
        return type.entry(type.defaultValue());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
