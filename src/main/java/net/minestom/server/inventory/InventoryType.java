// Package declaration for this file
package net.minestom.server.inventory;

/**
 * Represents a type of {@link Inventory}
 */
// Type declaration (class/interface/enum/record)
public enum InventoryType {

    // Code statement
    CHEST_1_ROW(9),
    // Code statement
    CHEST_2_ROW(18),
    // Code statement
    CHEST_3_ROW(27),
    // Code statement
    CHEST_4_ROW(36),
    // Code statement
    CHEST_5_ROW(45),
    // Code statement
    CHEST_6_ROW(54),
    // Code statement
    WINDOW_3X3(9),
    // Code statement
    CRAFTER_3X3(9),
    // Code statement
    ANVIL(3),
    // Code statement
    BEACON(1),
    // Code statement
    BLAST_FURNACE(3),
    // Code statement
    BREWING_STAND(5),
    // Code statement
    CRAFTING(10),
    // Code statement
    ENCHANTMENT(2),
    // Code statement
    FURNACE(3),
    // Code statement
    GRINDSTONE(3),
    // Code statement
    HOPPER(5),
    // Code statement
    LECTERN(1),
    // Code statement
    LOOM(4),
    // Code statement
    MERCHANT(3),
    // Code statement
    SHULKER_BOX(27),
    // Code statement
    SMITHING(4),
    // Code statement
    SMOKER(3),
    // Code statement
    CARTOGRAPHY(3),
    // Calls a method
    STONE_CUTTER(2);

    // Code statement
    private final int size;

    // Start of a method/block
    InventoryType(int size) {
        // Access to the current/parent object
        this.size = size;
    // End of a block/expression
    }

    // Start of a method/block
    public int getWindowType() {
        // Returns a value to the caller
        return ordinal();
    // End of a block/expression
    }

    // Start of a method/block
    public int getSize() {
        // Returns a value to the caller
        return size;
    // End of a block/expression
    }

    /**
     * @deprecated use {@link #getSize()}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int getAdditionalSlot() {
        // Returns a value to the caller
        return size;
    // End of a block/expression
    }

// End of a block/expression
}
