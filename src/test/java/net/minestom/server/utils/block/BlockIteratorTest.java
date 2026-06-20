// Package declaration for this file
package net.minestom.server.utils.block;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class BlockIteratorTest {
    // Start of a method/block
    private void assertContains(List<Point> points, Point point) {
        // Calls a method
        assertTrue(points.contains(point), "Expected " + points + " to contain " + point);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dOffsetppp() {
        // Calls a method
        Vec s = new Vec(0, 0.1, 0);
        // Calls a method
        Vec e = new Vec(2, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(1, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(1, 1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(2, 1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(3, 1, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dOffsetppn() {
        // Calls a method
        Vec s = new Vec(0, 0.1, 0);
        // Calls a method
        Vec e = new Vec(-2, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-1, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-2, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-2, 1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-3, 1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-4, 1, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dOffsetnpp() {
        // Calls a method
        Vec s = new Vec(0, -0.1, 0);
        // Calls a method
        Vec e = new Vec(2, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        assertEquals(new Vec(0, -1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(1, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(2, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(2, 1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(3, 1, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dOffsetnnp() {
        // Calls a method
        Vec s = new Vec(0, -0.1, 0);
        // Calls a method
        Vec e = new Vec(-2, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        assertEquals(new Vec(0, -1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-1, -1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-1, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-2, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-3, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-3, 1, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(-4, 1, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testZeroVelocity() {
        // Calls a method
        Vec s = new Vec(0, 0, 0);
        // Calls a method
        Vec e = new Vec(0, 0, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testLongDistance() {
        // Calls a method
        Vec s = new Vec(42.5, 0, 51.5);
        // Calls a method
        Vec e = new Vec(-12, 0, -36);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 37);

        // Calls a method
        List<Point> points = new ArrayList<>();
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            points.add(iterator.next());
        // End of a block/expression
        }

        // Assigns a value
        Point[] validPoints = new Point[]{
                // Creates a new object
                new Vec(42.0, 0.0, 51.0),
                // Creates a new object
                new Vec(42.0, 0.0, 50.0),
                // Creates a new object
                new Vec(41.0, 0.0, 50.0),
                // Creates a new object
                new Vec(42.0, 0.0, 49.0),
                // Creates a new object
                new Vec(41.0, 0.0, 49.0),
                // Creates a new object
                new Vec(41.0, 0.0, 48.0),
                // Creates a new object
                new Vec(41.0, 0.0, 47.0),
                // Creates a new object
                new Vec(40.0, 0.0, 47.0),
                // Creates a new object
                new Vec(41.0, 0.0, 46.0),
                // Creates a new object
                new Vec(40.0, 0.0, 46.0),
                // Creates a new object
                new Vec(40.0, 0.0, 45.0),
                // Creates a new object
                new Vec(40.0, 0.0, 44.0),
                // Creates a new object
                new Vec(39.0, 0.0, 44.0),
                // Creates a new object
                new Vec(40.0, 0.0, 43.0),
                // Creates a new object
                new Vec(39.0, 0.0, 43.0),
                // Creates a new object
                new Vec(39.0, 0.0, 42.0),
                // Creates a new object
                new Vec(39.0, 0.0, 41.0),
                // Creates a new object
                new Vec(38.0, 0.0, 41.0),
                // Creates a new object
                new Vec(39.0, 0.0, 40.0),
                // Creates a new object
                new Vec(38.0, 0.0, 40.0),
                // Creates a new object
                new Vec(38.0, 0.0, 39.0),
                // Creates a new object
                new Vec(38.0, 0.0, 38.0),
                // Creates a new object
                new Vec(37.0, 0.0, 38.0),
                // Creates a new object
                new Vec(38.0, 0.0, 37.0),
                // Creates a new object
                new Vec(37.0, 0.0, 37.0),
                // Creates a new object
                new Vec(37.0, 0.0, 36.0),
                // Creates a new object
                new Vec(37.0, 0.0, 35.0),
                // Creates a new object
                new Vec(36.0, 0.0, 35.0),
                // Creates a new object
                new Vec(37.0, 0.0, 34.0),
                // Creates a new object
                new Vec(36.0, 0.0, 34.0),
                // Creates a new object
                new Vec(36.0, 0.0, 33.0),
                // Creates a new object
                new Vec(36.0, 0.0, 32.0),
                // Creates a new object
                new Vec(35.0, 0.0, 32.0),
                // Creates a new object
                new Vec(36.0, 0.0, 31.0),
                // Creates a new object
                new Vec(35.0, 0.0, 31.0),
                // Creates a new object
                new Vec(35.0, 0.0, 30.0),
                // Creates a new object
                new Vec(35.0, 0.0, 29.0),
                // Creates a new object
                new Vec(34.0, 0.0, 29.0),
                // Creates a new object
                new Vec(35.0, 0.0, 28.0),
                // Creates a new object
                new Vec(34.0, 0.0, 28.0),
                // Creates a new object
                new Vec(34.0, 0.0, 27.0),
                // Creates a new object
                new Vec(34.0, 0.0, 26.0),
                // Creates a new object
                new Vec(33.0, 0.0, 26.0),
                // Creates a new object
                new Vec(34.0, 0.0, 25.0),
                // Creates a new object
                new Vec(33.0, 0.0, 25.0),
                // Creates a new object
                new Vec(33.0, 0.0, 24.0),
                // Creates a new object
                new Vec(33.0, 0.0, 23.0),
                // Creates a new object
                new Vec(32.0, 0.0, 23.0),
                // Creates a new object
                new Vec(33.0, 0.0, 22.0),
                // Creates a new object
                new Vec(32.0, 0.0, 22.0),
                // Creates a new object
                new Vec(32.0, 0.0, 21.0),
                // Creates a new object
                new Vec(32.0, 0.0, 20.0),
                // Creates a new object
                new Vec(31.0, 0.0, 20.0),
                // Creates a new object
                new Vec(32.0, 0.0, 19.0),
                // Creates a new object
                new Vec(31.0, 0.0, 19.0),
                // Creates a new object
                new Vec(31.0, 0.0, 18.0),
                // Creates a new object
                new Vec(31.0, 0.0, 17.0),
                // Creates a new object
                new Vec(30.0, 0.0, 17.0),
                // Creates a new object
                new Vec(31.0, 0.0, 16.0),
                // Creates a new object
                new Vec(30.0, 0.0, 16.0)
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point p : validPoints) {
            // Calls a method
            assertContains(points, p);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(validPoints.length, points.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSkipping() {
        // Calls a method
        Vec s = new Vec(0.5, 40, 0.5);
        // Calls a method
        Vec e = new Vec(27, 0, 21);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 34);

        // Calls a method
        List<Point> points = new ArrayList<>();
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            points.add(iterator.next());
        // End of a block/expression
        }

        // Assigns a value
        Point[] validPoints = new Point[]{
                // Creates a new object
                new Vec(0.0, 40.0, 0.0),
                // Creates a new object
                new Vec(1.0, 40.0, 0.0),
                // Creates a new object
                new Vec(1.0, 40.0, 1.0),
                // Creates a new object
                new Vec(2.0, 40.0, 1.0),
                // Creates a new object
                new Vec(2.0, 40.0, 2.0),
                // Creates a new object
                new Vec(3.0, 40.0, 2.0),
                // Creates a new object
                new Vec(3.0, 40.0, 3.0),
                // Creates a new object
                new Vec(4.0, 40.0, 3.0),
                // Creates a new object
                new Vec(5.0, 40.0, 3.0),
                // Creates a new object
                new Vec(4.0, 40.0, 4.0),
                // Creates a new object
                new Vec(5.0, 40.0, 4.0),
                // Creates a new object
                new Vec(6.0, 40.0, 4.0),
                // Creates a new object
                new Vec(6.0, 40.0, 5.0),
                // Creates a new object
                new Vec(7.0, 40.0, 5.0),
                // Creates a new object
                new Vec(7.0, 40.0, 6.0),
                // Creates a new object
                new Vec(8.0, 40.0, 6.0),
                // Creates a new object
                new Vec(8.0, 40.0, 7.0),
                // Creates a new object
                new Vec(9.0, 40.0, 7.0),
                // Creates a new object
                new Vec(10.0, 40.0, 7.0),
                // Creates a new object
                new Vec(10.0, 40.0, 8.0),
                // Creates a new object
                new Vec(11.0, 40.0, 8.0),
                // Creates a new object
                new Vec(11.0, 40.0, 9.0),
                // Creates a new object
                new Vec(12.0, 40.0, 9.0),
                // Creates a new object
                new Vec(12.0, 40.0, 10.0),
                // Creates a new object
                new Vec(13.0, 40.0, 10.0),
                // Creates a new object
                new Vec(14.0, 40.0, 10.0),
                // Creates a new object
                new Vec(13.0, 40.0, 11.0),
                // Creates a new object
                new Vec(14.0, 40.0, 11.0),
                // Creates a new object
                new Vec(15.0, 40.0, 11.0),
                // Creates a new object
                new Vec(15.0, 40.0, 12.0),
                // Creates a new object
                new Vec(16.0, 40.0, 12.0),
                // Creates a new object
                new Vec(16.0, 40.0, 13.0),
                // Creates a new object
                new Vec(17.0, 40.0, 13.0),
                // Creates a new object
                new Vec(17.0, 40.0, 14.0),
                // Creates a new object
                new Vec(18.0, 40.0, 14.0),
                // Creates a new object
                new Vec(19.0, 40.0, 14.0),
                // Creates a new object
                new Vec(19.0, 40.0, 15.0),
                // Creates a new object
                new Vec(20.0, 40.0, 15.0),
                // Creates a new object
                new Vec(20.0, 40.0, 16.0),
                // Creates a new object
                new Vec(21.0, 40.0, 16.0),
                // Creates a new object
                new Vec(21.0, 40.0, 17.0),
                // Creates a new object
                new Vec(22.0, 40.0, 17.0),
                // Creates a new object
                new Vec(23.0, 40.0, 17.0),
                // Creates a new object
                new Vec(22.0, 40.0, 18.0),
                // Creates a new object
                new Vec(23.0, 40.0, 18.0),
                // Creates a new object
                new Vec(24.0, 40.0, 18.0),
                // Creates a new object
                new Vec(24.0, 40.0, 19.0),
                // Creates a new object
                new Vec(25.0, 40.0, 19.0),
                // Creates a new object
                new Vec(25.0, 40.0, 20.0),
                // Creates a new object
                new Vec(26.0, 40.0, 20.0),
                // Creates a new object
                new Vec(26.0, 40.0, 21.0),
                // Creates a new object
                new Vec(27.0, 40.0, 21.0)
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point p : validPoints) {
            // Calls a method
            assertContains(points, p);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(validPoints.length, points.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testExactEnd() {
        // Calls a method
        Vec s = new Vec(0.5, 0, 0.5);
        // Calls a method
        Vec e = new Vec(0, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 1);
        // Calls a method
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Calls a method
        assertEquals(new Vec(0, 1, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSameEnd() {
        // Calls a method
        Vec s = new Vec(0.5, 0, 0.5);
        // Calls a method
        Vec e = new Vec(0, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 0.5);
        // Calls a method
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test3dExtraCollection() {
        // Calls a method
        Vec s = new Vec(0.1, 0.1, 0.1);
        // Calls a method
        Vec e = new Vec(1, 1, 1);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        List<Point> points = new ArrayList<>();
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            points.add(iterator.next());
        // End of a block/expression
        }

        // todo(mattw): I need to confirm that these are correct
        // Assigns a value
        Point[] validPoints = new Point[]{
                // Creates a new object
                new Vec(0.0, 0.0, 0.0),
                // Creates a new object
                new Vec(1.0, 1.0, 0.0),
                // Creates a new object
                new Vec(0.0, 1.0, 1.0),
                // Creates a new object
                new Vec(1.0, 0.0, 1.0),
                // Creates a new object
                new Vec(1.0, 0.0, 0.0),
                // Creates a new object
                new Vec(0.0, 1.0, 0.0),
                // Creates a new object
                new Vec(0.0, 0.0, 1.0),
                // Creates a new object
                new Vec(1.0, 1.0, 1.0),
                // Creates a new object
                new Vec(2.0, 2.0, 1.0),
                // Creates a new object
                new Vec(1.0, 2.0, 2.0),
                // Creates a new object
                new Vec(2.0, 1.0, 2.0),
                // Creates a new object
                new Vec(2.0, 1.0, 1.0),
                // Creates a new object
                new Vec(1.0, 2.0, 1.0),
                // Creates a new object
                new Vec(1.0, 1.0, 2.0),
                // Creates a new object
                new Vec(2.0, 2.0, 2.0)
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point p : validPoints) {
            // Calls a method
            assertContains(points, p);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(validPoints.length, points.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dpp() {
        // Calls a method
        Vec s = new Vec(0, 0, 0);
        // Calls a method
        Vec e = new Vec(2, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        List<Point> points = new ArrayList<>();
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            points.add(iterator.next());
        // End of a block/expression
        }

        // Assigns a value
        Point[] validPoints = new Point[]{
                // Creates a new object
                new Vec(0.0, 0.0, 0.0),
                // Creates a new object
                new Vec(1.0, 0.0, 0.0),
                // Creates a new object
                new Vec(2.0, 0.0, 0.0),
                // Creates a new object
                new Vec(1.0, 1.0, 0.0),
                // Creates a new object
                new Vec(2.0, 1.0, 0.0),
                // Creates a new object
                new Vec(3.0, 1.0, 0.0),
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point p : validPoints) {
            // Calls a method
            assertContains(points, p);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(validPoints.length, points.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dpn() {
        // Calls a method
        Vec s = new Vec(0, 0, 0);
        // Calls a method
        Vec e = new Vec(-2, 1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        List<Point> points = new ArrayList<>();
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            points.add(iterator.next());
        // End of a block/expression
        }

        // Assigns a value
        Point[] validPoints = new Point[]{
                // Creates a new object
                new Vec(0.0, 0.0, 0.0),
                // Creates a new object
                new Vec(-1.0, 0.0, 0.0),
                // Creates a new object
                new Vec(-2.0, 0.0, 0.0),
                // Creates a new object
                new Vec(-3.0, 0.0, 0.0),
                // Creates a new object
                new Vec(-2.0, 1.0, 0.0),
                // Creates a new object
                new Vec(-3.0, 1.0, 0.0),
                // Creates a new object
                new Vec(-4.0, 1.0, 0.0)
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point p : validPoints) {
            // Calls a method
            assertContains(points, p);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(validPoints.length, points.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test2dnn() {
        // Calls a method
        Vec s = new Vec(0, 0, 0);
        // Calls a method
        Vec e = new Vec(-2, -1, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Calls a method
        List<Point> points = new ArrayList<>();
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            points.add(iterator.next());
        // End of a block/expression
        }

        // Assigns a value
        Point[] validPoints = new Point[]{
                // Creates a new object
                new Vec(0.0, 0.0, 0.0),
                // Creates a new object
                new Vec(-1.0, 0.0, 0.0),
                // Creates a new object
                new Vec(0.0, -1.0, 0.0),
                // Creates a new object
                new Vec(-1.0, -1.0, 0.0),
                // Creates a new object
                new Vec(-2.0, -1.0, 0.0),
                // Creates a new object
                new Vec(-3.0, -1.0, 0.0),
                // Creates a new object
                new Vec(-2.0, -2.0, 0.0),
                // Creates a new object
                new Vec(-3.0, -2.0, 0.0),
                // Creates a new object
                new Vec(-4.0, -2.0, 0.0)
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point p : validPoints) {
            // Calls a method
            assertContains(points, p);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(validPoints.length, points.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void falling() {
        // Calls a method
        Vec s = new Vec(0, 42, 0);
        // Calls a method
        Vec e = new Vec(0, -10, 0);
        // Calls a method
        BlockIterator iterator = new BlockIterator(s, e, 0, 14.142135623730951);

        // Loop: repeats a block
        for (int y = 42; y >= 27; --y) assertEquals(new Vec(0, y, 0), iterator.next());
        // Calls a method
        assertFalse(iterator.hasNext());
    // End of a block/expression
    }
// End of a block/expression
}