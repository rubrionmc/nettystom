// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
final class ObjectArrayImpl {
    // Type declaration (class/interface/enum/record)
    static final class SingleThread<T> implements ObjectArray<T> {
        // Code statement
        private T[] array;
        // Assigns a value
        private int max = -1;

        // Start of a method/block
        SingleThread(int size) {
            //noinspection unchecked
            // Access to the current/parent object
            this.array = (T[]) new Object[size];
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @UnknownNullability T get(int index) {
            // Assigns a value
            final T[] array = this.array;
            // Returns a value to the caller
            return index < array.length ? array[index] : null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void set(int index, @Nullable T object) {
            // Branch: checks a condition
            if (object == null) {
                // Calls a method
                remove(index);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Assigns a value
            T[] array = this.array;
            // Branch: checks a condition
            if (index >= array.length) {
                // Assigns a value
                final int newLength = index * 2 + 1;
                // Access to the current/parent object
                this.array = array = Arrays.copyOf(array, newLength);
            // End of a block/expression
            }
            // Assigns a value
            array[index] = object;
            // Access to the current/parent object
            this.max = Math.max(max, index);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void remove(int index) {
            // Assigns a value
            final T[] array = this.array;
            // Branch: checks a condition
            if (index >= array.length) return; // Will be null anyway
            // Assigns a value
            array[index] = null;
            // Now we need to backtrack the max index,
            // For example [0, 1, 2, null, 4] removing 4 requires us to backtrack past the null
            // Assigns a value
            final int max = this.max;
            // Branch: checks a condition
            if (max == index) {
                // Assigns a value
                int lastNotNull = max - 1;
                // Loop: repeats a block
                while (lastNotNull >= 0 && array[lastNotNull] == null) {
                    // Code statement
                    lastNotNull--;
                // End of a block/expression
                }
                // Access to the current/parent object
                this.max = lastNotNull;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void trim() {
            // Access to the current/parent object
            this.array = Arrays.copyOf(array, max + 1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @UnknownNullability T [] arrayCopy(Class<T> type) {
            //noinspection unchecked,rawtypes
            // Returns a value to the caller
            return (T[]) Arrays.<T, T>copyOf(array, max + 1, (Class) type.arrayType());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<T> toList() {
            // Trim the array to the maximum size, it internally will be copied regardless.
            // Calls a method
            final T[] array = Arrays.copyOf(this.array, max + 1);
            // Returns a value to the caller
            return List.of(array);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class Concurrent<T> implements ObjectArray<T> {
        // Code statement
        private volatile T[] array;
        // Assigns a value
        private volatile int max = -1;

        // Start of a method/block
        Concurrent(int size) {
            //noinspection unchecked
            // Access to the current/parent object
            this.array = (T[]) new Object[size];
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @UnknownNullability T get(int index) {
            // Assigns a value
            final T[] array = this.array;
            // Returns a value to the caller
            return index < array.length ? array[index] : null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public synchronized void set(int index, @Nullable T object) {
            // Branch: checks a condition
            if (object == null) {
                // Calls a method
                remove(index);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Assigns a value
            T[] array = this.array;
            // Branch: checks a condition
            if (index >= array.length) {
                // Assigns a value
                final int newLength = index * 2 + 1;
                // Access to the current/parent object
                this.array = array = Arrays.copyOf(array, newLength);
            // End of a block/expression
            }
            // Assigns a value
            array[index] = object;
            // Access to the current/parent object
            this.max = Math.max(max, index);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public synchronized void remove(int index) {
            // Assigns a value
            final T[] array = this.array;
            // Branch: checks a condition
            if (index >= array.length) return; // Will be null anyway
            // Assigns a value
            array[index] = null;
            // Now we need to backtrack the max index,
            // For example [0, 1, 2, null, 4] removing 4 requires us to backtrack past the null
            // Assigns a value
            final int max = this.max;
            // Branch: checks a condition
            if (max == index) {
                // Assigns a value
                int lastNotNull = max - 1;
                // Loop: repeats a block
                while (lastNotNull >= 0 && array[lastNotNull] == null) {
                    // Code statement
                    lastNotNull--;
                // End of a block/expression
                }
                // Access to the current/parent object
                this.max = lastNotNull;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public synchronized void trim() {
            // Access to the current/parent object
            this.array = Arrays.copyOf(array, max + 1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @UnknownNullability T [] arrayCopy(Class<T> type) {
            //noinspection unchecked,rawtypes
            // Returns a value to the caller
            return (T[]) Arrays.<T, T>copyOf(array, max + 1, (Class) type.arrayType());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<T> toList() {
            // Trim the array to the maximum size, it internally will be copied regardless.
            // Calls a method
            final T[] array = Arrays.copyOf(this.array, this.max + 1);
            // Returns a value to the caller
            return List.of(array);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
