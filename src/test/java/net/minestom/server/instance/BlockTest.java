// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class BlockTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNBT() {
        // Assigns a value
        Block block = Block.CHEST;
        // Calls a method
        assertFalse(block.hasNbt());
        // Calls a method
        assertNull(block.nbt());

        // Calls a method
        var nbt = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Calls a method
        block = block.withNbt(nbt);
        // Calls a method
        assertTrue(block.hasNbt());
        // Calls a method
        assertEquals(block.nbt(), nbt);

        // Calls a method
        block = block.withNbt(null);
        // Calls a method
        assertFalse(block.hasNbt());
        // Calls a method
        assertNull(block.nbt());

        // Calls a method
        var value = block.getTag(Tag.String("key").defaultValue("Default"));
        // Calls a method
        assertEquals("Default", value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void validProperties() {
        // Assigns a value
        Block block = Block.CHEST;
        // Calls a method
        assertEquals(block.properties(), Objects.requireNonNull(Block.fromBlockId(block.id())).properties());

        // Default state may change, but the test is required to ensure the `properties` method is working
        // Code statement
        assertEquals(Map.of("facing", "north",
                // Code statement
                "type", "single",
                // Calls a method
                "waterlogged", "false"), block.properties());

        // Loop: repeats a block
        for (var possible : block.possibleStates()) {
            // Calls a method
            assertEquals(possible, block.withProperties(possible.properties()));
        // End of a block/expression
        }

        // Calls a method
        assertEquals("north", block.withProperty("facing", "north").getProperty("facing"));
        // Calls a method
        assertNotEquals(block.withProperty("facing", "north"), block.withProperty("facing", "south"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testState() {
        // Calls a method
        assertEquals("minecraft:dirt", Block.DIRT.state());
        // Calls a method
        assertEquals(Block.DIRT, Block.fromState("minecraft:dirt"));
        // Calls a method
        assertEquals(Block.CHEST, Block.fromState("minecraft:chest"));
        // Calls a method
        assertEquals(Block.CHEST, Block.fromState("minecraft:chest[]"));
        // Calls a method
        assertEquals(Block.CHEST.withProperty("facing", "north"), Block.fromState("minecraft:chest[facing=north]"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidProperties() {
        // Assigns a value
        Block block = Block.CHEST;
        // Calls a method
        assertThrows(Exception.class, () -> block.withProperty("random", "randomKey"));
        // Calls a method
        assertThrows(Exception.class, () -> block.withProperties(Map.of("random", "randomKey")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEquality() {
        // Calls a method
        var nbt = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Assigns a value
        Block b1 = Block.CHEST;
        // Assigns a value
        Block b2 = Block.CHEST;
        // Calls a method
        assertEquals(b1.withNbt(nbt), b2.withNbt(nbt));

        // Calls a method
        assertEquals("north", b1.withProperty("facing", "north").getProperty("facing"));
        // Calls a method
        assertEquals(b1.withProperty("facing", "north"), b2.withProperty("facing", "north"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMutability() {
        // Assigns a value
        Block block = Block.CHEST;
        // Calls a method
        assertThrows(Exception.class, () -> block.properties().put("facing", "north"));
        // Calls a method
        assertThrows(Exception.class, () -> block.withProperty("facing", "north").properties().put("facing", "south"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testShape() {
        // Calls a method
        Point start = Block.LANTERN.registry().collisionShape().relativeStart();
        // Calls a method
        Point end = Block.LANTERN.registry().collisionShape().relativeEnd();

        // Calls a method
        assertEquals(new Vec(0.3125, 0, 0.3125), start);
        // Calls a method
        assertEquals(new Vec(0.6875, 0.5625, 0.6875), end);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDuplicateProperties() {
        // Calls a method
        HashSet<Integer> assignedStates = new HashSet<>();
        // Loop: repeats a block
        for (Block block : Block.values()) {
            // Loop: repeats a block
            for (Block blockWithState : block.possibleStates()) {
                // Calls a method
                assertTrue(assignedStates.add(blockWithState.stateId()));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStateIdConversion() {
        // Loop: repeats a block
        for (Block block : Block.values()) {
            // Loop: repeats a block
            for (Block blockWithState : block.possibleStates()) {
                // Calls a method
                assertEquals(blockWithState, Block.fromStateId(blockWithState.stateId()));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testBlockEntityRegistryLoading() {
        // Sanity to ensure we correctly load block entity types
        // Calls a method
        assertEquals(BlockEntityType.SIGN, Block.OAK_SIGN.registry().blockEntityType());
    // End of a block/expression
    }
// End of a block/expression
}
