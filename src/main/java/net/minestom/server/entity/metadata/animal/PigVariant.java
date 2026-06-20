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
public sealed interface PigVariant extends PigVariants permits PigVariantImpl {
    // Affecte une valeur
    Codec<PigVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "model", Model.CODEC.optional(Model.NORMAL), PigVariant::model,
            // Instruction de code
            "asset_id", Codec.KEY, PigVariant::assetId,
            // Instruction de code
            PigVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<PigVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::pigVariant);
    // Appelle une méthode
    Codec<RegistryKey<PigVariant>> CODEC = RegistryKey.codec(Registries::pigVariant);

    /**
     * Creates a new instance of the "minecraft:pig_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<PigVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("pig_variant"), REGISTRY_CODEC, RegistryData.Resource.PIG_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static PigVariant create(Model model, Key assetId) {
        // Renvoie une valeur à l'appelant
        return new PigVariantImpl(model, assetId);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Model model();

    // Appelle une méthode
    Key assetId();

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
