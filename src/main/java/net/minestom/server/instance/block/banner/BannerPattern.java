// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.banner;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.Translatable;
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
public sealed interface BannerPattern extends Holder.Direct<BannerPattern>, BannerPatterns,
        // Début d'une méthode/d'un bloc
        Translatable permits BannerPatternImpl {
    // Affecte une valeur
    NetworkBuffer.Type<BannerPattern> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.KEY, BannerPattern::assetId,
            // Instruction de code
            NetworkBuffer.STRING, BannerPattern::translationKey,
            // Instruction de code
            BannerPattern::create);
    // Affecte une valeur
    Codec<BannerPattern> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "asset_id", Codec.KEY, BannerPattern::assetId,
            // Instruction de code
            "translation_key", Codec.STRING, BannerPattern::translationKey,
            // Instruction de code
            BannerPattern::create);

    // Appelle une méthode
    NetworkBuffer.Type<Holder<BannerPattern>> HOLDER_NETWORK_TYPE = Holder.networkType(Registries::bannerPattern, BannerPattern.REGISTRY_NETWORK_TYPE);
    // Appelle une méthode
    Codec<Holder<BannerPattern>> HOLDER_CODEC = Holder.codec(Registries::bannerPattern, BannerPattern.REGISTRY_CODEC);

    // Instruction de code
    static BannerPattern create(
            // Instruction de code
            Key assetId,
            // Instruction de code
            String translationKey
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new BannerPatternImpl(assetId, translationKey);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for banner patterns, loading the vanilla banner patterns.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<BannerPattern> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("banner_pattern"), REGISTRY_CODEC, RegistryData.Resource.BANNER_PATTERNS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Key assetId();

    // Annotation pour l'élément suivant
    @Override
    // Appelle une méthode
    String translationKey();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private Key assetId;
        // Instruction de code
        private String translationKey;

        // Début d'une méthode/d'un bloc
        private Builder() {
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
        public Builder translationKey(String translationKey) {
            // Accès à l'objet courant/parent
            this.translationKey = translationKey;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public BannerPattern build() {
            // Renvoie une valeur à l'appelant
            return new BannerPatternImpl(assetId, translationKey);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
