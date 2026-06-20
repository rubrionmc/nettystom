// Package declaration for this file
package net.minestom.server.entity.damage;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public sealed interface DamageType extends DamageTypes permits DamageTypeImpl {
    // Assigns a value
    Codec<DamageType> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "message_id", Codec.STRING, DamageType::messageId,
            // Code statement
            "scaling", Codec.STRING, DamageType::scaling,
            // Code statement
            "exhaustion", Codec.FLOAT, DamageType::exhaustion,
            // Code statement
            "effects", Codec.STRING.optional("hurt"), DamageType::effects,
            // Code statement
            "death_message_type", Codec.STRING.optional("default"), DamageType::deathMessageType,
            // Code statement
            DamageType::create);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<DamageType>> NETWORK_TYPE = RegistryKey.networkType(Registries::damageType);
    // Calls a method
    Codec<RegistryKey<DamageType>> CODEC = RegistryKey.codec(Registries::damageType);

    // Code statement
    static DamageType create(
            // Code statement
            String messageId,
            // Code statement
            String scaling,
            // Code statement
            float exhaustion,
            // Annotation for the following element
            @Nullable String effects,
            // Annotation for the following element
            @Nullable String deathMessageType
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new DamageTypeImpl(messageId, scaling, exhaustion, effects, deathMessageType);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for damage types, loading the vanilla damage types.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<DamageType> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("damage_type"), REGISTRY_CODEC, RegistryData.Resource.DAMAGE_TYPES);
    // End of a block/expression
    }

    // Calls a method
    String messageId();

    // Calls a method
    String scaling();

    // Calls a method
    float exhaustion();

    // Annotation for the following element
    @Nullable String effects();

    // Annotation for the following element
    @Nullable String deathMessageType();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private String messageId;
        // Code statement
        private String scaling;
        // Assigns a value
        private float exhaustion = 0f;
        // Code statement
        private @Nullable String effects;
        // Code statement
        private @Nullable String deathMessageType;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Start of a method/block
        public Builder messageId(String messageId) {
            // Access to the current/parent object
            this.messageId = messageId;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder scaling(String scaling) {
            // Access to the current/parent object
            this.scaling = scaling;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder exhaustion(float exhaustion) {
            // Access to the current/parent object
            this.exhaustion = exhaustion;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder effects(@Nullable String effects) {
            // Access to the current/parent object
            this.effects = effects;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder deathMessageType(@Nullable String deathMessageType) {
            // Access to the current/parent object
            this.deathMessageType = deathMessageType;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public DamageType build() {
            // Returns a value to the caller
            return new DamageTypeImpl(messageId, scaling, exhaustion, effects, deathMessageType);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}