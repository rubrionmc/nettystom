// Package declaration for this file
package net.minestom.server.world;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
class DimensionTypeBuilderTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    void testCoordinateScale() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().coordinateScale(0));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().coordinateScale(30000001));

        // Calls a method
        assertDoesNotThrow(() -> DimensionType.builder().coordinateScale(1.5));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testMinY() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().minY(-2048));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().minY(2032));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().minY(7));

        // Calls a method
        assertDoesNotThrow(() -> DimensionType.builder().minY(-16));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testHeight() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().height(0));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().height(4080));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().height(17));

        // Calls a method
        assertDoesNotThrow(() -> DimensionType.builder().height(16));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testLogicalHeight() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().logicalHeight(-1));

        // Calls a method
        assertDoesNotThrow(() -> DimensionType.builder().logicalHeight(17));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testMonsterSpawnBlockLightLimit() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().monsterSpawnBlockLightLimit(-1));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().monsterSpawnBlockLightLimit(16));

        // Calls a method
        assertDoesNotThrow(() -> DimensionType.builder().monsterSpawnBlockLightLimit(15));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testBuild() {
        // Code statement
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder()
                // Code statement
                .height(32)
                // Code statement
                .logicalHeight(33)
                // Calls a method
                .build());

        // Code statement
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder()
                // Code statement
                .height(32)
                // Code statement
                .minY(2016)
                // Calls a method
                .build());

        // Code statement
        assertDoesNotThrow(() -> DimensionType.builder()
                // Code statement
                .minY(2000)
                // Code statement
                .height(32)
                // Code statement
                .logicalHeight(10)
                // Calls a method
                .build());
    // End of a block/expression
    }
// End of a block/expression
}