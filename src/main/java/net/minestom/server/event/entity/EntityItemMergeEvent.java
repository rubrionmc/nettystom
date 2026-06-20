// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.ItemEntity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called when two {@link ItemEntity} are merging their {@link ItemStack} together to form a sole entity.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityItemMergeEvent implements EntityInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final ItemEntity merged;
    // Instruction de code
    private ItemStack result;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public EntityItemMergeEvent(ItemEntity source, ItemEntity merged, ItemStack result) {
        // Accès à l'objet courant/parent
        this.entity = source;
        // Accès à l'objet courant/parent
        this.merged = merged;
        // Accès à l'objet courant/parent
        this.result = result;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ItemEntity} who is receiving {@link #getMerged()}.
     * <p>
     * This can be used to get the final ItemEntity position.
     *
     * @return the source ItemEntity
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemEntity getEntity() {
        // Renvoie une valeur à l'appelant
        return (ItemEntity) entity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity who will be merged.
     * <p>
     * This entity will be removed after the event.
     *
     * @return the merged ItemEntity
     */
    // Début d'une méthode/d'un bloc
    public ItemEntity getMerged() {
        // Renvoie une valeur à l'appelant
        return merged;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the final item stack on the ground.
     *
     * @return the item stack
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getResult() {
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the item stack which will appear on the ground.
     *
     * @param result the new item stack
     */
    // Début d'une méthode/d'un bloc
    public void setResult(ItemStack result) {
        // Accès à l'objet courant/parent
        this.result = result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
