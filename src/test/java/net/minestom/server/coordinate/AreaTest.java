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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
        Set<BlockVec> expected = new HashSet<>();
        // Only blocks within distance 1.0 from center should be included
        // Boucle : répète un bloc
        for (int x = -1; x <= 1; x++) {
            // Boucle : répète un bloc
            for (int y = -1; y <= 1; y++) {
                // Boucle : répète un bloc
                for (int z = -1; z <= 1; z++) {
                    // Boucle : répète un bloc
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Instruction de code
        assertEquals(2, splits.size()); // Now returns individual single-block cuboids

        // Verify all splits are single blocks that match the line
        // Appelle une méthode
        Set<BlockVec> expectedBlocks = Set.of(new BlockVec(1, 2, 3), new BlockVec(2, 2, 3));
        // Affecte une valeur
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
        // Instruction de code
        assertEquals(3, splits.size()); // Now returns individual single-block cuboids

        // Verify all splits are single blocks that match the line
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
        // Affecte une valeur
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
        assertTrue(splits.size() > 0);

        // Verify that split covers exactly the sphere blocks
        // Affecte une valeur
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

        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
        // Affecte une valeur
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
            // Boucle : répète un bloc
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
        // Affecte une valeur
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
        List<Area.Cuboid> splits = largeSphere.split();

        // Verify that split covers all sphere blocks
        // Affecte une valeur
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

        // Affecte une valeur
        Set<BlockVec> sphereBlocks = new HashSet<>();
        // Boucle : répète un bloc
        for (BlockVec block : largeSphere) {
            // Appelle une méthode
            sphereBlocks.add(block);
        // Fin d'un bloc/d'une expression
        }

        // All sphere blocks should be covered by splits
        // Appelle une méthode
        assertTrue(allSplitBlocks.containsAll(sphereBlocks));

        // No extra blocks should be in splits (beyond minimal bounding)
        // Boucle : répète un bloc
        for (BlockVec block : allSplitBlocks) {
            // Check if block is within the sphere's bounding box
            // Appelle une méthode
            assertTrue(block.blockX() >= 16 - 20 && block.blockX() <= 16 + 20);
            // Appelle une méthode
            assertTrue(block.blockY() >= 16 - 20 && block.blockY() <= 16 + 20);
            // Appelle une méthode
            assertTrue(block.blockZ() >= 16 - 20 && block.blockZ() <= 16 + 20);
        // Fin d'un bloc/d'une expression
        }
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
            // Affecte une valeur
            Set<BlockVec> iteratorBlocks = new HashSet<>();
            // Boucle : répète un bloc
            for (BlockVec block : area) {
                // Appelle une méthode
                iteratorBlocks.add(block);
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
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
                Area.sphere(new BlockVec(8, 8, 8), 2), // Small multi-section sphere
                // Instruction de code
                Area.cuboid(new BlockVec(-5, -5, -5), new BlockVec(5, 5, 5)) // Negative coordinates
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
