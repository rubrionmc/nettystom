// Package declaration for this file
package net.minestom.server.utils.chunk;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Arrays;

/**
 * Allows to limit operations with recently operated chunks
 * <p>
 * {@link ChunkUpdateLimitChecker#historySize} defines how many last chunks will be remembered
 * to skip operations with them via {@link ChunkUpdateLimitChecker#addToHistory(Chunk)} returning {@code false}
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class ChunkUpdateLimitChecker {

    // Code statement
    private final int historySize;
    // Code statement
    private final long[] chunkHistory;

    // Start of a method/block
    public ChunkUpdateLimitChecker(int historySize) {
        // Access to the current/parent object
        this.historySize = Math.max(0, historySize);
        // Access to the current/parent object
        this.chunkHistory = new long[this.historySize];
        // Access to the current/parent object
        this.clearHistory();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isEnabled() {
        // Returns a value to the caller
        return historySize > 0;
    // End of a block/expression
    }

    /**
     * Adds the chunk to the history
     *
     * @param chunk chunk to add
     * @return {@code true} if it's a new chunk in the history
     */
    // Start of a method/block
    public boolean addToHistory(Chunk chunk) {
        // Branch: checks a condition
        if (!isEnabled()) {
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
        // Calls a method
        final long index = CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ());
        // Assigns a value
        boolean result = true;
        // Assigns a value
        final int lastIndex = historySize - 1;
        // Loop: repeats a block
        for (int i = 0; i <= lastIndex; i++) {
            // Branch: checks a condition
            if (chunkHistory[i] == index) {
                // Assigns a value
                result = false;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (i != lastIndex) {
                // Assigns a value
                chunkHistory[i] = chunkHistory[i + 1];
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Assigns a value
        chunkHistory[lastIndex] = index;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    public void clearHistory() {
        // Calls a method
        Arrays.fill(this.chunkHistory, Long.MAX_VALUE);
    // End of a block/expression
    }
// End of a block/expression
}
