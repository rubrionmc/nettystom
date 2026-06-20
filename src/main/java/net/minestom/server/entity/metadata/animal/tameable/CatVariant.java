// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

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
public sealed interface CatVariant extends CatVariants permits CatVariantImpl {
    // Assigns a value
    Codec<CatVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "asset_id", Codec.KEY, CatVariant::assetId,
            // Code statement
            "baby_asset_id", Codec.KEY, CatVariant::babyAssetId,
            // Code statement
            CatVariant::create);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<CatVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::catVariant);
    // Calls a method
    Codec<RegistryKey<CatVariant>> NBT_TYPE = RegistryKey.codec(Registries::catVariant);

    // Start of a method/block
    static CatVariant create(Key assetId, Key babyAssetId) {
        // Returns a value to the caller
        return new CatVariantImpl(assetId, babyAssetId);
    // End of a block/expression
    }

    /**
     * Creates a new instance of the "minecraft:cat_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<CatVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("cat_variant"), REGISTRY_CODEC, RegistryData.Resource.CAT_VARIANTS);
    // End of a block/expression
    }

    // Calls a method
    Key assetId();

    // Calls a method
    Key babyAssetId();
// End of a block/expression
}
