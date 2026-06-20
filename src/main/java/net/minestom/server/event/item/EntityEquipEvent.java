// Package declaration for this file
package net.minestom.server.event.item;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public class EntityEquipEvent implements EntityInstanceEvent, ItemEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private ItemStack equippedItem;
    // Code statement
    private final EquipmentSlot slot;

    // Start of a method/block
    public EntityEquipEvent(Entity entity, ItemStack equippedItem, EquipmentSlot slot) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.equippedItem = equippedItem;
        // Access to the current/parent object
        this.slot = slot;
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getEquippedItem() {
        // Returns a value to the caller
        return equippedItem;
    // End of a block/expression
    }

    // Start of a method/block
    public void setEquippedItem(ItemStack armorItem) {
        // Access to the current/parent object
        this.equippedItem = armorItem;
    // End of a block/expression
    }

    // Start of a method/block
    public EquipmentSlot getSlot() {
        // Returns a value to the caller
        return slot;
    // End of a block/expression
    }

    /**
     * Same as {@link #getEquippedItem()}.
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return equippedItem;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }
// End of a block/expression
}
