// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public sealed interface ZombieNautilusVariant extends ZombieNautilusVariants permits ZombieNautilusVariantImpl {
    // Assigns a value
    Codec<ZombieNautilusVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "model", Model.CODEC.optional(Model.NORMAL), ZombieNautilusVariant::model,
            // Code statement
            "asset_id", Codec.KEY, ZombieNautilusVariant::assetId,
            // Code statement
            ZombieNautilusVariantImpl::new);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<ZombieNautilusVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::zombieNautilusVariant);
    // Calls a method
    Codec<RegistryKey<ZombieNautilusVariant>> CODEC = RegistryKey.codec(Registries::zombieNautilusVariant);

    // Start of a method/block
    static ZombieNautilusVariant create(Model model, Key assetId) {
        // Returns a value to the caller
        return new ZombieNautilusVariantImpl(model, assetId);
    // End of a block/expression
    }

    /**
     * Creates a new instance of the "minecraft:zombie_nautilus_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<ZombieNautilusVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("zombie_nautilus_variant"), REGISTRY_CODEC, RegistryData.Resource.ZOMBIE_NAUTILUS_VARIANTS);
    // End of a block/expression
    }

    // Calls a method
    Model model();

    // Calls a method
    Key assetId();

    // Type declaration (class/interface/enum/record)
    enum Model {
        // Code statement
        NORMAL,
        // Code statement
        WARM;

        // Calls a method
        public static final Codec<Model> CODEC = Codec.Enum(Model.class);
    // End of a block/expression
    }
// End of a block/expression
}
