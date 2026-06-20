// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
final class PaletteAssertions {
    // Début d'une méthode/d'un bloc
    private PaletteAssertions() {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static List<Palette> testPalettes() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Palette.sized(2, 1, 5, 15, 3),
                // Instruction de code
                Palette.sized(4, 1, 5, 15, 3),
                // Instruction de code
                Palette.sized(8, 1, 5, 15, 3),
                // Instruction de code
                Palette.sized(16, 1, 5, 15, 3),
                // Instruction de code
                Palette.blocks()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void assertAllEquals(int expected, Palette palette) {
        // Appelle une méthode
        final int dim = palette.dimension();
        // Boucle : répète un bloc
        for (int y = 0; y < dim; y++) {
            // Boucle : répète un bloc
            for (int z = 0; z < dim; z++) {
                // Boucle : répète un bloc
                for (int x = 0; x < dim; x++) {
                    // Instruction de code
                    assertEquals(expected, palette.get(x, y, z),
                            // Appelle une méthode
                            "Mismatch at (" + x + "," + y + "," + z + ")");
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
