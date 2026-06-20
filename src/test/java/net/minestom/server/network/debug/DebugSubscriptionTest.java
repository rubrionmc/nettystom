// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class DebugSubscriptionTest {

    // Annotation pour l'élément suivant
    @Test
    // Instruction de code
    void testLookup() { // Bug when first introduced when the `DebugSubscriptions` was not loaded.
        // Instruction de code
        assertNotNull(DebugSubscription.fromId(0)); // Possible case where we returned null and provoke a NPE.
        // Appelle une méthode
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> assertNull(DebugSubscription.fromId(-1)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
