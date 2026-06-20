// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Déclaration de type (classe/interface/enum/record)
public class TranslationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testUnregisteredTranslation() {
        // Affecte une valeur
        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = true;
        // Gestion des exceptions
        try {
            // Début d'une méthode/d'un bloc
            MinestomFlattenerProvider.INSTANCE.flatten(Component.translatable("key.unregistered"), text -> {
            // Fin d'un bloc/d'une expression
            });
        // Début d'une méthode/d'un bloc
        } finally {
            // Affecte une valeur
            MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
