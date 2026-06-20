// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.generator.Generator;
// Import of a required class
import net.minestom.server.instance.heightmap.Heightmap;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.snapshot.Snapshotable;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.Taggable;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.locks.ReentrantReadWriteLock;
// Import of a required class
import java.util.function.Supplier;

// TODO light data & API

/**
 * A chunk is a part of an {@link Instance}, limited by a size of 16x256x16 blocks and subdivided in 16 sections of 16 blocks height.
 * Should contain all the blocks located at those positions and manage their tick updates.
 * Be aware that implementations do not need to be thread-safe, all chunks are guarded by their own instance ('this').
 * <p>
 * You can create your own implementation of this class by extending it
 * and create the objects in {@link InstanceContainer#setChunkSupplier(ChunkSupplier)}.
 * <p>
 * You generally want to avoid storing references of this object as this could lead to a huge memory leak,
 * you should store the chunk coordinates instead.
 */
// Type declaration (class/interface/enum/record)
public abstract class Chunk implements Block.Getter, Block.Setter, Biome.Getter, Biome.Setter, Viewable, Tickable, Taggable, Snapshotable {
    // Assigns a value
    public static final int CHUNK_SIZE_X = 16;
    // Assigns a value
    public static final int CHUNK_SIZE_Z = 16;
    // Assigns a value
    public static final int CHUNK_SECTION_SIZE = 16;

    // Code statement
    private final UUID identifier;
    // Calls a method
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Code statement
    protected Instance instance;
    // Code statement
    protected final int chunkX, chunkZ;
    // Code statement
    protected final int minSection, maxSection;

    // Options
    // Code statement
    private final boolean shouldGenerate;
    // Code statement
    private boolean readOnly;

    // Assigns a value
    protected volatile boolean loaded = true;
    // Code statement
    private final Viewable viewable;

    // Data
    // Calls a method
    private final TagHandler tagHandler = TagHandler.newHandler();

    // Start of a method/block
    public Chunk(Instance instance, int chunkX, int chunkZ, boolean shouldGenerate) {
        // Access to the current/parent object
        this.identifier = UUID.randomUUID();
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.chunkX = chunkX;
        // Access to the current/parent object
        this.chunkZ = chunkZ;
        // Access to the current/parent object
        this.shouldGenerate = shouldGenerate;
        // Calls a method
        final DimensionType instanceDim = instance.getCachedDimensionType();
        // Access to the current/parent object
        this.minSection = instanceDim.minY() / CHUNK_SECTION_SIZE;
        // Access to the current/parent object
        this.maxSection = (instanceDim.minY() + instanceDim.height()) / CHUNK_SECTION_SIZE;
        // Assigns a value
        final List<SharedInstance> shared = instance instanceof InstanceContainer instanceContainer ?
                // Calls a method
                instanceContainer.getSharedInstances() : List.of();
        // Access to the current/parent object
        this.viewable = instance.getEntityTracker().viewable(shared, chunkX, chunkZ);
    // End of a block/expression
    }

    /**
     * Sets a block at a position.
     * <p>
     * This is used when the previous block has to be destroyed/replaced, meaning that it clears the previous data and update method.
     * <p>
     * WARNING: this method is not thread-safe (in order to bring performance improvement with {@link net.minestom.server.instance.batch.Batch batches})
     * The thread-safe version is {@link Instance#setBlock(int, int, int, Block)} (or any similar instance methods)
     * Otherwise, remember to have this chunk {@link #lockWriteLock() locked} when this is called.
     *
     * @param x     the block X
     * @param y     the block Y
     * @param z     the block Z
     * @param block the block to place
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block) {
        // Calls a method
        assertWriteLock();
        // Calls a method
        setBlock(x, y, z, block, null, null);
    // End of a block/expression
    }

    // Code statement
    protected abstract void setBlock(int x, int y, int z, Block block,
                                     // Annotation for the following element
                                     @Nullable BlockHandler.Placement placement,
                                     // Annotation for the following element
                                     @Nullable BlockHandler.Destroy destroy);

    // Calls a method
    public abstract List<Section> getSections();

    // Calls a method
    public abstract Section getSection(int section);

    // Calls a method
    public abstract Heightmap motionBlockingHeightmap();
    // Calls a method
    public abstract Heightmap worldSurfaceHeightmap();
    // Calls a method
    public abstract void loadHeightmapsFromNBT(CompoundBinaryTag heightmaps);

    // Start of a method/block
    public Section getSectionAt(int blockY) {
        // Returns a value to the caller
        return getSection(CoordConversion.globalToChunk(blockY));
    // End of a block/expression
    }

    /**
     * Executes a chunk tick.
     * <p>
     * Should be used to update all the blocks in the chunk.
     * <p>
     * WARNING: this method doesn't necessary have to be thread-safe, proceed with caution.
     *
     * @param time the time of the update in milliseconds
     */
    // Annotation for the following element
    @Override
    // Calls a method
    public abstract void tick(long time);

    /**
     * Sends the chunk data to {@code player}.
     *
     * @param player the player
     */
    // Start of a method/block
    public void sendChunk(Player player) {
        // Calls a method
        player.sendChunk(this);
    // End of a block/expression
    }

