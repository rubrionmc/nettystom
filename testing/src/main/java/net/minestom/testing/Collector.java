// Déclaration du paquet de ce fichier
package net.minestom.testing;


// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public interface Collector<T> {
    // Appelle une méthode
    List<T> collect();

    // Début d'une méthode/d'un bloc
    default <P extends T> void assertSingle(Class<P> type, Consumer<P> consumer) {
        // Appelle une méthode
        List<T> elements = collect();
        // Appelle une méthode
        assertEquals(1, elements.size(), "Expected 1 element, got " + elements);
        // Appelle une méthode
        var element = elements.getFirst();
        // Appelle une méthode
        assertInstanceOf(type, element, "Expected type " + type.getSimpleName() + ", got " + element.getClass().getSimpleName());
        //noinspection unchecked
        // Appelle une méthode
        consumer.accept((P) element);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void assertSingle(Consumer<T> consumer) {
        // Appelle une méthode
        List<T> elements = collect();
        // Appelle une méthode
        assertEquals(1, elements.size(), "Expected 1 element, got " + elements);
        // Appelle une méthode
        consumer.accept(elements.getFirst());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void assertCount(int count) {
        // Appelle une méthode
        List<T> elements = collect();
        // Appelle une méthode
        assertEquals(count, elements.size(), "Expected " + count + " element(s), got " + elements.size() + ": " + elements);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void assertCount(int count, Predicate<? super T> predicate) {
        // Appelle une méthode
        List<T> elements = collect();
        // Appelle une méthode
        long matchingCount = elements.stream().filter(predicate).count();
        // Appelle une méthode
        assertEquals(count, matchingCount, "Expected " + count + " element(s) matching the predicate, got " + matchingCount + ": " + elements);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void assertSingle() {
        // Appelle une méthode
        assertCount(1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void assertEmpty() {
        // Appelle une méthode
        assertCount(0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void assertAny() {
        // Appelle une méthode
        List<T> elements = collect();
        // Appelle une méthode
        assertFalse(elements.isEmpty(), "Expected at least 1 element, got none.");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Asserts that at least one element matches the given predicate.
     */
    // Début d'une méthode/d'un bloc
    default void assertAnyMatch(Predicate<T> predicate) {
        // Appelle une méthode
        List<T> elements = collect();
        // Instruction de code
        assertTrue(elements.stream().anyMatch(predicate),
                // Instruction de code
                "No elements matched the predicate. Elements: " + elements);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Asserts that no elements match the given predicate.
     */
    // Début d'une méthode/d'un bloc
    default void assertNoneMatch(Predicate<T> predicate) {
        // Appelle une méthode
        List<T> elements = collect();
        // Instruction de code
        assertFalse(elements.stream().anyMatch(predicate),
                // Appelle une méthode
                "Found elements that matched the predicate: " + elements.stream().filter(predicate).toList());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Asserts that all elements match the given predicate.
     */
    // Début d'une méthode/d'un bloc
    default void assertAllMatch(Predicate<T> predicate) {
        // Appelle une méthode
        List<T> elements = collect();
        // Instruction de code
        assertTrue(elements.stream().allMatch(predicate),
                // Instruction de code
                "Not all elements matched the predicate. Elements: " + elements);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
