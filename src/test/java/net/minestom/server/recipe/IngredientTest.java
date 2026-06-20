// Package declaration for this file
package net.minestom.server.recipe;

// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;

// Type declaration (class/interface/enum/record)
public class IngredientTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cannotCreateAirIngredient() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(Material.AIR));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cannotCreateEmptyIngredient() {
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(List.of()));
    // End of a block/expression
    }
// End of a block/expression
}
