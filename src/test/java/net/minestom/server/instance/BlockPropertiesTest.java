// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.utils.block.BlockUtils.parseProperties;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class BlockPropertiesTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("[]"));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties(""));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void noBrackets() {
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("random test without brackets"));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("["));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("[end"));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("[random test without end bracket"));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("]"));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("start]"));
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("random test without start bracket]"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void spaces() {
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("[    ]"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void comma() {
        // Appelle une méthode
        assertEquals(Map.of(), parseProperties("[  , , ,,,,  ]"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void single() {
        // Appelle une méthode
        assertEquals(Map.of("facing", "east"), parseProperties("[facing=east]"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleSpace() {
        // Appelle une méthode
        assertEquals(Map.of("facing", "east", "key", "value"), parseProperties("[facing=east,key=value ]"));
        // Appelle une méthode
        assertEquals(Map.of("facing", "east", "key", "value"), parseProperties("[ facing = east, key= value ]"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void allLengths() {
        // Verify all length variations
        // Boucle : répète un bloc
        for (int i = 0; i < 13; i++) {
            // Appelle une méthode
            StringBuilder properties = new StringBuilder("[");
            // Boucle : répète un bloc
            for (int j = 0; j < i; j++) {
                // Appelle une méthode
                properties.append("key").append(j).append("=value").append(j);
                // Embranchement : vérifie une condition
                if (j != i - 1) properties.append(",");
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            properties.append("]");

            // Appelle une méthode
            var map = parseProperties(properties.toString());
            // Appelle une méthode
            assertEquals(i, map.size());
            // Boucle : répète un bloc
            for (int j = 0; j < i; j++) {
                // Appelle une méthode
                assertEquals("value" + j, map.get("key" + j));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void corrupted() {
        // Affecte une valeur
        final int size = 12;
        // Appelle une méthode
        StringBuilder properties = new StringBuilder("[");
        // Boucle : répète un bloc
        for (int j = 0; j < size; j++) {
            // Appelle une méthode
            properties.append("key").append(j).append("=value").append(j);
            // Embranchement : vérifie une condition
            if (j != size - 1) properties.append(",");
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        properties.append(", , ,]");

        // Appelle une méthode
        var map = parseProperties(properties.toString());
        // Appelle une méthode
        assertEquals(size, map.size());
        // Boucle : répète un bloc
        for (int j = 0; j < size; j++) {
            // Appelle une méthode
            assertEquals("value" + j, map.get("key" + j));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
