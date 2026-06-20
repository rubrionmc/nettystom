// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public final class TestUtils {
    // Début d'une méthode/d'un bloc
    public static void waitUntilCleared(WeakReference<?> ref) {
        // Affecte une valeur
        final int maxTries = 100;

        // Boucle : répète un bloc
        for (int i = 0; i < maxTries; i++) {
            // Appelle une méthode
            System.gc();
            // Embranchement : vérifie une condition
            if (ref.get() == null) {
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Gestion des exceptions
            try {
                // Appelle une méthode
                Thread.sleep(10);
            // Début d'une méthode/d'un bloc
            } catch (InterruptedException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        fail("Reference was not cleared");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <T> void assertEqualsIgnoreOrder(Collection<T> expected, Collection<? extends T> actual) {
        // Appelle une méthode
        assertEquals(Set.copyOf(expected), Set.copyOf(actual));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertEqualsSNBT(String snbt, BinaryTag compound) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final var converted = MinestomAdventure.tagStringIO().asTag(snbt);
            // Appelle une méthode
            assertEquals(converted, compound);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            fail(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertEqualsIgnoreSpace(String s1, String s2, boolean matchCase) {
        // Appelle une méthode
        final String val1 = stripExtraSpaces(s1);
        // Appelle une méthode
        final String val2 = stripExtraSpaces(s2);
        // Embranchement : vérifie une condition
        if (matchCase) {
            // Appelle une méthode
            assertEquals(val1, val2);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            assertTrue(val1.equalsIgnoreCase(val2));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertEqualsIgnoreSpace(String s1, String s2) {
        // Appelle une méthode
        assertEqualsIgnoreSpace(s1, s2, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertPoint(Point p1, Point p2) {
        // Appelle une méthode
        assertTrue(p1.samePoint(p2), String.format("Points don't match! Expected: %s, but got: %s", p1, p2));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static String stripExtraSpaces(String s) {
        // Appelle une méthode
        StringBuilder formattedString = new StringBuilder();
        // Appelle une méthode
        java.util.StringTokenizer st = new java.util.StringTokenizer(s);
        // Boucle : répète un bloc
        while (st.hasMoreTokens()) {
            // Boucle : répète un bloc
            formattedString.append(st.nextToken());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return formattedString.toString().trim();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
