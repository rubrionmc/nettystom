// Package declaration for this file
package net.minestom.server.instance.block.banner;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Holder;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Type declaration (class/interface/enum/record)
public sealed interface BannerPattern extends Holder.Direct<BannerPattern>, BannerPatterns,
        // Start of a method/block
        Translatable permits BannerPatternImpl {
    // Assigns a value
    NetworkBuffer.Type<BannerPattern> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.KEY, BannerPattern::assetId,
            // Code statement
            NetworkBuffer.STRING, BannerPattern::translationKey,
            // Code statement
            BannerPattern::create);
    // Assigns a value
    Codec<BannerPattern> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "asset_id", Codec.KEY, BannerPattern::assetId,
            // Code statement
            "translation_key", Codec.STRING, BannerPattern::translationKey,
            // Code statement
            BannerPattern::create);

    // Calls a method
    NetworkBuffer.Type<Holder<BannerPattern>> HOLDER_NETWORK_TYPE = Holder.networkType(Registries::bannerPattern, BannerPattern.REGISTRY_NETWORK_TYPE);
    // Calls a method
    Codec<Holder<BannerPattern>> HOLDER_CODEC = Holder.codec(Registries::bannerPattern, BannerPattern.REGISTRY_CODEC);

    // Code statement
    static BannerPattern create(
            // Code statement
            Key assetId,
            // Code statement
            String translationKey
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new BannerPatternImpl(assetId, translationKey);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for banner patterns, loading the vanilla banner patterns.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<BannerPattern> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("banner_pattern"), REGISTRY_CODEC, RegistryData.Resource.BANNER_PATTERNS);
    // End of a block/expression
    }

    // Calls a method
    Key assetId();

    // Annotation for the following element
    @Override
    // Calls a method
    String translationKey();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private Key assetId;
        // Code statement
        private String translationKey;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder assetId(Key assetId) {
            // Access to the current/parent object
            this.assetId = assetId;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder translationKey(String translationKey) {
            // Access to the current/parent object
            this.translationKey = translationKey;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public BannerPattern build() {
            // Returns a value to the caller
            return new BannerPatternImpl(assetId, translationKey);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
