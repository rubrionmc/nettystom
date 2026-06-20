// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test conversion from packet slots to internal ones (used in events and inventory methods)
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerSlotConversionTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void hotbar() {
        // Convert 36-44 into 0-8
        // Boucle : répète un bloc
        for (int i = 0; i < 9; i++) {
            // Appelle une méthode
            assertEquals(i, convertWindow0SlotToMinestomSlot(i + 36));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mainInventory() {
        // No conversion, slots should stay 9-35
        // Boucle : répète un bloc
        for (int i = 9; i < 9 * 4; i++) {
            // Appelle une méthode
            assertEquals(i, convertWindow0SlotToMinestomSlot(i));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void armor() {
        // Appelle une méthode
        assertEquals(HELMET_SLOT, 41);
        // Appelle une méthode
        assertEquals(CHESTPLATE_SLOT, 42);
        // Appelle une méthode
        assertEquals(LEGGINGS_SLOT, 43);
        // Appelle une méthode
        assertEquals(BOOTS_SLOT, 44);
        // Appelle une méthode
        assertEquals(OFFHAND_SLOT, 45);

        // Convert 5-8 & 45 into 41-45
        // Appelle une méthode
        assertEquals(HELMET_SLOT, convertWindow0SlotToMinestomSlot(5));
        // Appelle une méthode
        assertEquals(CHESTPLATE_SLOT, convertWindow0SlotToMinestomSlot(6));
        // Appelle une méthode
        assertEquals(LEGGINGS_SLOT, convertWindow0SlotToMinestomSlot(7));
        // Appelle une méthode
        assertEquals(BOOTS_SLOT, convertWindow0SlotToMinestomSlot(8));
        // Appelle une méthode
        assertEquals(OFFHAND_SLOT, convertWindow0SlotToMinestomSlot(45));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void craft() {
        // Appelle une méthode
        assertEquals(CRAFT_RESULT, 36);
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_1, 37);
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_2, 38);
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_3, 39);
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_4, 40);

        // Convert 0-4 into 36-40
        // Appelle une méthode
        assertEquals(CRAFT_RESULT, convertWindow0SlotToMinestomSlot(0));
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_1, convertWindow0SlotToMinestomSlot(1));
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_2, convertWindow0SlotToMinestomSlot(2));
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_3, convertWindow0SlotToMinestomSlot(3));
        // Appelle une méthode
        assertEquals(CRAFT_SLOT_4, convertWindow0SlotToMinestomSlot(4));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
