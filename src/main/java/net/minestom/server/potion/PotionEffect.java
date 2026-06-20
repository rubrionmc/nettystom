// Package declaration for this file
package net.minestom.server.potion;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface PotionEffect extends StaticProtocolObject<PotionEffect>, PotionEffects,
        // Start of a method/block
        Translatable permits PotionEffectImpl {
    // Calls a method
    NetworkBuffer.Type<PotionEffect> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(PotionEffect::fromId, PotionEffect::id);
    // Calls a method
    Codec<PotionEffect> CODEC = Codec.KEY.transform(PotionEffect::fromKey, PotionEffect::key);

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    RegistryData.PotionEffectEntry registry();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Key key() {
        // Returns a value to the caller
        return registry().key();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default int id() {
        // Returns a value to the caller
        return registry().id();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default String translationKey() {
        // Returns a value to the caller
        return registry().translationKey();
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<PotionEffect> values() {
        // Returns a value to the caller
        return PotionEffectImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable PotionEffect fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable PotionEffect fromKey(Key key) {
        // Returns a value to the caller
        return PotionEffectImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable PotionEffect fromId(int id) {
        // Returns a value to the caller
        return PotionEffectImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<PotionEffect> staticRegistry() {
        // Returns a value to the caller
        return PotionEffectImpl.REGISTRY;
    // End of a block/expression
    }
// End of a block/expression
}
