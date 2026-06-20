// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static net.minestom.server.coordinate.Point.EPSILON;
// Import statique d'un membre
import static net.minestom.server.coordinate.Pos.VIEW_EPSILON;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class PosTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testConstructors() {
        // Test full constructor
        // Appelle une méthode
        Pos pos1 = new Pos(1.5, 2.5, 3.5, 45f, 30f);
        // Appelle une méthode
        assertEquals(1.5, pos1.x());
        // Appelle une méthode
        assertEquals(2.5, pos1.y());
        // Appelle une méthode
        assertEquals(3.5, pos1.z());
        // Appelle une méthode
        assertEquals(45f, pos1.yaw());
        // Appelle une méthode
        assertEquals(30f, pos1.pitch());

        // Test coordinate-only constructor
        // Appelle une méthode
        Pos pos2 = new Pos(1.5, 2.5, 3.5);
        // Appelle une méthode
        assertEquals(1.5, pos2.x());
        // Appelle une méthode
        assertEquals(2.5, pos2.y());
        // Appelle une méthode
        assertEquals(3.5, pos2.z());
        // Appelle une méthode
        assertEquals(0f, pos2.yaw());
        // Appelle une méthode
        assertEquals(0f, pos2.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testYawPitchNormalization() {
        // Test yaw wrapping
        // Appelle une méthode
        Pos pos1 = new Pos(0, 0, 0, 200f, 0f);
        // Appelle une méthode
        assertEquals(-160f, pos1.yaw(), EPSILON);

        // Appelle une méthode
        Pos pos2 = new Pos(0, 0, 0, -200f, 0f);
        // Appelle une méthode
        assertEquals(160f, pos2.yaw(), EPSILON);

        // Appelle une méthode
        Pos pos3 = new Pos(0, 0, 0, 720f, 0f);
        // Appelle une méthode
        assertEquals(0f, pos3.yaw(), EPSILON);

        // Test pitch clamping
        // Appelle une méthode
        Pos pos4 = new Pos(0, 0, 0, 0f, 100f);
        // Appelle une méthode
        assertEquals(90f, pos4.pitch());

        // Appelle une méthode
        Pos pos5 = new Pos(0, 0, 0, 0f, -100f);
        // Appelle une méthode
        assertEquals(-90f, pos5.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testFixYaw() {
        // Appelle une méthode
        assertEquals(0f, Pos.fixYaw(0f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(90f, Pos.fixYaw(90f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(-90f, Pos.fixYaw(-90f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(180f, Pos.fixYaw(180f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(180f, Pos.fixYaw(-180f), VIEW_EPSILON);

        // Test wrapping
        // Appelle une méthode
        assertEquals(-160f, Pos.fixYaw(200f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(160f, Pos.fixYaw(-200f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(0f, Pos.fixYaw(360f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(0f, Pos.fixYaw(720f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(85f, Pos.fixYaw(-1355f), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(-135f, Pos.fixYaw(225f), VIEW_EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testFixPitch() {
        // Appelle une méthode
        assertEquals(0f, Pos.fixPitch(0f));
        // Appelle une méthode
        assertEquals(45f, Pos.fixPitch(45f));
        // Appelle une méthode
        assertEquals(-45f, Pos.fixPitch(-45f));

        // Test clamping
        // Appelle une méthode
        assertEquals(90f, Pos.fixPitch(90f));
        // Appelle une méthode
        assertEquals(-90f, Pos.fixPitch(-90f));
        // Appelle une méthode
        assertEquals(90f, Pos.fixPitch(100f));
        // Appelle une méthode
        assertEquals(-90f, Pos.fixPitch(-100f));
        // Appelle une méthode
        assertEquals(90f, Pos.fixPitch(225f));
        // Appelle une méthode
        assertEquals(-90f, Pos.fixPitch(-135f));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWithCoord() {
        // Appelle une méthode
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with coordinates
        // Appelle une méthode
        Pos modified = base.withCoord(15, 25, 35);
        // Appelle une méthode
        assertEquals(15, modified.x());
        // Appelle une méthode
        assertEquals(25, modified.y());
        // Appelle une méthode
        assertEquals(35, modified.z());
        // Appelle une méthode
        assertEquals(45f, modified.yaw());
        // Appelle une méthode
        assertEquals(30f, modified.pitch());

        // Test with Point
        // Appelle une méthode
        Vec point = new Vec(5, 10, 15);
        // Appelle une méthode
        modified = base.withCoord(point);
        // Appelle une méthode
        assertEquals(5, modified.x());
        // Appelle une méthode
        assertEquals(10, modified.y());
        // Appelle une méthode
        assertEquals(15, modified.z());
        // Appelle une méthode
        assertEquals(45f, modified.yaw());
        // Appelle une méthode
        assertEquals(30f, modified.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWithView() {
        // Appelle une méthode
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with yaw/pitch
        // Appelle une méthode
        Pos modified = base.withView(90f, -45f);
        // Appelle une méthode
        assertEquals(10, modified.x());
        // Appelle une méthode
        assertEquals(20, modified.y());
        // Appelle une méthode
        assertEquals(30, modified.z());
        // Appelle une méthode
        assertEquals(90f, modified.yaw());
        // Appelle une méthode
        assertEquals(-45f, modified.pitch());

        // Test with Pos
        // Appelle une méthode
        Pos other = new Pos(0, 0, 0, 120f, -60f);
        // Appelle une méthode
        modified = base.withView(other);
        // Appelle une méthode
        assertEquals(10, modified.x());
        // Appelle une méthode
        assertEquals(20, modified.y());
        // Appelle une méthode
        assertEquals(30, modified.z());
        // Appelle une méthode
        assertEquals(120f, modified.yaw());
        // Appelle une méthode
        assertEquals(-60f, modified.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWithDirection() {
        // Appelle une méthode
        Pos base = new Pos(0, 0, 0);

        // Look straight ahead (z positive)
        // Appelle une méthode
        Pos pos = base.withDirection(new Vec(0, 0, 10));
        // Appelle une méthode
        assertEquals(0f, pos.yaw(), 0.001f);
        // Appelle une méthode
        assertEquals(0f, pos.pitch(), 0.001f);

        // Look to the right (x positive)
        // Appelle une méthode
        pos = base.withDirection(new Vec(10, 0, 0));
        // Appelle une méthode
        assertEquals(-90f, pos.yaw(), 0.001f);
        // Appelle une méthode
        assertEquals(0f, pos.pitch(), 0.001f);

        // Look straight up
        // Appelle une méthode
        pos = base.withDirection(new Vec(0, 10, 0));
        // Appelle une méthode
        assertEquals(-90f, pos.pitch(), 0.001f);

        // Look straight down
        // Appelle une méthode
        pos = base.withDirection(new Vec(0, -10, 0));
        // Appelle une méthode
        assertEquals(90f, pos.pitch(), 0.001f);

        // Look at itself (edge case - x=0, z=0)
        // Appelle une méthode
        pos = base.withDirection(Vec.ZERO);
        // Should default to looking down
        // Appelle une méthode
        assertEquals(90f, pos.pitch(), 0.001f);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWithYaw() {
        // Appelle une méthode
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with value
        // Appelle une méthode
        Pos modified = base.withYaw(90f);
        // Appelle une méthode
        assertEquals(10, modified.x());
        // Appelle une méthode
        assertEquals(20, modified.y());
        // Appelle une méthode
        assertEquals(30, modified.z());
        // Appelle une méthode
        assertEquals(90f, modified.yaw());
        // Appelle une méthode
        assertEquals(30f, modified.pitch());

        // Test with operator
        // Appelle une méthode
        modified = base.withYaw(yaw -> yaw + 45f);
        // Appelle une méthode
        assertEquals(90f, modified.yaw());
        // Appelle une méthode
        assertEquals(30f, modified.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWithPitch() {
        // Appelle une méthode
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test with value
        // Appelle une méthode
        Pos modified = base.withPitch(-45f);
        // Appelle une méthode
        assertEquals(10, modified.x());
        // Appelle une méthode
        assertEquals(20, modified.y());
        // Appelle une méthode
        assertEquals(30, modified.z());
        // Appelle une méthode
        assertEquals(45f, modified.yaw());
        // Appelle une méthode
        assertEquals(-45f, modified.pitch());

        // Test with operator
        // Appelle une méthode
        modified = base.withPitch(pitch -> pitch + 15f);
        // Appelle une méthode
        assertEquals(45f, modified.yaw());
        // Appelle une méthode
        assertEquals(45f, modified.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSameView() {
        // Appelle une méthode
        Pos pos1 = new Pos(0, 0, 0, 45f, 30f);
        // Appelle une méthode
        Pos pos2 = new Pos(10, 20, 30, 45f, 30f);
        // Appelle une méthode
        Pos pos3 = new Pos(0, 0, 0, 46f, 30f);

        // Test with Pos
        // Appelle une méthode
        assertTrue(pos1.sameView(pos2));
        // Appelle une méthode
        assertFalse(pos1.sameView(pos3));

        // Test with values
        // Appelle une méthode
        assertTrue(pos1.sameView(45f, 30f));
        // Appelle une méthode
        assertFalse(pos1.sameView(46f, 30f));
        // Appelle une méthode
        assertFalse(pos1.sameView(45f, 31f));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSimilarView() {
        // Appelle une méthode
        Pos pos1 = new Pos(0, 0, 0, 45f, 30f);
        // Appelle une méthode
        Pos pos2 = new Pos(0, 0, 0, 45.0001f, 30.0001f);
        // Appelle une méthode
        Pos pos3 = new Pos(0, 0, 0, 46f, 31f);

        // Test with default epsilon
        // Appelle une méthode
        assertTrue(pos1.similarView(pos2));
        // Appelle une méthode
        assertFalse(pos1.similarView(pos3));

        // Test with custom epsilon
        // Appelle une méthode
        assertTrue(pos1.similarView(pos3, 2f));
        // Appelle une méthode
        assertFalse(pos1.similarView(pos3, 0.5f));

        // Test with yaw/pitch values
        // Appelle une méthode
        assertTrue(pos1.similarView(45.0001f, 30.0001f));
        // Appelle une méthode
        assertTrue(pos1.similarView(45.0001f, 30.0001f, VIEW_EPSILON));
        // Appelle une méthode
        assertFalse(pos1.similarView(46f, 31f, 0.5f));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDirection() {
        // Facing south (default)
        // Appelle une méthode
        Pos pos1 = new Pos(0, 0, 0, 0f, 0f);
        // Appelle une méthode
        Vec dir1 = pos1.direction();
        // Appelle une méthode
        assertEquals(0, dir1.x(), 0.0001);
        // Appelle une méthode
        assertEquals(0, dir1.y(), 0.0001);
        // Appelle une méthode
        assertEquals(1, dir1.z(), 0.0001);

        // Facing east
        // Appelle une méthode
        Pos pos2 = new Pos(0, 0, 0, -90f, 0f);
        // Appelle une méthode
        Vec dir2 = pos2.direction();
        // Appelle une méthode
        assertEquals(1, dir2.x(), 0.0001);
        // Appelle une méthode
        assertEquals(0, dir2.y(), 0.0001);
        // Appelle une méthode
        assertEquals(0, dir2.z(), 0.0001);

        // Facing down
        // Appelle une méthode
        Pos pos3 = new Pos(0, 0, 0, 0f, 90f);
        // Appelle une méthode
        Vec dir3 = pos3.direction();
        // Appelle une méthode
        assertEquals(0, dir3.x(), 0.0001);
        // Appelle une méthode
        assertEquals(-1, dir3.y(), 0.0001);
        // Appelle une méthode
        assertEquals(0, dir3.z(), 0.0001);

        // Direction should be unit vector
        // Appelle une méthode
        assertTrue(dir1.isNormalized());
        // Appelle une méthode
        assertTrue(dir2.isNormalized());
        // Appelle une méthode
        assertTrue(dir3.isNormalized());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testLerpView() {
        // Appelle une méthode
        Pos start = new Pos(0, 0, 0, 0f, 0f);
        // Appelle une méthode
        Pos end = new Pos(0, 0, 0, 90f, 45f);

        // Halfway
        // Appelle une méthode
        Pos mid = start.lerpView(end, 0.5f);
        // Appelle une méthode
        assertEquals(0, mid.x());
        // Appelle une méthode
        assertEquals(0, mid.y());
        // Appelle une méthode
        assertEquals(0, mid.z());
        // Appelle une méthode
        assertEquals(45f, mid.yaw(), VIEW_EPSILON);
        // Appelle une méthode
        assertEquals(22.5f, mid.pitch(), VIEW_EPSILON);

        // At start
        // Appelle une méthode
        Pos atStart = start.lerpView(end, 0);
        // Appelle une méthode
        assertEquals(0f, atStart.yaw());
        // Appelle une méthode
        assertEquals(0f, atStart.pitch());

        // At end
        // Appelle une méthode
        Pos atEnd = start.lerpView(end, 1);
        // Appelle une méthode
        assertEquals(90f, atEnd.yaw());
        // Appelle une méthode
        assertEquals(45f, atEnd.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNegView() {
        // Appelle une méthode
        Pos pos = new Pos(10, 20, 30, 45f, 30f);
        // Appelle une méthode
        Pos negated = pos.negView();

        // Appelle une méthode
        assertEquals(10, negated.x());
        // Appelle une méthode
        assertEquals(20, negated.y());
        // Appelle une méthode
        assertEquals(30, negated.z());
        // Appelle une méthode
        assertEquals(-45f, negated.yaw());
        // Appelle une méthode
        assertEquals(-30f, negated.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAbsView() {
        // Appelle une méthode
        Pos pos = new Pos(10, 20, 30, -45f, -30f);
        // Appelle une méthode
        Pos absolute = pos.absView();

        // Appelle une méthode
        assertEquals(10, absolute.x());
        // Appelle une méthode
        assertEquals(20, absolute.y());
        // Appelle une méthode
        assertEquals(30, absolute.z());
        // Appelle une méthode
        assertEquals(45f, absolute.yaw());
        // Appelle une méthode
        assertEquals(30f, absolute.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testApply() {
        // Appelle une méthode
        Pos pos = new Pos(10, 20, 30, 45f, 30f);

        // Test operator
        // Affecte une valeur
        Pos result = pos.apply((x, y, z, yaw, pitch) ->
            // Crée un nouvel objet
            new Pos(x * 2, y * 2, z * 2, yaw + 45f, pitch + 15f));

        // Appelle une méthode
        assertEquals(20, result.x());
        // Appelle une méthode
        assertEquals(40, result.y());
        // Appelle une méthode
        assertEquals(60, result.z());
        // Appelle une méthode
        assertEquals(90f, result.yaw());
        // Appelle une méthode
        assertEquals(45f, result.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAsPos() {
        // Appelle une méthode
        Point pos = new Pos(1, 2, 3, 45f, 30f);
        // Appelle une méthode
        assertSame(pos, pos.asPos());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAsPosWithView() {
        // Appelle une méthode
        Pos pos = new Pos(1, 2, 3, 45f, 30f);
        // Appelle une méthode
        Pos result = pos.asPos(90f, 60f);
        // Appelle une méthode
        assertEquals(1, result.x());
        // Appelle une méthode
        assertEquals(2, result.y());
        // Appelle une méthode
        assertEquals(3, result.z());
        // Appelle une méthode
        assertEquals(90f, result.yaw());
        // Appelle une méthode
        assertEquals(60f, result.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArithmeticPreservesView() {
        // Appelle une méthode
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test add
        // Appelle une méthode
        Pos added = base.add(5, 10, 15);
        // Appelle une méthode
        assertEquals(15, added.x());
        // Appelle une méthode
        assertEquals(30, added.y());
        // Appelle une méthode
        assertEquals(45, added.z());
        // Appelle une méthode
        assertEquals(45f, added.yaw());
        // Appelle une méthode
        assertEquals(30f, added.pitch());

        // Test sub
        // Appelle une méthode
        Pos subtracted = base.sub(5, 10, 15);
        // Appelle une méthode
        assertEquals(5, subtracted.x());
        // Appelle une méthode
        assertEquals(10, subtracted.y());
        // Appelle une méthode
        assertEquals(15, subtracted.z());
        // Appelle une méthode
        assertEquals(45f, subtracted.yaw());
        // Appelle une méthode
        assertEquals(30f, subtracted.pitch());

        // Test mul
        // Appelle une méthode
        Pos multiplied = base.mul(2);
        // Appelle une méthode
        assertEquals(20, multiplied.x());
        // Appelle une méthode
        assertEquals(40, multiplied.y());
        // Appelle une méthode
        assertEquals(60, multiplied.z());
        // Appelle une méthode
        assertEquals(45f, multiplied.yaw());
        // Appelle une méthode
        assertEquals(30f, multiplied.pitch());

        // Test div
        // Appelle une méthode
        Pos divided = base.div(2);
        // Appelle une méthode
        assertEquals(5, divided.x());
        // Appelle une méthode
        assertEquals(10, divided.y());
        // Appelle une méthode
        assertEquals(15, divided.z());
        // Appelle une méthode
        assertEquals(45f, divided.yaw());
        // Appelle une méthode
        assertEquals(30f, divided.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTransformationsPreserveView() {
        // Appelle une méthode
        Pos base = new Pos(10, 20, 30, 45f, 30f);

        // Test neg
        // Appelle une méthode
        Pos negated = base.neg();
        // Appelle une méthode
        assertEquals(-10, negated.x());
        // Appelle une méthode
        assertEquals(-20, negated.y());
        // Appelle une méthode
        assertEquals(-30, negated.z());
        // Appelle une méthode
        assertEquals(45f, negated.yaw());
        // Appelle une méthode
        assertEquals(30f, negated.pitch());

        // Test abs
        // Appelle une méthode
        Pos absolute = negated.abs();
        // Appelle une méthode
        assertEquals(10, absolute.x());
        // Appelle une méthode
        assertEquals(20, absolute.y());
        // Appelle une méthode
        assertEquals(30, absolute.z());
        // Appelle une méthode
        assertEquals(45f, absolute.yaw());
        // Appelle une méthode
        assertEquals(30f, absolute.pitch());

        // Test normalize
        // Appelle une méthode
        Pos normalized = base.normalize();
        // Appelle une méthode
        assertTrue(normalized.isNormalized());
        // Appelle une méthode
        assertEquals(45f, normalized.yaw());
        // Appelle une méthode
        assertEquals(30f, normalized.pitch());

        // Test cross
        // Appelle une méthode
        Pos other = new Pos(1, 0, 0);
        // Appelle une méthode
        Pos crossed = base.cross(other);
        // Appelle une méthode
        assertEquals(45f, crossed.yaw());
        // Appelle une méthode
        assertEquals(30f, crossed.pitch());

        // Test lerp
        // Appelle une méthode
        Pos end = new Pos(20, 40, 60);
        // Appelle une méthode
        Pos lerped = base.lerp(end, 0.5);
        // Appelle une méthode
        assertEquals(45f, lerped.yaw());
        // Appelle une méthode
        assertEquals(30f, lerped.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRandomPositions() {
        // Appelle une méthode
        Random random = new Random(98765L);

        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            double x = random.nextDouble() * 200 - 100;
            // Appelle une méthode
            double y = random.nextDouble() * 200 - 100;
            // Appelle une méthode
            double z = random.nextDouble() * 200 - 100;
            // Appelle une méthode
            float yaw = random.nextFloat() * 720 - 360;
            // Appelle une méthode
            float pitch = random.nextFloat() * 200 - 100;

            // Appelle une méthode
            Pos pos = new Pos(x, y, z, yaw, pitch);
            // Appelle une méthode
            assertEquals(x, pos.x(), 0.0001);
            // Appelle une méthode
            assertEquals(y, pos.y(), 0.0001);
            // Appelle une méthode
            assertEquals(z, pos.z(), 0.0001);

            // Verify yaw is normalized
            // Appelle une méthode
            assertTrue(pos.yaw() >= -180f && pos.yaw() <= 180f);
            // Verify pitch is clamped
            // Appelle une méthode
            assertTrue(pos.pitch() >= -90f && pos.pitch() <= 90f);

            // Test immutability
            // Affecte une valeur
            Pos original = pos;
            // Appelle une méthode
            Pos modified = pos.withCoord(0, 0, 0);
            // Appelle une méthode
            assertEquals(x, original.x(), 0.0001);
            // Appelle une méthode
            assertEquals(0, modified.x(), 0.0001);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMinMaxPreserveView() {
        // Appelle une méthode
        Pos pos1 = new Pos(10, 20, 30, 45f, 30f);
        // Appelle une méthode
        Pos pos2 = new Pos(15, 5, 25);

        // Test min
        // Appelle une méthode
        Pos min = pos1.min(pos2);
        // Appelle une méthode
        assertEquals(10, min.x());
        // Appelle une méthode
        assertEquals(5, min.y());
        // Appelle une méthode
        assertEquals(25, min.z());
        // Appelle une méthode
        assertEquals(45f, min.yaw());
        // Appelle une méthode
        assertEquals(30f, min.pitch());

        // Test max
        // Appelle une méthode
        Pos max = pos1.max(pos2);
        // Appelle une méthode
        assertEquals(15, max.x());
        // Appelle une méthode
        assertEquals(20, max.y());
        // Appelle une méthode
        assertEquals(30, max.z());
        // Appelle une méthode
        assertEquals(45f, max.yaw());
        // Appelle une méthode
        assertEquals(30f, max.pitch());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testViewEdgeCases() {
        // Test exact boundaries
        // Appelle une méthode
        Pos pos1 = new Pos(0, 0, 0, 180f, 90f);
        // Appelle une méthode
        assertEquals(180f, pos1.yaw());
        // Appelle une méthode
        assertEquals(90f, pos1.pitch());

        // Appelle une méthode
        Pos pos2 = new Pos(0, 0, 0, -180f, -90f);
        // Appelle une méthode
        assertEquals(180f, pos2.yaw());
        // Appelle une méthode
        assertEquals(-90f, pos2.pitch());

        // Test just outside boundaries
        // Appelle une méthode
        Pos pos3 = new Pos(0, 0, 0, 180.1f, 90.1f);
        // Appelle une méthode
        assertTrue(pos3.yaw() >= -180f && pos3.yaw() <= 180f);
        // Appelle une méthode
        assertEquals(90f, pos3.pitch());

        // Appelle une méthode
        Pos pos4 = new Pos(0, 0, 0, -180.1f, -90.1f);
        // Appelle une méthode
        assertTrue(pos4.yaw() >= -180f && pos4.yaw() <= 180f);
        // Appelle une méthode
        assertEquals(-90f, pos4.pitch());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

