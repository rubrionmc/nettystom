// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
public record BlockEntityTypeImpl(Key key, int id) implements BlockEntityType {
    // Assigns a value
    static final Registry<BlockEntityType> REGISTRY = RegistryData.createStaticRegistry(
            // Calls a method
            Key.key("block_entity_types"), BlockEntityTypeImpl::new);

    // Start of a method/block
    private BlockEntityTypeImpl(String namespace, RegistryData.Properties properties) {
        // Calls a method
        this(Key.key(namespace), properties.getInt("id"));
    // End of a block/expression
    }

    // Start of a method/block
    public static @UnknownNullability BlockEntityType get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }
// End of a block/expression
}
