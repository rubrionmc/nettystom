// Package declaration for this file
package net.minestom.server.inventory.type;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryProperty;
// Import of a required class
import net.minestom.server.inventory.InventoryType;

// Type declaration (class/interface/enum/record)
public class AnvilInventory extends Inventory {

    // Code statement
    private short repairCost;

    // Start of a method/block
    public AnvilInventory(Component title) {
        // Access to the current/parent object
        super(InventoryType.ANVIL, title);
    // End of a block/expression
    }

    // Start of a method/block
    public AnvilInventory(String title) {
        // Access to the current/parent object
        super(InventoryType.ANVIL, title);
    // End of a block/expression
    }

    /**
     * Gets the anvil repair cost.
     *
     * @return the repair cost
     */
    // Start of a method/block
    public short getRepairCost() {
        // Returns a value to the caller
        return repairCost;
    // End of a block/expression
    }

    /**
     * Sets the anvil repair cost.
     *
     * @param cost the new anvil repair cost
     */
    // Start of a method/block
    public void setRepairCost(short cost) {
        // Access to the current/parent object
        this.repairCost = cost;
        // Calls a method
        sendProperty(InventoryProperty.ANVIL_REPAIR_COST, cost);
    // End of a block/expression
    }
// End of a block/expression
}
