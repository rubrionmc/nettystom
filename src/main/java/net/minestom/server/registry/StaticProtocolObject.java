// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public interface StaticProtocolObject<T> extends RegistryKey<T> {

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default String name() {
        // Returns a value to the caller
        return key().asString();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Key key();

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    int id();

    // Start of a method/block
    default @Nullable Object registry() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }
// End of a block/expression
}
