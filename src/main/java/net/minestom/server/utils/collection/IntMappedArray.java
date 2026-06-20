// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.AbstractList;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.IntFunction;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class IntMappedArray<R> extends AbstractList<R> {
    // Code statement
    private final int[] elements;
    // Code statement
    private final IntFunction<R> function;

    // Start of a method/block
    public IntMappedArray(int[] elements, IntFunction<R> function) {
        // Access to the current/parent object
        this.elements = elements;
        // Access to the current/parent object
        this.function = function;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public R get(int index) {
        // Assigns a value
        final int[] elements = this.elements;
        // Calls a method
        Objects.checkIndex(index, elements.length);
        // Returns a value to the caller
        return function.apply(elements[index]);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int size() {
        // Returns a value to the caller
        return elements.length;
    // End of a block/expression
    }
// End of a block/expression
}
