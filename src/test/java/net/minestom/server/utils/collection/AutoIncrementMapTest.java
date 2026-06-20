// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class AutoIncrementMapTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test() {
        // Affecte une valeur
        AutoIncrementMap<String> map = new AutoIncrementMap<>();
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++) {
            // Appelle une méthode
            assertEquals(i, map.get("test" + i));
            // Boucle : répète un bloc
            for (int j = 0; j < i; j++) {
                // Appelle une méthode
                assertEquals(j, map.get("test" + j));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
