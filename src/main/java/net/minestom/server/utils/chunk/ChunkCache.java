// Déclaration du paquet de ce fichier
package net.minestom.server.utils.chunk;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class ChunkCache implements Block.Getter {
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private @Nullable Chunk chunk;

    // Instruction de code
    private final @Nullable Block defaultBlock;

    // Instruction de code
    public ChunkCache(Instance instance, @Nullable Chunk chunk,
                      // Annotation pour l'élément suivant
                      @Nullable Block defaultBlock) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.chunk = chunk;
        // Accès à l'objet courant/parent
        this.defaultBlock = defaultBlock;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ChunkCache(Instance instance, @Nullable Chunk chunk) {
        // Appelle une méthode
        this(instance, chunk, Block.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
        // Affecte une valeur
        Chunk chunk = this.chunk;
        // Appelle une méthode
        final int chunkX = CoordConversion.globalToChunk(x);
        // Appelle une méthode
        final int chunkZ = CoordConversion.globalToChunk(z);
        // Embranchement : vérifie une condition
        if (chunk == null || !chunk.isLoaded() ||
                // Début d'une méthode/d'un bloc
                chunk.getChunkX() != chunkX || chunk.getChunkZ() != chunkZ) {
            // Accès à l'objet courant/parent
            this.chunk = chunk = this.instance.getChunk(chunkX, chunkZ);
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (chunk != null) {
            // Appelle une méthode
            chunk.lockReadLock();
            // Gestion des exceptions
            try {
                // Renvoie une valeur à l'appelant
                return chunk.getBlock(x, y, z, condition);
            // Début d'une méthode/d'un bloc
            } finally {
                // Appelle une méthode
                chunk.unlockReadLock();
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else return defaultBlock;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
