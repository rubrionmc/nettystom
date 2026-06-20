// Package declaration for this file
package net.minestom.testing;


// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Predicate;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public interface Collector<T> {
    // Calls a method
    List<T> collect();

    // Start of a method/block
    default <P extends T> void assertSingle(Class<P> type, Consumer<P> consumer) {
        // Calls a method
        List<T> elements = collect();
        // Calls a method
        assertEquals(1, elements.size(), "Expected 1 element, got " + elements);
        // Calls a method
        var element = elements.getFirst();
        // Calls a method
        assertInstanceOf(type, element, "Expected type " + type.getSimpleName() + ", got " + element.getClass().getSimpleName());
        //noinspection unchecked
        // Calls a method
        consumer.accept((P) element);
    // End of a block/expression
    }

    // Start of a method/block
    default void assertSingle(Consumer<T> consumer) {
        // Calls a method
        List<T> elements = collect();
        // Calls a method
        assertEquals(1, elements.size(), "Expected 1 element, got " + elements);
        // Calls a method
        consumer.accept(elements.getFirst());
    // End of a block/expression
    }

    // Start of a method/block
    default void assertCount(int count) {
        // Calls a method
        List<T> elements = collect();
        // Calls a method
        assertEquals(count, elements.size(), "Expected " + count + " element(s), got " + elements.size() + ": " + elements);
    // End of a block/expression
    }

    // Start of a method/block
    default void assertCount(int count, Predicate<? super T> predicate) {
        // Calls a method
        List<T> elements = collect();
        // Calls a method
        long matchingCount = elements.stream().filter(predicate).count();
        // Calls a method
        assertEquals(count, matchingCount, "Expected " + count + " element(s) matching the predicate, got " + matchingCount + ": " + elements);
    // End of a block/expression
    }

    // Start of a method/block
    default void assertSingle() {
        // Calls a method
        assertCount(1);
    // End of a block/expression
    }

    // Start of a method/block
    default void assertEmpty() {
        // Calls a method
        assertCount(0);
    // End of a block/expression
    }

    // Start of a method/block
    default void assertAny() {
        // Calls a method
        List<T> elements = collect();
        // Calls a method
        assertFalse(elements.isEmpty(), "Expected at least 1 element, got none.");
    // End of a block/expression
    }

    /**
     * Asserts that at least one element matches the given predicate.
     */
    // Start of a method/block
    default void assertAnyMatch(Predicate<T> predicate) {
        // Calls a method
        List<T> elements = collect();
        // Code statement
        assertTrue(elements.stream().anyMatch(predicate),
                // Code statement
                "No elements matched the predicate. Elements: " + elements);
    // End of a block/expression
    }

    /**
     * Asserts that no elements match the given predicate.
     */
    // Start of a method/block
    default void assertNoneMatch(Predicate<T> predicate) {
        // Calls a method
        List<T> elements = collect();
        // Code statement
        assertFalse(elements.stream().anyMatch(predicate),
                // Calls a method
                "Found elements that matched the predicate: " + elements.stream().filter(predicate).toList());
    // End of a block/expression
    }

    /**
     * Asserts that all elements match the given predicate.
     */
    // Start of a method/block
    default void assertAllMatch(Predicate<T> predicate) {
        // Calls a method
        List<T> elements = collect();
        // Code statement
        assertTrue(elements.stream().allMatch(predicate),
                // Code statement
                "Not all elements matched the predicate. Elements: " + elements);
    // End of a block/expression
    }
// End of a block/expression
}
