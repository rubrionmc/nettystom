// Package declaration for this file
package net.minestom.server.inventory.click;

// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public final class InventoryClickResult {
    // Code statement
    private ItemStack clicked;
    // Code statement
    private ItemStack cursor;
    // Code statement
    private boolean cancel;

    // Start of a method/block
    public InventoryClickResult(ItemStack clicked, ItemStack cursor) {
        // Access to the current/parent object
        this.clicked = clicked;
        // Access to the current/parent object
        this.cursor = cursor;
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getClicked() {
        // Returns a value to the caller
        return clicked;
    // End of a block/expression
    }

    // Start of a method/block
    void setClicked(ItemStack clicked) {
        // Access to the current/parent object
        this.clicked = clicked;
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getCursor() {
        // Returns a value to the caller
        return cursor;
    // End of a block/expression
    }

    // Start of a method/block
    void setCursor(ItemStack cursor) {
        // Access to the current/parent object
        this.cursor = cursor;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCancel() {
        // Returns a value to the caller
        return cancel;
    // End of a block/expression
    }

    // Start of a method/block
    void setCancel(boolean cancel) {
        // Access to the current/parent object
        this.cancel = cancel;
    // End of a block/expression
    }

    // Start of a method/block
    InventoryClickResult cancelled() {
        // Calls a method
        setCancel(true);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }
// End of a block/expression
}
