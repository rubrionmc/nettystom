// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Iterator;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Function;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public record MappedCollection<O, R>(Collection<O> original,
                                     // Start of a method/block
                                     Function<O, R> mapper) implements Collection<R> {
    // Start of a method/block
    public static <O extends AtomicReference<R>, R> MappedCollection<O, R> plainReferences(Collection<O> original) {
        // Returns a value to the caller
        return new MappedCollection<>(original, AtomicReference::getPlain);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int size() {
        // Returns a value to the caller
        return original.size();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isEmpty() {
        // Returns a value to the caller
        return original.isEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean contains(Object o) {
        // Loop: repeats a block
        for (var entry : original) {
            // Branch: checks a condition
            if (mapper.apply(entry).equals(o)) return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterator<R> iterator() {
        // Calls a method
        var iterator = original.iterator();
        // Returns a value to the caller
        return new Iterator<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean hasNext() {
                // Returns a value to the caller
                return iterator.hasNext();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public R next() {
                // Returns a value to the caller
                return mapper.apply(iterator.next());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object [] toArray() {
        // TODO
        // Throws an exception
        throw new UnsupportedOperationException("Unsupported array object");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> T [] toArray(T [] a) {
        // TODO
        // Throws an exception
        throw new UnsupportedOperationException("Unsupported array generic");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean containsAll(Collection<?> c) {
        // Branch: checks a condition
        if (c.size() > original.size()) return false;
        // Loop: repeats a block
        for (var entry : c) {
            // Branch: checks a condition
            if (!contains(entry)) return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean add(R t) {
        // Throws an exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean remove(Object o) {
        // Throws an exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addAll(Collection<? extends R> c) {
        // Throws an exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeAll(Collection<?> c) {
        // Throws an exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean retainAll(Collection<?> c) {
        // Throws an exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clear() {
        // Throws an exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // End of a block/expression
    }
// End of a block/expression
}
