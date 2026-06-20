// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.generator.Generator;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.CompletableFuture;

/**
 * The {@link SharedInstance} is an instance that shares the same chunks as its linked {@link InstanceContainer},
 * entities are separated.
 */
// Type declaration (class/interface/enum/record)
public class SharedInstance extends Instance {
    // Code statement
    private final InstanceContainer instanceContainer;

    // Start of a method/block
    public SharedInstance(UUID uuid, InstanceContainer instanceContainer) {
        // Access to the current/parent object
        super(uuid, instanceContainer.getDimensionType());
        // Access to the current/parent object
        this.instanceContainer = instanceContainer;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block, boolean doBlockUpdates) {
        // Access to the current/parent object
        this.instanceContainer.setBlock(x, y, z, block, doBlockUpdates);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean placeBlock(BlockHandler.Placement placement, boolean doBlockUpdates) {
        // Returns a value to the caller
        return instanceContainer.placeBlock(placement, doBlockUpdates);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace, boolean doBlockUpdates) {
        // Returns a value to the caller
        return instanceContainer.breakBlock(player, blockPosition, blockFace, doBlockUpdates);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Chunk> loadChunk(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return instanceContainer.loadChunk(chunkX, chunkZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Chunk> loadOptionalChunk(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return instanceContainer.loadOptionalChunk(chunkX, chunkZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void unloadChunk(Chunk chunk) {
        // Calls a method
        instanceContainer.unloadChunk(chunk);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Chunk getChunk(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return instanceContainer.getChunk(chunkX, chunkZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> saveInstance() {
        // Returns a value to the caller
        return instanceContainer.saveInstance();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> saveChunkToStorage(Chunk chunk) {
        // Returns a value to the caller
        return instanceContainer.saveChunkToStorage(chunk);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> saveChunksToStorage() {
        // Returns a value to the caller
        return instanceContainer.saveChunksToStorage();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setChunkSupplier(ChunkSupplier chunkSupplier) {
        // Calls a method
        instanceContainer.setChunkSupplier(chunkSupplier);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ChunkSupplier getChunkSupplier() {
        // Returns a value to the caller
        return instanceContainer.getChunkSupplier();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Generator generator() {
        // Returns a value to the caller
        return instanceContainer.generator();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setGenerator(@Nullable Generator generator) {
        // Calls a method
        instanceContainer.setGenerator(generator);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> generateChunk(int chunkX, int chunkZ, Generator generator) {
        // Returns a value to the caller
        return instanceContainer.generateChunk(chunkX, chunkZ, generator);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Chunk> getChunks() {
        // Returns a value to the caller
        return instanceContainer.getChunks();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void enableAutoChunkLoad(boolean enable) {
        // Calls a method
        instanceContainer.enableAutoChunkLoad(enable);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean hasEnabledAutoChunkLoad() {
        // Returns a value to the caller
        return instanceContainer.hasEnabledAutoChunkLoad();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isInVoid(Point point) {
        // Returns a value to the caller
        return instanceContainer.isInVoid(point);
    // End of a block/expression
    }

    /**
     * Gets the {@link InstanceContainer} from where this instance takes its chunks from.
     *
     * @return the associated {@link InstanceContainer}
     */
    // Start of a method/block
    public InstanceContainer getInstanceContainer() {
        // Returns a value to the caller
        return instanceContainer;
    // End of a block/expression
    }

    /**
     * Gets if two instances share the same chunks.
     *
     * @param instance1 the first instance
     * @param instance2 the second instance
     * @return true if the two instances share the same chunks
     */
    // Start of a method/block
    public static boolean areLinked(Instance instance1, Instance instance2) {
        // SharedInstance check
        // Branch: checks a condition
        if (instance1 instanceof InstanceContainer && instance2 instanceof SharedInstance) {
            // Returns a value to the caller
            return ((SharedInstance) instance2).getInstanceContainer().equals(instance1);
        // Branch: checks a condition
        } else if (instance2 instanceof InstanceContainer && instance1 instanceof SharedInstance) {
            // Returns a value to the caller
            return ((SharedInstance) instance1).getInstanceContainer().equals(instance2);
        // Branch: checks a condition
        } else if (instance1 instanceof SharedInstance && instance2 instanceof SharedInstance) {
            // Calls a method
            final InstanceContainer container1 = ((SharedInstance) instance1).getInstanceContainer();
            // Calls a method
            final InstanceContainer container2 = ((SharedInstance) instance2).getInstanceContainer();
            // Returns a value to the caller
            return container1.equals(container2);
        // End of a block/expression
        }

        // InstanceContainer check (copied from)
        // Branch: checks a condition
        if (instance1 instanceof InstanceContainer container1 && instance2 instanceof InstanceContainer container2) {
            // Branch: checks a condition
            if (container1.getSrcInstance() != null) {
                // Returns a value to the caller
                return container1.getSrcInstance().equals(container2)
                        // Calls a method
                        && container1.getLastBlockChangeTime() == container2.getLastBlockChangeTime();
            // Branch: checks a condition
            } else if (container2.getSrcInstance() != null) {
                // Returns a value to the caller
                return container2.getSrcInstance().equals(container1)
                        // Calls a method
                        && container2.getLastBlockChangeTime() == container1.getLastBlockChangeTime();
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
