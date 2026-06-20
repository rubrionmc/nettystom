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
public sealed interface ChickenVariant extends ChickenVariants permits ChickenVariantImpl {
    // Assigns a value
    Codec<ChickenVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "model", Model.CODEC.optional(Model.NORMAL), ChickenVariant::model,
            // Code statement
            "asset_id", Codec.KEY, ChickenVariant::assetId,
            // Code statement
            "baby_asset_id", Codec.KEY, ChickenVariant::babyAssetId,
            // Code statement
            ChickenVariantImpl::new);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<ChickenVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::chickenVariant);
    // Calls a method
    Codec<RegistryKey<ChickenVariant>> CODEC = RegistryKey.codec(Registries::chickenVariant);

    // Start of a method/block
    static ChickenVariant create(Model model, Key assetId, Key babyAssetId) {
        // Returns a value to the caller
        return new ChickenVariantImpl(model, assetId, babyAssetId);
    // End of a block/expression
    }

    /**
     * Creates a new instance of the "minecraft:chicken_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<ChickenVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("chicken_variant"), REGISTRY_CODEC, RegistryData.Resource.CHICKEN_VARIANTS);
    // End of a block/expression
    }

    // Calls a method
    Model model();

    // Calls a method
    Key assetId();

    // Calls a method
    Key babyAssetId();

    // Type declaration (class/interface/enum/record)
    enum Model {
        // Code statement
        NORMAL,
        // Code statement
        COLD;

        // Calls a method
        public static final Codec<Model> CODEC = Codec.Enum(Model.class);
    // End of a block/expression
    }
// End of a block/expression
}
