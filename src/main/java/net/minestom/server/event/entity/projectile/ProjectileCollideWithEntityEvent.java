// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity.projectile;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;

// Déclaration de type (classe/interface/enum/record)
public final class ProjectileCollideWithEntityEvent extends ProjectileCollideEvent {

    // Instruction de code
    private final Entity target;

    // Instruction de code
    public ProjectileCollideWithEntityEvent(
            // Instruction de code
            Entity projectile,
            // Instruction de code
            Pos position,
            // Instruction de code
            Entity target
    // Début d'une méthode/d'un bloc
    ) {
        // Accès à l'objet courant/parent
        super(projectile, position);
        // Accès à l'objet courant/parent
        this.target = target;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return target;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
