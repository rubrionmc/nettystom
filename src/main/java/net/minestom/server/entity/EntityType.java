// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
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
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public sealed interface EntityType extends StaticProtocolObject<EntityType>, EntityTypes, Translatable
        // Start of a method/block
        permits EntityTypeImpl {
    // Calls a method
    NetworkBuffer.Type<EntityType> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(EntityType::fromId, EntityType::id);
    // Calls a method
    Codec<EntityType> CODEC = Codec.KEY.transform(EntityType::fromKey, EntityType::key);

    /**
     * Returns the entity registry.
     *
     * @return the entity registry
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    RegistryData.EntityEntry registry();

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
    default double width() {
        // Returns a value to the caller
        return registry().width();
    // End of a block/expression
    }

    // Start of a method/block
    default double height() {
        // Returns a value to the caller
        return registry().height();
    // End of a block/expression
    }

    // Start of a method/block
    default Map<Attribute, Double> defaultAttributes() {
        // Returns a value to the caller
        return registry().defaultAttributes();
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
    static Collection<EntityType> values() {
        // Returns a value to the caller
        return EntityTypeImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable EntityType fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable EntityType fromKey(Key key) {
        // Returns a value to the caller
        return EntityTypeImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable EntityType fromId(int id) {
        // Returns a value to the caller
        return EntityTypeImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<EntityType> staticRegistry() {
        // Returns a value to the caller
        return EntityTypeImpl.REGISTRY;
    // End of a block/expression
    }
// End of a block/expression
}
