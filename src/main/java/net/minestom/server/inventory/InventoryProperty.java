// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

/**
 * List of inventory property and their ID
 * <p>
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Set_Container_Property">the Minecraft wiki</a> for more information
 */
// Déclaration de type (classe/interface/enum/record)
public enum InventoryProperty {

    // Instruction de code
    FURNACE_FIRE_ICON((short) 0),
    // Instruction de code
    FURNACE_MAXIMUM_FUEL_BURN_TIME((short) 1),
    // Instruction de code
    FURNACE_PROGRESS_ARROW((short) 2),
    // Instruction de code
    FURNACE_MAXIMUM_PROGRESS((short) 3),

    // Instruction de code
    ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_TOP((short) 0),
    // Instruction de code
    ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_MIDDLE((short) 1),
    // Instruction de code
    ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_BOTTOM((short) 2),
    // Instruction de code
    ENCHANTMENT_TABLE_SEED((short) 3),
    // Instruction de code
    ENCHANTMENT_TABLE_ENCH_ID_TOP((short) 4),
    // Instruction de code
    ENCHANTMENT_TABLE_ENCH_ID_MIDDLE((short) 5),
    // Instruction de code
    ENCHANTMENT_TABLE_ENCH_ID_BOTTOM((short) 6),
    // Instruction de code
    ENCHANTMENT_TABLE_ENCH_LEVEL_TOP((short) 7),
    // Instruction de code
    ENCHANTMENT_TABLE_ENCH_LEVEL_MIDDLE((short) 8),
    // Instruction de code
    ENCHANTMENT_TABLE_ENCH_LEVEL_BOTTOM((short) 9),

    // Instruction de code
    BEACON_POWER_LEVEL((short) 0),
    // Instruction de code
    BEACON_FIRST_POTION((short) 1),
    // Instruction de code
    BEACON_SECOND_POTION((short) 2),

    // Instruction de code
    ANVIL_REPAIR_COST((short) 0),

    // Instruction de code
    BREWING_STAND_BREW_TIME((short) 0),
    // Instruction de code
    BREWING_STAND_FUEL_TIME((short) 1),

    // Instruction de code
    STONECUTTER_SELECTED_RECIPE((short) 0),

    // Instruction de code
    LOOM_SELECTED_PATTERN((short) 0),

    // Appelle une méthode
    LECTERN_PAGE_NUMBER((short) 0);


    // Instruction de code
    private final short property;

    // Début d'une méthode/d'un bloc
    InventoryProperty(short property) {
        // Accès à l'objet courant/parent
        this.property = property;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public short getProperty() {
        // Renvoie une valeur à l'appelant
        return property;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
