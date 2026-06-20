// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

// Déclaration de type (classe/interface/enum/record)
public class EntityDeathEvent implements EntityInstanceEvent {

    // TODO cause
    // Instruction de code
    private final Entity entity;

    // Début d'une méthode/d'un bloc
    public EntityDeathEvent(Entity entity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
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
