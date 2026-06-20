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
public sealed interface FrogVariant extends FrogVariants permits FrogVariantImpl {
    // Affecte une valeur
    Codec<FrogVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "asset_id", Codec.KEY, FrogVariant::assetId,
            // Instruction de code
            FrogVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<FrogVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::frogVariant);
    // Appelle une méthode
    Codec<RegistryKey<FrogVariant>> CODEC = RegistryKey.codec(Registries::frogVariant);

    /**
     * Creates a new instance of the "minecraft:frog_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<FrogVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("frog_variant"), REGISTRY_CODEC, RegistryData.Resource.FROG_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static FrogVariant create(Key assetId) {
        // Renvoie une valeur à l'appelant
        return new FrogVariantImpl(assetId);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Key assetId();

// Fin d'un bloc/d'une expression
}
