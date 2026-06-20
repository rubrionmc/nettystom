// Déclaration du paquet de ce fichier
package net.minestom.server.utils.inventory;

// Déclaration de type (classe/interface/enum/record)
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

    // Affecte une valeur
    public static final int WINDOW_0_OFFSET = 9;

    // Affecte une valeur
    public static final int CRAFT_RESULT = 36;
    // Affecte une valeur
    public static final int CRAFT_SLOT_1 = 37;
    // Affecte une valeur
    public static final int CRAFT_SLOT_2 = 38;
    // Affecte une valeur
    public static final int CRAFT_SLOT_3 = 39;
    // Affecte une valeur
    public static final int CRAFT_SLOT_4 = 40;

    // Affecte une valeur
    public static final int HELMET_SLOT = 41;
    // Affecte une valeur
    public static final int CHESTPLATE_SLOT = 42;
    // Affecte une valeur
    public static final int LEGGINGS_SLOT = 43;
    // Affecte une valeur
    public static final int BOOTS_SLOT = 44;
    // Affecte une valeur
    public static final int OFFHAND_SLOT = 45;

    // Début d'une méthode/d'un bloc
    private PlayerInventoryUtils() {

    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns true if the given minestom slot is on the hotbar or offhand, false otherwise.
     */
    // Début d'une méthode/d'un bloc
    public static boolean isHotbarOrOffHandSlot(int minestomSlot) {
        // Renvoie une valeur à l'appelant
        return (minestomSlot >= 0 && minestomSlot < 9) || minestomSlot == OFFHAND_SLOT;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts a window packet slot to a Minestom one.
     *
     * @param slot   the packet slot
     * @return a slot which can be use internally with Minestom
     */
    // Début d'une méthode/d'un bloc
    public static int convertWindow0SlotToMinestomSlot(int slot) {
        // Renvoie une valeur à l'appelant
        return switch (slot) {
            // Embranchement multiple (switch/case)
            case 0 -> CRAFT_RESULT;
            // Embranchement multiple (switch/case)
            case 1 -> CRAFT_SLOT_1;
            // Embranchement multiple (switch/case)
            case 2 -> CRAFT_SLOT_2;
            // Embranchement multiple (switch/case)
            case 3 -> CRAFT_SLOT_3;
            // Embranchement multiple (switch/case)
            case 4 -> CRAFT_SLOT_4;
            // Embranchement multiple (switch/case)
            case 5 -> HELMET_SLOT;
            // Embranchement multiple (switch/case)
            case 6 -> CHESTPLATE_SLOT;
            // Embranchement multiple (switch/case)
            case 7 -> LEGGINGS_SLOT;
            // Embranchement multiple (switch/case)
            case 8 -> BOOTS_SLOT;
            // Appelle une méthode
            default -> convertWindowSlotToMinestomSlot(slot, WINDOW_0_OFFSET);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int convertWindowSlotToMinestomSlot(int slot, int offset) {
        // Affecte une valeur
        final int rowSize = 9;
        // Affecte une valeur
        slot -= offset;
        // Embranchement : vérifie une condition
        if (slot >= rowSize * 3 && slot < rowSize * 4) {
            // Affecte une valeur
            slot = slot % 9; // Hotbar
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            slot = slot + rowSize; // Rest of inventory
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return slot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns true if the given Minestom slot is valid as a Player inventory slot (ie is it not a crafting grid slot)
     */
    // Début d'une méthode/d'un bloc
    public static boolean isPlayerInventorySlot(int minestomSlot) {
        // Renvoie une valeur à l'appelant
        return !(minestomSlot >= CRAFT_RESULT && minestomSlot <= CRAFT_SLOT_4);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to convert a Minestom slot to a player inventory slot. Only valid for some slots, should be tested
     * with {@link #isPlayerInventorySlot(int)} first.
     */
    // Début d'une méthode/d'un bloc
    public static int convertMinestomSlotToPlayerInventorySlot(int minestomSlot) {
        // Embranchement : vérifie une condition
        if (minestomSlot >= HELMET_SLOT && minestomSlot <= BOOTS_SLOT) {
            // Armor is in the reverse order Minestom tracks it, and immediately after the main inventory
            // Renvoie une valeur à l'appelant
            return (3 - (minestomSlot - HELMET_SLOT)) + 36;
        // Embranchement : vérifie une condition
        } else if (minestomSlot == OFFHAND_SLOT) {
            // Renvoie une valeur à l'appelant
            return 40;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return minestomSlot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to convert internal slot to one used in packets
     *
     * @param slot the internal slot
     * @return a slot id which can be used for packets
     */
    // Début d'une méthode/d'un bloc
    public static int convertMinestomSlotToWindowSlot(int slot) {
        // Embranchement : vérifie une condition
        if (slot > -1 && slot < 9) { // Held bar 0-8
            // Affecte une valeur
            slot = slot + 36;
        // Embranchement : vérifie une condition
        } else if (slot > 8 && slot < 36) { // Inventory 9-35
            // Affecte une valeur
            slot = slot;
        // Embranchement : vérifie une condition
        } else if (slot >= CRAFT_RESULT && slot <= CRAFT_SLOT_4) { // Crafting 36-40
            // Affecte une valeur
            slot = slot - 36;
        // Embranchement : vérifie une condition
        } else if (slot >= HELMET_SLOT && slot <= BOOTS_SLOT) { // Armor 41-44
            // Affecte une valeur
            slot = slot - 36;
        // Embranchement : vérifie une condition
        } else if (slot == OFFHAND_SLOT) { // Off hand
            // Affecte une valeur
            slot = 45;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return slot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to convert a player inventory slot from a client to a Minestom slot.
     * See above for description
     *
     * @param slot the client slot
     * @return a slot which can be used internally with Minestom
     */
    // Début d'une méthode/d'un bloc
    public static int convertPlayerInventorySlotToMinestomSlot(int slot) {
        // Embranchement : vérifie une condition
        if (slot < 0 || slot > 40) return -1; // Sanity
        // Armor slots are reversed in Minestom, and off hand is a different slot
        // Embranchement : vérifie une condition
        if (slot == 36) return BOOTS_SLOT;
        // Embranchement : vérifie une condition
        if (slot == 37) return LEGGINGS_SLOT;
        // Embranchement : vérifie une condition
        if (slot == 38) return CHESTPLATE_SLOT;
        // Embranchement : vérifie une condition
        if (slot == 39) return HELMET_SLOT;
        // Embranchement : vérifie une condition
        if (slot == 40) return OFFHAND_SLOT;
        // Renvoie une valeur à l'appelant
        return slot;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
