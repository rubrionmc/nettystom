// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceBlockUpdateEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceChunkUnloadEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerBlockBreakEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.anvil.AnvilLoader;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.Generator;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.GeneratorImpl;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockEntityDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.WorldEventPacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.async.AsyncUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkCache;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.worldevent.WorldEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;
// Import d'une classe nécessaire
import space.vectrix.flare.fastutil.Long2ObjectSyncMap;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.concurrent.locks.Lock;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Import statique d'un membre
import static net.minestom.server.utils.chunk.ChunkUtils.isLoaded;

/**
 * InstanceContainer is an instance that contains chunks in contrary to SharedInstance.
 */
// Déclaration de type (classe/interface/enum/record)
public class InstanceContainer extends Instance {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceContainer.class);

    // Appelle une méthode
    private static final AnvilLoader DEFAULT_LOADER = new AnvilLoader("world");

    // Affecte une valeur
    private static final BlockFace[] BLOCK_UPDATE_FACES = new BlockFace[]{
            // Instruction de code
            BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.BOTTOM, BlockFace.TOP
    // Fin d'un bloc/d'une expression
    };

    // the shared instances assigned to this instance
    // Affecte une valeur
    private final List<SharedInstance> sharedInstances = new CopyOnWriteArrayList<>();

    // the chunk generator used, can be null
    // Instruction de code
    private volatile @Nullable Generator generator;
    // (chunk index -> chunk) map, contains all the chunks in the instance
    // used as a monitor when access is required
    // Appelle une méthode
    private final Long2ObjectSyncMap<Chunk> chunks = Long2ObjectSyncMap.hashmap();
    // Affecte une valeur
    private final Map<Long, CompletableFuture<Chunk>> loadingChunks = new ConcurrentHashMap<>();

    // Appelle une méthode
    private final Lock changingBlockLock = new ReentrantLock();
    // Affecte une valeur
    private final Map<BlockVec, Block> currentlyChangingBlocks = new HashMap<>();

    // the chunk loader, used when trying to load/save a chunk from another source
    // Instruction de code
    private ChunkLoader chunkLoader;

    // used to automatically enable the chunk loading or not
    // Affecte une valeur
    private boolean autoChunkLoad = true;

    // used to supply a new chunk object at a position when requested
    // Instruction de code
    private ChunkSupplier chunkSupplier;

    // Fields for instance copy
    // Instruction de code
    protected InstanceContainer srcInstance; // only present if this instance has been created using a copy
    // Instruction de code
    private long lastBlockChangeTime; // Time at which the last block change happened (#setBlock)

    // Début d'une méthode/d'un bloc
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType) {
        // Appelle une méthode
        this(uuid, dimensionType, null, dimensionType.key());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType, Key dimensionName) {
        // Appelle une méthode
        this(uuid, dimensionType, null, dimensionName);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType, @Nullable ChunkLoader loader) {
        // Appelle une méthode
        this(uuid, dimensionType, loader, dimensionType.key());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InstanceContainer(UUID uuid, RegistryKey<DimensionType> dimensionType, @Nullable ChunkLoader loader, Key dimensionName) {
        // Appelle une méthode
        this(MinecraftServer.getDimensionTypeRegistry(), uuid, dimensionType, loader, dimensionName);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public InstanceContainer(
            // Instruction de code
            DynamicRegistry<DimensionType> dimensionTypeRegistry,
            // Instruction de code
            UUID uuid,
            // Instruction de code
            RegistryKey<DimensionType> dimensionType,
            // Annotation pour l'élément suivant
            @Nullable ChunkLoader loader,
            // Instruction de code
            Key dimensionName
    // Début d'une méthode/d'un bloc
    ) {
        // Accès à l'objet courant/parent
        super(dimensionTypeRegistry, uuid, dimensionType, dimensionName);
        // Appelle une méthode
        setChunkSupplier(DynamicChunk::new);
        // Appelle une méthode
        setChunkLoader(Objects.requireNonNullElse(loader, DEFAULT_LOADER));
        // Accès à l'objet courant/parent
        this.chunkLoader.loadInstance(this);
        // last block change starts at instance creation time
        // Appelle une méthode
        refreshLastBlockChangeTime();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block, boolean doBlockUpdates) {
        // Appelle une méthode
        Chunk chunk = getChunkAt(x, z);
        // Embranchement : vérifie une condition
        if (chunk == null) {
            // Instruction de code
            Check.stateCondition(!hasEnabledAutoChunkLoad(),
                    // Instruction de code
                    "Tried to set a block to an unloaded chunk with auto chunk load disabled");
            // Appelle une méthode
            chunk = loadChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z)).join();
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (isLoaded(chunk)) UNSAFE_setBlock(chunk, x, y, z, block, null, null, doBlockUpdates, 0);
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    private synchronized void UNSAFE_setBlock(Chunk chunk, int x, int y, int z, Block block,
                                              // Annotation pour l'élément suivant
                                              @Nullable BlockHandler.Placement placement, @Nullable BlockHandler.Destroy destroy,
                                              // Début d'une méthode/d'un bloc
                                              boolean doBlockUpdates, int updateDistance) {
        // Embranchement : vérifie une condition
        if (chunk.isReadOnly()) return;
        // Appelle une méthode
        final DimensionType dim = getCachedDimensionType();
        // Embranchement : vérifie une condition
        if (y >= dim.maxY() || y < dim.minY()) {
            // Appelle une méthode
            LOGGER.warn("tried to set a block outside the world bounds, should be within [{}, {}): {}", dim.minY(), dim.maxY(), y);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Refresh the last block change time
            // Accès à l'objet courant/parent
            this.lastBlockChangeTime = System.nanoTime();
            // Appelle une méthode
            final BlockVec blockPosition = new BlockVec(x, y, z);
            // Embranchement : vérifie une condition
            if (isAlreadyChanged(blockPosition, block)) { // do NOT change the block again.
                // Avoids StackOverflowExceptions when onDestroy tries to destroy the block itself
                // This can happen with nether portals which break the entire frame when a portal block is broken
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.currentlyChangingBlocks.put(blockPosition, block);

            // Change id based on neighbors
            // Appelle une méthode
            final BlockPlacementRule blockPlacementRule = MinecraftServer.getBlockManager().getBlockPlacementRule(block);
            // Embranchement : vérifie une condition
            if (placement != null && blockPlacementRule != null && doBlockUpdates) {
                // Instruction de code
                BlockPlacementRule.PlacementState rulePlacement;
                // Embranchement : vérifie une condition
                if (placement instanceof BlockHandler.PlayerPlacement pp) {
                    // Affecte une valeur
                    rulePlacement = new BlockPlacementRule.PlacementState(
                            // Instruction de code
                            this, block, pp.getBlockFace(), blockPosition,
                            // Crée un nouvel objet
                            new Vec(pp.getCursorX(), pp.getCursorY(), pp.getCursorZ()),
                            // Instruction de code
                            pp.getPlayer().getPosition(),
                            // Instruction de code
                            pp.getPlayer().getItemInHand(pp.getHand()),
                            // Instruction de code
                            pp.getPlayer().isSneaking()
                    // Fin d'un bloc/d'une expression
                    );
                // Branche alternative de la condition
                } else {
                    // Affecte une valeur
                    rulePlacement = new BlockPlacementRule.PlacementState(
                            // Instruction de code
                            this, block, null, blockPosition,
                            // Instruction de code
                            null, null, null,
                            // Instruction de code
                            false
                    // Fin d'un bloc/d'une expression
                    );
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                block = blockPlacementRule.blockPlace(rulePlacement);
                // Embranchement : vérifie une condition
                if (block == null) block = Block.AIR;
            // Fin d'un bloc/d'une expression
            }

            // Set the block
            // Appelle une méthode
            chunk.setBlock(x, y, z, block, placement, destroy);

            // Refresh neighbors since a new block has been placed
            // Embranchement : vérifie une condition
            if (doBlockUpdates) {
                // Appelle une méthode
                executeNeighboursBlockPlacementRule(blockPosition, updateDistance);
            // Fin d'un bloc/d'une expression
            }

            // Refresh player chunk block
            // Début d'un bloc
            {
                // Appelle une méthode
                chunk.sendPacketToViewers(new BlockChangePacket(blockPosition, block.stateId()));
                // Appelle une méthode
                BlockEntityType blockEntityType = block.registry().blockEntityType();
                // Embranchement : vérifie une condition
                if (blockEntityType != null) {
                    // Appelle une méthode
                    final CompoundBinaryTag data = BlockUtils.extractClientNbt(block);
                    // Appelle une méthode
                    chunk.sendPacketToViewers(new BlockEntityDataPacket(blockPosition, blockEntityType, data));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            EventDispatcher.call(new InstanceBlockUpdateEvent(this, blockPosition, block));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean placeBlock(BlockHandler.Placement placement, boolean doBlockUpdates) {
        // Appelle une méthode
        final Point blockPosition = placement.getBlockPosition();
        // Appelle une méthode
        final Chunk chunk = getChunkAt(blockPosition);
        // Embranchement : vérifie une condition
        if (!isLoaded(chunk)) return false;
        // Instruction de code
        UNSAFE_setBlock(chunk, blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(),
                // Appelle une méthode
                placement.getBlock(), placement, null, doBlockUpdates, 0);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace, boolean doBlockUpdates) {
        // Appelle une méthode
        final Chunk chunk = getChunkAt(blockPosition);
        // Appelle une méthode
        Check.notNull(chunk, "You cannot break blocks in a null chunk!");
        // Embranchement : vérifie une condition
        if (chunk.isReadOnly()) return false;
        // Embranchement : vérifie une condition
        if (!isLoaded(chunk)) return false;

        // Appelle une méthode
        final Block block = getBlock(blockPosition);
        // Appelle une méthode
        final int x = blockPosition.blockX();
        // Appelle une méthode
        final int y = blockPosition.blockY();
        // Appelle une méthode
        final int z = blockPosition.blockZ();
        // Embranchement : vérifie une condition
        if (block.isAir()) {
            // The player probably have a wrong version of this chunk section, send it
            // Appelle une méthode
            chunk.sendChunk(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        PlayerBlockBreakEvent blockBreakEvent = new PlayerBlockBreakEvent(player, block, Block.AIR, new BlockVec(blockPosition), blockFace);
        // Appelle une méthode
        EventDispatcher.call(blockBreakEvent);
        // Appelle une méthode
        final boolean allowed = !blockBreakEvent.isCancelled();
        // Embranchement : vérifie une condition
        if (allowed) {
            // Break or change the broken block based on event result
            // Appelle une méthode
            final Block resultBlock = blockBreakEvent.getResultBlock();
            // Instruction de code
            UNSAFE_setBlock(chunk, x, y, z, resultBlock, null,
                    // Crée un nouvel objet
                    new BlockHandler.PlayerDestroy(block, resultBlock, this, blockPosition, player), doBlockUpdates, 0);
            // Send the block break effect packet
            // Instruction de code
            PacketSendingUtils.sendGroupedPacket(chunk.getViewers(),
                    // Crée un nouvel objet
                    new WorldEventPacket(WorldEvent.PARTICLES_DESTROY_BLOCK.id(), blockPosition, block.stateId(), false),
                    // Prevent the block breaker to play the particles and sound two times
                    // Appelle une méthode
                    (viewer) -> !viewer.equals(player));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return allowed;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Chunk> loadChunk(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return loadOrRetrieve(chunkX, chunkZ, () -> retrieveChunk(chunkX, chunkZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Chunk> loadOptionalChunk(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return loadOrRetrieve(chunkX, chunkZ, () -> hasEnabledAutoChunkLoad() ? retrieveChunk(chunkX, chunkZ) : AsyncUtils.empty());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void unloadChunk(Chunk chunk) {
        // Embranchement : vérifie une condition
        if (!isLoaded(chunk)) return;
        // Appelle une méthode
        final int chunkX = chunk.getChunkX();
        // Appelle une méthode
        final int chunkZ = chunk.getChunkZ();
        // Appelle une méthode
        chunk.sendPacketToViewers(new UnloadChunkPacket(chunkX, chunkZ));
        // Appelle une méthode
        EventDispatcher.call(new InstanceChunkUnloadEvent(this, chunk));
        // Remove all entities in chunk
        // Appelle une méthode
        getEntityTracker().chunkEntities(chunkX, chunkZ, EntityTracker.Target.ENTITIES).forEach(Entity::remove);
        // Clear cache
        // Accès à l'objet courant/parent
        this.chunks.remove(CoordConversion.chunkIndex(chunkX, chunkZ));
        // Appelle une méthode
        chunk.unload();
        // Appelle une méthode
        chunkLoader.unloadChunk(chunk);
        // Appelle une méthode
        var dispatcher = MinecraftServer.process().dispatcher();
        // Appelle une méthode
        dispatcher.deletePartition(chunk);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Chunk getChunk(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return chunks.get(CoordConversion.chunkIndex(chunkX, chunkZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> saveInstance() {
        // Affecte une valeur
        final ChunkLoader chunkLoader = this.chunkLoader;
        // Renvoie une valeur à l'appelant
        return optionalAsync(chunkLoader.supportsParallelSaving(), () -> chunkLoader.saveInstance(this));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> saveChunkToStorage(Chunk chunk) {
        // Affecte une valeur
        final ChunkLoader chunkLoader = this.chunkLoader;
        // Renvoie une valeur à l'appelant
        return optionalAsync(chunkLoader.supportsParallelSaving(), () -> chunkLoader.saveChunk(chunk));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> saveChunksToStorage() {
        // Affecte une valeur
        final ChunkLoader chunkLoader = this.chunkLoader;
        // Renvoie une valeur à l'appelant
        return optionalAsync(chunkLoader.supportsParallelSaving(), () -> chunkLoader.saveChunks(getChunks()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private CompletableFuture<Void> optionalAsync(boolean async, Runnable runnable) {
        // Embranchement : vérifie une condition
        if (!async) {
            // Appelle une méthode
            runnable.run();
            // Renvoie une valeur à l'appelant
            return CompletableFuture.completedFuture(null);
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final CompletableFuture<Void> future = new CompletableFuture<>();
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                runnable.run();
                // Appelle une méthode
                future.complete(null);
            // Début d'une méthode/d'un bloc
            } catch (Throwable e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Renvoie une valeur à l'appelant
        return future;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected CompletableFuture<Chunk> retrieveChunk(int chunkX, int chunkZ) {
        // Affecte une valeur
        CompletableFuture<Chunk> completableFuture = new CompletableFuture<>();
        // Appelle une méthode
        final long index = CoordConversion.chunkIndex(chunkX, chunkZ);
        // Appelle une méthode
        final CompletableFuture<Chunk> prev = loadingChunks.putIfAbsent(index, completableFuture);
        // Embranchement : vérifie une condition
        if (prev != null) return prev;
        // Affecte une valeur
        final ChunkLoader loader = chunkLoader;
        // Affecte une valeur
        final Consumer<Chunk> generate = chunk -> {
            // Embranchement : vérifie une condition
            if (chunk == null) {
                // Loader couldn't load the chunk, generate it
                // Appelle une méthode
                var chunkGeneration = EventsJFR.newChunkGeneration(getUuid(), chunkX, chunkZ);
                // Appelle une méthode
                chunkGeneration.begin();
                // Appelle une méthode
                chunk = createChunk(chunkX, chunkZ);
                // Appelle une méthode
                chunk.onGenerate();
                // Appelle une méthode
                chunkGeneration.commit();
            // Fin d'un bloc/d'une expression
            }

            // TODO run in the instance thread?
            // Appelle une méthode
            cacheChunk(chunk);
            // Appelle une méthode
            chunk.onLoad();

            // Appelle une méthode
            EventDispatcher.call(new InstanceChunkLoadEvent(this, chunk));
            // Appelle une méthode
            final CompletableFuture<Chunk> future = this.loadingChunks.remove(index);
            // Instruction de code
            assert future == completableFuture : "Invalid future: " + future;
            // Appelle une méthode
            completableFuture.complete(chunk);
        // Fin d'un bloc/d'une expression
        };
        // Affecte une valeur
        Supplier<Chunk> loaderSupplier = () -> {
            // Appelle une méthode
            var chunkLoading = EventsJFR.newChunkLoading(getUuid(), loader.getClass(), chunkX, chunkZ);
            // Appelle une méthode
            chunkLoading.begin();
            // Appelle une méthode
            final Chunk chunk = loader.loadChunk(this, chunkX, chunkZ);
            // Appelle une méthode
            chunkLoading.end();
            // Embranchement : vérifie une condition
            if (chunk != null) chunkLoading.commit();
            // Renvoie une valeur à l'appelant
            return chunk;
        // Fin d'un bloc/d'une expression
        };
        // Embranchement : vérifie une condition
        if (loader.supportsParallelLoading()) {
            // Début d'une méthode/d'un bloc
            Thread.startVirtualThread(() -> {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final Chunk chunk = loaderSupplier.get();
                    // Appelle une méthode
                    generate.accept(chunk);
                // Début d'une méthode/d'un bloc
                } catch (Throwable e) {
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            final Chunk chunk = loaderSupplier.get();
            // Début d'une méthode/d'un bloc
            Thread.startVirtualThread(() -> {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    generate.accept(chunk);
                // Début d'une méthode/d'un bloc
                } catch (Throwable e) {
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return completableFuture;
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    Map<Long, List<GeneratorImpl.SectionModifierImpl>> generationForks = new ConcurrentHashMap<>();

    // Début d'une méthode/d'un bloc
    protected Chunk createChunk(int chunkX, int chunkZ) {
        // Appelle une méthode
        final Chunk chunk = chunkSupplier.createChunk(this, chunkX, chunkZ);
        // Appelle une méthode
        Check.notNull(chunk, "Chunks supplied by a ChunkSupplier cannot be null.");
        // Appelle une méthode
        Generator generator = generator();
        // Embranchement : vérifie une condition
        if (generator == null || !chunk.shouldGenerate()) {
            // No chunk generator, execute the callback with the empty chunk
            // Appelle une méthode
            processFork(chunk);
            // Renvoie une valeur à l'appelant
            return chunk;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        generateChunk(chunk, generator);
        // Renvoie une valeur à l'appelant
        return chunk;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void generateChunk(Chunk chunk, Generator generator) {
        // Appelle une méthode
        final int chunkX = chunk.getChunkX(), chunkZ = chunk.getChunkZ();
        // Appelle une méthode
        GeneratorImpl.GenSection[] genSections = new GeneratorImpl.GenSection[chunk.getSections().size()];
        // Début d'une méthode/d'un bloc
        Arrays.setAll(genSections, i -> {
            // Appelle une méthode
            Section section = chunk.getSections().get(i);
            // Renvoie une valeur à l'appelant
            return new GeneratorImpl.GenSection(section.blockPalette(), section.biomePalette());
        // Fin d'un bloc/d'une expression
        });
        // Affecte une valeur
        var chunkUnit = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), genSections,
                // Appelle une méthode
                chunk.getChunkX(), chunk.minSection, chunk.getChunkZ());
        // Gestion des exceptions
        try {
            // Generate block/biome palette
            // Appelle une méthode
            generator.generate(chunkUnit);
            // Apply nbt/handler
            // Embranchement : vérifie une condition
            if (chunkUnit.modifier() instanceof GeneratorImpl.AreaModifierImpl chunkModifier) {
                // Boucle : répète un bloc
                for (var section : chunkModifier.sections()) {
                    // Embranchement : vérifie une condition
                    if (section.modifier() instanceof GeneratorImpl.SectionModifierImpl sectionModifier) {
                        // Appelle une méthode
                        applyGenerationData(chunk, sectionModifier);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Register forks or apply locally
            // Boucle : répète un bloc
            for (var fork : chunkUnit.forks()) {
                // Appelle une méthode
                var sections = ((GeneratorImpl.AreaModifierImpl) fork.modifier()).sections();
                // Boucle : répète un bloc
                for (var section : sections) {
                    // Embranchement : vérifie une condition
                    if (section.modifier() instanceof GeneratorImpl.SectionModifierImpl sectionModifier) {
                        // Embranchement : vérifie une condition
                        if (sectionModifier.genSection().blocks().count() == 0)
                            // Passe à l'itération suivante de la boucle
                            continue;
                        // Appelle une méthode
                        final Point start = section.absoluteStart();
                        // Appelle une méthode
                        final Chunk forkChunk = start.chunkX() == chunkX && start.chunkZ() == chunkZ ? chunk : getChunkAt(start);
                        // Embranchement : vérifie une condition
                        if (forkChunk != null) {
                            // Appelle une méthode
                            applyFork(forkChunk, sectionModifier);
                            // Update players
                            // Boucle : répète un bloc
                            forkChunk.invalidate();
                            // Boucle : répète un bloc
                            forkChunk.sendChunk();
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            final long index = CoordConversion.chunkIndex(start);
                            // Accès à l'objet courant/parent
                            this.generationForks.compute(index, (i, sectionModifiers) -> {
                                // Embranchement : vérifie une condition
                                if (sectionModifiers == null) sectionModifiers = new ArrayList<>();
                                // Appelle une méthode
                                sectionModifiers.add(sectionModifier);
                                // Renvoie une valeur à l'appelant
                                return sectionModifiers;
                            // Fin d'un bloc/d'une expression
                            });
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Apply awaiting forks
            // Appelle une méthode
            processFork(chunk);
        // Début d'une méthode/d'un bloc
        } catch (Throwable e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Début d'une méthode/d'un bloc
        } finally {
            // End generation
            // Appelle une méthode
            refreshLastBlockChangeTime();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processFork(Chunk chunk) {
        // Accès à l'objet courant/parent
        this.generationForks.compute(CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ()), (aLong, sectionModifiers) -> {
            // Embranchement : vérifie une condition
            if (sectionModifiers != null) {
                // Boucle : répète un bloc
                for (var sectionModifier : sectionModifiers) {
                    // Appelle une méthode
                    applyFork(chunk, sectionModifier);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void applyFork(Chunk chunk, GeneratorImpl.SectionModifierImpl sectionModifier) {
        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Appelle une méthode
            Section section = chunk.getSectionAt(sectionModifier.start().blockY());
            // Appelle une méthode
            Palette currentBlocks = section.blockPalette();
            // -1 is necessary because forked units handle explicit changes by changing AIR 0 to 1
            // Appelle une méthode
            sectionModifier.genSection().blocks().getAllPresent((x, y, z, value) -> currentBlocks.set(x, y, z, value - 1));
            // Appelle une méthode
            applyGenerationData(chunk, sectionModifier);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void applyGenerationData(Chunk chunk, GeneratorImpl.SectionModifierImpl section) {
        // Appelle une méthode
        var cache = section.genSection().specials();
        // Embranchement : vérifie une condition
        if (cache.isEmpty()) return;
        // Appelle une méthode
        final int height = section.start().blockY();
        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Début d'une méthode/d'un bloc
            Int2ObjectMaps.fastForEach(cache, blockEntry -> {
                // Appelle une méthode
                final int index = blockEntry.getIntKey();
                // Appelle une méthode
                final Block block = blockEntry.getValue();
                // Appelle une méthode
                final int x = CoordConversion.chunkBlockIndexGetX(index);
                // Appelle une méthode
                final int y = CoordConversion.chunkBlockIndexGetY(index) + height;
                // Appelle une méthode
                final int z = CoordConversion.chunkBlockIndexGetZ(index);
                // Appelle une méthode
                chunk.setBlock(x, y, z, block);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void enableAutoChunkLoad(boolean enable) {
        // Accès à l'objet courant/parent
        this.autoChunkLoad = enable;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean hasEnabledAutoChunkLoad() {
        // Renvoie une valeur à l'appelant
        return autoChunkLoad;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isInVoid(Point point) {
        // TODO: more customizable
        // Renvoie une valeur à l'appelant
        return point.y() < getCachedDimensionType().minY() - 64;
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setChunkSupplier(ChunkSupplier chunkSupplier) {
        // Accès à l'objet courant/parent
        this.chunkSupplier = chunkSupplier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current {@link ChunkSupplier}.
     * <p>
     * You shouldn't use it to generate a new chunk, but as a way to view which one is currently in use.
     *
     * @return the current {@link ChunkSupplier}
     */
    // Début d'une méthode/d'un bloc
    public ChunkSupplier getChunkSupplier() {
        // Renvoie une valeur à l'appelant
        return chunkSupplier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the {@link SharedInstance} linked to this container.
     *
     * @return an unmodifiable {@link List} containing all the {@link SharedInstance} linked to this container
     */
    // Début d'une méthode/d'un bloc
    public List<SharedInstance> getSharedInstances() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableList(sharedInstances);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this instance has {@link SharedInstance} linked to it.
     *
     * @return true if {@link #getSharedInstances()} is not empty
     */
    // Début d'une méthode/d'un bloc
    public boolean hasSharedInstances() {
        // Renvoie une valeur à l'appelant
        return !sharedInstances.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Assigns a {@link SharedInstance} to this container.
     * <p>
     * Only used by {@link InstanceManager}, mostly unsafe.
     *
     * @param sharedInstance the shared instance to assign to this container
     */
    // Début d'une méthode/d'un bloc
    protected void addSharedInstance(SharedInstance sharedInstance) {
        // Accès à l'objet courant/parent
        this.sharedInstances.add(sharedInstance);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public synchronized InstanceContainer copy() {
        // Appelle une méthode
        InstanceContainer copiedInstance = new InstanceContainer(UUID.randomUUID(), getDimensionType());
        // Affecte une valeur
        copiedInstance.srcInstance = this;
        // Appelle une méthode
        copiedInstance.tagHandler = this.tagHandler.copy();
        // Affecte une valeur
        copiedInstance.lastBlockChangeTime = this.lastBlockChangeTime;
        // Boucle : répète un bloc
        for (Chunk chunk : chunks.values()) {
            // Appelle une méthode
            final int chunkX = chunk.getChunkX();
            // Appelle une méthode
            final int chunkZ = chunk.getChunkZ();
            // Appelle une méthode
            final Chunk copiedChunk = chunk.copy(copiedInstance, chunkX, chunkZ);
            // Appelle une méthode
            copiedInstance.cacheChunk(copiedChunk);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return copiedInstance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the instance from which this one has been copied.
     * <p>
     * Only present if this instance has been created with {@link InstanceContainer#copy()}.
     *
     * @return the instance source, null if not created by a copy
     * @see #copy() to create a copy of this instance with 'this' as the source
     */
    // Début d'une méthode/d'un bloc
    public @Nullable InstanceContainer getSrcInstance() {
        // Renvoie une valeur à l'appelant
        return srcInstance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the last time at which a block changed.
     *
     * @return the time at which the last block changed in nanoseconds. Only use this to calculate delta times
     */
    // Début d'une méthode/d'un bloc
    public long getLastBlockChangeTime() {
        // Renvoie une valeur à l'appelant
        return lastBlockChangeTime;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Signals the instance that a block changed.
     * <p>
     * Useful if you change blocks values directly using a {@link Chunk} object.
     */
    // Début d'une méthode/d'un bloc
    public void refreshLastBlockChangeTime() {
        // Accès à l'objet courant/parent
        this.lastBlockChangeTime = System.nanoTime();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Generator generator() {
        // Renvoie une valeur à l'appelant
        return generator;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setGenerator(@Nullable Generator generator) {
        // Accès à l'objet courant/parent
        this.generator = generator;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> generateChunk(int chunkX, int chunkZ, Generator generator) {
        // Affecte une valeur
        CompletableFuture<Void> future = new CompletableFuture<>();
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // Appelle une méthode
            Chunk chunk = loadChunk(chunkX, chunkZ).join();
            // Début d'une méthode/d'un bloc
            synchronized (chunk) {
                // Appelle une méthode
                generateChunk(chunk, generator);
                // Appelle une méthode
                chunk.invalidate();
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            chunk.sendChunk();
            // Appelle une méthode
            future.complete(null);
        // Fin d'un bloc/d'une expression
        });
        // Renvoie une valeur à l'appelant
        return future;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the instance chunks.
     *
     * @return the chunks of this instance
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Chunk> getChunks() {
        // Renvoie une valeur à l'appelant
        return chunks.values();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ChunkLoader} of this instance.
     *
     * @return the {@link ChunkLoader} of this instance
     */
    // Début d'une méthode/d'un bloc
    public ChunkLoader getChunkLoader() {
        // Renvoie une valeur à l'appelant
        return chunkLoader;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link ChunkLoader} of this instance (to change how chunks are retrieved when not already loaded).
     *
     * <p>{@link ChunkLoader#noop()} can be used to do nothing.</p>
     *
     * @param chunkLoader the new {@link ChunkLoader}
     */
    // Début d'une méthode/d'un bloc
    public void setChunkLoader(ChunkLoader chunkLoader) {
        // Accès à l'objet courant/parent
        this.chunkLoader = Objects.requireNonNull(chunkLoader, "Chunk loader cannot be null");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Time/world border
        // Accès à l'objet courant/parent
        super.tick(time);
        // Clear block change map
        // Affecte une valeur
        Lock wrlock = this.changingBlockLock;
        // Appelle une méthode
        wrlock.lock();
        // Accès à l'objet courant/parent
        this.currentlyChangingBlocks.clear();
        // Appelle une méthode
        wrlock.unlock();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Has this block already changed since last update?
     * Prevents StackOverflow with blocks trying to modify their position in onDestroy or onPlace.
     *
     * @param blockPosition the block position
     * @param block         the block
     * @return true if the block changed since the last update
     */
    // Début d'une méthode/d'un bloc
    private boolean isAlreadyChanged(BlockVec blockPosition, Block block) {
        // Appelle une méthode
        final Block changedBlock = currentlyChangingBlocks.get(blockPosition);
        // Renvoie une valeur à l'appelant
        return Objects.equals(changedBlock, block);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Executed when a block is modified, this is used to modify the states of neighbours blocks.
     * <p>
     * For example, this can be used for redstone wires which need an understanding of its neighborhoods to take the right shape.
     *
     * @param blockPosition the position of the modified block
     */
    // Début d'une méthode/d'un bloc
    private void executeNeighboursBlockPlacementRule(Point blockPosition, int updateDistance) {
        // Appelle une méthode
        ChunkCache cache = new ChunkCache(this, null, null);
        // Boucle : répète un bloc
        for (var updateFace : BLOCK_UPDATE_FACES) {
            // Appelle une méthode
            var direction = updateFace.toDirection();
            // Appelle une méthode
            final int neighborX = blockPosition.blockX() + direction.normalX();
            // Appelle une méthode
            final int neighborY = blockPosition.blockY() + direction.normalY();
            // Appelle une méthode
            final int neighborZ = blockPosition.blockZ() + direction.normalZ();
            // Embranchement : vérifie une condition
            if (neighborY < getCachedDimensionType().minY() || neighborY > getCachedDimensionType().height())
                // Passe à l'itération suivante de la boucle
                continue;
            // Appelle une méthode
            final Block neighborBlock = cache.getBlock(neighborX, neighborY, neighborZ, Condition.NONE);
            // Embranchement : vérifie une condition
            if (neighborBlock == null || neighborBlock.isAir())
                // Passe à l'itération suivante de la boucle
                continue;
            // Appelle une méthode
            final BlockPlacementRule neighborBlockPlacementRule = MinecraftServer.getBlockManager().getBlockPlacementRule(neighborBlock);
            // Embranchement : vérifie une condition
            if (neighborBlockPlacementRule == null || updateDistance >= neighborBlockPlacementRule.maxUpdateDistance())
                // Passe à l'itération suivante de la boucle
                continue;

            // Appelle une méthode
            final Vec neighborPosition = new Vec(neighborX, neighborY, neighborZ);
            // Affecte une valeur
            final Block newNeighborBlock = neighborBlockPlacementRule.blockUpdate(new BlockPlacementRule.UpdateState(
                    // Instruction de code
                    this,
                    // Instruction de code
                    neighborPosition,
                    // Instruction de code
                    neighborBlock,
                    // Instruction de code
                    updateFace.getOppositeFace()
            // Instruction de code
            ));
            // Embranchement : vérifie une condition
            if (neighborBlock != newNeighborBlock) {
                // Appelle une méthode
                final Chunk chunk = getChunkAt(neighborPosition);
                // Embranchement : vérifie une condition
                if (!isLoaded(chunk)) continue;
                // Instruction de code
                UNSAFE_setBlock(chunk, neighborPosition.blockX(), neighborPosition.blockY(), neighborPosition.blockZ(), newNeighborBlock,
                        // Instruction de code
                        null, null, true, updateDistance + 1);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private CompletableFuture<Chunk> loadOrRetrieve(int chunkX, int chunkZ, Supplier<CompletableFuture<Chunk>> supplier) {
        // Appelle une méthode
        final Chunk chunk = getChunk(chunkX, chunkZ);
        // Embranchement : vérifie une condition
        if (chunk != null) {
            // Chunk already loaded
            // Renvoie une valeur à l'appelant
            return CompletableFuture.completedFuture(chunk);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return supplier.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void cacheChunk(Chunk chunk) {
        // Accès à l'objet courant/parent
        this.chunks.put(CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ()), chunk);
        // Appelle une méthode
        var dispatcher = MinecraftServer.process().dispatcher();
        // Appelle une méthode
        dispatcher.createPartition(chunk);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
