// Package declaration for this file
package net.minestom.server.utils.inventory;

// Type declaration (class/interface/enum/record)
public final class PlayerInventoryUtils {
    /*
    There are 3 different slot mappings discussed in this file:
    - Minestom slots
    - Player inventory slots
    - Window slots

    *Minestom slots* represent all inventory slots, including items and player inventory specifics
      like the crafting grid, armor, and off hand. Those ids are specific to PlayerInventory and
      are mapped as follows:
      0-8: Hotbar
      9-35: Inventory
      36-40: Crafting grid
      41-44: Armor
      45: Offhand

    *Player inventory slots* represent the vanilla inventory slots, specifically the hotbar, 3 row
      inventory, armor slots, and off hand (NOT player crafting grid slots). Those ids are as follows:
      0-8: Hotbar
      9-35: Inventory
      36-39: Boots, Leggings, Chestplate, Helmet
      40: Offhand

    *Window slots* represent the slots in a window. Window id=0 represents the player crafting grid
      inventory. These slots start with W slots (where W = openInventory.getSize()) followed by the
      3 row player inventory and then the hotbar.
      0-W: Open inventory content
      W-(W+27): Player inventory content
      (W+27)-(W+36): Hotbar

      Window id=0 has special content in the first 9 slots:
      0: 2x2 crafting result
      1-4: 2x2 crafting grid
      5-8: Armor slots
     */

    // Assigns a value
    public static final int WINDOW_0_OFFSET = 9;

    // Assigns a value
    public static final int CRAFT_RESULT = 36;
    // Assigns a value
    public static final int CRAFT_SLOT_1 = 37;
    // Assigns a value
    public static final int CRAFT_SLOT_2 = 38;
    // Assigns a value
    public static final int CRAFT_SLOT_3 = 39;
    // Assigns a value
    public static final int CRAFT_SLOT_4 = 40;

    // Assigns a value
    public static final int HELMET_SLOT = 41;
    // Assigns a value
    public static final int CHESTPLATE_SLOT = 42;
    // Assigns a value
    public static final int LEGGINGS_SLOT = 43;
    // Assigns a value
    public static final int BOOTS_SLOT = 44;
    // Assigns a value
    public static final int OFFHAND_SLOT = 45;

    // Start of a method/block
    private PlayerInventoryUtils() {

    // End of a block/expression
    }

    /**
     * Returns true if the given minestom slot is on the hotbar or offhand, false otherwise.
     */
    // Start of a method/block
    public static boolean isHotbarOrOffHandSlot(int minestomSlot) {
        // Returns a value to the caller
        return (minestomSlot >= 0 && minestomSlot < 9) || minestomSlot == OFFHAND_SLOT;
    // End of a block/expression
    }

