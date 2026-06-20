// Package declaration for this file
package net.minestom.server.potion;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
record PotionTypeImpl(Key key, int id) implements PotionType {
    // Assigns a value
    static final Registry<PotionType> REGISTRY = RegistryData.createStaticRegistry(Key.key("potion_type"),
            // Calls a method
            (namespace, properties) -> new PotionTypeImpl(Key.key(namespace), properties.getInt("id")));

    // Start of a method/block
    static @UnknownNullability PotionType get(String key) {
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
