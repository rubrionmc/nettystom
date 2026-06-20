// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;
// Static import of a member
import static org.junit.jupiter.api.Assumptions.assumeFalse;

// Type declaration (class/interface/enum/record)
public class BlockPredicatesTest extends AbstractItemComponentTest<BlockPredicates> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<BlockPredicates> component() {
        // Returns a value to the caller
        return DataComponents.CAN_PLACE_ON; // CAN_BREAK is the same thing
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, BlockPredicates>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // TODO(1.21.5)
                // Code statement
                entry("empty", new BlockPredicates(List.of()))
//                entry("single, no tooltip", new BlockPredicates(BlockPredicate.ALL)),
//                entry("many", new BlockPredicates(List.of(BlockPredicate.ALL, BlockPredicate.NONE)))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSingleBlockNbtInput() throws IOException {
        // Calls a method
        assumeFalse(true, "TODO(1.21.5)");
        // Calls a method
        var tag = MinestomAdventure.tagStringIO().asTag("{blocks:'minecraft:stone'}");
        // Calls a method
        var component = assertOk(DataComponents.CAN_PLACE_ON.decode(Transcoder.NBT, tag));
        // Calls a method
        var expected = new BlockPredicates(new BlockPredicate(Block.STONE));
        // Calls a method
        assertEquals(expected, component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMultiMatch() {
        // Just sanity check that it actually runs both of the predicates
        // Calls a method
        var predicate = new BlockPredicates(List.of(BlockPredicate.NONE, BlockPredicate.ALL));
        // Calls a method
        assertTrue(predicate.test(Block.AIR));
    // End of a block/expression
    }

// End of a block/expression
}
