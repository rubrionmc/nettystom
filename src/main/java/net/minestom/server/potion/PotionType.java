// Package declaration for this file
package net.minestom.server.potion;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface PotionType extends StaticProtocolObject<PotionType>, PotionTypes permits PotionTypeImpl {

    // Calls a method
    NetworkBuffer.Type<PotionType> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(PotionType::fromId, PotionType::id);
    // Calls a method
    Codec<PotionType> CODEC = Codec.KEY.transform(PotionType::fromKey, PotionType::key);

    // Start of a method/block
    static Collection<PotionType> values() {
        // Returns a value to the caller
        return PotionTypeImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable PotionType fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable PotionType fromKey(Key key) {
        // Returns a value to the caller
        return PotionTypeImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable PotionType fromId(int id) {
        // Returns a value to the caller
        return PotionTypeImpl.REGISTRY.get(id);
    // End of a block/expression
    }
// End of a block/expression
}
