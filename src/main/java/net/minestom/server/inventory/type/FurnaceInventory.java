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
public class FurnaceInventory extends Inventory {

    // Code statement
    private short remainingFuelTick;
    // Code statement
    private short maximumFuelBurnTime;
    // Code statement
    private short progressArrow;
    // Code statement
    private short maximumProgress;

    // Start of a method/block
    public FurnaceInventory(Component title) {
        // Access to the current/parent object
        super(InventoryType.FURNACE, title);
    // End of a block/expression
    }

    // Start of a method/block
    public FurnaceInventory(String title) {
        // Access to the current/parent object
        super(InventoryType.FURNACE, title);
    // End of a block/expression
    }

    /**
     * Represents the amount of tick until the fire icon come empty.
     *
     * @return the amount of tick until the fire icon come empty
     */
    // Start of a method/block
    public short getRemainingFuelTick() {
        // Returns a value to the caller
        return remainingFuelTick;
    // End of a block/expression
    }

    /**
     * Represents the amount of tick until the fire icon come empty.
     *
     * @param remainingFuelTick the amount of tick until the fire icon is empty
     */
    // Start of a method/block
    public void setRemainingFuelTick(short remainingFuelTick) {
        // Access to the current/parent object
        this.remainingFuelTick = remainingFuelTick;
        // Calls a method
        sendProperty(InventoryProperty.FURNACE_FIRE_ICON, remainingFuelTick);
    // End of a block/expression
    }

    // Start of a method/block
    public short getMaximumFuelBurnTime() {
        // Returns a value to the caller
        return maximumFuelBurnTime;
    // End of a block/expression
    }

    // Start of a method/block
    public void setMaximumFuelBurnTime(short maximumFuelBurnTime) {
        // Access to the current/parent object
        this.maximumFuelBurnTime = maximumFuelBurnTime;
        // Calls a method
        sendProperty(InventoryProperty.FURNACE_MAXIMUM_FUEL_BURN_TIME, maximumFuelBurnTime);
    // End of a block/expression
    }

    // Start of a method/block
    public short getProgressArrow() {
        // Returns a value to the caller
        return progressArrow;
    // End of a block/expression
    }

    // Start of a method/block
    public void setProgressArrow(short progressArrow) {
        // Access to the current/parent object
        this.progressArrow = progressArrow;
        // Calls a method
        sendProperty(InventoryProperty.FURNACE_PROGRESS_ARROW, progressArrow);
    // End of a block/expression
    }

    // Start of a method/block
    public short getMaximumProgress() {
        // Returns a value to the caller
        return maximumProgress;
    // End of a block/expression
    }

    // Start of a method/block
    public void setMaximumProgress(short maximumProgress) {
        // Access to the current/parent object
        this.maximumProgress = maximumProgress;
        // Calls a method
        sendProperty(InventoryProperty.FURNACE_MAXIMUM_PROGRESS, maximumProgress);
    // End of a block/expression
    }
// End of a block/expression
}
