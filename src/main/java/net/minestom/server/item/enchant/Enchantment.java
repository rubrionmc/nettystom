// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.entity.EquipmentSlotGroup;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.*;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public sealed interface Enchantment extends Enchantments permits EnchantmentImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<Enchantment>> NETWORK_TYPE = RegistryKey.networkType(Registries::enchantment);
    // Calls a method
    Codec<RegistryKey<Enchantment>> CODEC = RegistryKey.codec(Registries::enchantment);

    // Assigns a value
    Codec<Enchantment> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "description", Codec.COMPONENT, Enchantment::description,
            // Code statement
            "exclusive_set", RegistryTag.codec(Registries::enchantment).optional(RegistryTag.empty()), Enchantment::exclusiveSet,
            // Code statement
            "supported_items", RegistryTag.codec(Registries::material), Enchantment::supportedItems,
            // Code statement
            "primary_items", RegistryTag.codec(Registries::material).optional(), Enchantment::primaryItems,
            // Code statement
            "weight", Codec.INT, Enchantment::weight,
            // Code statement
            "max_level", Codec.INT, Enchantment::maxLevel,
            // Code statement
            "min_cost", Cost.CODEC, Enchantment::minCost,
            // Code statement
            "max_cost", Cost.CODEC, Enchantment::maxCost,
            // Code statement
            "anvil_cost", Codec.INT, Enchantment::anvilCost,
            // Code statement
            "slots", EquipmentSlotGroup.CODEC.list(), Enchantment::slots,
            // Code statement
            "effects", EffectComponent.CODEC.optional(DataComponentMap.EMPTY), Enchantment::effects,
            // Code statement
            EnchantmentImpl::new);

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for enchantments, loading the vanilla enchantments.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<Enchantment> createDefaultRegistry(Registries registries) {
        // Returns a value to the caller
        return DynamicRegistry.createForEnchantmentsWithSelfReferentialLoadingNightmare(
                // Code statement
                Key.key("enchantment"), REGISTRY_CODEC, RegistryData.Resource.ENCHANTMENTS, registries
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Calls a method
    Component description();

    // Calls a method
    RegistryTag<Enchantment> exclusiveSet();

    // Calls a method
    RegistryTag<Material> supportedItems();

    // Annotation for the following element
    @Nullable RegistryTag<Material> primaryItems();

    // Calls a method
    int weight();

    // Calls a method
    int maxLevel();

    // Calls a method
    Cost minCost();

    // Calls a method
    Cost maxCost();

    // Calls a method
    int anvilCost();

    // Calls a method
    List<EquipmentSlotGroup> slots();

    // Calls a method
    DataComponentMap effects();

    // Type declaration (class/interface/enum/record)
    enum Target {
        // Code statement
        ATTACKER,
        // Code statement
        DAMAGING_ENTITY,
        // Code statement
        VICTIM;

        // Calls a method
        public static final Codec<Target> CODEC = Codec.Enum(Target.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Effect permits AttributeEffect, ConditionalEffect, DamageImmunityEffect, EntityEffect, LocationEffect, TargetedConditionalEffect, ValueEffect {

    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Cost(int base, int perLevelAboveFirst) {
        // Calls a method
        public static final Cost DEFAULT = new Cost(1, 1);

        // Assigns a value
        public static final Codec<Cost> CODEC = StructCodec.struct(
                // Code statement
                "base", Codec.INT, Cost::base,
                // Code statement
                "per_level_above_first", Codec.INT, Cost::perLevelAboveFirst,
                // Code statement
                Cost::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    class Builder {
        // Calls a method
        private Component description = Component.empty();
        // Calls a method
        private RegistryTag<Enchantment> exclusiveSet = RegistryTag.empty();
        // Calls a method
        private RegistryTag<Material> supportedItems = RegistryTag.empty();
        // Calls a method
        private RegistryTag<Material> primaryItems = RegistryTag.empty();
        // Assigns a value
        private int weight = 1;
        // Assigns a value
        private int maxLevel = 1;
        // Assigns a value
        private Cost minCost = Cost.DEFAULT;
        // Assigns a value
        private Cost maxCost = Cost.DEFAULT;
        // Assigns a value
        private int anvilCost = 0;
        // Calls a method
        private List<EquipmentSlotGroup> slots = List.of();
        // Calls a method
        private DataComponentMap.Builder effects = DataComponentMap.builder();

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Start of a method/block
        public Builder description(Component description) {
            // Access to the current/parent object
            this.description = description;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder exclusiveSet(RegistryTag<Enchantment> exclusiveSet) {
            // Access to the current/parent object
            this.exclusiveSet = exclusiveSet;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder supportedItems(RegistryTag<Material> supportedItems) {
            // Access to the current/parent object
            this.supportedItems = supportedItems;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder primaryItems(RegistryTag<Material> primaryItems) {
            // Access to the current/parent object
            this.primaryItems = primaryItems;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder weight(int weight) {
            // Access to the current/parent object
            this.weight = weight;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder maxLevel(int maxLevel) {
            // Access to the current/parent object
            this.maxLevel = maxLevel;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder minCost(int base, int perLevelAboveFirst) {
            // Returns a value to the caller
            return minCost(new Cost(base, perLevelAboveFirst));
        // End of a block/expression
        }

        // Start of a method/block
        public Builder minCost(Cost minCost) {
            // Access to the current/parent object
            this.minCost = minCost;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder maxCost(int base, int perLevelAboveFirst) {
            // Returns a value to the caller
            return maxCost(new Cost(base, perLevelAboveFirst));
        // End of a block/expression
        }

        // Start of a method/block
        public Builder maxCost(Cost maxCost) {
            // Access to the current/parent object
            this.maxCost = maxCost;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder anvilCost(int anvilCost) {
            // Access to the current/parent object
            this.anvilCost = anvilCost;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder slots(EquipmentSlotGroup... slots) {
            // Access to the current/parent object
            this.slots = List.of(slots);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder slots(List<EquipmentSlotGroup> slots) {
            // Access to the current/parent object
            this.slots = slots;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public <T> Builder effect(DataComponent<T> component, T value) {
            // Calls a method
            effects.set(component, value);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder effects(DataComponentMap effects) {
            // Access to the current/parent object
            this.effects = effects.toBuilder();
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Enchantment build() {
            // Returns a value to the caller
            return new EnchantmentImpl(
                    // Code statement
                    description, exclusiveSet, supportedItems,
                    // Code statement
                    primaryItems, weight, maxLevel, minCost, maxCost,
                    // Code statement
                    anvilCost, slots, effects.build()
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
