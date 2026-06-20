// Déclaration du paquet de ce fichier
package net.minestom.server.utils.chunk;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Arrays;

/**
 * Allows to limit operations with recently operated chunks
 * <p>
 * {@link ChunkUpdateLimitChecker#historySize} defines how many last chunks will be remembered
 * to skip operations with them via {@link ChunkUpdateLimitChecker#addToHistory(Chunk)} returning {@code false}
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class ChunkUpdateLimitChecker {

    // Instruction de code
    private final int historySize;
    // Instruction de code
    private final long[] chunkHistory;

    // Début d'une méthode/d'un bloc
    public ChunkUpdateLimitChecker(int historySize) {
        // Accès à l'objet courant/parent
        this.historySize = Math.max(0, historySize);
        // Accès à l'objet courant/parent
        this.chunkHistory = new long[this.historySize];
        // Accès à l'objet courant/parent
        this.clearHistory();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isEnabled() {
        // Renvoie une valeur à l'appelant
        return historySize > 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the chunk to the history
     *
     * @param chunk chunk to add
     * @return {@code true} if it's a new chunk in the history
     */
    // Début d'une méthode/d'un bloc
    public boolean addToHistory(Chunk chunk) {
        // Embranchement : vérifie une condition
        if (!isEnabled()) {
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final long index = CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ());
        // Affecte une valeur
        boolean result = true;
        // Affecte une valeur
        final int lastIndex = historySize - 1;
        // Boucle : répète un bloc
        for (int i = 0; i <= lastIndex; i++) {
            // Embranchement : vérifie une condition
            if (chunkHistory[i] == index) {
                // Affecte une valeur
                result = false;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (i != lastIndex) {
                // Affecte une valeur
                chunkHistory[i] = chunkHistory[i + 1];
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        chunkHistory[lastIndex] = index;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void clearHistory() {
        // Appelle une méthode
        Arrays.fill(this.chunkHistory, Long.MAX_VALUE);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
