// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertPoint;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class AreaTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lineArea() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(3, 0, 0));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : line) actual.add(v);
        // Appelle une méthode
        Set<BlockVec> expected = Set.of(new BlockVec(0, 0, 0), new BlockVec(1, 0, 0), new BlockVec(2, 0, 0), new BlockVec(3, 0, 0));
        // Appelle une méthode
        assertEquals(expected, actual);

        // Diagonal line
        // Appelle une méthode
        Area.Line diag = Area.line(new BlockVec(0, 0, 0), new BlockVec(2, 2, 2));
        // Appelle une méthode
        actual.clear();
        // Boucle : répète un bloc
        for (BlockVec v : diag) actual.add(v);
        // Appelle une méthode
        expected = Set.of(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1), new BlockVec(2, 2, 2));
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidArea() {
        // Appelle une méthode
        Area.Cuboid area = Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Appelle une méthode
        assertPoint(new BlockVec(1, 2, 3), area.min());
        // Appelle une méthode
        assertPoint(new BlockVec(4, 5, 6), area.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidSingle() {
        // Appelle une méthode
        Area.Cuboid area = Area.cuboid(BlockVec.ZERO, BlockVec.ZERO);
        // Appelle une méthode
        Set<BlockVec> expected = Set.of(BlockVec.ZERO);
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : area) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionArea() {
        // Appelle une méthode
        Area.Cuboid section = Area.section(0, 0, 0);
        // Appelle une méthode
        assertPoint(new BlockVec(0, 0, 0), section.min());
        // Appelle une méthode
        assertPoint(new BlockVec(15, 15, 15), section.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereArea() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 5);
        // Appelle une méthode
        assertPoint(new BlockVec(0, 0, 0), sphere.center());
        // Appelle une méthode
        assertEquals(5, sphere.radius());
    // Fin d'un bloc/d'une expression
    }

    // Exhaustive iteration tests for all Area types
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleAreaIteration() {
        // Appelle une méthode
        Area.Single single = Area.single(new BlockVec(1, 2, 3));
        // Appelle une méthode
        Set<BlockVec> expected = Set.of(new BlockVec(1, 2, 3));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : single) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lineAreaReverse() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(3, 0, 0), new BlockVec(0, 0, 0));
        // Appelle une méthode
        Set<BlockVec> expected = Set.of(new BlockVec(0, 0, 0), new BlockVec(1, 0, 0), new BlockVec(2, 0, 0), new BlockVec(3, 0, 0));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : line) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidIteration() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));
        // Affecte une valeur
        Set<BlockVec> expected = Set.of(
                // Crée un nouvel objet
                new BlockVec(0, 0, 0), new BlockVec(0, 0, 1),
                // Crée un nouvel objet
                new BlockVec(0, 1, 0), new BlockVec(0, 1, 1),
                // Crée un nouvel objet
                new BlockVec(1, 0, 0), new BlockVec(1, 0, 1),
                // Crée un nouvel objet
                new BlockVec(1, 1, 0), new BlockVec(1, 1, 1));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : cuboid) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidIterationUnorderedEndpoints() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(2, 3, 4), new BlockVec(1, 2, 3));
        // Appelle une méthode
        Set<BlockVec> expected = new HashSet<>();
        // Boucle : répète un bloc
        for (int x = 1; x <= 2; x++) {
            // Boucle : répète un bloc
            for (int y = 2; y <= 3; y++) {
                // Boucle : répète un bloc
                for (int z = 3; z <= 4; z++) {
                    // Appelle une méthode
                    expected.add(new BlockVec(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : cuboid) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereIterationRadius1() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 1);
        // Appelle une méthode
        Set<BlockVec> expected = new HashSet<>();
        // Only blocks within distance 1.0 from center should be included
        // Boucle : répète un bloc
        for (int x = -1; x <= 1; x++) {
            // Boucle : répète un bloc
            for (int y = -1; y <= 1; y++) {
                // Boucle : répète un bloc
                for (int z = -1; z <= 1; z++) {
                    // Appelle une méthode
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    // Embranchement : vérifie une condition
                    if (distance <= 1.0) {
                        // Appelle une méthode
                        expected.add(new BlockVec(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : sphere) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetCuboidIteration() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));
        // Appelle une méthode
        Area offset = cuboid.offset(1, 2, 3);
        // Appelle une méthode
        Set<BlockVec> expected = new HashSet<>();
        // Boucle : répète un bloc
        for (int x = 1; x <= 2; x++) {
            // Boucle : répète un bloc
            for (int y = 2; y <= 3; y++) {
                // Boucle : répète un bloc
                for (int z = 3; z <= 4; z++) {
                    // Appelle une méthode
                    expected.add(new BlockVec(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : offset) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cubeArea() {
        // Appelle une méthode
        Area.Cuboid cube = Area.cube(new BlockVec(0, 0, 0), 2);
        // Appelle une méthode
        Set<BlockVec> expected = new HashSet<>();
        // Boucle : répète un bloc
        for (int x = -1; x <= 1; x++) {
            // Boucle : répète un bloc
            for (int y = -1; y <= 1; y++) {
                // Boucle : répète un bloc
                for (int z = -1; z <= 1; z++) {
                    // Appelle une méthode
                    expected.add(new BlockVec(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : cube) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Tests for split method
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSingleSection() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(10, 5, 5));
        // Appelle une méthode
        List<Area.Cuboid> splits = cuboid.split();
        // Appelle une méthode
        assertEquals(1, splits.size());
        // Appelle une méthode
        Area.Cuboid sub = splits.getFirst();
        // Appelle une méthode
        assertPoint(new BlockVec(0, 0, 0), sub.min());
        // Appelle une méthode
        assertPoint(new BlockVec(10, 5, 5), sub.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitMultiSectionX() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(15, 0, 0), new BlockVec(17, 1, 1));
        // Appelle une méthode
        List<Area.Cuboid> splits = cuboid.split();
        // Appelle une méthode
        assertEquals(2, splits.size());
        // Affecte une valeur
        boolean foundSec0 = false, foundSec1 = false;
        // Boucle : répète un bloc
        for (Area.Cuboid sub : splits) {
            // Embranchement : vérifie une condition
            if (sub.min().equals(new BlockVec(15, 0, 0)) && sub.max().equals(new BlockVec(15, 1, 1)))
                // Affecte une valeur
                foundSec0 = true;
            // Embranchement : vérifie une condition
            if (sub.min().equals(new BlockVec(16, 0, 0)) && sub.max().equals(new BlockVec(17, 1, 1)))
                // Affecte une valeur
                foundSec1 = true;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertTrue(foundSec0);
        // Appelle une méthode
        assertTrue(foundSec1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitOnSingle() {
        // Appelle une méthode
        BlockVec point = new BlockVec(5, 5, 5);
        // Appelle une méthode
        List<Area.Cuboid> splits = Area.single(point).split();
        // Appelle une méthode
        assertEquals(1, splits.size());
        // Appelle une méthode
        Area.Cuboid sub = splits.getFirst();
        // Appelle une méthode
        assertPoint(point, sub.min());
        // Appelle une méthode
        assertPoint(point, sub.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitLineSingleSection() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3));
        // Appelle une méthode
        List<Area.Cuboid> splits = line.split();
        // Appelle une méthode
        assertEquals(1, splits.size());
        // Appelle une méthode
        assertEquals(Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3)), splits.getFirst());

        // Verify all split blocks match the line
        // Appelle une méthode
        Set<BlockVec> expectedBlocks = Set.of(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3));
        // Appelle une méthode
        Set<BlockVec> splitBlocks = new HashSet<>();
        // Boucle : répète un bloc
        for (Area.Cuboid split : splits) {
            // Boucle : répète un bloc
            for (BlockVec block : split) {
                // Appelle une méthode
                splitBlocks.add(block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(expectedBlocks, splitBlocks);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitLineCrossSection() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(15, 0, 0), new BlockVec(17, 0, 0));
        // Appelle une méthode
        List<Area.Cuboid> splits = line.split();
        // Appelle une méthode
        assertEquals(2, splits.size());

        // Verify all split blocks match the line
        // Affecte une valeur
        Set<BlockVec> expectedBlocks = Set.of(
                // Crée un nouvel objet
                new BlockVec(15, 0, 0),
                // Crée un nouvel objet
                new BlockVec(16, 0, 0),
                // Crée un nouvel objet
                new BlockVec(17, 0, 0)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        Set<BlockVec> splitBlocks = new HashSet<>();
        // Boucle : répète un bloc
        for (Area.Cuboid split : splits) {
            // Boucle : répète un bloc
            for (BlockVec block : split) {
                // Appelle une méthode
                splitBlocks.add(block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(expectedBlocks, splitBlocks);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSphere() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 1);
        // Appelle une méthode
        List<Area.Cuboid> splits = sphere.split();
        // A sphere with radius 1 centered at origin will span multiple sections
        // since it includes blocks from (-1,-1,-1) to (1,1,1) range
        // Appelle une méthode
        assertTrue(!splits.isEmpty());

        // Verify that split covers exactly the sphere blocks
        // Appelle une méthode
        Set<BlockVec> allSplitBlocks = new HashSet<>();
        // Boucle : répète un bloc
        for (Area.Cuboid split : splits) {
            // Boucle : répète un bloc
            for (BlockVec block : split) {
                // Appelle une méthode
                allSplitBlocks.add(block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Set<BlockVec> sphereBlocks = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec block : sphere) {
            // Appelle une méthode
            sphereBlocks.add(block);
        // Fin d'un bloc/d'une expression
        }

        // All sphere blocks should be covered by splits
        // Appelle une méthode
        assertTrue(allSplitBlocks.containsAll(sphereBlocks));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSectionArea() {
        // Appelle une méthode
        Area.Cuboid section = Area.section(0, 0, 0);
        // Appelle une méthode
        Set<Area.Cuboid> expected = Set.of(section);
        // Appelle une méthode
        Set<Area.Cuboid> actual = new HashSet<>(section.split());
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitCuboidMultiSectionsX() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(17, 1, 1));
        // Spans two sections, should be split into 2 cuboids
        // Appelle une méthode
        List<Area.Cuboid> splits = cuboid.split();
        // Appelle une méthode
        assertEquals(2, splits.size());
        // Affecte une valeur
        boolean foundSec0 = false, foundSec1 = false;
        // Boucle : répète un bloc
        for (Area.Cuboid sub : splits) {
            // Embranchement : vérifie une condition
            if (sub.min().equals(new BlockVec(0, 0, 0)) && sub.max().equals(new BlockVec(15, 1, 1)))
                // Affecte une valeur
                foundSec0 = true;
            // Embranchement : vérifie une condition
            if (sub.min().equals(new BlockVec(16, 0, 0)) && sub.max().equals(new BlockVec(17, 1, 1)))
                // Affecte une valeur
                foundSec1 = true;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertTrue(foundSec0);
        // Appelle une méthode
        assertTrue(foundSec1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitTwoFullSectionsX() {
        // Cuboid covers two full 16x16x16 sections along X
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(31, 15, 15));
        // Affecte une valeur
        Set<Area.Cuboid> expected = Set.of(
                // Instruction de code
                Area.section(0, 0, 0),
                // Instruction de code
                Area.section(1, 0, 0)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        Set<Area.Cuboid> actual = new HashSet<>(cuboid.split());
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitFullGridSections() {
        // Cuboid covers a 2x2x2 grid of full sections
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(31, 31, 31));
        // Appelle une méthode
        Set<Area.Cuboid> expected = new HashSet<>();
        // Boucle : répète un bloc
        for (int x = 0; x <= 1; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y <= 1; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z <= 1; z++) {
                    // Appelle une méthode
                    expected.add(Area.section(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Set<Area.Cuboid> actual = new HashSet<>(cuboid.split());
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundSingle() {
        // Appelle une méthode
        Area.Single single = Area.single(new BlockVec(5, 10, 15));
        // Appelle une méthode
        Area.Cuboid bound = single.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(5, 10, 15), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(5, 10, 15), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundLine() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Appelle une méthode
        Area.Cuboid bound = line.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(4, 5, 6), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundLineReversed() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(4, 5, 6), new BlockVec(1, 2, 3));
        // Appelle une méthode
        Area.Cuboid bound = line.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(4, 5, 6), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundLineDiagonal() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(-2, 10, -5), new BlockVec(3, -1, 2));
        // Appelle une méthode
        Area.Cuboid bound = line.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(-2, -1, -5), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(3, 10, 2), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundCuboid() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Appelle une méthode
        Area.Cuboid bound = cuboid.bound();
        // Bounding box of a cuboid should be itself
        // Appelle une méthode
        assertPoint(cuboid.min(), bound.min());
        // Appelle une méthode
        assertPoint(cuboid.max(), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundCuboidUnordered() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(4, 5, 6), new BlockVec(1, 2, 3));
        // Appelle une méthode
        Area.Cuboid bound = cuboid.bound();
        // Should still return correctly ordered bounds
        // Appelle une méthode
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(4, 5, 6), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundSphere() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 3);
        // Appelle une méthode
        Area.Cuboid bound = sphere.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(-3, -3, -3), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(3, 3, 3), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundSphereOffset() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(10, 20, 30), 5);
        // Appelle une méthode
        Area.Cuboid bound = sphere.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(5, 15, 25), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(15, 25, 35), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundSphereZeroRadius() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(1, 2, 3), 0);
        // Appelle une méthode
        Area.Cuboid bound = sphere.bound();
        // Appelle une méthode
        assertPoint(new BlockVec(1, 2, 3), bound.min());
        // Appelle une méthode
        assertPoint(new BlockVec(1, 2, 3), bound.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void containsSingle() {
        // Appelle une méthode
        Area.Single single = Area.single(new BlockVec(1, 2, 3));

        // Appelle une méthode
        assertTrue(single.contains(new BlockVec(1, 2, 3)));
        // Appelle une méthode
        assertTrue(single.contains(new Vec(1.9, 2.9, 3.9)));
        // Appelle une méthode
        assertFalse(single.contains(new BlockVec(1, 2, 4)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void containsLine() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(4, 2, 0));

        // Appelle une méthode
        assertTrue(line.contains(new BlockVec(0, 0, 0)));
        // Appelle une méthode
        assertTrue(line.contains(new BlockVec(1, 0, 0)));
        // Appelle une méthode
        assertTrue(line.contains(new BlockVec(2, 1, 0)));
        // Appelle une méthode
        assertTrue(line.contains(new BlockVec(4, 2, 0)));
        // Appelle une méthode
        assertFalse(line.contains(new BlockVec(2, 2, 0)));
        // Appelle une méthode
        assertFalse(line.contains(new BlockVec(5, 2, 0)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void containsCuboid() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(-1, 2, 3), new BlockVec(1, 4, 5));

        // Appelle une méthode
        assertTrue(cuboid.contains(new BlockVec(-1, 2, 3)));
        // Appelle une méthode
        assertTrue(cuboid.contains(new BlockVec(0, 3, 4)));
        // Appelle une méthode
        assertTrue(cuboid.contains(new BlockVec(1, 4, 5)));
        // Appelle une méthode
        assertFalse(cuboid.contains(new BlockVec(2, 4, 5)));
        // Appelle une méthode
        assertFalse(cuboid.contains(new BlockVec(1, 5, 5)));
        // Appelle une méthode
        assertFalse(cuboid.contains(new BlockVec(1, 4, 6)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void containsSphere() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 2);

        // Appelle une méthode
        assertTrue(sphere.contains(new BlockVec(0, 0, 0)));
        // Appelle une méthode
        assertTrue(sphere.contains(new BlockVec(2, 0, 0)));
        // Appelle une méthode
        assertTrue(sphere.contains(new BlockVec(1, 1, 1)));
        // Appelle une méthode
        assertFalse(sphere.contains(new BlockVec(2, 1, 0)));
        // Appelle une méthode
        assertFalse(sphere.contains(new BlockVec(0, 0, 3)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockCountSingle() {
        // Appelle une méthode
        assertEquals(1, Area.single(new BlockVec(1, 2, 3)).blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockCountLine() {
        // Appelle une méthode
        assertEquals(1, Area.line(new BlockVec(1, 2, 3), new BlockVec(1, 2, 3)).blockCount());
        // Appelle une méthode
        assertEquals(5, Area.line(new BlockVec(0, 0, 0), new BlockVec(4, 2, 0)).blockCount());
        // Appelle une méthode
        assertEquals(6, Area.line(new BlockVec(0, 0, 0), new BlockVec(2, 5, 1)).blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockCountCuboid() {
        // Appelle une méthode
        assertEquals(1, Area.cuboid(BlockVec.ZERO, BlockVec.ZERO).blockCount());
        // Appelle une méthode
        assertEquals(24, Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(3, 2, 1)).blockCount());
        // Appelle une méthode
        assertEquals(27, Area.cube(new BlockVec(0, 0, 0), 2).blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockCountSphere() {
        // Appelle une méthode
        Area.Sphere radius0 = Area.sphere(BlockVec.ZERO, 0);
        // Appelle une méthode
        Area.Sphere radius1 = Area.sphere(BlockVec.ZERO, 1);
        // Appelle une méthode
        Area.Sphere radius2 = Area.sphere(BlockVec.ZERO, 2);

        // Appelle une méthode
        assertEquals(blocks(radius0).size(), radius0.blockCount());
        // Appelle une méthode
        assertEquals(blocks(radius1).size(), radius1.blockCount());
        // Appelle une méthode
        assertEquals(blocks(radius2).size(), radius2.blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Additional comprehensive iterator tests
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lineIteratorEdgeCases() {
        // Test zero-length line
        // Appelle une méthode
        Area.Line zeroLine = Area.line(new BlockVec(5, 5, 5), new BlockVec(5, 5, 5));
        // Appelle une méthode
        Set<BlockVec> expected = Set.of(new BlockVec(5, 5, 5));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : zeroLine) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);

        // Test negative coordinates
        // Appelle une méthode
        Area.Line negativeLine = Area.line(new BlockVec(-2, -3, -4), new BlockVec(-1, -2, -3));
        // Appelle une méthode
        expected = Set.of(new BlockVec(-2, -3, -4), new BlockVec(-1, -2, -3));
        // Appelle une méthode
        actual.clear();
        // Boucle : répète un bloc
        for (BlockVec v : negativeLine) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereIteratorVariousRadii() {
        // Test radius 0 (single block)
        // Appelle une méthode
        Area.Sphere sphere0 = Area.sphere(new BlockVec(0, 0, 0), 0);
        // Appelle une méthode
        Set<BlockVec> expected = Set.of(new BlockVec(0, 0, 0));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : sphere0) actual.add(v);
        // Appelle une méthode
        assertEquals(expected, actual);

        // Test radius 2
        // Appelle une méthode
        Area.Sphere sphere2 = Area.sphere(new BlockVec(0, 0, 0), 2);
        // Appelle une méthode
        actual.clear();
        // Boucle : répète un bloc
        for (BlockVec v : sphere2) actual.add(v);

        // Verify all blocks are within radius 2
        // Boucle : répète un bloc
        for (BlockVec block : actual) {
            // Affecte une valeur
            double distance = Math.sqrt(block.blockX() * block.blockX() +
                    // Instruction de code
                    block.blockY() * block.blockY() +
                    // Appelle une méthode
                    block.blockZ() * block.blockZ());
            // Appelle une méthode
            assertTrue(distance <= 2.0, "Block " + block + " is outside radius 2, distance: " + distance);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidIteratorLargeArea() {
        // Appelle une méthode
        Area.Cuboid largeCuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(3, 2, 1));
        // Appelle une méthode
        Set<BlockVec> actual = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec v : largeCuboid) actual.add(v);

        // Should have 4 * 3 * 2 = 24 blocks
        // Appelle une méthode
        assertEquals(24, actual.size());

        // Verify all expected blocks are present
        // Boucle : répète un bloc
        for (int x = 0; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y <= 2; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z <= 1; z++) {
                    // Appelle une méthode
                    assertTrue(actual.contains(new BlockVec(x, y, z)));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Comprehensive split() tests
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSingleInDifferentSections() {
        // Test single blocks in different sections
        // Appelle une méthode
        Area.Single single1 = Area.single(new BlockVec(0, 0, 0));
        // Appelle une méthode
        assertEquals(1, single1.split().size());

        // Appelle une méthode
        Area.Single single2 = Area.single(new BlockVec(16, 16, 16));
        // Appelle une méthode
        assertEquals(1, single2.split().size());

        // Appelle une méthode
        Area.Single single3 = Area.single(new BlockVec(-1, -1, -1));
        // Appelle une méthode
        assertEquals(1, single3.split().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitCuboidFullSectionOptimization() {
        // Test that full sections are properly identified
        // Appelle une méthode
        Area.Cuboid fullSection = Area.section(1, 1, 1);
        // Appelle une méthode
        List<Area.Cuboid> splits = fullSection.split();
        // Appelle une méthode
        assertEquals(1, splits.size());
        // Appelle une méthode
        assertEquals(fullSection, splits.getFirst());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitCuboidPartialSections() {
        // Cuboid that partially fills multiple sections
        // Appelle une méthode
        Area.Cuboid partial = Area.cuboid(new BlockVec(14, 14, 14), new BlockVec(18, 18, 18));
        // Appelle une méthode
        List<Area.Cuboid> splits = partial.split();
        // Instruction de code
        assertEquals(8, splits.size()); // 2x2x2 sections

        // Verify no section boundary violations
        // Boucle : répète un bloc
        for (Area.Cuboid split : splits) {
            // Appelle une méthode
            int secMinX = Math.floorDiv(split.min().blockX(), 16);
            // Appelle une méthode
            int secMaxX = Math.floorDiv(split.max().blockX(), 16);
            // Appelle une méthode
            int secMinY = Math.floorDiv(split.min().blockY(), 16);
            // Appelle une méthode
            int secMaxY = Math.floorDiv(split.max().blockY(), 16);
            // Appelle une méthode
            int secMinZ = Math.floorDiv(split.min().blockZ(), 16);
            // Appelle une méthode
            int secMaxZ = Math.floorDiv(split.max().blockZ(), 16);

            // Appelle une méthode
            assertEquals(secMinX, secMaxX, "Split crosses section boundary in X");
            // Appelle une méthode
            assertEquals(secMinY, secMaxY, "Split crosses section boundary in Y");
            // Appelle une méthode
            assertEquals(secMinZ, secMaxZ, "Split crosses section boundary in Z");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSphereFullAndPartialSections() {
        // Large sphere that should have both full and partial sections
        // Appelle une méthode
        Area.Sphere largeSphere = Area.sphere(new BlockVec(16, 16, 16), 20);
        // Appelle une méthode
        assertEquals(blocks(largeSphere), splitBlocks(largeSphere));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSphereZeroRadius() {
        // Appelle une méthode
        Area.Sphere pointSphere = Area.sphere(new BlockVec(5, 5, 5), 0);
        // Appelle une méthode
        List<Area.Cuboid> splits = pointSphere.split();
        // Appelle une méthode
        assertEquals(1, splits.size());

        // Appelle une méthode
        Area.Cuboid split = splits.getFirst();
        // Appelle une méthode
        assertEquals(new BlockVec(5, 5, 5), split.min());
        // Appelle une méthode
        assertEquals(new BlockVec(5, 5, 5), split.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitNegativeCoordinates() {
        // Test areas in negative coordinate space
        // Appelle une méthode
        Area.Cuboid negativeCuboid = Area.cuboid(new BlockVec(-20, -20, -20), new BlockVec(-5, -5, -5));
        // Appelle une méthode
        List<Area.Cuboid> splits = negativeCuboid.split();
        // Appelle une méthode
        assertFalse(splits.isEmpty());

        // Verify all splits are section-aligned
        // Boucle : répète un bloc
        for (Area.Cuboid split : splits) {
            // Appelle une méthode
            int secMinX = Math.floorDiv(split.min().blockX(), 16);
            // Appelle une méthode
            int secMaxX = Math.floorDiv(split.max().blockX(), 16);
            // Appelle une méthode
            assertEquals(secMinX, secMaxX, "Split crosses section boundary in negative space");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void iteratorAndSplitConsistency() {
        // Verify that split() covers exactly the same blocks as iterator()
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Appelle une méthode
            Set<BlockVec> iteratorBlocks = new HashSet<>();
            // Boucle : répète un bloc
            for (BlockVec block : area) {
                // Appelle une méthode
                iteratorBlocks.add(block);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            Set<BlockVec> splitBlocks = new HashSet<>();
            // Boucle : répète un bloc
            for (Area.Cuboid split : area.split()) {
                // Boucle : répète un bloc
                for (BlockVec block : split) {
                    // Appelle une méthode
                    splitBlocks.add(block);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Split should contain exactly the same blocks as iterator - no more, no less
            // Instruction de code
            assertEquals(iteratorBlocks, splitBlocks,
                    // Instruction de code
                    "Split blocks don't exactly match iterator blocks for " + area.getClass().getSimpleName() +
                            // Appelle une méthode
                            ". Iterator has " + iteratorBlocks.size() + " blocks, split has " + splitBlocks.size() + " blocks");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitSectionAlignment() {
        // Verify all splits are properly section-aligned
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Boucle : répète un bloc
            for (Area.Cuboid split : area.split()) {
                // Each split should be within a single section
                // Appelle une méthode
                int secMinX = Math.floorDiv(split.min().blockX(), 16);
                // Appelle une méthode
                int secMaxX = Math.floorDiv(split.max().blockX(), 16);
                // Appelle une méthode
                int secMinY = Math.floorDiv(split.min().blockY(), 16);
                // Appelle une méthode
                int secMaxY = Math.floorDiv(split.max().blockY(), 16);
                // Appelle une méthode
                int secMinZ = Math.floorDiv(split.min().blockZ(), 16);
                // Appelle une méthode
                int secMaxZ = Math.floorDiv(split.max().blockZ(), 16);

                // Appelle une méthode
                assertEquals(secMinX, secMaxX, "Split crosses section boundary in X for " + area.getClass().getSimpleName());
                // Appelle une méthode
                assertEquals(secMinY, secMaxY, "Split crosses section boundary in Y for " + area.getClass().getSimpleName());
                // Appelle une méthode
                assertEquals(secMinZ, secMaxZ, "Split crosses section boundary in Z for " + area.getClass().getSimpleName());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void factoryMethodsFloorPointCoordinates() {
        // Appelle une méthode
        assertEquals(new BlockVec(1, -2, 3), Area.single(new Vec(1.9, -1.1, 3.0)).point());
        // Appelle une méthode
        assertEquals(new BlockVec(1, -2, 3), Area.line(new Vec(1.9, -1.1, 3.0), new Vec(4.2, 5.8, -6.1)).start());
        // Appelle une méthode
        assertEquals(new BlockVec(4, 5, -7), Area.line(new Vec(1.9, -1.1, 3.0), new Vec(4.2, 5.8, -6.1)).end());
        // Appelle une méthode
        assertEquals(new BlockVec(0, 0, 0), Area.cuboid(new Vec(1.9, 2.9, 3.9), new Vec(0.1, 0.1, 0.1)).min());
        // Appelle une méthode
        assertEquals(new BlockVec(1, 2, 3), Area.cuboid(new Vec(1.9, 2.9, 3.9), new Vec(0.1, 0.1, 0.1)).max());
        // Appelle une méthode
        assertEquals(new BlockVec(-1, 2, 3), Area.sphere(new Vec(-0.1, 2.9, 3.0), 2).center());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boxArea() {
        // Appelle une méthode
        Area.Cuboid box = Area.box(new BlockVec(10, 10, 10), new Vec(4, 2, 6));

        // Appelle une méthode
        assertEquals(new BlockVec(8, 9, 7), box.min());
        // Appelle une méthode
        assertEquals(new BlockVec(12, 11, 13), box.max());
        // Appelle une méthode
        assertEquals(5 * 3 * 7, blocks(box).size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void negativeSphereRadiusRejected() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Area.sphere(BlockVec.ZERO, -1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void negativeCubeSizeRejected() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Area.cube(BlockVec.ZERO, -1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void negativeBoxSizeRejected() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Area.box(BlockVec.ZERO, new Vec(-1, 2, 3)));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Area.box(BlockVec.ZERO, new Vec(1, -2, 3)));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> Area.box(BlockVec.ZERO, new Vec(1, 2, -3)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nullPointsRejected() {
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.single(null));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.line(null, BlockVec.ZERO));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.line(BlockVec.ZERO, null));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.cuboid(null, BlockVec.ZERO));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.cuboid(BlockVec.ZERO, null));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.box(null, BlockVec.ONE));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.box(BlockVec.ZERO, null));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> Area.sphere(null, 1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockCountMatchesIteratorAllAreas() {
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Affecte une valeur
            long iterated = 0;
            // Boucle : répète un bloc
            for (BlockVec ignored : area) iterated++;
            // Instruction de code
            assertEquals(iterated, area.blockCount(),
                    // Instruction de code
                    "blockCount mismatch for " + area);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void containsReturnsTrueForEveryIteratedBlock() {
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Boucle : répète un bloc
            for (BlockVec block : area) {
                // Instruction de code
                assertTrue(area.contains(block),
                        // Appelle une méthode
                        "contains(" + block + ") returned false for iterated block of " + area);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void containsRejectsBlocksOutsideBounds() {
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Appelle une méthode
            Area.Cuboid bound = area.bound();
            // Appelle une méthode
            BlockVec outside = bound.max().add(100, 100, 100).asBlockVec();
            // Instruction de code
            assertFalse(area.contains(outside),
                    // Instruction de code
                    "contains should reject far-outside block for " + area);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetPreservesBlockCount() {
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Appelle une méthode
            Area offset = area.offset(7, -3, 11);
            // Instruction de code
            assertEquals(area.blockCount(), offset.blockCount(),
                    // Instruction de code
                    "offset changed blockCount for " + area);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetSingle() {
        // Appelle une méthode
        Area.Single single = Area.single(new BlockVec(1, 2, 3));
        // Appelle une méthode
        Area offset = single.offset(4, 5, 6);
        // Appelle une méthode
        assertInstanceOf(Area.Single.class, offset);
        // Appelle une méthode
        assertEquals(new BlockVec(5, 7, 9), ((Area.Single) offset).point());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetLine() {
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(3, 0, 0));
        // Appelle une méthode
        Area offset = line.offset(10, 20, 30);
        // Appelle une méthode
        assertInstanceOf(Area.Line.class, offset);
        // Appelle une méthode
        Area.Line shifted = (Area.Line) offset;
        // Appelle une méthode
        assertEquals(new BlockVec(10, 20, 30), shifted.start());
        // Appelle une méthode
        assertEquals(new BlockVec(13, 20, 30), shifted.end());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetSphere() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 4);
        // Appelle une méthode
        Area offset = sphere.offset(1, 2, 3);
        // Appelle une méthode
        assertInstanceOf(Area.Sphere.class, offset);
        // Appelle une méthode
        Area.Sphere shifted = (Area.Sphere) offset;
        // Appelle une méthode
        assertEquals(new BlockVec(1, 2, 3), shifted.center());
        // Appelle une méthode
        assertEquals(4, shifted.radius());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetByPoint() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));
        // Appelle une méthode
        Area offset = cuboid.offset(new Vec(2.9, -1.5, 4.0));
        // Appelle une méthode
        assertInstanceOf(Area.Cuboid.class, offset);
        // Appelle une méthode
        Area.Cuboid shifted = (Area.Cuboid) offset;
        // Appelle une méthode
        assertEquals(new BlockVec(2, -2, 4), shifted.min());
        // Appelle une méthode
        assertEquals(new BlockVec(3, -1, 5), shifted.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recordEquality() {
        // Appelle une méthode
        assertEquals(Area.single(new BlockVec(1, 2, 3)), Area.single(new BlockVec(1, 2, 3)));
        // Instruction de code
        assertEquals(Area.single(new BlockVec(1, 2, 3)).hashCode(),
                // Appelle une méthode
                Area.single(new BlockVec(1, 2, 3)).hashCode());

        // Instruction de code
        assertEquals(Area.line(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)),
                // Appelle une méthode
                Area.line(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)));

        // Instruction de code
        assertEquals(Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)),
                // Appelle une méthode
                Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)));
        // Cuboid equality holds regardless of argument order (auto-ordered)
        // Instruction de code
        assertEquals(Area.cuboid(new BlockVec(0, 0, 0), new BlockVec(5, 5, 5)),
                // Appelle une méthode
                Area.cuboid(new BlockVec(5, 5, 5), new BlockVec(0, 0, 0)));

        // Instruction de code
        assertEquals(Area.sphere(new BlockVec(1, 2, 3), 4),
                // Appelle une méthode
                Area.sphere(new BlockVec(1, 2, 3), 4));

        // Appelle une méthode
        assertNotEquals(Area.single(new BlockVec(1, 2, 3)), Area.single(new BlockVec(1, 2, 4)));
        // Appelle une méthode
        assertNotEquals(Area.sphere(new BlockVec(0, 0, 0), 3), Area.sphere(new BlockVec(0, 0, 0), 4));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidConstructorOrders() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(5, 5, 5), new BlockVec(0, 0, 0));
        // Appelle une méthode
        assertEquals(new BlockVec(0, 0, 0), cuboid.min());
        // Appelle une méthode
        assertEquals(new BlockVec(5, 5, 5), cuboid.max());

        // Already-ordered inputs pass through unchanged
        // Appelle une méthode
        BlockVec min = new BlockVec(0, 0, 0);
        // Appelle une méthode
        BlockVec max = new BlockVec(5, 5, 5);
        // Appelle une méthode
        Area.Cuboid ordered = Area.cuboid(min, max);
        // Appelle une méthode
        assertSame(min, ordered.min());
        // Appelle une méthode
        assertSame(max, ordered.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cubeSizeZero() {
        // Appelle une méthode
        Area.Cuboid cube = Area.cube(new BlockVec(5, 5, 5), 0);
        // Appelle une méthode
        assertEquals(new BlockVec(5, 5, 5), cube.min());
        // Appelle une méthode
        assertEquals(new BlockVec(5, 5, 5), cube.max());
        // Appelle une méthode
        assertEquals(1, cube.blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boxZeroSize() {
        // Appelle une méthode
        Area.Cuboid box = Area.box(new BlockVec(3, 3, 3), Vec.ZERO);
        // Appelle une méthode
        assertEquals(new BlockVec(3, 3, 3), box.min());
        // Appelle une méthode
        assertEquals(new BlockVec(3, 3, 3), box.max());
        // Appelle une méthode
        assertEquals(1, box.blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereZeroRadiusBlockCount() {
        // Appelle une méthode
        assertEquals(1, Area.sphere(new BlockVec(5, 5, 5), 0).blockCount());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereLargerRadiusSplitMatchesIterator() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(0, 0, 0), 7);
        // Appelle une méthode
        assertEquals(blocks(sphere), splitBlocks(sphere));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereSplitCenteredOnSectionBoundary() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(16, 16, 16), 4);
        // Appelle une méthode
        assertEquals(blocks(sphere), splitBlocks(sphere));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sphereSplitNegativeCenter() {
        // Appelle une méthode
        Area.Sphere sphere = Area.sphere(new BlockVec(-8, -8, -8), 5);
        // Appelle une méthode
        assertEquals(blocks(sphere), splitBlocks(sphere));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cuboidContainedInSingleSectionSplitReturnsSelf() {
        // Appelle une méthode
        Area.Cuboid cuboid = Area.cuboid(new BlockVec(1, 2, 3), new BlockVec(4, 5, 6));
        // Appelle une méthode
        List<Area.Cuboid> splits = cuboid.split();
        // Appelle une méthode
        assertEquals(1, splits.size());
        // Appelle une méthode
        assertEquals(cuboid, splits.getFirst());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sectionAtNegativeCoordinates() {
        // Appelle une méthode
        Area.Cuboid section = Area.section(-1, -1, -1);
        // Appelle une méthode
        assertEquals(new BlockVec(-16, -16, -16), section.min());
        // Appelle une méthode
        assertEquals(new BlockVec(-1, -1, -1), section.max());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void boundOfOffsetMatchesOffsetOfBound() {
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Affecte une valeur
            Area.Cuboid expected = Area.cuboid(
                    // Instruction de code
                    area.bound().min().add(2, 3, 4).asBlockVec(),
                    // Appelle une méthode
                    area.bound().max().add(2, 3, 4).asBlockVec());
            // Appelle une méthode
            Area.Cuboid actual = area.offset(2, 3, 4).bound();
            // Appelle une méthode
            assertEquals(expected, actual, "bound mismatch after offset for " + area);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void splitNeverEmptyForNonEmptyArea() {
        // Boucle : répète un bloc
        for (Area area : areas()) {
            // Appelle une méthode
            assertFalse(area.split().isEmpty(), "split should not be empty for " + area);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lineDiagonal3D() {
        // Verify that a 3D diagonal line generates the expected number of blocks
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(10, 10, 10));
        // Appelle une méthode
        assertEquals(11, line.blockCount());
        // Appelle une méthode
        Set<BlockVec> blocks = blocks(line);
        // Appelle une méthode
        assertEquals(11, blocks.size());
        // Each block should be on the diagonal (x == y == z)
        // Boucle : répète un bloc
        for (BlockVec block : blocks) {
            // Appelle une méthode
            assertEquals(block.blockX(), block.blockY());
            // Appelle une méthode
            assertEquals(block.blockY(), block.blockZ());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lineContainsRejectsPointsOnBoundingBoxButOffLine() {
        // Bresenham diagonal: (0,0,0) -> (4,2,0) — point (4,0,0) is in the bbox but not on the line
        // Appelle une méthode
        Area.Line line = Area.line(new BlockVec(0, 0, 0), new BlockVec(4, 2, 0));
        // Appelle une méthode
        assertFalse(line.contains(new BlockVec(4, 0, 0)));
        // Appelle une méthode
        assertFalse(line.contains(new BlockVec(0, 2, 0)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Area> areas() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Area.single(new BlockVec(7, 8, 9)),
                // Instruction de code
                Area.line(new BlockVec(0, 0, 0), new BlockVec(5, 3, 2)),
                // Instruction de code
                Area.cuboid(new BlockVec(10, 10, 10), new BlockVec(12, 12, 12)),
                // Instruction de code
                Area.sphere(new BlockVec(0, 0, 0), 3),
                // Instruction de code
                Area.line(new BlockVec(14, 0, 0), new BlockVec(34, 0, 0)), // Multi-section line
                // Instruction de code
                Area.sphere(new BlockVec(8, 8, 8), 2), // Small multisection sphere
                // Instruction de code
                Area.cuboid(new BlockVec(-5, -5, -5), new BlockVec(5, 5, 5)) // Negative coordinates
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<BlockVec> blocks(Area area) {
        // Appelle une méthode
        Set<BlockVec> blocks = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec block : area) {
            // Appelle une méthode
            blocks.add(block);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return blocks;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<BlockVec> splitBlocks(Area area) {
        // Appelle une méthode
        Set<BlockVec> blocks = new HashSet<>();
        // Boucle : répète un bloc
        for (Area.Cuboid split : area.split()) {
            // Boucle : répète un bloc
            for (BlockVec block : split) {
                // Appelle une méthode
                blocks.add(block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return blocks;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
