// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.identity.Identified;
// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.pointer.Pointered;
// Import of a required class
import net.kyori.adventure.pointer.Pointers;
// Import of a required class
import net.kyori.adventure.pointer.PointersSupplier;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent.ShowEntity;
// Import of a required class
import net.kyori.adventure.text.event.HoverEventSource;
// Import of a required class
import net.minestom.server.*;
// Import of a required class
import net.minestom.server.collision.*;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import of a required class
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.EventHandler;
// Import of a required class
import net.minestom.server.event.EventNode;
// Import of a required class
import net.minestom.server.event.entity.*;
// Import of a required class
import net.minestom.server.event.instance.AddEntityToInstanceEvent;
// Import of a required class
import net.minestom.server.event.instance.RemoveEntityFromInstanceEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityEvent;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.EntityTracker;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.InstanceManager;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.item.component.CustomData;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.potion.Potion;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.potion.TimedPotion;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.snapshot.EntitySnapshot;
// Import of a required class
import net.minestom.server.snapshot.SnapshotImpl;
// Import of a required class
import net.minestom.server.snapshot.SnapshotUpdater;
// Import of a required class
import net.minestom.server.snapshot.Snapshotable;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.Taggable;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import net.minestom.server.thread.AcquirableSource;
// Import of a required class
import net.minestom.server.timer.Schedulable;
// Import of a required class
import net.minestom.server.timer.Scheduler;
// Import of a required class
import net.minestom.server.timer.TaskSchedule;
// Import of a required class
import net.minestom.server.utils.ArrayUtils;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.PacketViewableUtils;
// Import of a required class
import net.minestom.server.utils.async.AsyncUtils;
// Import of a required class
import net.minestom.server.utils.block.BlockIterator;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkCache;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkUtils;
// Import of a required class
import net.minestom.server.utils.entity.EntityUtils;
// Import of a required class
import net.minestom.server.utils.position.PositionUtils;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.intellij.lang.annotations.MagicConstant;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Predicate;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Could be a player, a monster, or an object.
 * <p>
 * To create your own entity you probably want to extend {@link LivingEntity} or {@link EntityCreature} instead.
 */
