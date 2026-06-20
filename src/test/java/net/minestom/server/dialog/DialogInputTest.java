// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
class DialogInputTest {
    
    /**
     * Tests for the validateKey(String key) method in the DialogInput class.
     * This method ensures that the input key only contains alphanumeric characters and underscores.
     * If invalid characters are found, it throws an IllegalArgumentException.
     */
    
    // Annotation for the following element
    @Test
    // Start of a method/block
    void validateKey_validKey_doesNotThrow() {
        // Assigns a value
        String validKey = "valid_key_123";
        // Calls a method
        assertDoesNotThrow(() -> DialogInput.validateKey(validKey));
    // End of a block/expression
    }
    
    // Annotation for the following element
    @Test
    // Start of a method/block
    void validateKey_keyWithSpecialCharacters_throwsException() {
        // Assigns a value
        String invalidKey = "invalid!key";
        // Assigns a value
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        // Calls a method
        () -> DialogInput.validateKey(invalidKey));
        // Calls a method
        assertEquals("Invalid input key: invalid!key. Must match [a-zA-Z0-9_]+", exception.getMessage());
    // End of a block/expression
    }
    
    // Annotation for the following element
    @Test
    // Start of a method/block
    void validateKey_keyWithSpaces_throwsException() {
        // Assigns a value
        String keyWithSpaces = "key with spaces";
        // Assigns a value
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        // Calls a method
        () -> DialogInput.validateKey(keyWithSpaces));
        // Calls a method
        assertEquals("Invalid input key: key with spaces. Must match [a-zA-Z0-9_]+", exception.getMessage());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void validateKey_keyWithNumbersOnly_doesNotThrow() {
        // Assigns a value
        String numericKey = "123456";
        // Calls a method
        assertDoesNotThrow(() -> DialogInput.validateKey(numericKey));
    // End of a block/expression
    }
    
    // Annotation for the following element
    @Test
    // Start of a method/block
    void validateKey_keyWithUnderscoresOnly_doesNotThrow() {
        // Assigns a value
        String underscoreKey = "_____";
        // Calls a method
        assertDoesNotThrow(() -> DialogInput.validateKey(underscoreKey));
    // End of a block/expression
    }
    
    // Annotation for the following element
    @Test
    // Start of a method/block
    void validateKey_keyWithMixedValidCharacters_doesNotThrow() {
        // Assigns a value
        String mixedKey = "key123_ABC";
        // Calls a method
        assertDoesNotThrow(() -> DialogInput.validateKey(mixedKey));
    // End of a block/expression
    }
// End of a block/expression
}