// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.ChunkRange;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Déclaration de type (classe/interface/enum/record)
public class ChunkUtilsTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("testForDifferingChunksInRangeParams")
    // Début d'une méthode/d'un bloc
    public void testForDifferingChunksInRange(int nx, int nz, int ox, int oz, int r) {
        // Affecte une valeur
        final Set<ChunkCoordinate> n = new HashSet<>();
        // Affecte une valeur
        final Set<ChunkCoordinate> o = new HashSet<>();
        // Appelle une méthode
        ChunkRange.chunksInRange(nx, nz, r, (x, z) -> n.add(new ChunkCoordinate(x, z)));
        // Appelle une méthode
        ChunkRange.chunksInRange(ox, oz, r, (x, z) -> o.add(new ChunkCoordinate(x, z)));

        // Affecte une valeur
        final List<ChunkCoordinate> actualNew = new ArrayList<>();
        // Affecte une valeur
        final List<ChunkCoordinate> actualOld = new ArrayList<>();
        // Instruction de code
        ChunkRange.chunksInRangeDiffering(nx, nz, ox, oz, r, ((x, z) -> actualNew.add(new ChunkCoordinate(x, z))),
                // Appelle une méthode
                ((x, z) -> actualOld.add(new ChunkCoordinate(x, z))));

        // Appelle une méthode
        final Comparator<ChunkCoordinate> sorter = Comparator.comparingInt(ChunkCoordinate::x).thenComparingInt(ChunkCoordinate::z);
        // Appelle une méthode
        final List<ChunkCoordinate> expectedNew = n.stream().filter(x -> !o.contains(x)).sorted(sorter).toList();
        // Appelle une méthode
        final List<ChunkCoordinate> expectedOld = o.stream().filter(x -> !n.contains(x)).sorted(sorter).toList();

        // Appelle une méthode
        Assertions.assertIterableEquals(expectedNew, actualNew.stream().sorted(sorter).toList());
        // Appelle une méthode
        Assertions.assertIterableEquals(expectedOld, actualOld.stream().sorted(sorter).toList());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Stream<Arguments> testForDifferingChunksInRangeParams() {
        // Renvoie une valeur à l'appelant
        return Stream.of(
                // Instruction de code
                Arguments.of(1, 0, 0, 0, 16),
                // Instruction de code
                Arguments.of(1, 1, 0, 0, 16),
                // Instruction de code
                Arguments.of(3, 1, 1, 0, 16),
                // Instruction de code
                Arguments.of(10, 1, 3, 5, 16),
                // Instruction de code
                Arguments.of(10, 10, -10, -10, 16),
                // Instruction de code
                Arguments.of(1, 0, 0, 0, 3),
                // Instruction de code
                Arguments.of(1, 1, 0, 0, 3),
                // Instruction de code
                Arguments.of(3, 1, 1, 0, 3),
                // Instruction de code
                Arguments.of(10, 1, 3, 5, 3),
                // Instruction de code
                Arguments.of(10, 10, -10, -10, 3)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record ChunkCoordinate(int x, int z) {}
// Fin d'un bloc/d'une expression
}
