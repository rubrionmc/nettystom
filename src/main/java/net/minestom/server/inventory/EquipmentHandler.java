// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

/**
 * Represents an {@link Entity} which can have {@link ItemStack} in hands and armor slots.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EquipmentHandler {

    /**
     * Gets the equipment in a specific slot.
     *
     * @param slot the equipment to get the item from
     * @return the equipment {@link ItemStack}
     */
    // Appelle une méthode
    ItemStack getEquipment(EquipmentSlot slot);

    // Appelle une méthode
    void setEquipment(EquipmentSlot slot, ItemStack itemStack);

    /**
     * Gets the {@link ItemStack} in main hand.
     *
     * @return the {@link ItemStack} in main hand
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getItemInMainHand() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.MAIN_HAND);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the main hand {@link ItemStack}.
     *
     * @param itemStack the main hand {@link ItemStack}
     */
    // Début d'une méthode/d'un bloc
    default void setItemInMainHand(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.MAIN_HAND, itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ItemStack} in off hand.
     *
     * @return the item in off hand
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getItemInOffHand() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.OFF_HAND);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the off hand {@link ItemStack}.
     *
     * @param itemStack the off hand {@link ItemStack}
     */
    // Début d'une méthode/d'un bloc
    default void setItemInOffHand(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.OFF_HAND, itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ItemStack} in the specific hand.
     *
     * @param hand the Hand to get the {@link ItemStack} from
     * @return the {@link ItemStack} in {@code hand}
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getItemInHand(PlayerHand hand) {
        // Renvoie une valeur à l'appelant
        return switch (hand) {
            // Embranchement multiple (switch/case)
            case MAIN -> getItemInMainHand();
            // Embranchement multiple (switch/case)
            case OFF -> getItemInOffHand();
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link ItemStack} in the specific hand.
     *
     * @param hand  the hand to set the item to
     * @param stack the {@link ItemStack} to set
     */
    // Début d'une méthode/d'un bloc
    default void setItemInHand(PlayerHand hand, ItemStack stack) {
        // Embranchement multiple (switch/case)
        switch (hand) {
            // Embranchement multiple (switch/case)
            case MAIN -> setItemInMainHand(stack);
            // Embranchement multiple (switch/case)
            case OFF -> setItemInOffHand(stack);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the helmet.
     *
     * @return the helmet
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getHelmet() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.HELMET);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the helmet.
     *
     * @param itemStack the helmet
     */
    // Début d'une méthode/d'un bloc
    default void setHelmet(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.HELMET, itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chestplate.
     *
     * @return the chestplate
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getChestplate() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.CHESTPLATE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the chestplate.
     *
     * @param itemStack the chestplate
     */
    // Début d'une méthode/d'un bloc
    default void setChestplate(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.CHESTPLATE, itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the leggings.
     *
     * @return the leggings
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getLeggings() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.LEGGINGS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the leggings.
     *
     * @param itemStack the leggings
     */
    // Début d'une méthode/d'un bloc
    default void setLeggings(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.LEGGINGS, itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the boots.
     *
     * @return the boots
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getBoots() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.BOOTS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the boots.
     *
     * @param itemStack the boots
     */
    // Début d'une méthode/d'un bloc
    default void setBoots(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.BOOTS, itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the body equipment. Used by horses, wolves, and llama's.
     *
     * @return the body equipment
     */
    // Début d'une méthode/d'un bloc
    default ItemStack getBodyEquipment() {
        // Renvoie une valeur à l'appelant
        return getEquipment(EquipmentSlot.BODY);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the body equipment. Used by horses, wolves, and llama's.
     *
     * @param itemStack the body equipment
     */
    // Début d'une méthode/d'un bloc
    default void setBodyEquipment(ItemStack itemStack) {
        // Appelle une méthode
        setEquipment(EquipmentSlot.BODY, itemStack);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean hasEquipment(EquipmentSlot slot) {
        // Renvoie une valeur à l'appelant
        return !getEquipment(slot).isAir();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a specific equipment to viewers.
     *
     * @param slot the slot of the equipment
     */
    // Début d'une méthode/d'un bloc
    default void syncEquipment(EquipmentSlot slot) {
        // Appelle une méthode
        syncEquipment(slot, getEquipment(slot));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void syncEquipment(EquipmentSlot slot, ItemStack stack) {
        // Appelle une méthode
        Check.stateCondition(!(this instanceof Entity), "Only accessible for Entity");

        // Affecte une valeur
        Entity entity = (Entity) this;
        // Appelle une méthode
        entity.sendPacketToViewers(new EntityEquipmentPacket(entity.getEntityId(), Map.of(slot, stack)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the packet with all the equipments.
     *
     * @return the packet with the equipments
     * @throws IllegalStateException if 'this' is not an {@link Entity}
     */
    // Début d'une méthode/d'un bloc
    default EntityEquipmentPacket getEquipmentsPacket() {
        // Appelle une méthode
        Check.stateCondition(!(this instanceof Entity), "Only accessible for Entity");
        // Affecte une valeur
        Map<EquipmentSlot, ItemStack> equipment = new HashMap<>();
        // Boucle : répète un bloc
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            // Appelle une méthode
            equipment.put(slot, this.getEquipment(slot));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new EntityEquipmentPacket(((Entity) this).getEntityId(), equipment);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
