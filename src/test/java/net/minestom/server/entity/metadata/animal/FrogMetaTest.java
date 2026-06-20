// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Metadata;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
class FrogMetaTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSetVariant() {
        // Calls a method
        Entity frog = new Entity(EntityType.FROG);
        // Calls a method
        frog.set(DataComponents.FROG_VARIANT, FrogVariant.COLD);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSerializeVariant() {
        // Variant is serialized as meta as of 1.21.6
        // Calls a method
        Entity frog = new Entity(EntityType.FROG);
        // Calls a method
        frog.set(DataComponents.FROG_VARIANT, FrogVariant.COLD);
        // Assigns a value
        boolean found = false;
        // Loop: repeats a block
        for (Map.Entry<Integer, Metadata.Entry<?>> entry : frog.getMetadataPacket().entries().entrySet()) {
            // Branch: checks a condition
            if (entry.getValue().type() == Metadata.TYPE_FROG_VARIANT) {
                // Calls a method
                Assertions.assertEquals(FrogVariant.COLD, entry.getValue().value());
                // Assigns a value
                found = true;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        Assertions.assertTrue(found, "Frog variant was not serialized");
    // End of a block/expression
    }
// End of a block/expression
}