// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
record NoopChunkLoaderImpl() implements ChunkLoader {
    // Appelle une méthode
    static final NoopChunkLoaderImpl INSTANCE = new NoopChunkLoaderImpl();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Chunk loadChunk(Instance instance, int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void saveChunk(Chunk chunk) {
        // Empty
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
