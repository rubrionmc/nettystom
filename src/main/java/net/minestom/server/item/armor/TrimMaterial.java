// Déclaration du paquet de ce fichier
package net.minestom.server.item.armor;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface TrimMaterial extends Holder.Direct<TrimMaterial>, TrimMaterials permits TrimMaterialImpl {
    // Affecte une valeur
    NetworkBuffer.Type<TrimMaterial> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, TrimMaterial::assetName,
            // Instruction de code
            NetworkBuffer.STRING.mapValue(NetworkBuffer.STRING), TrimMaterial::overrideArmorMaterials,
            // Instruction de code
            NetworkBuffer.COMPONENT, TrimMaterial::description,
            // Instruction de code
            TrimMaterial::create);
    // Affecte une valeur
    Codec<TrimMaterial> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "asset_name", Codec.STRING, TrimMaterial::assetName,
            // Instruction de code
            "override_armor_materials", Codec.STRING.mapValue(Codec.STRING).optional(Map.of()), TrimMaterial::overrideArmorMaterials,
            // Instruction de code
            "description", Codec.COMPONENT, TrimMaterial::description,
            // Instruction de code
            TrimMaterial::create);

    // Appelle une méthode
    NetworkBuffer.Type<Holder<TrimMaterial>> NETWORK_TYPE = Holder.networkType(Registries::trimMaterial, REGISTRY_NETWORK_TYPE);
    // Appelle une méthode
    Codec<Holder<TrimMaterial>> CODEC = Holder.codec(Registries::trimMaterial, REGISTRY_CODEC);

    // Instruction de code
    static TrimMaterial create(
            // Instruction de code
            String assetName,
            // Instruction de code
            Map<String, String> overrideArmorMaterials,
            // Instruction de code
            Component description
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new TrimMaterialImpl(assetName, overrideArmorMaterials, description);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for trim materials, loading the vanilla trim materials.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<TrimMaterial> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("trim_material"), REGISTRY_CODEC, RegistryData.Resource.TRIM_MATERIALS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    String assetName();

    // Appelle une méthode
    Map<String, String> overrideArmorMaterials();

    // Appelle une méthode
    Component description();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private String assetName;
        // Instruction de code
        private Material ingredient;
        // Appelle une méthode
        private final Map<String, String> overrideArmorMaterials = new HashMap<>();
        // Instruction de code
        private Component description;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder assetName(String assetName) {
            // Accès à l'objet courant/parent
            this.assetName = assetName;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder overrideArmorMaterials(Map<String, String> overrideArmorMaterials) {
            // Accès à l'objet courant/parent
            this.overrideArmorMaterials.putAll(overrideArmorMaterials);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_, _ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder overrideArmorMaterial(String slot, String material) {
            // Accès à l'objet courant/parent
            this.overrideArmorMaterials.put(slot, material);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder description(Component description) {
            // Accès à l'objet courant/parent
            this.description = description;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public TrimMaterial build() {
            // Renvoie une valeur à l'appelant
            return new TrimMaterialImpl(assetName, overrideArmorMaterials, description);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
