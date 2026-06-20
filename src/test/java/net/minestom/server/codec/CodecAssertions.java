// Déclaration du paquet de ce fichier
package net.minestom.server.codec;


// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

// Déclaration de type (classe/interface/enum/record)
public final class CodecAssertions {

    // Début d'une méthode/d'un bloc
    public static <T> T assertOk(Result<T> result) {
        // Renvoie une valeur à l'appelant
        return switch (result) {
            // Embranchement multiple (switch/case)
            case Result.Ok(T value) -> value;
            // Embranchement multiple (switch/case)
            case Result.Error(String message) -> throw new AssertionError("Expected Ok but got Error: " + message);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertError(String expected, Result<?> result) {
        // Appelle une méthode
        final String message = assertInstanceOf(Result.Error.class, result).message();
        // Appelle une méthode
        assertEquals(expected, message);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
