// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static net.minestom.server.instance.palette.PaletteAssertions.assertAllEquals;
// Import statique d'un membre
import static net.minestom.server.instance.palette.PaletteAssertions.testPalettes;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class PaletteTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singlePlacement() {
        // Appelle une méthode
        var palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 0, 1, 1);
        // Appelle une méthode
        assertEquals(1, palette.get(0, 0, 1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void placement() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            assertEquals(0, palette.get(0, 0, 0), "Default value should be 0");
            // Appelle une méthode
            assertEquals(0, palette.count());
            // Appelle une méthode
            palette.set(0, 0, 0, 64);
            // Appelle une méthode
            assertEquals(64, palette.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(1, palette.count());

            // Appelle une méthode
            palette.set(1, 0, 0, 65);
            // Appelle une méthode
            assertEquals(64, palette.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(65, palette.get(1, 0, 0));
            // Appelle une méthode
            assertEquals(2, palette.count());

            // Appelle une méthode
            palette.set(0, 1, 0, 66);
            // Appelle une méthode
            assertEquals(64, palette.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(65, palette.get(1, 0, 0));
            // Appelle une méthode
            assertEquals(66, palette.get(0, 1, 0));
            // Appelle une méthode
            assertEquals(3, palette.count());

            // Appelle une méthode
            palette.set(0, 0, 1, 67);
            // Appelle une méthode
            assertEquals(64, palette.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(65, palette.get(1, 0, 0));
            // Appelle une méthode
            assertEquals(66, palette.get(0, 1, 0));
            // Appelle une méthode
            assertEquals(67, palette.get(0, 0, 1));
            // Appelle une méthode
            assertEquals(4, palette.count());

            // Appelle une méthode
            palette.set(0, 0, 1, 68);
            // Appelle une méthode
            assertEquals(4, palette.count());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void placementHighValue() {
        // Affecte une valeur
        final int value = 250_000;
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 1, value);
            // Appelle une méthode
            assertEquals(value, palette.get(0, 0, 1));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void negPlacement() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            assertThrows(IllegalArgumentException.class, () -> palette.set(-1, 0, 0, 64));
            // Appelle une méthode
            assertThrows(IllegalArgumentException.class, () -> palette.set(0, -1, 0, 64));
            // Appelle une méthode
            assertThrows(IllegalArgumentException.class, () -> palette.set(0, 0, -1, 64));

            // Appelle une méthode
            assertThrows(IllegalArgumentException.class, () -> palette.get(-1, 0, 0));
            // Appelle une méthode
            assertThrows(IllegalArgumentException.class, () -> palette.get(0, -1, 0));
            // Appelle une méthode
            assertThrows(IllegalArgumentException.class, () -> palette.get(0, 0, -1));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void resize() {
        // Appelle une méthode
        Palette palette = Palette.sized(16, 1, 5, 15, 2);
        // Appelle une méthode
        palette.set(0, 0, 0, 1);
        // Appelle une méthode
        assertEquals(2, palette.bitsPerEntry());
        // Appelle une méthode
        palette.set(0, 0, 1, 2);
        // Appelle une méthode
        assertEquals(2, palette.bitsPerEntry());
        // Appelle une méthode
        palette.set(0, 0, 2, 3);
        // Appelle une méthode
        assertEquals(2, palette.bitsPerEntry());

        // Appelle une méthode
        palette.set(0, 0, 3, 4);
        // Appelle une méthode
        assertEquals(3, palette.bitsPerEntry());
        // Appelle une méthode
        assertEquals(1, palette.get(0, 0, 0));
        // Appelle une méthode
        assertEquals(2, palette.get(0, 0, 1));
        // Appelle une méthode
        assertEquals(3, palette.get(0, 0, 2));
        // Appelle une méthode
        assertEquals(4, palette.get(0, 0, 3));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fill() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            assertEquals(0, palette.count());
            // Appelle une méthode
            palette.set(0, 0, 0, 5);
            // Appelle une méthode
            assertEquals(1, palette.count());
            // Appelle une méthode
            assertEquals(5, palette.get(0, 0, 0));
            // Appelle une méthode
            palette.fill(6);
            // Appelle une méthode
            assertEquals(6, palette.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            assertAllEquals(6, palette);

            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            assertEquals(0, palette.count());
            // Appelle une méthode
            assertAllEquals(0, palette);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offset() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            palette.offset(1);
            // Appelle une méthode
            assertAllEquals(1, palette);

            // Appelle une méthode
            palette.fill(1);
            // Appelle une méthode
            palette.set(0, 0, 1, 2);
            // Appelle une méthode
            palette.offset(-1);
            // Boucle : répète un bloc
            for (int x = 0; x < palette.dimension(); x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < palette.dimension(); y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < palette.dimension(); z++) {
                        // Embranchement : vérifie une condition
                        if (x == 0 && y == 0 && z == 1) {
                            // Appelle une méthode
                            assertEquals(1, palette.get(x, y, z));
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            assertEquals(0, palette.get(x, y, z));
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            palette.offset(50);
            // Début d'une méthode/d'un bloc
            palette.getAll((x, y, z, value) -> {
                // Affecte une valeur
                int expected = x + y + z + 100 + 50;
                // Appelle une méthode
                assertEquals(expected, value);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Appelle une méthode
            palette.set(0, 1, 0, 2);
            // Appelle une méthode
            palette.set(1, 0, 0, 3);
            // Appelle une méthode
            palette.offset(50);
            // Début d'une méthode/d'un bloc
            palette.getAll((x, y, z, value) -> {
                // Embranchement : vérifie une condition
                if (x == 0 && y == 0 && z == 1) {
                    // Appelle une méthode
                    assertEquals(51, value);
                // Embranchement : vérifie une condition
                } else if (x == 0 && y == 1 && z == 0) {
                    // Appelle une méthode
                    assertEquals(52, value);
                // Embranchement : vérifie une condition
                } else if (x == 1 && y == 0 && z == 0) {
                    // Appelle une méthode
                    assertEquals(53, value);
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    assertEquals(50, value);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void offsetCount() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            assertEquals(0, palette.count());
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            assertEquals(0, palette.count());
            // Appelle une méthode
            palette.offset(1);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            palette.offset(-1);
            // Appelle une méthode
            assertEquals(0, palette.count());
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(1);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            palette.set(0, 0, 1, 2);
            // Appelle une méthode
            palette.set(0, 1, 0, 3);
            // Appelle une méthode
            palette.set(1, 0, 0, 4);
            // Appelle une méthode
            palette.offset(-1);
            // Appelle une méthode
            assertEquals(3, palette.count());
            // Appelle une méthode
            palette.offset(1);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            palette.offset(50);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            palette.offset(-50);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replace() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            palette.replace(0, 1);
            // Appelle une méthode
            assertAllEquals(1, palette);

            // Appelle une méthode
            palette.fill(1);
            // Appelle une méthode
            palette.set(0, 0, 1, 2);
            // Appelle une méthode
            palette.replace(2, 3);
            // Boucle : répète un bloc
            for (int x = 0; x < palette.dimension(); x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < palette.dimension(); y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < palette.dimension(); z++) {
                        // Embranchement : vérifie une condition
                        if (x == 0 && y == 0 && z == 1) {
                            // Appelle une méthode
                            assertEquals(3, palette.get(x, y, z));
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            assertEquals(1, palette.get(x, y, z));
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Appelle une méthode
            palette.set(0, 1, 0, 2);
            // Appelle une méthode
            palette.set(1, 0, 0, 3);
            // Appelle une méthode
            palette.replace(0, 50);
            // Début d'une méthode/d'un bloc
            palette.getAll((x, y, z, value) -> {
                // Embranchement : vérifie une condition
                if (x == 0 && y == 0 && z == 1) {
                    // Appelle une méthode
                    assertEquals(1, value);
                // Embranchement : vérifie une condition
                } else if (x == 0 && y == 1 && z == 0) {
                    // Appelle une méthode
                    assertEquals(2, value);
                // Embranchement : vérifie une condition
                } else if (x == 1 && y == 0 && z == 0) {
                    // Appelle une méthode
                    assertEquals(3, value);
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    assertEquals(50, value);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceCount() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            palette.replace(0, 1);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            palette.replace(1, 0);
            // Appelle une méthode
            assertEquals(0, palette.count());
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Appelle une méthode
            palette.set(1, 1, 1, 1);
            // Appelle une méthode
            palette.set(0, 1, 0, 2);
            // Appelle une méthode
            palette.set(1, 0, 0, 3);
            // Appelle une méthode
            assertEquals(4, palette.count());
            // Appelle une méthode
            palette.replace(1, 0);
            // Appelle une méthode
            assertEquals(2, palette.count());
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            palette.replace(100, 0);
            // Appelle une méthode
            assertEquals(palette.maxSize() - 1, palette.count());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceWithExistingValue() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 0, 1);
            // Appelle une méthode
            palette.set(1, 0, 0, 2);
            // Appelle une méthode
            palette.set(0, 1, 0, 2);

            // Appelle une méthode
            palette.replace(1, 2);

            // Appelle une méthode
            assertEquals(2, palette.get(0, 0, 0));
            // Appelle une méthode
            assertEquals(2, palette.get(1, 0, 0));
            // Appelle une méthode
            assertEquals(2, palette.get(0, 1, 0));
            // Appelle une méthode
            assertEquals(3, palette.count(2));
            // Appelle une méthode
            assertEquals(0, palette.count(1));
            // Appelle une méthode
            assertFalse(palette.any(1));
            // Appelle une méthode
            assertTrue(palette.any(2));

            // Appelle une méthode
            palette.set(1, 1, 0, 1);
            // Appelle une méthode
            assertEquals(1, palette.get(1, 1, 0));
            // Appelle une méthode
            assertEquals(1, palette.count(1));
            // Appelle une méthode
            assertEquals(3, palette.count(2));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void countValue() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count(0));
            // Appelle une méthode
            assertEquals(0, palette.count(1));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count(0));
            // Appelle une méthode
            palette.replace(0, 1);
            // Appelle une méthode
            assertEquals(0, palette.count(0));
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count(1));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Appelle une méthode
            palette.set(1, 1, 1, 1);
            // Appelle une méthode
            palette.set(0, 1, 0, 2);
            // Appelle une méthode
            palette.set(1, 0, 0, 3);
            // Appelle une méthode
            assertEquals(palette.maxSize() - 4, palette.count(0));
            // Appelle une méthode
            assertEquals(2, palette.count(1));
            // Appelle une méthode
            assertEquals(1, palette.count(2));
            // Appelle une méthode
            assertEquals(1, palette.count(3));
            // Appelle une méthode
            assertEquals(0, palette.count(4));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            assertEquals(0, palette.count(0));
            // Appelle une méthode
            assertEquals(1, palette.count(100));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void anyValue() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Initially all zero
            // Appelle une méthode
            assertFalse(palette.any(1));
            // Appelle une méthode
            assertTrue(palette.any(0));
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Appelle une méthode
            assertTrue(palette.any(1));
            // Appelle une méthode
            assertTrue(palette.any(0));
            // Appelle une méthode
            palette.set(0, 0, 1, 0);
            // Appelle une méthode
            assertFalse(palette.any(1));
            // Appelle une méthode
            assertTrue(palette.any(0));
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Appelle une méthode
            palette.replace(0, 2);
            // Appelle une méthode
            assertTrue(palette.any(1));
            // Appelle une méthode
            assertFalse(palette.any(0));
            // Appelle une méthode
            assertTrue(palette.any(2));
            // Appelle une méthode
            palette.replace(1, 2);
            // Appelle une méthode
            assertFalse(palette.any(1));
            // Appelle une méthode
            assertTrue(palette.any(2));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(5);
            // Appelle une méthode
            assertTrue(palette.any(5));
            // Appelle une méthode
            assertFalse(palette.any(0));
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            assertFalse(palette.any(5));
            // Appelle une méthode
            assertTrue(palette.any(0));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> (x + y + z) % 3);
            // Appelle une méthode
            assertTrue(palette.any(0));
            // Appelle une méthode
            assertTrue(palette.any(1));
            // Appelle une méthode
            assertTrue(palette.any(2));
            // Appelle une méthode
            assertFalse(palette.any(3));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void countValueEdgeCases() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // All zero
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count(0));
            // Appelle une méthode
            assertEquals(0, palette.count(-1));
            // Appelle une méthode
            assertEquals(0, palette.count(Integer.MAX_VALUE));
            // Fill with negative value
            // Appelle une méthode
            palette.fill(-7);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count(-7));
            // Appelle une méthode
            assertEquals(0, palette.count(0));
            // Fill with max int
            // Appelle une méthode
            palette.fill(Integer.MAX_VALUE);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count(Integer.MAX_VALUE));
            // Appelle une méthode
            assertEquals(0, palette.count(0));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> (x == 0 && y == 0 && z == 0) ? 42 : 0);
            // Appelle une méthode
            assertEquals(1, palette.count(42));
            // Appelle une méthode
            assertEquals(palette.maxSize() - 1, palette.count(0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void bulk() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            final int dimension = palette.dimension();
            // Place
            // Boucle : répète un bloc
            for (int x = 0; x < dimension; x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < dimension; y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < dimension; z++) {
                        // Appelle une méthode
                        palette.set(x, y, z, x + y + z + 1);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Verify
            // Boucle : répète un bloc
            for (int x = 0; x < dimension; x++) {
                // Boucle : répète un bloc
                for (int y = 0; y < dimension; y++) {
                    // Boucle : répète un bloc
                    for (int z = 0; z < dimension; z++) {
                        // Appelle une méthode
                        assertEquals(x + y + z + 1, palette.get(x, y, z));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void bulkAll() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Fill all entries
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 1);
            // Instruction de code
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 1, value,
                    // Appelle une méthode
                    "x: " + x + ", y: " + y + ", z: " + z + ", dimension: " + palette.dimension()));

            // Replacing
            // Début d'une méthode/d'un bloc
            palette.replaceAll((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(x + y + z + 1, value);
                // Renvoie une valeur à l'appelant
                return x + y + z + 2;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 2, value));
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            assertEquals(100, palette.get(0, 0, 0));
            // Instruction de code
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 100, value,
                    // Appelle une méthode
                    "x: " + x + ", y: " + y + ", z: " + z + ", dimension: " + palette.dimension()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void bulkAllOrder() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            AtomicInteger count = new AtomicInteger();

            // Ensure that the lambda is called for every entry
            // even if the array is initialized
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> count.incrementAndGet());
            // Appelle une méthode
            assertEquals(count.get(), palette.maxSize());

            // Fill all entries
            // Appelle une méthode
            count.set(0);
            // Appelle une méthode
            Set<Point> points = new HashSet<>();
            // Début d'une méthode/d'un bloc
            palette.setAll((x, y, z) -> {
                // Appelle une méthode
                assertTrue(points.add(new Vec(x, y, z)), "Duplicate point: " + x + ", " + y + ", " + z + ", dimension " + palette.dimension());
                // Renvoie une valeur à l'appelant
                return count.incrementAndGet();
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            assertEquals(palette.count(), count.get());

            // Appelle une méthode
            count.set(0);
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(count.incrementAndGet(), value));
            // Appelle une méthode
            assertEquals(count.get(), palette.count());

            // Replacing
            // Appelle une méthode
            count.set(0);
            // Début d'une méthode/d'un bloc
            palette.replaceAll((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(count.incrementAndGet(), value);
                // Renvoie une valeur à l'appelant
                return count.get();
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(count.get(), palette.count());

            // Appelle une méthode
            count.set(0);
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(count.incrementAndGet(), value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setAllConstant() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> 1);
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(1, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setAllBig() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 100);
            // Appelle une méthode
            assertEquals(palette.maxSize(), palette.count());
            // Appelle une méthode
            assertEquals(100, palette.get(0, 0, 0));
            // Début d'une méthode/d'un bloc
            palette.getAll((x, y, z, value) -> {
                // Affecte une valeur
                int expected = x + y + z + 100;
                // Appelle une méthode
                assertEquals(expected, value);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void getAllEmpty() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(0, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void getAllPresent() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.getAllPresent((x, y, z, value) -> fail("The palette should be empty"));
            // Appelle une méthode
            palette.set(0, 0, 1, 1);
            // Début d'une méthode/d'un bloc
            palette.getAllPresent((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(0, x);
                // Appelle une méthode
                assertEquals(0, y);
                // Appelle une méthode
                assertEquals(1, z);
                // Appelle une méthode
                assertEquals(1, value);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void getAllPresentNonAirFill() {
        // Filling with a non-air value then editing a cell must still report every non-air cell.
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(5);
            // Appelle une méthode
            palette.set(0, 0, 0, 7);
            // Appelle une méthode
            AtomicInteger reported = new AtomicInteger();
            // Début d'une méthode/d'un bloc
            palette.getAllPresent((x, y, z, value) -> {
                // Appelle une méthode
                assertNotEquals(0, value, "air must never be reported as present");
                // Appelle une méthode
                assertEquals(x == 0 && y == 0 && z == 0 ? 7 : 5, value);
                // Appelle une méthode
                reported.incrementAndGet();
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(palette.maxSize(), reported.get());
            // Appelle une méthode
            assertEquals(palette.count(), reported.get(), "getAllPresent must agree with count()");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void getAllPresentNonAirFillThenAir() {
        // Carving a single air cell out of a non-air fill must exclude only that cell.
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(5);
            // Appelle une méthode
            palette.set(0, 0, 0, 0);
            // Appelle une méthode
            AtomicInteger reported = new AtomicInteger();
            // Début d'une méthode/d'un bloc
            palette.getAllPresent((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(5, value);
                // Appelle une méthode
                assertFalse(x == 0 && y == 0 && z == 0, "the air cell must be excluded");
                // Appelle une méthode
                reported.incrementAndGet();
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(palette.maxSize() - 1, reported.get());
            // Appelle une méthode
            assertEquals(palette.count(), reported.get(), "getAllPresent must agree with count()");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceAll() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.setAll((x, y, z) -> x + y + z + 1);
            // Début d'une méthode/d'un bloc
            palette.replaceAll((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(x + y + z + 1, value);
                // Renvoie une valeur à l'appelant
                return x + y + z + 2;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(x + y + z + 2, value));
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(0);
            // Début d'une méthode/d'un bloc
            palette.replaceAll((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(0, value);
                // Renvoie une valeur à l'appelant
                return value + 1;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(1, value));
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.fill(1);
            // Début d'une méthode/d'un bloc
            palette.replaceAll((x, y, z, value) -> {
                // Appelle une méthode
                assertEquals(1, value);
                // Renvoie une valeur à l'appelant
                return value + 1;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            palette.getAll((x, y, z, value) -> assertEquals(2, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceUnary() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            palette.set(0, 0, 0, 1);
            // Début d'une méthode/d'un bloc
            palette.replace(0, 0, 0, operand -> {
                // Appelle une méthode
                assertEquals(1, operand);
                // Renvoie une valeur à l'appelant
                return 2;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            assertEquals(2, palette.get(0, 0, 0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceLoop() {
        // Appelle une méthode
        var palette = Palette.sized(2, 1, 8, 15, 4);
        // Appelle une méthode
        palette.setAll((x, y, z) -> x + y + z);
        // Appelle une méthode
        final int dimension = palette.dimension();
        // Boucle : répète un bloc
        for (int x = 0; x < dimension; x++) {
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < dimension; z++) {
                    // Appelle une méthode
                    palette.replace(x, y, z, value -> value + 1);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void dimension() {
        // Appelle une méthode
        assertThrows(Exception.class, () -> Palette.empty(-4, 5, 3, 15));
        // Appelle une méthode
        assertThrows(Exception.class, () -> Palette.empty(0, 5, 3, 15));
        // Appelle une méthode
        assertThrows(Exception.class, () -> Palette.empty(1, 5, 3, 15));
        // Appelle une méthode
        assertDoesNotThrow(() -> Palette.empty(2, 5, 3, 15));
        // Appelle une méthode
        assertThrows(Exception.class, () -> Palette.empty(3, 5, 3, 15));
        // Appelle une méthode
        assertDoesNotThrow(() -> Palette.empty(4, 5, 3, 15));
        // Appelle une méthode
        assertThrows(Exception.class, () -> Palette.empty(6, 5, 3, 15));
        // Appelle une méthode
        assertDoesNotThrow(() -> Palette.empty(16, 5, 3, 15));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBlockEmpty() {
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Palette palette = Palette.blocks();
        // Appelle une méthode
        buffer.write(Palette.BLOCK_SERIALIZER, palette);

        // Appelle une méthode
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);
        // Appelle une méthode
        assertTrue(palette.compare(deserialized));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBlockPalette() {
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Palette palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 0, 0, 1);
        // Appelle une méthode
        palette.set(1, 0, 0, 2);
        // Appelle une méthode
        buffer.write(Palette.BLOCK_SERIALIZER, palette);

        // Appelle une méthode
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);
        // Appelle une méthode
        assertTrue(palette.compare(deserialized));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBlockLinearMutation() {
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Palette palette = Palette.blocks();
        // Appelle une méthode
        palette.set(0, 0, 0, 1);
        // Appelle une méthode
        palette.set(1, 0, 0, 2);

        // Appelle une méthode
        buffer.write(Palette.BLOCK_SERIALIZER, palette);
        // Appelle une méthode
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);

        // Appelle une méthode
        deserialized.set(2, 0, 0, 3);

        // Appelle une méthode
        assertEquals(1, deserialized.get(0, 0, 0));
        // Appelle une méthode
        assertEquals(2, deserialized.get(1, 0, 0));
        // Appelle une méthode
        assertEquals(3, deserialized.get(2, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBlockDirect() {
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Random random = new Random(12345);
        // Appelle une méthode
        Palette palette = Palette.blocks();
        // Appelle une méthode
        palette.setAll((x, y, z) -> random.nextInt(2048));

        // Appelle une méthode
        buffer.write(Palette.BLOCK_SERIALIZER, palette);

        // Appelle une méthode
        Palette deserialized = buffer.read(Palette.BLOCK_SERIALIZER);
        // Appelle une méthode
        assertTrue(palette.compare(deserialized));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBiomeEmpty() {
        // Appelle une méthode
        final var serializer = Palette.biomeSerializer(128);
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Palette palette = Palette.biomes();
        // Appelle une méthode
        buffer.write(serializer, palette);

        // Appelle une méthode
        Palette deserialized = buffer.read(serializer);
        // Appelle une méthode
        assertTrue(palette.compare(deserialized));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBiomePalette() {
        // Appelle une méthode
        final var serializer = Palette.biomeSerializer(128);
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Palette palette = Palette.biomes();
        // Appelle une méthode
        palette.set(0, 0, 0, 1);
        // Appelle une méthode
        palette.set(1, 0, 0, 2);
        // Appelle une méthode
        buffer.write(serializer, palette);

        // Appelle une méthode
        Palette deserialized = buffer.read(serializer);
        // Appelle une méthode
        assertTrue(palette.compare(deserialized));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serializationBiomeDirect() {
        // Appelle une méthode
        final var serializer = Palette.biomeSerializer(128);
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        Palette palette = Palette.biomes();
        // Appelle une méthode
        Random random = new Random(12345);
        // Appelle une méthode
        palette.setAll((x, y, z) -> random.nextInt(2048));

        // Appelle une méthode
        buffer.write(serializer, palette);

        // Appelle une méthode
        Palette deserialized = buffer.read(serializer);
        // Appelle une méthode
        assertTrue(palette.compare(deserialized));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadBelowMinBitsPerEntry() {
        // Test loading with bpe below minBitsPerEntry - should resize to minBitsPerEntry
        // Affecte une valeur
        Palette palette = Palette.sized(4, 4, 8, 15, 4); // min=4, max=8, direct=15

        // Affecte une valeur
        int[] paletteData = {0, 1, 2, 3}; // 4 values need 2 bits, but min is 4
        // Affecte une valeur
        long[] values = new long[]{0x3210L}; // packed with 2 bits per entry

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should be resized to minBitsPerEntry (4)
        // Appelle une méthode
        assertEquals(4, palette.bitsPerEntry());

        // Values should still be accessible correctly
        // Appelle une méthode
        assertEquals(0, palette.get(0, 0, 0));
        // Appelle une méthode
        assertEquals(1, palette.get(1, 0, 0));
        // Appelle une méthode
        assertEquals(2, palette.get(2, 0, 0));
        // Appelle une méthode
        assertEquals(3, palette.get(3, 0, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadAboveMaxBitsPerEntry() {
        // Test loading with bpe above maxBitsPerEntry - should become direct palette
        // Affecte une valeur
        Palette palette = Palette.sized(4, 1, 3, 15, 1); // min=1, max=3, direct=15

        // Create palette that would need more than 3 bits (max) - 16 values need 4 bits
        // Affecte une valeur
        int[] paletteData = new int[16];
        // Boucle : répète un bloc
        for (int i = 0; i < 16; i++) {
            // Affecte une valeur
            paletteData[i] = i + 100; // arbitrary values
        // Fin d'un bloc/d'une expression
        }

        // Create values array with 4 bits per entry
        // Affecte une valeur
        long[] values = new long[4]; // 64 entries, 4 bits each = 16 longs per entry, 4 longs total
        // Boucle : répète un bloc
        for (int i = 0; i < 64; i++) {
            // Affecte une valeur
            int longIndex = i / 16;
            // Appelle une méthode
            int bitIndex = (i % 16) * 4;
            // Appelle une méthode
            values[longIndex] |= ((long) (i % 16)) << bitIndex;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should become direct palette (directBits = 15)
        // Appelle une méthode
        assertEquals(15, palette.bitsPerEntry());

        // Should not have a palette anymore (direct mode)
        // Appelle une méthode
        assertNull(((PaletteImpl) palette).paletteToValueList);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadWithinRange() {
        // Test loading with bpe within min-max range - should use calculated bpe
        // Affecte une valeur
        Palette palette = Palette.sized(4, 2, 6, 15, 2); // min=2, max=6, direct=15

        // Affecte une valeur
        int[] paletteData = {0, 10, 20, 30, 40}; // 5 values need 3 bits
        // Affecte une valeur
        long[] values = new long[12]; // 64 entries, 3 bits each

        // Fill with some test pattern
        // Boucle : répète un bloc
        for (int i = 0; i < 64; i++) {
            // Affecte une valeur
            int longIndex = i / 21; // 21 values per long with 3 bits each (63 bits used)
            // Appelle une méthode
            int bitIndex = (i % 21) * 3;
            // Embranchement : vérifie une condition
            if (longIndex < values.length) {
                // Appelle une méthode
                values[longIndex] |= ((long) (i % 5)) << bitIndex;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should use 3 bits (calculated from palette size)
        // Appelle une méthode
        assertEquals(3, palette.bitsPerEntry());

        // Should have palette
        // Appelle une méthode
        assertNotNull(((PaletteImpl) palette).paletteToValueList);

        // Verify palette contents
        // Appelle une méthode
        assertEquals(5, ((PaletteImpl) palette).paletteToValueList.size());
        // Appelle une méthode
        assertEquals(0, ((PaletteImpl) palette).paletteToValueList.getInt(0));
        // Appelle une méthode
        assertEquals(10, ((PaletteImpl) palette).paletteToValueList.getInt(1));
        // Appelle une méthode
        assertEquals(20, ((PaletteImpl) palette).paletteToValueList.getInt(2));
        // Appelle une méthode
        assertEquals(30, ((PaletteImpl) palette).paletteToValueList.getInt(3));
        // Appelle une méthode
        assertEquals(40, ((PaletteImpl) palette).paletteToValueList.getInt(4));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadExactlyMinBitsPerEntry() {
        // Test loading where calculated bpe equals minBitsPerEntry
        // Affecte une valeur
        Palette palette = Palette.sized(4, 3, 8, 15, 3); // min=3, max=8, direct=15

        // Affecte une valeur
        int[] paletteData = {0, 1, 2, 3, 4, 5, 6, 7}; // 8 values need exactly 3 bits
        // Affecte une valeur
        long[] values = new long[12]; // 64 entries, 3 bits each

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should use exactly minBitsPerEntry (3)
        // Appelle une méthode
        assertEquals(3, palette.bitsPerEntry());

        // Should have palette
        // Appelle une méthode
        assertNotNull(((PaletteImpl) palette).paletteToValueList);
        // Appelle une méthode
        assertEquals(8, ((PaletteImpl) palette).paletteToValueList.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadExactlyMaxBitsPerEntry() {
        // Test loading where calculated bpe equals maxBitsPerEntry
        // Affecte une valeur
        Palette palette = Palette.sized(4, 2, 4, 15, 2); // min=2, max=4, direct=15

        // Affecte une valeur
        int[] paletteData = new int[16]; // 16 values need exactly 4 bits
        // Boucle : répète un bloc
        for (int i = 0; i < 16; i++) {
            // Affecte une valeur
            paletteData[i] = i * 10;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        long[] values = new long[16]; // 64 entries, 4 bits each

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should use exactly maxBitsPerEntry (4)
        // Appelle une méthode
        assertEquals(4, palette.bitsPerEntry());

        // Should still have palette (not direct)
        // Appelle une méthode
        assertNotNull(((PaletteImpl) palette).paletteToValueList);
        // Appelle une méthode
        assertEquals(16, ((PaletteImpl) palette).paletteToValueList.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadEmptyPalette() {
        // Test loading with empty palette
        // Appelle une méthode
        Palette palette = Palette.sized(4, 1, 8, 15, 1);

        // Affecte une valeur
        int[] paletteData = {0}; // Single value palette
        // Affecte une valeur
        long[] values = new long[4]; // All zeros

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should use minBitsPerEntry since 1 value needs 0 bits but min is 1
        // Appelle une méthode
        assertEquals(1, palette.bitsPerEntry());

        // Should have palette with single entry
        // Appelle une méthode
        assertNotNull(((PaletteImpl) palette).paletteToValueList);
        // Appelle une méthode
        assertEquals(1, ((PaletteImpl) palette).paletteToValueList.size());
        // Appelle une méthode
        assertEquals(0, ((PaletteImpl) palette).paletteToValueList.getInt(0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadValuesCloned() {
        // Test that values array is properly cloned
        // Appelle une méthode
        Palette palette = Palette.sized(4, 2, 6, 15, 2);

        // Affecte une valeur
        int[] paletteData = {0, 1, 2};
        // Affecte une valeur
        long[] originalValues = {0x123456789ABCDEFL, 0xFEDCBA9876543210L};

        // Appelle une méthode
        palette.load(paletteData, originalValues);

        // Modify original array
        // Affecte une valeur
        originalValues[0] = 0L;
        // Affecte une valeur
        originalValues[1] = 0L;

        // Palette should still have the original values
        // Appelle une méthode
        long[] paletteValues = palette.indexedValues();
        // Appelle une méthode
        assertNotNull(paletteValues);
        // Appelle une méthode
        assertEquals(0x123456789ABCDEFL, paletteValues[0]);
        // Appelle une méthode
        assertEquals(0xFEDCBA9876543210L, paletteValues[1]);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadThousandsOfIndicesBecomesDirectPalette() {
        // Test loading with thousands of indices to ensure it becomes a direct palette
        // Affecte une valeur
        Palette palette = Palette.blocks(); // min=4, max=8, direct=15

        // Create palette with thousands of unique values (way more than max palette size of 2^8=256)
        // Affecte une valeur
        final int uniqueValueCount = 5000;
        // Affecte une valeur
        int[] paletteData = new int[uniqueValueCount];
        // Boucle : répète un bloc
        for (int i = 0; i < uniqueValueCount; i++) {
            // Affecte une valeur
            paletteData[i] = i + 1000; // Use offset to avoid zero values
        // Fin d'un bloc/d'une expression
        }

        // Calculate bits needed: log2(5000) ≈ 13 bits, which exceeds maxBitsPerEntry (8)
        // This should force direct palette mode
        // Affecte une valeur
        int calculatedBits = 13; // Math.ceil(Math.log(uniqueValueCount) / Math.log(2))

        // Create values array for 4096 entries (16x16x16) with calculated bits per entry
        // Affecte une valeur
        final int totalEntries = 16 * 16 * 16; // 4096 entries
        // Affecte une valeur
        final int valuesPerLong = 64 / calculatedBits;
        // Appelle une méthode
        final int valuesArrayLength = (totalEntries + valuesPerLong - 1) / valuesPerLong;
        // Affecte une valeur
        long[] values = new long[valuesArrayLength];

        // Fill with pattern using modulo to cycle through available palette indices
        // Appelle une méthode
        final long mask = (1L << calculatedBits) - 1;
        // Boucle : répète un bloc
        for (int i = 0; i < totalEntries; i++) {
            // Affecte une valeur
            int paletteIndex = i % uniqueValueCount;
            // Affecte une valeur
            int longIndex = i / valuesPerLong;
            // Appelle une méthode
            int bitIndex = (i % valuesPerLong) * calculatedBits;
            // Appelle une méthode
            values[longIndex] |= ((long) paletteIndex & mask) << bitIndex;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        palette.load(paletteData, values);

        // Should become direct palette since uniqueValueCount >> 2^maxBitsPerEntry
        // Instruction de code
        assertEquals(Palette.BLOCK_PALETTE_DIRECT_BITS, palette.bitsPerEntry(),
                // Instruction de code
                "Palette should use direct bits when loaded with thousands of indices");

        // Should not have indirect palette structures (direct mode)
        // Appelle une méthode
        PaletteImpl impl = (PaletteImpl) palette;
        // Instruction de code
        assertNull(impl.paletteToValueList,
                // Instruction de code
                "Direct palette should not have paletteToValueList");

        // Verify we can still read some values correctly
        // In direct mode, palette indices become the actual values
        // Appelle une méthode
        int firstValue = palette.get(0, 0, 0);
        // Instruction de code
        assertTrue(firstValue >= 1000 && firstValue < 1000 + uniqueValueCount,
                // Instruction de code
                "Value should be within expected range for direct palette: " + firstValue);

        // Verify the palette has proper count (non-zero blocks)
        // Appelle une méthode
        assertTrue(palette.count() > 0, "Palette should have non-zero count");
        // Appelle une méthode
        assertTrue(palette.count() <= palette.maxSize(), "Count should not exceed max size");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void height() {
        // Boucle : répète un bloc
        for (Palette palette : testPalettes()) {
            // Appelle une méthode
            final int dimension = palette.dimension();

            // Test with empty palette - predicate that always returns true should find the
            // top
            // Appelle une méthode
            assertEquals(dimension - 1, palette.height(0, 0, (x, y, z, value) -> true));
            // Predicate that always returns false should return -1
            // Appelle une méthode
            assertEquals(-1, palette.height(0, 0, (x, y, z, value) -> false));

            // Set a block at the top
            // Appelle une méthode
            palette.set(0, dimension - 1, 0, 1);
            // Appelle une méthode
            assertEquals(dimension - 1, palette.height(0, 0, (x, y, z, value) -> value != 0));

            // Set a block in the middle
            // Embranchement : vérifie une condition
            if (dimension > 1) {
                // Appelle une méthode
                palette.set(1, dimension / 2, 1, 2);
                // Appelle une méthode
                assertEquals(dimension / 2, palette.height(1, 1, (x, y, z, value) -> value != 0));
            // Fin d'un bloc/d'une expression
            }

            // Set blocks at multiple heights - should return the highest one
            // Embranchement : vérifie une condition
            if (dimension > 2) {
                // Appelle une méthode
                palette.set(2, 1, 2, 3);
                // Appelle une méthode
                palette.set(2, dimension - 2, 2, 4);
                // Appelle une méthode
                assertEquals(dimension - 2, palette.height(2, 2, (x, y, z, value) -> value != 0));
            // Fin d'un bloc/d'une expression
            }

            // Test with predicate that matches air (value 0)
            // Instruction de code
            palette.fill(5); // Fill with non-zero value
            // Appelle une méthode
            int testX = Math.min(1, dimension - 1);
            // Appelle une méthode
            int testZ = Math.min(1, dimension - 1);
            // Instruction de code
            palette.set(testX, dimension / 2, testZ, 0); // Set one block to air
            // Appelle une méthode
            assertEquals(dimension / 2, palette.height(testX, testZ, (x, y, z, value) -> value == 0));

            // Test edge cases - coordinates at boundaries
            // Appelle une méthode
            palette.fill(0);
            // Appelle une méthode
            palette.set(dimension - 1, dimension - 1, dimension - 1, 10);
            // Appelle une méthode
            assertEquals(dimension - 1, palette.height(dimension - 1, dimension - 1, (x, y, z, value) -> value != 0));

            // Test with complex predicate
            // Appelle une méthode
            palette.fill(0);
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Appelle une méthode
                palette.set(0, y, 0, y + 1);
            // Fin d'un bloc/d'une expression
            }
            // Find highest block with value > 5
            // Affecte une valeur
            int expectedHeight = -1;
            // Boucle : répète un bloc
            for (int y = dimension - 1; y >= 0; y--) {
                // Embranchement : vérifie une condition
                if (y + 1 > 5) {
                    // Affecte une valeur
                    expectedHeight = y;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            assertEquals(expectedHeight, palette.height(0, 0, (x, y, z, value) -> value > 5));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heightValidation() {
        // Appelle une méthode
        Palette palette = Palette.blocks();
        // Appelle une méthode
        final int dimension = palette.dimension();

        // Test invalid coordinates
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> palette.height(-1, 0, (x, y, z, value) -> true));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> palette.height(0, -1, (x, y, z, value) -> true));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> palette.height(dimension, 0, (x, y, z, value) -> true));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> palette.height(0, dimension, (x, y, z, value) -> true));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heightOptimization() {
        // Test single-value palette optimization
        // Appelle une méthode
        Palette singleValuePalette = Palette.blocks();
        // Appelle une méthode
        singleValuePalette.fill(42);
        
        // Should find the value at the top
        // Appelle une méthode
        assertEquals(15, singleValuePalette.height(0, 0, (x, y, z, value) -> value == 42));
        // Appelle une méthode
        assertEquals(-1, singleValuePalette.height(0, 0, (x, y, z, value) -> value == 0));
        
        // Test multi-value palette optimization
        // Appelle une méthode
        Palette multiValuePalette = Palette.blocks();
        // Appelle une méthode
        multiValuePalette.set(5, 10, 5, 100);
        // Appelle une méthode
        multiValuePalette.set(5, 8, 5, 200);
        // Appelle une méthode
        multiValuePalette.set(5, 12, 5, 300);
        
        // Should find the highest matching block
        // Appelle une méthode
        assertEquals(12, multiValuePalette.height(5, 5, (x, y, z, value) -> value != 0));
        // Appelle une méthode
        assertEquals(10, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 100));
        // Appelle une méthode
        assertEquals(8, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 200));
        // Appelle une méthode
        assertEquals(12, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 300));
        // Appelle une méthode
        assertEquals(-1, multiValuePalette.height(5, 5, (x, y, z, value) -> value == 999));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void count() {
        // Appelle une méthode
        Palette testPalette = Palette.blocks();
        // Appelle une méthode
        testPalette.fill(5000);
        // Appelle une méthode
        assertEquals(4096, testPalette.count());

        // Should correctly count
        // Appelle une méthode
        testPalette.set(0, 0, 0, 0);
        // Appelle une méthode
        testPalette.set(0, 0, 1, 1);
        // Appelle une méthode
        testPalette.set(0, 0, 2, 2);
        // Appelle une méthode
        testPalette.set(0, 0, 3, 3);
        // Appelle une méthode
        assertEquals(4095, testPalette.count());

        // Appelle une méthode
        testPalette.set(0, 0, 0, 5000);
        // Appelle une méthode
        assertEquals(4096, testPalette.count());

        // Appelle une méthode
        testPalette.replace(5000, 0);
        // Appelle une méthode
        assertEquals(3, testPalette.count());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void loadCount() {
        // Appelle une méthode
        Palette testPalette = Palette.empty(4, 4, 8, 12);
        // Affecte une valeur
        int[] palette = new int[] { 10, 2, 4, 0 };
        // 12 palette values that lead to 0 and 6 zeroed palette values
        // Affecte une valeur
        long[] values = new long[] { 0x01230123, 0x00130013, 0x33333333, 0x22222222 };
        // Appelle une méthode
        testPalette.load(palette, values);
        // Appelle une méthode
        assertEquals(testPalette.maxSize() - 12, testPalette.count());
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
