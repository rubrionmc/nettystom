// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called when a new instance is set for an entity.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntitySpawnEvent implements EntityInstanceEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final Instance spawnInstance;

    // Début d'une méthode/d'un bloc
    public EntitySpawnEvent(Entity entity, Instance spawnInstance) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.spawnInstance = spawnInstance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity who spawned in the instance.
     *
     * @return the entity
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity new instance.
     *
     * @return the instance
     */
    // Début d'une méthode/d'un bloc
    public Instance getSpawnInstance() {
        // Renvoie une valeur à l'appelant
        return spawnInstance;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
