// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.utils.position.PositionUtils;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class PositionUtilsTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void yaw() {
        // Appelle une méthode
        float plusX = PositionUtils.getLookYaw(10, 0);
        // Appelle une méthode
        assertEquals(-90, plusX, 1E-5);

        // Appelle une méthode
        float plusZ = PositionUtils.getLookYaw(0, 10);
        // Appelle une méthode
        assertEquals(0, plusZ, 1E-5);

        // Appelle une méthode
        float minusX = PositionUtils.getLookYaw(-10, 0);
        // Appelle une méthode
        assertEquals(90, minusX, 1E-5);

        // Appelle une méthode
        float minusZNegative = PositionUtils.getLookYaw(1E-5, -10);
        // Embranchement : vérifie une condition
        if (minusZNegative < -180) fail();
        // Appelle une méthode
        assertEquals(-180, minusZNegative, 1E-4);

        // Appelle une méthode
        float minusZPositive = PositionUtils.getLookYaw(-1E-5, -10);
        // Embranchement : vérifie une condition
        if (minusZPositive > 180) fail();
        // Appelle une méthode
        assertEquals(180, minusZPositive, 1E-4);

        // Appelle une méthode
        float oneThreeFive = PositionUtils.getLookYaw(-5, -5);
        // Appelle une méthode
        assertEquals(135, oneThreeFive, 1E-5);

        // Appelle une méthode
        float fortyFive = PositionUtils.getLookYaw(5, 5);
        // Appelle une méthode
        assertEquals(-45, fortyFive, 1E-5);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void highPitch() {
        // Appelle une méthode
        float high = PositionUtils.getLookPitch(0, 999999, 0);
        // Appelle une méthode
        assertEquals(-90, high, 1E-5);

        // Appelle une méthode
        float low = PositionUtils.getLookPitch(0, -999999, 0);
        // Appelle une méthode
        assertEquals(90, low, 1E-5);

        // Appelle une méthode
        float zero = PositionUtils.getLookPitch(-5, 0, 5);
        // Appelle une méthode
        assertEquals(0, zero, 1E-5);

        // Appelle une méthode
        float fortyFive = PositionUtils.getLookPitch(5, 5, 0);
        // Appelle une méthode
        assertEquals(-45, fortyFive, 1E-5);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
