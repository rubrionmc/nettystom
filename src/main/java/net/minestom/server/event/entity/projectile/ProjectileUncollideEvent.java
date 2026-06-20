// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity.projectile;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

// Déclaration de type (classe/interface/enum/record)
public final class ProjectileUncollideEvent implements EntityInstanceEvent {

    // Instruction de code
    private final Entity projectile;

    // Début d'une méthode/d'un bloc
    public ProjectileUncollideEvent(Entity projectile) {
        // Accès à l'objet courant/parent
        this.projectile = projectile;
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

// Fin d'un bloc/d'une expression
}
