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
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public sealed interface WolfVariant extends WolfVariants permits WolfVariantImpl {
    // Affecte une valeur
    Codec<WolfVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "assets", Assets.CODEC, WolfVariant::assets,
            // Instruction de code
            WolfVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<WolfVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::wolfVariant);
    // Appelle une méthode
    Codec<RegistryKey<WolfVariant>> CODEC = RegistryKey.codec(Registries::wolfVariant);

    // Début d'une méthode/d'un bloc
    static WolfVariant create(Assets assets) {
        // Renvoie une valeur à l'appelant
        return new WolfVariantImpl(assets);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static WolfVariant create(Key wild, Key tame, Key angry) {
        // Renvoie une valeur à l'appelant
        return new WolfVariantImpl(new Assets(wild, tame, angry));
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

    // Déclaration de type (classe/interface/enum/record)
    record Assets(Key wild, Key tame, Key angry) {
        // Affecte une valeur
        public static final Codec<Assets> CODEC = StructCodec.struct(
                // Instruction de code
                "wild", Codec.KEY, Assets::wild,
                // Instruction de code
                "tame", Codec.KEY, Assets::tame,
                // Instruction de code
                "angry", Codec.KEY, Assets::angry,
                // Instruction de code
                Assets::new);

        // Début d'une méthode/d'un bloc
        public Assets {
            // Builder may violate nullability constraints
            // Appelle une méthode
            Check.notNull(wild, "missing wild asset");
            // Appelle une méthode
            Check.notNull(tame, "missing tame asset");
            // Appelle une méthode
            Check.notNull(angry, "missing angry asset");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private Assets assets;
        // Instruction de code
        private Key wildAsset;
        // Instruction de code
        private Key tameAsset;
        // Instruction de code
        private Key angryAsset;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder wildAsset(Key wildAsset) {
            // Accès à l'objet courant/parent
            this.wildAsset = wildAsset;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder tameAsset(Key tameAsset) {
            // Accès à l'objet courant/parent
            this.tameAsset = tameAsset;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder angryAsset(Key angryAsset) {
            // Accès à l'objet courant/parent
            this.angryAsset = angryAsset;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder assets(Assets assets) {
            // Accès à l'objet courant/parent
            this.assets = assets;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public WolfVariant build() {
            // Appelle une méthode
            final Assets assets = Objects.requireNonNullElseGet(this.assets, () -> new Assets(wildAsset, tameAsset, angryAsset));
            // Renvoie une valeur à l'appelant
            return new WolfVariantImpl(assets);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
