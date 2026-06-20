// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Random;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class PaletteOptimizationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var palette = createPalette();
        // Appelle une méthode
        paletteEqualsOptimized(palette);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void single() {
        // Appelle une méthode
        var palette = createPalette();
        // Appelle une méthode
        palette.set(0, 0, 0, 1);
        // Appelle une méthode
        paletteEqualsOptimized(palette);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void random() {
        // Appelle une méthode
        var random = new Random(12345);
        // Appelle une méthode
        var palette = createPalette();
        // Appelle une méthode
        palette.setAll((x, y, z) -> random.nextInt(256));
        // Appelle une méthode
        paletteEqualsOptimized(palette);
        // Appelle une méthode
        palette.setAll((x, y, z) -> random.nextInt(2));
        // Appelle une méthode
        paletteEqualsOptimized(palette);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void manualFill() {
        // Appelle une méthode
        var palette = createPalette();
        // Appelle une méthode
        palette.setAll((x, y, z) -> 1);
        // Appelle une méthode
        paletteEqualsOptimized(palette);
        // Appelle une méthode
        palette.setAll((x, y, z) -> 2);
        // Appelle une méthode
        paletteEqualsOptimized(palette);
        // Appelle une méthode
        palette.setAll((x, y, z) -> 0);
        // Appelle une méthode
        paletteEqualsOptimized(palette);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    PaletteImpl createPalette() {
        // Renvoie une valeur à l'appelant
        return (PaletteImpl) Palette.blocks();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    Palette optimized(Palette palette, Palette.Optimization optimization) {
        // Appelle une méthode
        palette = palette.clone();
        // Appelle une méthode
        palette.optimize(optimization);
        // Renvoie une valeur à l'appelant
        return palette;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void paletteEqualsOptimized(Palette palette) {
        // Appelle une méthode
        paletteEquals(palette, optimized(palette, Palette.Optimization.SIZE), true);
        // Appelle une méthode
        paletteEquals(palette, optimized(palette, Palette.Optimization.SPEED), false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void paletteEquals(Palette palette, Palette optimized, boolean sizeCompare) {
        // Appelle une méthode
        assertTrue(palette.compare(optimized));
        // Embranchement : vérifie une condition
        if (sizeCompare) {
            // Appelle une méthode
            var array = NetworkBuffer.makeArray(Palette.BLOCK_SERIALIZER, palette);
            // Affecte une valeur
            int length1 = array.length;
            // Appelle une méthode
            array = NetworkBuffer.makeArray(Palette.BLOCK_SERIALIZER, optimized);
            // Affecte une valeur
            int length2 = array.length;
            // Appelle une méthode
            assertTrue(length1 >= length2, "Optimized palette is bigger than the original one: " + length1 + " : " + length2);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
