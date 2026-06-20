// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongSet;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CoordinateTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkIndex() {
        // Appelle une méthode
        var index = CoordConversion.chunkIndex(2, 5);
        // Appelle une méthode
        assertEquals(2, CoordConversion.chunkIndexGetX(index));
        // Appelle une méthode
        assertEquals(5, CoordConversion.chunkIndexGetZ(index));

        // Appelle une méthode
        index = CoordConversion.chunkIndex(-5, 25);
        // Appelle une méthode
        assertEquals(-5, CoordConversion.chunkIndexGetX(index));
        // Appelle une méthode
        assertEquals(25, CoordConversion.chunkIndexGetZ(index));

        // Appelle une méthode
        index = CoordConversion.chunkIndex(Integer.MAX_VALUE, Integer.MIN_VALUE);
        // Appelle une méthode
        assertEquals(Integer.MAX_VALUE, CoordConversion.chunkIndexGetX(index));
        // Appelle une méthode
        assertEquals(Integer.MIN_VALUE, CoordConversion.chunkIndexGetZ(index));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkCoordinate() {
        // Appelle une méthode
        assertEquals(0, CoordConversion.globalToChunk(15));
        // Appelle une méthode
        assertEquals(1, CoordConversion.globalToChunk(16));
        // Appelle une méthode
        assertEquals(-1, CoordConversion.globalToChunk(-16));
        // Appelle une méthode
        assertEquals(3, CoordConversion.globalToChunk(48));

        // Appelle une méthode
        assertEquals(4, CoordConversion.globalToChunk(65));
        // Appelle une méthode
        assertEquals(4, CoordConversion.globalToChunk(64));
        // Appelle une méthode
        assertEquals(3, CoordConversion.globalToChunk(63));
        // Appelle une méthode
        assertEquals(-2, CoordConversion.globalToChunk(-25));
        // Appelle une méthode
        assertEquals(23, CoordConversion.globalToChunk(380));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkCount() {
        // Appelle une méthode
        assertEquals(289, ChunkRange.chunksCount(8));
        // Appelle une méthode
        assertEquals(169, ChunkRange.chunksCount(6));
        // Appelle une méthode
        assertEquals(121, ChunkRange.chunksCount(5));
        // Appelle une méthode
        assertEquals(9, ChunkRange.chunksCount(1));
        // Appelle une méthode
        assertEquals(1, ChunkRange.chunksCount(0));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> ChunkRange.chunksCount(-1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vecAddition() {
        // Affecte une valeur
        Vec temp = Vec.ZERO;
        // Appelle une méthode
        assertEquals(0, temp.x());
        // Appelle une méthode
        assertEquals(0, temp.y());
        // Appelle une méthode
        assertEquals(0, temp.z());

        // Appelle une méthode
        temp = temp.add(1);
        // Appelle une méthode
        assertEquals(1, temp.x());
        // Appelle une méthode
        assertEquals(1, temp.y());
        // Appelle une méthode
        assertEquals(1, temp.z());

        // Appelle une méthode
        temp = temp.add(1, 0, 0);
        // Appelle une méthode
        assertEquals(2, temp.x());
        // Appelle une méthode
        assertEquals(1, temp.y());
        // Appelle une méthode
        assertEquals(1, temp.z());

        // Appelle une méthode
        temp = temp.add(0, 1, 0);
        // Appelle une méthode
        assertEquals(2, temp.x());
        // Appelle une méthode
        assertEquals(2, temp.y());
        // Appelle une méthode
        assertEquals(1, temp.z());

        // Appelle une méthode
        temp = temp.add(0, 0, 1);
        // Appelle une méthode
        assertEquals(2, temp.x());
        // Appelle une méthode
        assertEquals(2, temp.y());
        // Appelle une méthode
        assertEquals(2, temp.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vecWith() {
        // Appelle une méthode
        Vec temp = Vec.ZERO.withX(1);
        // Appelle une méthode
        assertEquals(1, temp.x());
        // Appelle une méthode
        assertEquals(0, temp.y());
        // Appelle une méthode
        assertEquals(0, temp.z());

        // Appelle une méthode
        temp = temp.withX(x -> x * 2 + 1);
        // Appelle une méthode
        assertEquals(3, temp.x());
        // Appelle une méthode
        assertEquals(0, temp.y());
        // Appelle une méthode
        assertEquals(0, temp.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vecNegCompare() {
        // Appelle une méthode
        assertFalse(Vec.ZERO.samePoint(Vec.ONE.neg()));
        // Appelle une méthode
        assertTrue(Vec.ZERO.samePoint(Vec.ZERO.neg()));
        // Appelle une méthode
        assertTrue(Vec.ZERO.samePoint(new Vec(-0, 0, 0)));
        // Appelle une méthode
        assertTrue(Vec.ZERO.samePoint(new Vec(-0, -0, -0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vecNanCompare() {
        // Appelle une méthode
        Vec nanX = new Vec(Double.NaN, 0, 0);
        // Appelle une méthode
        Vec nanY = new Vec(0, Double.NaN, 0);
        // Appelle une méthode
        Vec nanZ = new Vec(0, 0, Double.NaN);
        // Appelle une méthode
        Vec nanAll = new Vec(Double.NaN, Double.NaN, Double.NaN);
        // Affecte une valeur
        Vec normal = Vec.ZERO;

        // NaN is not equal to itself
        // Appelle une méthode
        assertFalse(nanX.samePoint(nanX));
        // Appelle une méthode
        assertFalse(nanY.samePoint(nanY));
        // Appelle une méthode
        assertFalse(nanZ.samePoint(nanZ));
        // Appelle une méthode
        assertFalse(nanAll.samePoint(nanAll));

        // NaN vectors are not equal to normal vectors
        // Appelle une méthode
        assertFalse(nanX.samePoint(normal));
        // Appelle une méthode
        assertFalse(nanY.samePoint(normal));
        // Appelle une méthode
        assertFalse(nanZ.samePoint(normal));
        // Appelle une méthode
        assertFalse(nanAll.samePoint(normal));

        // Different NaN vectors are not equal to each other
        // Appelle une méthode
        assertFalse(nanX.samePoint(nanY));
        // Appelle une méthode
        assertFalse(nanX.samePoint(nanZ));
        // Appelle une méthode
        assertFalse(nanY.samePoint(nanZ));
        // Appelle une méthode
        assertFalse(nanX.samePoint(nanAll));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void vecSameEpsilon() {
        // Affecte une valeur
        final double TEST_EPSILON = 0.000000999999; // Less than 1e-6
        // Appelle une méthode
        Vec v1 = new Vec(TEST_EPSILON, 0, 0);
        // Appelle une méthode
        Vec v2 = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec v3 = new Vec(TEST_EPSILON, -TEST_EPSILON, TEST_EPSILON);
        // Appelle une méthode
        Vec v4 = new Vec(0.001, 0, 0);
        // Appelle une méthode
        Vec v5 = v1.add(TEST_EPSILON);

        // Vectors with small differences should be considered the same under epsilon
        // Appelle une méthode
        assertTrue(v1.samePoint(v2, Vec.EPSILON));
        // Appelle une méthode
        assertTrue(v2.samePoint(v3, Vec.EPSILON));
        // Appelle une méthode
        assertTrue(v1.samePoint(v3, Vec.EPSILON));
        // Appelle une méthode
        assertTrue(v5.samePoint(v1, Vec.EPSILON));

        // Vectors with larger differences should not be considered the same
        // Appelle une méthode
        assertFalse(v1.samePoint(v4, Vec.EPSILON));

        // 0 epsilon should throw an exception
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> v5.samePoint(v5, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void toSectionRelativeCoordinate() {
        // Appelle une méthode
        assertEquals(8, CoordConversion.globalToSectionRelative(-40));
        // Appelle une méthode
        assertEquals(12, CoordConversion.globalToSectionRelative(-20));
        // Appelle une méthode
        assertEquals(0, CoordConversion.globalToSectionRelative(0));
        // Appelle une méthode
        assertEquals(5, CoordConversion.globalToSectionRelative(5));
        // Appelle une méthode
        assertEquals(15, CoordConversion.globalToSectionRelative(15));
        // Appelle une méthode
        assertEquals(0, CoordConversion.globalToSectionRelative(16));
        // Appelle une méthode
        assertEquals(4, CoordConversion.globalToSectionRelative(20));
        // Appelle une méthode
        assertEquals(0, CoordConversion.globalToSectionRelative(32));
        // Appelle une méthode
        assertEquals(1, CoordConversion.globalToSectionRelative(33));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockIndex() {
        // Test if the block index is correctly converted back and forth

        // Affecte une valeur
        List<Vec> tempEquals = List.of(
                // Zero vector with zero, positive and negative Y value
                // Instruction de code
                Vec.ZERO,
                // Instruction de code
                Vec.ZERO.withY(1),
                // Instruction de code
                Vec.ZERO.withY(-1),
                // One vector with positive and negative Y value
                // Instruction de code
                Vec.ONE,
                // Instruction de code
                Vec.ONE.withY(-1),
                // Vector with X/Z outside of chunk size
                // Crée un nouvel objet
                new Vec(Chunk.CHUNK_SIZE_X + 1, 20, Chunk.CHUNK_SIZE_Z + 1),
                // Crée un nouvel objet
                new Vec(Chunk.CHUNK_SIZE_X + 1, -20, Chunk.CHUNK_SIZE_Z + 1),
                // Vector with negative X/Z block pos
                // Crée un nouvel objet
                new Vec(-1, 20, -1),
                // Crée un nouvel objet
                new Vec(-1, -20, -1),
                // Check Y min and max value (23 bits, 2^23-1, -2^23+1)
                // Crée un nouvel objet
                new Vec(0, 8_388_607, 0),
                // Crée un nouvel objet
                new Vec(0, -8_388_607, 0)
        // Fin d'un bloc/d'une expression
        );

        // Boucle : répète un bloc
        for (Vec vec : tempEquals) {
            // Instruction de code
            assertEquals(CoordConversion.chunkBlockIndexGetGlobal(CoordConversion.chunkBlockIndex(vec.blockX(), vec.blockY(), vec.blockZ()),
                    // Appelle une méthode
                    vec.chunkX(), vec.chunkZ()), vec);
        // Fin d'un bloc/d'une expression
        }

        // Test if the block index does convert to wrong values due to overflow

        // Affecte une valeur
        List<Vec> tempNotEquals = List.of(
                // Above and below Y min and max value (> 2^23-1, < -2^23+1)
                // Integer overflows into the 24th bit which is not copied into block index,
                // so an error is expected here.
                // Crée un nouvel objet
                new Vec(0, 8_388_608, 0),
                // Crée un nouvel objet
                new Vec(0, -8_388_608, 0)
        // Fin d'un bloc/d'une expression
        );

        // Boucle : répète un bloc
        for (Vec vec : tempNotEquals) {
            // Instruction de code
            assertNotEquals(CoordConversion.chunkBlockIndexGetGlobal(CoordConversion.chunkBlockIndex(vec.blockX(), vec.blockY(), vec.blockZ()),
                    // Appelle une méthode
                    vec.chunkX(), vec.chunkZ()), vec);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockIndexDuplicate() {
        // Appelle une méthode
        LongSet temp = new LongOpenHashSet();

        // Boucle : répète un bloc
        for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
            // Boucle : répète un bloc
            for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                // Boucle : répète un bloc
                for (int y = -64; y < 364; y++) {
                    // Appelle une méthode
                    var vec = new Vec(x, y, z);
                    // Appelle une méthode
                    var index = CoordConversion.chunkBlockIndex(vec.blockX(), vec.blockY(), vec.blockZ());
                    // Appelle une méthode
                    assertTrue(temp.add(index), "Duplicate block index found: " + index + " " + vec);
                    // Appelle une méthode
                    assertEquals(CoordConversion.chunkBlockIndexGetGlobal(index, vec.chunkX(), vec.chunkZ()), vec);

                    // Appelle une méthode
                    assertEquals(CoordConversion.chunkBlockIndexGetX(index), x);
                    // Appelle une méthode
                    assertEquals(CoordConversion.chunkBlockIndexGetY(index), y);
                    // Appelle une méthode
                    assertEquals(CoordConversion.chunkBlockIndexGetZ(index), z);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockIndexZero() {
        // Appelle une méthode
        assertEquals(0, CoordConversion.chunkBlockIndex(0, 0, 0), "Bad default index for zero case! Bad sign bit?");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
