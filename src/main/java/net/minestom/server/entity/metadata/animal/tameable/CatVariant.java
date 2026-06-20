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

// Déclaration de type (classe/interface/enum/record)
public sealed interface CatVariant extends CatVariants permits CatVariantImpl {
    // Affecte une valeur
    Codec<CatVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "asset_id", Codec.KEY, CatVariant::assetId,
            // Instruction de code
            CatVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<CatVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::catVariant);
    // Appelle une méthode
    Codec<RegistryKey<CatVariant>> NBT_TYPE = RegistryKey.codec(Registries::catVariant);

    // Début d'une méthode/d'un bloc
    static CatVariant create(Key assetId) {
        // Renvoie une valeur à l'appelant
        return new CatVariantImpl(assetId);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance of the "minecraft:cat_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<CatVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("cat_variant"), REGISTRY_CODEC, RegistryData.Resource.CAT_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Key assetId();

// Fin d'un bloc/d'une expression
}
