// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identified;
// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointered;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointers;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.PointersSupplier;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ExperienceOrb;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.EventHandler;
// Import d'une classe nécessaire
import net.minestom.server.event.EventNode;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceSectionInvalidateEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceTickEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.InstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.Generator;
// Import d'une classe nécessaire
import net.minestom.server.instance.light.Light;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.InitializeWorldBorderPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TimeUpdatePacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.*;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Taggable;
// Import d'une classe nécessaire
import net.minestom.server.thread.ThreadDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.timer.Schedulable;
// Import d'une classe nécessaire
import net.minestom.server.timer.Scheduler;
// Import d'une classe nécessaire
import net.minestom.server.utils.ArrayUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkCache;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnmodifiableView;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

/**
 * Instances are what are called "worlds" in Minecraft, you can add an entity in it using {@link Entity#setInstance(Instance)}.
 * <p>
 * An instance has entities and chunks, each instance contains its own entity list but the
 * chunk implementation has to be defined, see {@link InstanceContainer}.
 * <p>
 * WARNING: when making your own implementation registering the instance manually is required
 * with {@link InstanceManager#registerInstance(Instance)}, and
 * you need to be sure to signal the {@link ThreadDispatcher} of every partition/element changes.
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class Instance implements Block.Getter, Block.Setter, Biome.Getter, Biome.Setter,
        // Début d'une méthode/d'un bloc
        Tickable, Schedulable, Snapshotable, EventHandler<InstanceEvent>, Taggable, PacketGroupingAudience, Pointered, Identified {

    // Adventure pointers
    // Affecte une valeur
    protected static final PointersSupplier<Instance> INSTANCE_POINTERS_SUPPLIER = PointersSupplier.<Instance>builder()
            // Instruction de code
            .resolving(Identity.UUID, Instance::getUuid)
            // Appelle une méthode
            .build();

    // Instruction de code
    private boolean registered;

    // Instruction de code
    private final RegistryKey<DimensionType> dimensionType;
    // Instruction de code
    private final DimensionType cachedDimensionType; // Cached to prevent self-destruction if the registry is changed, and to avoid the lookups.
    // Instruction de code
    private final String dimensionName;

    // World border of the instance
    // Instruction de code
    private WorldBorder worldBorder;
    // Instruction de code
    private double targetBorderDiameter;
    // Instruction de code
    private long remainingWorldBorderTransitionTicks;

    // Tick since the creation of the instance
    // Instruction de code
    private long worldAge;

    // The time of the instance
    // Instruction de code
    private long time;
    // Affecte une valeur
    private int timeRate = 1;
    // Affecte une valeur
    private int timeSynchronizationTicks = ServerFlag.SERVER_TICKS_PER_SECOND;

    // Weather of the instance
    // Affecte une valeur
    private Weather weather = Weather.CLEAR;
    // Affecte une valeur
    private Weather transitioningWeather = Weather.CLEAR;
    // Instruction de code
    private int remainingRainTransitionTicks;
    // Instruction de code
    private int remainingThunderTransitionTicks;

    // Attached boss bars
    // Affecte une valeur
    private final Set<BossBar> bossBars = new CopyOnWriteArraySet<>();

    // Field for tick events
    // Appelle une méthode
    private long lastTickAge = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

    // Appelle une méthode
    private final EntityTracker entityTracker = new EntityTrackerImpl();

    // Appelle une méthode
    private final ChunkCache blockRetriever = new ChunkCache(this, null, null);

    // Affecte une valeur
    protected int chunkViewDistance = ServerFlag.CHUNK_VIEW_DISTANCE;

    // the uuid of this instance
    // Instruction de code
    protected UUID uuid;

    // instance custom data
    // Appelle une méthode
    protected TagHandler tagHandler = TagHandler.newHandler();
    // Appelle une méthode
    private final Scheduler scheduler = Scheduler.newScheduler();
    // Instruction de code
    private final EventNode<InstanceEvent> eventNode;

    // the explosion supplier
    // Instruction de code
    private ExplosionSupplier explosionSupplier;

    /**
     * Creates a new instance.
     *
     * @param uuid          the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     */
    // Début d'une méthode/d'un bloc
    public Instance(UUID uuid, RegistryKey<DimensionType> dimensionType) {
        // Appelle une méthode
        this(uuid, dimensionType, dimensionType.key());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance.
     *
     * @param uuid          the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     */
    // Début d'une méthode/d'un bloc
    public Instance(UUID uuid, RegistryKey<DimensionType> dimensionType, Key dimensionName) {
        // Appelle une méthode
        this(MinecraftServer.getDimensionTypeRegistry(), uuid, dimensionType, dimensionName);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance.
     *
     * @param uuid          the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     */
    // Début d'une méthode/d'un bloc
    public Instance(DynamicRegistry<DimensionType> dimensionTypeRegistry, UUID uuid, RegistryKey<DimensionType> dimensionType, Key dimensionName) {
        // Accès à l'objet courant/parent
        this.uuid = uuid;
        // Accès à l'objet courant/parent
        this.dimensionType = dimensionType;
        // Accès à l'objet courant/parent
        this.cachedDimensionType = dimensionTypeRegistry.get(dimensionType);
        // Appelle une méthode
        Check.argCondition(cachedDimensionType == null, "The dimension " + dimensionType + " is not registered! Please add it to the registry (`MinecraftServer.getDimensionTypeRegistry().registry(dimensionType)`).");
        // Accès à l'objet courant/parent
        this.dimensionName = dimensionName.asString();

        // Accès à l'objet courant/parent
        this.worldBorder = WorldBorder.DEFAULT_BORDER;
        // Appelle une méthode
        targetBorderDiameter = this.worldBorder.diameter();

        // Appelle une méthode
        final ServerProcess process = MinecraftServer.process();
        // Embranchement : vérifie une condition
        if (process != null) {
            // Accès à l'objet courant/parent
            this.eventNode = process.eventHandler().map(this, EventFilter.INSTANCE);
        // Branche alternative de la condition
        } else {
            // Local nodes require a server process
            // Accès à l'objet courant/parent
            this.eventNode = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Schedules a task to be run during the next instance tick.
     *
     * @param callback the task to execute during the next instance tick
     */
    // Début d'une méthode/d'un bloc
    public void scheduleNextTick(Consumer<Instance> callback) {
        // Accès à l'objet courant/parent
        this.scheduler.scheduleNextTick(() -> callback.accept(this));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block) {
        // Appelle une méthode
        setBlock(x, y, z, block, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
        // Appelle une méthode
        Chunk chunk = getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Embranchement : vérifie une condition
        if (chunk == null) return;
        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Appelle une méthode
            chunk.setBiome(x, y, z, biome);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBlock(Point blockPosition, Block block, boolean doBlockUpdates) {
        // Appelle une méthode
        setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block, doBlockUpdates);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    public abstract void setBlock(int x, int y, int z, Block block, boolean doBlockUpdates);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public boolean placeBlock(BlockHandler.Placement placement) {
        // Renvoie une valeur à l'appelant
        return placeBlock(placement, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    public abstract boolean placeBlock(BlockHandler.Placement placement, boolean doBlockUpdates);

    /**
     * Does call {@link net.minestom.server.event.player.PlayerBlockBreakEvent}
     * and send particle packets
     *
     * @param player        the {@link Player} who break the block
     * @param blockPosition the position of the broken block
     * @return true if the block has been broken, false if it has been cancelled
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace) {
        // Renvoie une valeur à l'appelant
        return breakBlock(player, blockPosition, blockFace, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Does call {@link net.minestom.server.event.player.PlayerBlockBreakEvent}
     * and send particle packets
     *
     * @param player         the {@link Player} who break the block
     * @param blockPosition  the position of the broken block
     * @param doBlockUpdates true to do block updates, false otherwise
     * @return true if the block has been broken, false if it has been cancelled
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    public abstract boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace, boolean doBlockUpdates);

    /**
     * Forces the generation of a {@link Chunk}, even if no file and {@link Generator} are defined.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return a {@link CompletableFuture} completed once the chunk has been loaded
     */
    // Appelle une méthode
    public abstract CompletableFuture<Chunk> loadChunk(int chunkX, int chunkZ);

    /**
     * Loads the chunk at the given {@link Point} with a callback.
     *
     * @param point the chunk position
     */
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Chunk> loadChunk(Point point) {
        // Renvoie une valeur à l'appelant
        return loadChunk(point.chunkX(), point.chunkZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Loads the chunk if the chunk is already loaded or if
     * {@link #hasEnabledAutoChunkLoad()} returns true.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return a {@link CompletableFuture} completed once the chunk has been processed, can be null if not loaded
     */
    // Appelle une méthode
    public abstract CompletableFuture<@Nullable Chunk> loadOptionalChunk(int chunkX, int chunkZ);

    /**
     * Loads a {@link Chunk} (if {@link #hasEnabledAutoChunkLoad()} returns true)
     * at the given {@link Point} with a callback.
     *
     * @param point the chunk position
     * @return a {@link CompletableFuture} completed once the chunk has been processed, can be null if not loaded
     */
    // Début d'une méthode/d'un bloc
    public CompletableFuture<@Nullable Chunk> loadOptionalChunk(Point point) {
        // Renvoie une valeur à l'appelant
        return loadOptionalChunk(point.chunkX(), point.chunkZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Schedules the removal of a {@link Chunk}, this method does not promise when it will be done.
     * <p>
     * WARNING: during unloading, all entities other than {@link Player} will be removed.
     *
     * @param chunk the chunk to unload
     */
    // Appelle une méthode
    public abstract void unloadChunk(Chunk chunk);

    /**
     * Unloads the chunk at the given position.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    // Début d'une méthode/d'un bloc
    public void unloadChunk(int chunkX, int chunkZ) {
        // Appelle une méthode
        final Chunk chunk = getChunk(chunkX, chunkZ);
        // Appelle une méthode
        Check.notNull(chunk, "The chunk at {0}:{1} is already unloaded", chunkX, chunkZ);
        // Appelle une méthode
        unloadChunk(chunk);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void invalidateSection(int sectionX, int sectionY, int sectionZ) {
        // Appelle une méthode
        final Chunk chunk = getChunk(sectionX, sectionZ);
        // Embranchement : vérifie une condition
        if (chunk != null) {
            // Appelle une méthode
            Section section = chunk.getSection(sectionY);
            // Appelle une méthode
            section.skyLight().invalidate();
            // Appelle une méthode
            section.blockLight().invalidate();
            // Appelle une méthode
            chunk.invalidate();
            // Appelle une méthode
            EventDispatcher.call(new InstanceSectionInvalidateEvent(this, sectionX, sectionY, sectionZ));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the loaded {@link Chunk} at a position.
     * <p>
     * WARNING: this should only return already-loaded chunk, use {@link #loadChunk(int, int)} or similar to load one instead.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return the chunk at the specified position, null if not loaded
     */
    // Appelle une méthode
    public abstract @Nullable Chunk getChunk(int chunkX, int chunkZ);

    /**
     * @param chunkX the chunk X
     * @param chunkZ this chunk Z
     * @return true if the chunk is loaded
     */
    // Début d'une méthode/d'un bloc
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return getChunk(chunkX, chunkZ) != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param point coordinate of a block or other
     * @return true if the chunk is loaded
     */
    // Début d'une méthode/d'un bloc
    public boolean isChunkLoaded(Point point) {
        // Renvoie une valeur à l'appelant
        return isChunkLoaded(point.chunkX(), point.chunkZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Saves the current instance tags.
     * <p>
     * Warning: only the global instance data will be saved, not chunks.
     * You would need to call {@link #saveChunksToStorage()} too.
     *
     * @return the future called once the instance data has been saved
     */
    // Appelle une méthode
    public abstract CompletableFuture<Void> saveInstance();

    /**
     * Saves a {@link Chunk} to permanent storage.
     *
     * @param chunk the {@link Chunk} to save
     * @return future called when the chunk is done saving
     */
    // Appelle une méthode
    public abstract CompletableFuture<Void> saveChunkToStorage(Chunk chunk);

    /**
     * Saves multiple chunks to permanent storage.
     *
     * @return future called when the chunks are done saving
     */
    // Appelle une méthode
    public abstract CompletableFuture<Void> saveChunksToStorage();

    // Appelle une méthode
    public abstract void setChunkSupplier(ChunkSupplier chunkSupplier);

    /**
     * Gets the chunk supplier of the instance.
     *
     * @return the chunk supplier of the instance
     */
    // Appelle une méthode
    public abstract ChunkSupplier getChunkSupplier();

    /**
     * Gets the generator associated with the instance
     *
     * @return the generator if any
     */
    // Appelle une méthode
    public abstract @Nullable Generator generator();

    /**
     * Changes the generator of the instance
     *
     * @param generator the new generator, or null to disable generation
     */
    // Appelle une méthode
    public abstract void setGenerator(@Nullable Generator generator);

    /**
     * Runs the provided {@link Generator} to generate a chunk at the given position.
     * <p>
     * Loads the chunk if not already loaded.
     *
     * @param chunkX    the chunk X
     * @param chunkZ    the chunk Z
     * @param generator the generator to use
     * @return a future called once the generation is complete
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Appelle une méthode
    public abstract CompletableFuture<Void> generateChunk(int chunkX, int chunkZ, Generator generator);

    /**
     * Gets all the instance's loaded chunks.
     *
     * @return an unmodifiable containing all the instance chunks
     */
    // Appelle une méthode
    public abstract Collection<Chunk> getChunks();

    /**
     * When set to true, chunks will load automatically when requested.
     * Otherwise using {@link #loadChunk(int, int)} will be required to even spawn a player
     *
     * @param enable enable the auto chunk load
     */
    // Appelle une méthode
    public abstract void enableAutoChunkLoad(boolean enable);

    /**
     * Gets if the instance should auto load chunks.
     *
     * @return true if auto chunk load is enabled, false otherwise
     */
    // Appelle une méthode
    public abstract boolean hasEnabledAutoChunkLoad();

    /**
     * Determines whether a position in the void.
     *
     * @param point the point in the world
     * @return true if the point is inside the void
     */
    // Appelle une méthode
    public abstract boolean isInVoid(Point point);

    /**
     * Gets if the instance has been registered in {@link InstanceManager}.
     *
     * @return true if the instance has been registered
     */
    // Début d'une méthode/d'un bloc
    public boolean isRegistered() {
        // Renvoie une valeur à l'appelant
        return registered;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the registered field.
     * <p>
     * WARNING: should only be used by {@link InstanceManager}.
     *
     * @param registered true to mark the instance as registered
     */
    // Début d'une méthode/d'un bloc
    protected void setRegistered(boolean registered) {
        // Accès à l'objet courant/parent
        this.registered = registered;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the instance {@link DimensionType}.
     *
     * @return the dimension of the instance
     */
    // Début d'une méthode/d'un bloc
    public RegistryKey<DimensionType> getDimensionType() {
        // Renvoie une valeur à l'appelant
        return dimensionType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public DimensionType getCachedDimensionType() {
        // Renvoie une valeur à l'appelant
        return cachedDimensionType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the instance dimension name.
     *
     * @return the dimension name of the instance
     */
    // Début d'une méthode/d'un bloc
    public String getDimensionName() {
        // Renvoie une valeur à l'appelant
        return dimensionName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the age of this instance in tick.
     *
     * @return the age of this instance in tick
     */
    // Début d'une méthode/d'un bloc
    public long getWorldAge() {
        // Renvoie une valeur à l'appelant
        return worldAge;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the age of this instance in tick. It will send the age to all players.
     * Will send new age to all players in the instance, unaffected by {@link #getTimeSynchronizationTicks()}
     *
     * @param worldAge the age of this instance in tick
     */
    // Début d'une méthode/d'un bloc
    public void setWorldAge(long worldAge) {
        // Accès à l'objet courant/parent
        this.worldAge = worldAge;
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(getPlayers(), createTimePacket());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current time in the instance (sun/moon).
     *
     * @return the time in the instance
     */
    // Début d'une méthode/d'un bloc
    public long getTime() {
        // Renvoie une valeur à l'appelant
        return time;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the current time in the instance, from 0 to 24000.
     * <p>
     * If the time is negative, the vanilla client will not move the sun.
     * <p>
     * 0 = sunrise
     * 6000 = noon
     * 12000 = sunset
     * 18000 = midnight
     * <p>
     * This method is unaffected by {@link #getTimeRate()}
     * <p>
     * It does send the new time to all players in the instance, unaffected by {@link #getTimeSynchronizationTicks()}
     *
     * @param time the new time of the instance
     */
    // Début d'une méthode/d'un bloc
    public void setTime(long time) {
        // Accès à l'objet courant/parent
        this.time = time;
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(getPlayers(), createTimePacket());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the rate of the time passing, it is 1 by default
     *
     * @return the time rate of the instance
     */
    // Début d'une méthode/d'un bloc
    public int getTimeRate() {
        // Renvoie une valeur à l'appelant
        return timeRate;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the time rate of the instance
     * <p>
     * 1 is the default value and can be set to 0 to be completely disabled (constant time)
     *
     * @param timeRate the new time rate of the instance
     * @throws IllegalStateException if {@code timeRate} is lower than 0
     */
    // Début d'une méthode/d'un bloc
    public void setTimeRate(int timeRate) {
        // Appelle une méthode
        Check.stateCondition(timeRate < 0, "The time rate cannot be lower than 0");
        // Accès à l'objet courant/parent
        this.timeRate = timeRate;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the rate at which the client is updated with the current instance time
     *
     * @return the client update rate for time related packet
     */
    // Début d'une méthode/d'un bloc
    public int getTimeSynchronizationTicks() {
        // Renvoie une valeur à l'appelant
        return timeSynchronizationTicks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the natural client time packet synchronization period, defaults to {@link ServerFlag#SERVER_TICKS_PER_SECOND}.
     * <p>
     * Supplying 0 means that the client will never be synchronized with the current natural instance time
     * (time will still change server-side)
     *
     * @param timeSynchronizationTicks the rate to update time in ticks
     */
    // Début d'une méthode/d'un bloc
    public void setTimeSynchronizationTicks(int timeSynchronizationTicks) {
        // Appelle une méthode
        Check.stateCondition(timeSynchronizationTicks < 0, "The time Synchronization ticks cannot be lower than 0");
        // Accès à l'objet courant/parent
        this.timeSynchronizationTicks = timeSynchronizationTicks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link TimeUpdatePacket} with the current age and time of this instance
     *
     * @return the {@link TimeUpdatePacket} with this instance data
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public TimeUpdatePacket createTimePacket() {
        // Renvoie une valeur à l'appelant
        return new TimeUpdatePacket(worldAge, time, timeRate != 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current state of the instance {@link WorldBorder}.
     *
     * @return the {@link WorldBorder} for the instance of the current tick
     */
    // Début d'une méthode/d'un bloc
    public WorldBorder getWorldBorder() {
        // Renvoie une valeur à l'appelant
        return worldBorder;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Set the instance {@link WorldBorder} with a smooth transition.
     *
     * @param worldBorder    the desired final state of the world border
     * @param transitionTime the time in seconds this world border's diameter
     *                       will transition for (0 makes this instant)
     */
    // Début d'une méthode/d'un bloc
    public void setWorldBorder(WorldBorder worldBorder, double transitionTime) {
        // Appelle une méthode
        Check.stateCondition(transitionTime < 0, "Transition time cannot be lower than 0");
        // Affecte une valeur
        long transitionMilliseconds = (long) (transitionTime * 1000);
        // Appelle une méthode
        sendNewWorldBorderPackets(worldBorder, transitionMilliseconds);

        // Accès à l'objet courant/parent
        this.targetBorderDiameter = worldBorder.diameter();
        // Affecte une valeur
        long transitionTicks = transitionMilliseconds / MinecraftServer.TICK_MS;
        // Affecte une valeur
        remainingWorldBorderTransitionTicks = transitionTicks;
        // Embranchement : vérifie une condition
        if (transitionTicks == 0) this.worldBorder = worldBorder;
        // Branche alternative de la condition
        else this.worldBorder = worldBorder.withDiameter(this.worldBorder.diameter());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Set the instance {@link WorldBorder} with an instant transition.
     * see {@link Instance#setWorldBorder(WorldBorder, double)}.
     */
    // Début d'une méthode/d'un bloc
    public void setWorldBorder(WorldBorder worldBorder) {
        // Appelle une méthode
        setWorldBorder(worldBorder, 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates the {@link InitializeWorldBorderPacket} sent to players who join this instance.
     */
    // Début d'une méthode/d'un bloc
    public InitializeWorldBorderPacket createInitializeWorldBorderPacket() {
        // Renvoie une valeur à l'appelant
        return worldBorder.createInitializePacket(targetBorderDiameter, remainingWorldBorderTransitionTicks * MinecraftServer.TICK_MS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void sendNewWorldBorderPackets(WorldBorder newBorder, long transitionMilliseconds) {
        // Only send the relevant border packets
        // Embranchement : vérifie une condition
        if (this.worldBorder.diameter() != newBorder.diameter()) {
            // Embranchement : vérifie une condition
            if (transitionMilliseconds == 0) sendGroupedPacket(newBorder.createSizePacket());
            // Branche alternative de la condition
            else sendGroupedPacket(this.worldBorder.createLerpSizePacket(newBorder.diameter(), transitionMilliseconds));
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (this.worldBorder.centerX() != newBorder.centerX() || this.worldBorder.centerZ() != newBorder.centerZ()) {
            // Appelle une méthode
            sendGroupedPacket(newBorder.createCenterPacket());
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (this.worldBorder.warningTime() != newBorder.warningTime())
            // Appelle une méthode
            sendGroupedPacket(newBorder.createWarningDelayPacket());
        // Embranchement : vérifie une condition
        if (this.worldBorder.warningDistance() != newBorder.warningDistance())
            // Appelle une méthode
            sendGroupedPacket(newBorder.createWarningReachPacket());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private WorldBorder transitionWorldBorder(long remainingTicks) {
        // Embranchement : vérifie une condition
        if (remainingTicks <= 1) return worldBorder.withDiameter(targetBorderDiameter);
        // Renvoie une valeur à l'appelant
        return worldBorder.withDiameter(worldBorder.diameter() + (targetBorderDiameter - worldBorder.diameter()) * (1 / (double) remainingTicks));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entities in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the entities in the instance
     */
    // Début d'une méthode/d'un bloc
    public Set<Entity> getEntities() {
        // Renvoie une valeur à l'appelant
        return entityTracker.entities();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an entity based on its id (from {@link Entity#getEntityId()}).
     *
     * @param id the entity id
     * @return the entity having the specified id, null if not found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getEntityById(int id) {
        // Renvoie une valeur à l'appelant
        return entityTracker.getEntityById(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an entity based on its UUID (from {@link Entity#getUuid()}).
     *
     * @param uuid the entity UUID
     * @return the entity having the specified uuid, null if not found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getEntityByUuid(UUID uuid) {
        // Renvoie une valeur à l'appelant
        return entityTracker.getEntityByUuid(uuid);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a player based on its UUID (from {@link Entity#getUuid()}).
     *
     * @param uuid the player UUID
     * @return the player having the specified uuid, null if not found or not a player
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Player getPlayerByUuid(UUID uuid) {
        // Appelle une méthode
        Entity entity = entityTracker.getEntityByUuid(uuid);
        // Embranchement : vérifie une condition
        if (entity instanceof Player player) {
            // Renvoie une valeur à l'appelant
            return player;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the players in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the players in the instance
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<Player> getPlayers() {
        // Renvoie une valeur à l'appelant
        return entityTracker.entities(EntityTracker.Target.PLAYERS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the creatures in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the creatures in the instance
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Set<EntityCreature> getCreatures() {
        // Renvoie une valeur à l'appelant
        return entityTracker.entities().stream()
                // Instruction de code
                .filter(EntityCreature.class::isInstance)
                // Instruction de code
                .map(entity -> (EntityCreature) entity)
                // Appelle une méthode
                .collect(Collectors.toUnmodifiableSet());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the experience orbs in the instance.
     *
     * @return an unmodifiable {@link Set} containing all the experience orbs in the instance
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Set<ExperienceOrb> getExperienceOrbs() {
        // Renvoie une valeur à l'appelant
        return entityTracker.entities().stream()
                // Instruction de code
                .filter(ExperienceOrb.class::isInstance)
                // Instruction de code
                .map(entity -> (ExperienceOrb) entity)
                // Appelle une méthode
                .collect(Collectors.toUnmodifiableSet());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entities located in the chunk.
     *
     * @param chunk the chunk to get the entities from
     * @return an unmodifiable {@link Set} containing all the entities in a chunk,
     * if {@code chunk} is unloaded, return an empty {@link HashSet}
     */
    // Début d'une méthode/d'un bloc
    public Set<Entity> getChunkEntities(Chunk chunk) {
        // Appelle une méthode
        var chunkEntities = entityTracker.chunkEntities(chunk.toPosition(), EntityTracker.Target.ENTITIES);
        // Renvoie une valeur à l'appelant
        return ObjectArraySet.ofUnchecked(chunkEntities.toArray(Entity[]::new));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets nearby entities to the given position.
     *
     * @param point position to look at
     * @param range max range from the given point to collect entities at
     * @return entities that are not further than the specified distance from the transmitted position.
     */
    // Début d'une méthode/d'un bloc
    public Collection<Entity> getNearbyEntities(Point point, double range) {
        // Affecte une valeur
        List<Entity> result = new ArrayList<>();
        // Accès à l'objet courant/parent
        this.entityTracker.nearbyEntities(point, range, EntityTracker.Target.ENTITIES, result::add);
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Block getBlock(int x, int y, int z, Condition condition) {
        // Appelle une méthode
        final Block block = blockRetriever.getBlock(x, y, z, condition);
        // Embranchement : vérifie une condition
        if (block == null) throw new NullPointerException("Unloaded chunk at " + x + "," + y + "," + z);
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RegistryKey<Biome> getBiome(int x, int y, int z) {
        // Appelle une méthode
        Chunk chunk = getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Appelle une méthode
        Objects.requireNonNull(chunk);
        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Renvoie une valeur à l'appelant
            return chunk.getBiome(x, y, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a {@link BlockActionPacket} for all the viewers of the specific position.
     *
     * @param blockPosition the block position
     * @param actionId      the action id, depends on the block
     * @param actionParam   the action parameter, depends on the block
     * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Block_Action">BlockActionPacket</a> for the action id &amp; param
     */
    // Début d'une méthode/d'un bloc
    public void sendBlockAction(Point blockPosition, byte actionId, byte actionParam) {
        // Appelle une méthode
        final Block block = getBlock(blockPosition);
        // Appelle une méthode
        final Chunk chunk = getChunkAt(blockPosition);
        // Appelle une méthode
        Check.notNull(chunk, "The chunk at {0} is not loaded!", blockPosition);
        // Appelle une méthode
        chunk.sendPacketToViewers(new BlockActionPacket(blockPosition, actionId, actionParam, block));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link Chunk} at the given block position, null if not loaded.
     *
     * @param x the X position
     * @param z the Z position
     * @return the chunk at the given position, null if not loaded
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Chunk getChunkAt(double x, double z) {
        // Renvoie une valeur à l'appelant
        return getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link Chunk} at the given {@link Point}, null if not loaded.
     *
     * @param point the position
     * @return the chunk at the given position, null if not loaded
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Chunk getChunkAt(Point point) {
        // Renvoie une valeur à l'appelant
        return getChunk(point.chunkX(), point.chunkZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityTracker getEntityTracker() {
        // Renvoie une valeur à l'appelant
        return entityTracker;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the instance unique id.
     *
     * @return the instance unique id
     */
    // Début d'une méthode/d'un bloc
    public UUID getUuid() {
        // Renvoie une valeur à l'appelant
        return uuid;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Performs a single tick in the instance, including scheduled tasks from {@link #scheduleNextTick(Consumer)}.
     * <p>
     * Warning: this does not update chunks and entities.
     *
     * @param time the tick time in milliseconds, which may only be used as a delta and has no meaning in real life
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Scheduled tasks
        // Accès à l'objet courant/parent
        this.scheduler.processTick();
        // Time
        // Début d'un bloc
        {
            // Accès à l'objet courant/parent
            this.worldAge++;
            // Accès à l'objet courant/parent
            this.time += timeRate;
            // time needs to be sent to players
            // Embranchement : vérifie une condition
            if (timeSynchronizationTicks > 0 && this.worldAge % timeSynchronizationTicks == 0) {
                // Appelle une méthode
                PacketSendingUtils.sendGroupedPacket(getPlayers(), createTimePacket());
            // Fin d'un bloc/d'une expression
            }

        // Fin d'un bloc/d'une expression
        }
        // Weather
        // Embranchement : vérifie une condition
        if (remainingRainTransitionTicks > 0 || remainingThunderTransitionTicks > 0) {
            // Affecte une valeur
            Weather previousWeather = transitioningWeather;
            // Appelle une méthode
            transitioningWeather = transitionWeather(remainingRainTransitionTicks, remainingThunderTransitionTicks);
            // Appelle une méthode
            sendWeatherPackets(previousWeather);
            // Appelle une méthode
            remainingRainTransitionTicks = Math.max(0, remainingRainTransitionTicks - 1);
            // Appelle une méthode
            remainingThunderTransitionTicks = Math.max(0, remainingThunderTransitionTicks - 1);
        // Fin d'un bloc/d'une expression
        }
        // Tick event
        // Début d'un bloc
        {
            // Process tick events
            // Appelle une méthode
            EventDispatcher.call(new InstanceTickEvent(this, time, lastTickAge));
            // Set last tick age
            // Accès à l'objet courant/parent
            this.lastTickAge = time;
        // Fin d'un bloc/d'une expression
        }
        // World border
        // Embranchement : vérifie une condition
        if (remainingWorldBorderTransitionTicks > 0) {
            // Appelle une méthode
            worldBorder = transitionWorldBorder(remainingWorldBorderTransitionTicks);
            // Embranchement : vérifie une condition
            if (worldBorder.diameter() == targetBorderDiameter) remainingWorldBorderTransitionTicks = 0;
            // Branche alternative de la condition
            else remainingWorldBorderTransitionTicks--;
        // Fin d'un bloc/d'une expression
        }
        // End of tick scheduled tasks
        // Accès à l'objet courant/parent
        this.scheduler.processTickEnd();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the weather of this instance
     *
     * @return the instance weather
     */
    // Début d'une méthode/d'un bloc
    public Weather getWeather() {
        // Renvoie une valeur à l'appelant
        return weather;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the weather on this instance, transitions over time
     *
     * @param weather         the new weather
     * @param transitionTicks the ticks to transition to new weather
     */
    // Début d'une méthode/d'un bloc
    public void setWeather(Weather weather, int transitionTicks) {
        // Appelle une méthode
        Check.stateCondition(transitionTicks < 1, "Transition ticks cannot be lower than 0");
        // Accès à l'objet courant/parent
        this.weather = weather;
        // Affecte une valeur
        remainingRainTransitionTicks = transitionTicks;
        // Affecte une valeur
        remainingThunderTransitionTicks = transitionTicks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the weather of this instance with a fixed transition
     *
     * @param weather the new weather
     */
    // Début d'une méthode/d'un bloc
    public void setWeather(Weather weather) {
        // Accès à l'objet courant/parent
        this.weather = weather;
        // Appelle une méthode
        remainingRainTransitionTicks = (int) Math.max(1, Math.abs((this.weather.rainLevel() - transitioningWeather.rainLevel()) / 0.01));
        // Appelle une méthode
        remainingThunderTransitionTicks = (int) Math.max(1, Math.abs((this.weather.thunderLevel() - transitioningWeather.thunderLevel()) / 0.01));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void sendWeatherPackets(Weather previousWeather) {
        // Appelle une méthode
        boolean toggledRain = (transitioningWeather.isRaining() != previousWeather.isRaining());
        // Embranchement : vérifie une condition
        if (toggledRain) sendGroupedPacket(transitioningWeather.createIsRainingPacket());
        // Embranchement : vérifie une condition
        if (transitioningWeather.rainLevel() != previousWeather.rainLevel())
            // Appelle une méthode
            sendGroupedPacket(transitioningWeather.createRainLevelPacket());
        // Embranchement : vérifie une condition
        if (transitioningWeather.thunderLevel() != previousWeather.thunderLevel())
            // Appelle une méthode
            sendGroupedPacket(transitioningWeather.createThunderLevelPacket());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Weather transitionWeather(int remainingRainTransitionTicks, int remainingThunderTransitionTicks) {
        // Affecte une valeur
        Weather target = weather;
        // Affecte une valeur
        Weather current = transitioningWeather;
        // Appelle une méthode
        float rainLevel = current.rainLevel() + (target.rainLevel() - current.rainLevel()) * (1 / (float) Math.max(1, remainingRainTransitionTicks));
        // Appelle une méthode
        float thunderLevel = current.thunderLevel() + (target.thunderLevel() - current.thunderLevel()) * (1 / (float) Math.max(1, remainingThunderTransitionTicks));
        // Renvoie une valeur à l'appelant
        return new Weather(rainLevel, thunderLevel);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chunk view distance of this instance, which defaults to {@link ServerFlag#CHUNK_VIEW_DISTANCE}.
     *
     * @return The chunk view distance of this instance
     */
    // Début d'une méthode/d'un bloc
    public int viewDistance() {
        // Renvoie une valeur à l'appelant
        return this.chunkViewDistance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the chunk view distance of this instance
     *
     * @param newViewDistance the new view distance
     */
    // Début d'une méthode/d'un bloc
    public void viewDistance(int newViewDistance) {
        // Accès à l'objet courant/parent
        this.chunkViewDistance = newViewDistance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shows a {@link BossBar} to all players in the instance and tracks it.
     *
     * @param bar a boss bar
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void showBossBar(BossBar bar) {
        // Appelle une méthode
        Check.notNull(bar, "Boss bar cannot be null");
        // Embranchement : vérifie une condition
        if (!bossBars.add(bar)) return;
        // Appelle une méthode
        PacketGroupingAudience.super.showBossBar(bar);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Hides a {@link BossBar} from all players in the instance and stops tracking it.
     *
     * @param bar a boss bar
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void hideBossBar(BossBar bar) {
        // Appelle une méthode
        Check.notNull(bar, "Boss bar cannot be null");
        // Embranchement : vérifie une condition
        if (!bossBars.remove(bar)) return;
        // Appelle une méthode
        PacketGroupingAudience.super.hideBossBar(bar);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public @UnmodifiableView Set<BossBar> bossBars() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(bossBars);
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Scheduler scheduler() {
        // Renvoie une valeur à l'appelant
        return scheduler;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public EventNode<InstanceEvent> eventNode() {
        // Renvoie une valeur à l'appelant
        return eventNode;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public InstanceSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Affecte une valeur
        final Map<Long, AtomicReference<ChunkSnapshot>> chunksMap = updater.referencesMapLong(getChunks(),
                // Appelle une méthode
                value -> CoordConversion.chunkIndex(value.getChunkX(), value.getChunkZ()));
        // Appelle une méthode
        final int[] entities = ArrayUtils.mapToIntArray(entityTracker.entities(), Entity::getEntityId);
        // Renvoie une valeur à l'appelant
        return new SnapshotImpl.Instance(updater.reference(MinecraftServer.process()),
                // Instruction de code
                getDimensionType(), getWorldAge(), getTime(), chunksMap, entities,
                // Appelle une méthode
                tagHandler.readableCopy());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Plays a {@link Sound} at a given point, except to the excluded player
     *
     * @param excludedPlayer The player in the instance who won't receive the sound
     * @param sound          The sound to play
     * @param point          The point in this instance at which to play the sound
     */
    // Début d'une méthode/d'un bloc
    public void playSoundExcept(@Nullable Player excludedPlayer, Sound sound, Point point) {
        // Appelle une méthode
        playSoundExcept(excludedPlayer, sound, point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void playSoundExcept(@Nullable Player excludedPlayer, Sound sound, double x, double y, double z) {
        // Appelle une méthode
        ServerPacket packet = AdventurePacketConvertor.createSoundPacket(sound, x, y, z);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(getPlayers(), packet, p -> p != excludedPlayer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void playSoundExcept(@Nullable Player excludedPlayer, Sound sound, Sound.Emitter emitter) {
        // Embranchement : vérifie une condition
        if (emitter != Sound.Emitter.self()) {
            // Appelle une méthode
            ServerPacket packet = AdventurePacketConvertor.createSoundPacket(sound, emitter);
            // Appelle une méthode
            PacketSendingUtils.sendGroupedPacket(getPlayers(), packet, p -> p != excludedPlayer);
        // Branche alternative de la condition
        } else {
            // if we're playing on self, we need to delegate to each audience member
            // Boucle : répète un bloc
            for (Audience audience : this.audiences()) {
                // Embranchement : vérifie une condition
                if (audience == excludedPlayer) continue;
                // Appelle une méthode
                audience.playSound(sound, emitter);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an explosion at the given position with the given strength.
     * The algorithm used to compute damages is provided by {@link #getExplosionSupplier()}.
     *
     * @param centerX  the center X
     * @param centerY  the center Y
     * @param centerZ  the center Z
     * @param strength the strength of the explosion
     * @throws IllegalStateException If no {@link ExplosionSupplier} was supplied
     */
    // Début d'une méthode/d'un bloc
    public void explode(float centerX, float centerY, float centerZ, float strength) {
        // Appelle une méthode
        explode(centerX, centerY, centerZ, strength, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an explosion at the given position with the given strength.
     * The algorithm used to compute damages is provided by {@link #getExplosionSupplier()}.
     *
     * @param centerX        center X of the explosion
     * @param centerY        center Y of the explosion
     * @param centerZ        center Z of the explosion
     * @param strength       the strength of the explosion
     * @param additionalData data to pass to the explosion supplier
     * @throws IllegalStateException If no {@link ExplosionSupplier} was supplied
     */
    // Début d'une méthode/d'un bloc
    public void explode(float centerX, float centerY, float centerZ, float strength, @Nullable CompoundBinaryTag additionalData) {
        // Appelle une méthode
        final ExplosionSupplier explosionSupplier = getExplosionSupplier();
        // Appelle une méthode
        Check.stateCondition(explosionSupplier == null, "Tried to create an explosion with no explosion supplier");
        // Appelle une méthode
        final Explosion explosion = explosionSupplier.createExplosion(centerX, centerY, centerZ, strength, additionalData);
        // Appelle une méthode
        explosion.apply(this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the registered {@link ExplosionSupplier}, or null if none was provided.
     *
     * @return the instance explosion supplier, null if none was provided
     */
    // Début d'une méthode/d'un bloc
    public @Nullable ExplosionSupplier getExplosionSupplier() {
        // Renvoie une valeur à l'appelant
        return explosionSupplier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers the {@link ExplosionSupplier} to use in this instance.
     *
     * @param supplier the explosion supplier
     */
    // Début d'une méthode/d'un bloc
    public void setExplosionSupplier(@Nullable ExplosionSupplier supplier) {
        // Accès à l'objet courant/parent
        this.explosionSupplier = supplier;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pointers pointers() {
        // Renvoie une valeur à l'appelant
        return INSTANCE_POINTERS_SUPPLIER.view(this);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Identity identity() {
        // Renvoie une valeur à l'appelant
        return Identity.identity(this.uuid); // Warning, do not pull up until this.uuid is final
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getBlockLight(int blockX, int blockY, int blockZ) {
        // Appelle une méthode
        var chunk = getChunkAt(blockX, blockZ);
        // Embranchement : vérifie une condition
        if (chunk == null) return 0;
        // Appelle une méthode
        Section section = chunk.getSectionAt(blockY);
        // Appelle une méthode
        Light light = section.blockLight();
        // Appelle une méthode
        int sectionCoordinate = CoordConversion.globalToChunk(blockY);

        // Appelle une méthode
        int coordX = CoordConversion.globalToSectionRelative(blockX);
        // Appelle une méthode
        int coordY = CoordConversion.globalToSectionRelative(blockY);
        // Appelle une méthode
        int coordZ = CoordConversion.globalToSectionRelative(blockZ);

        // Embranchement : vérifie une condition
        if (light.requiresUpdate())
            // Appelle une méthode
            LightingChunk.relightSection(chunk.getInstance(), chunk.chunkX, sectionCoordinate, chunk.chunkZ);
        // Renvoie une valeur à l'appelant
        return light.getLevel(coordX, coordY, coordZ);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSkyLight(int blockX, int blockY, int blockZ) {
        // Appelle une méthode
        var chunk = getChunkAt(blockX, blockZ);
        // Embranchement : vérifie une condition
        if (chunk == null) return 0;
        // Appelle une méthode
        Section section = chunk.getSectionAt(blockY);
        // Appelle une méthode
        Light light = section.skyLight();
        // Appelle une méthode
        int sectionCoordinate = CoordConversion.globalToChunk(blockY);

        // Appelle une méthode
        int coordX = CoordConversion.globalToSectionRelative(blockX);
        // Appelle une méthode
        int coordY = CoordConversion.globalToSectionRelative(blockY);
        // Appelle une méthode
        int coordZ = CoordConversion.globalToSectionRelative(blockZ);

        // Embranchement : vérifie une condition
        if (light.requiresUpdate())
            // Appelle une méthode
            LightingChunk.relightSection(chunk.getInstance(), chunk.chunkX, sectionCoordinate, chunk.chunkZ);
        // Renvoie une valeur à l'appelant
        return light.getLevel(coordX, coordY, coordZ);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}