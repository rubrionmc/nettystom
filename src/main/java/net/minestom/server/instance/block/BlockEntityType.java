// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface BlockEntityType extends StaticProtocolObject<BlockEntityType>, BlockEntityTypes permits BlockEntityTypeImpl {
    // Calls a method
    NetworkBuffer.Type<BlockEntityType> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(BlockEntityType::fromId, BlockEntityType::id);
    // Calls a method
    Codec<BlockEntityType> CODEC = Codec.KEY.transform(BlockEntityType::fromKey, BlockEntityType::key);

    // Start of a method/block
    static Collection<BlockEntityType> values() {
        // Returns a value to the caller
        return BlockEntityTypeImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable BlockEntityType fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable BlockEntityType fromKey(Key key) {
        // Returns a value to the caller
        return BlockEntityTypeImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable BlockEntityType fromId(int id) {
        // Returns a value to the caller
        return BlockEntityTypeImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<BlockEntityType> staticRegistry() {
        // Returns a value to the caller
        return BlockEntityTypeImpl.REGISTRY;
    // End of a block/expression
    }

// End of a block/expression
}
