// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.utils.position.PositionUtils;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class PositionUtilsTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void yaw() {
        // Calls a method
        float plusX = PositionUtils.getLookYaw(10, 0);
        // Calls a method
        assertEquals(-90, plusX, 1E-5);

        // Calls a method
        float plusZ = PositionUtils.getLookYaw(0, 10);
        // Calls a method
        assertEquals(0, plusZ, 1E-5);

        // Calls a method
        float minusX = PositionUtils.getLookYaw(-10, 0);
        // Calls a method
        assertEquals(90, minusX, 1E-5);

        // Calls a method
        float minusZNegative = PositionUtils.getLookYaw(1E-5, -10);
        // Branch: checks a condition
        if (minusZNegative < -180) fail();
        // Calls a method
        assertEquals(-180, minusZNegative, 1E-4);

        // Calls a method
        float minusZPositive = PositionUtils.getLookYaw(-1E-5, -10);
        // Branch: checks a condition
        if (minusZPositive > 180) fail();
        // Calls a method
        assertEquals(180, minusZPositive, 1E-4);

        // Calls a method
        float oneThreeFive = PositionUtils.getLookYaw(-5, -5);
        // Calls a method
        assertEquals(135, oneThreeFive, 1E-5);

        // Calls a method
        float fortyFive = PositionUtils.getLookYaw(5, 5);
        // Calls a method
        assertEquals(-45, fortyFive, 1E-5);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void highPitch() {
        // Calls a method
        float high = PositionUtils.getLookPitch(0, 999999, 0);
        // Calls a method
        assertEquals(-90, high, 1E-5);

        // Calls a method
        float low = PositionUtils.getLookPitch(0, -999999, 0);
        // Calls a method
        assertEquals(90, low, 1E-5);

        // Calls a method
        float zero = PositionUtils.getLookPitch(-5, 0, 5);
        // Calls a method
        assertEquals(0, zero, 1E-5);

        // Calls a method
        float fortyFive = PositionUtils.getLookPitch(5, 5, 0);
        // Calls a method
        assertEquals(-45, fortyFive, 1E-5);
    // End of a block/expression
    }

// End of a block/expression
}
