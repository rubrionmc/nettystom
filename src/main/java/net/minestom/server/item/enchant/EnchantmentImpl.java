// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlotGroup;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
record EnchantmentImpl(
        // Instruction de code
        Component description,
        // Instruction de code
        RegistryTag<Enchantment> exclusiveSet,
        // Instruction de code
        RegistryTag<Material> supportedItems,
        // Annotation pour l'élément suivant
        @Nullable RegistryTag<Material> primaryItems,
        // Instruction de code
        int weight,
        // Instruction de code
        int maxLevel,
        // Instruction de code
        Cost minCost,
        // Instruction de code
        Cost maxCost,
        // Instruction de code
        int anvilCost,
        // Instruction de code
        List<EquipmentSlotGroup> slots,
        // Instruction de code
        DataComponentMap effects
// Début d'une méthode/d'un bloc
) implements Enchantment {

    // Début d'une méthode/d'un bloc
    EnchantmentImpl {
        // Appelle une méthode
        slots = List.copyOf(slots);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
