// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlotGroup;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.*;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Enchantment extends Enchantments permits EnchantmentImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<Enchantment>> NETWORK_TYPE = RegistryKey.networkType(Registries::enchantment);
    // Appelle une méthode
    Codec<RegistryKey<Enchantment>> CODEC = RegistryKey.codec(Registries::enchantment);

    // Affecte une valeur
    Codec<Enchantment> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "description", Codec.COMPONENT, Enchantment::description,
            // Instruction de code
            "exclusive_set", RegistryTag.codec(Registries::enchantment).optional(RegistryTag.empty()), Enchantment::exclusiveSet,
            // Instruction de code
            "supported_items", RegistryTag.codec(Registries::material), Enchantment::supportedItems,
            // Instruction de code
            "primary_items", RegistryTag.codec(Registries::material).optional(), Enchantment::primaryItems,
            // Instruction de code
            "weight", Codec.INT, Enchantment::weight,
            // Instruction de code
            "max_level", Codec.INT, Enchantment::maxLevel,
            // Instruction de code
            "min_cost", Cost.CODEC, Enchantment::minCost,
            // Instruction de code
            "max_cost", Cost.CODEC, Enchantment::maxCost,
            // Instruction de code
            "anvil_cost", Codec.INT, Enchantment::anvilCost,
            // Instruction de code
            "slots", EquipmentSlotGroup.CODEC.list(), Enchantment::slots,
            // Instruction de code
            "effects", EffectComponent.CODEC.optional(DataComponentMap.EMPTY), Enchantment::effects,
            // Instruction de code
            EnchantmentImpl::new);

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for enchantments, loading the vanilla enchantments.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<Enchantment> createDefaultRegistry(Registries registries) {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.createForEnchantmentsWithSelfReferentialLoadingNightmare(
                // Instruction de code
                Key.key("enchantment"), REGISTRY_CODEC, RegistryData.Resource.ENCHANTMENTS, registries
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Component description();

    // Appelle une méthode
    RegistryTag<Enchantment> exclusiveSet();

    // Appelle une méthode
    RegistryTag<Material> supportedItems();

    // Annotation pour l'élément suivant
    @Nullable RegistryTag<Material> primaryItems();

    // Appelle une méthode
    int weight();

    // Appelle une méthode
    int maxLevel();

    // Appelle une méthode
    Cost minCost();

    // Appelle une méthode
    Cost maxCost();

    // Appelle une méthode
    int anvilCost();

    // Appelle une méthode
    List<EquipmentSlotGroup> slots();

    // Appelle une méthode
    DataComponentMap effects();

    // Déclaration de type (classe/interface/enum/record)
    enum Target {
        // Instruction de code
        ATTACKER,
        // Instruction de code
        DAMAGING_ENTITY,
        // Instruction de code
        VICTIM;

        // Appelle une méthode
        public static final Codec<Target> CODEC = Codec.Enum(Target.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Effect permits AttributeEffect, ConditionalEffect, DamageImmunityEffect, EntityEffect, LocationEffect, TargetedConditionalEffect, ValueEffect {

    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Cost(int base, int perLevelAboveFirst) {
        // Appelle une méthode
        public static final Cost DEFAULT = new Cost(1, 1);

        // Affecte une valeur
        public static final Codec<Cost> CODEC = StructCodec.struct(
                // Instruction de code
                "base", Codec.INT, Cost::base,
                // Instruction de code
                "per_level_above_first", Codec.INT, Cost::perLevelAboveFirst,
                // Instruction de code
                Cost::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    class Builder {
        // Appelle une méthode
        private Component description = Component.empty();
        // Appelle une méthode
        private RegistryTag<Enchantment> exclusiveSet = RegistryTag.empty();
        // Appelle une méthode
        private RegistryTag<Material> supportedItems = RegistryTag.empty();
        // Appelle une méthode
        private RegistryTag<Material> primaryItems = RegistryTag.empty();
        // Affecte une valeur
        private int weight = 1;
        // Affecte une valeur
        private int maxLevel = 1;
        // Affecte une valeur
        private Cost minCost = Cost.DEFAULT;
        // Affecte une valeur
        private Cost maxCost = Cost.DEFAULT;
        // Affecte une valeur
        private int anvilCost = 0;
        // Appelle une méthode
        private List<EquipmentSlotGroup> slots = List.of();
        // Appelle une méthode
        private DataComponentMap.Builder effects = DataComponentMap.builder();

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder description(Component description) {
            // Accès à l'objet courant/parent
            this.description = description;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder exclusiveSet(RegistryTag<Enchantment> exclusiveSet) {
            // Accès à l'objet courant/parent
            this.exclusiveSet = exclusiveSet;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder supportedItems(RegistryTag<Material> supportedItems) {
            // Accès à l'objet courant/parent
            this.supportedItems = supportedItems;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder primaryItems(RegistryTag<Material> primaryItems) {
            // Accès à l'objet courant/parent
            this.primaryItems = primaryItems;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder weight(int weight) {
            // Accès à l'objet courant/parent
            this.weight = weight;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder maxLevel(int maxLevel) {
            // Accès à l'objet courant/parent
            this.maxLevel = maxLevel;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder minCost(int base, int perLevelAboveFirst) {
            // Renvoie une valeur à l'appelant
            return minCost(new Cost(base, perLevelAboveFirst));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder minCost(Cost minCost) {
            // Accès à l'objet courant/parent
            this.minCost = minCost;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder maxCost(int base, int perLevelAboveFirst) {
            // Renvoie une valeur à l'appelant
            return maxCost(new Cost(base, perLevelAboveFirst));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder maxCost(Cost maxCost) {
            // Accès à l'objet courant/parent
            this.maxCost = maxCost;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder anvilCost(int anvilCost) {
            // Accès à l'objet courant/parent
            this.anvilCost = anvilCost;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder slots(EquipmentSlotGroup... slots) {
            // Accès à l'objet courant/parent
            this.slots = List.of(slots);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder slots(List<EquipmentSlotGroup> slots) {
            // Accès à l'objet courant/parent
            this.slots = slots;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public <T> Builder effect(DataComponent<T> component, T value) {
            // Appelle une méthode
            effects.set(component, value);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder effects(DataComponentMap effects) {
            // Accès à l'objet courant/parent
            this.effects = effects.toBuilder();
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Enchantment build() {
            // Renvoie une valeur à l'appelant
            return new EnchantmentImpl(
                    // Instruction de code
                    description, exclusiveSet, supportedItems,
                    // Instruction de code
                    primaryItems, weight, maxLevel, minCost, maxCost,
                    // Instruction de code
                    anvilCost, slots, effects.build()
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
