// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.instance.block.Block;
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
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface Material extends StaticProtocolObject<Material>, Materials, Translatable permits MaterialImpl {

    // Calls a method
    NetworkBuffer.Type<Material> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Material::fromId, Material::id);
    // Calls a method
    Codec<Material> CODEC = Codec.KEY.transform(Material::fromKey, Material::key);

    /**
     * Returns the raw registry data for the material.
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    RegistryData.MaterialEntry registry();

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

    // Start of a method/block
    default boolean isBlock() {
        // Returns a value to the caller
        return registry().block() != null;
    // End of a block/expression
    }

    // Start of a method/block
    default @UnknownNullability Block block() {
        // Returns a value to the caller
        return registry().block();
    // End of a block/expression
    }

    // Start of a method/block
    default DataComponentMap prototype() {
        // Returns a value to the caller
        return registry().prototype();
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isArmor() {
        // Returns a value to the caller
        return registry().isArmor();
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
    default int maxStackSize() {
        // Returns a value to the caller
        return prototype().get(DataComponents.MAX_STACK_SIZE, 64);
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<Material> values() {
        // Returns a value to the caller
        return MaterialImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Material fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Material fromKey(Key key) {
        // Returns a value to the caller
        return MaterialImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Material fromId(int id) {
        // Returns a value to the caller
        return MaterialImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<Material> staticRegistry() {
        // Returns a value to the caller
        return MaterialImpl.REGISTRY;
    // End of a block/expression
    }
// End of a block/expression
}
