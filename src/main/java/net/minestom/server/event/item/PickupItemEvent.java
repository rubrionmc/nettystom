// Déclaration du paquet de ce fichier
package net.minestom.server.event.item;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.ItemEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Déclaration de type (classe/interface/enum/record)
public class PickupItemEvent implements EntityInstanceEvent, ItemEvent, CancellableEvent {

    // Instruction de code
    private final LivingEntity livingEntity;
    // Instruction de code
    private final ItemEntity itemEntity;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PickupItemEvent(LivingEntity livingEntity, ItemEntity itemEntity) {
        // Accès à l'objet courant/parent
        this.livingEntity = livingEntity;
        // Accès à l'objet courant/parent
        this.itemEntity = itemEntity;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LivingEntity getLivingEntity() {
        // Renvoie une valeur à l'appelant
        return livingEntity;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemEntity getItemEntity() {
        // Renvoie une valeur à l'appelant
        return itemEntity;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return getItemEntity().getItemStack();
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return livingEntity;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
