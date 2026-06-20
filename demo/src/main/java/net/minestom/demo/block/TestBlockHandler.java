// Package declaration for this file
package net.minestom.demo.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;

// Type declaration (class/interface/enum/record)
public class TestBlockHandler implements BlockHandler {
    // Calls a method
    public static final BlockHandler INSTANCE = new TestBlockHandler();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key getKey() {
        // Returns a value to the caller
        return Key.key("minestom", "test");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void onPlace(Placement placement) {
        // Calls a method
        System.out.println(placement);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void onDestroy(Destroy destroy) {
        // Calls a method
        System.out.println(destroy);
    // End of a block/expression
    }
// End of a block/expression
}
