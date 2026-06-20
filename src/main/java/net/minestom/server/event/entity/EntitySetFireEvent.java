// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

// Déclaration de type (classe/interface/enum/record)
public class EntitySetFireEvent implements EntityInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private int ticks;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public EntitySetFireEvent(Entity entity, int ticks) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.ticks = ticks;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getFireTicks() {
        // Renvoie une valeur à l'appelant
        return ticks;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFireTicks(int ticks) {
        // Accès à l'objet courant/parent
        this.ticks = ticks;
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
        return entity;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
