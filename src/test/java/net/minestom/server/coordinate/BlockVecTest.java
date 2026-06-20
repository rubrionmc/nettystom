// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Random;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class BlockVecTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testConstructors() {
        // Test primary constructor
        // Calls a method
        BlockVec vec1 = new BlockVec(5, 10, 15);
        // Calls a method
        assertEquals(5, vec1.blockX());
        // Calls a method
        assertEquals(10, vec1.blockY());
        // Calls a method
        assertEquals(15, vec1.blockZ());

        // Test double constructor (floors values)
        // Calls a method
        BlockVec vec2 = new BlockVec(5.7, 10.3, 15.9);
        // Calls a method
        assertEquals(5, vec2.blockX());
        // Calls a method
        assertEquals(10, vec2.blockY());
        // Calls a method
        assertEquals(15, vec2.blockZ());

        // Test negative double values
        // Calls a method
        BlockVec vec3 = new BlockVec(-5.7, -10.3, -15.9);
        // Calls a method
        assertEquals(-6, vec3.blockX());
        // Calls a method
        assertEquals(-11, vec3.blockY());
        // Calls a method
        assertEquals(-16, vec3.blockZ());

        // Test single value constructor (int)
        // Calls a method
        BlockVec vec4 = new BlockVec(7);
        // Calls a method
        assertEquals(7, vec4.blockX());
        // Calls a method
        assertEquals(7, vec4.blockY());
        // Calls a method
        assertEquals(7, vec4.blockZ());

        // Test single value constructor (double)
        // Calls a method
        BlockVec vec5 = new BlockVec(7.5);
        // Calls a method
        assertEquals(7, vec5.blockX());
        // Calls a method
        assertEquals(7, vec5.blockY());
        // Calls a method
        assertEquals(7, vec5.blockZ());

        // Test double value constructor (int)
        // Calls a method
        BlockVec vec6 = new BlockVec(6, 7);
        // Calls a method
        assertEquals(6, vec6.blockX());
        // Calls a method
        assertEquals(0, vec6.blockY());
        // Calls a method
        assertEquals(7, vec6.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testConstants() {
        // Calls a method
        assertEquals(0, BlockVec.ZERO.blockX());
        // Calls a method
        assertEquals(0, BlockVec.ZERO.blockY());
        // Calls a method
        assertEquals(0, BlockVec.ZERO.blockZ());

        // Calls a method
        assertEquals(1, BlockVec.ONE.blockX());
        // Calls a method
        assertEquals(1, BlockVec.ONE.blockY());
        // Calls a method
        assertEquals(1, BlockVec.ONE.blockZ());

        // Calls a method
        assertEquals(Point.SECTION_SIZE, BlockVec.SECTION.blockX());
        // Calls a method
        assertEquals(Point.SECTION_SIZE, BlockVec.SECTION.blockY());
        // Calls a method
        assertEquals(Point.SECTION_SIZE, BlockVec.SECTION.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWithBlock() {
        // Calls a method
        BlockVec base = new BlockVec(10, 20, 30);

        // Test withBlockX
        // Calls a method
        BlockVec modified = base.withBlockX(15);
        // Calls a method
        assertEquals(15, modified.blockX());
        // Calls a method
        assertEquals(20, modified.blockY());
        // Calls a method
        assertEquals(30, modified.blockZ());

        // Test withBlockX with operator
        // Calls a method
        modified = base.withBlockX(x -> x * 2);
        // Calls a method
        assertEquals(20, modified.blockX());
        // Calls a method
        assertEquals(20, modified.blockY());
        // Calls a method
        assertEquals(30, modified.blockZ());

        // Test withBlockY
        // Calls a method
        modified = base.withBlockY(25);
        // Calls a method
        assertEquals(10, modified.blockX());
        // Calls a method
        assertEquals(25, modified.blockY());
        // Calls a method
        assertEquals(30, modified.blockZ());

        // Test withBlockY with operator
        // Calls a method
        modified = base.withBlockY(y -> y + 5);
        // Calls a method
        assertEquals(10, modified.blockX());
        // Calls a method
        assertEquals(25, modified.blockY());
        // Calls a method
        assertEquals(30, modified.blockZ());

        // Test withBlockZ
        // Calls a method
        modified = base.withBlockZ(35);
        // Calls a method
        assertEquals(10, modified.blockX());
        // Calls a method
        assertEquals(20, modified.blockY());
        // Calls a method
        assertEquals(35, modified.blockZ());

        // Test withBlockZ with operator
        // Calls a method
        modified = base.withBlockZ(z -> z - 10);
        // Calls a method
        assertEquals(10, modified.blockX());
        // Calls a method
        assertEquals(20, modified.blockY());
        // Calls a method
        assertEquals(20, modified.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testIntegerArithmetic() {
        // Calls a method
        BlockVec v1 = new BlockVec(10, 20, 30);
        // Calls a method
        BlockVec v2 = new BlockVec(5, 8, 12);

        // Test add with integers
        // Calls a method
        BlockVec result = v1.add(5, 10, 15);
        // Calls a method
        assertEquals(15, result.blockX());
        // Calls a method
        assertEquals(30, result.blockY());
        // Calls a method
        assertEquals(45, result.blockZ());

        // Test add with BlockVec
        // Calls a method
        result = v1.add(v2);
        // Calls a method
        assertEquals(15, result.blockX());
        // Calls a method
        assertEquals(28, result.blockY());
        // Calls a method
        assertEquals(42, result.blockZ());

        // Test add with single value
        // Calls a method
        result = v1.add(3);
        // Calls a method
        assertEquals(13, result.blockX());
        // Calls a method
        assertEquals(23, result.blockY());
        // Calls a method
        assertEquals(33, result.blockZ());

        // Test sub with integers
        // Calls a method
        result = v1.sub(5, 10, 15);
        // Calls a method
        assertEquals(5, result.blockX());
        // Calls a method
        assertEquals(10, result.blockY());
        // Calls a method
        assertEquals(15, result.blockZ());

        // Test sub with BlockVec
        // Calls a method
        result = v1.sub(v2);
        // Calls a method
        assertEquals(5, result.blockX());
        // Calls a method
        assertEquals(12, result.blockY());
        // Calls a method
        assertEquals(18, result.blockZ());

        // Test sub with single value
        // Calls a method
        result = v1.sub(3);
        // Calls a method
        assertEquals(7, result.blockX());
        // Calls a method
        assertEquals(17, result.blockY());
        // Calls a method
        assertEquals(27, result.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMultiplicationDivision() {
        // Calls a method
        BlockVec base = new BlockVec(10, 20, 30);

        // Test mul with integers
        // Calls a method
        BlockVec result = base.mul(2, 3, 4);
        // Calls a method
        assertEquals(20, result.blockX());
        // Calls a method
        assertEquals(60, result.blockY());
        // Calls a method
        assertEquals(120, result.blockZ());

        // Test mul with BlockVec
        // Calls a method
        BlockVec v2 = new BlockVec(2, 3, 4);
        // Calls a method
        result = base.mul(v2);
        // Calls a method
        assertEquals(20, result.blockX());
        // Calls a method
        assertEquals(60, result.blockY());
        // Calls a method
        assertEquals(120, result.blockZ());

        // Test mul with single value
        // Calls a method
        result = base.mul(2);
        // Calls a method
        assertEquals(20, result.blockX());
        // Calls a method
        assertEquals(40, result.blockY());
        // Calls a method
        assertEquals(60, result.blockZ());

        // Test div with integers
        // Calls a method
        result = base.div(2, 4, 5);
        // Calls a method
        assertEquals(5, result.blockX());
        // Calls a method
        assertEquals(5, result.blockY());
        // Calls a method
        assertEquals(6, result.blockZ());

        // Test div with BlockVec
        // Calls a method
        result = base.div(new BlockVec(2, 4, 5));
        // Calls a method
        assertEquals(5, result.blockX());
        // Calls a method
        assertEquals(5, result.blockY());
        // Calls a method
        assertEquals(6, result.blockZ());

        // Test div with single value
        // Calls a method
        result = base.div(2);
        // Calls a method
        assertEquals(5, result.blockX());
        // Calls a method
        assertEquals(10, result.blockY());
        // Calls a method
        assertEquals(15, result.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMinMax() {
        // Calls a method
        BlockVec v1 = new BlockVec(10, 20, 30);
        // Calls a method
        BlockVec v2 = new BlockVec(15, 5, 25);

        // Test min with BlockVec
        // Calls a method
        BlockVec result = v1.min(v2);
        // Calls a method
        assertEquals(10, result.blockX());
        // Calls a method
        assertEquals(5, result.blockY());
        // Calls a method
        assertEquals(25, result.blockZ());

        // Test min with coordinates
        // Calls a method
        result = v1.min(15, 5, 25);
        // Calls a method
        assertEquals(10, result.blockX());
        // Calls a method
        assertEquals(5, result.blockY());
        // Calls a method
        assertEquals(25, result.blockZ());

        // Test min with single value
        // Calls a method
        result = v1.min(15);
        // Calls a method
        assertEquals(10, result.blockX());
        // Calls a method
        assertEquals(15, result.blockY());
        // Calls a method
        assertEquals(15, result.blockZ());

        // Test max with BlockVec
        // Calls a method
        result = v1.max(v2);
        // Calls a method
        assertEquals(15, result.blockX());
        // Calls a method
        assertEquals(20, result.blockY());
        // Calls a method
        assertEquals(30, result.blockZ());

        // Test max with coordinates
        // Calls a method
        result = v1.max(15, 5, 25);
        // Calls a method
        assertEquals(15, result.blockX());
        // Calls a method
        assertEquals(20, result.blockY());
        // Calls a method
        assertEquals(30, result.blockZ());

        // Test max with single value
        // Calls a method
        result = v1.max(15);
        // Calls a method
        assertEquals(15, result.blockX());
        // Calls a method
        assertEquals(20, result.blockY());
        // Calls a method
        assertEquals(30, result.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNegAbs() {
        // Calls a method
        BlockVec positive = new BlockVec(10, 20, 30);
        // Calls a method
        BlockVec negative = new BlockVec(-10, -20, -30);
        // Calls a method
        BlockVec mixed = new BlockVec(-10, 20, -30);

        // Test neg
        // Calls a method
        BlockVec result = positive.neg();
        // Calls a method
        assertEquals(-10, result.blockX());
        // Calls a method
        assertEquals(-20, result.blockY());
        // Calls a method
        assertEquals(-30, result.blockZ());

        // Calls a method
        result = negative.neg();
        // Calls a method
        assertEquals(10, result.blockX());
        // Calls a method
        assertEquals(20, result.blockY());
        // Calls a method
        assertEquals(30, result.blockZ());

        // Test abs
        // Calls a method
        result = negative.abs();
        // Calls a method
        assertEquals(10, result.blockX());
        // Calls a method
        assertEquals(20, result.blockY());
        // Calls a method
        assertEquals(30, result.blockZ());

        // Calls a method
        result = mixed.abs();
        // Calls a method
        assertEquals(10, result.blockX());
        // Calls a method
        assertEquals(20, result.blockY());
        // Calls a method
        assertEquals(30, result.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCross() {
        // Calls a method
        BlockVec v1 = new BlockVec(1, 0, 0);
        // Calls a method
        BlockVec v2 = new BlockVec(0, 1, 0);

        // Cross product of unit vectors
        // Calls a method
        BlockVec result = v1.cross(v2);
        // Calls a method
        assertEquals(0, result.blockX());
        // Calls a method
        assertEquals(0, result.blockY());
        // Calls a method
        assertEquals(1, result.blockZ());

        // Reverse order
        // Calls a method
        result = v2.cross(v1);
        // Calls a method
        assertEquals(0, result.blockX());
        // Calls a method
        assertEquals(0, result.blockY());
        // Calls a method
        assertEquals(-1, result.blockZ());

        // More complex case
        // Calls a method
        BlockVec v3 = new BlockVec(2, 3, 4);
        // Calls a method
        BlockVec v4 = new BlockVec(5, 6, 7);
        // Calls a method
        result = v3.cross(v4);
        // Calls a method
        assertEquals(-3, result.blockX());
        // Calls a method
        assertEquals(6, result.blockY());
        // Calls a method
        assertEquals(-3, result.blockZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSamePoint() {
        // Calls a method
        BlockVec v1 = new BlockVec(10, 20, 30);
        // Calls a method
        BlockVec v2 = new BlockVec(10, 20, 30);
        // Calls a method
        BlockVec v3 = new BlockVec(10, 20, 31);

        // Test with BlockVec
        // Calls a method
        assertTrue(v1.samePoint(v2));
        // Calls a method
        assertFalse(v1.samePoint(v3));

        // Test with coordinates
        // Calls a method
        assertTrue(v1.samePoint(10, 20, 30));
        // Calls a method
        assertFalse(v1.samePoint(10, 20, 31));
        // Calls a method
        assertFalse(v1.samePoint(11, 20, 30));
        // Calls a method
        assertFalse(v1.samePoint(10, 21, 30));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRelative() {
        // Calls a method
        BlockVec base = new BlockVec(10, 20, 30);

        // Calls a method
        assertEquals(new BlockVec(10, 21, 30), base.relative(BlockFace.TOP));
        // Calls a method
        assertEquals(new BlockVec(10, 19, 30), base.relative(BlockFace.BOTTOM));
        // Calls a method
        assertEquals(new BlockVec(11, 20, 30), base.relative(BlockFace.EAST));
        // Calls a method
        assertEquals(new BlockVec(9, 20, 30), base.relative(BlockFace.WEST));
        // Calls a method
        assertEquals(new BlockVec(10, 20, 31), base.relative(BlockFace.SOUTH));
        // Calls a method
        assertEquals(new BlockVec(10, 20, 29), base.relative(BlockFace.NORTH));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testApply() {
        // Calls a method
        BlockVec base = new BlockVec(10, 20, 30);

        // Test operator that doubles all values
        // Calls a method
        BlockVec result = base.apply((x, y, z) -> new BlockVec(x * 2, y * 2, z * 2));
        // Calls a method
        assertEquals(20, result.blockX());
        // Calls a method
        assertEquals(40, result.blockY());
        // Calls a method
        assertEquals(60, result.blockZ());

        // Test operator that creates constant
        // Calls a method
        result = base.apply((_, _, _) -> BlockVec.ZERO);
        // Calls a method
        assertEquals(BlockVec.ZERO, result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAsBlockVec() {
        // Calls a method
        Point vec = new BlockVec(5, 10, 15);
        // Calls a method
        assertSame(vec, vec.asBlockVec());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDoubleOperations() {
        // Calls a method
        BlockVec base = new BlockVec(10, 20, 30);

        // Test that double operations return Vec
        // Calls a method
        Vec result = base.add(1.5, 2.5, 3.5);
        // Calls a method
        assertInstanceOf(Vec.class, result);
        // Calls a method
        assertEquals(11.5, result.x(), 0.001);
        // Calls a method
        assertEquals(22.5, result.y(), 0.001);
        // Calls a method
        assertEquals(33.5, result.z(), 0.001);

        // Test withX returns Vec
        // Calls a method
        result = base.withX(15.5);
        // Calls a method
        assertInstanceOf(Vec.class, result);
        // Calls a method
        assertEquals(15.5, result.x(), 0.001);

        // Test normalize returns Vec
        // Calls a method
        result = base.normalize();
        // Calls a method
        assertInstanceOf(Vec.class, result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRandomBlockVectors() {
        // Calls a method
        Random random = new Random(12345L);

        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            int x = random.nextInt(2000) - 1000;
            // Calls a method
            int y = random.nextInt(2000) - 1000;
            // Calls a method
            int z = random.nextInt(2000) - 1000;

            // Calls a method
            BlockVec vec = new BlockVec(x, y, z);
            // Calls a method
            assertEquals(x, vec.blockX());
            // Calls a method
            assertEquals(y, vec.blockY());
            // Calls a method
            assertEquals(z, vec.blockZ());

            // Test that operations preserve immutability
            // Assigns a value
            BlockVec original = vec;
            // Calls a method
            BlockVec modified = vec.add(1, 2, 3);
            // Calls a method
            assertNotEquals(original, modified);
            // Calls a method
            assertEquals(x, original.blockX());
            // Calls a method
            assertEquals(x + 1, modified.blockX());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCoordinateConversions() {
        // Calls a method
        BlockVec vec = new BlockVec(32, 64, 96);

        // Test x/y/z methods return double values
        // Calls a method
        assertEquals(32.0, vec.x());
        // Calls a method
        assertEquals(64.0, vec.y());
        // Calls a method
        assertEquals(96.0, vec.z());

        // Test section coordinates
        // Calls a method
        assertEquals(2, vec.sectionX());
        // Calls a method
        assertEquals(4, vec.sectionY());
        // Calls a method
        assertEquals(6, vec.sectionZ());

        // Test chunk coordinates
        // Calls a method
        assertEquals(2, vec.chunkX());
        // Calls a method
        assertEquals(6, vec.chunkZ());
    // End of a block/expression
    }
// End of a block/expression
}

