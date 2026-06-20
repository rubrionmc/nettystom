// Package declaration for this file
package net.minestom.server;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
record FeatureFlagImpl(RegistryData.FeatureFlagEntry registry) implements FeatureFlag {
    // Assigns a value
    static final Registry<FeatureFlag> REGISTRY = RegistryData.createStaticRegistry(Key.key("feature_flag"),
            // Calls a method
            (namespace, properties) -> new FeatureFlagImpl(RegistryData.featureFlag(namespace, properties)));

    // Start of a method/block
    static @UnknownNullability FeatureFlag get(String key) {
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
