// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class PosViewDirectionTest {
    // Affecte une valeur
    private static final float EPSILON = 0.01f;

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void withLookAtPos() {
        // Appelle une méthode
        Pos initialPosition = new Pos(0, 40, 0);
        // Instruction de code
        Pos position;

        // look at itself, direction should not change
        // Appelle une méthode
        position = initialPosition.withLookAt(initialPosition);
        // Appelle une méthode
        assertEquals(initialPosition.yaw(), position.yaw());
        // Appelle une méthode
        assertEquals(initialPosition.pitch(), position.pitch());

        // Appelle une méthode
        position = initialPosition.withLookAt(new Pos(16, 40, 16));
        // Appelle une méthode
        assertEquals(-45f, position.yaw());
        // Appelle une méthode
        assertEquals(0f, position.pitch(), EPSILON);

        // Appelle une méthode
        position = initialPosition.withLookAt(new Pos(-16, 40, 56));
        // Appelle une méthode
        assertEquals(15.94f, position.yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(0f, position.pitch(), EPSILON);

        // Appelle une méthode
        position = initialPosition.withLookAt(new Pos(48, 36, 48));
        // Appelle une méthode
        assertEquals(-45f, position.yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(4.76f, position.pitch(), EPSILON);

        // Appelle une méthode
        position = initialPosition.withLookAt(new Pos(48, 36, -17));
        // Appelle une méthode
        assertEquals(-109.50f, position.yaw(), EPSILON);
        // should have the same pitch as the previous position
        // Appelle une méthode
        assertEquals(4.76f, position.pitch(), EPSILON);

        // Appelle une méthode
        position = initialPosition.withLookAt(new Pos(0, 87, 0));
        // looking from below, not checking the yaw
        // Appelle une méthode
        assertEquals(-90f, position.pitch(), EPSILON);

        // Appelle une méthode
        position = initialPosition.withLookAt(new Pos(-25, 42, 4));
        // Appelle une méthode
        assertEquals(80.90f, position.yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(-4.57f, position.pitch(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Testing {@link Pos#facing()}
     */
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void facingTest() {
        // Appelle une méthode
        Pos pos = new Pos(0, 0, 0);

        // Appelle une méthode
        assertEquals(Direction.SOUTH, pos.facing());

        // Appelle une méthode
        assertEquals(Direction.NORTH, pos.withYaw(180 + 360).facing());

        // Appelle une méthode
        assertEquals(Direction.EAST, pos.withYaw(-90).facing());

        // Appelle une méthode
        assertEquals(Direction.WEST, pos.withYaw(90).facing());

        // Appelle une méthode
        assertEquals(Direction.DOWN, pos.withYaw(543210).withPitch(53).facing());

        // Appelle une méthode
        assertEquals(Direction.UP, pos.withYaw(123456).withPitch(-90).facing());

        // edges
        // Appelle une méthode
        assertEquals(Direction.SOUTH, pos.withYaw(45).facing());

        // Appelle une méthode
        assertEquals(Direction.NORTH, pos.withYaw(-135).facing());

        // Appelle une méthode
        assertEquals(Direction.EAST, pos.withYaw(-45).facing());

        // Appelle une méthode
        assertEquals(Direction.WEST, pos.withYaw(135).facing());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
