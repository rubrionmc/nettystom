// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.coordinate.ChunkRange;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.stream.Stream;

// Type declaration (class/interface/enum/record)
public class ChunkUtilsTest {

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("testForDifferingChunksInRangeParams")
    // Start of a method/block
    public void testForDifferingChunksInRange(int nx, int nz, int ox, int oz, int r) {
        // Calls a method
        final Set<ChunkCoordinate> n = new HashSet<>();
        // Calls a method
        final Set<ChunkCoordinate> o = new HashSet<>();
        // Calls a method
        ChunkRange.chunksInRange(nx, nz, r, (x, z) -> n.add(new ChunkCoordinate(x, z)));
        // Calls a method
        ChunkRange.chunksInRange(ox, oz, r, (x, z) -> o.add(new ChunkCoordinate(x, z)));

        // Calls a method
        final List<ChunkCoordinate> actualNew = new ArrayList<>();
        // Calls a method
        final List<ChunkCoordinate> actualOld = new ArrayList<>();
        // Code statement
        ChunkRange.chunksInRangeDiffering(nx, nz, ox, oz, r, ((x, z) -> actualNew.add(new ChunkCoordinate(x, z))),
                // Calls a method
                ((x, z) -> actualOld.add(new ChunkCoordinate(x, z))));

        // Calls a method
        final Comparator<ChunkCoordinate> sorter = Comparator.comparingInt(ChunkCoordinate::x).thenComparingInt(ChunkCoordinate::z);
        // Calls a method
        final List<ChunkCoordinate> expectedNew = n.stream().filter(x -> !o.contains(x)).sorted(sorter).toList();
        // Calls a method
        final List<ChunkCoordinate> expectedOld = o.stream().filter(x -> !n.contains(x)).sorted(sorter).toList();

        // Calls a method
        Assertions.assertIterableEquals(expectedNew, actualNew.stream().sorted(sorter).toList());
        // Calls a method
        Assertions.assertIterableEquals(expectedOld, actualOld.stream().sorted(sorter).toList());
    // End of a block/expression
    }

    // Start of a method/block
    private static Stream<Arguments> testForDifferingChunksInRangeParams() {
        // Returns a value to the caller
        return Stream.of(
                // Code statement
                Arguments.of(1, 0, 0, 0, 16),
                // Code statement
                Arguments.of(1, 1, 0, 0, 16),
                // Code statement
                Arguments.of(3, 1, 1, 0, 16),
                // Code statement
                Arguments.of(10, 1, 3, 5, 16),
                // Code statement
                Arguments.of(10, 10, -10, -10, 16),
                // Code statement
                Arguments.of(1, 0, 0, 0, 3),
                // Code statement
                Arguments.of(1, 1, 0, 0, 3),
                // Code statement
                Arguments.of(3, 1, 1, 0, 3),
                // Code statement
                Arguments.of(10, 1, 3, 5, 3),
                // Code statement
                Arguments.of(10, 10, -10, -10, 3)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record ChunkCoordinate(int x, int z) {}
// End of a block/expression
}
