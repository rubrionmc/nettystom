// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.entity.EquipmentSlotGroup;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
record EnchantmentImpl(
        // Code statement
        Component description,
        // Code statement
        RegistryTag<Enchantment> exclusiveSet,
        // Code statement
        RegistryTag<Material> supportedItems,
        // Annotation for the following element
        @Nullable RegistryTag<Material> primaryItems,
        // Code statement
        int weight,
        // Code statement
        int maxLevel,
        // Code statement
        Cost minCost,
        // Code statement
        Cost maxCost,
        // Code statement
        int anvilCost,
        // Code statement
        List<EquipmentSlotGroup> slots,
        // Code statement
        DataComponentMap effects
// Start of a method/block
) implements Enchantment {

    // Start of a method/block
    EnchantmentImpl {
        // Calls a method
        slots = List.copyOf(slots);
    // End of a block/expression
    }

// End of a block/expression
}
