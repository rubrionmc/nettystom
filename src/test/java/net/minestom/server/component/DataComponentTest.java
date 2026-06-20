// Déclaration du paquet de ce fichier
package net.minestom.server.component;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class DataComponentTest {
    // Annotation pour l'élément suivant
    @Test
    // Instruction de code
    public void registry(Env env) { // Tricky registry; so we ensure they are loaded (requires class loading before accessible keys)
        // Appelle une méthode
        Assertions.assertNotNull(DataComponent.fromKey(Key.key("lore")), "Registry class was not initialized");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void stringFromKey(Env env) {
        // Appelle une méthode
        Assertions.assertSame(DataComponent.fromKey("lore"), DataComponent.fromKey(Key.key("lore")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStatic(Env env) {
        // Appelle une méthode
        Assertions.assertSame(DataComponents.LORE, DataComponent.fromKey("lore"));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
