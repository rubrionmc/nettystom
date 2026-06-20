// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.potion.Potion;

// Déclaration de type (classe/interface/enum/record)
public class EntityPotionRemoveEvent implements EntityInstanceEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final Potion potion;

    // Début d'une méthode/d'un bloc
    public EntityPotionRemoveEvent(Entity entity, Potion potion) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.potion = potion;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the potion that was removed.
     *
     * @return the removed potion.
     */
    // Début d'une méthode/d'un bloc
    public Potion getPotion() {
        // Renvoie une valeur à l'appelant
        return potion;
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
