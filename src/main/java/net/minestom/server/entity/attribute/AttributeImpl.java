// Package declaration for this file
package net.minestom.server.entity.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
record AttributeImpl(RegistryData.AttributeEntry registry) implements Attribute {
    // Assigns a value
    static final Registry<Attribute> REGISTRY = RegistryData.createStaticRegistry(Key.key("attribute"),
            // Calls a method
            (namespace, properties) -> new AttributeImpl(RegistryData.attribute(namespace, properties)));

    // Start of a method/block
    static @UnknownNullability Attribute get(String namespace) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(namespace));
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
