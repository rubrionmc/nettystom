// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Random;

// Static import of a member
import static net.minestom.server.coordinate.Point.EPSILON;
// Static import of a member
import static net.minestom.server.coordinate.Pos.VIEW_EPSILON;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class PosTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testConstructors() {
        // Test full constructor
        // Calls a method
        Pos pos1 = new Pos(1.5, 2.5, 3.5, 45f, 30f);
        // Calls a method
        assertEquals(1.5, pos1.x());
        // Calls a method
        assertEquals(2.5, pos1.y());
        // Calls a method
        assertEquals(3.5, pos1.z());
        // Calls a method
        assertEquals(45f, pos1.yaw());
        // Calls a method
        assertEquals(30f, pos1.pitch());

        // Test coordinate-only constructor
        // Calls a method
        Pos pos2 = new Pos(1.5, 2.5, 3.5);
        // Calls a method
        assertEquals(1.5, pos2.x());
        // Calls a method
        assertEquals(2.5, pos2.y());
        // Calls a method
        assertEquals(3.5, pos2.z());
        // Calls a method
        assertEquals(0f, pos2.yaw());
        // Calls a method
        assertEquals(0f, pos2.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testYawPitchNormalization() {
        // Test yaw wrapping
        // Calls a method
        Pos pos1 = new Pos(0, 0, 0, 200f, 0f);
        // Calls a method
        assertEquals(-160f, pos1.yaw(), EPSILON);

        // Calls a method
        Pos pos2 = new Pos(0, 0, 0, -200f, 0f);
        // Calls a method
        assertEquals(160f, pos2.yaw(), EPSILON);

        // Calls a method
        Pos pos3 = new Pos(0, 0, 0, 720f, 0f);
        // Calls a method
        assertEquals(0f, pos3.yaw(), EPSILON);

        // Test pitch clamping
        // Calls a method
        Pos pos4 = new Pos(0, 0, 0, 0f, 100f);
        // Calls a method
        assertEquals(90f, pos4.pitch());

        // Calls a method
        Pos pos5 = new Pos(0, 0, 0, 0f, -100f);
        // Calls a method
        assertEquals(-90f, pos5.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testFixYaw() {
        // Calls a method
        assertEquals(0f, Pos.fixYaw(0f), VIEW_EPSILON);
        // Calls a method
        assertEquals(90f, Pos.fixYaw(90f), VIEW_EPSILON);
        // Calls a method
        assertEquals(-90f, Pos.fixYaw(-90f), VIEW_EPSILON);
        // Calls a method
        assertEquals(180f, Pos.fixYaw(180f), VIEW_EPSILON);
        // Calls a method
        assertEquals(180f, Pos.fixYaw(-180f), VIEW_EPSILON);

        // Test wrapping
        // Calls a method
        assertEquals(-160f, Pos.fixYaw(200f), VIEW_EPSILON);
        // Calls a method
        assertEquals(160f, Pos.fixYaw(-200f), VIEW_EPSILON);
        // Calls a method
        assertEquals(0f, Pos.fixYaw(360f), VIEW_EPSILON);
        // Calls a method
        assertEquals(0f, Pos.fixYaw(720f), VIEW_EPSILON);
        // Calls a method
        assertEquals(85f, Pos.fixYaw(-1355f), VIEW_EPSILON);
        // Calls a method
        assertEquals(-135f, Pos.fixYaw(225f), VIEW_EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testFixPitch() {
        // Calls a method
        assertEquals(0f, Pos.fixPitch(0f));
        // Calls a method
        assertEquals(45f, Pos.fixPitch(45f));
        // Calls a method
        assertEquals(-45f, Pos.fixPitch(-45f));

        // Test clamping
        // Calls a method
        assertEquals(90f, Pos.fixPitch(90f));
        // Calls a method
        assertEquals(-90f, Pos.fixPitch(-90f));
        // Calls a method
        assertEquals(90f, Pos.fixPitch(100f));
        // Calls a method
        assertEquals(-90f, Pos.fixPitch(-100f));
        // Calls a method
        assertEquals(90f, Pos.fixPitch(225f));
        // Calls a method
        assertEquals(-90f, Pos.fixPitch(-135f));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWithCoord() {
        // Calls a method
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with coordinates
        // Calls a method
        Pos modified = base.withCoord(15, 25, 35);
        // Calls a method
        assertEquals(15, modified.x());
        // Calls a method
        assertEquals(25, modified.y());
        // Calls a method
        assertEquals(35, modified.z());
        // Calls a method
        assertEquals(45f, modified.yaw());
        // Calls a method
        assertEquals(30f, modified.pitch());

        // Test with Point
        // Calls a method
        Vec point = new Vec(5, 10, 15);
        // Calls a method
        modified = base.withCoord(point);
        // Calls a method
        assertEquals(5, modified.x());
        // Calls a method
        assertEquals(10, modified.y());
        // Calls a method
        assertEquals(15, modified.z());
        // Calls a method
        assertEquals(45f, modified.yaw());
        // Calls a method
        assertEquals(30f, modified.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWithView() {
        // Calls a method
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with yaw/pitch
        // Calls a method
        Pos modified = base.withView(90f, -45f);
        // Calls a method
        assertEquals(10, modified.x());
        // Calls a method
        assertEquals(20, modified.y());
        // Calls a method
        assertEquals(30, modified.z());
        // Calls a method
        assertEquals(90f, modified.yaw());
        // Calls a method
        assertEquals(-45f, modified.pitch());

        // Test with Pos
        // Calls a method
        Pos other = new Pos(0, 0, 0, 120f, -60f);
        // Calls a method
        modified = base.withView(other);
        // Calls a method
        assertEquals(10, modified.x());
        // Calls a method
        assertEquals(20, modified.y());
        // Calls a method
        assertEquals(30, modified.z());
        // Calls a method
        assertEquals(120f, modified.yaw());
        // Calls a method
        assertEquals(-60f, modified.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWithDirection() {
        // Calls a method
        Pos base = new Pos(0, 0, 0);

        // Look straight ahead (z positive)
        // Calls a method
        Pos pos = base.withDirection(new Vec(0, 0, 10));
        // Calls a method
        assertEquals(0f, pos.yaw(), 0.001f);
        // Calls a method
        assertEquals(0f, pos.pitch(), 0.001f);

        // Look to the right (x positive)
        // Calls a method
        pos = base.withDirection(new Vec(10, 0, 0));
        // Calls a method
        assertEquals(-90f, pos.yaw(), 0.001f);
        // Calls a method
        assertEquals(0f, pos.pitch(), 0.001f);

        // Look straight up
        // Calls a method
        pos = base.withDirection(new Vec(0, 10, 0));
        // Calls a method
        assertEquals(-90f, pos.pitch(), 0.001f);

        // Look straight down
        // Calls a method
        pos = base.withDirection(new Vec(0, -10, 0));
        // Calls a method
        assertEquals(90f, pos.pitch(), 0.001f);

        // Look at itself (edge case - x=0, z=0)
        // Calls a method
        pos = base.withDirection(Vec.ZERO);
        // Should default to looking down
        // Calls a method
        assertEquals(90f, pos.pitch(), 0.001f);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWithYaw() {
        // Calls a method
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with value
        // Calls a method
        Pos modified = base.withYaw(90f);
        // Calls a method
        assertEquals(10, modified.x());
        // Calls a method
        assertEquals(20, modified.y());
        // Calls a method
        assertEquals(30, modified.z());
        // Calls a method
        assertEquals(90f, modified.yaw());
        // Calls a method
        assertEquals(30f, modified.pitch());

        // Test with operator
        // Calls a method
        modified = base.withYaw(yaw -> yaw + 45f);
        // Calls a method
        assertEquals(90f, modified.yaw());
        // Calls a method
        assertEquals(30f, modified.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWithPitch() {
        // Calls a method
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with value
        // Calls a method
        Pos modified = base.withPitch(-45f);
        // Calls a method
        assertEquals(10, modified.x());
        // Calls a method
        assertEquals(20, modified.y());
        // Calls a method
        assertEquals(30, modified.z());
        // Calls a method
        assertEquals(45f, modified.yaw());
        // Calls a method
        assertEquals(-45f, modified.pitch());

        // Test with operator
        // Calls a method
        modified = base.withPitch(pitch -> pitch + 15f);
        // Calls a method
        assertEquals(45f, modified.yaw());
        // Calls a method
        assertEquals(45f, modified.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSameView() {
        // Calls a method
        Pos pos1 = new Pos(0, 0, 0, 45f, 30f);
        // Calls a method
        Pos pos2 = new Pos(10, 20, 30, 45f, 30f);
        // Calls a method
        Pos pos3 = new Pos(0, 0, 0, 46f, 30f);

        // Test with Pos
        // Calls a method
        assertTrue(pos1.sameView(pos2));
        // Calls a method
        assertFalse(pos1.sameView(pos3));

        // Test with values
        // Calls a method
        assertTrue(pos1.sameView(45f, 30f));
        // Calls a method
        assertFalse(pos1.sameView(46f, 30f));
        // Calls a method
        assertFalse(pos1.sameView(45f, 31f));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSimilarView() {
        // Calls a method
        Pos pos1 = new Pos(0, 0, 0, 45f, 30f);
        // Calls a method
        Pos pos2 = new Pos(0, 0, 0, 45.0001f, 30.0001f);
        // Calls a method
        Pos pos3 = new Pos(0, 0, 0, 46f, 31f);

        // Test with default epsilon
        // Calls a method
        assertTrue(pos1.similarView(pos2));
        // Calls a method
        assertFalse(pos1.similarView(pos3));

        // Test with custom epsilon
        // Calls a method
        assertTrue(pos1.similarView(pos3, 2f));
        // Calls a method
        assertFalse(pos1.similarView(pos3, 0.5f));

        // Test with yaw/pitch values
        // Calls a method
        assertTrue(pos1.similarView(45.0001f, 30.0001f));
        // Calls a method
        assertTrue(pos1.similarView(45.0001f, 30.0001f, VIEW_EPSILON));
        // Calls a method
        assertFalse(pos1.similarView(46f, 31f, 0.5f));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDirection() {
        // Facing south (default)
        // Calls a method
        Pos pos1 = new Pos(0, 0, 0, 0f, 0f);
        // Calls a method
        Vec dir1 = pos1.direction();
        // Calls a method
        assertEquals(0, dir1.x(), 0.0001);
        // Calls a method
        assertEquals(0, dir1.y(), 0.0001);
        // Calls a method
        assertEquals(1, dir1.z(), 0.0001);

        // Facing east
        // Calls a method
        Pos pos2 = new Pos(0, 0, 0, -90f, 0f);
        // Calls a method
        Vec dir2 = pos2.direction();
        // Calls a method
        assertEquals(1, dir2.x(), 0.0001);
        // Calls a method
        assertEquals(0, dir2.y(), 0.0001);
        // Calls a method
        assertEquals(0, dir2.z(), 0.0001);

        // Facing down
        // Calls a method
        Pos pos3 = new Pos(0, 0, 0, 0f, 90f);
        // Calls a method
        Vec dir3 = pos3.direction();
        // Calls a method
        assertEquals(0, dir3.x(), 0.0001);
        // Calls a method
        assertEquals(-1, dir3.y(), 0.0001);
        // Calls a method
        assertEquals(0, dir3.z(), 0.0001);

        // Direction should be unit vector
        // Calls a method
        assertTrue(dir1.isNormalized());
        // Calls a method
        assertTrue(dir2.isNormalized());
        // Calls a method
        assertTrue(dir3.isNormalized());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testLerpView() {
        // Calls a method
        Pos start = new Pos(0, 0, 0, 0f, 0f);
        // Calls a method
        Pos end = new Pos(0, 0, 0, 90f, 45f);

        // Halfway
        // Calls a method
        Pos mid = start.lerpView(end, 0.5f);
        // Calls a method
        assertEquals(0, mid.x());
        // Calls a method
        assertEquals(0, mid.y());
        // Calls a method
        assertEquals(0, mid.z());
        // Calls a method
        assertEquals(45f, mid.yaw(), VIEW_EPSILON);
        // Calls a method
        assertEquals(22.5f, mid.pitch(), VIEW_EPSILON);

        // At start
        // Calls a method
        Pos atStart = start.lerpView(end, 0);
        // Calls a method
        assertEquals(0f, atStart.yaw());
        // Calls a method
        assertEquals(0f, atStart.pitch());

        // At end
        // Calls a method
        Pos atEnd = start.lerpView(end, 1);
        // Calls a method
        assertEquals(90f, atEnd.yaw());
        // Calls a method
        assertEquals(45f, atEnd.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNegView() {
        // Calls a method
        Pos pos = new Pos(10, 20, 30, 45f, 30f);
        // Calls a method
        Pos negated = pos.negView();

        // Calls a method
        assertEquals(10, negated.x());
        // Calls a method
        assertEquals(20, negated.y());
        // Calls a method
        assertEquals(30, negated.z());
        // Calls a method
        assertEquals(-45f, negated.yaw());
        // Calls a method
        assertEquals(-30f, negated.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAbsView() {
        // Calls a method
        Pos pos = new Pos(10, 20, 30, -45f, -30f);
        // Calls a method
        Pos absolute = pos.absView();

        // Calls a method
        assertEquals(10, absolute.x());
        // Calls a method
        assertEquals(20, absolute.y());
        // Calls a method
        assertEquals(30, absolute.z());
        // Calls a method
        assertEquals(45f, absolute.yaw());
        // Calls a method
        assertEquals(30f, absolute.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testApply() {
        // Calls a method
        Pos pos = new Pos(10, 20, 30, 45f, 30f);

        // Test operator
        // Assigns a value
        Pos result = pos.apply((x, y, z, yaw, pitch) ->
            // Creates a new object
            new Pos(x * 2, y * 2, z * 2, yaw + 45f, pitch + 15f));

        // Calls a method
        assertEquals(20, result.x());
        // Calls a method
        assertEquals(40, result.y());
        // Calls a method
        assertEquals(60, result.z());
        // Calls a method
        assertEquals(90f, result.yaw());
        // Calls a method
        assertEquals(45f, result.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAsPos() {
        // Calls a method
        Point pos = new Pos(1, 2, 3, 45f, 30f);
        // Calls a method
        assertSame(pos, pos.asPos());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testAsPosWithView() {
        // Calls a method
        Pos pos = new Pos(1, 2, 3, 45f, 30f);
        // Calls a method
        Pos result = pos.asPos(90f, 60f);
        // Calls a method
        assertEquals(1, result.x());
        // Calls a method
        assertEquals(2, result.y());
        // Calls a method
        assertEquals(3, result.z());
        // Calls a method
        assertEquals(90f, result.yaw());
        // Calls a method
        assertEquals(60f, result.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArithmeticPreservesView() {
        // Calls a method
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test add
        // Calls a method
        Pos added = base.add(5, 10, 15);
        // Calls a method
        assertEquals(15, added.x());
        // Calls a method
        assertEquals(30, added.y());
        // Calls a method
        assertEquals(45, added.z());
        // Calls a method
        assertEquals(45f, added.yaw());
        // Calls a method
        assertEquals(30f, added.pitch());

        // Test sub
        // Calls a method
        Pos subtracted = base.sub(5, 10, 15);
        // Calls a method
        assertEquals(5, subtracted.x());
        // Calls a method
        assertEquals(10, subtracted.y());
        // Calls a method
        assertEquals(15, subtracted.z());
        // Calls a method
        assertEquals(45f, subtracted.yaw());
        // Calls a method
        assertEquals(30f, subtracted.pitch());

        // Test mul
        // Calls a method
        Pos multiplied = base.mul(2);
        // Calls a method
        assertEquals(20, multiplied.x());
        // Calls a method
        assertEquals(40, multiplied.y());
        // Calls a method
        assertEquals(60, multiplied.z());
        // Calls a method
        assertEquals(45f, multiplied.yaw());
        // Calls a method
        assertEquals(30f, multiplied.pitch());

        // Test div
        // Calls a method
        Pos divided = base.div(2);
        // Calls a method
        assertEquals(5, divided.x());
        // Calls a method
        assertEquals(10, divided.y());
        // Calls a method
        assertEquals(15, divided.z());
        // Calls a method
        assertEquals(45f, divided.yaw());
        // Calls a method
        assertEquals(30f, divided.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTransformationsPreserveView() {
        // Calls a method
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test neg
        // Calls a method
        Pos negated = base.neg();
        // Calls a method
        assertEquals(-10, negated.x());
        // Calls a method
        assertEquals(-20, negated.y());
        // Calls a method
        assertEquals(-30, negated.z());
        // Calls a method
        assertEquals(45f, negated.yaw());
        // Calls a method
        assertEquals(30f, negated.pitch());

        // Test abs
        // Calls a method
        Pos absolute = negated.abs();
        // Calls a method
        assertEquals(10, absolute.x());
        // Calls a method
        assertEquals(20, absolute.y());
        // Calls a method
        assertEquals(30, absolute.z());
        // Calls a method
        assertEquals(45f, absolute.yaw());
        // Calls a method
        assertEquals(30f, absolute.pitch());

        // Test normalize
        // Calls a method
        Pos normalized = base.normalize();
        // Calls a method
        assertTrue(normalized.isNormalized());
        // Calls a method
        assertEquals(45f, normalized.yaw());
        // Calls a method
        assertEquals(30f, normalized.pitch());

        // Test cross
        // Calls a method
        Pos other = new Pos(1, 0, 0);
        // Calls a method
        Pos crossed = base.cross(other);
        // Calls a method
        assertEquals(45f, crossed.yaw());
        // Calls a method
        assertEquals(30f, crossed.pitch());

        // Test lerp
        // Calls a method
        Pos end = new Pos(20, 40, 60);
        // Calls a method
        Pos lerped = base.lerp(end, 0.5);
        // Calls a method
        assertEquals(45f, lerped.yaw());
        // Calls a method
        assertEquals(30f, lerped.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRandomPositions() {
        // Calls a method
        Random random = new Random(98765L);

        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            double x = random.nextDouble() * 200 - 100;
            // Calls a method
            double y = random.nextDouble() * 200 - 100;
            // Calls a method
            double z = random.nextDouble() * 200 - 100;
            // Calls a method
            float yaw = random.nextFloat() * 720 - 360;
            // Calls a method
            float pitch = random.nextFloat() * 200 - 100;

            // Calls a method
            Pos pos = new Pos(x, y, z, yaw, pitch);
            // Calls a method
            assertEquals(x, pos.x(), 0.0001);
            // Calls a method
            assertEquals(y, pos.y(), 0.0001);
            // Calls a method
            assertEquals(z, pos.z(), 0.0001);

            // Verify yaw is normalized
            // Calls a method
            assertTrue(pos.yaw() >= -180f && pos.yaw() <= 180f);
            // Verify pitch is clamped
            // Calls a method
            assertTrue(pos.pitch() >= -90f && pos.pitch() <= 90f);

            // Test immutability
            // Assigns a value
            Pos original = pos;
            // Calls a method
            Pos modified = pos.withCoord(0, 0, 0);
            // Calls a method
            assertEquals(x, original.x(), 0.0001);
            // Calls a method
            assertEquals(0, modified.x(), 0.0001);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMinMaxPreserveView() {
        // Calls a method
        Pos pos1 = new Pos(10, 20, 30, 45f, 30f);
        // Calls a method
        Pos pos2 = new Pos(15, 5, 25);

        // Test min
        // Calls a method
        Pos min = pos1.min(pos2);
        // Calls a method
        assertEquals(10, min.x());
        // Calls a method
        assertEquals(5, min.y());
        // Calls a method
        assertEquals(25, min.z());
        // Calls a method
        assertEquals(45f, min.yaw());
        // Calls a method
        assertEquals(30f, min.pitch());

        // Test max
        // Calls a method
        Pos max = pos1.max(pos2);
        // Calls a method
        assertEquals(15, max.x());
        // Calls a method
        assertEquals(20, max.y());
        // Calls a method
        assertEquals(30, max.z());
        // Calls a method
        assertEquals(45f, max.yaw());
        // Calls a method
        assertEquals(30f, max.pitch());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testViewEdgeCases() {
        // Test exact boundaries
        // Calls a method
        Pos pos1 = new Pos(0, 0, 0, 180f, 90f);
        // Calls a method
        assertEquals(180f, pos1.yaw());
        // Calls a method
        assertEquals(90f, pos1.pitch());

        // Calls a method
        Pos pos2 = new Pos(0, 0, 0, -180f, -90f);
        // Calls a method
        assertEquals(180f, pos2.yaw());
        // Calls a method
        assertEquals(-90f, pos2.pitch());

        // Test just outside boundaries
        // Calls a method
        Pos pos3 = new Pos(0, 0, 0, 180.1f, 90.1f);
        // Calls a method
        assertTrue(pos3.yaw() >= -180f && pos3.yaw() <= 180f);
        // Calls a method
        assertEquals(90f, pos3.pitch());

        // Calls a method
        Pos pos4 = new Pos(0, 0, 0, -180.1f, -90.1f);
        // Calls a method
        assertTrue(pos4.yaw() >= -180f && pos4.yaw() <= 180f);
        // Calls a method
        assertEquals(-90f, pos4.pitch());
    // End of a block/expression
    }
// End of a block/expression
}

