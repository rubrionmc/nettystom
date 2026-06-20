// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.Range;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
sealed interface StaticIntMap<T extends @UnknownNullability Object> permits StaticIntMap.Array {

    // Calls a method
    T get(@Range(from = 0, to = Integer.MAX_VALUE) int key);

    // Calls a method
    void forValues(Consumer<T> consumer);

    // Calls a method
    StaticIntMap<T> copy();

    // Methods potentially causing re-hashing

    // Calls a method
    void put(@Range(from = 0, to = Integer.MAX_VALUE) int key, T value);

    // Calls a method
    void remove(@Range(from = 0, to = Integer.MAX_VALUE) int key);

    // Calls a method
    void updateContent(StaticIntMap<T> content);

    // Type declaration (class/interface/enum/record)
    final class Array<T extends @UnknownNullability Object> implements StaticIntMap<T> {
        // Assigns a value
        private static final Object[] EMPTY_ARRAY = new Object[0];

        // Code statement
        private T[] array;

        // Start of a method/block
        public Array(T[] array) {
            // Access to the current/parent object
            this.array = array;
        // End of a block/expression
        }

        // Start of a method/block
        public Array() {
            //noinspection unchecked
            // Access to the current/parent object
            this.array = (T[]) EMPTY_ARRAY;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable T get(int key) {
            // Assigns a value
            final T[] array = this.array;
            // Returns a value to the caller
            return key < array.length ? array[key] : null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void forValues(Consumer<T> consumer) {
            // Assigns a value
            final T[] array = this.array;
            // Loop: repeats a block
            for (T value : array) {
                // Branch: checks a condition
                if (value != null) consumer.accept(value);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StaticIntMap<T> copy() {
            // Returns a value to the caller
            return new Array<>(array.clone());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void put(int key, T value) {
            // Assigns a value
            T[] array = this.array;
            // Branch: checks a condition
            if (key >= array.length) {
                // Calls a method
                array = updateArray(Arrays.copyOf(array, key * 2 + 1));
            // End of a block/expression
            }
            // Assigns a value
            array[key] = value;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void updateContent(StaticIntMap<T> content) {
            // Branch: checks a condition
            if (content instanceof StaticIntMap.Array<T> arrayMap) {
                // Calls a method
                updateArray(arrayMap.array.clone());
            // Alternative branch of the condition
            } else {
                // Throws an exception
                throw new IllegalArgumentException("Invalid content type: " + content.getClass());
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void remove(int key) {
            // Assigns a value
            T[] array = this.array;
            // Branch: checks a condition
            if (key < array.length) array[key] = null;
        // End of a block/expression
        }

        // Start of a method/block
        T[] updateArray(T[] result) {
            // Access to the current/parent object
            this.array = result;
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
