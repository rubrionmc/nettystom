// Package declaration for this file
package net.minestom.server;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface FeatureFlag extends StaticProtocolObject<FeatureFlag>, FeatureFlags permits FeatureFlagImpl {

    // Start of a method/block
    static Collection<FeatureFlag> values() {
        // Returns a value to the caller
        return FeatureFlagImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable FeatureFlag fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable FeatureFlag fromKey(Key key) {
        // Returns a value to the caller
        return FeatureFlagImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable FeatureFlag fromId(int id) {
        // Returns a value to the caller
        return FeatureFlagImpl.REGISTRY.get(id);
    // End of a block/expression
    }

// End of a block/expression
}
