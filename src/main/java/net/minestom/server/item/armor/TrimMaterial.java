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
import net.minestom.server.item.Material;
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

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public sealed interface TrimMaterial extends Holder.Direct<TrimMaterial>, TrimMaterials permits TrimMaterialImpl {
    // Assigns a value
    NetworkBuffer.Type<TrimMaterial> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING, TrimMaterial::assetName,
            // Code statement
            NetworkBuffer.STRING.mapValue(NetworkBuffer.STRING), TrimMaterial::overrideArmorMaterials,
            // Code statement
            NetworkBuffer.COMPONENT, TrimMaterial::description,
            // Code statement
            TrimMaterial::create);
    // Assigns a value
    Codec<TrimMaterial> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "asset_name", Codec.STRING, TrimMaterial::assetName,
            // Code statement
            "override_armor_materials", Codec.STRING.mapValue(Codec.STRING).optional(Map.of()), TrimMaterial::overrideArmorMaterials,
            // Code statement
            "description", Codec.COMPONENT, TrimMaterial::description,
            // Code statement
            TrimMaterial::create);

    // Calls a method
    NetworkBuffer.Type<Holder<TrimMaterial>> NETWORK_TYPE = Holder.networkType(Registries::trimMaterial, REGISTRY_NETWORK_TYPE);
    // Calls a method
    Codec<Holder<TrimMaterial>> CODEC = Holder.codec(Registries::trimMaterial, REGISTRY_CODEC);

    // Code statement
    static TrimMaterial create(
            // Code statement
            String assetName,
            // Code statement
            Map<String, String> overrideArmorMaterials,
            // Code statement
            Component description
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new TrimMaterialImpl(assetName, overrideArmorMaterials, description);
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
    static DynamicRegistry<TrimMaterial> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("trim_material"), REGISTRY_CODEC, RegistryData.Resource.TRIM_MATERIALS);
    // End of a block/expression
    }

    // Calls a method
    String assetName();

    // Calls a method
    Map<String, String> overrideArmorMaterials();

    // Calls a method
    Component description();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private String assetName;
        // Code statement
        private Material ingredient;
        // Calls a method
        private final Map<String, String> overrideArmorMaterials = new HashMap<>();
        // Code statement
        private Component description;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder assetName(String assetName) {
            // Access to the current/parent object
            this.assetName = assetName;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this", pure = true)
        // Start of a method/block
        public Builder overrideArmorMaterials(Map<String, String> overrideArmorMaterials) {
            // Access to the current/parent object
            this.overrideArmorMaterials.putAll(overrideArmorMaterials);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_, _ -> this", pure = true)
        // Start of a method/block
        public Builder overrideArmorMaterial(String slot, String material) {
            // Access to the current/parent object
            this.overrideArmorMaterials.put(slot, material);
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
        @Contract(pure = true)
        // Start of a method/block
        public TrimMaterial build() {
            // Returns a value to the caller
            return new TrimMaterialImpl(assetName, overrideArmorMaterials, description);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