    /**
     * Converts a window packet slot to a Minestom one.
     *
     * @param slot   the packet slot
     * @return a slot which can be use internally with Minestom
     */
    // Start of a method/block
    public static int convertWindow0SlotToMinestomSlot(int slot) {
        // Returns a value to the caller
        return switch (slot) {
            // Multiple branching (switch/case)
            case 0 -> CRAFT_RESULT;
            // Multiple branching (switch/case)
            case 1 -> CRAFT_SLOT_1;
            // Multiple branching (switch/case)
            case 2 -> CRAFT_SLOT_2;
            // Multiple branching (switch/case)
            case 3 -> CRAFT_SLOT_3;
            // Multiple branching (switch/case)
            case 4 -> CRAFT_SLOT_4;
            // Multiple branching (switch/case)
            case 5 -> HELMET_SLOT;
            // Multiple branching (switch/case)
            case 6 -> CHESTPLATE_SLOT;
            // Multiple branching (switch/case)
            case 7 -> LEGGINGS_SLOT;
            // Multiple branching (switch/case)
            case 8 -> BOOTS_SLOT;
            // Multiple branching (switch/case)
            default -> convertWindowSlotToMinestomSlot(slot, WINDOW_0_OFFSET);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public static int convertWindowSlotToMinestomSlot(int slot, int offset) {
        // Assigns a value
        final int rowSize = 9;
        // Code statement
        slot -= offset;
        // Branch: checks a condition
        if (slot >= rowSize * 3 && slot < rowSize * 4) {
            // Assigns a value
            slot = slot % 9; // Hotbar
        // Alternative branch of the condition
        } else {
            // Assigns a value
            slot = slot + rowSize; // Rest of inventory
        // End of a block/expression
        }
        // Returns a value to the caller
        return slot;
    // End of a block/expression
    }

    /**
     * Returns true if the given Minestom slot is valid as a Player inventory slot (ie is it not a crafting grid slot)
     */
    // Start of a method/block
    public static boolean isPlayerInventorySlot(int minestomSlot) {
        // Returns a value to the caller
        return !(minestomSlot >= CRAFT_RESULT && minestomSlot <= CRAFT_SLOT_4);
    // End of a block/expression
    }

    /**
     * Used to convert a Minestom slot to a player inventory slot. Only valid for some slots, should be tested
     * with {@link #isPlayerInventorySlot(int)} first.
     */
    // Start of a method/block
    public static int convertMinestomSlotToPlayerInventorySlot(int minestomSlot) {
        // Branch: checks a condition
        if (minestomSlot >= HELMET_SLOT && minestomSlot <= BOOTS_SLOT) {
            // Armor is in the reverse order Minestom tracks it, and immediately after the main inventory
            // Returns a value to the caller
            return (3 - (minestomSlot - HELMET_SLOT)) + 36;
        // Branch: checks a condition
        } else if (minestomSlot == OFFHAND_SLOT) {
            // Returns a value to the caller
            return 40;
        // End of a block/expression
        }
        // Returns a value to the caller
        return minestomSlot;
    // End of a block/expression
    }

    /**
     * Used to convert internal slot to one used in packets
     *
     * @param slot the internal slot
     * @return a slot id which can be used for packets
     */
    // Start of a method/block
    public static int convertMinestomSlotToWindowSlot(int slot) {
        // Branch: checks a condition
        if (slot > -1 && slot < 9) { // Held bar 0-8
            // Assigns a value
            slot = slot + 36;
        // Branch: checks a condition
        } else if (slot > 8 && slot < 36) { // Inventory 9-35
            // Assigns a value
            slot = slot;
        // Branch: checks a condition
        } else if (slot >= CRAFT_RESULT && slot <= CRAFT_SLOT_4) { // Crafting 36-40
            // Assigns a value
            slot = slot - 36;
        // Branch: checks a condition
        } else if (slot >= HELMET_SLOT && slot <= BOOTS_SLOT) { // Armor 41-44
            // Assigns a value
            slot = slot - 36;
        // Branch: checks a condition
        } else if (slot == OFFHAND_SLOT) { // Off hand
            // Assigns a value
            slot = 45;
        // End of a block/expression
        }
        // Returns a value to the caller
        return slot;
    // End of a block/expression
    }

    /**
     * Used to convert a player inventory slot from a client to a Minestom slot.
     * See above for description
     *
     * @param slot the client slot
     * @return a slot which can be used internally with Minestom
     */
    // Start of a method/block
    public static int convertPlayerInventorySlotToMinestomSlot(int slot) {
        // Branch: checks a condition
        if (slot < 0 || slot > 40) return -1; // Sanity
        // Armor slots are reversed in Minestom, and off hand is a different slot
        // Branch: checks a condition
        if (slot == 36) return BOOTS_SLOT;
        // Branch: checks a condition
        if (slot == 37) return LEGGINGS_SLOT;
        // Branch: checks a condition
        if (slot == 38) return CHESTPLATE_SLOT;
        // Branch: checks a condition
        if (slot == 39) return HELMET_SLOT;
        // Branch: checks a condition
        if (slot == 40) return OFFHAND_SLOT;
        // Returns a value to the caller
        return slot;
    // End of a block/expression
    }
// End of a block/expression
}
