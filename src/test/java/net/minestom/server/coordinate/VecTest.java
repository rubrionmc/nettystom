// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static net.minestom.server.coordinate.Point.EPSILON;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class VecTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testConstructors() {
        // Test 3-param constructor
        // Appelle une méthode
        Vec vec1 = new Vec(1.5, 2.5, 3.5);
        // Appelle une méthode
        assertEquals(1.5, vec1.x());
        // Appelle une méthode
        assertEquals(2.5, vec1.y());
        // Appelle une méthode
        assertEquals(3.5, vec1.z());

        // Test 2-param constructor (x, z) - y defaults to 0
        // Appelle une méthode
        Vec vec2 = new Vec(1.5, 3.5);
        // Appelle une méthode
        assertEquals(1.5, vec2.x());
        // Appelle une méthode
        assertEquals(0.0, vec2.y());
        // Appelle une méthode
        assertEquals(3.5, vec2.z());

        // Test single value constructor
        // Appelle une méthode
        Vec vec3 = new Vec(5.5);
        // Appelle une méthode
        assertEquals(5.5, vec3.x());
        // Appelle une méthode
        assertEquals(5.5, vec3.y());
        // Appelle une méthode
        assertEquals(5.5, vec3.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testConstants() {
        // Appelle une méthode
        assertEquals(0, Vec.ZERO.x());
        // Appelle une méthode
        assertEquals(0, Vec.ZERO.y());
        // Appelle une méthode
        assertEquals(0, Vec.ZERO.z());

        // Appelle une méthode
        assertEquals(1, Vec.ONE.x());
        // Appelle une méthode
        assertEquals(1, Vec.ONE.y());
        // Appelle une méthode
        assertEquals(1, Vec.ONE.z());

        // Appelle une méthode
        assertEquals(Point.SECTION_SIZE, Vec.SECTION.x());
        // Appelle une méthode
        assertEquals(Point.SECTION_SIZE, Vec.SECTION.y());
        // Appelle une méthode
        assertEquals(Point.SECTION_SIZE, Vec.SECTION.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateAroundX() {
        // Appelle une méthode
        Vec vec = new Vec(0, 1, 0);

        // Rotate 90 degrees (pi/2 radians)
        // Appelle une méthode
        Vec rotated = vec.rotateAroundX(Math.PI / 2);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(1, rotated.z(), EPSILON);

        // Rotate 180 degrees
        // Appelle une méthode
        rotated = vec.rotateAroundX(Math.PI);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(-1, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 360 degrees (should return to original)
        // Appelle une méthode
        rotated = vec.rotateAroundX(2 * Math.PI);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(1, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateAroundY() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);

        // Rotate 90 degrees
        // Appelle une méthode
        Vec rotated = vec.rotateAroundY(Math.PI / 2);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(-1, rotated.z(), EPSILON);

        // Rotate 180 degrees
        // Appelle une méthode
        rotated = vec.rotateAroundY(Math.PI);
        // Appelle une méthode
        assertEquals(-1, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 360 degrees
        // Appelle une méthode
        rotated = vec.rotateAroundY(2 * Math.PI);
        // Appelle une méthode
        assertEquals(1, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateAroundZ() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);

        // Rotate 90 degrees
        // Appelle une méthode
        Vec rotated = vec.rotateAroundZ(Math.PI / 2);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(1, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 180 degrees
        // Appelle une méthode
        rotated = vec.rotateAroundZ(Math.PI);
        // Appelle une méthode
        assertEquals(-1, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 360 degrees
        // Appelle une méthode
        rotated = vec.rotateAroundZ(2 * Math.PI);
        // Appelle une méthode
        assertEquals(1, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotate() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);

        // Rotate around all axes
        // Appelle une méthode
        Vec rotated = vec.rotate(Math.PI / 2, Math.PI / 2, Math.PI / 2);
        // Appelle une méthode
        assertNotNull(rotated);

        // Verify it's a combination of individual rotations
        // Affecte une valeur
        Vec expected = vec.rotateAroundX(Math.PI / 2)
                // Instruction de code
                .rotateAroundY(Math.PI / 2)
                // Appelle une méthode
                .rotateAroundZ(Math.PI / 2);
        // Appelle une méthode
        assertEquals(expected.x(), rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(expected.y(), rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(expected.z(), rotated.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateFromView() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);

        // Test rotation with yaw and pitch
        // Appelle une méthode
        Vec rotated = vec.rotateFromView(0f, 0f);
        // Appelle une méthode
        assertNotNull(rotated);

        // Test with 90 degree yaw
        // Appelle une méthode
        rotated = vec.rotateFromView(90f, 0f);
        // Appelle une méthode
        assertNotNull(rotated);

        // Test with pitch
        // Appelle une méthode
        rotated = vec.rotateFromView(0f, 45f);
        // Appelle une méthode
        assertNotNull(rotated);

        // Test with both
        // Appelle une méthode
        rotated = vec.rotateFromView(45f, 30f);
        // Appelle une méthode
        assertNotNull(rotated);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateFromViewWithPos() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);
        // Appelle une méthode
        Pos pos = new Pos(0, 0, 0, 45f, 30f);

        // Appelle une méthode
        Vec rotated = vec.rotateFromView(pos);
        // Appelle une méthode
        Vec expected = vec.rotateFromView(45f, 30f);

        // Appelle une méthode
        assertEquals(expected.x(), rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(expected.y(), rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(expected.z(), rotated.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateAroundAxis() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);
        // Affecte une valeur
        Vec axis = new Vec(0, 1, 0); // Y-axis

        // Rotate 90 degrees around Y-axis
        // Appelle une méthode
        Vec rotated = vec.rotateAroundAxis(axis, Math.PI / 2);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(-1, rotated.z(), EPSILON);

        // Test with non-unit axis (should normalize automatically)
        // Appelle une méthode
        Vec nonUnitAxis = new Vec(0, 2, 0);
        // Appelle une méthode
        rotated = vec.rotateAroundAxis(nonUnitAxis, Math.PI / 2);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(-1, rotated.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRotateAroundNonUnitAxis() {
        // Appelle une méthode
        Vec vec = new Vec(1, 0, 0);
        // Appelle une méthode
        Vec axis = new Vec(0, 1, 0);

        // Appelle une méthode
        Vec rotated = vec.rotateAroundNonUnitAxis(axis, Math.PI / 2);
        // Appelle une méthode
        assertEquals(0, rotated.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, rotated.y(), EPSILON);
        // Appelle une méthode
        assertEquals(-1, rotated.z(), EPSILON);

        // Test with scaled axis - result should be scaled
        // Appelle une méthode
        Vec scaledAxis = new Vec(0, 2, 0);
        // Appelle une méthode
        Vec scaledRotated = vec.rotateAroundNonUnitAxis(scaledAxis, Math.PI / 2);
        // Length should be different from normalized version
        // Appelle une méthode
        assertNotEquals(rotated.length(), scaledRotated.length(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNormalize() {
        // Appelle une méthode
        Vec vec = new Vec(3, 4, 0);
        // Appelle une méthode
        Vec normalized = vec.normalize();

        // Length should be 1
        // Appelle une méthode
        assertEquals(1.0, normalized.length(), EPSILON);

        // Direction should be preserved
        // Appelle une méthode
        assertEquals(0.6, normalized.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0.8, normalized.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, normalized.z(), EPSILON);

        // Already normalized vector
        // Appelle une méthode
        Vec unit = new Vec(1, 0, 0);
        // Appelle une méthode
        Vec normalizedUnit = unit.normalize();
        // Appelle une méthode
        assertEquals(1.0, normalizedUnit.length(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testIsNormalized() {
        // Appelle une méthode
        Vec unit = new Vec(1, 0, 0);
        // Appelle une méthode
        assertTrue(unit.isNormalized());

        // Appelle une méthode
        Vec normalized = new Vec(3, 4, 0).normalize();
        // Appelle une méthode
        assertTrue(normalized.isNormalized());

        // Appelle une méthode
        Vec notNormalized = new Vec(2, 0, 0);
        // Appelle une méthode
        assertFalse(notNormalized.isNormalized());

        // Affecte une valeur
        Vec zero = Vec.ZERO;
        // Appelle une méthode
        assertFalse(zero.isNormalized());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testLength() {
        // Appelle une méthode
        Vec vec = new Vec(3, 4, 0);
        // Appelle une méthode
        assertEquals(5.0, vec.length(), EPSILON);

        // Appelle une méthode
        Vec vec2 = new Vec(1, 1, 1);
        // Appelle une méthode
        assertEquals(Math.sqrt(3), vec2.length(), EPSILON);

        // Appelle une méthode
        assertEquals(0, Vec.ZERO.length(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testLengthSquared() {
        // Appelle une méthode
        Vec vec = new Vec(3, 4, 0);
        // Appelle une méthode
        assertEquals(25.0, vec.lengthSquared(), EPSILON);

        // Appelle une méthode
        Vec vec2 = new Vec(1, 1, 1);
        // Appelle une méthode
        assertEquals(3.0, vec2.lengthSquared(), EPSILON);

        // Appelle une méthode
        assertEquals(0, Vec.ZERO.lengthSquared(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDot() {
        // Appelle une méthode
        Vec v1 = new Vec(1, 2, 3);
        // Appelle une méthode
        Vec v2 = new Vec(4, 5, 6);

        // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        // Appelle une méthode
        assertEquals(32.0, v1.dot(v2), EPSILON);

        // Perpendicular vectors
        // Appelle une méthode
        Vec v3 = new Vec(1, 0, 0);
        // Appelle une méthode
        Vec v4 = new Vec(0, 1, 0);
        // Appelle une méthode
        assertEquals(0.0, v3.dot(v4), EPSILON);

        // Parallel vectors
        // Appelle une méthode
        Vec v5 = new Vec(2, 0, 0);
        // Appelle une méthode
        assertEquals(2.0, v3.dot(v5), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAngle() {
        // Appelle une méthode
        Vec v1 = new Vec(1, 0, 0);
        // Appelle une méthode
        Vec v2 = new Vec(0, 1, 0);

        // 90 degrees = pi/2 radians
        // Appelle une méthode
        assertEquals(Math.PI / 2, v1.angle(v2), EPSILON);

        // 180 degrees
        // Appelle une méthode
        Vec v3 = new Vec(-1, 0, 0);
        // Appelle une méthode
        assertEquals(Math.PI, v1.angle(v3), EPSILON);

        // 0 degrees (same direction)
        // Appelle une méthode
        Vec v4 = new Vec(2, 0, 0);
        // Appelle une méthode
        assertEquals(0, v1.angle(v4), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCross() {
        // Appelle une méthode
        Vec v1 = new Vec(1, 0, 0);
        // Appelle une méthode
        Vec v2 = new Vec(0, 1, 0);

        // Appelle une méthode
        Vec cross = v1.cross(v2);
        // Appelle une méthode
        assertEquals(0, cross.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, cross.y(), EPSILON);
        // Appelle une méthode
        assertEquals(1, cross.z(), EPSILON);

        // Reverse order should negate
        // Appelle une méthode
        Vec crossReverse = v2.cross(v1);
        // Appelle une méthode
        assertEquals(0, crossReverse.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, crossReverse.y(), EPSILON);
        // Appelle une méthode
        assertEquals(-1, crossReverse.z(), EPSILON);

        // More complex
        // Appelle une méthode
        Vec v3 = new Vec(2, 3, 4);
        // Appelle une méthode
        Vec v4 = new Vec(5, 6, 7);
        // Appelle une méthode
        Vec result = v3.cross(v4);
        // Appelle une méthode
        assertEquals(-3, result.x(), EPSILON);
        // Appelle une méthode
        assertEquals(6, result.y(), EPSILON);
        // Appelle une méthode
        assertEquals(-3, result.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testLerp() {
        // Appelle une méthode
        Vec start = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec end = new Vec(10, 10, 10);

        // Halfway
        // Appelle une méthode
        Vec mid = start.lerp(end, 0.5);
        // Appelle une méthode
        assertEquals(5, mid.x(), EPSILON);
        // Appelle une méthode
        assertEquals(5, mid.y(), EPSILON);
        // Appelle une méthode
        assertEquals(5, mid.z(), EPSILON);

        // Start
        // Appelle une méthode
        Vec atStart = start.lerp(end, 0);
        // Appelle une méthode
        assertEquals(0, atStart.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, atStart.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, atStart.z(), EPSILON);

        // End
        // Appelle une méthode
        Vec atEnd = start.lerp(end, 1);
        // Appelle une méthode
        assertEquals(10, atEnd.x(), EPSILON);
        // Appelle une méthode
        assertEquals(10, atEnd.y(), EPSILON);
        // Appelle une méthode
        assertEquals(10, atEnd.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testApply() {
        // Appelle une méthode
        Vec vec = new Vec(1, 2, 3);

        // Test operator
        // Appelle une méthode
        Vec result = vec.apply((x, y, z) -> new Vec(x * 2, y * 2, z * 2));
        // Appelle une méthode
        assertEquals(2, result.x(), EPSILON);
        // Appelle une méthode
        assertEquals(4, result.y(), EPSILON);
        // Appelle une méthode
        assertEquals(6, result.z(), EPSILON);

        // Test with predefined operators
        // Appelle une méthode
        Vec epsilon = new Vec(0.0000001, 0.0000001, 0.0000001);
        // Appelle une méthode
        Vec epsilonResult = epsilon.apply(Vec.Operator.EPSILON);
        // Appelle une méthode
        assertEquals(0, epsilonResult.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, epsilonResult.y(), EPSILON);
        // Appelle une méthode
        assertEquals(0, epsilonResult.z(), EPSILON);

        // Test FLOOR
        // Appelle une méthode
        Vec decimal = new Vec(1.7, 2.3, 3.9);
        // Appelle une méthode
        Vec floored = decimal.apply(Vec.Operator.FLOOR);
        // Appelle une méthode
        assertEquals(1, floored.x(), EPSILON);
        // Appelle une méthode
        assertEquals(2, floored.y(), EPSILON);
        // Appelle une méthode
        assertEquals(3, floored.z(), EPSILON);

        // Test CEIL
        // Appelle une méthode
        Vec ceiled = decimal.apply(Vec.Operator.CEIL);
        // Appelle une méthode
        assertEquals(2, ceiled.x(), EPSILON);
        // Appelle une méthode
        assertEquals(3, ceiled.y(), EPSILON);
        // Appelle une méthode
        assertEquals(4, ceiled.z(), EPSILON);

        // Test SIGNUM
        // Appelle une méthode
        Vec signed = new Vec(-5, 0, 5);
        // Appelle une méthode
        Vec signum = signed.apply(Vec.Operator.SIGNUM);
        // Appelle une méthode
        assertEquals(-1, signum.x(), EPSILON);
        // Appelle une méthode
        assertEquals(0, signum.y(), EPSILON);
        // Appelle une méthode
        assertEquals(1, signum.z(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAsVec() {
        // Appelle une méthode
        Point vec = new Vec(1, 2, 3);
        // Appelle une méthode
        assertSame(vec, vec.asVec());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAsPosWithView() {
        // Appelle une méthode
        Vec vec = new Vec(1, 2, 3);
        // Appelle une méthode
        Pos result = vec.asPos(45f, 30f);
        // Appelle une méthode
        assertEquals(1, result.x());
        // Appelle une méthode
        assertEquals(2, result.y());
        // Appelle une méthode
        assertEquals(3, result.z());
        // Appelle une méthode
        assertEquals(45f, result.yaw());
        // Appelle une méthode
        assertEquals(30f, result.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDistance() {
        // Appelle une méthode
        Vec v1 = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec v2 = new Vec(3, 4, 0);

        // Appelle une méthode
        assertEquals(5.0, v1.distance(v2), EPSILON);
        // Appelle une méthode
        assertEquals(5.0, v2.distance(v1), EPSILON);

        // Distance to self
        // Appelle une méthode
        assertEquals(0, v1.distance(v1), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDistanceSquared() {
        // Appelle une méthode
        Vec v1 = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec v2 = new Vec(3, 4, 0);

        // Appelle une méthode
        assertEquals(25.0, v1.distanceSquared(v2), EPSILON);
        // Appelle une méthode
        assertEquals(25.0, v2.distanceSquared(v1), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testIsZero() {
        // Appelle une méthode
        assertTrue(Vec.ZERO.isZero());
        // Appelle une méthode
        assertFalse(Vec.ONE.isZero());
        // Appelle une méthode
        assertFalse(new Vec(EPSILON, 0, 0).isZero());
        // Appelle une méthode
        assertFalse(new Vec(0, EPSILON, 0).isZero());
        // Appelle une méthode
        assertFalse(new Vec(0, 0, EPSILON).isZero());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRandomVectors() {
        // Appelle une méthode
        Random random = new Random(54321L);

        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            double x = random.nextDouble() * 200 - 100;
            // Appelle une méthode
            double y = random.nextDouble() * 200 - 100;
            // Appelle une méthode
            double z = random.nextDouble() * 200 - 100;

            // Appelle une méthode
            Vec vec = new Vec(x, y, z);
            // Appelle une méthode
            assertEquals(x, vec.x(), EPSILON);
            // Appelle une méthode
            assertEquals(y, vec.y(), EPSILON);
            // Appelle une méthode
            assertEquals(z, vec.z(), EPSILON);

            // Test immutability
            // Affecte une valeur
            Vec original = vec;
            // Appelle une méthode
            Vec modified = vec.add(1, 2, 3);
            // Appelle une méthode
            assertEquals(x, original.x(), EPSILON);
            // Appelle une méthode
            assertEquals(x + 1, modified.x(), EPSILON);

            // Test length consistency
            // Appelle une méthode
            double length = vec.length();
            // Appelle une méthode
            double lengthFromSquared = Math.sqrt(vec.lengthSquared());
            // Appelle une méthode
            assertEquals(length, lengthFromSquared, EPSILON);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

