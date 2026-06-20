// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;

// Type declaration (class/interface/enum/record)
public class ComponentTest extends AbstractItemComponentTest<Component> {
    // This is not a test, but it creates a compile error if the component type is changed away from Component,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<Component>> SHARED_COMPONENTS = List.of(
            // Code statement
            DataComponents.CUSTOM_NAME,
            // Code statement
            DataComponents.ITEM_NAME
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<Component> component() {
        // Returns a value to the caller
        return SHARED_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, Component>> directReadWriteEntries() {
        // Component serialization is well tested elsewhere, this is just a sanity check really.
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("empty component", Component.empty()),
                // Code statement
                Map.entry("text component", Component.text("Hello, world!"))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testItemNameParseRegression() throws Exception {
        // Calls a method
        var nbt = MinestomAdventure.tagStringIO().asTag("{translate: \"item.minecraft.diamond\"}");
        // Calls a method
        var component = DataComponents.ITEM_NAME.decode(Transcoder.NBT, nbt);
        // Calls a method
        assertOk(component);
    // End of a block/expression
    }
// End of a block/expression
}
