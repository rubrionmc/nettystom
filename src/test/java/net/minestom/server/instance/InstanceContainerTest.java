// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class InstanceContainerTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void copyPreservesTag() {
        // Calls a method
        var tag = Tag.String("test");
        // Calls a method
        var instance = new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD);
        // Calls a method
        instance.setTag(tag, "123");

        // Calls a method
        var copyInstance = instance.copy();
        // Calls a method
        var result = copyInstance.getTag(tag);
        // Calls a method
        assertEquals("123", result);
    // End of a block/expression
    }
// End of a block/expression
}
