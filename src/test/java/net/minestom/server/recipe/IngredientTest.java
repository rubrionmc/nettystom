// Déclaration du paquet de ce fichier
package net.minestom.server.recipe;

// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;

// Déclaration de type (classe/interface/enum/record)
public class IngredientTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cannotCreateAirIngredient() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(Material.AIR));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cannotCreateEmptyIngredient() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(List.of()));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
