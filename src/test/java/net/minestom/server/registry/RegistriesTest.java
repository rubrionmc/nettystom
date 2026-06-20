// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class RegistriesTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testMaterialPrototypes() {
        // Appelle une méthode
        var registries = Registries.vanilla();
        // Boucle : répète un bloc
        for (var entry : registries.material().values()) {
            // Appelle une méthode
            var prototype = entry.prototype();
            // Appelle une méthode
            Assertions.assertNotNull(prototype);
            // Embranchement : vérifie une condition
            if (prototype.isEmpty()) {
                // Appelle une méthode
                Assertions.assertSame(DataComponentMap.EMPTY, prototype);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
