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
public sealed interface FrogVariant extends FrogVariants permits FrogVariantImpl {
    // Assigns a value
    Codec<FrogVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "asset_id", Codec.KEY, FrogVariant::assetId,
            // Code statement
            FrogVariantImpl::new);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<FrogVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::frogVariant);
    // Calls a method
    Codec<RegistryKey<FrogVariant>> CODEC = RegistryKey.codec(Registries::frogVariant);

    /**
     * Creates a new instance of the "minecraft:frog_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<FrogVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("frog_variant"), REGISTRY_CODEC, RegistryData.Resource.FROG_VARIANTS);
    // End of a block/expression
    }

    // Start of a method/block
    static FrogVariant create(Key assetId) {
        // Returns a value to the caller
        return new FrogVariantImpl(assetId);
    // End of a block/expression
    }

    // Calls a method
    Key assetId();

// End of a block/expression
}
