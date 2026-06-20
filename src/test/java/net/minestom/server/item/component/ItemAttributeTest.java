// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlotGroup;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeModifier;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeOperation;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public class ItemAttributeTest extends AbstractItemComponentTest<AttributeList> {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<AttributeList> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.ATTRIBUTE_MODIFIERS;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, AttributeList>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("empty", AttributeList.EMPTY),
                // Instruction de code
                Map.entry("single", new AttributeList(new AttributeList.Modifier(Attribute.MOVEMENT_SPEED, new AttributeModifier("minestom:movement_test", 0.1, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND))),
                // Instruction de code
                Map.entry("multiple", new AttributeList(List.of(
                        // Crée un nouvel objet
                        new AttributeList.Modifier(Attribute.MAX_HEALTH, new AttributeModifier("minestom:health_test", 5, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND),
                        // Crée un nouvel objet
                        new AttributeList.Modifier(Attribute.ATTACK_DAMAGE, new AttributeModifier("minestom:attack_test", 3, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.ANY),
                        // Crée un nouvel objet
                        new AttributeList.Modifier(Attribute.ATTACK_DAMAGE, new AttributeModifier("minestom:attack_test_1", 1.4, AttributeOperation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.CHEST)

                // Instruction de code
                )))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
