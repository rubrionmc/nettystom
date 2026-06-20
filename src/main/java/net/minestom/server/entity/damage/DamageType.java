// Déclaration du paquet de ce fichier
package net.minestom.server.entity.damage;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public sealed interface DamageType extends DamageTypes permits DamageTypeImpl {
    // Affecte une valeur
    Codec<DamageType> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "message_id", Codec.STRING, DamageType::messageId,
            // Instruction de code
            "scaling", Codec.STRING, DamageType::scaling,
            // Instruction de code
            "exhaustion", Codec.FLOAT, DamageType::exhaustion,
            // Instruction de code
            "effects", Codec.STRING.optional("hurt"), DamageType::effects,
            // Instruction de code
            "death_message_type", Codec.STRING.optional("default"), DamageType::deathMessageType,
            // Instruction de code
            DamageType::create);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<DamageType>> NETWORK_TYPE = RegistryKey.networkType(Registries::damageType);
    // Appelle une méthode
    Codec<RegistryKey<DamageType>> CODEC = RegistryKey.codec(Registries::damageType);

    // Instruction de code
    static DamageType create(
            // Instruction de code
            String messageId,
            // Instruction de code
            String scaling,
            // Instruction de code
            float exhaustion,
            // Annotation pour l'élément suivant
            @Nullable String effects,
            // Annotation pour l'élément suivant
            @Nullable String deathMessageType
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new DamageTypeImpl(messageId, scaling, exhaustion, effects, deathMessageType);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for damage types, loading the vanilla damage types.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<DamageType> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("damage_type"), REGISTRY_CODEC, RegistryData.Resource.DAMAGE_TYPES);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    String messageId();

    // Appelle une méthode
    String scaling();

    // Appelle une méthode
    float exhaustion();

    // Annotation pour l'élément suivant
    @Nullable String effects();

    // Annotation pour l'élément suivant
    @Nullable String deathMessageType();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private String messageId;
        // Instruction de code
        private String scaling;
        // Affecte une valeur
        private float exhaustion = 0f;
        // Instruction de code
        private @Nullable String effects;
        // Instruction de code
        private @Nullable String deathMessageType;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder messageId(String messageId) {
            // Accès à l'objet courant/parent
            this.messageId = messageId;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder scaling(String scaling) {
            // Accès à l'objet courant/parent
            this.scaling = scaling;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder exhaustion(float exhaustion) {
            // Accès à l'objet courant/parent
            this.exhaustion = exhaustion;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder effects(@Nullable String effects) {
            // Accès à l'objet courant/parent
            this.effects = effects;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder deathMessageType(@Nullable String deathMessageType) {
            // Accès à l'objet courant/parent
            this.deathMessageType = deathMessageType;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public DamageType build() {
            // Renvoie une valeur à l'appelant
            return new DamageTypeImpl(messageId, scaling, exhaustion, effects, deathMessageType);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}