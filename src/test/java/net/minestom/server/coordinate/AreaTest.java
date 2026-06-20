// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

// Static import of a member
import static net.minestom.testing.TestUtils.assertPoint;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class AreaTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lineArea() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(3, 0, 0));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : line) actual.add(v);
        // Calls a method
        Set<BlockVec> expected = Set.of(new BlockVec(0, 0, 0), new BlockVec(1, 0, 0), new BlockVec(2, 0, 0), new BlockVec(3, 0, 0));
        // Calls a method
        assertEquals(expected, actual);

        // Diagonal line
        // Calls a method
        Area.Line diag = Area.line(new BlockVec(0, 0, 0), new BlockVec(2, 2, 2));
        // Calls a method
        actual.clear();
        // Loop: repeats a block
        for (BlockVec v : diag) actual.add(v);
        // Calls a method
        expected = Set.of(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1), new BlockVec(2, 2, 2));
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidArea() {
        // Calls a method
        Area.Cuboid area = Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Calls a method
        assertPoint(new BlockVec(1, 2, 3), area.min());
        // Calls a method
        assertPoint(new BlockVec(4, 5, 6), area.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidSingle() {
        // Calls a method
        Area.Cuboid area = Area.cuboid(BlockVec.ZERO, BlockVec.ZERO);
        // Calls a method
        Set<BlockVec> expected = Set.of(BlockVec.ZERO);
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : area) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionArea() {
        // Calls a method
        Area.Cuboid section = Area.section(0, 0, 0);
        // Calls a method
        assertPoint(new BlockVec(0, 0, 0), section.min());
        // Calls a method
        assertPoint(new BlockVec(15, 15, 15), section.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereArea() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 5);
        // Calls a method
        assertPoint(new BlockVec(0, 0, 0), sphere.center());
        // Calls a method
        assertEquals(5, sphere.radius());
    // End of a block/expression
    }

    // Exhaustive iteration tests for all Area types
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleAreaIteration() {
        // Calls a method
        Area.Single single = Area.single(new BlockVec(1, 2, 3));
        // Calls a method
        Set<BlockVec> expected = Set.of(new BlockVec(1, 2, 3));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : single) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lineAreaReverse() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(3, 0, 0), new BlockVec(0, 0, 0));
        // Calls a method
        Set<BlockVec> expected = Set.of(new BlockVec(0, 0, 0), new BlockVec(1, 0, 0), new BlockVec(2, 0, 0), new BlockVec(3, 0, 0));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : line) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidIteration() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));
        // Assigns a value
        Set<BlockVec> expected = Set.of(
                // Creates a new object
                new BlockVec(0, 0, 0), new BlockVec(0, 0, 1),
                // Creates a new object
                new BlockVec(0, 1, 0), new BlockVec(0, 1, 1),
                // Creates a new object
                new BlockVec(1, 0, 0), new BlockVec(1, 0, 1),
                // Creates a new object
                new BlockVec(1, 1, 0), new BlockVec(1, 1, 1));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : cuboid) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidIterationUnorderedEndpoints() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(2, 3, 4), new BlockVec(1, 2, 3));
        // Calls a method
        Set<BlockVec> expected = new HashSet<>();
        // Loop: repeats a block
        for (int x = 1; x <= 2; x++) {
            // Loop: repeats a block
            for (int y = 2; y <= 3; y++) {
                // Loop: repeats a block
                for (int z = 3; z <= 4; z++) {
                    // Calls a method
                    expected.add(new BlockVec(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : cuboid) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereIterationRadius1() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 1);
        // Calls a method
        Set<BlockVec> expected = new HashSet<>();
        // Only blocks within distance 1.0 from center should be included
        // Loop: repeats a block
        for (int x = -1; x <= 1; x++) {
            // Loop: repeats a block
            for (int y = -1; y <= 1; y++) {
                // Loop: repeats a block
                for (int z = -1; z <= 1; z++) {
                    // Calls a method
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    // Branch: checks a condition
                    if (distance <= 1.0) {
                        // Calls a method
                        expected.add(new BlockVec(x, y, z));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : sphere) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetCuboidIteration() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));
        // Calls a method
        Area offset = cuboid.offset(1, 2, 3);
        // Calls a method
        Set<BlockVec> expected = new HashSet<>();
        // Loop: repeats a block
        for (int x = 1; x <= 2; x++) {
            // Loop: repeats a block
            for (int y = 2; y <= 3; y++) {
                // Loop: repeats a block
                for (int z = 3; z <= 4; z++) {
                    // Calls a method
                    expected.add(new BlockVec(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : offset) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cubeArea() {
        // Calls a method
        Area.Cuboid cube = Area.cube(new BlockVec(0, 0, 0), 2);
        // Calls a method
        Set<BlockVec> expected = new HashSet<>();
        // Loop: repeats a block
        for (int x = -1; x <= 1; x++) {
            // Loop: repeats a block
            for (int y = -1; y <= 1; y++) {
                // Loop: repeats a block
                for (int z = -1; z <= 1; z++) {
                    // Calls a method
                    expected.add(new BlockVec(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : cube) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Tests for split method
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSingleSection() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(10, 5, 5));
        // Calls a method
        List<Area.Cuboid> splits = cuboid.split();
        // Calls a method
        assertEquals(1, splits.size());
        // Calls a method
        Area.Cuboid sub = splits.getFirst();
        // Calls a method
        assertPoint(new BlockVec(0, 0, 0), sub.min());
        // Calls a method
        assertPoint(new BlockVec(10, 5, 5), sub.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitMultiSectionX() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(15, 0, 0), new BlockVec(17, 1, 1));
        // Calls a method
        List<Area.Cuboid> splits = cuboid.split();
        // Calls a method
        assertEquals(2, splits.size());
        // Assigns a value
        boolean foundSec0 = false, foundSec1 = false;
        // Loop: repeats a block
        for (Area.Cuboid sub : splits) {
            // Branch: checks a condition
            if (sub.min().equals(new BlockVec(15, 0, 0)) && sub.max().equals(new BlockVec(15, 1, 1)))
                // Assigns a value
                foundSec0 = true;
            // Branch: checks a condition
            if (sub.min().equals(new BlockVec(16, 0, 0)) && sub.max().equals(new BlockVec(17, 1, 1)))
                // Assigns a value
                foundSec1 = true;
        // End of a block/expression
        }
        // Calls a method
        assertTrue(foundSec0);
        // Calls a method
        assertTrue(foundSec1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitOnSingle() {
        // Calls a method
        BlockVec point = new BlockVec(5, 5, 5);
        // Calls a method
        List<Area.Cuboid> splits = Area.single(point).split();
        // Calls a method
        assertEquals(1, splits.size());
        // Calls a method
        Area.Cuboid sub = splits.getFirst();
        // Calls a method
        assertPoint(point, sub.min());
        // Calls a method
        assertPoint(point, sub.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitLineSingleSection() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3));
        // Calls a method
        List<Area.Cuboid> splits = line.split();
        // Calls a method
        assertEquals(1, splits.size());
        // Calls a method
        assertEquals(Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3)), splits.getFirst());

        // Verify all split blocks match the line
        // Calls a method
        Set<BlockVec> expectedBlocks = Set.of(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3));
        // Calls a method
        Set<BlockVec> splitBlocks = new HashSet<>();
        // Loop: repeats a block
        for (Area.Cuboid split : splits) {
            // Loop: repeats a block
            for (BlockVec block : split) {
                // Calls a method
                splitBlocks.add(block);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        assertEquals(expectedBlocks, splitBlocks);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitLineCrossSection() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(15, 0, 0), new BlockVec(17, 0, 0));
        // Calls a method
        List<Area.Cuboid> splits = line.split();
        // Calls a method
        assertEquals(2, splits.size());

        // Verify all split blocks match the line
        // Assigns a value
        Set<BlockVec> expectedBlocks = Set.of(
                // Creates a new object
                new BlockVec(15, 0, 0),
                // Creates a new object
                new BlockVec(16, 0, 0),
                // Creates a new object
                new BlockVec(17, 0, 0)
        // End of a block/expression
        );
        // Calls a method
        Set<BlockVec> splitBlocks = new HashSet<>();
        // Loop: repeats a block
        for (Area.Cuboid split : splits) {
            // Loop: repeats a block
            for (BlockVec block : split) {
                // Calls a method
                splitBlocks.add(block);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        assertEquals(expectedBlocks, splitBlocks);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSphere() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 1);
        // Calls a method
        List<Area.Cuboid> splits = sphere.split();
        // A sphere with radius 1 centered at origin will span multiple sections
        // since it includes blocks from (-1,-1,-1) to (1,1,1) range
        // Calls a method
        assertTrue(!splits.isEmpty());

        // Verify that split covers exactly the sphere blocks
        // Calls a method
        Set<BlockVec> allSplitBlocks = new HashSet<>();
        // Loop: repeats a block
        for (Area.Cuboid split : splits) {
            // Loop: repeats a block
            for (BlockVec block : split) {
                // Calls a method
                allSplitBlocks.add(block);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        Set<BlockVec> sphereBlocks = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec block : sphere) {
            // Calls a method
            sphereBlocks.add(block);
        // End of a block/expression
        }

        // All sphere blocks should be covered by splits
        // Calls a method
        assertTrue(allSplitBlocks.containsAll(sphereBlocks));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSectionArea() {
        // Calls a method
        Area.Cuboid section = Area.section(0, 0, 0);
        // Calls a method
        Set<Area.Cuboid> expected = Set.of(section);
        // Calls a method
        Set<Area.Cuboid> actual = new HashSet<>(section.split());
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitCuboidMultiSectionsX() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(17, 1, 1));
        // Spans two sections, should be split into 2 cuboids
        // Calls a method
        List<Area.Cuboid> splits = cuboid.split();
        // Calls a method
        assertEquals(2, splits.size());
        // Assigns a value
        boolean foundSec0 = false, foundSec1 = false;
        // Loop: repeats a block
        for (Area.Cuboid sub : splits) {
            // Branch: checks a condition
            if (sub.min().equals(new BlockVec(0, 0, 0)) && sub.max().equals(new BlockVec(15, 1, 1)))
                // Assigns a value
                foundSec0 = true;
            // Branch: checks a condition
            if (sub.min().equals(new BlockVec(16, 0, 0)) && sub.max().equals(new BlockVec(17, 1, 1)))
                // Assigns a value
                foundSec1 = true;
        // End of a block/expression
        }
        // Calls a method
        assertTrue(foundSec0);
        // Calls a method
        assertTrue(foundSec1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitTwoFullSectionsX() {
        // Cuboid covers two full 16x16x16 sections along X
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(31, 15, 15));
        // Assigns a value
        Set<Area.Cuboid> expected = Set.of(
                // Code statement
                Area.section(0, 0, 0),
                // Code statement
                Area.section(1, 0, 0)
        // End of a block/expression
        );
        // Calls a method
        Set<Area.Cuboid> actual = new HashSet<>(cuboid.split());
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitFullGridSections() {
        // Cuboid covers a 2x2x2 grid of full sections
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(31, 31, 31));
        // Calls a method
        Set<Area.Cuboid> expected = new HashSet<>();
        // Loop: repeats a block
        for (int x = 0; x <= 1; x++) {
            // Loop: repeats a block
            for (int y = 0; y <= 1; y++) {
                // Loop: repeats a block
                for (int z = 0; z <= 1; z++) {
                    // Calls a method
                    expected.add(Area.section(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        Set<Area.Cuboid> actual = new HashSet<>(cuboid.split());
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundSingle() {
        // Calls a method
        Area.Single single = Area.single(new BlockVec(5, 10, 15));
        // Calls a method
        Area.Cuboid bound = single.bound();
        // Calls a method
        assertPoint(new BlockVec(5, 10, 15), bound.min());
        // Calls a method
        assertPoint(new BlockVec(5, 10, 15), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundLine() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Calls a method
        Area.Cuboid bound = line.bound();
        // Calls a method
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Calls a method
        assertPoint(new BlockVec(4, 5, 6), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundLineReversed() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(4, 5, 6), new BlockVec(1, 2, 3));
        // Calls a method
        Area.Cuboid bound = line.bound();
        // Calls a method
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Calls a method
        assertPoint(new BlockVec(4, 5, 6), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundLineDiagonal() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(-2, 10, -5), new BlockVec(3, -1, 2));
        // Calls a method
        Area.Cuboid bound = line.bound();
        // Calls a method
        assertPoint(new BlockVec(-2, -1, -5), bound.min());
        // Calls a method
        assertPoint(new BlockVec(3, 10, 2), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundCuboid() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Calls a method
        Area.Cuboid bound = cuboid.bound();
        // Bounding box of a cuboid should be itself
        // Calls a method
        assertPoint(cuboid.min(), bound.min());
        // Calls a method
        assertPoint(cuboid.max(), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundCuboidUnordered() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(4, 5, 6), new BlockVec(1, 2, 3));
        // Calls a method
        Area.Cuboid bound = cuboid.bound();
        // Should still return correctly ordered bounds
        // Calls a method
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Calls a method
        assertPoint(new BlockVec(4, 5, 6), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundSphere() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 3);
        // Calls a method
        Area.Cuboid bound = sphere.bound();
        // Calls a method
        assertPoint(new BlockVec(-3, -3, -3), bound.min());
        // Calls a method
        assertPoint(new BlockVec(3, 3, 3), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundSphereOffset() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(10, 20, 30), 5);
        // Calls a method
        Area.Cuboid bound = sphere.bound();
        // Calls a method
        assertPoint(new BlockVec(5, 15, 25), bound.min());
        // Calls a method
        assertPoint(new BlockVec(15, 25, 35), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundSphereZeroRadius() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(1, 2, 3), 0);
        // Calls a method
        Area.Cuboid bound = sphere.bound();
        // Calls a method
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Calls a method
        assertPoint(new BlockVec(1, 2, 3), bound.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void containsSingle() {
        // Calls a method
        Area.Single single = Area.single(new BlockVec(1, 2, 3));

        // Calls a method
        assertTrue(single.contains(new BlockVec(1, 2, 3)));
        // Calls a method
        assertTrue(single.contains(new Vec(1.9, 2.9, 3.9)));
        // Calls a method
        assertFalse(single.contains(new BlockVec(1, 2, 4)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void containsLine() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(4, 2, 0));

        // Calls a method
        assertTrue(line.contains(new BlockVec(0, 0, 0)));
        // Calls a method
        assertTrue(line.contains(new BlockVec(1, 0, 0)));
        // Calls a method
        assertTrue(line.contains(new BlockVec(2, 1, 0)));
        // Calls a method
        assertTrue(line.contains(new BlockVec(4, 2, 0)));
        // Calls a method
        assertFalse(line.contains(new BlockVec(2, 2, 0)));
        // Calls a method
        assertFalse(line.contains(new BlockVec(5, 2, 0)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void containsCuboid() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(-1, 2, 3), new BlockVec(1, 4, 5));

        // Calls a method
        assertTrue(cuboid.contains(new BlockVec(-1, 2, 3)));
        // Calls a method
        assertTrue(cuboid.contains(new BlockVec(0, 3, 4)));
        // Calls a method
        assertTrue(cuboid.contains(new BlockVec(1, 4, 5)));
        // Calls a method
        assertFalse(cuboid.contains(new BlockVec(2, 4, 5)));
        // Calls a method
        assertFalse(cuboid.contains(new BlockVec(1, 5, 5)));
        // Calls a method
        assertFalse(cuboid.contains(new BlockVec(1, 4, 6)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void containsSphere() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 2);

        // Calls a method
        assertTrue(sphere.contains(new BlockVec(0, 0, 0)));
        // Calls a method
        assertTrue(sphere.contains(new BlockVec(2, 0, 0)));
        // Calls a method
        assertTrue(sphere.contains(new BlockVec(1, 1, 1)));
        // Calls a method
        assertFalse(sphere.contains(new BlockVec(2, 1, 0)));
        // Calls a method
        assertFalse(sphere.contains(new BlockVec(0, 0, 3)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockCountSingle() {
        // Calls a method
        assertEquals(1, Area.single(new BlockVec(1, 2, 3)).blockCount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockCountLine() {
        // Calls a method
        assertEquals(1, Area.line(new BlockVec(1, 2, 3), new BlockVec(1, 2, 3)).blockCount());
        // Calls a method
        assertEquals(5, Area.line(new BlockVec(0, 0, 0), new BlockVec(4, 2, 0)).blockCount());
        // Calls a method
        assertEquals(6, Area.line(new BlockVec(0, 0, 0), new BlockVec(2, 5, 1)).blockCount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockCountCuboid() {
        // Calls a method
        assertEquals(1, Area.cuboid(BlockVec.ZERO, BlockVec.ZERO).blockCount());
        // Calls a method
        assertEquals(24, Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(3, 2, 1)).blockCount());
        // Calls a method
        assertEquals(27, Area.cube(new BlockVec(0, 0, 0), 2).blockCount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockCountSphere() {
        // Calls a method
        Area.Sphere radius0 = Area.sphere(BlockVec.ZERO, 0);
        // Calls a method
        Area.Sphere radius1 = Area.sphere(BlockVec.ZERO, 1);
        // Calls a method
        Area.Sphere radius2 = Area.sphere(BlockVec.ZERO, 2);

        // Calls a method
        assertEquals(blocks(radius0).size(), radius0.blockCount());
        // Calls a method
        assertEquals(blocks(radius1).size(), radius1.blockCount());
        // Calls a method
        assertEquals(blocks(radius2).size(), radius2.blockCount());
    // End of a block/expression
    }

    // Additional comprehensive iterator tests
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lineIteratorEdgeCases() {
        // Test zero-length line
        // Calls a method
        Area.Line zeroLine = Area.line(new BlockVec(5, 5, 5), new BlockVec(5, 5, 5));
        // Calls a method
        Set<BlockVec> expected = Set.of(new BlockVec(5, 5, 5));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : zeroLine) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);

        // Test negative coordinates
        // Calls a method
        Area.Line negativeLine = Area.line(new BlockVec(-2, -3, -4), new BlockVec(-1, -2, -3));
        // Calls a method
        expected = Set.of(new BlockVec(-2, -3, -4), new BlockVec(-1, -2, -3));
        // Calls a method
        actual.clear();
        // Loop: repeats a block
        for (BlockVec v : negativeLine) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereIteratorVariousRadii() {
        // Test radius 0 (single block)
        // Calls a method
        Area.Sphere sphere0 = Area.sphere(new BlockVec(0, 0, 0), 0);
        // Calls a method
        Set<BlockVec> expected = Set.of(new BlockVec(0, 0, 0));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : sphere0) actual.add(v);
        // Calls a method
        assertEquals(expected, actual);

        // Test radius 2
        // Calls a method
        Area.Sphere sphere2 = Area.sphere(new BlockVec(0, 0, 0), 2);
        // Calls a method
        actual.clear();
        // Loop: repeats a block
        for (BlockVec v : sphere2) actual.add(v);

        // Verify all blocks are within radius 2
        // Loop: repeats a block
        for (BlockVec block : actual) {
            // Assigns a value
            double distance = Math.sqrt(block.blockX() * block.blockX() +
                    // Code statement
                    block.blockY() * block.blockY() +
                    // Calls a method
                    block.blockZ() * block.blockZ());
            // Calls a method
            assertTrue(distance <= 2.0, "Block " + block + " is outside radius 2, distance: " + distance);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidIteratorLargeArea() {
        // Calls a method
        Area.Cuboid largeCuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(3, 2, 1));
        // Calls a method
        Set<BlockVec> actual = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec v : largeCuboid) actual.add(v);

        // Should have 4 * 3 * 2 = 24 blocks
        // Calls a method
        assertEquals(24, actual.size());

        // Verify all expected blocks are present
        // Loop: repeats a block
        for (int x = 0; x <= 3; x++) {
            // Loop: repeats a block
            for (int y = 0; y <= 2; y++) {
                // Loop: repeats a block
                for (int z = 0; z <= 1; z++) {
                    // Calls a method
                    assertTrue(actual.contains(new BlockVec(x, y, z)));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Comprehensive split() tests
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSingleInDifferentSections() {
        // Test single blocks in different sections
        // Calls a method
        Area.Single single1 = Area.single(new BlockVec(0, 0, 0));
        // Calls a method
        assertEquals(1, single1.split().size());

        // Calls a method
        Area.Single single2 = Area.single(new BlockVec(16, 16, 16));
        // Calls a method
        assertEquals(1, single2.split().size());

        // Calls a method
        Area.Single single3 = Area.single(new BlockVec(-1, -1, -1));
        // Calls a method
        assertEquals(1, single3.split().size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitCuboidFullSectionOptimization() {
        // Test that full sections are properly identified
        // Calls a method
        Area.Cuboid fullSection = Area.section(1, 1, 1);
        // Calls a method
        List<Area.Cuboid> splits = fullSection.split();
        // Calls a method
        assertEquals(1, splits.size());
        // Calls a method
        assertEquals(fullSection, splits.getFirst());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitCuboidPartialSections() {
        // Cuboid that partially fills multiple sections
        // Calls a method
        Area.Cuboid partial = Area.cuboid(new BlockVec(14, 14, 14), new BlockVec(18, 18, 18));
        // Calls a method
        List<Area.Cuboid> splits = partial.split();
        // Code statement
        assertEquals(8, splits.size()); // 2x2x2 sections

        // Verify no section boundary violations
        // Loop: repeats a block
        for (Area.Cuboid split : splits) {
            // Calls a method
            int secMinX = Math.floorDiv(split.min().blockX(), 16);
            // Calls a method
            int secMaxX = Math.floorDiv(split.max().blockX(), 16);
            // Calls a method
            int secMinY = Math.floorDiv(split.min().blockY(), 16);
            // Calls a method
            int secMaxY = Math.floorDiv(split.max().blockY(), 16);
            // Calls a method
            int secMinZ = Math.floorDiv(split.min().blockZ(), 16);
            // Calls a method
            int secMaxZ = Math.floorDiv(split.max().blockZ(), 16);

            // Calls a method
            assertEquals(secMinX, secMaxX, "Split crosses section boundary in X");
            // Calls a method
            assertEquals(secMinY, secMaxY, "Split crosses section boundary in Y");
            // Calls a method
            assertEquals(secMinZ, secMaxZ, "Split crosses section boundary in Z");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSphereFullAndPartialSections() {
        // Large sphere that should have both full and partial sections
        // Calls a method
        Area.Sphere largeSphere = Area.sphere(new BlockVec(16, 16, 16), 20);
        // Calls a method
        assertEquals(blocks(largeSphere), splitBlocks(largeSphere));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSphereZeroRadius() {
        // Calls a method
        Area.Sphere pointSphere = Area.sphere(new BlockVec(5, 5, 5), 0);
        // Calls a method
        List<Area.Cuboid> splits = pointSphere.split();
        // Calls a method
        assertEquals(1, splits.size());

        // Calls a method
        Area.Cuboid split = splits.getFirst();
        // Calls a method
        assertEquals(new BlockVec(5, 5, 5), split.min());
        // Calls a method
        assertEquals(new BlockVec(5, 5, 5), split.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitNegativeCoordinates() {
        // Test areas in negative coordinate space
        // Calls a method
        Area.Cuboid negativeCuboid = Area.cuboid(new BlockVec(-20, -20, -20), new BlockVec(-5, -5, -5));
        // Calls a method
        List<Area.Cuboid> splits = negativeCuboid.split();
        // Calls a method
        assertFalse(splits.isEmpty());

        // Verify all splits are section-aligned
        // Loop: repeats a block
        for (Area.Cuboid split : splits) {
            // Calls a method
            int secMinX = Math.floorDiv(split.min().blockX(), 16);
            // Calls a method
            int secMaxX = Math.floorDiv(split.max().blockX(), 16);
            // Calls a method
            assertEquals(secMinX, secMaxX, "Split crosses section boundary in negative space");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void iteratorAndSplitConsistency() {
        // Verify that split() covers exactly the same blocks as iterator()
        // Loop: repeats a block
        for (Area area : areas()) {
            // Calls a method
            Set<BlockVec> iteratorBlocks = new HashSet<>();
            // Loop: repeats a block
            for (BlockVec block : area) {
                // Calls a method
                iteratorBlocks.add(block);
            // End of a block/expression
            }

            // Calls a method
            Set<BlockVec> splitBlocks = new HashSet<>();
            // Loop: repeats a block
            for (Area.Cuboid split : area.split()) {
                // Loop: repeats a block
                for (BlockVec block : split) {
                    // Calls a method
                    splitBlocks.add(block);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Split should contain exactly the same blocks as iterator - no more, no less
            // Code statement
            assertEquals(iteratorBlocks, splitBlocks,
                    // Code statement
                    "Split blocks don't exactly match iterator blocks for " + area.getClass().getSimpleName() +
                            // Calls a method
                            ". Iterator has " + iteratorBlocks.size() + " blocks, split has " + splitBlocks.size() + " blocks");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitSectionAlignment() {
        // Verify all splits are properly section-aligned
        // Loop: repeats a block
        for (Area area : areas()) {
            // Loop: repeats a block
            for (Area.Cuboid split : area.split()) {
                // Each split should be within a single section
                // Calls a method
                int secMinX = Math.floorDiv(split.min().blockX(), 16);
                // Calls a method
                int secMaxX = Math.floorDiv(split.max().blockX(), 16);
                // Calls a method
                int secMinY = Math.floorDiv(split.min().blockY(), 16);
                // Calls a method
                int secMaxY = Math.floorDiv(split.max().blockY(), 16);
                // Calls a method
                int secMinZ = Math.floorDiv(split.min().blockZ(), 16);
                // Calls a method
                int secMaxZ = Math.floorDiv(split.max().blockZ(), 16);

                // Calls a method
                assertEquals(secMinX, secMaxX, "Split crosses section boundary in X for " + area.getClass().getSimpleName());
                // Calls a method
                assertEquals(secMinY, secMaxY, "Split crosses section boundary in Y for " + area.getClass().getSimpleName());
                // Calls a method
                assertEquals(secMinZ, secMaxZ, "Split crosses section boundary in Z for " + area.getClass().getSimpleName());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void factoryMethodsFloorPointCoordinates() {
        // Calls a method
        assertEquals(new BlockVec(1, -2, 3), Area.single(new Vec(1.9, -1.1, 3.0)).point());
        // Calls a method
        assertEquals(new BlockVec(1, -2, 3), Area.line(new Vec(1.9, -1.1, 3.0), new Vec(4.2, 5.8, -6.1)).start());
        // Calls a method
        assertEquals(new BlockVec(4, 5, -7), Area.line(new Vec(1.9, -1.1, 3.0), new Vec(4.2, 5.8, -6.1)).end());
        // Calls a method
        assertEquals(new BlockVec(0, 0, 0), Area.cuboid(new Vec(1.9, 2.9, 3.9), new Vec(0.1, 0.1, 0.1)).min());
        // Calls a method
        assertEquals(new BlockVec(1, 2, 3), Area.cuboid(new Vec(1.9, 2.9, 3.9), new Vec(0.1, 0.1, 0.1)).max());
        // Calls a method
        assertEquals(new BlockVec(-1, 2, 3), Area.sphere(new Vec(-0.1, 2.9, 3.0), 2).center());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boxArea() {
        // Calls a method
        Area.Cuboid box = Area.box(new BlockVec(10, 10, 10), new Vec(4, 2, 6));

        // Calls a method
        assertEquals(new BlockVec(8, 9, 7), box.min());
        // Calls a method
        assertEquals(new BlockVec(12, 11, 13), box.max());
        // Calls a method
        assertEquals(5 * 3 * 7, blocks(box).size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void negativeSphereRadiusRejected() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Area.sphere(BlockVec.ZERO, -1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void negativeCubeSizeRejected() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Area.cube(BlockVec.ZERO, -1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void negativeBoxSizeRejected() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Area.box(BlockVec.ZERO, new Vec(-1, 2, 3)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Area.box(BlockVec.ZERO, new Vec(1, -2, 3)));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> Area.box(BlockVec.ZERO, new Vec(1, 2, -3)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nullPointsRejected() {
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.single(null));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.line(null, BlockVec.ZERO));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.line(BlockVec.ZERO, null));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.cuboid(null, BlockVec.ZERO));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.cuboid(BlockVec.ZERO, null));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.box(null, BlockVec.ONE));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.box(BlockVec.ZERO, null));
        // Calls a method
        assertThrows(NullPointerException.class, () -> Area.sphere(null, 1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockCountMatchesIteratorAllAreas() {
        // Loop: repeats a block
        for (Area area : areas()) {
            // Assigns a value
            long iterated = 0;
            // Loop: repeats a block
            for (BlockVec ignored : area) iterated++;
            // Code statement
            assertEquals(iterated, area.blockCount(),
                    // Code statement
                    "blockCount mismatch for " + area);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void containsReturnsTrueForEveryIteratedBlock() {
        // Loop: repeats a block
        for (Area area : areas()) {
            // Loop: repeats a block
            for (BlockVec block : area) {
                // Code statement
                assertTrue(area.contains(block),
                        // Calls a method
                        "contains(" + block + ") returned false for iterated block of " + area);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void containsRejectsBlocksOutsideBounds() {
        // Loop: repeats a block
        for (Area area : areas()) {
            // Calls a method
            Area.Cuboid bound = area.bound();
            // Calls a method
            BlockVec outside = bound.max().add(100, 100, 100).asBlockVec();
            // Code statement
            assertFalse(area.contains(outside),
                    // Code statement
                    "contains should reject far-outside block for " + area);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetPreservesBlockCount() {
        // Loop: repeats a block
        for (Area area : areas()) {
            // Calls a method
            Area offset = area.offset(7, -3, 11);
            // Code statement
            assertEquals(area.blockCount(), offset.blockCount(),
                    // Code statement
                    "offset changed blockCount for " + area);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetSingle() {
        // Calls a method
        Area.Single single = Area.single(new BlockVec(1, 2, 3));
        // Calls a method
        Area offset = single.offset(4, 5, 6);
        // Calls a method
        assertInstanceOf(Area.Single.class, offset);
        // Calls a method
        assertEquals(new BlockVec(5, 7, 9), ((Area.Single) offset).point());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetLine() {
        // Calls a method
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(3, 0, 0));
        // Calls a method
        Area offset = line.offset(10, 20, 30);
        // Calls a method
        assertInstanceOf(Area.Line.class, offset);
        // Calls a method
        Area.Line shifted = (Area.Line) offset;
        // Calls a method
        assertEquals(new BlockVec(10, 20, 30), shifted.start());
        // Calls a method
        assertEquals(new BlockVec(13, 20, 30), shifted.end());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetSphere() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 4);
        // Calls a method
        Area offset = sphere.offset(1, 2, 3);
        // Calls a method
        assertInstanceOf(Area.Sphere.class, offset);
        // Calls a method
        Area.Sphere shifted = (Area.Sphere) offset;
        // Calls a method
        assertEquals(new BlockVec(1, 2, 3), shifted.center());
        // Calls a method
        assertEquals(4, shifted.radius());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void offsetByPoint() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));
        // Calls a method
        Area offset = cuboid.offset(new Vec(2.9, -1.5, 4.0));
        // Calls a method
        assertInstanceOf(Area.Cuboid.class, offset);
        // Calls a method
        Area.Cuboid shifted = (Area.Cuboid) offset;
        // Calls a method
        assertEquals(new BlockVec(2, -2, 4), shifted.min());
        // Calls a method
        assertEquals(new BlockVec(3, -1, 5), shifted.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recordEquality() {
        // Calls a method
        assertEquals(Area.single(new BlockVec(1, 2, 3)), Area.single(new BlockVec(1, 2, 3)));
        // Code statement
        assertEquals(Area.single(new BlockVec(1, 2, 3)).hashCode(),
                // Calls a method
                Area.single(new BlockVec(1, 2, 3)).hashCode());

        // Code statement
        assertEquals(Area.line(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)),
                // Calls a method
                Area.line(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)));

        // Code statement
        assertEquals(Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)),
                // Calls a method
                Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)));
        // Cuboid equality holds regardless of argument order (auto-ordered)
        // Code statement
        assertEquals(Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)),
                // Calls a method
                Area.cuboid(new BlockVec(5, 5, 5), new BlockVec(0, 0, 0)));

        // Code statement
        assertEquals(Area.sphere(new BlockVec(1, 2, 3), 4),
                // Calls a method
                Area.sphere(new BlockVec(1, 2, 3), 4));

        // Calls a method
        assertNotEquals(Area.single(new BlockVec(1, 2, 3)), Area.single(new BlockVec(1, 2, 4)));
        // Calls a method
        assertNotEquals(Area.sphere(new BlockVec(0, 0, 0), 3), Area.sphere(new BlockVec(0, 0, 0), 4));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidConstructorOrders() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(5, 5, 5), new BlockVec(0, 0, 0));
        // Calls a method
        assertEquals(new BlockVec(0, 0, 0), cuboid.min());
        // Calls a method
        assertEquals(new BlockVec(5, 5, 5), cuboid.max());

        // Already-ordered inputs pass through unchanged
        // Calls a method
        BlockVec min = new BlockVec(0, 0, 0);
        // Calls a method
        BlockVec max = new BlockVec(5, 5, 5);
        // Calls a method
        Area.Cuboid ordered = Area.cuboid(min, max);
        // Calls a method
        assertSame(min, ordered.min());
        // Calls a method
        assertSame(max, ordered.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cubeSizeZero() {
        // Calls a method
        Area.Cuboid cube = Area.cube(new BlockVec(5, 5, 5), 0);
        // Calls a method
        assertEquals(new BlockVec(5, 5, 5), cube.min());
        // Calls a method
        assertEquals(new BlockVec(5, 5, 5), cube.max());
        // Calls a method
        assertEquals(1, cube.blockCount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boxZeroSize() {
        // Calls a method
        Area.Cuboid box = Area.box(new BlockVec(3, 3, 3), Vec.ZERO);
        // Calls a method
        assertEquals(new BlockVec(3, 3, 3), box.min());
        // Calls a method
        assertEquals(new BlockVec(3, 3, 3), box.max());
        // Calls a method
        assertEquals(1, box.blockCount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereZeroRadiusBlockCount() {
        // Calls a method
        assertEquals(1, Area.sphere(new BlockVec(5, 5, 5), 0).blockCount());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereLargerRadiusSplitMatchesIterator() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 7);
        // Calls a method
        assertEquals(blocks(sphere), splitBlocks(sphere));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereSplitCenteredOnSectionBoundary() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(16, 16, 16), 4);
        // Calls a method
        assertEquals(blocks(sphere), splitBlocks(sphere));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sphereSplitNegativeCenter() {
        // Calls a method
        Area.Sphere sphere = Area.sphere(new BlockVec(-8, -8, -8), 5);
        // Calls a method
        assertEquals(blocks(sphere), splitBlocks(sphere));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cuboidContainedInSingleSectionSplitReturnsSelf() {
        // Calls a method
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Calls a method
        List<Area.Cuboid> splits = cuboid.split();
        // Calls a method
        assertEquals(1, splits.size());
        // Calls a method
        assertEquals(cuboid, splits.getFirst());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sectionAtNegativeCoordinates() {
        // Calls a method
        Area.Cuboid section = Area.section(-1, -1, -1);
        // Calls a method
        assertEquals(new BlockVec(-16, -16, -16), section.min());
        // Calls a method
        assertEquals(new BlockVec(-1, -1, -1), section.max());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void boundOfOffsetMatchesOffsetOfBound() {
        // Loop: repeats a block
        for (Area area : areas()) {
            // Assigns a value
            Area.Cuboid expected = Area.cuboid(
                    // Code statement
                    area.bound().min().add(2, 3, 4).asBlockVec(),
                    // Calls a method
                    area.bound().max().add(2, 3, 4).asBlockVec());
            // Calls a method
            Area.Cuboid actual = area.offset(2, 3, 4).bound();
            // Calls a method
            assertEquals(expected, actual, "bound mismatch after offset for " + area);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void splitNeverEmptyForNonEmptyArea() {
        // Loop: repeats a block
        for (Area area : areas()) {
            // Calls a method
            assertFalse(area.split().isEmpty(), "split should not be empty for " + area);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lineDiagonal3D() {
        // Verify that a 3D diagonal line generates the expected number of blocks
        // Calls a method
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(10, 10, 10));
        // Calls a method
        assertEquals(11, line.blockCount());
        // Calls a method
        Set<BlockVec> blocks = blocks(line);
        // Calls a method
        assertEquals(11, blocks.size());
        // Each block should be on the diagonal (x == y == z)
        // Loop: repeats a block
        for (BlockVec block : blocks) {
            // Calls a method
            assertEquals(block.blockX(), block.blockY());
            // Calls a method
            assertEquals(block.blockY(), block.blockZ());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lineContainsRejectsPointsOnBoundingBoxButOffLine() {
        // Bresenham diagonal: (0,0,0) -> (4,2,0) — point (4,0,0) is in the bbox but not on the line
        // Calls a method
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(4, 2, 0));
        // Calls a method
        assertFalse(line.contains(new BlockVec(4, 0, 0)));
        // Calls a method
        assertFalse(line.contains(new BlockVec(0, 2, 0)));
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Area> areas() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Area.single(new BlockVec(7, 8, 9)),
                // Code statement
                Area.line(new BlockVec(0, 0, 0), new BlockVec(5, 3, 2)),
                // Code statement
                Area.cuboid(new BlockVec(10, 10, 10), new BlockVec(12, 12, 12)),
                // Code statement
                Area.sphere(new BlockVec(0, 0, 0), 3),
                // Code statement
                Area.line(new BlockVec(14, 0, 0), new BlockVec(34, 0, 0)), // Multi-section line
                // Code statement
                Area.sphere(new BlockVec(8, 8, 8), 2), // Small multisection sphere
                // Code statement
                Area.cuboid(new BlockVec(-5, -5, -5), new BlockVec(5, 5, 5)) // Negative coordinates
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<BlockVec> blocks(Area area) {
        // Calls a method
        Set<BlockVec> blocks = new HashSet<>();
        // Loop: repeats a block
        for (BlockVec block : area) {
            // Calls a method
            blocks.add(block);
        // End of a block/expression
        }
        // Returns a value to the caller
        return blocks;
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<BlockVec> splitBlocks(Area area) {
        // Calls a method
        Set<BlockVec> blocks = new HashSet<>();
        // Loop: repeats a block
        for (Area.Cuboid split : area.split()) {
            // Loop: repeats a block
            for (BlockVec block : split) {
                // Calls a method
                blocks.add(block);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return blocks;
    // End of a block/expression
    }
// End of a block/expression
}
