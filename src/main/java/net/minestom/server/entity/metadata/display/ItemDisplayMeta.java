// Package declaration for this file
package net.minestom.server.entity.metadata.display;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public class ItemDisplayMeta extends AbstractDisplayMeta {
    // Start of a method/block
    public ItemDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ItemDisplay.DISPLAYED_ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setItemStack(ItemStack value) {
        // Calls a method
        metadata.set(MetadataDef.ItemDisplay.DISPLAYED_ITEM, value);
    // End of a block/expression
    }

    // Start of a method/block
    public DisplayContext getDisplayContext() {
        // Returns a value to the caller
        return DisplayContext.VALUES[metadata.get(MetadataDef.ItemDisplay.DISPLAY_TYPE)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setDisplayContext(DisplayContext value) {
        // Calls a method
        metadata.set(MetadataDef.ItemDisplay.DISPLAY_TYPE, (byte) value.ordinal());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum DisplayContext {
        // Code statement
        NONE,
        // Code statement
        THIRDPERSON_LEFT_HAND,
        // Code statement
        THIRDPERSON_RIGHT_HAND,
        // Code statement
        FIRSTPERSON_LEFT_HAND,
        // Code statement
        FIRSTPERSON_RIGHT_HAND,
        // Code statement
        HEAD,
        // Code statement
        GUI,
        // Code statement
        GROUND,
        // Code statement
        FIXED,
        // Code statement
        ON_SHELF;

        // Calls a method
        private final static DisplayContext[] VALUES = values();

    // End of a block/expression
    }
// End of a block/expression
}
