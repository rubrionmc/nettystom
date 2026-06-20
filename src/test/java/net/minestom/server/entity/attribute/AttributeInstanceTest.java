// Déclaration du paquet de ce fichier
package net.minestom.server.entity.attribute;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class AttributeInstanceTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testReplaceAttributeSameValue() {
        // Appelle une méthode
        var attribute = new AttributeInstance(Attribute.SAFE_FALL_DISTANCE, null);
        // Appelle une méthode
        var modifier = new AttributeModifier("test", 1.0, AttributeOperation.ADD_VALUE);

        // Appelle une méthode
        attribute.addModifier(modifier);
        // Appelle une méthode
        assertEquals(4, attribute.getValue());

        // Appelle une méthode
        attribute.addModifier(modifier);
        // Appelle une méthode
        assertEquals(4, attribute.getValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testReplaceAttributeNewValue() {
        // Appelle une méthode
        var attribute = new AttributeInstance(Attribute.SAFE_FALL_DISTANCE, null);

        // Appelle une méthode
        attribute.addModifier(new AttributeModifier("test", 1.0, AttributeOperation.ADD_VALUE));
        // Appelle une méthode
        assertEquals(4, attribute.getValue());

        // Appelle une méthode
        attribute.addModifier(new AttributeModifier("test", 2.0, AttributeOperation.ADD_VALUE));
        // Instruction de code
        assertEquals(5, attribute.getValue()); // New value

        // Appelle une méthode
        attribute.addModifier(new AttributeModifier("test", 2.0, AttributeOperation.ADD_MULTIPLIED_BASE));
        // Instruction de code
        assertEquals(9, attribute.getValue()); // New operation

    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
