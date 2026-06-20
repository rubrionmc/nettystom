// Déclaration du paquet de ce fichier
package net.minestom.server.event.item;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Déclaration de type (classe/interface/enum/record)
public class EntityEquipEvent implements EntityInstanceEvent, ItemEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private ItemStack equippedItem;
    // Instruction de code
    private final EquipmentSlot slot;

    // Début d'une méthode/d'un bloc
    public EntityEquipEvent(Entity entity, ItemStack equippedItem, EquipmentSlot slot) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.equippedItem = equippedItem;
        // Accès à l'objet courant/parent
        this.slot = slot;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getEquippedItem() {
        // Renvoie une valeur à l'appelant
        return equippedItem;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setEquippedItem(ItemStack armorItem) {
        // Accès à l'objet courant/parent
        this.equippedItem = armorItem;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EquipmentSlot getSlot() {
        // Renvoie une valeur à l'appelant
        return slot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Same as {@link #getEquippedItem()}.
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return equippedItem;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
