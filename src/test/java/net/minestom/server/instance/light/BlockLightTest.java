// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Type declaration (class/interface/enum/record)
public class BlockLightTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        var result = LightCompute.compute(palette, BlockLight.buildInternalQueue(palette));
        // Loop: repeats a block
        for (byte light : result) {
            // Calls a method
            assertEquals(0, light);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void glowstone() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Code statement
        assertLight(palette, Map.of(
                // Creates a new object
                new Vec(0, 1, 0), 15,
                // Creates a new object
                new Vec(0, 1, 1), 14,
                // Creates a new object
                new Vec(0, 1, 2), 13));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleGlowstone() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Calls a method
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());

        // Code statement
        assertLight(palette, Map.of(
                // Creates a new object
                new Vec(1, 1, 3), 11,
                // Creates a new object
                new Vec(3, 3, 7), 9,
                // Creates a new object
                new Vec(1, 1, 1), 13,
                // Creates a new object
                new Vec(3, 1, 4), 14));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void glowstoneBorder() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Code statement
        assertLight(palette, Map.of(
                // X axis
                // Creates a new object
                new Vec(-1, 0, 0), 13,
                // Creates a new object
                new Vec(-1, 1, 0), 14,
                // Creates a new object
                new Vec(-1, 2, 0), 13,
                // Creates a new object
                new Vec(-1, 3, 0), 12,
                // Z axis
                // Creates a new object
                new Vec(0, 0, -1), 13,
                // Creates a new object
                new Vec(0, 1, -1), 14,
                // Creates a new object
                new Vec(0, 2, -1), 13,
                // Creates a new object
                new Vec(0, 3, -1), 12));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void glowstoneBlock() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(0, 1, 0, Block.GLOWSTONE.stateId());
        // Calls a method
        palette.set(0, 1, 1, Block.STONE.stateId());
        // Code statement
        assertLight(palette, Map.of(
                // Creates a new object
                new Vec(0, 1, 0), 15,
                // Creates a new object
                new Vec(0, 1, 1), 0,
                // Creates a new object
                new Vec(0, 1, 2), 11));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void isolated() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());

        // Calls a method
        palette.set(3, 1, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Calls a method
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Code statement
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Code statement
                entry(new Vec(4, 1, 4), 15),
                // Isolation
                // Code statement
                entry(new Vec(3, 1, 4), 0),
                // Code statement
                entry(new Vec(4, 1, 5), 0),
                // Code statement
                entry(new Vec(4, 1, 3), 0),
                // Code statement
                entry(new Vec(5, 1, 4), 0),
                // Code statement
                entry(new Vec(4, 2, 4), 0),
                // Code statement
                entry(new Vec(4, 0, 4), 0),
                // Outside location
                // Calls a method
                entry(new Vec(2, 2, 3), 0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void isolatedStair() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Code statement
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Code statement
                "facing", "east",
                // Code statement
                "half", "bottom",
                // Calls a method
                "shape", "straight")).stateId());
        // Calls a method
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Calls a method
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Code statement
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Code statement
                entry(new Vec(4, 1, 4), 15),
                // Front of stair
                // Calls a method
                entry(new Vec(2, 1, 4), 0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void isolatedStairOpposite() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Code statement
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Code statement
                "facing", "west",
                // Code statement
                "half", "bottom",
                // Calls a method
                "shape", "straight")).stateId());
        // Calls a method
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Calls a method
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Code statement
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Code statement
                entry(new Vec(4, 1, 4), 15),
                // Stair
                // Code statement
                entry(new Vec(3, 1, 4), 14),
                // Front of stair
                // Code statement
                entry(new Vec(2, 1, 4), 11),
                // Others
                // Code statement
                entry(new Vec(3, 0, 5), 12),
                // Calls a method
                entry(new Vec(3, 0, 3), 12)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void isolatedStairWest() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Code statement
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Code statement
                "facing", "west",
                // Code statement
                "half", "bottom",
                // Calls a method
                "shape", "straight")).stateId());
        // Calls a method
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Calls a method
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Code statement
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Code statement
                entry(new Vec(4, 1, 4), 15),
                // Stair
                // Code statement
                entry(new Vec(3, 1, 4), 14),
                // Front of stair
                // Code statement
                entry(new Vec(2, 1, 4), 11),
                // Others
                // Code statement
                entry(new Vec(3, 0, 5), 12),
                // Code statement
                entry(new Vec(3, 0, 3), 12),
                // Code statement
                entry(new Vec(3, 2, 4), 13),
                // Code statement
                entry(new Vec(3, -1, 4), 10),
                // Calls a method
                entry(new Vec(2, 0, 4), 10)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void isolatedStairSouth() {
        // Calls a method
        var palette = Palette.blocks();
        // Calls a method
        palette.set(4, 1, 4, Block.GLOWSTONE.stateId());
        // Code statement
        palette.set(3, 1, 4, Block.OAK_STAIRS.withProperties(Map.of(
                // Code statement
                "facing", "south",
                // Code statement
                "half", "bottom",
                // Calls a method
                "shape", "straight")).stateId());
        // Calls a method
        palette.set(4, 1, 5, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 1, 3, Block.STONE.stateId());
        // Calls a method
        palette.set(5, 1, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 2, 4, Block.STONE.stateId());
        // Calls a method
        palette.set(4, 0, 4, Block.STONE.stateId());

        // Code statement
        assertLight(palette, Map.ofEntries(
                // Glowstone
                // Code statement
                entry(new Vec(4, 1, 4), 15),
                // Stair
                // Code statement
                entry(new Vec(3, 1, 4), 14),
                // Front of stair
                // Code statement
                entry(new Vec(2, 1, 4), 13),
                // Others
                // Code statement
                entry(new Vec(3, 0, 5), 10),
                // Calls a method
                entry(new Vec(3, 0, 3), 12)));
    // End of a block/expression
    }

    // Start of a method/block
    void assertLight(Palette palette, Map<Vec, Integer> expectedLights) {
        // Calls a method
        byte[] result = LightCompute.compute(palette, BlockLight.buildInternalQueue(palette));
        // Calls a method
        List<String> errors = new ArrayList<>();
        // Loop: repeats a block
        for (int x = 0; x < 16; x++) {
            // Loop: repeats a block
            for (int y = 0; y < 16; y++) {
                // Loop: repeats a block
                for (int z = 0; z < 16; z++) {
                    // Calls a method
                    var expected = expectedLights.get(new Vec(x, y, z));
                    // Branch: checks a condition
                    if (expected != null) {
                        // Calls a method
                        final int light = LightCompute.getLight(result, x, y, z);
                        // Branch: checks a condition
                        if (light != expected) {
                            // Calls a method
                            errors.add(String.format("Expected %d at [%d,%d,%d] but got %d", expected, x, y, z, light));
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (!errors.isEmpty()) {
            // Calls a method
            StringBuilder sb = new StringBuilder();
            // Loop: repeats a block
            for (String s : errors) {
                // Calls a method
                sb.append(s).append("\n");
            // End of a block/expression
            }
            // Calls a method
            System.err.println(sb);
            // Calls a method
            fail();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
