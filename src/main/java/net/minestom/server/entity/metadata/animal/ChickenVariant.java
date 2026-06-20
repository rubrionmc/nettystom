// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

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

// Déclaration de type (classe/interface/enum/record)
public sealed interface ChickenVariant extends ChickenVariants permits ChickenVariantImpl {
    // Affecte une valeur
    Codec<ChickenVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "model", Model.CODEC.optional(Model.NORMAL), ChickenVariant::model,
            // Instruction de code
            "asset_id", Codec.KEY, ChickenVariant::assetId,
            // Instruction de code
            "baby_asset_id", Codec.KEY, ChickenVariant::babyAssetId,
            // Instruction de code
            ChickenVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<ChickenVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::chickenVariant);
    // Appelle une méthode
    Codec<RegistryKey<ChickenVariant>> CODEC = RegistryKey.codec(Registries::chickenVariant);

    // Début d'une méthode/d'un bloc
    static ChickenVariant create(Model model, Key assetId, Key babyAssetId) {
        // Renvoie une valeur à l'appelant
        return new ChickenVariantImpl(model, assetId, babyAssetId);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance of the "minecraft:chicken_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<ChickenVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("chicken_variant"), REGISTRY_CODEC, RegistryData.Resource.CHICKEN_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Model model();

    // Appelle une méthode
    Key assetId();

    // Appelle une méthode
    Key babyAssetId();

    // Déclaration de type (classe/interface/enum/record)
    enum Model {
        // Instruction de code
        NORMAL,
        // Instruction de code
        COLD;

        // Appelle une méthode
        public static final Codec<Model> CODEC = Codec.Enum(Model.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
