// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
record MaterialImpl(RegistryData.MaterialEntry registry) implements Material {
    // Assigns a value
    static final Registry<Material> REGISTRY = RegistryData.createStaticRegistry(Key.key("item"),
            // Calls a method
            (namespace, properties) -> new MaterialImpl(RegistryData.material(namespace, properties)));

    // Start of a method/block
    static @UnknownNullability Material get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return name();
    // End of a block/expression
    }
// End of a block/expression
}
