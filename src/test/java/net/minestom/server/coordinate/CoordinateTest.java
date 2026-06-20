// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
// Import of a required class
import it.unimi.dsi.fastutil.longs.LongSet;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CoordinateTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkIndex() {
        // Calls a method
        var index = CoordConversion.chunkIndex(2, 5);
        // Calls a method
        assertEquals(2, CoordConversion.chunkIndexGetX(index));
        // Calls a method
        assertEquals(5, CoordConversion.chunkIndexGetZ(index));

        // Calls a method
        index = CoordConversion.chunkIndex(-5, 25);
        // Calls a method
        assertEquals(-5, CoordConversion.chunkIndexGetX(index));
        // Calls a method
        assertEquals(25, CoordConversion.chunkIndexGetZ(index));

        // Calls a method
        index = CoordConversion.chunkIndex(Integer.MAX_VALUE, Integer.MIN_VALUE);
        // Calls a method
        assertEquals(Integer.MAX_VALUE, CoordConversion.chunkIndexGetX(index));
        // Calls a method
        assertEquals(Integer.MIN_VALUE, CoordConversion.chunkIndexGetZ(index));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkCoordinate() {
        // Calls a method
        assertEquals(0, CoordConversion.globalToChunk(15));
        // Calls a method
        assertEquals(1, CoordConversion.globalToChunk(16));
        // Calls a method
        assertEquals(-1, CoordConversion.globalToChunk(-16));
        // Calls a method
        assertEquals(3, CoordConversion.globalToChunk(48));

        // Calls a method
        assertEquals(4, CoordConversion.globalToChunk(65));
        // Calls a method
        assertEquals(4, CoordConversion.globalToChunk(64));
        // Calls a method
        assertEquals(3, CoordConversion.globalToChunk(63));
        // Calls a method
        assertEquals(-2, CoordConversion.globalToChunk(-25));
        // Calls a method
        assertEquals(23, CoordConversion.globalToChunk(380));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkCount() {
        // Calls a method
        assertEquals(289, ChunkRange.chunksCount(8));
        // Calls a method
        assertEquals(169, ChunkRange.chunksCount(6));
        // Calls a method
        assertEquals(121, ChunkRange.chunksCount(5));
        // Calls a method
        assertEquals(9, ChunkRange.chunksCount(1));
        // Calls a method
        assertEquals(1, ChunkRange.chunksCount(0));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> ChunkRange.chunksCount(-1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vecAddition() {
        // Assigns a value
        Vec temp = Vec.ZERO;
        // Calls a method
        assertEquals(0, temp.x());
        // Calls a method
        assertEquals(0, temp.y());
        // Calls a method
        assertEquals(0, temp.z());

        // Calls a method
        temp = temp.add(1);
        // Calls a method
        assertEquals(1, temp.x());
        // Calls a method
        assertEquals(1, temp.y());
        // Calls a method
        assertEquals(1, temp.z());

        // Calls a method
        temp = temp.add(1, 0, 0);
        // Calls a method
        assertEquals(2, temp.x());
        // Calls a method
        assertEquals(1, temp.y());
        // Calls a method
        assertEquals(1, temp.z());

        // Calls a method
        temp = temp.add(0, 1, 0);
        // Calls a method
        assertEquals(2, temp.x());
        // Calls a method
        assertEquals(2, temp.y());
        // Calls a method
        assertEquals(1, temp.z());

        // Calls a method
        temp = temp.add(0, 0, 1);
        // Calls a method
        assertEquals(2, temp.x());
        // Calls a method
        assertEquals(2, temp.y());
        // Calls a method
        assertEquals(2, temp.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vecWith() {
        // Calls a method
        Vec temp = Vec.ZERO.withX(1);
        // Calls a method
        assertEquals(1, temp.x());
        // Calls a method
        assertEquals(0, temp.y());
        // Calls a method
        assertEquals(0, temp.z());

        // Calls a method
        temp = temp.withX(x -> x * 2 + 1);
        // Calls a method
        assertEquals(3, temp.x());
        // Calls a method
        assertEquals(0, temp.y());
        // Calls a method
        assertEquals(0, temp.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vecNegCompare() {
        // Calls a method
        assertFalse(Vec.ZERO.samePoint(Vec.ONE.neg()));
        // Calls a method
        assertTrue(Vec.ZERO.samePoint(Vec.ZERO.neg()));
        // Calls a method
        assertTrue(Vec.ZERO.samePoint(new Vec(-0, 0, 0)));
        // Calls a method
        assertTrue(Vec.ZERO.samePoint(new Vec(-0, -0, -0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vecNanCompare() {
        // Calls a method
        Vec nanX = new Vec(Double.NaN, 0, 0);
        // Calls a method
        Vec nanY = new Vec(0, Double.NaN, 0);
        // Calls a method
        Vec nanZ = new Vec(0, 0, Double.NaN);
        // Calls a method
        Vec nanAll = new Vec(Double.NaN, Double.NaN, Double.NaN);
        // Assigns a value
        Vec normal = Vec.ZERO;

        // NaN is not equal to itself
        // Calls a method
        assertFalse(nanX.samePoint(nanX));
        // Calls a method
        assertFalse(nanY.samePoint(nanY));
        // Calls a method
        assertFalse(nanZ.samePoint(nanZ));
        // Calls a method
        assertFalse(nanAll.samePoint(nanAll));

        // NaN vectors are not equal to normal vectors
        // Calls a method
        assertFalse(nanX.samePoint(normal));
        // Calls a method
        assertFalse(nanY.samePoint(normal));
        // Calls a method
        assertFalse(nanZ.samePoint(normal));
        // Calls a method
        assertFalse(nanAll.samePoint(normal));

        // Different NaN vectors are not equal to each other
        // Calls a method
        assertFalse(nanX.samePoint(nanY));
        // Calls a method
        assertFalse(nanX.samePoint(nanZ));
        // Calls a method
        assertFalse(nanY.samePoint(nanZ));
        // Calls a method
        assertFalse(nanX.samePoint(nanAll));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void vecSameEpsilon() {
        // Assigns a value
        final double TEST_EPSILON = 0.000000999999; // Less than 1e-6
        // Calls a method
        Vec v1 = new Vec(TEST_EPSILON, 0, 0);
        // Calls a method
        Vec v2 = new Vec(0, 0, 0);
        // Calls a method
        Vec v3 = new Vec(TEST_EPSILON, -TEST_EPSILON, TEST_EPSILON);
        // Calls a method
        Vec v4 = new Vec(0.001, 0, 0);
        // Calls a method
        Vec v5 = v1.add(TEST_EPSILON);

        // Vectors with small differences should be considered the same under epsilon
        // Calls a method
        assertTrue(v1.samePoint(v2, Vec.EPSILON));
        // Calls a method
        assertTrue(v2.samePoint(v3, Vec.EPSILON));
        // Calls a method
        assertTrue(v1.samePoint(v3, Vec.EPSILON));
        // Calls a method
        assertTrue(v5.samePoint(v1, Vec.EPSILON));

        // Vectors with larger differences should not be considered the same
        // Calls a method
        assertFalse(v1.samePoint(v4, Vec.EPSILON));

        // 0 epsilon should throw an exception
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> v5.samePoint(v5, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void toSectionRelativeCoordinate() {
        // Calls a method
        assertEquals(8, CoordConversion.globalToSectionRelative(-40));
        // Calls a method
        assertEquals(12, CoordConversion.globalToSectionRelative(-20));
        // Calls a method
        assertEquals(0, CoordConversion.globalToSectionRelative(0));
        // Calls a method
        assertEquals(5, CoordConversion.globalToSectionRelative(5));
        // Calls a method
        assertEquals(15, CoordConversion.globalToSectionRelative(15));
        // Calls a method
        assertEquals(0, CoordConversion.globalToSectionRelative(16));
        // Calls a method
        assertEquals(4, CoordConversion.globalToSectionRelative(20));
        // Calls a method
        assertEquals(0, CoordConversion.globalToSectionRelative(32));
        // Calls a method
        assertEquals(1, CoordConversion.globalToSectionRelative(33));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockIndex() {
        // Test if the block index is correctly converted back and forth

        // Assigns a value
        List<Vec> tempEquals = List.of(
                // Zero vector with zero, positive and negative Y value
                // Code statement
                Vec.ZERO,
                // Code statement
                Vec.ZERO.withY(1),
                // Code statement
                Vec.ZERO.withY(-1),
                // One vector with positive and negative Y value
                // Code statement
                Vec.ONE,
                // Code statement
                Vec.ONE.withY(-1),
                // Vector with X/Z outside of chunk size
                // Creates a new object
                new Vec(Chunk.CHUNK_SIZE_X + 1, 20, Chunk.CHUNK_SIZE_Z + 1),
                // Creates a new object
                new Vec(Chunk.CHUNK_SIZE_X + 1, -20, Chunk.CHUNK_SIZE_Z + 1),
                // Vector with negative X/Z block pos
                // Creates a new object
                new Vec(-1, 20, -1),
                // Creates a new object
                new Vec(-1, -20, -1),
                // Check Y min and max value (23 bits, 2^23-1, -2^23+1)
                // Creates a new object
                new Vec(0, 8_388_607, 0),
                // Creates a new object
                new Vec(0, -8_388_607, 0)
        // End of a block/expression
        );

        // Loop: repeats a block
        for (Vec vec : tempEquals) {
            // Code statement
            assertEquals(CoordConversion.chunkBlockIndexGetGlobal(CoordConversion.chunkBlockIndex(vec.blockX(), vec.blockY(), vec.blockZ()),
                    // Calls a method
                    vec.chunkX(), vec.chunkZ()), vec);
        // End of a block/expression
        }

        // Test if the block index does convert to wrong values due to overflow

        // Assigns a value
        List<Vec> tempNotEquals = List.of(
                // Above and below Y min and max value (> 2^23-1, < -2^23+1)
                // Integer overflows into the 24th bit which is not copied into block index,
                // so an error is expected here.
                // Creates a new object
                new Vec(0, 8_388_608, 0),
                // Creates a new object
                new Vec(0, -8_388_608, 0)
        // End of a block/expression
        );

        // Loop: repeats a block
        for (Vec vec : tempNotEquals) {
            // Code statement
            assertNotEquals(CoordConversion.chunkBlockIndexGetGlobal(CoordConversion.chunkBlockIndex(vec.blockX(), vec.blockY(), vec.blockZ()),
                    // Calls a method
                    vec.chunkX(), vec.chunkZ()), vec);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockIndexDuplicate() {
        // Calls a method
        LongSet temp = new LongOpenHashSet();

        // Loop: repeats a block
        for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
            // Loop: repeats a block
            for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                // Loop: repeats a block
                for (int y = -64; y < 364; y++) {
                    // Calls a method
                    var vec = new Vec(x, y, z);
                    // Calls a method
                    var index = CoordConversion.chunkBlockIndex(vec.blockX(), vec.blockY(), vec.blockZ());
                    // Calls a method
                    assertTrue(temp.add(index), "Duplicate block index found: " + index + " " + vec);
                    // Calls a method
                    assertEquals(CoordConversion.chunkBlockIndexGetGlobal(index, vec.chunkX(), vec.chunkZ()), vec);

                    // Calls a method
                    assertEquals(CoordConversion.chunkBlockIndexGetX(index), x);
                    // Calls a method
                    assertEquals(CoordConversion.chunkBlockIndexGetY(index), y);
                    // Calls a method
                    assertEquals(CoordConversion.chunkBlockIndexGetZ(index), z);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockIndexZero() {
        // Calls a method
        assertEquals(0, CoordConversion.chunkBlockIndex(0, 0, 0), "Bad default index for zero case! Bad sign bit?");
    // End of a block/expression
    }
// End of a block/expression
}
