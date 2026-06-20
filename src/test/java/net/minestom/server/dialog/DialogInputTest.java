// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
class DialogInputTest {
    
    /**
     * Tests for the validateKey(String key) method in the DialogInput class.
     * This method ensures that the input key only contains alphanumeric characters and underscores.
     * If invalid characters are found, it throws an IllegalArgumentException.
     */
    
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void validateKey_validKey_doesNotThrow() {
        // Affecte une valeur
        String validKey = "valid_key_123";
        // Appelle une méthode
        assertDoesNotThrow(() -> DialogInput.validateKey(validKey));
    // Fin d'un bloc/d'une expression
    }
    
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void validateKey_keyWithSpecialCharacters_throwsException() {
        // Affecte une valeur
        String invalidKey = "invalid!key";
        // Affecte une valeur
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        // Appelle une méthode
        () -> DialogInput.validateKey(invalidKey));
        // Appelle une méthode
        assertEquals("Invalid input key: invalid!key. Must match [a-zA-Z0-9_]+", exception.getMessage());
    // Fin d'un bloc/d'une expression
    }
    
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void validateKey_keyWithSpaces_throwsException() {
        // Affecte une valeur
        String keyWithSpaces = "key with spaces";
        // Affecte une valeur
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        // Appelle une méthode
        () -> DialogInput.validateKey(keyWithSpaces));
        // Appelle une méthode
        assertEquals("Invalid input key: key with spaces. Must match [a-zA-Z0-9_]+", exception.getMessage());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void validateKey_keyWithNumbersOnly_doesNotThrow() {
        // Affecte une valeur
        String numericKey = "123456";
        // Appelle une méthode
        assertDoesNotThrow(() -> DialogInput.validateKey(numericKey));
    // Fin d'un bloc/d'une expression
    }
    
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void validateKey_keyWithUnderscoresOnly_doesNotThrow() {
        // Affecte une valeur
        String underscoreKey = "_____";
        // Appelle une méthode
        assertDoesNotThrow(() -> DialogInput.validateKey(underscoreKey));
    // Fin d'un bloc/d'une expression
    }
    
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void validateKey_keyWithMixedValidCharacters_doesNotThrow() {
        // Affecte une valeur
        String mixedKey = "key123_ABC";
        // Appelle une méthode
        assertDoesNotThrow(() -> DialogInput.validateKey(mixedKey));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}