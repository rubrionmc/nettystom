// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.coordinate.Point;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public final class TestUtils {
    // Start of a method/block
    public static void waitUntilCleared(WeakReference<?> ref) {
        // Assigns a value
        final int maxTries = 100;

        // Loop: repeats a block
        for (int i = 0; i < maxTries; i++) {
            // Calls a method
            System.gc();
            // Branch: checks a condition
            if (ref.get() == null) {
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Exception handling
            try {
                // Calls a method
                Thread.sleep(10);
            // Start of a method/block
            } catch (InterruptedException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        fail("Reference was not cleared");
    // End of a block/expression
    }

    // Start of a method/block
    public static <T> void assertEqualsIgnoreOrder(Collection<T> expected, Collection<? extends T> actual) {
        // Calls a method
        assertEquals(Set.copyOf(expected), Set.copyOf(actual));
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertEqualsSNBT(String snbt, BinaryTag compound) {
        // Exception handling
        try {
            // Calls a method
            final var converted = MinestomAdventure.tagStringIO().asTag(snbt);
            // Calls a method
            assertEquals(converted, compound);
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            fail(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertEqualsIgnoreSpace(String s1, String s2, boolean matchCase) {
        // Calls a method
        final String val1 = stripExtraSpaces(s1);
        // Calls a method
        final String val2 = stripExtraSpaces(s2);
        // Branch: checks a condition
        if (matchCase) {
            // Calls a method
            assertEquals(val1, val2);
        // Alternative branch of the condition
        } else {
            // Calls a method
            assertTrue(val1.equalsIgnoreCase(val2));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertEqualsIgnoreSpace(String s1, String s2) {
        // Calls a method
        assertEqualsIgnoreSpace(s1, s2, true);
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertPoint(Point p1, Point p2) {
        // Calls a method
        assertTrue(p1.samePoint(p2), String.format("Points don't match! Expected: %s, but got: %s", p1, p2));
    // End of a block/expression
    }

    // Start of a method/block
    private static String stripExtraSpaces(String s) {
        // Calls a method
        StringBuilder formattedString = new StringBuilder();
        // Calls a method
        java.util.StringTokenizer st = new java.util.StringTokenizer(s);
        // Loop: repeats a block
        while (st.hasMoreTokens()) {
            // Calls a method
            formattedString.append(st.nextToken());
        // End of a block/expression
        }
        // Returns a value to the caller
        return formattedString.toString().trim();
    // End of a block/expression
    }
// End of a block/expression
}
