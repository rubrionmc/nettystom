// Package declaration for this file
package net.minestom.server.event.instance;

// Import of a required class
import net.minestom.server.event.trait.InstanceEvent;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called when a chunk in an instance is unloaded.
 */
// Type declaration (class/interface/enum/record)
public class InstanceChunkUnloadEvent implements InstanceEvent {

    // Code statement
    private final Instance instance;
    // Code statement
    private final Chunk chunk;

    // Start of a method/block
    public InstanceChunkUnloadEvent(Instance instance, Chunk chunk) {
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.chunk = chunk;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Instance getInstance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    /**
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    // Start of a method/block
    public int getChunkX() {
        // Returns a value to the caller
        return chunk.getChunkX();
    // End of a block/expression
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    // Start of a method/block
    public int getChunkZ() {
        // Returns a value to the caller
        return chunk.getChunkZ();
    // End of a block/expression
    }

    /**
     * Gets the chunk.
     *
     * @return the chunk.
     */
    // Start of a method/block
    public Chunk getChunk() {
        // Returns a value to the caller
        return chunk;
    // End of a block/expression
    }
// End of a block/expression
}
