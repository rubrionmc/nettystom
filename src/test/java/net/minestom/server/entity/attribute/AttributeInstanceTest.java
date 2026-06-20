// Package declaration for this file
package net.minestom.server.entity.attribute;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class AttributeInstanceTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testReplaceAttributeSameValue() {
        // Calls a method
        var attribute = new AttributeInstance(Attribute.SAFE_FALL_DISTANCE, null);
        // Calls a method
        var modifier = new AttributeModifier("test", 1.0, AttributeOperation.ADD_VALUE);

        // Calls a method
        attribute.addModifier(modifier);
        // Calls a method
        assertEquals(4, attribute.getValue());

        // Calls a method
        attribute.addModifier(modifier);
        // Calls a method
        assertEquals(4, attribute.getValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testReplaceAttributeNewValue() {
        // Calls a method
        var attribute = new AttributeInstance(Attribute.SAFE_FALL_DISTANCE, null);

        // Calls a method
        attribute.addModifier(new AttributeModifier("test", 1.0, AttributeOperation.ADD_VALUE));
        // Calls a method
        assertEquals(4, attribute.getValue());

        // Calls a method
        attribute.addModifier(new AttributeModifier("test", 2.0, AttributeOperation.ADD_VALUE));
        // Code statement
        assertEquals(5, attribute.getValue()); // New value

        // Calls a method
        attribute.addModifier(new AttributeModifier("test", 2.0, AttributeOperation.ADD_MULTIPLIED_BASE));
        // Code statement
        assertEquals(9, attribute.getValue()); // New operation

    // End of a block/expression
    }

// End of a block/expression
}
