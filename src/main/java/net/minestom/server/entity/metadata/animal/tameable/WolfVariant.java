// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

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
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public sealed interface WolfVariant extends WolfVariants permits WolfVariantImpl {
    // Affecte une valeur
    Codec<WolfVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "assets", Assets.CODEC, WolfVariant::assets,
            // Instruction de code
            "baby_assets", Assets.CODEC, WolfVariant::babyAssets,
            // Instruction de code
            WolfVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<WolfVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::wolfVariant);
    // Appelle une méthode
    Codec<RegistryKey<WolfVariant>> CODEC = RegistryKey.codec(Registries::wolfVariant);

    // Début d'une méthode/d'un bloc
    static WolfVariant create(Assets assets, Assets babyAssets) {
        // Renvoie une valeur à l'appelant
        return new WolfVariantImpl(assets, babyAssets);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance of the "minecraft:wolf_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<WolfVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("wolf_variant"), REGISTRY_CODEC, RegistryData.Resource.WOLF_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Assets assets();

    // Appelle une méthode
    Assets babyAssets();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Assets permits WolfVariantImpl.AssetsImpl {
        // Affecte une valeur
        Codec<Assets> CODEC = StructCodec.struct(
                // Instruction de code
                "wild", Codec.KEY, Assets::wild,
                // Instruction de code
                "tame", Codec.KEY, Assets::tame,
                // Instruction de code
                "angry", Codec.KEY, Assets::angry,
                // Instruction de code
                Assets::create);

        // Début d'une méthode/d'un bloc
        static Builder builder() {
            // Renvoie une valeur à l'appelant
            return new Builder();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static Assets create(Key wild, Key tame, Key angry) {
            // Renvoie une valeur à l'appelant
            return new WolfVariantImpl.AssetsImpl(wild, tame, angry);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Key wild();

        // Appelle une méthode
        Key tame();

        // Appelle une méthode
        Key angry();

        // Déclaration de type (classe/interface/enum/record)
        final class Builder {
            // Instruction de code
            private @UnknownNullability Key wild;
            // Instruction de code
            private @UnknownNullability Key tame;
            // Instruction de code
            private @UnknownNullability Key angry;

            // Début d'une méthode/d'un bloc
            private Builder() {
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public Builder wild(Key wild) {
                // Accès à l'objet courant/parent
                this.wild = Objects.requireNonNull(wild, "wild");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public Builder tame(Key tame) {
                // Accès à l'objet courant/parent
                this.tame = Objects.requireNonNull(tame, "tame");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public Builder angry(Key angry) {
                // Accès à l'objet courant/parent
                this.angry = Objects.requireNonNull(angry, "angry");
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public Assets build() {
                // Renvoie une valeur à l'appelant
                return new WolfVariantImpl.AssetsImpl(wild, tame, angry);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private @UnknownNullability Assets assets;
        // Instruction de code
        private @UnknownNullability Assets babyAssets;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder assets(Assets assets) {
            // Accès à l'objet courant/parent
            this.assets = Objects.requireNonNull(assets, "assets");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder babyAssets(Assets babyAssets) {
            // Accès à l'objet courant/parent
            this.babyAssets = Objects.requireNonNull(babyAssets, "babyAssets");
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public WolfVariant build() {
            // Renvoie une valeur à l'appelant
            return new WolfVariantImpl(assets, babyAssets);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
