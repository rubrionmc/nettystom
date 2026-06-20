// Package declaration for this file
package net.minestom.server.entity.metadata.other;

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
import net.minestom.server.registry.*;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public sealed interface PaintingVariant extends Holder.Direct<PaintingVariant>, PaintingVariants permits PaintingVariantImpl {
    // Assigns a value
    NetworkBuffer.Type<PaintingVariant> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.INT, PaintingVariant::width,
            // Code statement
            NetworkBuffer.INT, PaintingVariant::height,
            // Code statement
            NetworkBuffer.KEY, PaintingVariant::assetId,
            // Code statement
            NetworkBuffer.COMPONENT.optional(), PaintingVariant::title,
            // Code statement
            NetworkBuffer.COMPONENT.optional(), PaintingVariant::author,
            // Code statement
            PaintingVariantImpl::new);
    // Assigns a value
    Codec<PaintingVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "width", Codec.INT, PaintingVariant::width,
            // Code statement
            "height", Codec.INT, PaintingVariant::height,
            // Code statement
            "asset_id", Codec.KEY, PaintingVariant::assetId,
            // Code statement
            "title", Codec.COMPONENT.optional(), PaintingVariant::title,
            // Code statement
            "author", Codec.COMPONENT.optional(), PaintingVariant::author,
            // Code statement
            PaintingVariantImpl::new);

    // For some unknown reason, the network type still uses a holder even though the codec does not.
    // This appears to be a mistake since stopping inline values was explicitly mentioned as a change in snapshot notes.
    // It would also not work on vanilla as serializing a painting entity with inline variant would fail.
    // However, we don't serialize painting entities, so we can allow this :) Use at your own risk.
    // IMPL: Please remove the workaround later if this is fixed.
    // Calls a method
    NetworkBuffer.Type<Holder<PaintingVariant>> NETWORK_TYPE = Holder.networkType(Registries::paintingVariant, REGISTRY_NETWORK_TYPE);
    // Assigns a value
    Codec<Holder<PaintingVariant>> CODEC = RegistryKey.codec(Registries::paintingVariant)
            // Calls a method
            .transform(key -> key, Holder::asKey);

    // Code statement
    static PaintingVariant create(
            // Code statement
            Key assetId,
            // Code statement
            int width, int height,
            // Annotation for the following element
            @Nullable Component title,
            // Annotation for the following element
            @Nullable Component author
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new PaintingVariantImpl(width, height, assetId, title, author);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for painting variants, loading the vanilla painting variants.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<PaintingVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("painting_variant"), REGISTRY_CODEC, RegistryData.Resource.PAINTING_VARIANTS);
    // End of a block/expression
    }

    // Calls a method
    Key assetId();

    // Calls a method
    int width();

    // Calls a method
    int height();

    // Annotation for the following element
    @Nullable Component title();

    // Annotation for the following element
    @Nullable Component author();

    // Type declaration (class/interface/enum/record)
    class Builder {
        // Code statement
        private Key assetId;
        // Code statement
        private int width;
        // Code statement
        private int height;
        // Code statement
        private Component title;
        // Code statement
        private Component author;

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
        public Builder width(int width) {
            // Access to the current/parent object
            this.width = width;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder height(int height) {
            // Access to the current/parent object
            this.height = height;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder title(@Nullable Component title) {
            // Access to the current/parent object
            this.title = title;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder author(@Nullable Component author) {
            // Access to the current/parent object
            this.author = author;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public PaintingVariant build() {
            // Returns a value to the caller
            return new PaintingVariantImpl(width, height, assetId, title, author);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
