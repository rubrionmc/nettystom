// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.InstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called when an instance processes a tick.
 */
// Déclaration de type (classe/interface/enum/record)
public class InstanceTickEvent implements InstanceEvent {

    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final int duration;

    // Début d'une méthode/d'un bloc
    public InstanceTickEvent(Instance instance, long time, long lastTickAge) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.duration = (int) (time - lastTickAge);
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

    /**
     * Gets the duration of the tick in ms.
     *
     * @return the duration
     */
    // Début d'une méthode/d'un bloc
    public int getDuration() {
        // Renvoie une valeur à l'appelant
        return duration;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}