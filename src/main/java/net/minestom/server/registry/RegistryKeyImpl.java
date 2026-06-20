// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record RegistryKeyImpl<T>(Key key) implements RegistryKey<T> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof RegistryKey<?> that)) return false;
        // Returns a value to the caller
        return Objects.equals(key, that.key());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hashCode(key);
    // End of a block/expression
    }

// End of a block/expression
}
