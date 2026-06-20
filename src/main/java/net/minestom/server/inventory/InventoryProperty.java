// Package declaration for this file
package net.minestom.server.inventory;

/**
 * List of inventory property and their ID
 * <p>
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Set_Container_Property">the Minecraft wiki</a> for more information
 */
// Type declaration (class/interface/enum/record)
public enum InventoryProperty {

    // Code statement
    FURNACE_FIRE_ICON((short) 0),
    // Code statement
    FURNACE_MAXIMUM_FUEL_BURN_TIME((short) 1),
    // Code statement
    FURNACE_PROGRESS_ARROW((short) 2),
    // Code statement
    FURNACE_MAXIMUM_PROGRESS((short) 3),

    // Code statement
    ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_TOP((short) 0),
    // Code statement
    ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_MIDDLE((short) 1),
    // Code statement
    ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_BOTTOM((short) 2),
    // Code statement
    ENCHANTMENT_TABLE_SEED((short) 3),
    // Code statement
    ENCHANTMENT_TABLE_ENCH_ID_TOP((short) 4),
    // Code statement
    ENCHANTMENT_TABLE_ENCH_ID_MIDDLE((short) 5),
    // Code statement
    ENCHANTMENT_TABLE_ENCH_ID_BOTTOM((short) 6),
    // Code statement
    ENCHANTMENT_TABLE_ENCH_LEVEL_TOP((short) 7),
    // Code statement
    ENCHANTMENT_TABLE_ENCH_LEVEL_MIDDLE((short) 8),
    // Code statement
    ENCHANTMENT_TABLE_ENCH_LEVEL_BOTTOM((short) 9),

    // Code statement
    BEACON_POWER_LEVEL((short) 0),
    // Code statement
    BEACON_FIRST_POTION((short) 1),
    // Code statement
    BEACON_SECOND_POTION((short) 2),

    // Code statement
    ANVIL_REPAIR_COST((short) 0),

    // Code statement
    BREWING_STAND_BREW_TIME((short) 0),
    // Code statement
    BREWING_STAND_FUEL_TIME((short) 1),

    // Code statement
    STONECUTTER_SELECTED_RECIPE((short) 0),

    // Code statement
    LOOM_SELECTED_PATTERN((short) 0),

    // Calls a method
    LECTERN_PAGE_NUMBER((short) 0);


    // Code statement
    private final short property;

    // Start of a method/block
    InventoryProperty(short property) {
        // Access to the current/parent object
        this.property = property;
    // End of a block/expression
    }

    // Start of a method/block
    public short getProperty() {
        // Returns a value to the caller
        return property;
    // End of a block/expression
    }
// End of a block/expression
}
