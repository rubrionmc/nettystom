// Déclaration du paquet de ce fichier
package net.minestom.server.utils.block;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class BlockIteratorTest {
    // Début d'une méthode/d'un bloc
    private void assertContains(List<Point> points, Point point) {
        // Appelle une méthode
        assertTrue(points.contains(point), "Expected " + points + " to contain " + point);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dOffsetppp() {
        // Appelle une méthode
        Vec s = new Vec(0, 0.1, 0);
        // Appelle une méthode
        Vec e = new Vec(2, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(1, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(1, 1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(2, 1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(3, 1, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dOffsetppn() {
        // Appelle une méthode
        Vec s = new Vec(0, 0.1, 0);
        // Appelle une méthode
        Vec e = new Vec(-2, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-1, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-2, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-2, 1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-3, 1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-4, 1, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dOffsetnpp() {
        // Appelle une méthode
        Vec s = new Vec(0, -0.1, 0);
        // Appelle une méthode
        Vec e = new Vec(2, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        assertEquals(new Vec(0, -1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(1, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(2, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(2, 1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(3, 1, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dOffsetnnp() {
        // Appelle une méthode
        Vec s = new Vec(0, -0.1, 0);
        // Appelle une méthode
        Vec e = new Vec(-2, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        assertEquals(new Vec(0, -1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-1, -1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-1, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-2, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-3, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-3, 1, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(-4, 1, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testZeroVelocity() {
        // Appelle une méthode
        Vec s = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec e = new Vec(0, 0, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testLongDistance() {
        // Appelle une méthode
        Vec s = new Vec(42.5, 0, 51.5);
        // Appelle une méthode
        Vec e = new Vec(-12, 0, -36);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 37);

        // Appelle une méthode
        List<Point> points = new ArrayList<>();
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            points.add(iterator.next());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Point[] validPoints = new Point[]{
                // Crée un nouvel objet
                new Vec(42.0, 0.0, 51.0),
                // Crée un nouvel objet
                new Vec(42.0, 0.0, 50.0),
                // Crée un nouvel objet
                new Vec(41.0, 0.0, 50.0),
                // Crée un nouvel objet
                new Vec(42.0, 0.0, 49.0),
                // Crée un nouvel objet
                new Vec(41.0, 0.0, 49.0),
                // Crée un nouvel objet
                new Vec(41.0, 0.0, 48.0),
                // Crée un nouvel objet
                new Vec(41.0, 0.0, 47.0),
                // Crée un nouvel objet
                new Vec(40.0, 0.0, 47.0),
                // Crée un nouvel objet
                new Vec(41.0, 0.0, 46.0),
                // Crée un nouvel objet
                new Vec(40.0, 0.0, 46.0),
                // Crée un nouvel objet
                new Vec(40.0, 0.0, 45.0),
                // Crée un nouvel objet
                new Vec(40.0, 0.0, 44.0),
                // Crée un nouvel objet
                new Vec(39.0, 0.0, 44.0),
                // Crée un nouvel objet
                new Vec(40.0, 0.0, 43.0),
                // Crée un nouvel objet
                new Vec(39.0, 0.0, 43.0),
                // Crée un nouvel objet
                new Vec(39.0, 0.0, 42.0),
                // Crée un nouvel objet
                new Vec(39.0, 0.0, 41.0),
                // Crée un nouvel objet
                new Vec(38.0, 0.0, 41.0),
                // Crée un nouvel objet
                new Vec(39.0, 0.0, 40.0),
                // Crée un nouvel objet
                new Vec(38.0, 0.0, 40.0),
                // Crée un nouvel objet
                new Vec(38.0, 0.0, 39.0),
                // Crée un nouvel objet
                new Vec(38.0, 0.0, 38.0),
                // Crée un nouvel objet
                new Vec(37.0, 0.0, 38.0),
                // Crée un nouvel objet
                new Vec(38.0, 0.0, 37.0),
                // Crée un nouvel objet
                new Vec(37.0, 0.0, 37.0),
                // Crée un nouvel objet
                new Vec(37.0, 0.0, 36.0),
                // Crée un nouvel objet
                new Vec(37.0, 0.0, 35.0),
                // Crée un nouvel objet
                new Vec(36.0, 0.0, 35.0),
                // Crée un nouvel objet
                new Vec(37.0, 0.0, 34.0),
                // Crée un nouvel objet
                new Vec(36.0, 0.0, 34.0),
                // Crée un nouvel objet
                new Vec(36.0, 0.0, 33.0),
                // Crée un nouvel objet
                new Vec(36.0, 0.0, 32.0),
                // Crée un nouvel objet
                new Vec(35.0, 0.0, 32.0),
                // Crée un nouvel objet
                new Vec(36.0, 0.0, 31.0),
                // Crée un nouvel objet
                new Vec(35.0, 0.0, 31.0),
                // Crée un nouvel objet
                new Vec(35.0, 0.0, 30.0),
                // Crée un nouvel objet
                new Vec(35.0, 0.0, 29.0),
                // Crée un nouvel objet
                new Vec(34.0, 0.0, 29.0),
                // Crée un nouvel objet
                new Vec(35.0, 0.0, 28.0),
                // Crée un nouvel objet
                new Vec(34.0, 0.0, 28.0),
                // Crée un nouvel objet
                new Vec(34.0, 0.0, 27.0),
                // Crée un nouvel objet
                new Vec(34.0, 0.0, 26.0),
                // Crée un nouvel objet
                new Vec(33.0, 0.0, 26.0),
                // Crée un nouvel objet
                new Vec(34.0, 0.0, 25.0),
                // Crée un nouvel objet
                new Vec(33.0, 0.0, 25.0),
                // Crée un nouvel objet
                new Vec(33.0, 0.0, 24.0),
                // Crée un nouvel objet
                new Vec(33.0, 0.0, 23.0),
                // Crée un nouvel objet
                new Vec(32.0, 0.0, 23.0),
                // Crée un nouvel objet
                new Vec(33.0, 0.0, 22.0),
                // Crée un nouvel objet
                new Vec(32.0, 0.0, 22.0),
                // Crée un nouvel objet
                new Vec(32.0, 0.0, 21.0),
                // Crée un nouvel objet
                new Vec(32.0, 0.0, 20.0),
                // Crée un nouvel objet
                new Vec(31.0, 0.0, 20.0),
                // Crée un nouvel objet
                new Vec(32.0, 0.0, 19.0),
                // Crée un nouvel objet
                new Vec(31.0, 0.0, 19.0),
                // Crée un nouvel objet
                new Vec(31.0, 0.0, 18.0),
                // Crée un nouvel objet
                new Vec(31.0, 0.0, 17.0),
                // Crée un nouvel objet
                new Vec(30.0, 0.0, 17.0),
                // Crée un nouvel objet
                new Vec(31.0, 0.0, 16.0),
                // Crée un nouvel objet
                new Vec(30.0, 0.0, 16.0)
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point p : validPoints) {
            // Appelle une méthode
            assertContains(points, p);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(validPoints.length, points.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSkipping() {
        // Appelle une méthode
        Vec s = new Vec(0.5, 40, 0.5);
        // Appelle une méthode
        Vec e = new Vec(27, 0, 21);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 34);

        // Appelle une méthode
        List<Point> points = new ArrayList<>();
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            points.add(iterator.next());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Point[] validPoints = new Point[]{
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 0.0),
                // Crée un nouvel objet
                new Vec(1.0, 40.0, 0.0),
                // Crée un nouvel objet
                new Vec(1.0, 40.0, 1.0),
                // Crée un nouvel objet
                new Vec(2.0, 40.0, 1.0),
                // Crée un nouvel objet
                new Vec(2.0, 40.0, 2.0),
                // Crée un nouvel objet
                new Vec(3.0, 40.0, 2.0),
                // Crée un nouvel objet
                new Vec(3.0, 40.0, 3.0),
                // Crée un nouvel objet
                new Vec(4.0, 40.0, 3.0),
                // Crée un nouvel objet
                new Vec(5.0, 40.0, 3.0),
                // Crée un nouvel objet
                new Vec(4.0, 40.0, 4.0),
                // Crée un nouvel objet
                new Vec(5.0, 40.0, 4.0),
                // Crée un nouvel objet
                new Vec(6.0, 40.0, 4.0),
                // Crée un nouvel objet
                new Vec(6.0, 40.0, 5.0),
                // Crée un nouvel objet
                new Vec(7.0, 40.0, 5.0),
                // Crée un nouvel objet
                new Vec(7.0, 40.0, 6.0),
                // Crée un nouvel objet
                new Vec(8.0, 40.0, 6.0),
                // Crée un nouvel objet
                new Vec(8.0, 40.0, 7.0),
                // Crée un nouvel objet
                new Vec(9.0, 40.0, 7.0),
                // Crée un nouvel objet
                new Vec(10.0, 40.0, 7.0),
                // Crée un nouvel objet
                new Vec(10.0, 40.0, 8.0),
                // Crée un nouvel objet
                new Vec(11.0, 40.0, 8.0),
                // Crée un nouvel objet
                new Vec(11.0, 40.0, 9.0),
                // Crée un nouvel objet
                new Vec(12.0, 40.0, 9.0),
                // Crée un nouvel objet
                new Vec(12.0, 40.0, 10.0),
                // Crée un nouvel objet
                new Vec(13.0, 40.0, 10.0),
                // Crée un nouvel objet
                new Vec(14.0, 40.0, 10.0),
                // Crée un nouvel objet
                new Vec(13.0, 40.0, 11.0),
                // Crée un nouvel objet
                new Vec(14.0, 40.0, 11.0),
                // Crée un nouvel objet
                new Vec(15.0, 40.0, 11.0),
                // Crée un nouvel objet
                new Vec(15.0, 40.0, 12.0),
                // Crée un nouvel objet
                new Vec(16.0, 40.0, 12.0),
                // Crée un nouvel objet
                new Vec(16.0, 40.0, 13.0),
                // Crée un nouvel objet
                new Vec(17.0, 40.0, 13.0),
                // Crée un nouvel objet
                new Vec(17.0, 40.0, 14.0),
                // Crée un nouvel objet
                new Vec(18.0, 40.0, 14.0),
                // Crée un nouvel objet
                new Vec(19.0, 40.0, 14.0),
                // Crée un nouvel objet
                new Vec(19.0, 40.0, 15.0),
                // Crée un nouvel objet
                new Vec(20.0, 40.0, 15.0),
                // Crée un nouvel objet
                new Vec(20.0, 40.0, 16.0),
                // Crée un nouvel objet
                new Vec(21.0, 40.0, 16.0),
                // Crée un nouvel objet
                new Vec(21.0, 40.0, 17.0),
                // Crée un nouvel objet
                new Vec(22.0, 40.0, 17.0),
                // Crée un nouvel objet
                new Vec(23.0, 40.0, 17.0),
                // Crée un nouvel objet
                new Vec(22.0, 40.0, 18.0),
                // Crée un nouvel objet
                new Vec(23.0, 40.0, 18.0),
                // Crée un nouvel objet
                new Vec(24.0, 40.0, 18.0),
                // Crée un nouvel objet
                new Vec(24.0, 40.0, 19.0),
                // Crée un nouvel objet
                new Vec(25.0, 40.0, 19.0),
                // Crée un nouvel objet
                new Vec(25.0, 40.0, 20.0),
                // Crée un nouvel objet
                new Vec(26.0, 40.0, 20.0),
                // Crée un nouvel objet
                new Vec(26.0, 40.0, 21.0),
                // Crée un nouvel objet
                new Vec(27.0, 40.0, 21.0)
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point p : validPoints) {
            // Appelle une méthode
            assertContains(points, p);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(validPoints.length, points.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testExactEnd() {
        // Appelle une méthode
        Vec s = new Vec(0.5, 0, 0.5);
        // Appelle une méthode
        Vec e = new Vec(0, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 1);
        // Appelle une méthode
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Appelle une méthode
        assertEquals(new Vec(0, 1, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSameEnd() {
        // Appelle une méthode
        Vec s = new Vec(0.5, 0, 0.5);
        // Appelle une méthode
        Vec e = new Vec(0, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 0.5);
        // Appelle une méthode
        assertEquals(new Vec(0, 0, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test3dExtraCollection() {
        // Appelle une méthode
        Vec s = new Vec(0.1, 0.1, 0.1);
        // Appelle une méthode
        Vec e = new Vec(1, 1, 1);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        List<Point> points = new ArrayList<>();
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            points.add(iterator.next());
        // Fin d'un bloc/d'une expression
        }

        // todo(mattw): I need to confirm that these are correct
        // Affecte une valeur
        Point[] validPoints = new Point[]{
                // Crée un nouvel objet
                new Vec(0.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(1.0, 1.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 1.0, 1.0),
                // Crée un nouvel objet
                new Vec(1.0, 0.0, 1.0),
                // Crée un nouvel objet
                new Vec(1.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 1.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 0.0, 1.0),
                // Crée un nouvel objet
                new Vec(1.0, 1.0, 1.0),
                // Crée un nouvel objet
                new Vec(2.0, 2.0, 1.0),
                // Crée un nouvel objet
                new Vec(1.0, 2.0, 2.0),
                // Crée un nouvel objet
                new Vec(2.0, 1.0, 2.0),
                // Crée un nouvel objet
                new Vec(2.0, 1.0, 1.0),
                // Crée un nouvel objet
                new Vec(1.0, 2.0, 1.0),
                // Crée un nouvel objet
                new Vec(1.0, 1.0, 2.0),
                // Crée un nouvel objet
                new Vec(2.0, 2.0, 2.0)
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point p : validPoints) {
            // Appelle une méthode
            assertContains(points, p);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(validPoints.length, points.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dpp() {
        // Appelle une méthode
        Vec s = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec e = new Vec(2, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        List<Point> points = new ArrayList<>();
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            points.add(iterator.next());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Point[] validPoints = new Point[]{
                // Crée un nouvel objet
                new Vec(0.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(1.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(2.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(1.0, 1.0, 0.0),
                // Crée un nouvel objet
                new Vec(2.0, 1.0, 0.0),
                // Crée un nouvel objet
                new Vec(3.0, 1.0, 0.0),
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point p : validPoints) {
            // Appelle une méthode
            assertContains(points, p);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(validPoints.length, points.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dpn() {
        // Appelle une méthode
        Vec s = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec e = new Vec(-2, 1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        List<Point> points = new ArrayList<>();
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            points.add(iterator.next());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Point[] validPoints = new Point[]{
                // Crée un nouvel objet
                new Vec(0.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(-1.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(-2.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(-3.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(-2.0, 1.0, 0.0),
                // Crée un nouvel objet
                new Vec(-3.0, 1.0, 0.0),
                // Crée un nouvel objet
                new Vec(-4.0, 1.0, 0.0)
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point p : validPoints) {
            // Appelle une méthode
            assertContains(points, p);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(validPoints.length, points.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test2dnn() {
        // Appelle une méthode
        Vec s = new Vec(0, 0, 0);
        // Appelle une méthode
        Vec e = new Vec(-2, -1, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 4);

        // Appelle une méthode
        List<Point> points = new ArrayList<>();
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            points.add(iterator.next());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Point[] validPoints = new Point[]{
                // Crée un nouvel objet
                new Vec(0.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(-1.0, 0.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, -1.0, 0.0),
                // Crée un nouvel objet
                new Vec(-1.0, -1.0, 0.0),
                // Crée un nouvel objet
                new Vec(-2.0, -1.0, 0.0),
                // Crée un nouvel objet
                new Vec(-3.0, -1.0, 0.0),
                // Crée un nouvel objet
                new Vec(-2.0, -2.0, 0.0),
                // Crée un nouvel objet
                new Vec(-3.0, -2.0, 0.0),
                // Crée un nouvel objet
                new Vec(-4.0, -2.0, 0.0)
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point p : validPoints) {
            // Appelle une méthode
            assertContains(points, p);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(validPoints.length, points.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void falling() {
        // Appelle une méthode
        Vec s = new Vec(0, 42, 0);
        // Appelle une méthode
        Vec e = new Vec(0, -10, 0);
        // Appelle une méthode
        BlockIterator iterator = new BlockIterator(s, e, 0, 14.142135623730951);

        // Boucle : répète un bloc
        for (int y = 42; y >= 27; --y) assertEquals(new Vec(0, y, 0), iterator.next());
        // Appelle une méthode
        assertFalse(iterator.hasNext());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}