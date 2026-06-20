// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

/**
 * Represents an {@link Entity} which can have {@link ItemStack} in hands and armor slots.
 */
// Type declaration (class/interface/enum/record)
public interface EquipmentHandler {

    /**
     * Gets the equipment in a specific slot.
     *
     * @param slot the equipment to get the item from
     * @return the equipment {@link ItemStack}
     */
    // Calls a method
    ItemStack getEquipment(EquipmentSlot slot);

    // Calls a method
    void setEquipment(EquipmentSlot slot, ItemStack itemStack);

    /**
     * Gets the {@link ItemStack} in main hand.
     *
     * @return the {@link ItemStack} in main hand
     */
    // Start of a method/block
    default ItemStack getItemInMainHand() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.MAIN_HAND);
    // End of a block/expression
    }

    /**
     * Changes the main hand {@link ItemStack}.
     *
     * @param itemStack the main hand {@link ItemStack}
     */
    // Start of a method/block
    default void setItemInMainHand(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.MAIN_HAND, itemStack);
    // End of a block/expression
    }

    /**
     * Gets the {@link ItemStack} in off hand.
     *
     * @return the item in off hand
     */
    // Start of a method/block
    default ItemStack getItemInOffHand() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.OFF_HAND);
    // End of a block/expression
    }

    /**
     * Changes the off hand {@link ItemStack}.
     *
     * @param itemStack the off hand {@link ItemStack}
     */
    // Start of a method/block
    default void setItemInOffHand(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.OFF_HAND, itemStack);
    // End of a block/expression
    }

    /**
     * Gets the {@link ItemStack} in the specific hand.
     *
     * @param hand the Hand to get the {@link ItemStack} from
     * @return the {@link ItemStack} in {@code hand}
     */
    // Start of a method/block
    default ItemStack getItemInHand(PlayerHand hand) {
        // Returns a value to the caller
        return switch (hand) {
            // Multiple branching (switch/case)
            case MAIN -> getItemInMainHand();
            // Multiple branching (switch/case)
            case OFF -> getItemInOffHand();
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Changes the {@link ItemStack} in the specific hand.
     *
     * @param hand  the hand to set the item to
     * @param stack the {@link ItemStack} to set
     */
    // Start of a method/block
    default void setItemInHand(PlayerHand hand, ItemStack stack) {
        // Multiple branching (switch/case)
        switch (hand) {
            // Multiple branching (switch/case)
            case MAIN -> setItemInMainHand(stack);
            // Multiple branching (switch/case)
            case OFF -> setItemInOffHand(stack);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the helmet.
     *
     * @return the helmet
     */
    // Start of a method/block
    default ItemStack getHelmet() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.HELMET);
    // End of a block/expression
    }

    /**
     * Changes the helmet.
     *
     * @param itemStack the helmet
     */
    // Start of a method/block
    default void setHelmet(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.HELMET, itemStack);
    // End of a block/expression
    }

    /**
     * Gets the chestplate.
     *
     * @return the chestplate
     */
    // Start of a method/block
    default ItemStack getChestplate() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.CHESTPLATE);
    // End of a block/expression
    }

    /**
     * Changes the chestplate.
     *
     * @param itemStack the chestplate
     */
    // Start of a method/block
    default void setChestplate(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.CHESTPLATE, itemStack);
    // End of a block/expression
    }

    /**
     * Gets the leggings.
     *
     * @return the leggings
     */
    // Start of a method/block
    default ItemStack getLeggings() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.LEGGINGS);
    // End of a block/expression
    }

    /**
     * Changes the leggings.
     *
     * @param itemStack the leggings
     */
    // Start of a method/block
    default void setLeggings(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.LEGGINGS, itemStack);
    // End of a block/expression
    }

    /**
     * Gets the boots.
     *
     * @return the boots
     */
    // Start of a method/block
    default ItemStack getBoots() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.BOOTS);
    // End of a block/expression
    }

    /**
     * Changes the boots.
     *
     * @param itemStack the boots
     */
    // Start of a method/block
    default void setBoots(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.BOOTS, itemStack);
    // End of a block/expression
    }

    /**
     * Gets the body equipment. Used by horses, wolves, and llama's.
     *
     * @return the body equipment
     */
    // Start of a method/block
    default ItemStack getBodyEquipment() {
        // Returns a value to the caller
        return getEquipment(EquipmentSlot.BODY);
    // End of a block/expression
    }

    /**
     * Changes the body equipment. Used by horses, wolves, and llama's.
     *
     * @param itemStack the body equipment
     */
    // Start of a method/block
    default void setBodyEquipment(ItemStack itemStack) {
        // Calls a method
        setEquipment(EquipmentSlot.BODY, itemStack);
    // End of a block/expression
    }

    // Start of a method/block
    default boolean hasEquipment(EquipmentSlot slot) {
        // Returns a value to the caller
        return !getEquipment(slot).isAir();
    // End of a block/expression
    }

    /**
     * Sends a specific equipment to viewers.
     *
     * @param slot the slot of the equipment
     */
    // Start of a method/block
    default void syncEquipment(EquipmentSlot slot) {
        // Calls a method
        syncEquipment(slot, getEquipment(slot));
    // End of a block/expression
    }

    // Start of a method/block
    default void syncEquipment(EquipmentSlot slot, ItemStack stack) {
        // Calls a method
        Check.stateCondition(!(this instanceof Entity), "Only accessible for Entity");

        // Calls a method
        Entity entity = (Entity) this;
        // Calls a method
        entity.sendPacketToViewers(new EntityEquipmentPacket(entity.getEntityId(), Map.of(slot, stack)));
    // End of a block/expression
    }

    /**
     * Gets the packet with all the equipments.
     *
     * @return the packet with the equipments
     * @throws IllegalStateException if 'this' is not an {@link Entity}
     */
    // Start of a method/block
    default EntityEquipmentPacket getEquipmentsPacket() {
        // Calls a method
        Check.stateCondition(!(this instanceof Entity), "Only accessible for Entity");
        // Calls a method
        Map<EquipmentSlot, ItemStack> equipment = new HashMap<>();
        // Loop: repeats a block
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            // Calls a method
            equipment.put(slot, this.getEquipment(slot));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new EntityEquipmentPacket(((Entity) this).getEntityId(), equipment);
    // End of a block/expression
    }

// End of a block/expression
}
