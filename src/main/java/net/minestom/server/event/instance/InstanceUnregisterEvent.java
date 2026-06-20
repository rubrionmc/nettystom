// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.InstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called when an instance is unregistered
 */
// Déclaration de type (classe/interface/enum/record)
public class InstanceUnregisterEvent implements InstanceEvent {
    // Instruction de code
    private final Instance instance;

    // Début d'une méthode/d'un bloc
    public InstanceUnregisterEvent(Instance instance) {
        // Accès à l'objet courant/parent
        this.instance = instance;
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
// Fin d'un bloc/d'une expression
}
