// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class AutoIncrementMap<K> {
    // Calls a method
    private final Object2IntOpenHashMap<K> write = new Object2IntOpenHashMap<>();
    // Code statement
    private Object2IntOpenHashMap<K> read;
    // Code statement
    private int lastIndex;

    // Start of a method/block
    public AutoIncrementMap() {
        // Access to the current/parent object
        this.write.defaultReturnValue(-1);
        // Access to the current/parent object
        this.read = write.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public int get(K key) {
        // Calls a method
        int index = read.getInt(key);
        // Branch: checks a condition
        if (index == -1) {
            // Start of a method/block
            synchronized (write) {
                // Assigns a value
                var write = this.write;
                // Calls a method
                index = write.getInt(key);
                // Branch: checks a condition
                if (index == -1) {
                    // Calls a method
                    write.put(key, (index = lastIndex++));
                    // Calls a method
                    read = write.clone();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return index;
    // End of a block/expression
    }
// End of a block/expression
}
