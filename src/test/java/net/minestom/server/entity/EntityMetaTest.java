// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class EntityMetaTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void ensureRegistration() throws IllegalAccessException {
        // Calls a method
        List<String> list = new ArrayList<>();
        // Loop: repeats a block
        for (var field : EntityTypes.class.getDeclaredFields()) {
            // Calls a method
            final EntityType entityType = (EntityType) field.get(this);
            // Calls a method
            final String name = entityType.name();
            // Branch: checks a condition
            if (MetadataHolder.ENTITY_META_SUPPLIER.get(name) == null) {
                // Calls a method
                list.add(name);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        assertTrue(list.isEmpty(), "Missing meta for: " + list);
    // End of a block/expression
    }
// End of a block/expression
}
