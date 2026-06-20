// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class TestShape {

    // Start of a method/block
    private static Stream<Arguments> isFullFaceCases() {
        // Returns a value to the caller
        return Stream.of(
                // Code statement
                Arguments.of(Block.STONE, BlockFace.BOTTOM, true),
                // Code statement
                Arguments.of(Block.ENCHANTING_TABLE, BlockFace.BOTTOM, true),
                // Code statement
                Arguments.of(Block.ENCHANTING_TABLE, BlockFace.TOP, false),
                // Code statement
                Arguments.of(Block.ENCHANTING_TABLE, BlockFace.NORTH, false),
                // Code statement
                Arguments.of(Block.ACACIA_FENCE, BlockFace.TOP, false),
                // Code statement
                Arguments.of(Block.IRON_BARS, BlockFace.TOP, false),
                // We are testing collision faces here, so this should be true even though it doesnt occlude light
                // Code statement
                Arguments.of(Block.GLASS, BlockFace.TOP, true),
                // Code statement
                Arguments.of(Block.DARK_OAK_DOOR, BlockFace.NORTH, false),
                // Code statement
                Arguments.of(Block.DARK_OAK_DOOR, BlockFace.SOUTH, true)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("isFullFaceCases")
    // Start of a method/block
    void isFullFace(Block block, BlockFace face, boolean isFullFace) {
        // Calls a method
        assertEquals(block.registry().collisionShape().isFaceFull(face), isFullFace);
    // End of a block/expression
    }
// End of a block/expression
}
