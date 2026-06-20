// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.Generator;
// Import d'une classe nécessaire
import net.minestom.server.instance.heightmap.Heightmap;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.Snapshotable;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Taggable;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantReadWriteLock;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public abstract class Chunk implements Block.Getter, Block.Setter, Biome.Getter, Biome.Setter, Viewable, Tickable, Taggable, Snapshotable {
    // Affecte une valeur
    public static final int CHUNK_SIZE_X = 16;
    // Affecte une valeur
    public static final int CHUNK_SIZE_Z = 16;
    // Affecte une valeur
    public static final int CHUNK_SECTION_SIZE = 16;

    // Instruction de code
    private final UUID identifier;
    // Appelle une méthode
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Instruction de code
    protected Instance instance;
    // Instruction de code
    protected final int chunkX, chunkZ;
    // Instruction de code
    protected final int minSection, maxSection;

    // Options
    // Instruction de code
    private final boolean shouldGenerate;
    // Instruction de code
    private boolean readOnly;

    // Affecte une valeur
    protected volatile boolean loaded = true;
    // Instruction de code
    private final Viewable viewable;

    // Data
    // Appelle une méthode
    private final TagHandler tagHandler = TagHandler.newHandler();

    // Début d'une méthode/d'un bloc
    public Chunk(Instance instance, int chunkX, int chunkZ, boolean shouldGenerate) {
        // Accès à l'objet courant/parent
        this.identifier = UUID.randomUUID();
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.chunkX = chunkX;
        // Accès à l'objet courant/parent
        this.chunkZ = chunkZ;
        // Accès à l'objet courant/parent
        this.shouldGenerate = shouldGenerate;
        // Appelle une méthode
        final DimensionType instanceDim = instance.getCachedDimensionType();
        // Accès à l'objet courant/parent
        this.minSection = instanceDim.minY() / CHUNK_SECTION_SIZE;
        // Accès à l'objet courant/parent
        this.maxSection = (instanceDim.minY() + instanceDim.height()) / CHUNK_SECTION_SIZE;
        // Affecte une valeur
        final List<SharedInstance> shared = instance instanceof InstanceContainer instanceContainer ?
                // Appelle une méthode
                instanceContainer.getSharedInstances() : List.of();
        // Accès à l'objet courant/parent
        this.viewable = instance.getEntityTracker().viewable(shared, chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block) {
        // Appelle une méthode
        assertWriteLock();
        // Appelle une méthode
        setBlock(x, y, z, block, null, null);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    protected abstract void setBlock(int x, int y, int z, Block block,
                                     // Annotation pour l'élément suivant
                                     @Nullable BlockHandler.Placement placement,
                                     // Annotation pour l'élément suivant
                                     @Nullable BlockHandler.Destroy destroy);

    // Appelle une méthode
    public abstract List<Section> getSections();

    // Appelle une méthode
    public abstract Section getSection(int section);

    // Appelle une méthode
    public abstract Heightmap motionBlockingHeightmap();
    // Appelle une méthode
    public abstract Heightmap worldSurfaceHeightmap();
    // Appelle une méthode
    public abstract void loadHeightmapsFromNBT(CompoundBinaryTag heightmaps);

    // Début d'une méthode/d'un bloc
    public Section getSectionAt(int blockY) {
        // Renvoie une valeur à l'appelant
        return getSection(CoordConversion.globalToChunk(blockY));
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Override
    // Appelle une méthode
    public abstract void tick(long time);

    /**
     * Sends the chunk data to {@code player}.
     *
     * @param player the player
     */
    // Début d'une méthode/d'un bloc
    public void sendChunk(Player player) {
        // Appelle une méthode
        player.sendChunk(this);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendChunk() {
        // Appelle une méthode
        getViewers().forEach(this::sendChunk);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
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
    // Appelle une méthode
    public abstract Chunk copy(Instance instance, int chunkX, int chunkZ);

    /**
     * Resets the chunk, this means clearing all the data making it empty.
     */
    // Appelle une méthode
    public abstract void reset();

    /**
     * Gets the unique identifier of this chunk.
     * <p>
     * WARNING: this UUID is not persistent but randomized once the object is instantiated.
     *
     * @return the chunk identifier
     */
    // Début d'une méthode/d'un bloc
    public UUID getIdentifier() {
        // Renvoie une valeur à l'appelant
        return identifier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the instance where this chunk is stored
     *
     * @return the linked instance
     */
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
        return chunkX;
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
        return chunkZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the lowest (inclusive) section Y available in this chunk
     *
     * @return the lowest (inclusive) section Y available in this chunk
     */
    // Début d'une méthode/d'un bloc
    public int getMinSection() {
        // Renvoie une valeur à l'appelant
        return minSection;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the highest (exclusive) section Y available in this chunk
     *
     * @return the highest (exclusive) section Y available in this chunk
     */
    // Début d'une méthode/d'un bloc
    public int getMaxSection() {
        // Renvoie une valeur à l'appelant
        return maxSection;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the world position of this chunk.
     *
     * @return the position of this chunk
     */
    // Début d'une méthode/d'un bloc
    public Point toPosition() {
        // Renvoie une valeur à l'appelant
        return new Vec(CHUNK_SIZE_X * getChunkX(), 0, CHUNK_SIZE_Z * getChunkZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this chunk will or had been loaded with a {@link Generator}.
     * <p>
     * If false, the chunk will be entirely empty when loaded.
     *
     * @return true if this chunk is affected by a {@link Generator}
     */
    // Début d'une méthode/d'un bloc
    public boolean shouldGenerate() {
        // Renvoie une valeur à l'appelant
        return shouldGenerate;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this chunk is read-only.
     * <p>
     * Being read-only should prevent block placing/breaking and setting block from an {@link Instance}.
     * It does not affect {@link ChunkLoader} and {@link Generator}.
     *
     * @return true if the chunk is read-only
     */
    // Début d'une méthode/d'un bloc
    public boolean isReadOnly() {
        // Renvoie une valeur à l'appelant
        return readOnly;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the read state of the chunk.
     * <p>
     * Being read-only should prevent block placing/breaking and setting block from an {@link Instance}.
     * It does not affect {@link ChunkLoader} and {@link Generator}.
     *
     * @param readOnly true to make the chunk read-only, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void setReadOnly(boolean readOnly) {
        // Appelle une méthode
        assertWriteLock();
        // Accès à l'objet courant/parent
        this.readOnly = readOnly;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to verify if the chunk should still be kept in memory.
     *
     * @return true if the chunk is loaded
     */
    // Début d'une méthode/d'un bloc
    public boolean isLoaded() {
        // Renvoie une valeur à l'appelant
        return loaded;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when the chunk has been successfully loaded.
     */
    // Instruction de code
    protected void onLoad() {}

    /**
     * Called when the chunk generator has finished generating the chunk.
     */
    // Instruction de code
    public void onGenerate() {}

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return getClass().getSimpleName() + "[" + chunkX + ":" + chunkZ + "]";
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Renvoie une valeur à l'appelant
        return viewable.addViewer(player);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeViewer(Player player) {
        // Renvoie une valeur à l'appelant
        return viewable.removeViewer(player);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<? extends Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return viewable.getViewers();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TagHandler tagHandler() {
        // Renvoie une valeur à l'appelant
        return tagHandler;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the chunk as "unloaded".
     */
    // Début d'une méthode/d'un bloc
    protected void unload() {
        // Accès à l'objet courant/parent
        this.loaded = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Invalidate the chunk caches
     */
    // Appelle une méthode
    public abstract void invalidate();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected final void assertWriteLock() {
        // Appelle une méthode
        assert holdsWriteLock() : "Not holding write-lock for chunk " + chunkX + "," + chunkZ;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected final void assertReadLock() {
        // Appelle une méthode
        assert holdsReadLock() : "Not holding read-lock for chunk " + chunkX + "," + chunkZ;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public final void lockWriteLock() {
        // Appelle une méthode
        assert holdsWriteLock() || lock.getReadHoldCount() == 0 : "Cannot acquire write-lock while holding read-lock for chunk " + chunkX + "," + chunkZ;
        // Appelle une méthode
        lock.writeLock().lock();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public final void unlockWriteLock() {
        // Appelle une méthode
        lock.writeLock().unlock();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public final void lockReadLock() {
        // Appelle une méthode
        lock.readLock().lock();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public final void unlockReadLock() {
        // Appelle une méthode
        lock.readLock().unlock();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return whether the calling thread holds the chunk write-lock
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public final boolean holdsWriteLock() {
        // Renvoie une valeur à l'appelant
        return lock.isWriteLockedByCurrentThread();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return whether the calling thread holds the chunk read-lock
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public final boolean holdsReadLock() {
        // Renvoie une valeur à l'appelant
        return holdsWriteLock() || lock.getReadHoldCount() > 0;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}