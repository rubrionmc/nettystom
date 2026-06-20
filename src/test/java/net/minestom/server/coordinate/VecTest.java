// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Random;

// Static import of a member
import static net.minestom.server.coordinate.Point.EPSILON;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class VecTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testConstructors() {
        // Test 3-param constructor
        // Calls a method
        Vec vec1 = new Vec(1.5, 2.5, 3.5);
        // Calls a method
        assertEquals(1.5, vec1.x());
        // Calls a method
        assertEquals(2.5, vec1.y());
        // Calls a method
        assertEquals(3.5, vec1.z());

        // Test 2-param constructor (x, z) - y defaults to 0
        // Calls a method
        Vec vec2 = new Vec(1.5, 3.5);
        // Calls a method
        assertEquals(1.5, vec2.x());
        // Calls a method
        assertEquals(0.0, vec2.y());
        // Calls a method
        assertEquals(3.5, vec2.z());

        // Test single value constructor
        // Calls a method
        Vec vec3 = new Vec(5.5);
        // Calls a method
        assertEquals(5.5, vec3.x());
        // Calls a method
        assertEquals(5.5, vec3.y());
        // Calls a method
        assertEquals(5.5, vec3.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testConstants() {
        // Calls a method
        assertEquals(0, Vec.ZERO.x());
        // Calls a method
        assertEquals(0, Vec.ZERO.y());
        // Calls a method
        assertEquals(0, Vec.ZERO.z());

        // Calls a method
        assertEquals(1, Vec.ONE.x());
        // Calls a method
        assertEquals(1, Vec.ONE.y());
        // Calls a method
        assertEquals(1, Vec.ONE.z());

        // Calls a method
        assertEquals(Point.SECTION_SIZE, Vec.SECTION.x());
        // Calls a method
        assertEquals(Point.SECTION_SIZE, Vec.SECTION.y());
        // Calls a method
        assertEquals(Point.SECTION_SIZE, Vec.SECTION.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateAroundX() {
        // Calls a method
        Vec vec = new Vec(0, 1, 0);

        // Rotate 90 degrees (pi/2 radians)
        // Calls a method
        Vec rotated = vec.rotateAroundX(Math.PI / 2);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(1, rotated.z(), EPSILON);

        // Rotate 180 degrees
        // Calls a method
        rotated = vec.rotateAroundX(Math.PI);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(-1, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 360 degrees (should return to original)
        // Calls a method
        rotated = vec.rotateAroundX(2 * Math.PI);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(1, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateAroundY() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);

        // Rotate 90 degrees
        // Calls a method
        Vec rotated = vec.rotateAroundY(Math.PI / 2);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(-1, rotated.z(), EPSILON);

        // Rotate 180 degrees
        // Calls a method
        rotated = vec.rotateAroundY(Math.PI);
        // Calls a method
        assertEquals(-1, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 360 degrees
        // Calls a method
        rotated = vec.rotateAroundY(2 * Math.PI);
        // Calls a method
        assertEquals(1, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateAroundZ() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);

        // Rotate 90 degrees
        // Calls a method
        Vec rotated = vec.rotateAroundZ(Math.PI / 2);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(1, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 180 degrees
        // Calls a method
        rotated = vec.rotateAroundZ(Math.PI);
        // Calls a method
        assertEquals(-1, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);

        // Rotate 360 degrees
        // Calls a method
        rotated = vec.rotateAroundZ(2 * Math.PI);
        // Calls a method
        assertEquals(1, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotate() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);

        // Rotate around all axes
        // Calls a method
        Vec rotated = vec.rotate(Math.PI / 2, Math.PI / 2, Math.PI / 2);
        // Calls a method
        assertNotNull(rotated);

        // Verify it's a combination of individual rotations
        // Assigns a value
        Vec expected = vec.rotateAroundX(Math.PI / 2)
                // Code statement
                .rotateAroundY(Math.PI / 2)
                // Calls a method
                .rotateAroundZ(Math.PI / 2);
        // Calls a method
        assertEquals(expected.x(), rotated.x(), EPSILON);
        // Calls a method
        assertEquals(expected.y(), rotated.y(), EPSILON);
        // Calls a method
        assertEquals(expected.z(), rotated.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateFromView() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);

        // Test rotation with yaw and pitch
        // Calls a method
        Vec rotated = vec.rotateFromView(0f, 0f);
        // Calls a method
        assertNotNull(rotated);

        // Test with 90 degree yaw
        // Calls a method
        rotated = vec.rotateFromView(90f, 0f);
        // Calls a method
        assertNotNull(rotated);

        // Test with pitch
        // Calls a method
        rotated = vec.rotateFromView(0f, 45f);
        // Calls a method
        assertNotNull(rotated);

        // Test with both
        // Calls a method
        rotated = vec.rotateFromView(45f, 30f);
        // Calls a method
        assertNotNull(rotated);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateFromViewWithPos() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);
        // Calls a method
        Pos pos = new Pos(0, 0, 0, 45f, 30f);

        // Calls a method
        Vec rotated = vec.rotateFromView(pos);
        // Calls a method
        Vec expected = vec.rotateFromView(45f, 30f);

        // Calls a method
        assertEquals(expected.x(), rotated.x(), EPSILON);
        // Calls a method
        assertEquals(expected.y(), rotated.y(), EPSILON);
        // Calls a method
        assertEquals(expected.z(), rotated.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateAroundAxis() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);
        // Assigns a value
        Vec axis = new Vec(0, 1, 0); // Y-axis

        // Rotate 90 degrees around Y-axis
        // Calls a method
        Vec rotated = vec.rotateAroundAxis(axis, Math.PI / 2);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(-1, rotated.z(), EPSILON);

        // Test with non-unit axis (should normalize automatically)
        // Calls a method
        Vec nonUnitAxis = new Vec(0, 2, 0);
        // Calls a method
        rotated = vec.rotateAroundAxis(nonUnitAxis, Math.PI / 2);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(-1, rotated.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRotateAroundNonUnitAxis() {
        // Calls a method
        Vec vec = new Vec(1, 0, 0);
        // Calls a method
        Vec axis = new Vec(0, 1, 0);

        // Calls a method
        Vec rotated = vec.rotateAroundNonUnitAxis(axis, Math.PI / 2);
        // Calls a method
        assertEquals(0, rotated.x(), EPSILON);
        // Calls a method
        assertEquals(0, rotated.y(), EPSILON);
        // Calls a method
        assertEquals(-1, rotated.z(), EPSILON);

        // Test with scaled axis - result should be scaled
        // Calls a method
        Vec scaledAxis = new Vec(0, 2, 0);
        // Calls a method
        Vec scaledRotated = vec.rotateAroundNonUnitAxis(scaledAxis, Math.PI / 2);
        // Length should be different from normalized version
        // Calls a method
        assertNotEquals(rotated.length(), scaledRotated.length(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNormalize() {
        // Calls a method
        Vec vec = new Vec(3, 4, 0);
        // Calls a method
        Vec normalized = vec.normalize();

        // Length should be 1
        // Calls a method
        assertEquals(1.0, normalized.length(), EPSILON);

        // Direction should be preserved
        // Calls a method
        assertEquals(0.6, normalized.x(), EPSILON);
        // Calls a method
        assertEquals(0.8, normalized.y(), EPSILON);
        // Calls a method
        assertEquals(0, normalized.z(), EPSILON);

        // Already normalized vector
        // Calls a method
        Vec unit = new Vec(1, 0, 0);
        // Calls a method
        Vec normalizedUnit = unit.normalize();
        // Calls a method
        assertEquals(1.0, normalizedUnit.length(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testIsNormalized() {
        // Calls a method
        Vec unit = new Vec(1, 0, 0);
        // Calls a method
        assertTrue(unit.isNormalized());

        // Calls a method
        Vec normalized = new Vec(3, 4, 0).normalize();
        // Calls a method
        assertTrue(normalized.isNormalized());

        // Calls a method
        Vec notNormalized = new Vec(2, 0, 0);
        // Calls a method
        assertFalse(notNormalized.isNormalized());

        // Assigns a value
        Vec zero = Vec.ZERO;
        // Calls a method
        assertFalse(zero.isNormalized());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testLength() {
        // Calls a method
        Vec vec = new Vec(3, 4, 0);
        // Calls a method
        assertEquals(5.0, vec.length(), EPSILON);

        // Calls a method
        Vec vec2 = new Vec(1, 1, 1);
        // Calls a method
        assertEquals(Math.sqrt(3), vec2.length(), EPSILON);

        // Calls a method
        assertEquals(0, Vec.ZERO.length(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testLengthSquared() {
        // Calls a method
        Vec vec = new Vec(3, 4, 0);
        // Calls a method
        assertEquals(25.0, vec.lengthSquared(), EPSILON);

        // Calls a method
        Vec vec2 = new Vec(1, 1, 1);
        // Calls a method
        assertEquals(3.0, vec2.lengthSquared(), EPSILON);

        // Calls a method
        assertEquals(0, Vec.ZERO.lengthSquared(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDot() {
        // Calls a method
        Vec v1 = new Vec(1, 2, 3);
        // Calls a method
        Vec v2 = new Vec(4, 5, 6);

        // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        // Calls a method
        assertEquals(32.0, v1.dot(v2), EPSILON);

        // Perpendicular vectors
        // Calls a method
        Vec v3 = new Vec(1, 0, 0);
        // Calls a method
        Vec v4 = new Vec(0, 1, 0);
        // Calls a method
        assertEquals(0.0, v3.dot(v4), EPSILON);

        // Parallel vectors
        // Calls a method
        Vec v5 = new Vec(2, 0, 0);
        // Calls a method
        assertEquals(2.0, v3.dot(v5), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAngle() {
        // Calls a method
        Vec v1 = new Vec(1, 0, 0);
        // Calls a method
        Vec v2 = new Vec(0, 1, 0);

        // 90 degrees = pi/2 radians
        // Calls a method
        assertEquals(Math.PI / 2, v1.angle(v2), EPSILON);

        // 180 degrees
        // Calls a method
        Vec v3 = new Vec(-1, 0, 0);
        // Calls a method
        assertEquals(Math.PI, v1.angle(v3), EPSILON);

        // 0 degrees (same direction)
        // Calls a method
        Vec v4 = new Vec(2, 0, 0);
        // Calls a method
        assertEquals(0, v1.angle(v4), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCross() {
        // Calls a method
        Vec v1 = new Vec(1, 0, 0);
        // Calls a method
        Vec v2 = new Vec(0, 1, 0);

        // Calls a method
        Vec cross = v1.cross(v2);
        // Calls a method
        assertEquals(0, cross.x(), EPSILON);
        // Calls a method
        assertEquals(0, cross.y(), EPSILON);
        // Calls a method
        assertEquals(1, cross.z(), EPSILON);

        // Reverse order should negate
        // Calls a method
        Vec crossReverse = v2.cross(v1);
        // Calls a method
        assertEquals(0, crossReverse.x(), EPSILON);
        // Calls a method
        assertEquals(0, crossReverse.y(), EPSILON);
        // Calls a method
        assertEquals(-1, crossReverse.z(), EPSILON);

        // More complex
        // Calls a method
        Vec v3 = new Vec(2, 3, 4);
        // Calls a method
        Vec v4 = new Vec(5, 6, 7);
        // Calls a method
        Vec result = v3.cross(v4);
        // Calls a method
        assertEquals(-3, result.x(), EPSILON);
        // Calls a method
        assertEquals(6, result.y(), EPSILON);
        // Calls a method
        assertEquals(-3, result.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testLerp() {
        // Calls a method
        Vec start = new Vec(0, 0, 0);
        // Calls a method
        Vec end = new Vec(10, 10, 10);

        // Halfway
        // Calls a method
        Vec mid = start.lerp(end, 0.5);
        // Calls a method
        assertEquals(5, mid.x(), EPSILON);
        // Calls a method
        assertEquals(5, mid.y(), EPSILON);
        // Calls a method
        assertEquals(5, mid.z(), EPSILON);

        // Start
        // Calls a method
        Vec atStart = start.lerp(end, 0);
        // Calls a method
        assertEquals(0, atStart.x(), EPSILON);
        // Calls a method
        assertEquals(0, atStart.y(), EPSILON);
        // Calls a method
        assertEquals(0, atStart.z(), EPSILON);

        // End
        // Calls a method
        Vec atEnd = start.lerp(end, 1);
        // Calls a method
        assertEquals(10, atEnd.x(), EPSILON);
        // Calls a method
        assertEquals(10, atEnd.y(), EPSILON);
        // Calls a method
        assertEquals(10, atEnd.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testApply() {
        // Calls a method
        Vec vec = new Vec(1, 2, 3);

        // Test operator
        // Calls a method
        Vec result = vec.apply((x, y, z) -> new Vec(x * 2, y * 2, z * 2));
        // Calls a method
        assertEquals(2, result.x(), EPSILON);
        // Calls a method
        assertEquals(4, result.y(), EPSILON);
        // Calls a method
        assertEquals(6, result.z(), EPSILON);

        // Test with predefined operators
        // Calls a method
        Vec epsilon = new Vec(0.0000001, 0.0000001, 0.0000001);
        // Calls a method
        Vec epsilonResult = epsilon.apply(Vec.Operator.EPSILON);
        // Calls a method
        assertEquals(0, epsilonResult.x(), EPSILON);
        // Calls a method
        assertEquals(0, epsilonResult.y(), EPSILON);
        // Calls a method
        assertEquals(0, epsilonResult.z(), EPSILON);

        // Test FLOOR
        // Calls a method
        Vec decimal = new Vec(1.7, 2.3, 3.9);
        // Calls a method
        Vec floored = decimal.apply(Vec.Operator.FLOOR);
        // Calls a method
        assertEquals(1, floored.x(), EPSILON);
        // Calls a method
        assertEquals(2, floored.y(), EPSILON);
        // Calls a method
        assertEquals(3, floored.z(), EPSILON);

        // Test CEIL
        // Calls a method
        Vec ceiled = decimal.apply(Vec.Operator.CEIL);
        // Calls a method
        assertEquals(2, ceiled.x(), EPSILON);
        // Calls a method
        assertEquals(3, ceiled.y(), EPSILON);
        // Calls a method
        assertEquals(4, ceiled.z(), EPSILON);

        // Test SIGNUM
        // Calls a method
        Vec signed = new Vec(-5, 0, 5);
        // Calls a method
        Vec signum = signed.apply(Vec.Operator.SIGNUM);
        // Calls a method
        assertEquals(-1, signum.x(), EPSILON);
        // Calls a method
        assertEquals(0, signum.y(), EPSILON);
        // Calls a method
        assertEquals(1, signum.z(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAsVec() {
        // Calls a method
        Point vec = new Vec(1, 2, 3);
        // Calls a method
        assertSame(vec, vec.asVec());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAsPosWithView() {
        // Calls a method
        Vec vec = new Vec(1, 2, 3);
        // Calls a method
        Pos result = vec.asPos(45f, 30f);
        // Calls a method
        assertEquals(1, result.x());
        // Calls a method
        assertEquals(2, result.y());
        // Calls a method
        assertEquals(3, result.z());
        // Calls a method
        assertEquals(45f, result.yaw());
        // Calls a method
        assertEquals(30f, result.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDistance() {
        // Calls a method
        Vec v1 = new Vec(0, 0, 0);
        // Calls a method
        Vec v2 = new Vec(3, 4, 0);

        // Calls a method
        assertEquals(5.0, v1.distance(v2), EPSILON);
        // Calls a method
        assertEquals(5.0, v2.distance(v1), EPSILON);

        // Distance to self
        // Calls a method
        assertEquals(0, v1.distance(v1), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDistanceSquared() {
        // Calls a method
        Vec v1 = new Vec(0, 0, 0);
        // Calls a method
        Vec v2 = new Vec(3, 4, 0);

        // Calls a method
        assertEquals(25.0, v1.distanceSquared(v2), EPSILON);
        // Calls a method
        assertEquals(25.0, v2.distanceSquared(v1), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testIsZero() {
        // Calls a method
        assertTrue(Vec.ZERO.isZero());
        // Calls a method
        assertFalse(Vec.ONE.isZero());
        // Calls a method
        assertFalse(new Vec(EPSILON, 0, 0).isZero());
        // Calls a method
        assertFalse(new Vec(0, EPSILON, 0).isZero());
        // Calls a method
        assertFalse(new Vec(0, 0, EPSILON).isZero());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRandomVectors() {
        // Calls a method
        Random random = new Random(54321L);

        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            double x = random.nextDouble() * 200 - 100;
            // Calls a method
            double y = random.nextDouble() * 200 - 100;
            // Calls a method
            double z = random.nextDouble() * 200 - 100;

            // Calls a method
            Vec vec = new Vec(x, y, z);
            // Calls a method
            assertEquals(x, vec.x(), EPSILON);
            // Calls a method
            assertEquals(y, vec.y(), EPSILON);
            // Calls a method
            assertEquals(z, vec.z(), EPSILON);

            // Test immutability
            // Assigns a value
            Vec original = vec;
            // Calls a method
            Vec modified = vec.add(1, 2, 3);
            // Calls a method
            assertEquals(x, original.x(), EPSILON);
            // Calls a method
            assertEquals(x + 1, modified.x(), EPSILON);

            // Test length consistency
            // Calls a method
            double length = vec.length();
            // Calls a method
            double lengthFromSquared = Math.sqrt(vec.lengthSquared());
            // Calls a method
            assertEquals(length, lengthFromSquared, EPSILON);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}

