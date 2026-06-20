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
public sealed interface ZombieNautilusVariant extends ZombieNautilusVariants permits ZombieNautilusVariantImpl {
    // Affecte une valeur
    Codec<ZombieNautilusVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "model", Model.CODEC.optional(Model.NORMAL), ZombieNautilusVariant::model,
            // Instruction de code
            "asset_id", Codec.KEY, ZombieNautilusVariant::assetId,
            // Instruction de code
            ZombieNautilusVariantImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<ZombieNautilusVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::zombieNautilusVariant);
    // Appelle une méthode
    Codec<RegistryKey<ZombieNautilusVariant>> CODEC = RegistryKey.codec(Registries::zombieNautilusVariant);

    // Début d'une méthode/d'un bloc
    static ZombieNautilusVariant create(Model model, Key assetId) {
        // Renvoie une valeur à l'appelant
        return new ZombieNautilusVariantImpl(model, assetId);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance of the "minecraft:zombie_nautilus_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<ZombieNautilusVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("zombie_nautilus_variant"), REGISTRY_CODEC, RegistryData.Resource.ZOMBIE_NAUTILUS_VARIANTS);
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
        WARM;

        // Appelle une méthode
        public static final Codec<Model> CODEC = Codec.Enum(Model.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
