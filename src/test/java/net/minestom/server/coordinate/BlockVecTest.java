// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class BlockVecTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testConstructors() {
        // Test primary constructor
        // Appelle une méthode
        BlockVec vec1 = new BlockVec(5, 10, 15);
        // Appelle une méthode
        assertEquals(5, vec1.blockX());
        // Appelle une méthode
        assertEquals(10, vec1.blockY());
        // Appelle une méthode
        assertEquals(15, vec1.blockZ());

        // Test double constructor (floors values)
        // Appelle une méthode
        BlockVec vec2 = new BlockVec(5.7, 10.3, 15.9);
        // Appelle une méthode
        assertEquals(5, vec2.blockX());
        // Appelle une méthode
        assertEquals(10, vec2.blockY());
        // Appelle une méthode
        assertEquals(15, vec2.blockZ());

        // Test negative double values
        // Appelle une méthode
        BlockVec vec3 = new BlockVec(-5.7, -10.3, -15.9);
        // Appelle une méthode
        assertEquals(-6, vec3.blockX());
        // Appelle une méthode
        assertEquals(-11, vec3.blockY());
        // Appelle une méthode
        assertEquals(-16, vec3.blockZ());

        // Test single value constructor (int)
        // Appelle une méthode
        BlockVec vec4 = new BlockVec(7);
        // Appelle une méthode
        assertEquals(7, vec4.blockX());
        // Appelle une méthode
        assertEquals(7, vec4.blockY());
        // Appelle une méthode
        assertEquals(7, vec4.blockZ());

        // Test single value constructor (double)
        // Appelle une méthode
        BlockVec vec5 = new BlockVec(7.5);
        // Appelle une méthode
        assertEquals(7, vec5.blockX());
        // Appelle une méthode
        assertEquals(7, vec5.blockY());
        // Appelle une méthode
        assertEquals(7, vec5.blockZ());

        // Test double value constructor (int)
        // Appelle une méthode
        BlockVec vec6 = new BlockVec(6, 7);
        // Appelle une méthode
        assertEquals(6, vec6.blockX());
        // Appelle une méthode
        assertEquals(0, vec6.blockY());
        // Appelle une méthode
        assertEquals(7, vec6.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testConstants() {
        // Appelle une méthode
        assertEquals(0, BlockVec.ZERO.blockX());
        // Appelle une méthode
        assertEquals(0, BlockVec.ZERO.blockY());
        // Appelle une méthode
        assertEquals(0, BlockVec.ZERO.blockZ());

        // Appelle une méthode
        assertEquals(1, BlockVec.ONE.blockX());
        // Appelle une méthode
        assertEquals(1, BlockVec.ONE.blockY());
        // Appelle une méthode
        assertEquals(1, BlockVec.ONE.blockZ());

        // Appelle une méthode
        assertEquals(Point.SECTION_SIZE, BlockVec.SECTION.blockX());
        // Appelle une méthode
        assertEquals(Point.SECTION_SIZE, BlockVec.SECTION.blockY());
        // Appelle une méthode
        assertEquals(Point.SECTION_SIZE, BlockVec.SECTION.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWithBlock() {
        // Appelle une méthode
        BlockVec base = new BlockVec(10, 20, 30);

        // Test withBlockX
        // Appelle une méthode
        BlockVec modified = base.withBlockX(15);
        // Appelle une méthode
        assertEquals(15, modified.blockX());
        // Appelle une méthode
        assertEquals(20, modified.blockY());
        // Appelle une méthode
        assertEquals(30, modified.blockZ());

        // Test withBlockX with operator
        // Appelle une méthode
        modified = base.withBlockX(x -> x * 2);
        // Appelle une méthode
        assertEquals(20, modified.blockX());
        // Appelle une méthode
        assertEquals(20, modified.blockY());
        // Appelle une méthode
        assertEquals(30, modified.blockZ());

        // Test withBlockY
        // Appelle une méthode
        modified = base.withBlockY(25);
        // Appelle une méthode
        assertEquals(10, modified.blockX());
        // Appelle une méthode
        assertEquals(25, modified.blockY());
        // Appelle une méthode
        assertEquals(30, modified.blockZ());

        // Test withBlockY with operator
        // Appelle une méthode
        modified = base.withBlockY(y -> y + 5);
        // Appelle une méthode
        assertEquals(10, modified.blockX());
        // Appelle une méthode
        assertEquals(25, modified.blockY());
        // Appelle une méthode
        assertEquals(30, modified.blockZ());

        // Test withBlockZ
        // Appelle une méthode
        modified = base.withBlockZ(35);
        // Appelle une méthode
        assertEquals(10, modified.blockX());
        // Appelle une méthode
        assertEquals(20, modified.blockY());
        // Appelle une méthode
        assertEquals(35, modified.blockZ());

        // Test withBlockZ with operator
        // Appelle une méthode
        modified = base.withBlockZ(z -> z - 10);
        // Appelle une méthode
        assertEquals(10, modified.blockX());
        // Appelle une méthode
        assertEquals(20, modified.blockY());
        // Appelle une méthode
        assertEquals(20, modified.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testIntegerArithmetic() {
        // Appelle une méthode
        BlockVec v1 = new BlockVec(10, 20, 30);
        // Appelle une méthode
        BlockVec v2 = new BlockVec(5, 8, 12);

        // Test add with integers
        // Appelle une méthode
        BlockVec result = v1.add(5, 10, 15);
        // Appelle une méthode
        assertEquals(15, result.blockX());
        // Appelle une méthode
        assertEquals(30, result.blockY());
        // Appelle une méthode
        assertEquals(45, result.blockZ());

        // Test add with BlockVec
        // Appelle une méthode
        result = v1.add(v2);
        // Appelle une méthode
        assertEquals(15, result.blockX());
        // Appelle une méthode
        assertEquals(28, result.blockY());
        // Appelle une méthode
        assertEquals(42, result.blockZ());

        // Test add with single value
        // Appelle une méthode
        result = v1.add(3);
        // Appelle une méthode
        assertEquals(13, result.blockX());
        // Appelle une méthode
        assertEquals(23, result.blockY());
        // Appelle une méthode
        assertEquals(33, result.blockZ());

        // Test sub with integers
        // Appelle une méthode
        result = v1.sub(5, 10, 15);
        // Appelle une méthode
        assertEquals(5, result.blockX());
        // Appelle une méthode
        assertEquals(10, result.blockY());
        // Appelle une méthode
        assertEquals(15, result.blockZ());

        // Test sub with BlockVec
        // Appelle une méthode
        result = v1.sub(v2);
        // Appelle une méthode
        assertEquals(5, result.blockX());
        // Appelle une méthode
        assertEquals(12, result.blockY());
        // Appelle une méthode
        assertEquals(18, result.blockZ());

        // Test sub with single value
        // Appelle une méthode
        result = v1.sub(3);
        // Appelle une méthode
        assertEquals(7, result.blockX());
        // Appelle une méthode
        assertEquals(17, result.blockY());
        // Appelle une méthode
        assertEquals(27, result.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMultiplicationDivision() {
        // Appelle une méthode
        BlockVec base = new BlockVec(10, 20, 30);

        // Test mul with integers
        // Appelle une méthode
        BlockVec result = base.mul(2, 3, 4);
        // Appelle une méthode
        assertEquals(20, result.blockX());
        // Appelle une méthode
        assertEquals(60, result.blockY());
        // Appelle une méthode
        assertEquals(120, result.blockZ());

        // Test mul with BlockVec
        // Appelle une méthode
        BlockVec v2 = new BlockVec(2, 3, 4);
        // Appelle une méthode
        result = base.mul(v2);
        // Appelle une méthode
        assertEquals(20, result.blockX());
        // Appelle une méthode
        assertEquals(60, result.blockY());
        // Appelle une méthode
        assertEquals(120, result.blockZ());

        // Test mul with single value
        // Appelle une méthode
        result = base.mul(2);
        // Appelle une méthode
        assertEquals(20, result.blockX());
        // Appelle une méthode
        assertEquals(40, result.blockY());
        // Appelle une méthode
        assertEquals(60, result.blockZ());

        // Test div with integers
        // Appelle une méthode
        result = base.div(2, 4, 5);
        // Appelle une méthode
        assertEquals(5, result.blockX());
        // Appelle une méthode
        assertEquals(5, result.blockY());
        // Appelle une méthode
        assertEquals(6, result.blockZ());

        // Test div with BlockVec
        // Appelle une méthode
        result = base.div(new BlockVec(2, 4, 5));
        // Appelle une méthode
        assertEquals(5, result.blockX());
        // Appelle une méthode
        assertEquals(5, result.blockY());
        // Appelle une méthode
        assertEquals(6, result.blockZ());

        // Test div with single value
        // Appelle une méthode
        result = base.div(2);
        // Appelle une méthode
        assertEquals(5, result.blockX());
        // Appelle une méthode
        assertEquals(10, result.blockY());
        // Appelle une méthode
        assertEquals(15, result.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMinMax() {
        // Appelle une méthode
        BlockVec v1 = new BlockVec(10, 20, 30);
        // Appelle une méthode
        BlockVec v2 = new BlockVec(15, 5, 25);

        // Test min with BlockVec
        // Appelle une méthode
        BlockVec result = v1.min(v2);
        // Appelle une méthode
        assertEquals(10, result.blockX());
        // Appelle une méthode
        assertEquals(5, result.blockY());
        // Appelle une méthode
        assertEquals(25, result.blockZ());

        // Test min with coordinates
        // Appelle une méthode
        result = v1.min(15, 5, 25);
        // Appelle une méthode
        assertEquals(10, result.blockX());
        // Appelle une méthode
        assertEquals(5, result.blockY());
        // Appelle une méthode
        assertEquals(25, result.blockZ());

        // Test min with single value
        // Appelle une méthode
        result = v1.min(15);
        // Appelle une méthode
        assertEquals(10, result.blockX());
        // Appelle une méthode
        assertEquals(15, result.blockY());
        // Appelle une méthode
        assertEquals(15, result.blockZ());

        // Test max with BlockVec
        // Appelle une méthode
        result = v1.max(v2);
        // Appelle une méthode
        assertEquals(15, result.blockX());
        // Appelle une méthode
        assertEquals(20, result.blockY());
        // Appelle une méthode
        assertEquals(30, result.blockZ());

        // Test max with coordinates
        // Appelle une méthode
        result = v1.max(15, 5, 25);
        // Appelle une méthode
        assertEquals(15, result.blockX());
        // Appelle une méthode
        assertEquals(20, result.blockY());
        // Appelle une méthode
        assertEquals(30, result.blockZ());

        // Test max with single value
        // Appelle une méthode
        result = v1.max(15);
        // Appelle une méthode
        assertEquals(15, result.blockX());
        // Appelle une méthode
        assertEquals(20, result.blockY());
        // Appelle une méthode
        assertEquals(30, result.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNegAbs() {
        // Appelle une méthode
        BlockVec positive = new BlockVec(10, 20, 30);
        // Appelle une méthode
        BlockVec negative = new BlockVec(-10, -20, -30);
        // Appelle une méthode
        BlockVec mixed = new BlockVec(-10, 20, -30);

        // Test neg
        // Appelle une méthode
        BlockVec result = positive.neg();
        // Appelle une méthode
        assertEquals(-10, result.blockX());
        // Appelle une méthode
        assertEquals(-20, result.blockY());
        // Appelle une méthode
        assertEquals(-30, result.blockZ());

        // Appelle une méthode
        result = negative.neg();
        // Appelle une méthode
        assertEquals(10, result.blockX());
        // Appelle une méthode
        assertEquals(20, result.blockY());
        // Appelle une méthode
        assertEquals(30, result.blockZ());

        // Test abs
        // Appelle une méthode
        result = negative.abs();
        // Appelle une méthode
        assertEquals(10, result.blockX());
        // Appelle une méthode
        assertEquals(20, result.blockY());
        // Appelle une méthode
        assertEquals(30, result.blockZ());

        // Appelle une méthode
        result = mixed.abs();
        // Appelle une méthode
        assertEquals(10, result.blockX());
        // Appelle une méthode
        assertEquals(20, result.blockY());
        // Appelle une méthode
        assertEquals(30, result.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCross() {
        // Appelle une méthode
        BlockVec v1 = new BlockVec(1, 0, 0);
        // Appelle une méthode
        BlockVec v2 = new BlockVec(0, 1, 0);

        // Cross product of unit vectors
        // Appelle une méthode
        BlockVec result = v1.cross(v2);
        // Appelle une méthode
        assertEquals(0, result.blockX());
        // Appelle une méthode
        assertEquals(0, result.blockY());
        // Appelle une méthode
        assertEquals(1, result.blockZ());

        // Reverse order
        // Appelle une méthode
        result = v2.cross(v1);
        // Appelle une méthode
        assertEquals(0, result.blockX());
        // Appelle une méthode
        assertEquals(0, result.blockY());
        // Appelle une méthode
        assertEquals(-1, result.blockZ());

        // More complex case
        // Appelle une méthode
        BlockVec v3 = new BlockVec(2, 3, 4);
        // Appelle une méthode
        BlockVec v4 = new BlockVec(5, 6, 7);
        // Appelle une méthode
        result = v3.cross(v4);
        // Appelle une méthode
        assertEquals(-3, result.blockX());
        // Appelle une méthode
        assertEquals(6, result.blockY());
        // Appelle une méthode
        assertEquals(-3, result.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSamePoint() {
        // Appelle une méthode
        BlockVec v1 = new BlockVec(10, 20, 30);
        // Appelle une méthode
        BlockVec v2 = new BlockVec(10, 20, 30);
        // Appelle une méthode
        BlockVec v3 = new BlockVec(10, 20, 31);

        // Test with BlockVec
        // Appelle une méthode
        assertTrue(v1.samePoint(v2));
        // Appelle une méthode
        assertFalse(v1.samePoint(v3));

        // Test with coordinates
        // Appelle une méthode
        assertTrue(v1.samePoint(10, 20, 30));
        // Appelle une méthode
        assertFalse(v1.samePoint(10, 20, 31));
        // Appelle une méthode
        assertFalse(v1.samePoint(11, 20, 30));
        // Appelle une méthode
        assertFalse(v1.samePoint(10, 21, 30));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRelative() {
        // Appelle une méthode
        BlockVec base = new BlockVec(10, 20, 30);

        // Appelle une méthode
        assertEquals(new BlockVec(10, 21, 30), base.relative(BlockFace.TOP));
        // Appelle une méthode
        assertEquals(new BlockVec(10, 19, 30), base.relative(BlockFace.BOTTOM));
        // Appelle une méthode
        assertEquals(new BlockVec(11, 20, 30), base.relative(BlockFace.EAST));
        // Appelle une méthode
        assertEquals(new BlockVec(9, 20, 30), base.relative(BlockFace.WEST));
        // Appelle une méthode
        assertEquals(new BlockVec(10, 20, 31), base.relative(BlockFace.SOUTH));
        // Appelle une méthode
        assertEquals(new BlockVec(10, 20, 29), base.relative(BlockFace.NORTH));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testApply() {
        // Appelle une méthode
        BlockVec base = new BlockVec(10, 20, 30);

        // Test operator that doubles all values
        // Appelle une méthode
        BlockVec result = base.apply((x, y, z) -> new BlockVec(x * 2, y * 2, z * 2));
        // Appelle une méthode
        assertEquals(20, result.blockX());
        // Appelle une méthode
        assertEquals(40, result.blockY());
        // Appelle une méthode
        assertEquals(60, result.blockZ());

        // Test operator that creates constant
        // Appelle une méthode
        result = base.apply((_, _, _) -> BlockVec.ZERO);
        // Appelle une méthode
        assertEquals(BlockVec.ZERO, result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAsBlockVec() {
        // Appelle une méthode
        Point vec = new BlockVec(5, 10, 15);
        // Appelle une méthode
        assertSame(vec, vec.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDoubleOperations() {
        // Appelle une méthode
        BlockVec base = new BlockVec(10, 20, 30);

        // Test that double operations return Vec
        // Appelle une méthode
        Vec result = base.add(1.5, 2.5, 3.5);
        // Appelle une méthode
        assertInstanceOf(Vec.class, result);
        // Appelle une méthode
        assertEquals(11.5, result.x(), 0.001);
        // Appelle une méthode
        assertEquals(22.5, result.y(), 0.001);
        // Appelle une méthode
        assertEquals(33.5, result.z(), 0.001);

        // Test withX returns Vec
        // Appelle une méthode
        result = base.withX(15.5);
        // Appelle une méthode
        assertInstanceOf(Vec.class, result);
        // Appelle une méthode
        assertEquals(15.5, result.x(), 0.001);

        // Test normalize returns Vec
        // Appelle une méthode
        result = base.normalize();
        // Appelle une méthode
        assertInstanceOf(Vec.class, result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRandomBlockVectors() {
        // Appelle une méthode
        Random random = new Random(12345L);

        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            int x = random.nextInt(2000) - 1000;
            // Appelle une méthode
            int y = random.nextInt(2000) - 1000;
            // Appelle une méthode
            int z = random.nextInt(2000) - 1000;

            // Appelle une méthode
            BlockVec vec = new BlockVec(x, y, z);
            // Appelle une méthode
            assertEquals(x, vec.blockX());
            // Appelle une méthode
            assertEquals(y, vec.blockY());
            // Appelle une méthode
            assertEquals(z, vec.blockZ());

            // Test that operations preserve immutability
            // Affecte une valeur
            BlockVec original = vec;
            // Appelle une méthode
            BlockVec modified = vec.add(1, 2, 3);
            // Appelle une méthode
            assertNotEquals(original, modified);
            // Appelle une méthode
            assertEquals(x, original.blockX());
            // Appelle une méthode
            assertEquals(x + 1, modified.blockX());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCoordinateConversions() {
        // Appelle une méthode
        BlockVec vec = new BlockVec(32, 64, 96);

        // Test x/y/z methods return double values
        // Appelle une méthode
        assertEquals(32.0, vec.x());
        // Appelle une méthode
        assertEquals(64.0, vec.y());
        // Appelle une méthode
        assertEquals(96.0, vec.z());

        // Test section coordinates
        // Appelle une méthode
        assertEquals(2, vec.sectionX());
        // Appelle une méthode
        assertEquals(4, vec.sectionY());
        // Appelle une méthode
        assertEquals(6, vec.sectionZ());

        // Test chunk coordinates
        // Appelle une méthode
        assertEquals(2, vec.chunkX());
        // Appelle une méthode
        assertEquals(6, vec.chunkZ());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

