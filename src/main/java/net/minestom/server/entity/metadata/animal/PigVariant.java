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
public sealed interface PigVariant extends PigVariants permits PigVariantImpl {
    // Assigns a value
    Codec<PigVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "model", Model.CODEC.optional(Model.NORMAL), PigVariant::model,
            // Code statement
            "asset_id", Codec.KEY, PigVariant::assetId,
            // Code statement
            "baby_asset_id", Codec.KEY, PigVariant::babyAssetId,
            // Code statement
            PigVariant::create);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<PigVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::pigVariant);
    // Calls a method
    Codec<RegistryKey<PigVariant>> CODEC = RegistryKey.codec(Registries::pigVariant);

    /**
     * Creates a new instance of the "minecraft:pig_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<PigVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("pig_variant"), REGISTRY_CODEC, RegistryData.Resource.PIG_VARIANTS);
    // End of a block/expression
    }

    // Start of a method/block
    static PigVariant create(Model model, Key assetId, Key babyAssetId) {
        // Returns a value to the caller
        return new PigVariantImpl(model, assetId, babyAssetId);
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
