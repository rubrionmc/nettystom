// Package declaration for this file
package net.minestom.server.instance.fluid;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
record FluidImpl(RegistryData.FluidEntry registry) implements Fluid {
    // Assigns a value
    static final Registry<Fluid> REGISTRY = RegistryData.createStaticRegistry(Key.key("fluid"),
            // Calls a method
            (namespace, properties) -> new FluidImpl(RegistryData.fluid(namespace, properties)));


    // Start of a method/block
    static @UnknownNullability Fluid get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key key() {
        // Returns a value to the caller
        return registry.key();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int id() {
        // Returns a value to the caller
        return registry.id();
    // End of a block/expression
    }
// End of a block/expression
}
