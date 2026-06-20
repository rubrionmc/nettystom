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
public class BrewingStandInventory extends Inventory {

    // Code statement
    private short brewTime;
    // Code statement
    private short fuelTime;

    // Start of a method/block
    public BrewingStandInventory(Component title) {
        // Access to the current/parent object
        super(InventoryType.BREWING_STAND, title);
    // End of a block/expression
    }

    // Start of a method/block
    public BrewingStandInventory(String title) {
        // Access to the current/parent object
        super(InventoryType.BREWING_STAND, title);
    // End of a block/expression
    }

    /**
     * Gets the brewing stand brew time.
     *
     * @return the brew time in tick
     */
    // Start of a method/block
    public short getBrewTime() {
        // Returns a value to the caller
        return brewTime;
    // End of a block/expression
    }

    /**
     * Changes the brew time.
     *
     * @param brewTime the new brew time in tick
     */
    // Start of a method/block
    public void setBrewTime(short brewTime) {
        // Access to the current/parent object
        this.brewTime = brewTime;
        // Calls a method
        sendProperty(InventoryProperty.BREWING_STAND_BREW_TIME, brewTime);
    // End of a block/expression
    }

    /**
     * Gets the brewing stand fuel time.
     *
     * @return the fuel time in tick
     */
    // Start of a method/block
    public short getFuelTime() {
        // Returns a value to the caller
        return fuelTime;
    // End of a block/expression
    }

    /**
     * Changes the fuel time.
     *
     * @param fuelTime the new fuel time in tick
     */
    // Start of a method/block
    public void setFuelTime(short fuelTime) {
        // Access to the current/parent object
        this.fuelTime = fuelTime;
        // Calls a method
        sendProperty(InventoryProperty.BREWING_STAND_FUEL_TIME, fuelTime);
    // End of a block/expression
    }

// End of a block/expression
}
