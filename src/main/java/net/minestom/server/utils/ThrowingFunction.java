// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface ThrowingFunction<I extends @UnknownNullability Object, O extends @UnknownNullability Object> {
    // Calls a method
    O apply(I i) throws Exception;

    // Start of a method/block
    static <T> ThrowingFunction<T, T> identity() {
        // Returns a value to the caller
        return t -> t;
    // End of a block/expression
    }
// End of a block/expression
}
