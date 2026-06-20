// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.EquipmentSlotGroup;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeModifier;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeOperation;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public class ItemAttributeTest extends AbstractItemComponentTest<AttributeList> {
    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<AttributeList> component() {
        // Returns a value to the caller
        return DataComponents.ATTRIBUTE_MODIFIERS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, AttributeList>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("empty", AttributeList.EMPTY),
                // Code statement
                Map.entry("single", new AttributeList(new AttributeList.Modifier(Attribute.MOVEMENT_SPEED, new AttributeModifier("minestom:movement_test", 0.1, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND))),
                // Code statement
                Map.entry("multiple", new AttributeList(List.of(
                        // Creates a new object
                        new AttributeList.Modifier(Attribute.MAX_HEALTH, new AttributeModifier("minestom:health_test", 5, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.MAIN_HAND),
                        // Creates a new object
                        new AttributeList.Modifier(Attribute.ATTACK_DAMAGE, new AttributeModifier("minestom:attack_test", 3, AttributeOperation.ADD_VALUE), EquipmentSlotGroup.ANY),
                        // Creates a new object
                        new AttributeList.Modifier(Attribute.ATTACK_DAMAGE, new AttributeModifier("minestom:attack_test_1", 1.4, AttributeOperation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.CHEST)

                // Code statement
                )))
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
