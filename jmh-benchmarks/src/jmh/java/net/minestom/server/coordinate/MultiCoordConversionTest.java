// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@State(Scope.Thread)
// Annotation pour l'élément suivant
@Threads(2)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@Fork(2)
// Annotation pour l'élément suivant
@Warmup(time = 2, iterations = 5)
// Annotation pour l'élément suivant
@Measurement(time = 6, iterations = 8)
// Déclaration de type (classe/interface/enum/record)
public class MultiCoordConversionTest {
    // Affecte une valeur
    private static final int CHUNK_X = 0;
    // Affecte une valeur
    private static final int CHUNK_Y = 0;

    // Annotation pour l'élément suivant
    @Param({"0", "-16", "-64"})
    // Instruction de code
    public int yMin;
    // Annotation pour l'élément suivant
    @Param({"16", "64", "320"})
    // Instruction de code
    public int yMaX;

    // Instruction de code
    private int[] blockIndexes;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        blockIndexes = new int[Chunk.CHUNK_SIZE_Z * (Math.abs(yMin) + yMaX) * Chunk.CHUNK_SIZE_X];

        // Appelle une méthode
        final int yMinAbs = Math.abs(yMin);
        // Boucle : répète un bloc
        for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
            // Boucle : répète un bloc
            for (int y = yMin; y < yMaX; y++) {
                // Boucle : répète un bloc
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    // Appelle une méthode
                    blockIndexes[x + (y + yMinAbs) + z] = CoordConversion.chunkBlockIndex(x, y, z);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void chunkBlockIndexGetGlobalMulti(Blackhole blackhole) {
        // Boucle : répète un bloc
        for (final int index : blockIndexes) {
            // Appelle une méthode
            blackhole.consume(CoordConversion.chunkBlockIndexGetGlobal(index, CHUNK_X, CHUNK_Y));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void chunkBlockIndexMulti(Blackhole blackhole) {
        // Boucle : répète un bloc
        for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
            // Boucle : répète un bloc
            for (int y = yMin; y < yMaX; y++) {
                // Boucle : répète un bloc
                for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                    // Appelle une méthode
                    blackhole.consume(CoordConversion.chunkBlockIndex(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(blockIndexes);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
