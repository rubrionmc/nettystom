// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.predicate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Nested;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;
// Import statique d'un membre
import static org.junit.jupiter.params.provider.Arguments.arguments;

// Déclaration de type (classe/interface/enum/record)
public class PropertiesPredicateTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMultiMatch() {
        // Affecte une valeur
        var predicate = new PropertiesPredicate(Map.of("facing", new PropertiesPredicate.ValuePredicate.Exact("east"),
                // Appelle une méthode
                "shape", new PropertiesPredicate.ValuePredicate.Exact("inner_left")));
        // Appelle une méthode
        assertTrue(predicate.test(Block.STONE_STAIRS.withProperties(Map.of("facing", "east", "shape", "inner_left"))));
        // Appelle une méthode
        assertFalse(predicate.test(Block.STONE_STAIRS.withProperties(Map.of("facing", "east"))));
        // Appelle une méthode
        assertFalse(predicate.test(Block.STONE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nested
    // Déclaration de type (classe/interface/enum/record)
    class ValuePredicate {

        // Début d'une méthode/d'un bloc
        private static Stream<Arguments> exactTests() {
            // Renvoie une valeur à l'appelant
            return Stream.of(
                    // name, expected, actual, valid
                    // Instruction de code
                    arguments("success", "value", "value", true),
                    // Instruction de code
                    arguments("fail", "value", "other", false),
                    // Instruction de code
                    arguments("missing exp", null, "value", false),
                    // Instruction de code
                    arguments("missing act", "value", null, false)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @ParameterizedTest(name = "{0}")
        // Annotation pour l'élément suivant
        @MethodSource("exactTests")
        // Début d'une méthode/d'un bloc
        public void matchExact(String name, String expected, String actual, boolean valid) {
            // Appelle une méthode
            var predicate = new PropertiesPredicate.ValuePredicate.Exact(expected);
            // Appelle une méthode
            assertEquals(valid, predicate.test(actual));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static Stream<Arguments> rangeTests() {
            // Renvoie une valeur à l'appelant
            return Stream.of(
                    // name, min, max, value, valid
                    // Instruction de code
                    arguments("int / min exact", "0", null, "0", true),
                    // Instruction de code
                    arguments("int / min too low (inclusive)", "1", null, "0", false),
                    // Instruction de code
                    arguments("int / max exact", null, "1", "0", true),
                    // Instruction de code
                    arguments("int / max too high (exclusive)", null, "1", "1", false),
                    // Instruction de code
                    arguments("int / range good a", "0", "2", "1", true),
                    // Instruction de code
                    arguments("int / range good b", "0", "20", "11", true),
                    // Instruction de code
                    arguments("int / range too low", "0", "2", "-1", false),
                    // Instruction de code
                    arguments("int / range too high", "0", "2", "3", false),

                    // Instruction de code
                    arguments("string / min exact", "a", null, "a", true),
                    // Instruction de code
                    arguments("string / max exact", null, "b", "a", true),
                    // Instruction de code
                    arguments("string / range good", "c", "g", "e", true),
                    // Instruction de code
                    arguments("string / range bad low", "c", "g", "a", false),
                    // Instruction de code
                    arguments("string / range bad high", "c", "g", "z", false)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @ParameterizedTest(name = "{0}")
        // Annotation pour l'élément suivant
        @MethodSource("rangeTests")
        // Début d'une méthode/d'un bloc
        public void matchRange(String name, String min, String max, String value, boolean valid) {
            // Appelle une méthode
            var predicate = new PropertiesPredicate.ValuePredicate.Range(min, max);
            // Appelle une méthode
            assertEquals(valid, predicate.test(value));
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
