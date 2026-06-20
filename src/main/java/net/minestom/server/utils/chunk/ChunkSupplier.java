// Déclaration du paquet de ce fichier
package net.minestom.server.utils.chunk;

// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Used to customize which type of {@link Chunk} an implementation should use.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface ChunkSupplier {

    /**
     * Creates a {@link Chunk} object.
     *
     * @param instance the linked instance
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @return a newly {@link Chunk} object, cannot be null
     */
    // Appelle une méthode
    Chunk createChunk(Instance instance, int chunkX, int chunkZ);
// Fin d'un bloc/d'une expression
}
