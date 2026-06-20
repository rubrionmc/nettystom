// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
final class MetadataDefImpl {
    // Calls a method
    static final Map<String, Integer> MAX_INDEX = new HashMap<>();

    // Start of a method/block
    static <T extends @UnknownNullability Object> MetadataDef.Entry.Index<T> index(int index, Function<T, Metadata.Entry<T>> function, T defaultValue) {
        // Calls a method
        final String caller = caller();
        // Calls a method
        storeMaxIndex(caller, index);
        // Calls a method
        final int superIndex = findSuperIndex(caller);
        // Returns a value to the caller
        return new MetadataDef.Entry.Index<>(superIndex + index, function, defaultValue);
    // End of a block/expression
    }

    // Start of a method/block
    static MetadataDef.Entry.BitMask bitMask(int index, byte bitMask, boolean defaultValue) {
        // Calls a method
        final String caller = caller();
        // Calls a method
        storeMaxIndex(caller, index);
        // Calls a method
        final int superIndex = findSuperIndex(caller);
        // Returns a value to the caller
        return new MetadataDef.Entry.BitMask(superIndex + index, bitMask, defaultValue);
    // End of a block/expression
    }

    // Start of a method/block
    static MetadataDef.Entry.ByteMask byteMask(int index, byte byteMask, int offset, byte defaultValue) {
        // Calls a method
        final String caller = caller();
        // Calls a method
        storeMaxIndex(caller, index);
        // Calls a method
        final int superIndex = findSuperIndex(caller);
        // Returns a value to the caller
        return new MetadataDef.Entry.ByteMask(superIndex + index, byteMask, offset, defaultValue);
    // End of a block/expression
    }

    // Start of a method/block
    static <T extends MetadataDef> int count(Class<T> clazz) {
        // Calls a method
        final String name = clazz.getName();
        // Exception handling
        try {
            // Force load the class to ensure entries are registered
            // Calls a method
            Class.forName(name);
        // Start of a method/block
        } catch (ClassNotFoundException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
        // Calls a method
        final int classIndex = MAX_INDEX.get(name);
        // Calls a method
        final int superIndex = findSuperIndex(name);
        // Returns a value to the caller
        return classIndex + superIndex + 1;
    // End of a block/expression
    }

    // Start of a method/block
    private static String caller() {
        // Returns a value to the caller
        return Thread.currentThread().getStackTrace()[3].getClassName();
    // End of a block/expression
    }

    // Start of a method/block
    static void storeMaxIndex(String caller, int index) {
        // Calls a method
        final int currentMax = MAX_INDEX.getOrDefault(caller, 0);
        // Calls a method
        MAX_INDEX.put(caller, Math.max(currentMax, index));
    // End of a block/expression
    }

    // Start of a method/block
    static int findSuperIndex(String caller) {
        // Exception handling
        try {
            // Calls a method
            final Class<?> subclass = Class.forName(caller);
            // Calls a method
            Class<?> superclass = subclass.getSuperclass();
            // Branch: checks a condition
            if (superclass == Object.class) return 0;

            // Assigns a value
            int index = 0;
            // Loop: repeats a block
            do {
                // Calls a method
                index += MAX_INDEX.get(superclass.getName()) + 1;
                // Calls a method
                superclass = superclass.getSuperclass();
            // Calls a method
            } while (superclass != Object.class);

            // Returns a value to the caller
            return index;
        // Start of a method/block
        } catch (ClassNotFoundException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
