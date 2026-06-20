// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.InstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called when a chunk in an instance is unloaded.
 */
// Déclaration de type (classe/interface/enum/record)
public class InstanceChunkUnloadEvent implements InstanceEvent {

    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final Chunk chunk;

    // Début d'une méthode/d'un bloc
    public InstanceChunkUnloadEvent(Instance instance, Chunk chunk) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.chunk = chunk;
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
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    // Début d'une méthode/d'un bloc
    public int getChunkX() {
        // Renvoie une valeur à l'appelant
        return chunk.getChunkX();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    // Début d'une méthode/d'un bloc
    public int getChunkZ() {
        // Renvoie une valeur à l'appelant
        return chunk.getChunkZ();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chunk.
     *
     * @return the chunk.
     */
    // Début d'une méthode/d'un bloc
    public Chunk getChunk() {
        // Renvoie une valeur à l'appelant
        return chunk;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
