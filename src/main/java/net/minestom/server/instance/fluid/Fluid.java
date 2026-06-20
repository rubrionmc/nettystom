// Package declaration for this file
package net.minestom.server.instance.fluid;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface Fluid extends StaticProtocolObject<Fluid>, Fluids permits FluidImpl {

    // Annotation for the following element
    @Override
    // Calls a method
    RegistryData.FluidEntry registry();

    // Start of a method/block
    static Collection<Fluid> values() {
        // Returns a value to the caller
        return FluidImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Fluid fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Fluid fromKey(Key key) {
        // Returns a value to the caller
        return FluidImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Fluid fromId(int id) {
        // Returns a value to the caller
        return FluidImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<Fluid> staticRegistry() {
        // Returns a value to the caller
        return FluidImpl.REGISTRY;
    // End of a block/expression
    }
// End of a block/expression
}
