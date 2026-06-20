// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
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
public sealed interface VillagerProfession extends StaticProtocolObject<VillagerProfession>, VillagerProfessions permits VillagerProfessionImpl {

    // Calls a method
    NetworkBuffer.Type<VillagerProfession> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(VillagerProfession::fromId, VillagerProfession::id);
    // Calls a method
    Codec<VillagerProfession> NBT_TYPE = Codec.STRING.transform(VillagerProfession::fromKey, VillagerProfession::name);

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
    @Contract(pure = true)
    // Calls a method
    RegistryData.VillagerProfessionEntry registry();

    // Start of a method/block
    static Collection<VillagerProfession> values() {
        // Returns a value to the caller
        return VillagerProfessionImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable VillagerProfession fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable VillagerProfession fromKey(Key key) {
        // Returns a value to the caller
        return VillagerProfessionImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable VillagerProfession fromId(int id) {
        // Returns a value to the caller
        return VillagerProfessionImpl.REGISTRY.get(id);
    // End of a block/expression
    }

// End of a block/expression
}
