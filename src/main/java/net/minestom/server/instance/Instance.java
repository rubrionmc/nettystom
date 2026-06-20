// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import of a required class
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.identity.Identified;
// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.pointer.Pointered;
// Import of a required class
import net.kyori.adventure.pointer.Pointers;
// Import of a required class
import net.kyori.adventure.pointer.PointersSupplier;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ExperienceOrb;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.EventHandler;
// Import of a required class
import net.minestom.server.event.EventNode;
// Import of a required class
import net.minestom.server.event.instance.InstanceSectionInvalidateEvent;
// Import of a required class
import net.minestom.server.event.instance.InstanceTickEvent;
// Import of a required class
import net.minestom.server.event.trait.InstanceEvent;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.generator.Generator;
// Import of a required class
import net.minestom.server.instance.light.Light;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockActionPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.InitializeWorldBorderPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetTimePacket;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.snapshot.*;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.Taggable;
// Import of a required class
import net.minestom.server.thread.ThreadDispatcher;
// Import of a required class
import net.minestom.server.timer.Schedulable;
// Import of a required class
import net.minestom.server.timer.Scheduler;
// Import of a required class
import net.minestom.server.utils.ArrayUtils;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkCache;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnmodifiableView;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public abstract class Instance implements Block.Getter, Block.Setter, Biome.Getter, Biome.Setter,
        // Start of a method/block
        Tickable, Schedulable, Snapshotable, EventHandler<InstanceEvent>, Taggable, PacketGroupingAudience, Pointered, Identified {

    // Adventure pointers
    // Assigns a value
    protected static final PointersSupplier<Instance> INSTANCE_POINTERS_SUPPLIER = PointersSupplier.<Instance>builder()
            // Code statement
            .resolving(Identity.UUID, Instance::getUuid)
            // Calls a method
            .build();

    // Code statement
    private boolean registered;

    // Code statement
    private final RegistryKey<DimensionType> dimensionType;
    // Code statement
    private final DimensionType cachedDimensionType; // Cached to prevent self-destruction if the registry is changed, and to avoid the lookups.
    // Code statement
    private final String dimensionName;

    // World border of the instance
    // Code statement
    private WorldBorder worldBorder;
    // Code statement
    private double targetBorderDiameter;
    // Code statement
    private long remainingWorldBorderTransitionTicks;

    // Time
    // Code statement
    private long worldAge;
    // Code statement
    private final Map<RegistryKey<WorldClock>, ClockInstance> clocks;

    // Weather of the instance
    // Assigns a value
    private Weather weather = Weather.CLEAR;
    // Assigns a value
    private Weather transitioningWeather = Weather.CLEAR;
    // Code statement
    private int remainingRainTransitionTicks;
    // Code statement
    private int remainingThunderTransitionTicks;

    // Attached boss bars
    // Calls a method
    private final Set<BossBar> bossBars = new CopyOnWriteArraySet<>();

    // Field for tick events
    // Calls a method
    private long lastTickAge = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

    // Calls a method
    private final EntityTracker entityTracker = new EntityTrackerImpl();

    // Calls a method
    private final ChunkCache blockRetriever = new ChunkCache(this, null, null);

    // Assigns a value
    protected int chunkViewDistance = ServerFlag.CHUNK_VIEW_DISTANCE;

    // the uuid of this instance
    // Code statement
    protected UUID uuid;

    // instance custom data
    // Calls a method
    protected TagHandler tagHandler = TagHandler.newHandler();
    // Calls a method
    private final Scheduler scheduler = Scheduler.newScheduler();
    // Code statement
    private final EventNode<InstanceEvent> eventNode;

    // the explosion supplier
    // Code statement
    private ExplosionSupplier explosionSupplier;

    /**
     * Creates a new instance.
     *
     * @param uuid          the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     */
    // Start of a method/block
    public Instance(UUID uuid, RegistryKey<DimensionType> dimensionType) {
        // Calls a method
        this(uuid, dimensionType, dimensionType.key());
    // End of a block/expression
    }

    /**
     * Creates a new instance.
     *
     * @param uuid          the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     */
    // Start of a method/block
    public Instance(UUID uuid, RegistryKey<DimensionType> dimensionType, Key dimensionName) {
        // Calls a method
        this(MinecraftServer.process(), uuid, dimensionType, dimensionName);
    // End of a block/expression
    }

    /**
     * Creates a new instance.
     *
     * @param uuid          the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     */
    // Start of a method/block
    public Instance(Registries registries, UUID uuid, RegistryKey<DimensionType> dimensionType, Key dimensionName) {
        // Access to the current/parent object
        this.uuid = uuid;
        // Access to the current/parent object
        this.dimensionType = dimensionType;
        // Access to the current/parent object
        this.cachedDimensionType = registries.dimensionType().get(dimensionType);
        // Calls a method
        Check.argCondition(cachedDimensionType == null, "The dimension " + dimensionType + " is not registered! Please add it to the registry (`MinecraftServer.getDimensionTypeRegistry().registry(dimensionType)`).");
        // Access to the current/parent object
        this.dimensionName = dimensionName.asString();

        // Access to the current/parent object
        this.clocks = new Object2ObjectArrayMap<>();
        // Loop: repeats a block
        for (var worldClock : registries.worldClock().keys())
            // Access to the current/parent object
            this.clocks.put(worldClock, new ClockInstance(worldClock));

        // Access to the current/parent object
        this.worldBorder = WorldBorder.DEFAULT_BORDER;
        // Calls a method
        targetBorderDiameter = this.worldBorder.diameter();

        // Calls a method
        final ServerProcess process = MinecraftServer.process();
        // Branch: checks a condition
        if (process != null) {
            // Access to the current/parent object
            this.eventNode = process.eventHandler().map(this, EventFilter.INSTANCE);
        // Alternative branch of the condition
        } else {
            // Local nodes require a server process
            // Access to the current/parent object
            this.eventNode = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Schedules a task to be run during the next instance tick.
     *
     * @param callback the task to execute during the next instance tick
     */
    // Start of a method/block
    public void scheduleNextTick(Consumer<Instance> callback) {
        // Access to the current/parent object
        this.scheduler.scheduleNextTick(() -> callback.accept(this));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block) {
        // Calls a method
        setBlock(x, y, z, block, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
        // Calls a method
        Chunk chunk = getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Branch: checks a condition
        if (chunk == null) return;
        // Calls a method
        chunk.lockWriteLock();
        // Exception handling
        try {
            // Calls a method
            chunk.setBiome(x, y, z, biome);
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockWriteLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void setBlock(Point blockPosition, Block block, boolean doBlockUpdates) {
        // Calls a method
        setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block, doBlockUpdates);
    // End of a block/expression
    }

    // Calls a method
    public abstract void setBlock(int x, int y, int z, Block block, boolean doBlockUpdates);

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public boolean placeBlock(BlockHandler.Placement placement) {
        // Returns a value to the caller
        return placeBlock(placement, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    public abstract boolean placeBlock(BlockHandler.Placement placement, boolean doBlockUpdates);

    /**
     * Does call {@link net.minestom.server.event.player.PlayerBlockBreakEvent}
     * and send particle packets
     *
     * @param player        the {@link Player} who break the block
     * @param blockPosition the position of the broken block
     * @return true if the block has been broken, false if it has been cancelled
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace) {
        // Returns a value to the caller
        return breakBlock(player, blockPosition, blockFace, true);
    // End of a block/expression
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
    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    public abstract boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace, boolean doBlockUpdates);

    /**
     * Forces the generation of a {@link Chunk}, even if no file and {@link Generator} are defined.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return a {@link CompletableFuture} completed once the chunk has been loaded
     */
    // Calls a method
    public abstract CompletableFuture<Chunk> loadChunk(int chunkX, int chunkZ);

    /**
     * Loads the chunk at the given {@link Point} with a callback.
     *
     * @param point the chunk position
     */
    // Start of a method/block
    public CompletableFuture<Chunk> loadChunk(Point point) {
        // Returns a value to the caller
        return loadChunk(point.chunkX(), point.chunkZ());
    // End of a block/expression
    }

    /**
     * Loads the chunk if the chunk is already loaded or if
     * {@link #hasEnabledAutoChunkLoad()} returns true.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return a {@link CompletableFuture} completed once the chunk has been processed, can be null if not loaded
     */
    // Calls a method
    public abstract CompletableFuture<@Nullable Chunk> loadOptionalChunk(int chunkX, int chunkZ);

    /**
     * Loads a {@link Chunk} (if {@link #hasEnabledAutoChunkLoad()} returns true)
     * at the given {@link Point} with a callback.
     *
     * @param point the chunk position
     * @return a {@link CompletableFuture} completed once the chunk has been processed, can be null if not loaded
     */
    // Start of a method/block
    public CompletableFuture<@Nullable Chunk> loadOptionalChunk(Point point) {
        // Returns a value to the caller
        return loadOptionalChunk(point.chunkX(), point.chunkZ());
    // End of a block/expression
    }

    /**
     * Schedules the removal of a {@link Chunk}, this method does not promise when it will be done.
     * <p>
     * WARNING: during unloading, all entities other than {@link Player} will be removed.
     *
     * @param chunk the chunk to unload
     */
    // Calls a method
    public abstract void unloadChunk(Chunk chunk);

    /**
     * Unloads the chunk at the given position.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    // Start of a method/block
    public void unloadChunk(int chunkX, int chunkZ) {
        // Calls a method
        final Chunk chunk = getChunk(chunkX, chunkZ);
        // Calls a method
        Check.notNull(chunk, "The chunk at {0}:{1} is already unloaded", chunkX, chunkZ);
        // Calls a method
        unloadChunk(chunk);
    // End of a block/expression
    }

    // Start of a method/block
    public void invalidateSection(int sectionX, int sectionY, int sectionZ) {
        // Calls a method
        final Chunk chunk = getChunk(sectionX, sectionZ);
        // Branch: checks a condition
        if (chunk != null) {
            // Calls a method
            Section section = chunk.getSection(sectionY);
            // Calls a method
            section.invalidate();
            // Calls a method
            chunk.invalidate();
            // Calls a method
            EventDispatcher.call(new InstanceSectionInvalidateEvent(this, sectionX, sectionY, sectionZ));
        // End of a block/expression
        }
    // End of a block/expression
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
    // Calls a method
    public abstract @Nullable Chunk getChunk(int chunkX, int chunkZ);

    /**
     * @param chunkX the chunk X
     * @param chunkZ this chunk Z
     * @return true if the chunk is loaded
     */
    // Start of a method/block
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return getChunk(chunkX, chunkZ) != null;
    // End of a block/expression
    }

    /**
     * @param point coordinate of a block or other
     * @return true if the chunk is loaded
     */
    // Start of a method/block
    public boolean isChunkLoaded(Point point) {
        // Returns a value to the caller
        return isChunkLoaded(point.chunkX(), point.chunkZ());
    // End of a block/expression
    }

    /**
     * Saves the current instance tags.
     * <p>
     * Warning: only the global instance data will be saved, not chunks.
     * You would need to call {@link #saveChunksToStorage()} too.
     *
     * @return the future called once the instance data has been saved
     */
    // Calls a method
    public abstract CompletableFuture<Void> saveInstance();

    /**
     * Saves a {@link Chunk} to permanent storage.
     *
     * @param chunk the {@link Chunk} to save
     * @return future called when the chunk is done saving
     */
    // Calls a method
    public abstract CompletableFuture<Void> saveChunkToStorage(Chunk chunk);

    /**
     * Saves multiple chunks to permanent storage.
     *
     * @return future called when the chunks are done saving
     */
    // Calls a method
    public abstract CompletableFuture<Void> saveChunksToStorage();

    // Calls a method
    public abstract void setChunkSupplier(ChunkSupplier chunkSupplier);

    /**
     * Gets the chunk supplier of the instance.
     *
     * @return the chunk supplier of the instance
     */
    // Calls a method
    public abstract ChunkSupplier getChunkSupplier();

    /**
     * Gets the generator associated with the instance
     *
     * @return the generator if any
     */
    // Calls a method
    public abstract @Nullable Generator generator();

    /**
     * Changes the generator of the instance
     *
     * @param generator the new generator, or null to disable generation
     */
    // Calls a method
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
    // Annotation for the following element
    @ApiStatus.Experimental
    // Calls a method
    public abstract CompletableFuture<Void> generateChunk(int chunkX, int chunkZ, Generator generator);

    /**
     * Gets all the instance's loaded chunks.
     *
     * @return an unmodifiable containing all the instance chunks
     */
    // Calls a method
    public abstract Collection<Chunk> getChunks();

    /**
     * When set to true, chunks will load automatically when requested.
     * Otherwise using {@link #loadChunk(int, int)} will be required to even spawn a player
     *
     * @param enable enable the auto chunk load
     */
    // Calls a method
    public abstract void enableAutoChunkLoad(boolean enable);

    /**
     * Gets if the instance should auto load chunks.
     *
     * @return true if auto chunk load is enabled, false otherwise
     */
    // Calls a method
    public abstract boolean hasEnabledAutoChunkLoad();

    /**
     * Determines whether a position in the void.
     *
     * @param point the point in the world
     * @return true if the point is inside the void
     */
    // Calls a method
    public abstract boolean isInVoid(Point point);

    /**
     * Gets if the instance has been registered in {@link InstanceManager}.
     *
     * @return true if the instance has been registered
     */
    // Start of a method/block
    public boolean isRegistered() {
        // Returns a value to the caller
        return registered;
    // End of a block/expression
    }

    /**
     * Changes the registered field.
     * <p>
     * WARNING: should only be used by {@link InstanceManager}.
     *
     * @param registered true to mark the instance as registered
     */
    // Start of a method/block
    protected void setRegistered(boolean registered) {
        // Access to the current/parent object
        this.registered = registered;
    // End of a block/expression
    }

    /**
     * Gets the instance {@link DimensionType}.
     *
     * @return the dimension of the instance
     */
    // Start of a method/block
    public RegistryKey<DimensionType> getDimensionType() {
        // Returns a value to the caller
        return dimensionType;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public DimensionType getCachedDimensionType() {
        // Returns a value to the caller
        return cachedDimensionType;
    // End of a block/expression
    }

    /**
     * Gets the instance dimension name.
     *
     * @return the dimension name of the instance
     */
    // Start of a method/block
    public String getDimensionName() {
        // Returns a value to the caller
        return dimensionName;
    // End of a block/expression
    }

    /// Returns the current world age (aka game time) of this Instance.
    // Start of a method/block
    public long getWorldAge() {
        // Returns a value to the caller
        return worldAge;
    // End of a block/expression
    }

    /// Sets the current world age (aka game time) of this Instance.
    // Start of a method/block
    public void setWorldAge(long worldAge) {
        // Access to the current/parent object
        this.worldAge = worldAge;
        // Calls a method
        refreshTime();
    // End of a block/expression
    }

    /// Returns the current time (in ticks) of the default clock, or -1 if there is no default clock
    // Start of a method/block
    public long getTime() {
        // Calls a method
        var clock = defaultClock();
        // Returns a value to the caller
        return clock != null ? clock.time() : -1;
    // End of a block/expression
    }

    /// Returns the current time (in ticks) of the given clock.
    ///
    /// @throws IllegalArgumentException if the clock was not registered when the instance was created.
    // Start of a method/block
    public long getTime(RegistryKey<WorldClock> clock) {
        // Returns a value to the caller
        return clock(clock).time();
    // End of a block/expression
    }

    /// Sets the current time (in ticks) of the default clock, or -1 if there is no default clock
    // Start of a method/block
    public void setTime(long time) {
        // Calls a method
        var clock = defaultClock();
        // Branch: checks a condition
        if (clock != null) clock.time(time);
    // End of a block/expression
    }

    /// @throws IllegalArgumentException if the clock was not registered when the instance was created.
    // Start of a method/block
    public void setTime(RegistryKey<WorldClock> clock, long time) {
        // Calls a method
        clock(clock).time(time);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Clock defaultClock() {
        // Returns a value to the caller
        return clocks.get(getCachedDimensionType().defaultClock());
    // End of a block/expression
    }

    // Start of a method/block
    public Clock clock(RegistryKey<WorldClock> clock) {
        // Calls a method
        var clockInstance = clocks.get(clock);
        // Calls a method
        Check.argCondition(clockInstance == null, "Clock {0} is not registered in this instance", clock);
        // Returns a value to the caller
        return clockInstance;
    // End of a block/expression
    }

    /**
     * Creates a {@link SetTimePacket} with the current age and time of this instance
     *
     * @return the {@link SetTimePacket} with this instance data
     */
    // Start of a method/block
    public SetTimePacket createTimePacket() {
        // Calls a method
        var entries = new HashMap<RegistryKey<WorldClock>, SetTimePacket.ClockState>();
        // Loop: repeats a block
        for (var clockInstance : this.clocks.values()) {
            // Code statement
            entries.put(clockInstance.clock(), new SetTimePacket.ClockState(
                // Code statement
                clockInstance.time(),
                // Code statement
                clockInstance.partialTick(),
                // Code statement
                clockInstance.effectiveRate()
            // Code statement
            ));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new SetTimePacket(worldAge, entries);
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshTime() {
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(getPlayers(), createTimePacket());
    // End of a block/expression
    }

    /**
     * Gets the current state of the instance {@link WorldBorder}.
     *
     * @return the {@link WorldBorder} for the instance of the current tick
     */
    // Start of a method/block
    public WorldBorder getWorldBorder() {
        // Returns a value to the caller
        return worldBorder;
    // End of a block/expression
    }

    /**
     * Set the instance {@link WorldBorder} with a smooth transition.
     *
     * @param worldBorder    the desired final state of the world border
     * @param transitionTime the time in seconds this world border's diameter
     *                       will transition for (0 makes this instant)
     */
    // Start of a method/block
    public void setWorldBorder(WorldBorder worldBorder, double transitionTime) {
        // Calls a method
        Check.stateCondition(transitionTime < 0, "Transition time cannot be lower than 0");
        // Calls a method
        long transitionMilliseconds = (long) (transitionTime * 1000);
        // Calls a method
        sendNewWorldBorderPackets(worldBorder, transitionMilliseconds);

        // Access to the current/parent object
        this.targetBorderDiameter = worldBorder.diameter();
        // Assigns a value
        long transitionTicks = transitionMilliseconds / MinecraftServer.TICK_MS;
        // Assigns a value
        remainingWorldBorderTransitionTicks = transitionTicks;
        // Branch: checks a condition
        if (transitionTicks == 0) this.worldBorder = worldBorder;
        // Alternative branch of the condition
        else this.worldBorder = worldBorder.withDiameter(this.worldBorder.diameter());
    // End of a block/expression
    }

    /**
     * Set the instance {@link WorldBorder} with an instant transition.
     * see {@link Instance#setWorldBorder(WorldBorder, double)}.
     */
    // Start of a method/block
    public void setWorldBorder(WorldBorder worldBorder) {
        // Calls a method
        setWorldBorder(worldBorder, 0);
    // End of a block/expression
    }

    /**
     * Creates the {@link InitializeWorldBorderPacket} sent to players who join this instance.
     */
    // Start of a method/block
    public InitializeWorldBorderPacket createInitializeWorldBorderPacket() {
        // Returns a value to the caller
        return worldBorder.createInitializePacket(targetBorderDiameter, remainingWorldBorderTransitionTicks * MinecraftServer.TICK_MS);
    // End of a block/expression
    }

    // Start of a method/block
    private void sendNewWorldBorderPackets(WorldBorder newBorder, long transitionMilliseconds) {
        // Only send the relevant border packets
        // Branch: checks a condition
        if (this.worldBorder.diameter() != newBorder.diameter()) {
            // Branch: checks a condition
            if (transitionMilliseconds == 0) sendGroupedPacket(newBorder.createSizePacket());
            // Alternative branch of the condition
            else sendGroupedPacket(this.worldBorder.createLerpSizePacket(newBorder.diameter(), transitionMilliseconds));
        // End of a block/expression
        }
        // Branch: checks a condition
        if (this.worldBorder.centerX() != newBorder.centerX() || this.worldBorder.centerZ() != newBorder.centerZ()) {
            // Calls a method
            sendGroupedPacket(newBorder.createCenterPacket());
        // End of a block/expression
        }
        // Branch: checks a condition
        if (this.worldBorder.warningTime() != newBorder.warningTime())
            // Calls a method
            sendGroupedPacket(newBorder.createWarningDelayPacket());
        // Branch: checks a condition
        if (this.worldBorder.warningDistance() != newBorder.warningDistance())
            // Calls a method
            sendGroupedPacket(newBorder.createWarningReachPacket());
    // End of a block/expression
    }

    // Start of a method/block
    private WorldBorder transitionWorldBorder(long remainingTicks) {
        // Branch: checks a condition
        if (remainingTicks <= 1) return worldBorder.withDiameter(targetBorderDiameter);
        // Returns a value to the caller
        return worldBorder.withDiameter(worldBorder.diameter() + (targetBorderDiameter - worldBorder.diameter()) * (1 / (double) remainingTicks));
    // End of a block/expression
    }

    /**
     * Gets the entities in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the entities in the instance
     */
    // Start of a method/block
    public Set<Entity> getEntities() {
        // Returns a value to the caller
        return entityTracker.entities();
    // End of a block/expression
    }

    /**
     * Gets an entity based on its id (from {@link Entity#getEntityId()}).
     *
     * @param id the entity id
     * @return the entity having the specified id, null if not found
     */
    // Start of a method/block
    public @Nullable Entity getEntityById(int id) {
        // Returns a value to the caller
        return entityTracker.getEntityById(id);
    // End of a block/expression
    }

    /**
     * Gets an entity based on its UUID (from {@link Entity#getUuid()}).
     *
     * @param uuid the entity UUID
     * @return the entity having the specified uuid, null if not found
     */
    // Start of a method/block
    public @Nullable Entity getEntityByUuid(UUID uuid) {
        // Returns a value to the caller
        return entityTracker.getEntityByUuid(uuid);
    // End of a block/expression
    }

    /**
     * Gets a player based on its UUID (from {@link Entity#getUuid()}).
     *
     * @param uuid the player UUID
     * @return the player having the specified uuid, null if not found or not a player
     */
    // Start of a method/block
    public @Nullable Player getPlayerByUuid(UUID uuid) {
        // Calls a method
        Entity entity = entityTracker.getEntityByUuid(uuid);
        // Branch: checks a condition
        if (entity instanceof Player player) {
            // Returns a value to the caller
            return player;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Gets the players in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the players in the instance
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<Player> getPlayers() {
        // Returns a value to the caller
        return entityTracker.entities(EntityTracker.Target.PLAYERS);
    // End of a block/expression
    }

    /**
     * Gets the creatures in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the creatures in the instance
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Set<EntityCreature> getCreatures() {
        // Returns a value to the caller
        return entityTracker.entities().stream()
                // Code statement
                .filter(EntityCreature.class::isInstance)
                // Code statement
                .map(entity -> (EntityCreature) entity)
                // Calls a method
                .collect(Collectors.toUnmodifiableSet());
    // End of a block/expression
    }

    /**
     * Gets the experience orbs in the instance.
     *
     * @return an unmodifiable {@link Set} containing all the experience orbs in the instance
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Set<ExperienceOrb> getExperienceOrbs() {
        // Returns a value to the caller
        return entityTracker.entities().stream()
                // Code statement
                .filter(ExperienceOrb.class::isInstance)
                // Code statement
                .map(entity -> (ExperienceOrb) entity)
                // Calls a method
                .collect(Collectors.toUnmodifiableSet());
    // End of a block/expression
    }

    /**
     * Gets the entities located in the chunk.
     *
     * @param chunk the chunk to get the entities from
     * @return an unmodifiable {@link Set} containing all the entities in a chunk,
     * if {@code chunk} is unloaded, return an empty {@link HashSet}
     */
    // Start of a method/block
    public Set<Entity> getChunkEntities(Chunk chunk) {
        // Calls a method
        var chunkEntities = entityTracker.chunkEntities(chunk.toPosition(), EntityTracker.Target.ENTITIES);
        // Returns a value to the caller
        return ObjectArraySet.ofUnchecked(chunkEntities.toArray(Entity[]::new));
    // End of a block/expression
    }

    /**
     * Gets nearby entities to the given position.
     *
     * @param point position to look at
     * @param range max range from the given point to collect entities at
     * @return entities that are not further than the specified distance from the transmitted position.
     */
    // Start of a method/block
    public Collection<Entity> getNearbyEntities(Point point, double range) {
        // Calls a method
        List<Entity> result = new ArrayList<>();
        // Access to the current/parent object
        this.entityTracker.nearbyEntities(point, range, EntityTracker.Target.ENTITIES, result::add);
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Block getBlock(int x, int y, int z, Condition condition) {
        // Calls a method
        final Block block = blockRetriever.getBlock(x, y, z, condition);
        // Branch: checks a condition
        if (block == null) throw new NullPointerException("Unloaded chunk at " + x + "," + y + "," + z);
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RegistryKey<Biome> getBiome(int x, int y, int z) {
        // Calls a method
        Chunk chunk = getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Calls a method
        Objects.requireNonNull(chunk);
        // Calls a method
        chunk.lockReadLock();
        // Exception handling
        try {
            // Returns a value to the caller
            return chunk.getBiome(x, y, z);
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockReadLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Sends a {@link BlockActionPacket} for all the viewers of the specific position.
     *
     * @param blockPosition the block position
     * @param actionId      the action id, depends on the block
     * @param actionParam   the action parameter, depends on the block
     * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Block_Action">BlockActionPacket</a> for the action id &amp; param
     */
    // Start of a method/block
    public void sendBlockAction(Point blockPosition, byte actionId, byte actionParam) {
        // Calls a method
        final Block block = getBlock(blockPosition);
        // Calls a method
        final Chunk chunk = getChunkAt(blockPosition);
        // Calls a method
        Check.notNull(chunk, "The chunk at {0} is not loaded!", blockPosition);
        // Calls a method
        chunk.sendPacketToViewers(new BlockActionPacket(blockPosition, actionId, actionParam, block));
    // End of a block/expression
    }

    /**
     * Gets the {@link Chunk} at the given block position, null if not loaded.
     *
     * @param x the X position
     * @param z the Z position
     * @return the chunk at the given position, null if not loaded
     */
    // Start of a method/block
    public @Nullable Chunk getChunkAt(double x, double z) {
        // Returns a value to the caller
        return getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
    // End of a block/expression
    }

    /**
     * Gets the {@link Chunk} at the given {@link Point}, null if not loaded.
     *
     * @param point the position
     * @return the chunk at the given position, null if not loaded
     */
    // Start of a method/block
    public @Nullable Chunk getChunkAt(Point point) {
        // Returns a value to the caller
        return getChunk(point.chunkX(), point.chunkZ());
    // End of a block/expression
    }

    // Start of a method/block
    public EntityTracker getEntityTracker() {
        // Returns a value to the caller
        return entityTracker;
    // End of a block/expression
    }

    /**
     * Gets the instance unique id.
     *
     * @return the instance unique id
     */
    // Start of a method/block
    public UUID getUuid() {
        // Returns a value to the caller
        return uuid;
    // End of a block/expression
    }

    /**
     * Performs a single tick in the instance, including scheduled tasks from {@link #scheduleNextTick(Consumer)}.
     * <p>
     * Warning: this does not update chunks and entities.
     *
     * @param time the tick time in milliseconds, which may only be used as a delta and has no meaning in real life
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Scheduled tasks
        // Access to the current/parent object
        this.scheduler.processTick();
        // Time
        // Start of a block
        {
            // Access to the current/parent object
            this.worldAge++;
            // Loop: repeats a block
            for (var clock : clocks.values()) clock.tick();
        // End of a block/expression
        }
        // Weather
        // Branch: checks a condition
        if (remainingRainTransitionTicks > 0 || remainingThunderTransitionTicks > 0) {
            // Assigns a value
            Weather previousWeather = transitioningWeather;
            // Calls a method
            transitioningWeather = transitionWeather(remainingRainTransitionTicks, remainingThunderTransitionTicks);
            // Calls a method
            sendWeatherPackets(previousWeather);
            // Calls a method
            remainingRainTransitionTicks = Math.max(0, remainingRainTransitionTicks - 1);
            // Calls a method
            remainingThunderTransitionTicks = Math.max(0, remainingThunderTransitionTicks - 1);
        // End of a block/expression
        }
        // Tick event
        // Start of a block
        {
            // Process tick events
            // Calls a method
            EventDispatcher.call(new InstanceTickEvent(this, time, lastTickAge));
            // Set last tick age
            // Access to the current/parent object
            this.lastTickAge = time;
        // End of a block/expression
        }
        // World border
        // Branch: checks a condition
        if (remainingWorldBorderTransitionTicks > 0) {
            // Calls a method
            worldBorder = transitionWorldBorder(remainingWorldBorderTransitionTicks);
            // Branch: checks a condition
            if (worldBorder.diameter() == targetBorderDiameter) remainingWorldBorderTransitionTicks = 0;
            // Alternative branch of the condition
            else remainingWorldBorderTransitionTicks--;
        // End of a block/expression
        }
        // End of tick scheduled tasks
        // Access to the current/parent object
        this.scheduler.processTickEnd();
    // End of a block/expression
    }

    /**
     * Gets the weather of this instance
     *
     * @return the instance weather
     */
    // Start of a method/block
    public Weather getWeather() {
        // Returns a value to the caller
        return weather;
    // End of a block/expression
    }

    /**
     * Sets the weather on this instance, transitions over time
     *
     * @param weather         the new weather
     * @param transitionTicks the ticks to transition to new weather
     */
    // Start of a method/block
    public void setWeather(Weather weather, int transitionTicks) {
        // Calls a method
        Check.stateCondition(transitionTicks < 1, "Transition ticks cannot be lower than 0");
        // Access to the current/parent object
        this.weather = weather;
        // Assigns a value
        remainingRainTransitionTicks = transitionTicks;
        // Assigns a value
        remainingThunderTransitionTicks = transitionTicks;
    // End of a block/expression
    }

    /**
     * Sets the weather of this instance with a fixed transition
     *
     * @param weather the new weather
     */
    // Start of a method/block
    public void setWeather(Weather weather) {
        // Access to the current/parent object
        this.weather = weather;
        // Calls a method
        remainingRainTransitionTicks = (int) Math.max(1, Math.abs((this.weather.rainLevel() - transitioningWeather.rainLevel()) / 0.01));
        // Calls a method
        remainingThunderTransitionTicks = (int) Math.max(1, Math.abs((this.weather.thunderLevel() - transitioningWeather.thunderLevel()) / 0.01));
    // End of a block/expression
    }

    // Start of a method/block
    private void sendWeatherPackets(Weather previousWeather) {
        // Calls a method
        boolean toggledRain = (transitioningWeather.isRaining() != previousWeather.isRaining());
        // Branch: checks a condition
        if (toggledRain) sendGroupedPacket(transitioningWeather.createIsRainingPacket());
        // Branch: checks a condition
        if (transitioningWeather.rainLevel() != previousWeather.rainLevel())
            // Calls a method
            sendGroupedPacket(transitioningWeather.createRainLevelPacket());
        // Branch: checks a condition
        if (transitioningWeather.thunderLevel() != previousWeather.thunderLevel())
            // Calls a method
            sendGroupedPacket(transitioningWeather.createThunderLevelPacket());
    // End of a block/expression
    }

    // Start of a method/block
    private Weather transitionWeather(int remainingRainTransitionTicks, int remainingThunderTransitionTicks) {
        // Assigns a value
        Weather target = weather;
        // Assigns a value
        Weather current = transitioningWeather;
        // Calls a method
        float rainLevel = current.rainLevel() + (target.rainLevel() - current.rainLevel()) * (1 / (float) Math.max(1, remainingRainTransitionTicks));
        // Calls a method
        float thunderLevel = current.thunderLevel() + (target.thunderLevel() - current.thunderLevel()) * (1 / (float) Math.max(1, remainingThunderTransitionTicks));
        // Returns a value to the caller
        return new Weather(rainLevel, thunderLevel);
    // End of a block/expression
    }

    /**
     * Gets the chunk view distance of this instance, which defaults to {@link ServerFlag#CHUNK_VIEW_DISTANCE}.
     *
     * @return The chunk view distance of this instance
     */
    // Start of a method/block
    public int viewDistance() {
        // Returns a value to the caller
        return this.chunkViewDistance;
    // End of a block/expression
    }

    /**
     * Sets the chunk view distance of this instance
     *
     * @param newViewDistance the new view distance
     */
    // Start of a method/block
    public void viewDistance(int newViewDistance) {
        // Access to the current/parent object
        this.chunkViewDistance = newViewDistance;
    // End of a block/expression
    }

    /**
     * Shows a {@link BossBar} to all players in the instance and tracks it.
     *
     * @param bar a boss bar
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void showBossBar(BossBar bar) {
        // Calls a method
        Objects.requireNonNull(bar, "Boss bar cannot be null");
        // Branch: checks a condition
        if (!bossBars.add(bar)) return;
        // Calls a method
        PacketGroupingAudience.super.showBossBar(bar);
    // End of a block/expression
    }

    /**
     * Hides a {@link BossBar} from all players in the instance and stops tracking it.
     *
     * @param bar a boss bar
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void hideBossBar(BossBar bar) {
        // Calls a method
        Objects.requireNonNull(bar, "Boss bar cannot be null");
        // Branch: checks a condition
        if (!bossBars.remove(bar)) return;
        // Calls a method
        PacketGroupingAudience.super.hideBossBar(bar);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public @UnmodifiableView Set<BossBar> bossBars() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(bossBars);
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Scheduler scheduler() {
        // Returns a value to the caller
        return scheduler;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public EventNode<InstanceEvent> eventNode() {
        // Returns a value to the caller
        return eventNode;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public InstanceSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Assigns a value
        final Map<Long, AtomicReference<ChunkSnapshot>> chunksMap = updater.referencesMapLong(getChunks(),
                // Calls a method
                value -> CoordConversion.chunkIndex(value.getChunkX(), value.getChunkZ()));
        // Calls a method
        final int[] entities = ArrayUtils.mapToIntArray(entityTracker.entities(), Entity::getEntityId);
        // Returns a value to the caller
        return new SnapshotImpl.Instance(updater.reference(MinecraftServer.process()),
                // Code statement
                getDimensionType(), getWorldAge(), getTime(), chunksMap, entities,
                // Calls a method
                tagHandler.readableCopy());
    // End of a block/expression
    }

    /**
     * Plays a {@link Sound} at a given point, except to the excluded player
     *
     * @param excludedPlayer The player in the instance who won't receive the sound
     * @param sound          The sound to play
     * @param point          The point in this instance at which to play the sound
     */
    // Start of a method/block
    public void playSoundExcept(@Nullable Player excludedPlayer, Sound sound, Point point) {
        // Calls a method
        playSoundExcept(excludedPlayer, sound, point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Start of a method/block
    public void playSoundExcept(@Nullable Player excludedPlayer, Sound sound, double x, double y, double z) {
        // Calls a method
        ServerPacket packet = AdventurePacketConvertor.createSoundPacket(sound, x, y, z);
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(getPlayers(), packet, p -> p != excludedPlayer);
    // End of a block/expression
    }

    // Start of a method/block
    public void playSoundExcept(@Nullable Player excludedPlayer, Sound sound, Sound.Emitter emitter) {
        // Branch: checks a condition
        if (emitter != Sound.Emitter.self()) {
            // Calls a method
            ServerPacket packet = AdventurePacketConvertor.createSoundPacket(sound, emitter);
            // Calls a method
            PacketSendingUtils.sendGroupedPacket(getPlayers(), packet, p -> p != excludedPlayer);
        // Alternative branch of the condition
        } else {
            // if we're playing on self, we need to delegate to each audience member
            // Loop: repeats a block
            for (Audience audience : this.audiences()) {
                // Branch: checks a condition
                if (audience == excludedPlayer) continue;
                // Calls a method
                audience.playSound(sound, emitter);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
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
    // Start of a method/block
    public void explode(float centerX, float centerY, float centerZ, float strength) {
        // Calls a method
        explode(centerX, centerY, centerZ, strength, null);
    // End of a block/expression
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
    // Start of a method/block
    public void explode(float centerX, float centerY, float centerZ, float strength, @Nullable CompoundBinaryTag additionalData) {
        // Calls a method
        final ExplosionSupplier explosionSupplier = getExplosionSupplier();
        // Calls a method
        Check.stateCondition(explosionSupplier == null, "Tried to create an explosion with no explosion supplier");
        // Calls a method
        final Explosion explosion = explosionSupplier.createExplosion(centerX, centerY, centerZ, strength, additionalData);
        // Calls a method
        explosion.apply(this);
    // End of a block/expression
    }

    /**
     * Gets the registered {@link ExplosionSupplier}, or null if none was provided.
     *
     * @return the instance explosion supplier, null if none was provided
     */
    // Start of a method/block
    public @Nullable ExplosionSupplier getExplosionSupplier() {
        // Returns a value to the caller
        return explosionSupplier;
    // End of a block/expression
    }

    /**
     * Registers the {@link ExplosionSupplier} to use in this instance.
     *
     * @param supplier the explosion supplier
     */
    // Start of a method/block
    public void setExplosionSupplier(@Nullable ExplosionSupplier supplier) {
        // Access to the current/parent object
        this.explosionSupplier = supplier;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pointers pointers() {
        // Returns a value to the caller
        return INSTANCE_POINTERS_SUPPLIER.view(this);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Identity identity() {
        // Returns a value to the caller
        return Identity.identity(this.uuid); // Warning, do not pull up until this.uuid is final
    // End of a block/expression
    }

    // Start of a method/block
    public int getBlockLight(int blockX, int blockY, int blockZ) {
        // Calls a method
        var chunk = getChunkAt(blockX, blockZ);
        // Branch: checks a condition
        if (chunk == null) return 0;
        // Calls a method
        Section section = chunk.getSectionAt(blockY);
        // Calls a method
        Light light = section.blockLight();
        // Calls a method
        int sectionCoordinate = CoordConversion.globalToChunk(blockY);

        // Calls a method
        int coordX = CoordConversion.globalToSectionRelative(blockX);
        // Calls a method
        int coordY = CoordConversion.globalToSectionRelative(blockY);
        // Calls a method
        int coordZ = CoordConversion.globalToSectionRelative(blockZ);

        // Branch: checks a condition
        if (light.requiresUpdate())
            // Calls a method
            LightingChunk.relightSection(chunk.getInstance(), chunk.chunkX, sectionCoordinate, chunk.chunkZ);
        // Returns a value to the caller
        return light.getLevel(coordX, coordY, coordZ);
    // End of a block/expression
    }

    // Start of a method/block
    public int getSkyLight(int blockX, int blockY, int blockZ) {
        // Calls a method
        var chunk = getChunkAt(blockX, blockZ);
        // Branch: checks a condition
        if (chunk == null) return 0;
        // Calls a method
        Section section = chunk.getSectionAt(blockY);
        // Calls a method
        Light light = section.skyLight();
        // Calls a method
        int sectionCoordinate = CoordConversion.globalToChunk(blockY);

        // Calls a method
        int coordX = CoordConversion.globalToSectionRelative(blockX);
        // Calls a method
        int coordY = CoordConversion.globalToSectionRelative(blockY);
        // Calls a method
        int coordZ = CoordConversion.globalToSectionRelative(blockZ);

        // Branch: checks a condition
        if (light.requiresUpdate())
            // Calls a method
            LightingChunk.relightSection(chunk.getInstance(), chunk.chunkX, sectionCoordinate, chunk.chunkZ);
        // Returns a value to the caller
        return light.getLevel(coordX, coordY, coordZ);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClockInstance implements Clock {
        // Code statement
        private final RegistryKey<WorldClock> clock;
        // Assigns a value
        private boolean paused = false;
        // Assigns a value
        private float rate = 1f;
        // Assigns a value
        private float partialTick = 0f;
        // Code statement
        private long time;

        // Start of a method/block
        private ClockInstance(RegistryKey<WorldClock> clock) {
            // Access to the current/parent object
            this.clock = clock;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RegistryKey<WorldClock> clock() {
            // Returns a value to the caller
            return this.clock;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float rate() {
            // Returns a value to the caller
            return this.rate;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void rate(float rate) {
            // Calls a method
            Check.argCondition(rate < 0, "rate cannot be negative");
            // Access to the current/parent object
            this.rate = rate;
            // Calls a method
            refreshTime();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public long time() {
            // Returns a value to the caller
            return this.time;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void time(long newTime) {
            // Access to the current/parent object
            this.time = newTime;
            // Calls a method
            refreshTime();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean paused() {
            // Returns a value to the caller
            return this.paused;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void pause() {
            // Branch: checks a condition
            if (this.paused) return;
            // Access to the current/parent object
            this.paused = true;
            // Calls a method
            refreshTime();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void resume() {
            // Branch: checks a condition
            if (!this.paused) return;
            // Access to the current/parent object
            this.paused = false;
            // Calls a method
            refreshTime();
        // End of a block/expression
        }

        // Start of a method/block
        float partialTick() {
            // Returns a value to the caller
            return this.partialTick;
        // End of a block/expression
        }

        // Start of a method/block
        float effectiveRate() {
            // Returns a value to the caller
            return paused ? 0f : rate;
        // End of a block/expression
        }

        // Start of a method/block
        void tick() {
            // Branch: checks a condition
            if (paused) return;

            // Access to the current/parent object
            this.partialTick += rate;
            // Calls a method
            int ticks = (int) this.partialTick;
            // Access to the current/parent object
            this.partialTick -= ticks;
            // Access to the current/parent object
            this.time += ticks;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}