// Type declaration (class/interface/enum/record)
public class Entity implements Viewable, Tickable, Schedulable, Snapshotable, EventHandler<EntityEvent>, Taggable,
        // Start of a method/block
        HoverEventSource<ShowEntity>, Sound.Emitter, Shape, AcquirableSource<Entity>, DataComponent.Holder, Pointered, Identified {
    // This is somewhat arbitrary, but we don't want to hit the max int ever because it is very easy to
    // overflow while working with a position at the max int (for example, looping over a bounding box)
    // Assigns a value
    static final int MAX_COORDINATE = 2_000_000_000;

    // Calls a method
    private static final AtomicInteger LAST_ENTITY_ID = new AtomicInteger();

    // Protected due to PointersSupplier.Builder#parent
    // Assigns a value
    protected static PointersSupplier<Entity> ENTITY_POINTERS_SUPPLIER = PointersSupplier.<Entity>builder()
            // Code statement
            .resolving(Identity.DISPLAY_NAME, (entity) -> entity.get(DataComponents.CUSTOM_NAME))
            // Code statement
            .resolving(Identity.UUID, Entity::getUuid)
            // Calls a method
            .build();

    // Certain entities should only have their position packets sent during synchronization
    // Assigns a value
    private static final Set<EntityType> SYNCHRONIZE_ONLY_ENTITIES = Set.of(EntityType.ITEM, EntityType.FALLING_BLOCK,
            // Code statement
            EntityType.ARROW, EntityType.SPECTRAL_ARROW, EntityType.TRIDENT, EntityType.LLAMA_SPIT, EntityType.WIND_CHARGE,
            // Code statement
            EntityType.FISHING_BOBBER, EntityType.SNOWBALL, EntityType.EGG, EntityType.ENDER_PEARL, EntityType.SPLASH_POTION,
            // Code statement
            EntityType.LINGERING_POTION, EntityType.EYE_OF_ENDER, EntityType.DRAGON_FIREBALL, EntityType.FIREBALL,
            // Code statement
            EntityType.SMALL_FIREBALL, EntityType.TNT);
    // Assigns a value
    private static final Set<EntityType> ALLOW_BLOCK_PLACEMENT_ENTITIES = Set.of(EntityType.ARROW, EntityType.ITEM,
            // Code statement
            EntityType.SNOWBALL, EntityType.EXPERIENCE_BOTTLE, EntityType.EXPERIENCE_ORB, EntityType.SPLASH_POTION,
            // Code statement
            EntityType.LINGERING_POTION, EntityType.AREA_EFFECT_CLOUD);
    // Assigns a value
    private static final Set<EntityType> NO_ENTITY_COLLISION_ENTITIES = Set.of(EntityType.TEXT_DISPLAY, EntityType.ITEM_DISPLAY,
            // Code statement
            EntityType.BLOCK_DISPLAY);
    // Calls a method
    private final CachedPacket destroyPacketCache = new CachedPacket(() -> new DestroyEntitiesPacket(getEntityId()));

    // Code statement
    protected Instance instance;
    // Code statement
    protected Chunk currentChunk;
    // Code statement
    protected Pos position; // Should be updated by setPositionInternal only.
    // Code statement
    protected float headRotation;
    // Code statement
    protected Pos previousPosition;
    // Code statement
    protected Pos lastSyncedPosition;
    // Code statement
    protected boolean onGround;

    // Code statement
    protected BoundingBox boundingBox;
    // Assigns a value
    private @Nullable PhysicsResult previousPhysicsResult = null;

    // Code statement
    protected @Nullable Entity vehicle;

    // Velocity
    // Assigns a value
    protected Vec velocity = Vec.ZERO; // Movement in block per second
    // Assigns a value
    protected boolean hasPhysics = true;
    // Assigns a value
    protected boolean collidesWithEntities = true;
    // Assigns a value
    protected boolean preventBlockPlacement = true;

    // Code statement
    private Aerodynamics aerodynamics;
    // Code statement
    protected int gravityTickCount; // Number of tick where gravity tick was applied

    // Code statement
    private final int id;
    // Players must be aware of all surrounding entities
    // General entities should only be aware of surrounding players to update their viewing list
    // Assigns a value
    private final EntityTracker.Target<Entity> trackingTarget = this instanceof Player ?
            // Calls a method
            EntityTracker.Target.ENTITIES : EntityTracker.Target.class.cast(EntityTracker.Target.PLAYERS);
    // Assigns a value
    protected final EntityTracker.Update<Entity> trackingUpdate = new EntityTracker.Update<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void add(Entity entity) {
            // Calls a method
            viewEngine.handleAutoViewAddition(entity);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void remove(Entity entity) {
            // Calls a method
            viewEngine.handleAutoViewRemoval(entity);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void referenceUpdate(Point point, @Nullable EntityTracker tracker) {
            // Assigns a value
            final Instance currentInstance = tracker != null ? instance : null;
            // Code statement
            assert currentInstance == null || currentInstance.getEntityTracker() == tracker :
                    // Code statement
                    "EntityTracker does not match current instance";
            // Calls a method
            viewEngine.updateTracker(currentInstance, point);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Calls a method
    protected final EntityView viewEngine = new EntityView(this);
    // Assigns a value
    protected final Set<Player> viewers = viewEngine.set;
    // Calls a method
    private final TagHandler tagHandler = TagHandler.newHandler();
    // Calls a method
    private final Scheduler scheduler = Scheduler.newScheduler();
    // Code statement
    private final EventNode<EntityEvent> eventNode;

    // Code statement
    private final UUID uuid;
    // Code statement
    private boolean isActive; // False if entity has only been instanced without being added somewhere
    // Code statement
    protected boolean removed;

    // Calls a method
    private final Set<Entity> passengers = new CopyOnWriteArraySet<>();

    // Calls a method
    private final Set<Entity> leashedEntities = new CopyOnWriteArraySet<>();
    // Code statement
    private Entity leashHolder;

    // Code statement
    protected EntityType entityType; // UNSAFE to change, modify at your own risk

    // Network synchronization, send the absolute position of the entity every n ticks
    // Assigns a value
    private long synchronizationTicks = ServerFlag.ENTITY_SYNCHRONIZATION_TICKS;
    // Assigns a value
    private long nextSynchronizationTick = synchronizationTicks;

    // Calls a method
    protected MetadataHolder metadata = new MetadataHolder(this::notifyMetadataChanges);
    // Code statement
    protected EntityMeta entityMeta;

    // Calls a method
    private final List<TimedPotion> effects = new CopyOnWriteArrayList<>();

    // Tick related
    // Code statement
    private long ticks;

    // Calls a method
    private final Acquirable<Entity> acquirable = Acquirable.unassigned(this);

    // Start of a method/block
    public Entity(EntityType entityType, UUID uuid) {
        // Access to the current/parent object
        this.id = generateId();
        // Access to the current/parent object
        this.entityType = entityType;
        // Access to the current/parent object
        this.uuid = uuid;
        // Access to the current/parent object
        this.position = Pos.ZERO;
        // Access to the current/parent object
        this.headRotation = 0;
        // Access to the current/parent object
        this.previousPosition = Pos.ZERO;
        // Access to the current/parent object
        this.lastSyncedPosition = Pos.ZERO;

        // Access to the current/parent object
        this.entityMeta = MetadataHolder.createMeta(entityType, this, this.metadata);

        // Calls a method
        final RegistryData.EntityEntry registry = entityType.registry();
        // Calls a method
        setBoundingBox(entityType.registry().boundingBox());

        // Access to the current/parent object
        this.aerodynamics = new Aerodynamics(
                // Code statement
                registry.acceleration(),
                // Code statement
                registry.horizontalAirResistance(),
                // Calls a method
                registry.verticalAirResistance());

        // Calls a method
        final ServerProcess process = MinecraftServer.process();
        // Branch: checks a condition
        if (process != null) {
            // Access to the current/parent object
            this.eventNode = process.eventHandler().map(this, EventFilter.ENTITY);
        // Alternative branch of the condition
        } else {
            // Local nodes require a server process
            // Access to the current/parent object
            this.eventNode = null;
        // End of a block/expression
        }
        // Calls a method
        updateCollisions();
    // End of a block/expression
    }

    // Start of a method/block
    public Entity(EntityType entityType) {
        // Calls a method
        this(entityType, UUID.randomUUID());
    // End of a block/expression
    }

    // Start of a method/block
    protected void setPositionInternal(Pos newPosition, float headRotation) {
        // Branch: checks a condition
        if (newPosition.x() >= MAX_COORDINATE || newPosition.x() <= -MAX_COORDINATE ||
                // Code statement
                newPosition.y() >= MAX_COORDINATE || newPosition.y() <= -MAX_COORDINATE ||
                // Start of a method/block
                newPosition.z() >= MAX_COORDINATE || newPosition.z() <= -MAX_COORDINATE) {
            // Assigns a value
            newPosition = newPosition.withCoord(
                    // Code statement
                    MathUtils.clamp(newPosition.x(), -MAX_COORDINATE, MAX_COORDINATE),
                    // Code statement
                    MathUtils.clamp(newPosition.y(), -MAX_COORDINATE, MAX_COORDINATE),
                    // Code statement
                    MathUtils.clamp(newPosition.z(), -MAX_COORDINATE, MAX_COORDINATE)
            // End of a block/expression
            );
        // End of a block/expression
        }
        // Access to the current/parent object
        this.position = newPosition;
        // Access to the current/parent object
        this.headRotation = headRotation;
    // End of a block/expression
    }

    /**
     * Schedules a task to be run during the next entity tick.
     *
     * @param callback the task to execute during the next entity tick
     */
    // Start of a method/block
    public void scheduleNextTick(Consumer<? super Entity> callback) {
        // Access to the current/parent object
        this.scheduler.scheduleNextTick(() -> callback.accept(this));
    // End of a block/expression
    }

    /**
     * Generate and return a new unique entity id.
     * <p>
     * Useful if you want to spawn entities using packet but don't risk to have duplicated id.
     *
     * @return a newly generated entity id
     */
    // Start of a method/block
    public static int generateId() {
        // Returns a value to the caller
        return LAST_ENTITY_ID.incrementAndGet();
    // End of a block/expression
    }

    /**
     * Called each tick.
     *
     * @param time time of the update in milliseconds. This may only be used as a delta and has no meaning in the real world
     */
    // Start of a method/block
    public void update(long time) {

    // End of a block/expression
    }

    /**
     * Called when a new instance is set.
     */
    // Start of a method/block
    public void spawn() {

    // End of a block/expression
    }

    /**
     * Called right before an entity is removed
     */
    // Start of a method/block
    protected void despawn() {

    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnGround() {
        // Returns a value to the caller
        return onGround;
    // End of a block/expression
    }

    /**
     * Gets metadata of this entity.
     * You may want to cast it to specific implementation.
     *
     * @return metadata of this entity.
     */
    // Start of a method/block
    public EntityMeta getEntityMeta() {
        // Returns a value to the caller
        return this.entityMeta;
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.CUSTOM_DATA)
            // Returns a value to the caller
            return (T) new CustomData(tagHandler.asCompound());
        // Returns a value to the caller
        return EntityMeta.getComponent(getEntityMeta(), component);
    // End of a block/expression
    }

    // Start of a method/block
    public <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.CUSTOM_DATA) {
            // Calls a method
            tagHandler.updateContent(((CustomData) value).nbt());
        // Alternative branch of the condition
        } else EntityMeta.setComponent(getEntityMeta(), component, value);
    // End of a block/expression
    }

    /**
     * Do a batch edit of this entity's metadata.
     */
    // Start of a method/block
    public <TMeta extends EntityMeta> void editEntityMeta(Class<TMeta> metaClass, Consumer<TMeta> editor) {
        // Calls a method
        entityMeta.setNotifyAboutChanges(false);
        // Exception handling
        try {
            // Calls a method
            TMeta casted = metaClass.cast(entityMeta);
            // Calls a method
            editor.accept(casted);
        // Start of a method/block
        } catch (Throwable t) {
            // Throws an exception
            throw new RuntimeException("Error editing entity " + id + " " + entityType.name() + " meta", t);
        // Start of a method/block
        } finally {
            // Calls a method
            entityMeta.setNotifyAboutChanges(true);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public CompletableFuture<Void> teleport(Pos position) {
        // Returns a value to the caller
        return teleport(position, null, RelativeFlags.NONE);
    // End of a block/expression
    }

    // Start of a method/block
    public CompletableFuture<Void> teleport(Pos position, Vec velocity) {
        // Returns a value to the caller
        return teleport(position, velocity, null, RelativeFlags.NONE);
    // End of a block/expression
    }

    // Code statement
    public CompletableFuture<Void> teleport(Pos position, long @Nullable [] chunks,
                                            // Annotation for the following element
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Returns a value to the caller
        return teleport(position, chunks, flags, true);
    // End of a block/expression
    }

    // Code statement
    public CompletableFuture<Void> teleport(Pos position, Vec velocity, long @Nullable [] chunks,
                                            // Annotation for the following element
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Returns a value to the caller
        return teleport(position, velocity, chunks, flags, true);
    // End of a block/expression
    }

    // Code statement
    public CompletableFuture<Void> teleport(Pos position, long @Nullable [] chunks,
                                            // Annotation for the following element
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags,
                                            // Start of a method/block
                                            boolean shouldConfirm) {
        // Use delta coord if not providing a delta velocity (to avoid resetting velocity)
        // Returns a value to the caller
        return teleport(position, Vec.ZERO, chunks, flags | RelativeFlags.DELTA_COORD, shouldConfirm);
    // End of a block/expression
    }

    /**
     * Teleports the entity only if the chunk at {@code position} is loaded or if
     * {@link Instance#hasEnabledAutoChunkLoad()} returns true.
     *
     * @param position      the teleport position
     * @param chunks        the chunk indexes to load before teleporting the entity,
     *                      indexes are from {@link CoordConversion#chunkIndex(int, int)},
     *                      can be null or empty to only load the chunk at {@code position}
     * @param flags         flags used to teleport the entity relatively rather than absolutely
     *                      use {@link RelativeFlags} to see available flags
     * @param shouldConfirm if false, the teleportation will be done without confirmation
     * @throws IllegalStateException if you try to teleport an entity before settings its instance
     */
    // Code statement
    public CompletableFuture<Void> teleport(Pos position, Vec velocity, long @Nullable [] chunks,
                                            // Annotation for the following element
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags,
                                            // Start of a method/block
                                            boolean shouldConfirm) {
        // Calls a method
        Check.stateCondition(instance == null, "You need to use Entity#setInstance before teleporting an entity!");

        // Calls a method
        EntityTeleportEvent event = new EntityTeleportEvent(this, position, flags);
        // Calls a method
        EventDispatcher.call(event);

        // Calls a method
        final Pos globalPosition = PositionUtils.getPositionWithRelativeFlags(this.position, position, flags);
        // Calls a method
        final Vec globalVelocity = PositionUtils.getVelocityWithRelativeFlags(this.velocity, velocity, flags);

        // Assigns a value
        final Runnable endCallback = () -> {
            // Access to the current/parent object
            this.previousPosition = this.position;
            // Calls a method
            setPositionInternal(globalPosition, globalPosition.yaw());
            // Access to the current/parent object
            this.velocity = globalVelocity;
            // Calls a method
            refreshCoordinate(globalPosition);
            // Branch: checks a condition
            if (this instanceof Player player)
                // Calls a method
                player.synchronizePositionAfterTeleport(position, velocity, flags, shouldConfirm);
            // Alternative branch of the condition
            else synchronizePosition();
        // End of a block/expression
        };

        // Branch: checks a condition
        if (chunks != null && chunks.length > 0) {
            // Chunks need to be loaded before the teleportation can happen
            // Returns a value to the caller
            return ChunkUtils.optionalLoadAll(instance, chunks, null).thenRun(endCallback);
        // End of a block/expression
        }
        // Assigns a value
        final Pos currentPosition = this.position;
        // Branch: checks a condition
        if (!currentPosition.sameChunk(globalPosition)) {
            // Ensure that the chunk is loaded
            // Returns a value to the caller
            return instance.loadOptionalChunk(globalPosition).thenRun(endCallback);
        // Alternative branch of the condition
        } else {
            // Position is in the same chunk, keep it sync
            // Calls a method
            endCallback.run();
            // Returns a value to the caller
            return AsyncUtils.empty();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Changes the view of the entity.
     * The head rotation will be updated to the yaw value.
     *
     * @param yaw   the new yaw
     * @param pitch the new pitch
     */
    // Start of a method/block
    public void setView(float yaw, float pitch) {
        // Calls a method
        setView(yaw, pitch, yaw);
    // End of a block/expression
    }

    /**
     * Changes the view and head rotation of the entity.
     * This is only really useful for mobs whose heads are looking in a different direction than their body.
     * <p>
     * The client has a lot of prediction on this front, so using your own logic for this might not produce the desired result.
     * For example: if the entity is not moving, the body will automatically rotate towards the head after a few ticks.
     *
     * @param yaw          the new yaw
     * @param pitch        the new pitch
     * @param headRotation the new head rotation
     */
    // Start of a method/block
    public void setView(float yaw, float pitch, float headRotation) {
        // Calls a method
        headRotation = Pos.fixYaw(headRotation);
        // Assigns a value
        final Pos currentPosition = this.position;
        // Branch: checks a condition
        if (currentPosition.sameView(yaw, pitch) && this.headRotation == headRotation) return;
        // Calls a method
        setPositionInternal(currentPosition.withView(yaw, pitch), headRotation);
        // Calls a method
        synchronizeView();
    // End of a block/expression
    }

    /**
     * Changes the view of the entity so that it looks in a direction to the given position if
     * it is different from the entity's current position.
     *
     * @param point the point to look at.
     */
    // Start of a method/block
    public void lookAt(Point point) {
        // Calls a method
        final Pos newPosition = this.position.add(0, getEyeHeight(), 0).withLookAt(point);
        // Calls a method
        setView(newPosition.yaw(), newPosition.pitch());
    // End of a block/expression
    }

    /**
     * Changes the view of the entity so that it looks in a direction to the given entity.
     *
     * @param entity the entity to look at.
     * @throws IllegalArgumentException if the entities are not in the same instance
     */
    // Start of a method/block
    public void lookAt(Entity entity) {
        // Calls a method
        Check.argCondition(entity.instance != instance, "Entity cannot look at an entity in another instance");
        // Calls a method
        lookAt(entity.position.withY(entity.position.y() + entity.getEyeHeight()));
    // End of a block/expression
    }

    /**
     * Gets if this entity is automatically sent to surrounding players.
     * True by default.
     *
     * @return true if the entity is automatically viewable for close players, false otherwise
     */
    // Start of a method/block
    public boolean isAutoViewable() {
        // Returns a value to the caller
        return viewEngine.viewableOption.isAuto();
    // End of a block/expression
    }

    /**
     * Decides if this entity should be auto-viewable by nearby players.
     *
     * @param autoViewable true to add surrounding players, false to remove
     * @see #isAutoViewable()
     */
    // Start of a method/block
    public void setAutoViewable(boolean autoViewable) {
        // Access to the current/parent object
        this.viewEngine.viewableOption.updateAuto(autoViewable);
    // End of a block/expression
    }

    // Start of a method/block
    public void updateViewableRule(@Nullable Predicate<? super Player> predicate) {
        // Access to the current/parent object
        this.viewEngine.viewableOption.updateRule(predicate);
    // End of a block/expression
    }

    // Start of a method/block
    public void updateViewableRule() {
        // Access to the current/parent object
        this.viewEngine.viewableOption.updateRule();
    // End of a block/expression
    }

    /**
     * Gets if surrounding entities are automatically visible by this.
     * True by default.
     *
     * @return true if surrounding entities are visible by this
     */
    // Start of a method/block
    public boolean autoViewEntities() {
        // Returns a value to the caller
        return viewEngine.viewerOption.isAuto();
    // End of a block/expression
    }

    /**
     * Decides if surrounding entities must be visible.
     *
     * @param autoViewer true to add view surrounding entities, false to remove
     */
    // Start of a method/block
    public void setAutoViewEntities(boolean autoViewer) {
        // Access to the current/parent object
        this.viewEngine.viewerOption.updateAuto(autoViewer);
    // End of a block/expression
    }

    // Start of a method/block
    public void updateViewerRule(@Nullable Predicate<? super Entity> predicate) {
        // Access to the current/parent object
        this.viewEngine.viewerOption.updateRule(predicate);
    // End of a block/expression
    }

    // Start of a method/block
    public void updateViewerRule() {
        // Access to the current/parent object
        this.viewEngine.viewerOption.updateRule();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public final boolean addViewer(Player player) {
        // Calls a method
        Check.stateCondition(!isActive(), "Entities must be in an instance before adding viewers");
        // Branch: checks a condition
        if (!viewEngine.manualAdd(player)) return false;
        // Calls a method
        updateNewViewer(player);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public final boolean removeViewer(Player player) {
        // Branch: checks a condition
        if (!viewEngine.manualRemove(player)) return false;
        // Calls a method
        updateOldViewer(player);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Called when a new viewer must be shown.
     * Method can be subject to deadlocking if the target's viewers are also accessed.
     *
     * @param player the player to send the packets to
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void updateNewViewer(Player player) {
        // Calls a method
        player.sendPacket(getSpawnPacket());
        // Branch: checks a condition
        if (hasVelocity()) player.sendPacket(getVelocityPacket());
        // Calls a method
        player.sendPacket(this.getMetadataPacket());
        // Passengers are handled in EntityView

        // Leashes
        // Branch: checks a condition
        if (leashHolder != null && (player.equals(leashHolder) || leashHolder.isViewer(player))) {
            // Calls a method
            player.sendPacket(getAttachEntityPacket());
        // End of a block/expression
        }
        // Loop: repeats a block
        for (Entity entity : leashedEntities) {
            // Branch: checks a condition
            if (entity.isViewer(player)) {
                // Calls a method
                player.sendPacket(entity.getAttachEntityPacket());
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Head position
        // Calls a method
        player.sendPacket(new EntityHeadLookPacket(getEntityId(), headRotation));
    // End of a block/expression
    }

    /**
     * Called when a viewer must be destroyed.
     * Method can be subject to deadlocking if the target's viewers are also accessed.
     *
     * @param player the player to send the packets to
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void updateOldViewer(Player player) {
        // Calls a method
        leashedEntities.forEach(entity -> player.sendPacket(new AttachEntityPacket(entity.getEntityId(), -1)));
        // Calls a method
        player.sendPacket(destroyPacketCache);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return viewers;
    // End of a block/expression
    }

    /**
     * Gets if this entity's viewers (surrounding players) can be predicted from surrounding chunks.
     */
    // Start of a method/block
    public boolean hasPredictableViewers() {
        // Returns a value to the caller
        return viewEngine.hasPredictableViewers();
    // End of a block/expression
    }

    /**
     * Changes the entity type of this entity.
     * <p>
     * Works by changing the internal entity type field and by calling {@link #removeViewer(Player)}
     * followed by {@link #addViewer(Player)} to all current viewers.
     * <p>
     * Be aware that this only change the visual of the entity, the {@link BoundingBox}
     * will not be modified.
     *
     * @param entityType the new entity type
     */
    // Start of a method/block
    public synchronized void switchEntityType(EntityType entityType) {
        // Access to the current/parent object
        this.entityType = entityType;
        // Access to the current/parent object
        this.metadata = new MetadataHolder(this::notifyMetadataChanges);
        // Access to the current/parent object
        this.entityMeta = MetadataHolder.createMeta(entityType, this, this.metadata);

        // Calls a method
        final RegistryData.EntityEntry registry = entityType.registry();
        // Access to the current/parent object
        this.aerodynamics = aerodynamics.withAirResistance(
                // Code statement
                registry.horizontalAirResistance(),
                // Calls a method
                registry.verticalAirResistance());

        // Calls a method
        updateCollisions();
        // Calls a method
        Set<Player> viewers = new HashSet<>(getViewers());
        // Calls a method
        getViewers().forEach(this::updateOldViewer);
        // Calls a method
        viewers.forEach(this::updateNewViewer);
    // End of a block/expression
    }

    /**
     * Updates the entity, called every tick.
     * <p>
     * Ignored if {@link #getInstance()} returns null.
     *
     * @param time the update time in milliseconds. This may only be used as a delta and has no meaning in the real world.
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Branch: checks a condition
        if (instance == null || isRemoved() || !ChunkUtils.isLoaded(currentChunk))
            // Returns a value to the caller
            return;

        // scheduled tasks
        // Access to the current/parent object
        this.scheduler.processTick();
        // Branch: checks a condition
        if (isRemoved()) return;

        // Entity tick
        // Start of a block
        {
            // handle position and velocity updates
            // Calls a method
            movementTick();

            // handle block contacts
            // Calls a method
            touchTick();

            // Call the abstract update method
            // Calls a method
            update(time);

            // Code statement
            ticks++;
            // Calls a method
            EventDispatcher.call(new EntityTickEvent(this));

            // remove expired effects
            // Calls a method
            effectTick();
        // End of a block/expression
        }
        // Scheduled synchronization
        // Branch: checks a condition
        if (ticks >= nextSynchronizationTick) {
            // Branch: checks a condition
            if (vehicle == null) {
                // Calls a method
                synchronizePosition();
                // Calls a method
                sendPacketToViewers(getVelocityPacket());
            // Alternative branch of the condition
            } else {
                // Calls a method
                synchronizeView();
                // Assigns a value
                nextSynchronizationTick = ticks + synchronizationTicks;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // End of tick scheduled tasks
        // Access to the current/parent object
        this.scheduler.processTickEnd();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected void movementTick() {
        // Access to the current/parent object
        this.gravityTickCount = onGround ? 0 : gravityTickCount + 1;
        // Branch: checks a condition
        if (vehicle != null) return;

        // Assigns a value
        boolean entityIsPlayer = this instanceof Player;
        // Calls a method
        boolean entityFlying = entityIsPlayer && ((Player) this).isFlying();
        // Calls a method
        final Block.Getter chunkCache = new ChunkCache(instance, currentChunk, Block.STONE);
        // Assigns a value
        PhysicsResult physicsResult = PhysicsUtils.simulateMovement(position, velocity.div(ServerFlag.SERVER_TICKS_PER_SECOND), boundingBox,
                // Calls a method
                instance.getWorldBorder(), chunkCache, aerodynamics, hasNoGravity(), hasPhysics, onGround, entityFlying, previousPhysicsResult);
        // Access to the current/parent object
        this.previousPhysicsResult = physicsResult;

        // Calls a method
        Chunk finalChunk = ChunkUtils.retrieve(instance, currentChunk, physicsResult.newPosition());
        // Branch: checks a condition
        if (!ChunkUtils.isLoaded(finalChunk)) return;

        // Calls a method
        velocity = physicsResult.newVelocity().mul(ServerFlag.SERVER_TICKS_PER_SECOND);
        // Branch: checks a condition
        if (!(this instanceof Player)) {
            // Calls a method
            onGround = physicsResult.isOnGround();
            // Calls a method
            refreshPosition(physicsResult.newPosition(), true, !SYNCHRONIZE_ONLY_ENTITIES.contains(entityType));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void touchTick() {
        // Branch: checks a condition
        if (!hasPhysics) return;

        // TODO do not call every tick (it is pretty expensive)
        // Assigns a value
        final Pos position = this.position;
        // Assigns a value
        final BoundingBox boundingBox = this.boundingBox;
        // Calls a method
        ChunkCache cache = new ChunkCache(instance, currentChunk);

        // Calls a method
        final int minX = (int) Math.floor(boundingBox.minX() + position.x());
        // Calls a method
        final int maxX = (int) Math.ceil(boundingBox.maxX() + position.x());
        // Calls a method
        final int minY = (int) Math.floor(boundingBox.minY() + position.y());
        // Calls a method
        final int maxY = (int) Math.ceil(boundingBox.maxY() + position.y());
        // Calls a method
        final int minZ = (int) Math.floor(boundingBox.minZ() + position.z());
        // Calls a method
        final int maxZ = (int) Math.ceil(boundingBox.maxZ() + position.z());

        // Loop: repeats a block
        for (int y = minY; y <= maxY; y++) {
            // Loop: repeats a block
            for (int x = minX; x <= maxX; x++) {
                // Loop: repeats a block
                for (int z = minZ; z <= maxZ; z++) {
                    // Calls a method
                    final Block block = cache.getBlock(x, y, z, Block.Getter.Condition.CACHED);
                    // Branch: checks a condition
                    if (block == null) continue;
                    // Calls a method
                    final BlockHandler handler = block.handler();
                    // Branch: checks a condition
                    if (handler != null) {
                        // Move a small amount towards the entity. If the entity is within 0.01 blocks of the block, touch will trigger
                        // Calls a method
                        Vec blockPos = new Vec(x, y, z);
                        // Calls a method
                        Point blockEntityVector = (blockPos.sub(position)).normalize().mul(0.01);
                        // Branch: checks a condition
                        if (block.registry().collisionShape().intersectBox(position.sub(blockPos).add(blockEntityVector), boundingBox)) {
                            // Calls a method
                            handler.onTouch(new BlockHandler.Touch(block, instance, new Vec(x, y, z), this));
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void effectTick() {
        // Assigns a value
        final List<TimedPotion> effects = this.effects;
        // Branch: checks a condition
        if (effects.isEmpty()) return;
        // Start of a method/block
        effects.removeIf(timedPotion -> {
            // Calls a method
            long duration = timedPotion.potion().duration();
            // Branch: checks a condition
            if (duration == Potion.INFINITE_DURATION) return false;
            // Remove if the potion should be expired
            // Branch: checks a condition
            if (getAliveTicks() >= timedPotion.startingTicks() + duration) {
                // Send the packet that the potion should no longer be applied
                // Calls a method
                timedPotion.potion().sendRemovePacket(this);
                // Calls a method
                EventDispatcher.call(new EntityPotionRemoveEvent(this, timedPotion.potion()));
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
            // Returns a value to the caller
            return false;
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Gets the number of ticks this entity has been active for.
     *
     * @return the number of ticks this entity has been active for
     */
    // Start of a method/block
    public long getAliveTicks() {
        // Returns a value to the caller
        return ticks;
    // End of a block/expression
    }

    /**
     * Each entity has an unique id (server-wide) which will change after a restart.
     *
     * @return the unique entity id
     * @see Instance#getEntityById(int) to retrieve an entity based on its id
     */
    // Start of a method/block
    public int getEntityId() {
        // Returns a value to the caller
        return id;
    // End of a block/expression
    }

    /**
     * Returns the entity type.
     *
     * @return the entity type
     */
    // Start of a method/block
    public EntityType getEntityType() {
        // Returns a value to the caller
        return entityType;
    // End of a block/expression
    }

    /**
     * Gets the entity {@link UUID}.
     *
     * @return the entity unique id
     */
    // Start of a method/block
    public UUID getUuid() {
        // Returns a value to the caller
        return uuid;
    // End of a block/expression
    }

    /**
     * Returns whether this entity will run physics calculations.
     *
     * @return whether the entity will have physics calculations running
     */
    // Start of a method/block
    public boolean hasPhysics() {
        // Returns a value to the caller
        return hasPhysics;
    // End of a block/expression
    }

    /**
     * Changes whether this entity has physics calculations running.
     *
     * @param hasPhysics whether the entity will have physics calculations running
     */
    // Start of a method/block
    public void setHasPhysics(boolean hasPhysics) {
        // Access to the current/parent object
        this.hasPhysics = hasPhysics;
    // End of a block/expression
    }

    /**
     * Returns false just after instantiation, set to true after calling {@link #setInstance(Instance)}.
     *
     * @return true if the entity has been linked to an instance, false otherwise
     */
    // Start of a method/block
    public boolean isActive() {
        // Returns a value to the caller
        return isActive;
    // End of a block/expression
    }

    /**
     * Returns the current bounding box (based on pose).
     * Is used to check collision with coordinates or other blocks/entities.
     *
     * @return the entity bounding box
     */
    // Start of a method/block
    public BoundingBox getBoundingBox() {
        // Check if there is a specific bounding box for this pose
        // Calls a method
        BoundingBox poseBoundingBox = BoundingBox.fromPose(getPose());
        // Returns a value to the caller
        return poseBoundingBox == null ? boundingBox : poseBoundingBox;
    // End of a block/expression
    }

    /**
     * Changes the internal entity standing bounding box.
     * When the pose is not standing, a different bounding box may be used for collision.
     * <p>
     * WARNING: this does not change the entity hit-box which is client-side.
     *
     * @param width  the bounding box X size
     * @param height the bounding box Y size
     * @param depth  the bounding box Z size
     */
    // Start of a method/block
    public void setBoundingBox(double width, double height, double depth) {
        // Calls a method
        setBoundingBox(new BoundingBox(width, height, depth));
    // End of a block/expression
    }

    /**
     * Changes the internal entity standing bounding box.
     * When the pose is not standing, a different bounding box may be used for collision.
     * <p>
     * WARNING: this does not change the entity hit-box which is client-side.
     *
     * @param boundingBox the new bounding box
     */
    // Start of a method/block
    public void setBoundingBox(BoundingBox boundingBox) {
        // Access to the current/parent object
        this.boundingBox = boundingBox;
    // End of a block/expression
    }

    /**
     * Convenient method to get the entity current chunk.
     *
     * @return the entity chunk, can be null even if unlikely
     */
    // Start of a method/block
    public @Nullable Chunk getChunk() {
        // Returns a value to the caller
        return currentChunk;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected void refreshCurrentChunk(Chunk currentChunk) {
        // Access to the current/parent object
        this.currentChunk = currentChunk;
        // Calls a method
        MinecraftServer.process().dispatcher().updateElement(this, currentChunk);
    // End of a block/expression
    }

    /**
     * Gets the entity current instance.
     *
     * @return the entity instance, can be null if the entity doesn't have an instance yet
     */
    // Start of a method/block
    public @UnknownNullability Instance getInstance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    /**
     * Changes the entity instance, i.e. spawns it.
     *
     * @param instance      the new instance of the entity
     * @param spawnPosition the spawn position for the entity.
     * @return a {@link CompletableFuture} called once the entity's instance has been set,
     * this is due to chunks needing to load
     * @throws IllegalStateException if {@code instance} has not been registered in {@link InstanceManager}
     */
    // Start of a method/block
    public CompletableFuture<Void> setInstance(Instance instance, Pos spawnPosition) {
        // Code statement
        Check.stateCondition(!instance.isRegistered(),
                // Code statement
                "Instances need to be registered, please use InstanceManager#registerInstance or InstanceManager#registerSharedInstance");
        // Assigns a value
        final Instance previousInstance = this.instance;
        // Branch: checks a condition
        if (Objects.equals(previousInstance, instance)) {
            // Returns a value to the caller
            return teleport(spawnPosition); // Already in the instance, teleport to spawn point
        // End of a block/expression
        }
        // Calls a method
        AddEntityToInstanceEvent event = new AddEntityToInstanceEvent(instance, this);
        // Calls a method
        EventDispatcher.call(event);
        // Branch: checks a condition
        if (event.isCancelled()) return null; // TODO what to return?

        // Branch: checks a condition
        if (previousInstance != null) removeFromInstance(previousInstance);
        // Branch: checks a condition
        if (this instanceof Player player) instance.bossBars().forEach(player::showBossBar);
        // Calls a method
        EventsJFR.newInstanceJoin(getUuid(), instance.getUuid()).commit();

        // Access to the current/parent object
        this.isActive = true;
        // Calls a method
        setPositionInternal(spawnPosition, spawnPosition.yaw());
        // Access to the current/parent object
        this.previousPosition = spawnPosition;
        // Access to the current/parent object
        this.lastSyncedPosition = spawnPosition;
        // Access to the current/parent object
        this.previousPhysicsResult = null;
        // Access to the current/parent object
        this.instance = instance;
        // Returns a value to the caller
        return instance.loadOptionalChunk(spawnPosition).thenAccept(chunk -> {
            // Exception handling
            try {
                // Calls a method
                Objects.requireNonNull(chunk, "Entity has been placed in an unloaded chunk!");
                // Calls a method
                refreshCurrentChunk(chunk);
                // Branch: checks a condition
                if (this instanceof Player player) {
                    // Calls a method
                    player.sendPacket(instance.createInitializeWorldBorderPacket());
                    // Calls a method
                    player.sendPacket(instance.createTimePacket());
                    // Calls a method
                    player.sendPackets(instance.getWeather().createWeatherPackets());
                // End of a block/expression
                }
                // Calls a method
                instance.getEntityTracker().register(this, spawnPosition, trackingTarget, trackingUpdate);
                // Calls a method
                spawn();
                // Calls a method
                EventDispatcher.call(new EntitySpawnEvent(this, instance));
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    public CompletableFuture<Void> setInstance(Instance instance, Point spawnPosition) {
        // Returns a value to the caller
        return setInstance(instance, spawnPosition.asPos());
    // End of a block/expression
    }

    /**
     * Changes the entity instance.
     *
     * @param instance the new instance of the entity
     * @return a {@link CompletableFuture} called once the entity's instance has been set,
     * this is due to chunks needing to load
     * @throws NullPointerException  if {@code instance} is null
     * @throws IllegalStateException if {@code instance} has not been registered in {@link InstanceManager}
     */
    // Start of a method/block
    public CompletableFuture<Void> setInstance(Instance instance) {
        // Returns a value to the caller
        return setInstance(instance, this.position);
    // End of a block/expression
    }

    // Start of a method/block
    private void removeFromInstance(Instance instance) {
        // Calls a method
        EventDispatcher.call(new RemoveEntityFromInstanceEvent(instance, this));
        // Branch: checks a condition
        if (this instanceof Player player) instance.bossBars().forEach(player::hideBossBar);
        // Calls a method
        instance.getEntityTracker().unregister(this, trackingTarget, trackingUpdate);
        // Access to the current/parent object
        this.viewEngine.forManuals(this::removeViewer);
        // Calls a method
        EventsJFR.newInstanceLeave(getUuid(), instance.getUuid()).commit();
    // End of a block/expression
    }

    /**
     * Gets the entity current velocity.
     *
     * @return the entity current velocity
     */
    // Start of a method/block
    public Vec getVelocity() {
        // Returns a value to the caller
        return velocity;
    // End of a block/expression
    }

    /**
     * Changes the entity velocity and calls {@link EntityVelocityEvent}.
     * <p>
     * The final velocity can be cancelled or modified by the event.
     *
     * @param velocity the new entity velocity
     */
    // Start of a method/block
    public void setVelocity(Vec velocity) {
        // Calls a method
        EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent(this, velocity);
        // Start of a method/block
        EventDispatcher.callCancellable(entityVelocityEvent, () -> {
            // Access to the current/parent object
            this.velocity = entityVelocityEvent.getVelocity();
            // Calls a method
            sendPacketToViewersAndSelf(getVelocityPacket());
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Gets if the entity currently has a velocity applied.
     *
     * @return true if the entity is moving
     */
    // Start of a method/block
    public boolean hasVelocity() {
        // Branch: checks a condition
        if (isOnGround()) {
            // if the entity is on the ground and only "moves" downwards, it does not have a velocity.
            // Returns a value to the caller
            return Double.compare(velocity.x(), 0) != 0 || Double.compare(velocity.z(), 0) != 0 || velocity.y() > 0;
        // Alternative branch of the condition
        } else {
            // The entity does not have velocity if the velocity is zero
            // Returns a value to the caller
            return !velocity.isZero();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the aerodynamics; how the entity behaves in the air.
     *
     * @return the aerodynamic properties this entity is using
     */
    // Start of a method/block
    public Aerodynamics getAerodynamics() {
        // Returns a value to the caller
        return aerodynamics;
    // End of a block/expression
    }

    /**
     * Sets the aerodynamics; how the entity behaves in the air.
     *
     * @param aerodynamics the new aerodynamic properties
     */
    // Start of a method/block
    public void setAerodynamics(Aerodynamics aerodynamics) {
        // Access to the current/parent object
        this.aerodynamics = aerodynamics;
    // End of a block/expression
    }

    /**
     * Gets the number of tick this entity has been applied gravity.
     *
     * @return the number of tick of which gravity has been consequently applied
     */
    // Start of a method/block
    public int getGravityTickCount() {
        // Returns a value to the caller
        return gravityTickCount;
    // End of a block/expression
    }

    // Start of a method/block
    public double getDistance(Point point) {
        // Returns a value to the caller
        return getPosition().distance(point);
    // End of a block/expression
    }

    /**
     * Gets the distance between two entities.
     *
     * @param entity the entity to get the distance from
     * @return the distance between this and {@code entity}
     */
    // Start of a method/block
    public double getDistance(Entity entity) {
        // Returns a value to the caller
        return getDistance(entity.getPosition());
    // End of a block/expression
    }

    // Start of a method/block
    public double getDistanceSquared(Point point) {
        // Returns a value to the caller
        return getPosition().distanceSquared(point);
    // End of a block/expression
    }

    /**
     * Gets the distance squared between two entities.
     *
     * @param entity the entity to get the distance from
     * @return the distance squared between this and {@code entity}
     */
    // Start of a method/block
    public double getDistanceSquared(Entity entity) {
        // Returns a value to the caller
        return getPosition().distanceSquared(entity.getPosition());
    // End of a block/expression
    }

    /**
     * Gets the entity vehicle or null.
     *
     * @return the entity vehicle, or null if there is not any
     */
    // Start of a method/block
    public @Nullable Entity getVehicle() {
        // Returns a value to the caller
        return vehicle;
    // End of a block/expression
    }

    /**
     * Adds a new passenger to this entity.
     *
     * @param entity the new passenger
     * @throws NullPointerException  if {@code entity} is null
     * @throws IllegalStateException if {@link #getInstance()} returns null or the passenger cannot be added
     */
    // Start of a method/block
    public void addPassenger(Entity entity) {
        // Assigns a value
        final Instance currentInstance = this.instance;
        // Calls a method
        Check.stateCondition(currentInstance == null, "You need to set an instance using Entity#setInstance");
        // Calls a method
        Check.stateCondition(entity == getVehicle(), "Cannot add the entity vehicle as a passenger");
        // Calls a method
        final Entity vehicle = entity.getVehicle();
        // Branch: checks a condition
        if (vehicle != null) vehicle.removePassenger(entity);
        // Branch: checks a condition
        if (!currentInstance.equals(entity.getInstance()))
            // Calls a method
            entity.setInstance(currentInstance, position).join();
        // Access to the current/parent object
        this.passengers.add(entity);
        // Assigns a value
        entity.vehicle = this;
        // Calls a method
        sendPacketToViewersAndSelf(getPassengersPacket());
        // Calls a method
        updatePassengerPosition(position, entity);
        // Calls a method
        entity.synchronizePosition();
    // End of a block/expression
    }


    /**
     * Removes a passenger to this entity.
     *
     * @param entity the passenger to remove
     * @throws NullPointerException  if {@code entity} is null
     * @throws IllegalStateException if {@link #getInstance()} returns null
     */
    // Start of a method/block
    public void removePassenger(Entity entity) {
        // Calls a method
        Check.stateCondition(instance == null, "You need to set an instance using Entity#setInstance");
        // Branch: checks a condition
        if (!passengers.remove(entity)) return;
        // Assigns a value
        entity.vehicle = null;
        // Calls a method
        sendPacketToViewersAndSelf(getPassengersPacket());
        // Calls a method
        entity.synchronizePosition();
    // End of a block/expression
    }

    /**
     * Gets if the entity has any passenger.
     *
     * @return true if the entity has any passenger, false otherwise
     */
    // Start of a method/block
    public boolean hasPassenger() {
        // Returns a value to the caller
        return !passengers.isEmpty();
    // End of a block/expression
    }

    /**
     * Gets the entity passengers.
     *
     * @return an unmodifiable list containing all the entity passengers
     */
    // Start of a method/block
    public Set<Entity> getPassengers() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(passengers);
    // End of a block/expression
    }

    // Start of a method/block
    protected SetPassengersPacket getPassengersPacket() {
        // Returns a value to the caller
        return new SetPassengersPacket(getEntityId(), passengers.stream().map(Entity::getEntityId).toList());
    // End of a block/expression
    }

    /**
     * Gets the entities that this entity is leashing.
     *
     * @return an unmodifiable list containing all the leashed entities
     */
    // Start of a method/block
    public Set<Entity> getLeashedEntities() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(leashedEntities);
    // End of a block/expression
    }

    /**
     * Gets the current leash holder.
     *
     * @return the entity leashing this entity, null if no leash holder
     */
    // Start of a method/block
    public @Nullable Entity getLeashHolder() {
        // Returns a value to the caller
        return leashHolder;
    // End of a block/expression
    }

    /**
     * Sets the leash holder to this entity.
     *
     * @param entity the new leash holder
     */
    // Start of a method/block
    public void setLeashHolder(@Nullable Entity entity) {
        // Branch: checks a condition
        if (leashHolder != null) leashHolder.leashedEntities.remove(this);
        // Branch: checks a condition
        if (entity != null) entity.leashedEntities.add(this);
        // Access to the current/parent object
        this.leashHolder = entity;
        // Calls a method
        sendPacketToViewersAndSelf(getAttachEntityPacket());
    // End of a block/expression
    }

    // Start of a method/block
    protected AttachEntityPacket getAttachEntityPacket() {
        // Assigns a value
        Entity leashHolder = this.leashHolder;
        // Returns a value to the caller
        return new AttachEntityPacket(getEntityId(), leashHolder != null ? leashHolder.getEntityId() : -1);
    // End of a block/expression
    }

    /**
     * Entity statuses can be found <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_statuses">here</a>.
     *
     * @param status the status to trigger
     */
    // Start of a method/block
    public void triggerStatus(byte status) {
        // Calls a method
        sendPacketToViewersAndSelf(new EntityStatusPacket(getEntityId(), status));
    // End of a block/expression
    }

    /**
     * Gets if the entity is on fire.
     *
     * @return true if the entity is in fire, false otherwise
     */
    // Start of a method/block
    public boolean isOnFire() {
        // Returns a value to the caller
        return this.entityMeta.isOnFire();
    // End of a block/expression
    }

    /**
     * Gets if the entity is sneaking.
     * <p>
     * WARNING: this can be bypassed by hacked client, this is only what the client told the server.
     *
     * @return true if the player is sneaking
     */
    // Start of a method/block
    public boolean isSneaking() {
        // Returns a value to the caller
        return this.entityMeta.isSneaking();
    // End of a block/expression
    }

    /**
     * Makes the entity sneak.
     * <p>
     * WARNING: this will not work for the client itself.
     *
     * @param sneaking true to make the entity sneak
     */
    // Start of a method/block
    public void setSneaking(boolean sneaking) {
        // Access to the current/parent object
        this.entityMeta.setSneaking(sneaking);
        // Calls a method
        updatePose();
    // End of a block/expression
    }

    /**
     * Gets if the player is sprinting.
     * <p>
     * WARNING: this can be bypassed by hacked client, this is only what the client told the server.
     *
     * @return true if the player is sprinting
     */
    // Start of a method/block
    public boolean isSprinting() {
        // Returns a value to the caller
        return this.entityMeta.isSprinting();
    // End of a block/expression
    }

    /**
     * Makes the entity sprint.
     * <p>
     * WARNING: this will not work on the client itself.
     *
     * @param sprinting true to make the entity sprint
     */
    // Start of a method/block
    public void setSprinting(boolean sprinting) {
        // Access to the current/parent object
        this.entityMeta.setSprinting(sprinting);
    // End of a block/expression
    }

    /**
     * Gets if the entity is invisible or not.
     *
     * @return true if the entity is invisible, false otherwise
     */
    // Start of a method/block
    public boolean isInvisible() {
        // Returns a value to the caller
        return this.entityMeta.isInvisible();
    // End of a block/expression
    }

    /**
     * Changes the internal invisible value and send a {@link EntityMetaDataPacket}
     * to make visible or invisible the entity to its viewers.
     *
     * @param invisible true to set the entity invisible, false otherwise
     */
    // Start of a method/block
    public void setInvisible(boolean invisible) {
        // Access to the current/parent object
        this.entityMeta.setInvisible(invisible);
    // End of a block/expression
    }

    /**
     * Gets if the entity is glowing or not.
     *
     * @return true if the entity is glowing, false otherwise
     */
    // Start of a method/block
    public boolean isGlowing() {
        // Returns a value to the caller
        return this.entityMeta.isHasGlowingEffect();
    // End of a block/expression
    }

    /**
     * Sets or remove the entity glowing effect.
     *
     * @param glowing true to make the entity glows, false otherwise
     */
    // Start of a method/block
    public void setGlowing(boolean glowing) {
        // Access to the current/parent object
        this.entityMeta.setHasGlowingEffect(glowing);
    // End of a block/expression
    }

    /**
     * Gets the current entity pose.
     *
     * @return the entity pose
     */
    // Start of a method/block
    public EntityPose getPose() {
        // Returns a value to the caller
        return this.entityMeta.getPose();
    // End of a block/expression
    }

    /**
     * Changes the entity pose.
     * <p>
     * The internal {@code crouched} and {@code swimming} field will be
     * updated accordingly.
     *
     * @param pose the new entity pose
     */
    // Start of a method/block
    public void setPose(EntityPose pose) {
        // Access to the current/parent object
        this.entityMeta.setPose(pose);
    // End of a block/expression
    }

    // Start of a method/block
    protected void updatePose() {
        // Branch: checks a condition
        if (entityMeta.isFlyingWithElytra()) {
            // Calls a method
            setPose(EntityPose.FALL_FLYING);
        // Branch: checks a condition
        } else if (entityMeta.isSwimming()) {
            // Calls a method
            setPose(EntityPose.SWIMMING);
        // Branch: checks a condition
        } else if (entityMeta instanceof LivingEntityMeta livingMeta && livingMeta.isInRiptideSpinAttack()) {
            // Calls a method
            setPose(EntityPose.SPIN_ATTACK);
        // Branch: checks a condition
        } else if (entityMeta.isSneaking()) {
            // Calls a method
            setPose(EntityPose.SNEAKING);
        // Alternative branch of the condition
        } else {
            // Calls a method
            setPose(EntityPose.STANDING);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the entity custom name.
     *
     * @return the custom name of the entity, null if there is not
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public @Nullable Component getCustomName() {
        // Returns a value to the caller
        return this.entityMeta.getCustomName();
    // End of a block/expression
    }

    /**
     * Changes the entity custom name.
     *
     * @param customName the custom name of the entity, null to remove it
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setCustomName(@Nullable Component customName) {
        // Access to the current/parent object
        this.entityMeta.setCustomName(customName);
    // End of a block/expression
    }

    /**
     * Gets the custom name visible metadata field.
     *
     * @return true if the custom name is visible, false otherwise
     */
    // Start of a method/block
    public boolean isCustomNameVisible() {
        // Returns a value to the caller
        return this.entityMeta.isCustomNameVisible();
    // End of a block/expression
    }

    /**
     * Changes the internal custom name visible field and send a {@link EntityMetaDataPacket}
     * to update the entity state to its viewers.
     *
     * @param customNameVisible true to make the custom name visible, false otherwise
     */
    // Start of a method/block
    public void setCustomNameVisible(boolean customNameVisible) {
        // Access to the current/parent object
        this.entityMeta.setCustomNameVisible(customNameVisible);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSilent() {
        // Returns a value to the caller
        return this.entityMeta.isSilent();
    // End of a block/expression
    }

    // Start of a method/block
    public void setSilent(boolean silent) {
        // Access to the current/parent object
        this.entityMeta.setSilent(silent);
    // End of a block/expression
    }

    /**
     * Gets the noGravity metadata field.
     *
     * @return true if the entity ignore gravity, false otherwise
     */
    // Start of a method/block
    public boolean hasNoGravity() {
        // Returns a value to the caller
        return this.entityMeta.isHasNoGravity();
    // End of a block/expression
    }

    /**
     * Changes the noGravity metadata field and change the gravity behaviour accordingly.
     *
     * @param noGravity should the entity ignore gravity
     */
    // Start of a method/block
    public void setNoGravity(boolean noGravity) {
        // Access to the current/parent object
        this.entityMeta.setHasNoGravity(noGravity);
    // End of a block/expression
    }

    /**
     * Updates internal fields and sends updates.
     *
     * @param newPosition the new position
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void refreshPosition(final Pos newPosition, boolean ignoreView, boolean sendPackets) {
        // Assigns a value
        final var previousPosition = this.position;
        // Calls a method
        final Pos position = ignoreView ? previousPosition.withCoord(newPosition) : newPosition;
        // Assigns a value
        final Pos lastSyncedPosition = this.lastSyncedPosition;
        // Branch: checks a condition
        if (position.equals(lastSyncedPosition)) return;
        // Calls a method
        setPositionInternal(position, ignoreView ? headRotation : position.yaw());
        // Access to the current/parent object
        this.previousPosition = previousPosition;
        // Branch: checks a condition
        if (!position.samePoint(previousPosition)) refreshCoordinate(position);
        // Branch: checks a condition
        if (nextSynchronizationTick <= ticks + 1 || !sendPackets) {
            // The entity will be synchronized at the end of its tick
            // not returning here will duplicate position packets
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Update viewers
        // Calls a method
        final boolean viewChange = !position.sameView(lastSyncedPosition);
        // Calls a method
        final double distanceX = Math.abs(position.x() - lastSyncedPosition.x());
        // Calls a method
        final double distanceY = Math.abs(position.y() - lastSyncedPosition.y());
        // Calls a method
        final double distanceZ = Math.abs(position.z() - lastSyncedPosition.z());
        // Calls a method
        final boolean positionChange = (distanceX + distanceY + distanceZ) > 0;

        // Calls a method
        final Chunk chunk = getChunk();
        // Code statement
        assert chunk != null;
        // Branch: checks a condition
        if (distanceX > 8 || distanceY > 8 || distanceZ > 8) {
            // Send relative 0 velocity to avoid affecting it in this case
            // Code statement
            PacketViewableUtils.prepareViewablePacket(chunk, new EntityTeleportPacket(getEntityId(), position,
                    // Calls a method
                    Vec.ZERO, RelativeFlags.DELTA_COORD, isOnGround()), this);
            // Assigns a value
            nextSynchronizationTick = synchronizationTicks + 1;
        // Branch: checks a condition
        } else if (positionChange && viewChange) {
            // Code statement
            PacketViewableUtils.prepareViewablePacket(chunk, EntityPositionAndRotationPacket.getPacket(getEntityId(), position,
                    // Calls a method
                    lastSyncedPosition, isOnGround()), this);
            // Fix head rotation
            // Calls a method
            PacketViewableUtils.prepareViewablePacket(chunk, new EntityHeadLookPacket(getEntityId(), headRotation), this);
        // Branch: checks a condition
        } else if (positionChange) {
            // This is a confusing fix for a confusing issue. If rotation is only sent when the entity actually changes, then spawning an entity
            // on the ground causes the entity not to update its rotation correctly. It works fine if the entity is spawned in the air. Very weird.
            // Code statement
            PacketViewableUtils.prepareViewablePacket(chunk, EntityPositionAndRotationPacket.getPacket(getEntityId(), position,
                    // Code statement
                    lastSyncedPosition, onGround), this);
        // Branch: checks a condition
        } else if (viewChange) {
            // Calls a method
            PacketViewableUtils.prepareViewablePacket(chunk, new EntityHeadLookPacket(getEntityId(), headRotation), this);
            // Code statement
            PacketViewableUtils.prepareViewablePacket(chunk, EntityPositionAndRotationPacket.getPacket(getEntityId(), position,
                    // Calls a method
                    lastSyncedPosition, isOnGround()), this);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.lastSyncedPosition = position;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void refreshPosition(final Pos newPosition, boolean ignoreView) {
        // Calls a method
        refreshPosition(newPosition, ignoreView, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void refreshPosition(final Pos newPosition) {
        // Calls a method
        refreshPosition(newPosition, false);
    // End of a block/expression
    }

    /**
     * Sets the coordinates of the passenger to the coordinates of this vehicle + {@link EntityUtils#getPassengerHeightOffset(Entity, Entity)}
     *
     * @param newPosition the new position of this vehicle
     * @param passenger   the passenger to be moved
     */
    // Start of a method/block
    private void updatePassengerPosition(Point newPosition, Entity passenger) {
        // Assigns a value
        final Pos oldPassengerPos = passenger.position;
        // Assigns a value
        final Pos newPassengerPos = oldPassengerPos.withCoord(newPosition.x(),
                // Code statement
                newPosition.y() + EntityUtils.getPassengerHeightOffset(this, passenger),
                // Calls a method
                newPosition.z());
        // Calls a method
        passenger.setPositionInternal(newPassengerPos, newPassengerPos.yaw());
        // Assigns a value
        passenger.previousPosition = oldPassengerPos;
        // Calls a method
        passenger.refreshCoordinate(newPassengerPos);
    // End of a block/expression
    }

    /**
     * Used to refresh the entity and its passengers position
     * - put the entity in the right instance chunk
     * - update the viewable chunks (load and unload)
     * - add/remove players from the viewers list if {@link #isAutoViewable()} is enabled
     * <p>
     * WARNING: unsafe, should only be used internally in Minestom. Use {@link #teleport(Pos)} instead.
     *
     * @param newPosition the new position
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected void refreshCoordinate(Point newPosition) {
        // Passengers update
        // Calls a method
        final Set<Entity> passengers = getPassengers();
        // Branch: checks a condition
        if (!passengers.isEmpty()) {
            // Loop: repeats a block
            for (Entity passenger : passengers) {
                // Calls a method
                updatePassengerPosition(newPosition, passenger);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Handle chunk switch
        // Calls a method
        final Instance instance = getInstance();
        // Code statement
        assert instance != null;
        // Calls a method
        instance.getEntityTracker().move(this, newPosition, trackingTarget, trackingUpdate);
        // Calls a method
        final int lastChunkX = currentChunk.getChunkX();
        // Calls a method
        final int lastChunkZ = currentChunk.getChunkZ();
        // Calls a method
        final int newChunkX = newPosition.chunkX();
        // Calls a method
        final int newChunkZ = newPosition.chunkZ();
        // Branch: checks a condition
        if (lastChunkX != newChunkX || lastChunkZ != newChunkZ) {
            // Entity moved in a new chunk
            // Calls a method
            final Chunk newChunk = instance.getChunk(newChunkX, newChunkZ);
            // Calls a method
            Check.notNull(newChunk, "The entity {0} tried to move in an unloaded chunk at {1}", getEntityId(), newPosition);
            // Branch: checks a condition
            if (this instanceof Player player) player.sendChunkUpdates(newChunk);
            // Calls a method
            refreshCurrentChunk(newChunk);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the entity position.
     *
     * @return the current position of the entity
     */
    // Start of a method/block
    public Pos getPosition() {
        // Returns a value to the caller
        return position;
    // End of a block/expression
    }

    /**
     * Gets the entity head rotation.
     * In most cases, this will be the same as their yaw.
     * It might be different for mobs which are looking in a different direction than their body.
     * <p>
     * The head rotation can be changed using {@link #setView(float, float, float)}.
     *
     * @return the head rotation
     */
    // Start of a method/block
    public float getHeadRotation() {
        // Returns a value to the caller
        return headRotation;
    // End of a block/expression
    }

    /**
     * Gets the previous entity position.
     *
     * @return the previous position of the entity
     */
    // Start of a method/block
    public Pos getPreviousPosition() {
        // Returns a value to the caller
        return previousPosition;
    // End of a block/expression
    }

    /**
     * Gets the entity eye height.
     *
     * @return the entity eye height
     */
    // Start of a method/block
    public double getEyeHeight() {
        // Returns a value to the caller
        return getPose() == EntityPose.SLEEPING ? 0.2 : entityType.registry().eyeHeight();
    // End of a block/expression
    }

    /**
     * Gets all the potion effect of this entity.
     *
     * @return an unmodifiable list of all this entity effects
     */
    // Start of a method/block
    public List<TimedPotion> getActiveEffects() {
        // Returns a value to the caller
        return Collections.unmodifiableList(effects);
    // End of a block/expression
    }

    /**
     * Adds an effect to an entity.
     *
     * @param potion The potion to add
     */
    // Start of a method/block
    public void addEffect(Potion potion) {
        // Start of a method/block
        EventDispatcher.callCancellable(new EntityPotionAddEvent(this, potion), () -> {
            // Calls a method
            removeEffect(potion.effect());
            // Access to the current/parent object
            this.effects.add(new TimedPotion(potion, getAliveTicks()));
            // Calls a method
            potion.sendAddPacket(this);
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Removes effect from entity, if it has it.
     *
     * @param effect The effect to remove
     */
    // Start of a method/block
    public void removeEffect(PotionEffect effect) {
        // Access to the current/parent object
        this.effects.removeIf(timedPotion -> {
            // Branch: checks a condition
            if (timedPotion.potion().effect() == effect) {
                // Calls a method
                timedPotion.potion().sendRemovePacket(this);
                // Calls a method
                EventDispatcher.call(new EntityPotionRemoveEvent(this, timedPotion.potion()));
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
            // Returns a value to the caller
            return false;
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * If the entity has the specified effect.
     *
     * @param effect the effect to check
     */
    // Start of a method/block
    public boolean hasEffect(PotionEffect effect) {
        // Returns a value to the caller
        return this.effects.stream().anyMatch(timedPotion -> timedPotion.potion().effect() == effect);
    // End of a block/expression
    }

    /**
     * Gets the TimedPotion of the specified effect.
     *
     * @param effect the effect type
     * @return the effect, null if not found
     */
    // Start of a method/block
    public @Nullable TimedPotion getEffect(PotionEffect effect) {
        // Returns a value to the caller
        return this.effects.stream().filter(timedPotion -> timedPotion.potion().effect() == effect).findFirst().orElse(null);
    // End of a block/expression
    }

    /**
     * Gets the level of the specified effect.
     *
     * @param effect the effect type
     * @return the effect level, -1 if not found
     */
    // Start of a method/block
    public int getEffectLevel(PotionEffect effect) {
        // Calls a method
        TimedPotion timedPotion = getEffect(effect);
        // Returns a value to the caller
        return timedPotion == null ? -1 : timedPotion.potion().amplifier();
    // End of a block/expression
    }

    /**
     * Removes all the effects currently applied to the entity.
     */
    // Start of a method/block
    public void clearEffects() {
        // Loop: repeats a block
        for (TimedPotion timedPotion : effects) {
            // Calls a method
            timedPotion.potion().sendRemovePacket(this);
            // Calls a method
            EventDispatcher.call(new EntityPotionRemoveEvent(this, timedPotion.potion()));
        // End of a block/expression
        }
        // Access to the current/parent object
        this.effects.clear();
    // End of a block/expression
    }

    /**
     * Removes the entity from the server immediately.
     * <p>
     * WARNING: this does not trigger {@link EntityDeathEvent}.
     */
    // Start of a method/block
    public void remove() {
        // Calls a method
        remove(true);
    // End of a block/expression
    }

    // Start of a method/block
    protected void remove(boolean permanent) {
        // Branch: checks a condition
        if (isRemoved()) return;
        // Calls a method
        EventDispatcher.call(new EntityDespawnEvent(this));
        // Exception handling
        try {
            // Calls a method
            despawn();
        // Start of a method/block
        } catch (Throwable t) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(t);
        // End of a block/expression
        }

        // Remove passengers if any (also done with LivingEntity#kill)
        // Calls a method
        Set<Entity> passengers = getPassengers();
        // Branch: checks a condition
        if (!passengers.isEmpty()) passengers.forEach(this::removePassenger);
        // Assigns a value
        final Entity vehicle = this.vehicle;
        // Branch: checks a condition
        if (vehicle != null) vehicle.removePassenger(this);

        // Calls a method
        Set<Entity> leashedEntities = getLeashedEntities();
        // Calls a method
        leashedEntities.forEach(entity -> entity.setLeashHolder(null));

        // Calls a method
        MinecraftServer.process().dispatcher().removeElement(this);
        // Access to the current/parent object
        this.removed = true;
        // Branch: checks a condition
        if (!permanent) {
            // Reset some state to be ready for re-use
            // Calls a method
            setPositionInternal(Pos.ZERO, 0);
            // Access to the current/parent object
            this.previousPosition = Pos.ZERO;
            // Access to the current/parent object
            this.lastSyncedPosition = Pos.ZERO;
        // End of a block/expression
        }
        // Assigns a value
        Instance currentInstance = this.instance;
        // Branch: checks a condition
        if (currentInstance != null) {
            // Calls a method
            removeFromInstance(currentInstance);
            // Access to the current/parent object
            this.instance = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets if this entity has been removed.
     *
     * @return true if this entity is removed
     */
    // Start of a method/block
    public boolean isRemoved() {
        // Returns a value to the caller
        return removed;
    // End of a block/expression
    }

    /**
     * Triggers {@link #remove()} after the specified time.
     *
     * @param delay        the time before removing the entity,
     *                     0 to cancel the removing
     * @param temporalUnit the unit of the delay
     */
    // Start of a method/block
    public void scheduleRemove(long delay, TemporalUnit temporalUnit) {
        // Branch: checks a condition
        if (temporalUnit == TimeUnit.SERVER_TICK) {
            // Calls a method
            scheduleRemove(TaskSchedule.tick((int) delay));
        // Alternative branch of the condition
        } else {
            // Calls a method
            scheduleRemove(Duration.of(delay, temporalUnit));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Triggers {@link #remove()} after the specified time.
     *
     * @param delay the time before removing the entity
     */
    // Start of a method/block
    public void scheduleRemove(Duration delay) {
        // Calls a method
        scheduleRemove(TaskSchedule.duration(delay));
    // End of a block/expression
    }

    // Start of a method/block
    private void scheduleRemove(TaskSchedule schedule) {
        // Access to the current/parent object
        this.scheduler.buildTask(this::remove).delay(schedule).schedule();
    // End of a block/expression
    }

    // Start of a method/block
    protected Vec getVelocityForPacket() {
        // Returns a value to the caller
        return this.velocity.div(ServerFlag.SERVER_TICKS_PER_SECOND);
    // End of a block/expression
    }

    // Start of a method/block
    protected SpawnEntityPacket getSpawnPacket() {
        // Assigns a value
        int data = 0;
        // Assigns a value
        Vec velocity = Vec.ZERO;
        // Branch: checks a condition
        if (getEntityMeta() instanceof ObjectDataProvider objectDataProvider) {
            // Calls a method
            data = objectDataProvider.getObjectData();
            // Branch: checks a condition
            if (objectDataProvider.requiresVelocityPacketAtSpawn()) {
                // Calls a method
                velocity = getVelocityForPacket();
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        final Pos position = getPosition();
        // Returns a value to the caller
        return new SpawnEntityPacket(getEntityId(), getUuid(), getEntityType(),
                // Calls a method
                position, position.yaw(), data, velocity);
    // End of a block/expression
    }

    // Start of a method/block
    protected EntityVelocityPacket getVelocityPacket() {
        // Returns a value to the caller
        return new EntityVelocityPacket(getEntityId(), getVelocityForPacket());
    // End of a block/expression
    }

    /**
     * Gets an {@link EntityMetaDataPacket} sent when adding viewers. Used for synchronization.
     *
     * @return The {@link EntityMetaDataPacket} related to this entity
     */
    // Start of a method/block
    public EntityMetaDataPacket getMetadataPacket() {
        // Returns a value to the caller
        return new EntityMetaDataPacket(getEntityId(), metadata.getEntries());
    // End of a block/expression
    }

    // Currently file-private so it can be used in MetadataHolder, planned to be private.
    // Start of a method/block
    void notifyMetadataChanges(Map<Integer, Metadata.Entry<?>> changes) {
        // Branch: checks a condition
        if (!isActive()) return;
        // Calls a method
        sendPacketToViewersAndSelf(new EntityMetaDataPacket(getEntityId(), changes));
    // End of a block/expression
    }

    /**
     * Used to synchronize entity position with viewers by sending a full
     * {@link EntityPositionSyncPacket} to viewers.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected void synchronizePosition() {
        // Assigns a value
        final Pos posCache = this.position;
        // Calls a method
        final Pos delta = posCache.sub(lastSyncedPosition);
        // Calls a method
        PacketViewableUtils.prepareViewablePacket(currentChunk, new EntityPositionSyncPacket(getEntityId(), posCache, delta, posCache.yaw(), posCache.pitch(), isOnGround()), this);
        // Assigns a value
        nextSynchronizationTick = ticks + synchronizationTicks;
        // Access to the current/parent object
        this.lastSyncedPosition = posCache;
    // End of a block/expression
    }

    /**
     * Used to synchronize the head position and rotation of the entity
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    protected void synchronizeView() {
        // Assigns a value
        final Pos position = this.position;
        // Calls a method
        sendPacketToViewers(new EntityHeadLookPacket(getEntityId(), headRotation));
        // Calls a method
        sendPacketToViewers(new EntityRotationPacket(getEntityId(), position.yaw(), position.pitch(), onGround));
    // End of a block/expression
    }

    /**
     * Asks for a position synchronization to happen during next entity tick.
     */
    // Start of a method/block
    public void synchronizeNextTick() {
        // Access to the current/parent object
        this.nextSynchronizationTick = 0;
    // End of a block/expression
    }

    /**
     * Returns the current synchronization interval. The default value is {@link ServerFlag#ENTITY_SYNCHRONIZATION_TICKS}
     * but can be overridden per entity with {@link #setSynchronizationTicks(long)}.
     *
     * @return The current synchronization ticks
     */
    // Start of a method/block
    public long getSynchronizationTicks() {
        // Returns a value to the caller
        return this.synchronizationTicks;
    // End of a block/expression
    }

    /**
     * Set the tick period until this entity's position is synchronized.
     *
     * @param ticks the new synchronization tick period
     */
    // Start of a method/block
    public void setSynchronizationTicks(long ticks) {
        // Access to the current/parent object
        this.synchronizationTicks = ticks;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public HoverEvent<ShowEntity> asHoverEvent(UnaryOperator<ShowEntity> op) {
        // Returns a value to the caller
        return HoverEvent.showEntity(ShowEntity.showEntity(this.entityType, this.uuid));
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
    // Start of a method/block
    public EntitySnapshot updateSnapshot(SnapshotUpdater updater) {
        // Assigns a value
        final Chunk chunk = currentChunk;
        // Calls a method
        final int[] viewersId = this.viewEngine.viewableOption.bitSet.toIntArray();
        // Calls a method
        final int[] passengersId = ArrayUtils.mapToIntArray(passengers, Entity::getEntityId);
        // Assigns a value
        final Entity vehicle = this.vehicle;
        // Returns a value to the caller
        return new SnapshotImpl.Entity(entityType, uuid, id, position, velocity,
                // Code statement
                updater.reference(instance), chunk.getChunkX(), chunk.getChunkZ(),
                // Code statement
                viewersId, passengersId, vehicle == null ? -1 : vehicle.getEntityId(),
                // Calls a method
                tagHandler.readableCopy());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<EntityEvent> eventNode() {
        // Returns a value to the caller
        return eventNode;
    // End of a block/expression
    }

    /**
     * Applies knockback to the entity
     *
     * @param strength the strength of the knockback, 0.4 is the vanilla value for a bare hand hit
     * @param x        knockback on x axle, for default knockback use the following formula <pre>sin(attacker.yaw * (pi/180))</pre>
     * @param z        knockback on z axle, for default knockback use the following formula <pre>-cos(attacker.yaw * (pi/180))</pre>
     */
    // Start of a method/block
    public void takeKnockback(float strength, final double x, final double z) {
        // Branch: checks a condition
        if (strength > 0) {
            //TODO check possible side effects of unnatural TPS (other than 20TPS)
            // Code statement
            strength *= ServerFlag.SERVER_TICKS_PER_SECOND;
            // Calls a method
            final Vec velocityModifier = new Vec(x, z).normalize().mul(strength);
            // Assigns a value
            final double verticalLimit = .4d * ServerFlag.SERVER_TICKS_PER_SECOND;

            // Code statement
            setVelocity(new Vec(velocity.x() / 2d - velocityModifier.x(),
                    // Code statement
                    onGround ? Math.min(verticalLimit, velocity.y() / 2d + strength) : velocity.y(),
                    // Code statement
                    velocity.z() / 2d - velocityModifier.z()
            // Code statement
            ));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the line of sight of the entity.
     *
     * @param maxDistance The max distance to scan
     * @return A list of {@link Point points} in this entities line of sight
     */
    // Start of a method/block
    public List<Point> getLineOfSight(int maxDistance) {
        // Calls a method
        Instance instance = getInstance();
        // Branch: checks a condition
        if (instance == null) {
            // Returns a value to the caller
            return List.of();
        // End of a block/expression
        }

        // Calls a method
        List<Point> blocks = new ArrayList<>();
        // Calls a method
        var it = new BlockIterator(this, maxDistance);
        // Loop: repeats a block
        while (it.hasNext()) {
            // Calls a method
            final Point position = it.next();
            // Branch: checks a condition
            if (!instance.getBlock(position).isAir()) blocks.add(position);
        // End of a block/expression
        }
        // Returns a value to the caller
        return blocks;
    // End of a block/expression
    }

    /**
     * Raycasts current entity's eye position to target eye position.
     *
     * @param entity    the entity to be checked.
     * @param exactView if set to TRUE, checks whether target is IN the line of sight of the current one;
     *                  otherwise checks if the current entity can rotate so that target will be in its line of sight.
     * @return true if the ray reaches the target bounding box before hitting a block.
     */
    // Start of a method/block
    public boolean hasLineOfSight(Entity entity, boolean exactView) {
        // Calls a method
        Instance instance = getInstance();
        // Branch: checks a condition
        if (instance == null) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Calls a method
        final Pos start = position.withY(position.y() + getEyeHeight());
        // Calls a method
        final Pos end = entity.position.withY(entity.position.y() + entity.getEyeHeight());
        // Calls a method
        final Vec direction = exactView ? position.direction() : end.sub(start).asVec().normalize();
        // Branch: checks a condition
        if (!entity.boundingBox.boundingBoxRayIntersectionCheck(start.asVec(), direction, entity.position)) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return CollisionUtils.isLineOfSightReachingShape(instance, currentChunk, start, end, entity.boundingBox, entity.position);
    // End of a block/expression
    }

    /**
     * @param entity the entity to be checked.
     * @return if the current entity has line of sight to the given one.
     * @see Entity#hasLineOfSight(Entity, boolean)
     */
    // Start of a method/block
    public boolean hasLineOfSight(Entity entity) {
        // Returns a value to the caller
        return hasLineOfSight(entity, false);
    // End of a block/expression
    }

    /**
     * Gets first entity on the line of sight of the current one that matches the given predicate.
     *
     * @param range     max length of the line of sight of the current entity to be checked.
     * @param predicate optional predicate
     * @return resulting entity whether there're any, null otherwise.
     */
    // Start of a method/block
    public @Nullable Entity getLineOfSightEntity(double range, Predicate<? super Entity> predicate) {
        // Calls a method
        Instance instance = getInstance();
        // Branch: checks a condition
        if (instance == null) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Calls a method
        final Pos start = position.withY(position.y() + getEyeHeight());
        // Calls a method
        final Vec startAsVec = start.asVec();
        // Assigns a value
        final Predicate<Entity> finalPredicate = e -> e != this
                // Code statement
                && e.boundingBox.boundingBoxRayIntersectionCheck(startAsVec, position.direction(), e.position)
                // Code statement
                && predicate.test(e)
                // Code statement
                && CollisionUtils.isLineOfSightReachingShape(instance, currentChunk, start,
                // Calls a method
                e.position.withY(e.position.y() + e.getEyeHeight()), e.boundingBox, e.position);

        // Assigns a value
        Optional<Entity> nearby = instance.getNearbyEntities(position, range).stream()
                // Code statement
                .filter(finalPredicate)
                // Calls a method
                .min(Comparator.comparingDouble(e -> e.getDistanceSquared(this)));

        // Returns a value to the caller
        return nearby.orElse(null);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isOccluded(Shape shape, BlockFace face) {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean intersectBox(Point positionRelative, BoundingBox boundingBox) {
        // Returns a value to the caller
        return this.boundingBox.intersectBox(positionRelative, boundingBox);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean intersectBoxSwept(Point rayStart, Point rayDirection, Point shapePos, BoundingBox moving, SweepResult finalResult) {
        // Returns a value to the caller
        return boundingBox.intersectBoxSwept(rayStart, rayDirection, shapePos, moving, finalResult);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Point relativeStart() {
        // Returns a value to the caller
        return boundingBox.relativeStart();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Point relativeEnd() {
        // Returns a value to the caller
        return boundingBox.relativeEnd();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasEntityCollision() {
        // Returns a value to the caller
        return collidesWithEntities;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean preventBlockPlacement() {
        // EntityMeta can change at any time, so initializing this during #initCollisions is not an option
        // Can be overridden to allow for custom behaviour
        // Branch: checks a condition
        if (entityMeta instanceof ArmorStandMeta armorStandMeta && armorStandMeta.isMarker()) return false;
        // Returns a value to the caller
        return preventBlockPlacement;
    // End of a block/expression
    }

    // Start of a method/block
    protected void updateCollisions() {
        // Calls a method
        preventBlockPlacement = !ALLOW_BLOCK_PLACEMENT_ENTITIES.contains(entityType);
        // Calls a method
        collidesWithEntities = !NO_ENTITY_COLLISION_ENTITIES.contains(entityType);
    // End of a block/expression
    }

    /**
     * Acquires this entity.
     *
     * @param <T> the type of object to be acquired
     * @return the acquirable for this entity
     * @deprecated It's preferred to use {@link AcquirableSource#acquirable()} instead, as it is overridden by
     * subclasses
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public <T extends Entity> Acquirable<T> getAcquirable() {
        // Returns a value to the caller
        return (Acquirable<T>) acquirable;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends Entity> acquirable() {
        // Returns a value to the caller
        return acquirable;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Identity identity() {
        // Returns a value to the caller
        return Identity.identity(this.uuid); // Unfortunate pollution, if we extended Identity (contains UUID static)
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pointers pointers() {
        // Returns a value to the caller
        return ENTITY_POINTERS_SUPPLIER.view(this);
    // End of a block/expression
    }
// End of a block/expression
}
