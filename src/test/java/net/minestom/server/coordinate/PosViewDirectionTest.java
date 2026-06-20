// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class PosViewDirectionTest {
    // Assigns a value
    private static final float EPSILON = 0.01f;

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void withLookAtPos() {
        // Calls a method
        Pos initialPosition = new Pos(0, 40, 0);
        // Code statement
        Pos position;

        // look at itself, direction should not change
        // Calls a method
        position = initialPosition.withLookAt(initialPosition);
        // Calls a method
        assertEquals(initialPosition.yaw(), position.yaw());
        // Calls a method
        assertEquals(initialPosition.pitch(), position.pitch());

        // Calls a method
        position = initialPosition.withLookAt(new Pos(16, 40, 16));
        // Calls a method
        assertEquals(-45f, position.yaw());
        // Calls a method
        assertEquals(0f, position.pitch(), EPSILON);

        // Calls a method
        position = initialPosition.withLookAt(new Pos(-16, 40, 56));
        // Calls a method
        assertEquals(15.94f, position.yaw(), EPSILON);
        // Calls a method
        assertEquals(0f, position.pitch(), EPSILON);

        // Calls a method
        position = initialPosition.withLookAt(new Pos(48, 36, 48));
        // Calls a method
        assertEquals(-45f, position.yaw(), EPSILON);
        // Calls a method
        assertEquals(4.76f, position.pitch(), EPSILON);

        // Calls a method
        position = initialPosition.withLookAt(new Pos(48, 36, -17));
        // Calls a method
        assertEquals(-109.50f, position.yaw(), EPSILON);
        // should have the same pitch as the previous position
        // Calls a method
        assertEquals(4.76f, position.pitch(), EPSILON);

        // Calls a method
        position = initialPosition.withLookAt(new Pos(0, 87, 0));
        // looking from below, not checking the yaw
        // Calls a method
        assertEquals(-90f, position.pitch(), EPSILON);

        // Calls a method
        position = initialPosition.withLookAt(new Pos(-25, 42, 4));
        // Calls a method
        assertEquals(80.90f, position.yaw(), EPSILON);
        // Calls a method
        assertEquals(-4.57f, position.pitch(), EPSILON);
    // End of a block/expression
    }

    /**
     * Testing {@link Pos#facing()}
     */
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void facingTest() {
        // Calls a method
        Pos pos = new Pos(0, 0, 0);

        // Calls a method
        assertEquals(Direction.SOUTH, pos.facing());

        // Calls a method
        assertEquals(Direction.NORTH, pos.withYaw(180 + 360).facing());

        // Calls a method
        assertEquals(Direction.EAST, pos.withYaw(-90).facing());

        // Calls a method
        assertEquals(Direction.WEST, pos.withYaw(90).facing());

        // Calls a method
        assertEquals(Direction.DOWN, pos.withYaw(543210).withPitch(53).facing());

        // Calls a method
        assertEquals(Direction.UP, pos.withYaw(123456).withPitch(-90).facing());

        // edges
        // Calls a method
        assertEquals(Direction.SOUTH, pos.withYaw(45).facing());

        // Calls a method
        assertEquals(Direction.NORTH, pos.withYaw(-135).facing());

        // Calls a method
        assertEquals(Direction.EAST, pos.withYaw(-45).facing());

        // Calls a method
        assertEquals(Direction.WEST, pos.withYaw(135).facing());
    // End of a block/expression
    }
// End of a block/expression
}
