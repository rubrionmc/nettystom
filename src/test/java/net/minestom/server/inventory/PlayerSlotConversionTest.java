// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test conversion from packet slots to internal ones (used in events and inventory methods)
 */
// Type declaration (class/interface/enum/record)
public class PlayerSlotConversionTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void hotbar() {
        // Convert 36-44 into 0-8
        // Loop: repeats a block
        for (int i = 0; i < 9; i++) {
            // Calls a method
            assertEquals(i, convertWindow0SlotToMinestomSlot(i + 36));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mainInventory() {
        // No conversion, slots should stay 9-35
        // Loop: repeats a block
        for (int i = 9; i < 9 * 4; i++) {
            // Calls a method
            assertEquals(i, convertWindow0SlotToMinestomSlot(i));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void armor() {
        // Calls a method
        assertEquals(HELMET_SLOT, 41);
        // Calls a method
        assertEquals(CHESTPLATE_SLOT, 42);
        // Calls a method
        assertEquals(LEGGINGS_SLOT, 43);
        // Calls a method
        assertEquals(BOOTS_SLOT, 44);
        // Calls a method
        assertEquals(OFFHAND_SLOT, 45);

        // Convert 5-8 & 45 into 41-45
        // Calls a method
        assertEquals(HELMET_SLOT, convertWindow0SlotToMinestomSlot(5));
        // Calls a method
        assertEquals(CHESTPLATE_SLOT, convertWindow0SlotToMinestomSlot(6));
        // Calls a method
        assertEquals(LEGGINGS_SLOT, convertWindow0SlotToMinestomSlot(7));
        // Calls a method
        assertEquals(BOOTS_SLOT, convertWindow0SlotToMinestomSlot(8));
        // Calls a method
        assertEquals(OFFHAND_SLOT, convertWindow0SlotToMinestomSlot(45));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void craft() {
        // Calls a method
        assertEquals(CRAFT_RESULT, 36);
        // Calls a method
        assertEquals(CRAFT_SLOT_1, 37);
        // Calls a method
        assertEquals(CRAFT_SLOT_2, 38);
        // Calls a method
        assertEquals(CRAFT_SLOT_3, 39);
        // Calls a method
        assertEquals(CRAFT_SLOT_4, 40);

        // Convert 0-4 into 36-40
        // Calls a method
        assertEquals(CRAFT_RESULT, convertWindow0SlotToMinestomSlot(0));
        // Calls a method
        assertEquals(CRAFT_SLOT_1, convertWindow0SlotToMinestomSlot(1));
        // Calls a method
        assertEquals(CRAFT_SLOT_2, convertWindow0SlotToMinestomSlot(2));
        // Calls a method
        assertEquals(CRAFT_SLOT_3, convertWindow0SlotToMinestomSlot(3));
        // Calls a method
        assertEquals(CRAFT_SLOT_4, convertWindow0SlotToMinestomSlot(4));
    // End of a block/expression
    }
// End of a block/expression
}
