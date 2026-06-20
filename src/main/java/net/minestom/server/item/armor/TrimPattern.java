// Déclaration du paquet de ce fichier
package net.minestom.server.item.armor;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Déclaration de type (classe/interface/enum/record)
public sealed interface TrimPattern extends Holder.Direct<TrimPattern>, TrimPatterns permits TrimPatternImpl {
    // Affecte une valeur
    NetworkBuffer.Type<TrimPattern> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.KEY, TrimPattern::assetId,
            // Instruction de code
            NetworkBuffer.COMPONENT, TrimPattern::description,
            // Instruction de code
            NetworkBuffer.BOOLEAN, TrimPattern::isDecal,
            // Instruction de code
            TrimPattern::create);
    // Affecte une valeur
    Codec<TrimPattern> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "asset_id", Codec.KEY, TrimPattern::assetId,
            // Instruction de code
            "description", Codec.COMPONENT, TrimPattern::description,
            // Instruction de code
            "decal", Codec.BOOLEAN, TrimPattern::isDecal,
            // Instruction de code
            TrimPattern::create);

    // Appelle une méthode
    NetworkBuffer.Type<Holder<TrimPattern>> NETWORK_TYPE = Holder.networkType(Registries::trimPattern, REGISTRY_NETWORK_TYPE);
    // Appelle une méthode
    Codec<Holder<TrimPattern>> CODEC = Holder.codec(Registries::trimPattern, REGISTRY_CODEC);

    // Instruction de code
    static TrimPattern create(
            // Instruction de code
            Key assetId,
            // Instruction de code
            Component description,
            // Instruction de code
            boolean decal
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new TrimPatternImpl(assetId, description, decal);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for trim materials, loading the vanilla trim materials.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<TrimPattern> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("trim_pattern"), REGISTRY_CODEC, RegistryData.Resource.TRIM_PATTERNS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Key assetId();

    // Appelle une méthode
    Component description();

    // Appelle une méthode
    boolean isDecal();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private Key assetId;
        // Instruction de code
        private Component description;
        // Instruction de code
        private boolean decal;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder assetId(String assetId) {
            // Renvoie une valeur à l'appelant
            return assetId(Key.key(assetId));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder assetId(Key assetId) {
            // Accès à l'objet courant/parent
            this.assetId = assetId;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder description(Component description) {
            // Accès à l'objet courant/parent
            this.description = description;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder decal(boolean decal) {
            // Accès à l'objet courant/parent
            this.decal = decal;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public TrimPattern build() {
            // Renvoie une valeur à l'appelant
            return new TrimPatternImpl(assetId, description, decal);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
