// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.instance.InstanceBlockUpdateEvent;
// Import of a required class
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
// Import of a required class
import net.minestom.server.event.instance.InstanceChunkUnloadEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerBlockBreakEvent;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import net.minestom.server.instance.generator.Generator;
// Import of a required class
import net.minestom.server.instance.generator.GeneratorImpl;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockChangePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockEntityDataPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.WorldEventPacket;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import net.minestom.server.utils.async.AsyncUtils;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkCache;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.worldevent.WorldEvent;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;
// Import of a required class
import space.vectrix.flare.fastutil.Long2ObjectSyncMap;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.concurrent.locks.Lock;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Supplier;

// Static import of a member
import static net.minestom.server.utils.chunk.ChunkUtils.isLoaded;

/**
 * InstanceContainer is an instance that contains chunks in contrary to SharedInstance.
 */
// Type declaration (class/interface/enum/record)
public class InstanceContainer extends Instance {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceContainer.class);

    // Assigns a value
    private static final NoopChunkLoaderImpl DEFAULT_LOADER = NoopChunkLoaderImpl.INSTANCE;

    // Assigns a value
    private static final BlockFace[] BLOCK_UPDATE_FACES = new BlockFace[]{
            // Code statement
            BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.BOTTOM, BlockFace.TOP
    // End of a block/expression
    };

    // the shared instances assigned to this instance
    // Calls a method
    private final List<SharedInstance> sharedInstances = new CopyOnWriteArrayList<>();

    // the chunk generator used, can be null
    // Code statement
    private volatile @Nullable Generator generator;
    // (chunk index -> chunk) map, contains all the chunks in the instance
    // used as a monitor when access is required
    // Calls a method
    private final Long2ObjectSyncMap<Chunk> chunks = Long2ObjectSyncMap.hashmap();
    // Calls a method
    private final Map<Long, CompletableFuture<Chunk>> loadingChunks = new ConcurrentHashMap<>();

    // Calls a method
    private final Lock changingBlockLock = new ReentrantLock();
    // Calls a method
    private final Map<BlockVec, Block> currentlyChangingBlocks = new HashMap<>();

    // the chunk loader, used when trying to load/save a chunk from another source
    // Code statement
    private ChunkLoader chunkLoader;

    // used to automatically enable the chunk loading or not
    // Assigns a value
    private boolean autoChunkLoad = true;

    // used to supply a new chunk object at a position when requested
    // Code statement
    private ChunkSupplier chunkSupplier;

    // Fields for instance copy
    // Code statement
    protected InstanceContainer srcInstance; // only present if this instance has been created using a copy
    // Code statement
    private long lastBlockChangeTime; // Time at which the last block change happened (#setBlock)

    // Start of a method/block
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType) {
        // Calls a method
        this(uuid, dimensionType, null, dimensionType.key());
    // End of a block/expression
    }

    // Start of a method/block
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType, Key dimensionName) {
        // Calls a method
        this(uuid, dimensionType, null, dimensionName);
    // End of a block/expression
    }

    // Start of a method/block
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType, @Nullable ChunkLoader loader) {
        // Calls a method
        this(uuid, dimensionType, loader, dimensionType.key());
    // End of a block/expression
    }

    // Start of a method/block
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType, @Nullable ChunkLoader loader, Key dimensionName) {
        // Calls a method
        this(MinecraftServer.process(), uuid, dimensionType, loader, dimensionName);
    // End of a block/expression
    }

    // Code statement
    public InstanceContainer(
            // Code statement
            Registries registries,
            // Code statement
            UUID uuid,
            // Code statement
            RegistryKey<DimensionType> dimensionType,
            // Annotation for the following element
            @Nullable ChunkLoader loader,
            // Code statement
            Key dimensionName
    // Start of a method/block
    ) {
        // Access to the current/parent object
        super(registries, uuid, dimensionType, dimensionName);
        // Calls a method
        setChunkSupplier(DynamicChunk::new);
        // Calls a method
        setChunkLoader(Objects.requireNonNullElse(loader, DEFAULT_LOADER));
        // Access to the current/parent object
        this.chunkLoader.loadInstance(this);
        // last block change starts at instance creation time
        // Calls a method
        refreshLastBlockChangeTime();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block, boolean doBlockUpdates) {
        // Calls a method
        Chunk chunk = getChunkAt(x, z);
        // Branch: checks a condition
        if (chunk == null) {
            // Code statement
            Check.stateCondition(!hasEnabledAutoChunkLoad(),
                    // Code statement
                    "Tried to set a block to an unloaded chunk with auto chunk load disabled");
            // Calls a method
            chunk = loadChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z)).join();
        // End of a block/expression
        }
        // Branch: checks a condition
        if (isLoaded(chunk)) UNSAFE_setBlock(chunk, x, y, z, block, null, null, doBlockUpdates, 0);
    // End of a block/expression
    }

    /**
     * Sets a block at the specified position.
     * <p>
     * Unsafe because the method is not synchronized and it does not verify if the chunk is loaded or not.
     *
     * @param chunk the {@link Chunk} which should be loaded
     * @param x     the block X
     * @param y     the block Y
     * @param z     the block Z
     * @param block the block to place
     */
    // Code statement
    private synchronized void UNSAFE_setBlock(Chunk chunk, int x, int y, int z, Block block,
                                              // Annotation for the following element
                                              @Nullable BlockHandler.Placement placement, @Nullable BlockHandler.Destroy destroy,
                                              // Start of a method/block
                                              boolean doBlockUpdates, int updateDistance) {
        // Branch: checks a condition
        if (chunk.isReadOnly()) return;
        // Calls a method
        final DimensionType dim = getCachedDimensionType();
        // Branch: checks a condition
        if (y >= dim.maxY() || y < dim.minY()) {
            // Calls a method
            LOGGER.warn("tried to set a block outside the world bounds, should be within [{}, {}): {}", dim.minY(), dim.maxY(), y);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        chunk.lockWriteLock();
        // Exception handling
        try {
            // Refresh the last block change time
            // Access to the current/parent object
            this.lastBlockChangeTime = System.nanoTime();
            // Calls a method
            final BlockVec blockPosition = new BlockVec(x, y, z);
            // Branch: checks a condition
            if (isAlreadyChanged(blockPosition, block)) { // do NOT change the block again.
                // Avoids StackOverflowExceptions when onDestroy tries to destroy the block itself
                // This can happen with nether portals which break the entire frame when a portal block is broken
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Access to the current/parent object
            this.currentlyChangingBlocks.put(blockPosition, block);

            // Change id based on neighbors
            // Calls a method
            final BlockPlacementRule blockPlacementRule = MinecraftServer.getBlockManager().getBlockPlacementRule(block);
            // Branch: checks a condition
            if (placement != null && blockPlacementRule != null && doBlockUpdates) {
                // Code statement
                BlockPlacementRule.PlacementState rulePlacement;
                // Branch: checks a condition
                if (placement instanceof BlockHandler.PlayerPlacement pp) {
                    // Assigns a value
                    rulePlacement = new BlockPlacementRule.PlacementState(
                            // Code statement
                            this, block, pp.getBlockFace(), blockPosition,
                            // Creates a new object
                            new Vec(pp.getCursorX(), pp.getCursorY(), pp.getCursorZ()),
                            // Code statement
                            pp.getPlayer().getPosition(),
                            // Code statement
                            pp.getPlayer().getItemInHand(pp.getHand()),
                            // Code statement
                            pp.getPlayer().isSneaking()
                    // End of a block/expression
                    );
                // Alternative branch of the condition
                } else {
                    // Assigns a value
                    rulePlacement = new BlockPlacementRule.PlacementState(
                            // Code statement
                            this, block, null, blockPosition,
                            // Code statement
                            null, null, null,
                            // Code statement
                            false
                    // End of a block/expression
                    );
                // End of a block/expression
                }

                // Calls a method
                block = blockPlacementRule.blockPlace(rulePlacement);
                // Branch: checks a condition
                if (block == null) block = Block.AIR;
            // End of a block/expression
            }

            // Set the block
            // Calls a method
            chunk.setBlock(x, y, z, block, placement, destroy);

            // Refresh neighbors since a new block has been placed
            // Branch: checks a condition
            if (doBlockUpdates) {
                // Calls a method
                executeNeighboursBlockPlacementRule(blockPosition, updateDistance);
            // End of a block/expression
            }

            // Refresh player chunk block
            // Start of a block
            {
                // Calls a method
                chunk.sendPacketToViewers(new BlockChangePacket(blockPosition, block.stateId()));
                // Calls a method
                BlockEntityType blockEntityType = block.registry().blockEntityType();
                // Branch: checks a condition
                if (blockEntityType != null) {
                    // Calls a method
                    final CompoundBinaryTag data = BlockUtils.extractClientNbt(block);
                    // Calls a method
                    chunk.sendPacketToViewers(new BlockEntityDataPacket(blockPosition, blockEntityType, data));
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            EventDispatcher.call(new InstanceBlockUpdateEvent(this, blockPosition, block));
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockWriteLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean placeBlock(BlockHandler.Placement placement, boolean doBlockUpdates) {
        // Calls a method
        final Point blockPosition = placement.getBlockPosition();
        // Calls a method
        final Chunk chunk = getChunkAt(blockPosition);
        // Branch: checks a condition
        if (!isLoaded(chunk)) return false;
        // Code statement
        UNSAFE_setBlock(chunk, blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(),
                // Calls a method
                placement.getBlock(), placement, null, doBlockUpdates, 0);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace, boolean doBlockUpdates) {
        // Calls a method
        final Chunk chunk = getChunkAt(blockPosition);
        // Calls a method
        Objects.requireNonNull(chunk, "You cannot break blocks in a null chunk!");
        // Branch: checks a condition
        if (chunk.isReadOnly()) return false;
        // Branch: checks a condition
        if (!isLoaded(chunk)) return false;

        // Calls a method
        final Block block = getBlock(blockPosition);
        // Calls a method
        final int x = blockPosition.blockX();
        // Calls a method
        final int y = blockPosition.blockY();
        // Calls a method
        final int z = blockPosition.blockZ();
        // Branch: checks a condition
        if (block.isAir()) {
            // The player probably have a wrong version of this chunk section, send it
            // Calls a method
            chunk.sendChunk(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        PlayerBlockBreakEvent blockBreakEvent = new PlayerBlockBreakEvent(player, this, block, Block.AIR, blockPosition.asBlockVec(), blockFace);
        // Calls a method
        EventDispatcher.call(blockBreakEvent);
        // Calls a method
        final boolean allowed = !blockBreakEvent.isCancelled();
        // Branch: checks a condition
        if (allowed) {
            // Break or change the broken block based on event result
            // Calls a method
            final Block resultBlock = blockBreakEvent.getResultBlock();
            // Code statement
            UNSAFE_setBlock(chunk, x, y, z, resultBlock, null,
                    // Creates a new object
                    new BlockHandler.PlayerDestroy(block, resultBlock, this, blockPosition, player), doBlockUpdates, 0);
            // Send the block break effect packet
            // Code statement
            PacketSendingUtils.sendGroupedPacket(chunk.getViewers(),
                    // Creates a new object
                    new WorldEventPacket(WorldEvent.PARTICLES_DESTROY_BLOCK.id(), blockPosition, block.stateId(), false),
                    // Prevent the block breaker to play the particles and sound two times
                    // Calls a method
                    (viewer) -> !viewer.equals(player));
        // End of a block/expression
        }
        // Returns a value to the caller
        return allowed;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Chunk> loadChunk(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return loadOrRetrieve(chunkX, chunkZ, () -> retrieveChunk(chunkX, chunkZ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Chunk> loadOptionalChunk(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return loadOrRetrieve(chunkX, chunkZ, () -> hasEnabledAutoChunkLoad() ? retrieveChunk(chunkX, chunkZ) : AsyncUtils.empty());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void unloadChunk(Chunk chunk) {
        // Branch: checks a condition
        if (!isLoaded(chunk)) return;
        // Calls a method
        final int chunkX = chunk.getChunkX();
        // Calls a method
        final int chunkZ = chunk.getChunkZ();
        // Calls a method
        chunk.sendPacketToViewers(new UnloadChunkPacket(chunkX, chunkZ));
        // Calls a method
        EventDispatcher.call(new InstanceChunkUnloadEvent(this, chunk));
        // Remove all entities in chunk
        // Calls a method
        getEntityTracker().chunkEntities(chunkX, chunkZ, EntityTracker.Target.ENTITIES).forEach(Entity::remove);
        // Clear cache
        // Access to the current/parent object
        this.chunks.remove(CoordConversion.chunkIndex(chunkX, chunkZ));
        // Calls a method
        chunk.unload();
        // Calls a method
        chunkLoader.unloadChunk(chunk);
        // Calls a method
        var dispatcher = MinecraftServer.process().dispatcher();
        // Calls a method
        dispatcher.deletePartition(chunk);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Chunk getChunk(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return chunks.get(CoordConversion.chunkIndex(chunkX, chunkZ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> saveInstance() {
        // Assigns a value
        final ChunkLoader chunkLoader = this.chunkLoader;
        // Returns a value to the caller
        return optionalAsync(chunkLoader.supportsParallelSaving(), () -> chunkLoader.saveInstance(this));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> saveChunkToStorage(Chunk chunk) {
        // Assigns a value
        final ChunkLoader chunkLoader = this.chunkLoader;
        // Returns a value to the caller
        return optionalAsync(chunkLoader.supportsParallelSaving(), () -> chunkLoader.saveChunk(chunk));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> saveChunksToStorage() {
        // Assigns a value
        final ChunkLoader chunkLoader = this.chunkLoader;
        // Returns a value to the caller
        return optionalAsync(chunkLoader.supportsParallelSaving(), () -> chunkLoader.saveChunks(getChunks()));
    // End of a block/expression
    }

    // Start of a method/block
    private CompletableFuture<Void> optionalAsync(boolean async, Runnable runnable) {
        // Branch: checks a condition
        if (!async) {
            // Calls a method
            runnable.run();
            // Returns a value to the caller
            return CompletableFuture.completedFuture(null);
        // End of a block/expression
        }
        // Calls a method
        final CompletableFuture<Void> future = new CompletableFuture<>();
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // Exception handling
            try {
                // Calls a method
                runnable.run();
                // Calls a method
                future.complete(null);
            // Start of a method/block
            } catch (Throwable e) {
                // Calls a method
                future.completeExceptionally(e);
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Returns a value to the caller
        return future;
    // End of a block/expression
    }

    // Start of a method/block
    protected CompletableFuture<Chunk> retrieveChunk(int chunkX, int chunkZ) {
        // Calls a method
        CompletableFuture<Chunk> completableFuture = new CompletableFuture<>();
        // Calls a method
        final long index = CoordConversion.chunkIndex(chunkX, chunkZ);
        // Calls a method
        final CompletableFuture<Chunk> prev = loadingChunks.putIfAbsent(index, completableFuture);
        // Branch: checks a condition
        if (prev != null) return prev;
        // Assigns a value
        final ChunkLoader loader = chunkLoader;
        // Assigns a value
        final Consumer<Chunk> generate = chunk -> {
            // Branch: checks a condition
            if (chunk == null) {
                // Loader couldn't load the chunk, generate it
                // Calls a method
                var chunkGeneration = EventsJFR.newChunkGeneration(getUuid(), chunkX, chunkZ);
                // Calls a method
                chunkGeneration.begin();
                // Calls a method
                chunk = createChunk(chunkX, chunkZ);
                // Calls a method
                chunk.onGenerate();
                // Calls a method
                chunkGeneration.commit();
            // End of a block/expression
            }

            // TODO run in the instance thread?
            // Calls a method
            cacheChunk(chunk);
            // Calls a method
            chunk.onLoad();

            // Calls a method
            final CompletableFuture<Chunk> future = this.loadingChunks.remove(index);
            // Code statement
            assert future == completableFuture : "Invalid future: " + future;
            // Calls a method
            completableFuture.complete(chunk);
            // Calls a method
            EventDispatcher.call(new InstanceChunkLoadEvent(this, chunk));
        // End of a block/expression
        };
        // Assigns a value
        Supplier<Chunk> loaderSupplier = () -> {
            // Calls a method
            var chunkLoading = EventsJFR.newChunkLoading(getUuid(), loader.getClass(), chunkX, chunkZ);
            // Calls a method
            chunkLoading.begin();
            // Calls a method
            final Chunk chunk = loader.loadChunk(this, chunkX, chunkZ);
            // Calls a method
            chunkLoading.end();
            // Branch: checks a condition
            if (chunk != null) chunkLoading.commit();
            // Returns a value to the caller
            return chunk;
        // End of a block/expression
        };
        // Branch: checks a condition
        if (loader.supportsParallelLoading()) {
            // Start of a method/block
            Thread.startVirtualThread(() -> {
                // Exception handling
                try {
                    // Calls a method
                    final Chunk chunk = loaderSupplier.get();
                    // Calls a method
                    generate.accept(chunk);
                // Start of a method/block
                } catch (Throwable e) {
                    // Access to the current/parent object
                    this.loadingChunks.remove(index, completableFuture);
                    // Calls a method
                    completableFuture.completeExceptionally(e);
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                // End of a block/expression
                }
            // End of a block/expression
            });
        // Alternative branch of the condition
        } else {
            // Code statement
            final Chunk chunk;
            // Exception handling
            try {
                // Calls a method
                chunk = loaderSupplier.get();
            // Start of a method/block
            } catch (Throwable e) {
                // Access to the current/parent object
                this.loadingChunks.remove(index, completableFuture);
                // Calls a method
                completableFuture.completeExceptionally(e);
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
                // Returns a value to the caller
                return completableFuture;
            // End of a block/expression
            }
            // Start of a method/block
            Thread.startVirtualThread(() -> {
                // Exception handling
                try {
                    // Calls a method
                    generate.accept(chunk);
                // Start of a method/block
                } catch (Throwable e) {
                    // Access to the current/parent object
                    this.loadingChunks.remove(index, completableFuture);
                    // Calls a method
                    completableFuture.completeExceptionally(e);
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                // End of a block/expression
                }
            // End of a block/expression
            });
        // End of a block/expression
        }
        // Returns a value to the caller
        return completableFuture;
    // End of a block/expression
    }

    // Calls a method
    Map<Long, List<GeneratorImpl.SectionModifierImpl>> generationForks = new ConcurrentHashMap<>();

    // Start of a method/block
    protected Chunk createChunk(int chunkX, int chunkZ) {
        // Calls a method
        final Chunk chunk = chunkSupplier.createChunk(this, chunkX, chunkZ);
        // Calls a method
        Objects.requireNonNull(chunk, "Chunks supplied by a ChunkSupplier cannot be null.");
        // Calls a method
        Generator generator = generator();
        // Branch: checks a condition
        if (generator == null || !chunk.shouldGenerate()) {
            // No chunk generator, execute the callback with the empty chunk
            // Calls a method
            processFork(chunk);
            // Returns a value to the caller
            return chunk;
        // End of a block/expression
        }
        // Calls a method
        generateChunk(chunk, generator);
        // Returns a value to the caller
        return chunk;
    // End of a block/expression
    }

    // Start of a method/block
    protected void generateChunk(Chunk chunk, Generator generator) {
        // Calls a method
        final int chunkX = chunk.getChunkX(), chunkZ = chunk.getChunkZ();
        // Calls a method
        GeneratorImpl.GenSection[] genSections = new GeneratorImpl.GenSection[chunk.getSections().size()];
        // Start of a method/block
        Arrays.setAll(genSections, i -> {
            // Calls a method
            Section section = chunk.getSections().get(i);
            // Returns a value to the caller
            return new GeneratorImpl.GenSection(section.blockPalette(), section.biomePalette());
        // End of a block/expression
        });
        // Assigns a value
        var chunkUnit = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), genSections,
                // Calls a method
                chunk.getChunkX(), chunk.minSection, chunk.getChunkZ());
        // Exception handling
        try {
            // Generate block/biome palette
            // Calls a method
            generator.generate(chunkUnit);
            // Apply nbt/handler
            // Branch: checks a condition
            if (chunkUnit.modifier() instanceof GeneratorImpl.AreaModifierImpl chunkModifier) {
                // Loop: repeats a block
                for (var section : chunkModifier.sections()) {
                    // Branch: checks a condition
                    if (section.modifier() instanceof GeneratorImpl.SectionModifierImpl sectionModifier) {
                        // Calls a method
                        applyGenerationData(chunk, sectionModifier);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Register forks or apply locally
            // Loop: repeats a block
            for (var fork : chunkUnit.forks()) {
                // Calls a method
                var sections = ((GeneratorImpl.AreaModifierImpl) fork.modifier()).sections();
                // Loop: repeats a block
                for (var section : sections) {
                    // Branch: checks a condition
                    if (section.modifier() instanceof GeneratorImpl.SectionModifierImpl sectionModifier) {
                        // Branch: checks a condition
                        if (sectionModifier.genSection().blocks().count() == 0)
                            // Continues to the next loop iteration
                            continue;
                        // Calls a method
                        final Point start = section.absoluteStart();
                        // Calls a method
                        final Chunk forkChunk = start.chunkX() == chunkX && start.chunkZ() == chunkZ ? chunk : getChunkAt(start);
                        // Branch: checks a condition
                        if (forkChunk != null) {
                            // Calls a method
                            applyFork(forkChunk, sectionModifier);
                            // Update players
                            // Calls a method
                            forkChunk.invalidate();
                            // Calls a method
                            forkChunk.sendChunk();
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            final long index = CoordConversion.chunkIndex(start);
                            // Access to the current/parent object
                            this.generationForks.compute(index, (i, sectionModifiers) -> {
                                // Branch: checks a condition
                                if (sectionModifiers == null) sectionModifiers = new ArrayList<>();
                                // Calls a method
                                sectionModifiers.add(sectionModifier);
                                // Returns a value to the caller
                                return sectionModifiers;
                            // End of a block/expression
                            });
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Apply awaiting forks
            // Calls a method
            processFork(chunk);
        // Start of a method/block
        } catch (Throwable e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // Start of a method/block
        } finally {
            // End generation
            // Calls a method
            refreshLastBlockChangeTime();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void processFork(Chunk chunk) {
        // Access to the current/parent object
        this.generationForks.compute(CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ()), (aLong, sectionModifiers) -> {
            // Branch: checks a condition
            if (sectionModifiers != null) {
                // Loop: repeats a block
                for (var sectionModifier : sectionModifiers) {
                    // Calls a method
                    applyFork(chunk, sectionModifier);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return null;
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    private void applyFork(Chunk chunk, GeneratorImpl.SectionModifierImpl sectionModifier) {
        // Calls a method
        chunk.lockWriteLock();
        // Exception handling
        try {
            // Calls a method
            Section section = chunk.getSectionAt(sectionModifier.start().blockY());
            // Calls a method
            Palette currentBlocks = section.blockPalette();
            // -1 is necessary because forked units handle explicit changes by changing AIR 0 to 1
            // Calls a method
            sectionModifier.genSection().blocks().getAllPresent((x, y, z, value) -> currentBlocks.set(x, y, z, value - 1));
            // Calls a method
            applyGenerationData(chunk, sectionModifier);
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockWriteLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void applyGenerationData(Chunk chunk, GeneratorImpl.SectionModifierImpl section) {
        // Calls a method
        var cache = section.genSection().specials();
        // Branch: checks a condition
        if (cache.isEmpty()) return;
        // Calls a method
        final int height = section.start().blockY();
        // Calls a method
        chunk.lockWriteLock();
        // Exception handling
        try {
            // Start of a method/block
            Int2ObjectMaps.fastForEach(cache, blockEntry -> {
                // Calls a method
                final int index = blockEntry.getIntKey();
                // Calls a method
                final Block block = blockEntry.getValue();
                // Calls a method
                final int x = CoordConversion.chunkBlockIndexGetX(index);
                // Calls a method
                final int y = CoordConversion.chunkBlockIndexGetY(index) + height;
                // Calls a method
                final int z = CoordConversion.chunkBlockIndexGetZ(index);
                // Calls a method
                chunk.setBlock(x, y, z, block);
            // End of a block/expression
            });
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockWriteLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void enableAutoChunkLoad(boolean enable) {
        // Access to the current/parent object
        this.autoChunkLoad = enable;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean hasEnabledAutoChunkLoad() {
        // Returns a value to the caller
        return autoChunkLoad;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isInVoid(Point point) {
        // TODO: more customizable
        // Returns a value to the caller
        return point.y() < getCachedDimensionType().minY() - 64;
    // End of a block/expression
    }

    /**
     * Changes which type of {@link Chunk} implementation to use once one needs to be loaded.
     * <p>
     * Uses {@link DynamicChunk} by default.
     * <p>
     * WARNING: if you need to save this instance's chunks later,
     * the code needs to be predictable for {@link ChunkLoader#loadChunk(Instance, int, int)}
     * to create the correct type of {@link Chunk}. tl;dr: Need chunk save = no random type.
     *
     * @param chunkSupplier the new {@link ChunkSupplier} of this instance, chunks need to be non-null
     * @throws NullPointerException if {@code chunkSupplier} is null
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setChunkSupplier(ChunkSupplier chunkSupplier) {
        // Access to the current/parent object
        this.chunkSupplier = chunkSupplier;
    // End of a block/expression
    }

    /**
     * Gets the current {@link ChunkSupplier}.
     * <p>
     * You shouldn't use it to generate a new chunk, but as a way to view which one is currently in use.
     *
     * @return the current {@link ChunkSupplier}
     */
    // Start of a method/block
    public ChunkSupplier getChunkSupplier() {
        // Returns a value to the caller
        return chunkSupplier;
    // End of a block/expression
    }

    /**
     * Gets all the {@link SharedInstance} linked to this container.
     *
     * @return an unmodifiable {@link List} containing all the {@link SharedInstance} linked to this container
     */
    // Start of a method/block
    public List<SharedInstance> getSharedInstances() {
        // Returns a value to the caller
        return Collections.unmodifiableList(sharedInstances);
    // End of a block/expression
    }

    /**
     * Gets if this instance has {@link SharedInstance} linked to it.
     *
     * @return true if {@link #getSharedInstances()} is not empty
     */
    // Start of a method/block
    public boolean hasSharedInstances() {
        // Returns a value to the caller
        return !sharedInstances.isEmpty();
    // End of a block/expression
    }

    /**
     * Assigns a {@link SharedInstance} to this container.
     * <p>
     * Only used by {@link InstanceManager}, mostly unsafe.
     *
     * @param sharedInstance the shared instance to assign to this container
     */
    // Start of a method/block
    protected void addSharedInstance(SharedInstance sharedInstance) {
        // Access to the current/parent object
        this.sharedInstances.add(sharedInstance);
    // End of a block/expression
    }

    /**
     * Copies all the chunks of this instance and create a new instance container with all of them.
     * <p>
     * Chunks are copied with {@link Chunk#copy(Instance, int, int)},
     * {@link UUID} is randomized and {@link DimensionType} is passed over.
     *
     * @return an {@link InstanceContainer} with the exact same chunks as 'this'
     * @see #getSrcInstance() to retrieve the "creation source" of the copied instance
     */
    // Start of a method/block
    public synchronized InstanceContainer copy() {
        // Calls a method
        InstanceContainer copiedInstance = new InstanceContainer(UUID.randomUUID(), getDimensionType());
        // Assigns a value
        copiedInstance.srcInstance = this;
        // Calls a method
        copiedInstance.tagHandler = this.tagHandler.copy();
        // Assigns a value
        copiedInstance.lastBlockChangeTime = this.lastBlockChangeTime;
        // Loop: repeats a block
        for (Chunk chunk : chunks.values()) {
            // Calls a method
            final int chunkX = chunk.getChunkX();
            // Calls a method
            final int chunkZ = chunk.getChunkZ();
            // Calls a method
            final Chunk copiedChunk = chunk.copy(copiedInstance, chunkX, chunkZ);
            // Calls a method
            copiedInstance.cacheChunk(copiedChunk);
        // End of a block/expression
        }
        // Returns a value to the caller
        return copiedInstance;
    // End of a block/expression
    }

    /**
     * Gets the instance from which this one has been copied.
     * <p>
     * Only present if this instance has been created with {@link InstanceContainer#copy()}.
     *
     * @return the instance source, null if not created by a copy
     * @see #copy() to create a copy of this instance with 'this' as the source
     */
    // Start of a method/block
    public @Nullable InstanceContainer getSrcInstance() {
        // Returns a value to the caller
        return srcInstance;
    // End of a block/expression
    }

    /**
     * Gets the last time at which a block changed.
     *
     * @return the time at which the last block changed in nanoseconds. Only use this to calculate delta times
     */
    // Start of a method/block
    public long getLastBlockChangeTime() {
        // Returns a value to the caller
        return lastBlockChangeTime;
    // End of a block/expression
    }

    /**
     * Signals the instance that a block changed.
     * <p>
     * Useful if you change blocks values directly using a {@link Chunk} object.
     */
    // Start of a method/block
    public void refreshLastBlockChangeTime() {
        // Access to the current/parent object
        this.lastBlockChangeTime = System.nanoTime();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Generator generator() {
        // Returns a value to the caller
        return generator;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setGenerator(@Nullable Generator generator) {
        // Access to the current/parent object
        this.generator = generator;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> generateChunk(int chunkX, int chunkZ, Generator generator) {
        // Calls a method
        CompletableFuture<Void> future = new CompletableFuture<>();
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // Exception handling
            try {
                // Calls a method
                Chunk chunk = loadChunk(chunkX, chunkZ).join();
                // Calls a method
                chunk.lockWriteLock();
                // Exception handling
                try {
                    // Calls a method
                    generateChunk(chunk, generator);
                    // Calls a method
                    chunk.invalidate();
                // Start of a method/block
                } finally {
                    // Calls a method
                    chunk.unlockWriteLock();
                // End of a block/expression
                }
                // Calls a method
                chunk.sendChunk();
                // Calls a method
                future.complete(null);
            // Start of a method/block
            } catch (Throwable e) {
                // Calls a method
                future.completeExceptionally(e);
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Returns a value to the caller
        return future;
    // End of a block/expression
    }

    /**
     * Gets all the instance chunks.
     *
     * @return the chunks of this instance
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Chunk> getChunks() {
        // Returns a value to the caller
        return chunks.values();
    // End of a block/expression
    }

    /**
     * Gets the {@link ChunkLoader} of this instance.
     *
     * @return the {@link ChunkLoader} of this instance
     */
    // Start of a method/block
    public ChunkLoader getChunkLoader() {
        // Returns a value to the caller
        return chunkLoader;
    // End of a block/expression
    }

    /**
     * Changes the {@link ChunkLoader} of this instance (to change how chunks are retrieved when not already loaded).
     *
     * <p>{@link ChunkLoader#noop()} can be used to do nothing.</p>
     *
     * @param chunkLoader the new {@link ChunkLoader}
     */
    // Start of a method/block
    public void setChunkLoader(ChunkLoader chunkLoader) {
        // Access to the current/parent object
        this.chunkLoader = Objects.requireNonNull(chunkLoader, "Chunk loader cannot be null");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Time/world border
        // Access to the current/parent object
        super.tick(time);
        // Clear block change map
        // Assigns a value
        Lock wrlock = this.changingBlockLock;
        // Calls a method
        wrlock.lock();
        // Access to the current/parent object
        this.currentlyChangingBlocks.clear();
        // Calls a method
        wrlock.unlock();
    // End of a block/expression
    }

    /**
     * Has this block already changed since last update?
     * Prevents StackOverflow with blocks trying to modify their position in onDestroy or onPlace.
     *
     * @param blockPosition the block position
     * @param block         the block
     * @return true if the block changed since the last update
     */
    // Start of a method/block
    private boolean isAlreadyChanged(BlockVec blockPosition, Block block) {
        // Calls a method
        final Block changedBlock = currentlyChangingBlocks.get(blockPosition);
        // Returns a value to the caller
        return Objects.equals(changedBlock, block);
    // End of a block/expression
    }

    /**
     * Executed when a block is modified, this is used to modify the states of neighbours blocks.
     * <p>
     * For example, this can be used for redstone wires which need an understanding of its neighborhoods to take the right shape.
     *
     * @param blockPosition the position of the modified block
     */
    // Start of a method/block
    private void executeNeighboursBlockPlacementRule(Point blockPosition, int updateDistance) {
        // Calls a method
        ChunkCache cache = new ChunkCache(this, null, null);
        // Loop: repeats a block
        for (var updateFace : BLOCK_UPDATE_FACES) {
            // Calls a method
            var direction = updateFace.toDirection();
            // Calls a method
            final int neighborX = blockPosition.blockX() + direction.normalX();
            // Calls a method
            final int neighborY = blockPosition.blockY() + direction.normalY();
            // Calls a method
            final int neighborZ = blockPosition.blockZ() + direction.normalZ();
            // Branch: checks a condition
            if (neighborY < getCachedDimensionType().minY() || neighborY > getCachedDimensionType().height())
                // Continues to the next loop iteration
                continue;
            // Calls a method
            final Block neighborBlock = cache.getBlock(neighborX, neighborY, neighborZ, Condition.NONE);
            // Branch: checks a condition
            if (neighborBlock == null || neighborBlock.isAir())
                // Continues to the next loop iteration
                continue;
            // Calls a method
            final BlockPlacementRule neighborBlockPlacementRule = MinecraftServer.getBlockManager().getBlockPlacementRule(neighborBlock);
            // Branch: checks a condition
            if (neighborBlockPlacementRule == null || updateDistance >= neighborBlockPlacementRule.maxUpdateDistance())
                // Continues to the next loop iteration
                continue;

            // Calls a method
            final Vec neighborPosition = new Vec(neighborX, neighborY, neighborZ);
            // Assigns a value
            final Block newNeighborBlock = neighborBlockPlacementRule.blockUpdate(new BlockPlacementRule.UpdateState(
                    // Code statement
                    this,
                    // Code statement
                    neighborPosition,
                    // Code statement
                    neighborBlock,
                    // Code statement
                    updateFace.getOppositeFace()
            // Code statement
            ));
            // Branch: checks a condition
            if (neighborBlock != newNeighborBlock) {
                // Calls a method
                final Chunk chunk = getChunkAt(neighborPosition);
                // Branch: checks a condition
                if (!isLoaded(chunk)) continue;
                // Code statement
                UNSAFE_setBlock(chunk, neighborPosition.blockX(), neighborPosition.blockY(), neighborPosition.blockZ(), newNeighborBlock,
                        // Code statement
                        null, null, true, updateDistance + 1);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private CompletableFuture<Chunk> loadOrRetrieve(int chunkX, int chunkZ, Supplier<CompletableFuture<Chunk>> supplier) {
        // Calls a method
        final Chunk chunk = getChunk(chunkX, chunkZ);
        // Branch: checks a condition
        if (chunk != null) {
            // Chunk already loaded
            // Returns a value to the caller
            return CompletableFuture.completedFuture(chunk);
        // End of a block/expression
        }
        // Returns a value to the caller
        return supplier.get();
    // End of a block/expression
    }

    // Start of a method/block
    private void cacheChunk(Chunk chunk) {
        // Access to the current/parent object
        this.chunks.put(CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ()), chunk);
        // Calls a method
        var dispatcher = MinecraftServer.process().dispatcher();
        // Calls a method
        dispatcher.createPartition(chunk);
    // End of a block/expression
    }
// End of a block/expression
}
