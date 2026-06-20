// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ItemAirTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testAir() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND_SWORD);
        // Appelle une méthode
        assertFalse(item.isAir());
        // Appelle une méthode
        assertTrue(ItemStack.AIR.isAir());
        // Appelle une méthode
        var emptyItem = item.withAmount(0);
        // Appelle une méthode
        assertTrue(emptyItem.isAir());
        // Appelle une méthode
        assertEquals(emptyItem, ItemStack.AIR, "AIR item can be compared to empty item");
        // Appelle une méthode
        assertSame(emptyItem, ItemStack.AIR, "AIR item identity can be compared to empty item");

        // Appelle une méthode
        assertSame(ItemStack.AIR, ItemStack.of(Material.DIAMOND, 0));
        // Appelle une méthode
        assertSame(ItemStack.AIR, ItemStack.builder(Material.DIAMOND).amount(0).build());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