    // Start of a method/block
    public void sendChunk() {
        // Calls a method
        getViewers().forEach(this::sendChunk);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    public abstract SendablePacket getFullDataPacket();

    /**
     * Creates a copy of this chunk, including blocks state id, custom block id, biomes, update data.
     * <p>
     * The chunk position (X/Z) can be modified using the given arguments.
     *
     * @param instance the chunk owner
     * @param chunkX   the chunk X of the copy
     * @param chunkZ   the chunk Z of the copy
     * @return a copy of this chunk with a potentially new instance and position
     */
    // Calls a method
    public abstract Chunk copy(Instance instance, int chunkX, int chunkZ);

    /**
     * Resets the chunk, this means clearing all the data making it empty.
     */
    // Calls a method
    public abstract void reset();

    /**
     * Gets the unique identifier of this chunk.
     * <p>
     * WARNING: this UUID is not persistent but randomized once the object is instantiated.
     *
     * @return the chunk identifier
     */
    // Start of a method/block
    public UUID getIdentifier() {
        // Returns a value to the caller
        return identifier;
    // End of a block/expression
    }

    /**
     * Gets the instance where this chunk is stored
     *
     * @return the linked instance
     */
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
        return chunkX;
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
        return chunkZ;
    // End of a block/expression
    }

    /**
     * Gets the lowest (inclusive) section Y available in this chunk
     *
     * @return the lowest (inclusive) section Y available in this chunk
     */
    // Start of a method/block
    public int getMinSection() {
        // Returns a value to the caller
        return minSection;
    // End of a block/expression
    }

    /**
     * Gets the highest (exclusive) section Y available in this chunk
     *
     * @return the highest (exclusive) section Y available in this chunk
     */
    // Start of a method/block
    public int getMaxSection() {
        // Returns a value to the caller
        return maxSection;
    // End of a block/expression
    }

    /**
     * Gets the world position of this chunk.
     *
     * @return the position of this chunk
     */
    // Start of a method/block
    public Point toPosition() {
        // Returns a value to the caller
        return new Vec(CHUNK_SIZE_X * getChunkX(), 0, CHUNK_SIZE_Z * getChunkZ());
    // End of a block/expression
    }

    /**
     * Gets if this chunk will or had been loaded with a {@link Generator}.
     * <p>
     * If false, the chunk will be entirely empty when loaded.
     *
     * @return true if this chunk is affected by a {@link Generator}
     */
    // Start of a method/block
    public boolean shouldGenerate() {
        // Returns a value to the caller
        return shouldGenerate;
    // End of a block/expression
    }

    /**
     * Gets if this chunk is read-only.
     * <p>
     * Being read-only should prevent block placing/breaking and setting block from an {@link Instance}.
     * It does not affect {@link ChunkLoader} and {@link Generator}.
     *
     * @return true if the chunk is read-only
     */
    // Start of a method/block
    public boolean isReadOnly() {
        // Returns a value to the caller
        return readOnly;
    // End of a block/expression
    }

    /**
     * Changes the read state of the chunk.
     * <p>
     * Being read-only should prevent block placing/breaking and setting block from an {@link Instance}.
     * It does not affect {@link ChunkLoader} and {@link Generator}.
     *
     * @param readOnly true to make the chunk read-only, false otherwise
     */
    // Start of a method/block
    public void setReadOnly(boolean readOnly) {
        // Calls a method
        assertWriteLock();
        // Access to the current/parent object
        this.readOnly = readOnly;
    // End of a block/expression
    }

    /**
     * Used to verify if the chunk should still be kept in memory.
     *
     * @return true if the chunk is loaded
     */
    // Start of a method/block
    public boolean isLoaded() {
        // Returns a value to the caller
        return loaded;
    // End of a block/expression
    }

    /**
     * Called when the chunk has been successfully loaded.
     */
    // Code statement
    protected void onLoad() {}

    /**
     * Called when the chunk generator has finished generating the chunk.
     */
    // Code statement
    public void onGenerate() {}

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return getClass().getSimpleName() + "[" + chunkX + ":" + chunkZ + "]";
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addViewer(Player player) {
        // Returns a value to the caller
        return viewable.addViewer(player);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeViewer(Player player) {
        // Returns a value to the caller
        return viewable.removeViewer(player);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return viewable.getViewers();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TagHandler tagHandler() {
        // Returns a value to the caller
        return tagHandler;
    // End of a block/expression
    }

    /**
     * Sets the chunk as "unloaded".
     */
    // Start of a method/block
    protected void unload() {
        // Access to the current/parent object
        this.loaded = false;
    // End of a block/expression
    }

    /**
     * Invalidate the chunk caches
     */
    // Calls a method
    public abstract void invalidate();

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected final void assertWriteLock() {
        // Calls a method
        assert holdsWriteLock() : "Not holding write-lock for chunk " + chunkX + "," + chunkZ;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected final void assertReadLock() {
        // Calls a method
        assert holdsReadLock() : "Not holding read-lock for chunk " + chunkX + "," + chunkZ;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public final void lockWriteLock() {
        // Calls a method
        assert holdsWriteLock() || lock.getReadHoldCount() == 0 : "Cannot acquire write-lock while holding read-lock for chunk " + chunkX + "," + chunkZ;
        // Calls a method
        lock.writeLock().lock();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public final void unlockWriteLock() {
        // Calls a method
        lock.writeLock().unlock();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public final void lockReadLock() {
        // Calls a method
        lock.readLock().lock();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public final void unlockReadLock() {
        // Calls a method
        lock.readLock().unlock();
    // End of a block/expression
    }

    /**
     * @return whether the calling thread holds the chunk write-lock
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public final boolean holdsWriteLock() {
        // Returns a value to the caller
        return lock.isWriteLockedByCurrentThread();
    // End of a block/expression
    }

    /**
     * @return whether the calling thread holds the chunk read-lock
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public final boolean holdsReadLock() {
        // Returns a value to the caller
        return holdsWriteLock() || lock.getReadHoldCount() > 0;
    // End of a block/expression
    }
// End of a block/expression
}