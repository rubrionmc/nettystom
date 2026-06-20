// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity.projectile;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.RecursiveEvent;

// Déclaration de type (classe/interface/enum/record)
class ProjectileCollideEvent implements EntityInstanceEvent, CancellableEvent, RecursiveEvent {

    // Instruction de code
    private final Entity projectile;
    // Instruction de code
    private final Pos position;
    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    protected ProjectileCollideEvent(Entity projectile, Pos position) {
        // Accès à l'objet courant/parent
        this.projectile = projectile;
        // Accès à l'objet courant/parent
        this.position = position;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return projectile;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Pos getCollisionPosition() {
        // Renvoie une valeur à l'appelant
        return position;
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
        // Affecte une valeur
        cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
