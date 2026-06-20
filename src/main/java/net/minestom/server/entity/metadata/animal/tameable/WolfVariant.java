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
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public sealed interface WolfVariant extends WolfVariants permits WolfVariantImpl {
    // Assigns a value
    Codec<WolfVariant> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "assets", Assets.CODEC, WolfVariant::assets,
            // Code statement
            "baby_assets", Assets.CODEC, WolfVariant::babyAssets,
            // Code statement
            WolfVariantImpl::new);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<WolfVariant>> NETWORK_TYPE = RegistryKey.networkType(Registries::wolfVariant);
    // Calls a method
    Codec<RegistryKey<WolfVariant>> CODEC = RegistryKey.codec(Registries::wolfVariant);

    // Start of a method/block
    static WolfVariant create(Assets assets, Assets babyAssets) {
        // Returns a value to the caller
        return new WolfVariantImpl(assets, babyAssets);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * Creates a new instance of the "minecraft:wolf_variant" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<WolfVariant> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("wolf_variant"), REGISTRY_CODEC, RegistryData.Resource.WOLF_VARIANTS);
    // End of a block/expression
    }

    // Calls a method
    Assets assets();

    // Calls a method
    Assets babyAssets();

    // Type declaration (class/interface/enum/record)
    sealed interface Assets permits WolfVariantImpl.AssetsImpl {
        // Assigns a value
        Codec<Assets> CODEC = StructCodec.struct(
                // Code statement
                "wild", Codec.KEY, Assets::wild,
                // Code statement
                "tame", Codec.KEY, Assets::tame,
                // Code statement
                "angry", Codec.KEY, Assets::angry,
                // Code statement
                Assets::create);

        // Start of a method/block
        static Builder builder() {
            // Returns a value to the caller
            return new Builder();
        // End of a block/expression
        }

        // Start of a method/block
        static Assets create(Key wild, Key tame, Key angry) {
            // Returns a value to the caller
            return new WolfVariantImpl.AssetsImpl(wild, tame, angry);
        // End of a block/expression
        }

        // Calls a method
        Key wild();

        // Calls a method
        Key tame();

        // Calls a method
        Key angry();

        // Type declaration (class/interface/enum/record)
        final class Builder {
            // Code statement
            private @UnknownNullability Key wild;
            // Code statement
            private @UnknownNullability Key tame;
            // Code statement
            private @UnknownNullability Key angry;

            // Start of a method/block
            private Builder() {
            // End of a block/expression
            }

            // Start of a method/block
            public Builder wild(Key wild) {
                // Access to the current/parent object
                this.wild = Objects.requireNonNull(wild, "wild");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public Builder tame(Key tame) {
                // Access to the current/parent object
                this.tame = Objects.requireNonNull(tame, "tame");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public Builder angry(Key angry) {
                // Access to the current/parent object
                this.angry = Objects.requireNonNull(angry, "angry");
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Start of a method/block
            public Assets build() {
                // Returns a value to the caller
                return new WolfVariantImpl.AssetsImpl(wild, tame, angry);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private @UnknownNullability Assets assets;
        // Code statement
        private @UnknownNullability Assets babyAssets;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Start of a method/block
        public Builder assets(Assets assets) {
            // Access to the current/parent object
            this.assets = Objects.requireNonNull(assets, "assets");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder babyAssets(Assets babyAssets) {
            // Access to the current/parent object
            this.babyAssets = Objects.requireNonNull(babyAssets, "babyAssets");
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public WolfVariant build() {
            // Returns a value to the caller
            return new WolfVariantImpl(assets, babyAssets);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
