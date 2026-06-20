// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.InstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * This event is triggered when a section of an instance is manually marked as invalid.
 * <p>
 * Changes in this case are not known but indicate that its content must be reinterpreted.
 * <p>
 * Can be triggered using {@link Instance#invalidateSection(int, int, int)}
 */
// Déclaration de type (classe/interface/enum/record)
public class InstanceSectionInvalidateEvent implements InstanceEvent {
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final int sectionX, sectionY, sectionZ;

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public InstanceSectionInvalidateEvent(Instance instance, int sectionX, int sectionY, int sectionZ) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.sectionX = sectionX;
        // Accès à l'objet courant/parent
        this.sectionY = sectionY;
        // Accès à l'objet courant/parent
        this.sectionZ = sectionZ;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Instance getInstance() {
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int sectionX() {
        // Renvoie une valeur à l'appelant
        return sectionX;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int sectionY() {
        // Renvoie une valeur à l'appelant
        return sectionY;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int sectionZ() {
        // Renvoie une valeur à l'appelant
        return sectionZ;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
