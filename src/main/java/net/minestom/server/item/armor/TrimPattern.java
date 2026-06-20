// Package declaration for this file
package net.minestom.server.item.armor;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
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
public sealed interface TrimPattern extends Holder.Direct<TrimPattern>, TrimPatterns permits TrimPatternImpl {
    // Assigns a value
    NetworkBuffer.Type<TrimPattern> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.KEY, TrimPattern::assetId,
            // Code statement
            NetworkBuffer.COMPONENT, TrimPattern::description,
            // Code statement
            NetworkBuffer.BOOLEAN, TrimPattern::isDecal,
            // Code statement
            TrimPattern::create);
    // Assigns a value
    Codec<TrimPattern> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "asset_id", Codec.KEY, TrimPattern::assetId,
            // Code statement
            "description", Codec.COMPONENT, TrimPattern::description,
            // Code statement
            "decal", Codec.BOOLEAN, TrimPattern::isDecal,
            // Code statement
            TrimPattern::create);

    // Calls a method
    NetworkBuffer.Type<Holder<TrimPattern>> NETWORK_TYPE = Holder.networkType(Registries::trimPattern, REGISTRY_NETWORK_TYPE);
    // Calls a method
    Codec<Holder<TrimPattern>> CODEC = Holder.codec(Registries::trimPattern, REGISTRY_CODEC);

    // Code statement
    static TrimPattern create(
            // Code statement
            Key assetId,
            // Code statement
            Component description,
            // Code statement
            boolean decal
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new TrimPatternImpl(assetId, description, decal);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for trim materials, loading the vanilla trim materials.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<TrimPattern> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("trim_pattern"), REGISTRY_CODEC, RegistryData.Resource.TRIM_PATTERNS);
    // End of a block/expression
    }

    // Calls a method
    Key assetId();

    // Calls a method
    Component description();

    // Calls a method
    boolean isDecal();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private Key assetId;
        // Code statement
        private Component description;
        // Code statement
        private boolean decal;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder assetId(String assetId) {
            // Returns a value to the caller
            return assetId(Key.key(assetId));
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
        public Builder description(Component description) {
            // Access to the current/parent object
            this.description = description;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder decal(boolean decal) {
            // Access to the current/parent object
            this.decal = decal;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public TrimPattern build() {
            // Returns a value to the caller
            return new TrimPatternImpl(assetId, description, decal);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
