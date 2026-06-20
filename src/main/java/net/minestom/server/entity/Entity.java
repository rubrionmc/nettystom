// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identified;
// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointered;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointers;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.PointersSupplier;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent.ShowEntity;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEventSource;
// Import d'une classe nécessaire
import net.minestom.server.*;
// Import d'une classe nécessaire
import net.minestom.server.collision.*;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.EventHandler;
// Import d'une classe nécessaire
import net.minestom.server.event.EventNode;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.AddEntityToInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.RemoveEntityFromInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.EntityTracker;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.item.component.CustomData;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.potion.Potion;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.potion.TimedPotion;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.EntitySnapshot;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.SnapshotImpl;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.SnapshotUpdater;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.Snapshotable;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Taggable;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import net.minestom.server.thread.AcquirableSource;
// Import d'une classe nécessaire
import net.minestom.server.timer.Schedulable;
// Import d'une classe nécessaire
import net.minestom.server.timer.Scheduler;
// Import d'une classe nécessaire
import net.minestom.server.timer.TaskSchedule;
// Import d'une classe nécessaire
import net.minestom.server.utils.ArrayUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketViewableUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.async.AsyncUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockIterator;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkCache;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.position.PositionUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.MagicConstant;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Could be a player, a monster, or an object.
 * <p>
 * To create your own entity you probably want to extend {@link LivingEntity} or {@link EntityCreature} instead.
 */
// Déclaration de type (classe/interface/enum/record)
public class Entity implements Viewable, Tickable, Schedulable, Snapshotable, EventHandler<EntityEvent>, Taggable,
        // Début d'une méthode/d'un bloc
        HoverEventSource<ShowEntity>, Sound.Emitter, Shape, AcquirableSource<Entity>, DataComponent.Holder, Pointered, Identified {
    // This is somewhat arbitrary, but we don't want to hit the max int ever because it is very easy to
    // overflow while working with a position at the max int (for example, looping over a bounding box)
    // Affecte une valeur
    static final int MAX_COORDINATE = 2_000_000_000;

    // Appelle une méthode
    private static final AtomicInteger LAST_ENTITY_ID = new AtomicInteger();

    // Protected due to PointersSupplier.Builder#parent
    // Affecte une valeur
    protected static PointersSupplier<Entity> ENTITY_POINTERS_SUPPLIER = PointersSupplier.<Entity>builder()
            // Instruction de code
            .resolving(Identity.DISPLAY_NAME, (entity) -> entity.get(DataComponents.CUSTOM_NAME))
            // Instruction de code
            .resolving(Identity.UUID, Entity::getUuid)
            // Appelle une méthode
            .build();

    // Certain entities should only have their position packets sent during synchronization
    // Affecte une valeur
    private static final Set<EntityType> SYNCHRONIZE_ONLY_ENTITIES = Set.of(EntityType.ITEM, EntityType.FALLING_BLOCK,
            // Instruction de code
            EntityType.ARROW, EntityType.SPECTRAL_ARROW, EntityType.TRIDENT, EntityType.LLAMA_SPIT, EntityType.WIND_CHARGE,
            // Instruction de code
            EntityType.FISHING_BOBBER, EntityType.SNOWBALL, EntityType.EGG, EntityType.ENDER_PEARL, EntityType.SPLASH_POTION,
            // Instruction de code
            EntityType.LINGERING_POTION, EntityType.EYE_OF_ENDER, EntityType.DRAGON_FIREBALL, EntityType.FIREBALL,
            // Instruction de code
            EntityType.SMALL_FIREBALL, EntityType.TNT);
    // Affecte une valeur
    private static final Set<EntityType> ALLOW_BLOCK_PLACEMENT_ENTITIES = Set.of(EntityType.ARROW, EntityType.ITEM,
            // Instruction de code
            EntityType.SNOWBALL, EntityType.EXPERIENCE_BOTTLE, EntityType.EXPERIENCE_ORB, EntityType.SPLASH_POTION,
            // Instruction de code
            EntityType.LINGERING_POTION, EntityType.AREA_EFFECT_CLOUD);
    // Affecte une valeur
    private static final Set<EntityType> NO_ENTITY_COLLISION_ENTITIES = Set.of(EntityType.TEXT_DISPLAY, EntityType.ITEM_DISPLAY,
            // Instruction de code
            EntityType.BLOCK_DISPLAY);
    // Appelle une méthode
    private final CachedPacket destroyPacketCache = new CachedPacket(() -> new DestroyEntitiesPacket(getEntityId()));

    // Instruction de code
    protected Instance instance;
    // Instruction de code
    protected Chunk currentChunk;
    // Instruction de code
    protected Pos position; // Should be updated by setPositionInternal only.
    // Instruction de code
    protected float headRotation;
    // Instruction de code
    protected Pos previousPosition;
    // Instruction de code
    protected Pos lastSyncedPosition;
    // Instruction de code
    protected boolean onGround;

    // Instruction de code
    protected BoundingBox boundingBox;
    // Affecte une valeur
    private @Nullable PhysicsResult previousPhysicsResult = null;

    // Instruction de code
    protected @Nullable Entity vehicle;

    // Velocity
    // Affecte une valeur
    protected Vec velocity = Vec.ZERO; // Movement in block per second
    // Affecte une valeur
    protected boolean hasPhysics = true;
    // Affecte une valeur
    protected boolean collidesWithEntities = true;
    // Affecte une valeur
    protected boolean preventBlockPlacement = true;

    // Instruction de code
    private Aerodynamics aerodynamics;
    // Instruction de code
    protected int gravityTickCount; // Number of tick where gravity tick was applied

    // Instruction de code
    private final int id;
    // Players must be aware of all surrounding entities
    // General entities should only be aware of surrounding players to update their viewing list
    // Affecte une valeur
    private final EntityTracker.Target<Entity> trackingTarget = this instanceof Player ?
            // Appelle une méthode
            EntityTracker.Target.ENTITIES : EntityTracker.Target.class.cast(EntityTracker.Target.PLAYERS);
    // Affecte une valeur
    protected final EntityTracker.Update<Entity> trackingUpdate = new EntityTracker.Update<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void add(Entity entity) {
            // Appelle une méthode
            viewEngine.handleAutoViewAddition(entity);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void remove(Entity entity) {
            // Appelle une méthode
            viewEngine.handleAutoViewRemoval(entity);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void referenceUpdate(Point point, @Nullable EntityTracker tracker) {
            // Affecte une valeur
            final Instance currentInstance = tracker != null ? instance : null;
            // Instruction de code
            assert currentInstance == null || currentInstance.getEntityTracker() == tracker :
                    // Instruction de code
                    "EntityTracker does not match current instance";
            // Appelle une méthode
            viewEngine.updateTracker(currentInstance, point);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Appelle une méthode
    protected final EntityView viewEngine = new EntityView(this);
    // Affecte une valeur
    protected final Set<Player> viewers = viewEngine.set;
    // Appelle une méthode
    private final TagHandler tagHandler = TagHandler.newHandler();
    // Appelle une méthode
    private final Scheduler scheduler = Scheduler.newScheduler();
    // Instruction de code
    private final EventNode<EntityEvent> eventNode;

    // Instruction de code
    private final UUID uuid;
    // Instruction de code
    private boolean isActive; // False if entity has only been instanced without being added somewhere
    // Instruction de code
    protected boolean removed;

    // Appelle une méthode
    private final Set<Entity> passengers = new CopyOnWriteArraySet<>();

    // Appelle une méthode
    private final Set<Entity> leashedEntities = new CopyOnWriteArraySet<>();
    // Instruction de code
    private Entity leashHolder;

    // Instruction de code
    protected EntityType entityType; // UNSAFE to change, modify at your own risk

    // Network synchronization, send the absolute position of the entity every n ticks
    // Affecte une valeur
    private long synchronizationTicks = ServerFlag.ENTITY_SYNCHRONIZATION_TICKS;
    // Affecte une valeur
    private long nextSynchronizationTick = synchronizationTicks;

    // Appelle une méthode
    protected MetadataHolder metadata = new MetadataHolder(this::notifyMetadataChanges);
    // Instruction de code
    protected EntityMeta entityMeta;

    // Appelle une méthode
    private final List<TimedPotion> effects = new CopyOnWriteArrayList<>();

    // Tick related
    // Instruction de code
    private long ticks;

    // Appelle une méthode
    private final Acquirable<Entity> acquirable = Acquirable.unassigned(this);

    // Début d'une méthode/d'un bloc
    public Entity(EntityType entityType, UUID uuid) {
        // Accès à l'objet courant/parent
        this.id = generateId();
        // Accès à l'objet courant/parent
        this.entityType = entityType;
        // Accès à l'objet courant/parent
        this.uuid = uuid;
        // Accès à l'objet courant/parent
        this.position = Pos.ZERO;
        // Accès à l'objet courant/parent
        this.headRotation = 0;
        // Accès à l'objet courant/parent
        this.previousPosition = Pos.ZERO;
        // Accès à l'objet courant/parent
        this.lastSyncedPosition = Pos.ZERO;

        // Accès à l'objet courant/parent
        this.entityMeta = MetadataHolder.createMeta(entityType, this, this.metadata);

        // Appelle une méthode
        final RegistryData.EntityEntry registry = entityType.registry();
        // Appelle une méthode
        setBoundingBox(entityType.registry().boundingBox());

        // Accès à l'objet courant/parent
        this.aerodynamics = new Aerodynamics(
                // Instruction de code
                registry.acceleration(),
                // Instruction de code
                registry.horizontalAirResistance(),
                // Appelle une méthode
                registry.verticalAirResistance());

        // Appelle une méthode
        final ServerProcess process = MinecraftServer.process();
        // Embranchement : vérifie une condition
        if (process != null) {
            // Accès à l'objet courant/parent
            this.eventNode = process.eventHandler().map(this, EventFilter.ENTITY);
        // Branche alternative de la condition
        } else {
            // Local nodes require a server process
            // Accès à l'objet courant/parent
            this.eventNode = null;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        updateCollisions();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Entity(EntityType entityType) {
        // Appelle une méthode
        this(entityType, UUID.randomUUID());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void setPositionInternal(Pos newPosition, float headRotation) {
        // Embranchement : vérifie une condition
        if (newPosition.x() >= MAX_COORDINATE || newPosition.x() <= -MAX_COORDINATE ||
                // Instruction de code
                newPosition.y() >= MAX_COORDINATE || newPosition.y() <= -MAX_COORDINATE ||
                // Début d'une méthode/d'un bloc
                newPosition.z() >= MAX_COORDINATE || newPosition.z() <= -MAX_COORDINATE) {
            // Affecte une valeur
            newPosition = newPosition.withCoord(
                    // Instruction de code
                    MathUtils.clamp(newPosition.x(), -MAX_COORDINATE, MAX_COORDINATE),
                    // Instruction de code
                    MathUtils.clamp(newPosition.y(), -MAX_COORDINATE, MAX_COORDINATE),
                    // Instruction de code
                    MathUtils.clamp(newPosition.z(), -MAX_COORDINATE, MAX_COORDINATE)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.position = newPosition;
        // Accès à l'objet courant/parent
        this.headRotation = headRotation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Schedules a task to be run during the next entity tick.
     *
     * @param callback the task to execute during the next entity tick
     */
    // Début d'une méthode/d'un bloc
    public void scheduleNextTick(Consumer<? super Entity> callback) {
        // Accès à l'objet courant/parent
        this.scheduler.scheduleNextTick(() -> callback.accept(this));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Generate and return a new unique entity id.
     * <p>
     * Useful if you want to spawn entities using packet but don't risk to have duplicated id.
     *
     * @return a newly generated entity id
     */
    // Début d'une méthode/d'un bloc
    public static int generateId() {
        // Renvoie une valeur à l'appelant
        return LAST_ENTITY_ID.incrementAndGet();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called each tick.
     *
     * @param time time of the update in milliseconds. This may only be used as a delta and has no meaning in the real world
     */
    // Début d'une méthode/d'un bloc
    public void update(long time) {

    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a new instance is set.
     */
    // Début d'une méthode/d'un bloc
    public void spawn() {

    // Fin d'un bloc/d'une expression
    }

    /**
     * Called right before an entity is removed
     */
    // Début d'une méthode/d'un bloc
    protected void despawn() {

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnGround() {
        // Renvoie une valeur à l'appelant
        return onGround;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets metadata of this entity.
     * You may want to cast it to specific implementation.
     *
     * @return metadata of this entity.
     */
    // Début d'une méthode/d'un bloc
    public EntityMeta getEntityMeta() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CUSTOM_DATA)
            // Renvoie une valeur à l'appelant
            return (T) new CustomData(tagHandler.asCompound());
        // Renvoie une valeur à l'appelant
        return EntityMeta.getComponent(getEntityMeta(), component);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CUSTOM_DATA) {
            // Appelle une méthode
            tagHandler.updateContent(((CustomData) value).nbt());
        // Branche alternative de la condition
        } else EntityMeta.setComponent(getEntityMeta(), component, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Do a batch edit of this entity's metadata.
     */
    // Début d'une méthode/d'un bloc
    public <TMeta extends EntityMeta> void editEntityMeta(Class<TMeta> metaClass, Consumer<TMeta> editor) {
        // Appelle une méthode
        entityMeta.setNotifyAboutChanges(false);
        // Gestion des exceptions
        try {
            // Appelle une méthode
            TMeta casted = metaClass.cast(entityMeta);
            // Appelle une méthode
            editor.accept(casted);
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Lève une exception
            throw new RuntimeException("Error editing entity " + id + " " + entityType.name() + " meta", t);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            entityMeta.setNotifyAboutChanges(true);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> teleport(Pos position) {
        // Renvoie une valeur à l'appelant
        return teleport(position, null, RelativeFlags.NONE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> teleport(Pos position, Vec velocity) {
        // Renvoie une valeur à l'appelant
        return teleport(position, velocity, null, RelativeFlags.NONE);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public CompletableFuture<Void> teleport(Pos position, long @Nullable [] chunks,
                                            // Annotation pour l'élément suivant
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Renvoie une valeur à l'appelant
        return teleport(position, chunks, flags, true);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public CompletableFuture<Void> teleport(Pos position, Vec velocity, long @Nullable [] chunks,
                                            // Annotation pour l'élément suivant
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Renvoie une valeur à l'appelant
        return teleport(position, velocity, chunks, flags, true);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public CompletableFuture<Void> teleport(Pos position, long @Nullable [] chunks,
                                            // Annotation pour l'élément suivant
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags,
                                            // Début d'une méthode/d'un bloc
                                            boolean shouldConfirm) {
        // Use delta coord if not providing a delta velocity (to avoid resetting velocity)
        // Renvoie une valeur à l'appelant
        return teleport(position, Vec.ZERO, chunks, flags | RelativeFlags.DELTA_COORD, shouldConfirm);
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    public CompletableFuture<Void> teleport(Pos position, Vec velocity, long @Nullable [] chunks,
                                            // Annotation pour l'élément suivant
                                            @MagicConstant(flagsFromClass = RelativeFlags.class) int flags,
                                            // Début d'une méthode/d'un bloc
                                            boolean shouldConfirm) {
        // Appelle une méthode
        Check.stateCondition(instance == null, "You need to use Entity#setInstance before teleporting an entity!");

        // Appelle une méthode
        EntityTeleportEvent event = new EntityTeleportEvent(this, position, flags);
        // Appelle une méthode
        EventDispatcher.call(event);

        // Appelle une méthode
        final Pos globalPosition = PositionUtils.getPositionWithRelativeFlags(this.position, position, flags);
        // Appelle une méthode
        final Vec globalVelocity = PositionUtils.getVelocityWithRelativeFlags(this.velocity, velocity, flags);

        // Affecte une valeur
        final Runnable endCallback = () -> {
            // Accès à l'objet courant/parent
            this.previousPosition = this.position;
            // Appelle une méthode
            setPositionInternal(globalPosition, globalPosition.yaw());
            // Accès à l'objet courant/parent
            this.velocity = globalVelocity;
            // Appelle une méthode
            refreshCoordinate(globalPosition);
            // Embranchement : vérifie une condition
            if (this instanceof Player player)
                // Appelle une méthode
                player.synchronizePositionAfterTeleport(position, velocity, flags, shouldConfirm);
            // Branche alternative de la condition
            else synchronizePosition();
        // Fin d'un bloc/d'une expression
        };

        // Embranchement : vérifie une condition
        if (chunks != null && chunks.length > 0) {
            // Chunks need to be loaded before the teleportation can happen
            // Renvoie une valeur à l'appelant
            return ChunkUtils.optionalLoadAll(instance, chunks, null).thenRun(endCallback);
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final Pos currentPosition = this.position;
        // Embranchement : vérifie une condition
        if (!currentPosition.sameChunk(globalPosition)) {
            // Ensure that the chunk is loaded
            // Renvoie une valeur à l'appelant
            return instance.loadOptionalChunk(globalPosition).thenRun(endCallback);
        // Branche alternative de la condition
        } else {
            // Position is in the same chunk, keep it sync
            // Appelle une méthode
            endCallback.run();
            // Renvoie une valeur à l'appelant
            return AsyncUtils.empty();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the view of the entity.
     * The head rotation will be updated to the yaw value.
     *
     * @param yaw   the new yaw
     * @param pitch the new pitch
     */
    // Début d'une méthode/d'un bloc
    public void setView(float yaw, float pitch) {
        // Appelle une méthode
        setView(yaw, pitch, yaw);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public void setView(float yaw, float pitch, float headRotation) {
        // Appelle une méthode
        headRotation = Pos.fixYaw(headRotation);
        // Affecte une valeur
        final Pos currentPosition = this.position;
        // Embranchement : vérifie une condition
        if (currentPosition.sameView(yaw, pitch) && this.headRotation == headRotation) return;
        // Appelle une méthode
        setPositionInternal(currentPosition.withView(yaw, pitch), headRotation);
        // Appelle une méthode
        synchronizeView();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the view of the entity so that it looks in a direction to the given position if
     * it is different from the entity's current position.
     *
     * @param point the point to look at.
     */
    // Début d'une méthode/d'un bloc
    public void lookAt(Point point) {
        // Appelle une méthode
        final Pos newPosition = this.position.add(0, getEyeHeight(), 0).withLookAt(point);
        // Appelle une méthode
        setView(newPosition.yaw(), newPosition.pitch());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the view of the entity so that it looks in a direction to the given entity.
     *
     * @param entity the entity to look at.
     * @throws IllegalArgumentException if the entities are not in the same instance
     */
    // Début d'une méthode/d'un bloc
    public void lookAt(Entity entity) {
        // Appelle une méthode
        Check.argCondition(entity.instance != instance, "Entity cannot look at an entity in another instance");
        // Appelle une méthode
        lookAt(entity.position.withY(entity.position.y() + entity.getEyeHeight()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this entity is automatically sent to surrounding players.
     * True by default.
     *
     * @return true if the entity is automatically viewable for close players, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isAutoViewable() {
        // Renvoie une valeur à l'appelant
        return viewEngine.viewableOption.isAuto();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Decides if this entity should be auto-viewable by nearby players.
     *
     * @param autoViewable true to add surrounding players, false to remove
     * @see #isAutoViewable()
     */
    // Début d'une méthode/d'un bloc
    public void setAutoViewable(boolean autoViewable) {
        // Accès à l'objet courant/parent
        this.viewEngine.viewableOption.updateAuto(autoViewable);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateViewableRule(@Nullable Predicate<? super Player> predicate) {
        // Accès à l'objet courant/parent
        this.viewEngine.viewableOption.updateRule(predicate);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateViewableRule() {
        // Accès à l'objet courant/parent
        this.viewEngine.viewableOption.updateRule();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if surrounding entities are automatically visible by this.
     * True by default.
     *
     * @return true if surrounding entities are visible by this
     */
    // Début d'une méthode/d'un bloc
    public boolean autoViewEntities() {
        // Renvoie une valeur à l'appelant
        return viewEngine.viewerOption.isAuto();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Decides if surrounding entities must be visible.
     *
     * @param autoViewer true to add view surrounding entities, false to remove
     */
    // Début d'une méthode/d'un bloc
    public void setAutoViewEntities(boolean autoViewer) {
        // Accès à l'objet courant/parent
        this.viewEngine.viewerOption.updateAuto(autoViewer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateViewerRule(@Nullable Predicate<? super Entity> predicate) {
        // Accès à l'objet courant/parent
        this.viewEngine.viewerOption.updateRule(predicate);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateViewerRule() {
        // Accès à l'objet courant/parent
        this.viewEngine.viewerOption.updateRule();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public final boolean addViewer(Player player) {
        // Appelle une méthode
        Check.stateCondition(!isActive(), "Entities must be in an instance before adding viewers");
        // Embranchement : vérifie une condition
        if (!viewEngine.manualAdd(player)) return false;
        // Appelle une méthode
        updateNewViewer(player);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public final boolean removeViewer(Player player) {
        // Embranchement : vérifie une condition
        if (!viewEngine.manualRemove(player)) return false;
        // Appelle une méthode
        updateOldViewer(player);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a new viewer must be shown.
     * Method can be subject to deadlocking if the target's viewers are also accessed.
     *
     * @param player the player to send the packets to
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void updateNewViewer(Player player) {
        // Appelle une méthode
        player.sendPacket(getSpawnPacket());
        // Embranchement : vérifie une condition
        if (hasVelocity()) player.sendPacket(getVelocityPacket());
        // Appelle une méthode
        player.sendPacket(this.getMetadataPacket());
        // Passengers are handled in EntityView

        // Leashes
        // Embranchement : vérifie une condition
        if (leashHolder != null && (player.equals(leashHolder) || leashHolder.isViewer(player))) {
            // Appelle une méthode
            player.sendPacket(getAttachEntityPacket());
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (Entity entity : leashedEntities) {
            // Embranchement : vérifie une condition
            if (entity.isViewer(player)) {
                // Appelle une méthode
                player.sendPacket(entity.getAttachEntityPacket());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Head position
        // Appelle une méthode
        player.sendPacket(new EntityHeadLookPacket(getEntityId(), headRotation));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a viewer must be destroyed.
     * Method can be subject to deadlocking if the target's viewers are also accessed.
     *
     * @param player the player to send the packets to
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void updateOldViewer(Player player) {
        // Appelle une méthode
        leashedEntities.forEach(entity -> player.sendPacket(new AttachEntityPacket(entity.getEntityId(), -1)));
        // Appelle une méthode
        player.sendPacket(destroyPacketCache);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<? extends Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return viewers;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this entity's viewers (surrounding players) can be predicted from surrounding chunks.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPredictableViewers() {
        // Renvoie une valeur à l'appelant
        return viewEngine.hasPredictableViewers();
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public synchronized void switchEntityType(EntityType entityType) {
        // Accès à l'objet courant/parent
        this.entityType = entityType;
        // Accès à l'objet courant/parent
        this.metadata = new MetadataHolder(this::notifyMetadataChanges);
        // Accès à l'objet courant/parent
        this.entityMeta = MetadataHolder.createMeta(entityType, this, this.metadata);

        // Appelle une méthode
        final RegistryData.EntityEntry registry = entityType.registry();
        // Accès à l'objet courant/parent
        this.aerodynamics = aerodynamics.withAirResistance(
                // Instruction de code
                registry.horizontalAirResistance(),
                // Appelle une méthode
                registry.verticalAirResistance());

        // Appelle une méthode
        updateCollisions();
        // Appelle une méthode
        Set<Player> viewers = new HashSet<>(getViewers());
        // Appelle une méthode
        getViewers().forEach(this::updateOldViewer);
        // Appelle une méthode
        viewers.forEach(this::updateNewViewer);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the entity, called every tick.
     * <p>
     * Ignored if {@link #getInstance()} returns null.
     *
     * @param time the update time in milliseconds. This may only be used as a delta and has no meaning in the real world.
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Embranchement : vérifie une condition
        if (instance == null || isRemoved() || !ChunkUtils.isLoaded(currentChunk))
            // Renvoie une valeur à l'appelant
            return;

        // scheduled tasks
        // Accès à l'objet courant/parent
        this.scheduler.processTick();
        // Embranchement : vérifie une condition
        if (isRemoved()) return;

        // Entity tick
        // Début d'un bloc
        {
            // handle position and velocity updates
            // Appelle une méthode
            movementTick();

            // handle block contacts
            // Appelle une méthode
            touchTick();

            // Call the abstract update method
            // Appelle une méthode
            update(time);

            // Instruction de code
            ticks++;
            // Appelle une méthode
            EventDispatcher.call(new EntityTickEvent(this));

            // remove expired effects
            // Appelle une méthode
            effectTick();
        // Fin d'un bloc/d'une expression
        }
        // Scheduled synchronization
        // Embranchement : vérifie une condition
        if (ticks >= nextSynchronizationTick) {
            // Embranchement : vérifie une condition
            if (vehicle == null) {
                // Appelle une méthode
                synchronizePosition();
                // Appelle une méthode
                sendPacketToViewers(getVelocityPacket());
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                synchronizeView();
                // Affecte une valeur
                nextSynchronizationTick = ticks + synchronizationTicks;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // End of tick scheduled tasks
        // Accès à l'objet courant/parent
        this.scheduler.processTickEnd();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected void movementTick() {
        // Accès à l'objet courant/parent
        this.gravityTickCount = onGround ? 0 : gravityTickCount + 1;
        // Embranchement : vérifie une condition
        if (vehicle != null) return;

        // Affecte une valeur
        boolean entityIsPlayer = this instanceof Player;
        // Appelle une méthode
        boolean entityFlying = entityIsPlayer && ((Player) this).isFlying();
        // Appelle une méthode
        final Block.Getter chunkCache = new ChunkCache(instance, currentChunk, Block.STONE);
        // Affecte une valeur
        PhysicsResult physicsResult = PhysicsUtils.simulateMovement(position, velocity.div(ServerFlag.SERVER_TICKS_PER_SECOND), boundingBox,
                // Appelle une méthode
                instance.getWorldBorder(), chunkCache, aerodynamics, hasNoGravity(), hasPhysics, onGround, entityFlying, previousPhysicsResult);
        // Accès à l'objet courant/parent
        this.previousPhysicsResult = physicsResult;

        // Appelle une méthode
        Chunk finalChunk = ChunkUtils.retrieve(instance, currentChunk, physicsResult.newPosition());
        // Embranchement : vérifie une condition
        if (!ChunkUtils.isLoaded(finalChunk)) return;

        // Appelle une méthode
        velocity = physicsResult.newVelocity().mul(ServerFlag.SERVER_TICKS_PER_SECOND);
        // Embranchement : vérifie une condition
        if (!(this instanceof Player)) {
            // Appelle une méthode
            onGround = physicsResult.isOnGround();
            // Appelle une méthode
            refreshPosition(physicsResult.newPosition(), true, !SYNCHRONIZE_ONLY_ENTITIES.contains(entityType));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void touchTick() {
        // Embranchement : vérifie une condition
        if (!hasPhysics) return;

        // TODO do not call every tick (it is pretty expensive)
        // Affecte une valeur
        final Pos position = this.position;
        // Affecte une valeur
        final BoundingBox boundingBox = this.boundingBox;
        // Appelle une méthode
        ChunkCache cache = new ChunkCache(instance, currentChunk);

        // Appelle une méthode
        final int minX = (int) Math.floor(boundingBox.minX() + position.x());
        // Appelle une méthode
        final int maxX = (int) Math.ceil(boundingBox.maxX() + position.x());
        // Appelle une méthode
        final int minY = (int) Math.floor(boundingBox.minY() + position.y());
        // Appelle une méthode
        final int maxY = (int) Math.ceil(boundingBox.maxY() + position.y());
        // Appelle une méthode
        final int minZ = (int) Math.floor(boundingBox.minZ() + position.z());
        // Appelle une méthode
        final int maxZ = (int) Math.ceil(boundingBox.maxZ() + position.z());

        // Boucle : répète un bloc
        for (int y = minY; y <= maxY; y++) {
            // Boucle : répète un bloc
            for (int x = minX; x <= maxX; x++) {
                // Boucle : répète un bloc
                for (int z = minZ; z <= maxZ; z++) {
                    // Appelle une méthode
                    final Block block = cache.getBlock(x, y, z, Block.Getter.Condition.CACHED);
                    // Embranchement : vérifie une condition
                    if (block == null) continue;
                    // Appelle une méthode
                    final BlockHandler handler = block.handler();
                    // Embranchement : vérifie une condition
                    if (handler != null) {
                        // Move a small amount towards the entity. If the entity is within 0.01 blocks of the block, touch will trigger
                        // Appelle une méthode
                        Vec blockPos = new Vec(x, y, z);
                        // Appelle une méthode
                        Point blockEntityVector = (blockPos.sub(position)).normalize().mul(0.01);
                        // Embranchement : vérifie une condition
                        if (block.registry().collisionShape().intersectBox(position.sub(blockPos).add(blockEntityVector), boundingBox)) {
                            // Appelle une méthode
                            handler.onTouch(new BlockHandler.Touch(block, instance, new Vec(x, y, z), this));
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void effectTick() {
        // Affecte une valeur
        final List<TimedPotion> effects = this.effects;
        // Embranchement : vérifie une condition
        if (effects.isEmpty()) return;
        // Début d'une méthode/d'un bloc
        effects.removeIf(timedPotion -> {
            // Appelle une méthode
            long duration = timedPotion.potion().duration();
            // Embranchement : vérifie une condition
            if (duration == Potion.INFINITE_DURATION) return false;
            // Remove if the potion should be expired
            // Embranchement : vérifie une condition
            if (getAliveTicks() >= timedPotion.startingTicks() + duration) {
                // Send the packet that the potion should no longer be applied
                // Appelle une méthode
                timedPotion.potion().sendRemovePacket(this);
                // Appelle une méthode
                EventDispatcher.call(new EntityPotionRemoveEvent(this, timedPotion.potion()));
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the number of ticks this entity has been active for.
     *
     * @return the number of ticks this entity has been active for
     */
    // Début d'une méthode/d'un bloc
    public long getAliveTicks() {
        // Renvoie une valeur à l'appelant
        return ticks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Each entity has an unique id (server-wide) which will change after a restart.
     *
     * @return the unique entity id
     * @see Instance#getEntityById(int) to retrieve an entity based on its id
     */
    // Début d'une méthode/d'un bloc
    public int getEntityId() {
        // Renvoie une valeur à l'appelant
        return id;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the entity type.
     *
     * @return the entity type
     */
    // Début d'une méthode/d'un bloc
    public EntityType getEntityType() {
        // Renvoie une valeur à l'appelant
        return entityType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity {@link UUID}.
     *
     * @return the entity unique id
     */
    // Début d'une méthode/d'un bloc
    public UUID getUuid() {
        // Renvoie une valeur à l'appelant
        return uuid;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns whether this entity will run physics calculations.
     *
     * @return whether the entity will have physics calculations running
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPhysics() {
        // Renvoie une valeur à l'appelant
        return hasPhysics;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes whether this entity has physics calculations running.
     *
     * @param hasPhysics whether the entity will have physics calculations running
     */
    // Début d'une méthode/d'un bloc
    public void setHasPhysics(boolean hasPhysics) {
        // Accès à l'objet courant/parent
        this.hasPhysics = hasPhysics;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns false just after instantiation, set to true after calling {@link #setInstance(Instance)}.
     *
     * @return true if the entity has been linked to an instance, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isActive() {
        // Renvoie une valeur à l'appelant
        return isActive;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the current bounding box (based on pose).
     * Is used to check collision with coordinates or other blocks/entities.
     *
     * @return the entity bounding box
     */
    // Début d'une méthode/d'un bloc
    public BoundingBox getBoundingBox() {
        // Check if there is a specific bounding box for this pose
        // Appelle une méthode
        BoundingBox poseBoundingBox = BoundingBox.fromPose(getPose());
        // Renvoie une valeur à l'appelant
        return poseBoundingBox == null ? boundingBox : poseBoundingBox;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public void setBoundingBox(double width, double height, double depth) {
        // Appelle une méthode
        setBoundingBox(new BoundingBox(width, height, depth));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the internal entity standing bounding box.
     * When the pose is not standing, a different bounding box may be used for collision.
     * <p>
     * WARNING: this does not change the entity hit-box which is client-side.
     *
     * @param boundingBox the new bounding box
     */
    // Début d'une méthode/d'un bloc
    public void setBoundingBox(BoundingBox boundingBox) {
        // Accès à l'objet courant/parent
        this.boundingBox = boundingBox;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Convenient method to get the entity current chunk.
     *
     * @return the entity chunk, can be null even if unlikely
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Chunk getChunk() {
        // Renvoie une valeur à l'appelant
        return currentChunk;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected void refreshCurrentChunk(Chunk currentChunk) {
        // Accès à l'objet courant/parent
        this.currentChunk = currentChunk;
        // Appelle une méthode
        MinecraftServer.process().dispatcher().updateElement(this, currentChunk);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity current instance.
     *
     * @return the entity instance, can be null if the entity doesn't have an instance yet
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability Instance getInstance() {
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> setInstance(Instance instance, Pos spawnPosition) {
        // Instruction de code
        Check.stateCondition(!instance.isRegistered(),
                // Instruction de code
                "Instances need to be registered, please use InstanceManager#registerInstance or InstanceManager#registerSharedInstance");
        // Affecte une valeur
        final Instance previousInstance = this.instance;
        // Embranchement : vérifie une condition
        if (Objects.equals(previousInstance, instance)) {
            // Renvoie une valeur à l'appelant
            return teleport(spawnPosition); // Already in the instance, teleport to spawn point
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        AddEntityToInstanceEvent event = new AddEntityToInstanceEvent(instance, this);
        // Appelle une méthode
        EventDispatcher.call(event);
        // Embranchement : vérifie une condition
        if (event.isCancelled()) return null; // TODO what to return?

        // Embranchement : vérifie une condition
        if (previousInstance != null) removeFromInstance(previousInstance);
        // Embranchement : vérifie une condition
        if (this instanceof Player player) instance.bossBars().forEach(player::showBossBar);
        // Appelle une méthode
        EventsJFR.newInstanceJoin(getUuid(), instance.getUuid()).commit();

        // Accès à l'objet courant/parent
        this.isActive = true;
        // Appelle une méthode
        setPositionInternal(spawnPosition, spawnPosition.yaw());
        // Accès à l'objet courant/parent
        this.previousPosition = spawnPosition;
        // Accès à l'objet courant/parent
        this.lastSyncedPosition = spawnPosition;
        // Accès à l'objet courant/parent
        this.previousPhysicsResult = null;
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Renvoie une valeur à l'appelant
        return instance.loadOptionalChunk(spawnPosition).thenAccept(chunk -> {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                Objects.requireNonNull(chunk, "Entity has been placed in an unloaded chunk!");
                // Appelle une méthode
                refreshCurrentChunk(chunk);
                // Embranchement : vérifie une condition
                if (this instanceof Player player) {
                    // Appelle une méthode
                    player.sendPacket(instance.createInitializeWorldBorderPacket());
                    // Appelle une méthode
                    player.sendPacket(instance.createTimePacket());
                    // Appelle une méthode
                    player.sendPackets(instance.getWeather().createWeatherPackets());
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                instance.getEntityTracker().register(this, spawnPosition, trackingTarget, trackingUpdate);
                // Appelle une méthode
                spawn();
                // Appelle une méthode
                EventDispatcher.call(new EntitySpawnEvent(this, instance));
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> setInstance(Instance instance, Point spawnPosition) {
        // Renvoie une valeur à l'appelant
        return setInstance(instance, spawnPosition.asPos());
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> setInstance(Instance instance) {
        // Renvoie une valeur à l'appelant
        return setInstance(instance, this.position);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void removeFromInstance(Instance instance) {
        // Appelle une méthode
        EventDispatcher.call(new RemoveEntityFromInstanceEvent(instance, this));
        // Embranchement : vérifie une condition
        if (this instanceof Player player) instance.bossBars().forEach(player::hideBossBar);
        // Appelle une méthode
        instance.getEntityTracker().unregister(this, trackingTarget, trackingUpdate);
        // Accès à l'objet courant/parent
        this.viewEngine.forManuals(this::removeViewer);
        // Appelle une méthode
        EventsJFR.newInstanceLeave(getUuid(), instance.getUuid()).commit();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity current velocity.
     *
     * @return the entity current velocity
     */
    // Début d'une méthode/d'un bloc
    public Vec getVelocity() {
        // Renvoie une valeur à l'appelant
        return velocity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the entity velocity and calls {@link EntityVelocityEvent}.
     * <p>
     * The final velocity can be cancelled or modified by the event.
     *
     * @param velocity the new entity velocity
     */
    // Début d'une méthode/d'un bloc
    public void setVelocity(Vec velocity) {
        // Appelle une méthode
        EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent(this, velocity);
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(entityVelocityEvent, () -> {
            // Accès à l'objet courant/parent
            this.velocity = entityVelocityEvent.getVelocity();
            // Appelle une méthode
            sendPacketToViewersAndSelf(getVelocityPacket());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity currently has a velocity applied.
     *
     * @return true if the entity is moving
     */
    // Début d'une méthode/d'un bloc
    public boolean hasVelocity() {
        // Embranchement : vérifie une condition
        if (isOnGround()) {
            // if the entity is on the ground and only "moves" downwards, it does not have a velocity.
            // Renvoie une valeur à l'appelant
            return Double.compare(velocity.x(), 0) != 0 || Double.compare(velocity.z(), 0) != 0 || velocity.y() > 0;
        // Branche alternative de la condition
        } else {
            // The entity does not have velocity if the velocity is zero
            // Renvoie une valeur à l'appelant
            return !velocity.isZero();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the aerodynamics; how the entity behaves in the air.
     *
     * @return the aerodynamic properties this entity is using
     */
    // Début d'une méthode/d'un bloc
    public Aerodynamics getAerodynamics() {
        // Renvoie une valeur à l'appelant
        return aerodynamics;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the aerodynamics; how the entity behaves in the air.
     *
     * @param aerodynamics the new aerodynamic properties
     */
    // Début d'une méthode/d'un bloc
    public void setAerodynamics(Aerodynamics aerodynamics) {
        // Accès à l'objet courant/parent
        this.aerodynamics = aerodynamics;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the number of tick this entity has been applied gravity.
     *
     * @return the number of tick of which gravity has been consequently applied
     */
    // Début d'une méthode/d'un bloc
    public int getGravityTickCount() {
        // Renvoie une valeur à l'appelant
        return gravityTickCount;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getDistance(Point point) {
        // Renvoie une valeur à l'appelant
        return getPosition().distance(point);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the distance between two entities.
     *
     * @param entity the entity to get the distance from
     * @return the distance between this and {@code entity}
     */
    // Début d'une méthode/d'un bloc
    public double getDistance(Entity entity) {
        // Renvoie une valeur à l'appelant
        return getDistance(entity.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getDistanceSquared(Point point) {
        // Renvoie une valeur à l'appelant
        return getPosition().distanceSquared(point);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the distance squared between two entities.
     *
     * @param entity the entity to get the distance from
     * @return the distance squared between this and {@code entity}
     */
    // Début d'une méthode/d'un bloc
    public double getDistanceSquared(Entity entity) {
        // Renvoie une valeur à l'appelant
        return getPosition().distanceSquared(entity.getPosition());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity vehicle or null.
     *
     * @return the entity vehicle, or null if there is not any
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getVehicle() {
        // Renvoie une valeur à l'appelant
        return vehicle;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds a new passenger to this entity.
     *
     * @param entity the new passenger
     * @throws NullPointerException  if {@code entity} is null
     * @throws IllegalStateException if {@link #getInstance()} returns null or the passenger cannot be added
     */
    // Début d'une méthode/d'un bloc
    public void addPassenger(Entity entity) {
        // Affecte une valeur
        final Instance currentInstance = this.instance;
        // Appelle une méthode
        Check.stateCondition(currentInstance == null, "You need to set an instance using Entity#setInstance");
        // Appelle une méthode
        Check.stateCondition(entity == getVehicle(), "Cannot add the entity vehicle as a passenger");
        // Appelle une méthode
        final Entity vehicle = entity.getVehicle();
        // Embranchement : vérifie une condition
        if (vehicle != null) vehicle.removePassenger(entity);
        // Embranchement : vérifie une condition
        if (!currentInstance.equals(entity.getInstance()))
            // Appelle une méthode
            entity.setInstance(currentInstance, position).join();
        // Accès à l'objet courant/parent
        this.passengers.add(entity);
        // Affecte une valeur
        entity.vehicle = this;
        // Appelle une méthode
        sendPacketToViewersAndSelf(getPassengersPacket());
        // Appelle une méthode
        updatePassengerPosition(position, entity);
        // Appelle une méthode
        entity.synchronizePosition();
    // Fin d'un bloc/d'une expression
    }


    /**
     * Removes a passenger to this entity.
     *
     * @param entity the passenger to remove
     * @throws NullPointerException  if {@code entity} is null
     * @throws IllegalStateException if {@link #getInstance()} returns null
     */
    // Début d'une méthode/d'un bloc
    public void removePassenger(Entity entity) {
        // Appelle une méthode
        Check.stateCondition(instance == null, "You need to set an instance using Entity#setInstance");
        // Embranchement : vérifie une condition
        if (!passengers.remove(entity)) return;
        // Affecte une valeur
        entity.vehicle = null;
        // Appelle une méthode
        sendPacketToViewersAndSelf(getPassengersPacket());
        // Appelle une méthode
        entity.synchronizePosition();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity has any passenger.
     *
     * @return true if the entity has any passenger, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPassenger() {
        // Renvoie une valeur à l'appelant
        return !passengers.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity passengers.
     *
     * @return an unmodifiable list containing all the entity passengers
     */
    // Début d'une méthode/d'un bloc
    public Set<Entity> getPassengers() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(passengers);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected SetPassengersPacket getPassengersPacket() {
        // Renvoie une valeur à l'appelant
        return new SetPassengersPacket(getEntityId(), passengers.stream().map(Entity::getEntityId).toList());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entities that this entity is leashing.
     *
     * @return an unmodifiable list containing all the leashed entities
     */
    // Début d'une méthode/d'un bloc
    public Set<Entity> getLeashedEntities() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(leashedEntities);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current leash holder.
     *
     * @return the entity leashing this entity, null if no leash holder
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getLeashHolder() {
        // Renvoie une valeur à l'appelant
        return leashHolder;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the leash holder to this entity.
     *
     * @param entity the new leash holder
     */
    // Début d'une méthode/d'un bloc
    public void setLeashHolder(@Nullable Entity entity) {
        // Embranchement : vérifie une condition
        if (leashHolder != null) leashHolder.leashedEntities.remove(this);
        // Embranchement : vérifie une condition
        if (entity != null) entity.leashedEntities.add(this);
        // Accès à l'objet courant/parent
        this.leashHolder = entity;
        // Appelle une méthode
        sendPacketToViewersAndSelf(getAttachEntityPacket());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected AttachEntityPacket getAttachEntityPacket() {
        // Affecte une valeur
        Entity leashHolder = this.leashHolder;
        // Renvoie une valeur à l'appelant
        return new AttachEntityPacket(getEntityId(), leashHolder != null ? leashHolder.getEntityId() : -1);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Entity statuses can be found <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_statuses">here</a>.
     *
     * @param status the status to trigger
     */
    // Début d'une méthode/d'un bloc
    public void triggerStatus(byte status) {
        // Appelle une méthode
        sendPacketToViewersAndSelf(new EntityStatusPacket(getEntityId(), status));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is on fire.
     *
     * @return true if the entity is in fire, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isOnFire() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isOnFire();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is sneaking.
     * <p>
     * WARNING: this can be bypassed by hacked client, this is only what the client told the server.
     *
     * @return true if the player is sneaking
     */
    // Début d'une méthode/d'un bloc
    public boolean isSneaking() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isSneaking();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Makes the entity sneak.
     * <p>
     * WARNING: this will not work for the client itself.
     *
     * @param sneaking true to make the entity sneak
     */
    // Début d'une méthode/d'un bloc
    public void setSneaking(boolean sneaking) {
        // Accès à l'objet courant/parent
        this.entityMeta.setSneaking(sneaking);
        // Appelle une méthode
        updatePose();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is sprinting.
     * <p>
     * WARNING: this can be bypassed by hacked client, this is only what the client told the server.
     *
     * @return true if the player is sprinting
     */
    // Début d'une méthode/d'un bloc
    public boolean isSprinting() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isSprinting();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Makes the entity sprint.
     * <p>
     * WARNING: this will not work on the client itself.
     *
     * @param sprinting true to make the entity sprint
     */
    // Début d'une méthode/d'un bloc
    public void setSprinting(boolean sprinting) {
        // Accès à l'objet courant/parent
        this.entityMeta.setSprinting(sprinting);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is invisible or not.
     *
     * @return true if the entity is invisible, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isInvisible() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isInvisible();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the internal invisible value and send a {@link EntityMetaDataPacket}
     * to make visible or invisible the entity to its viewers.
     *
     * @param invisible true to set the entity invisible, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void setInvisible(boolean invisible) {
        // Accès à l'objet courant/parent
        this.entityMeta.setInvisible(invisible);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is glowing or not.
     *
     * @return true if the entity is glowing, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isGlowing() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isHasGlowingEffect();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets or remove the entity glowing effect.
     *
     * @param glowing true to make the entity glows, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void setGlowing(boolean glowing) {
        // Accès à l'objet courant/parent
        this.entityMeta.setHasGlowingEffect(glowing);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current entity pose.
     *
     * @return the entity pose
     */
    // Début d'une méthode/d'un bloc
    public EntityPose getPose() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.getPose();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the entity pose.
     * <p>
     * The internal {@code crouched} and {@code swimming} field will be
     * updated accordingly.
     *
     * @param pose the new entity pose
     */
    // Début d'une méthode/d'un bloc
    public void setPose(EntityPose pose) {
        // Accès à l'objet courant/parent
        this.entityMeta.setPose(pose);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void updatePose() {
        // Embranchement : vérifie une condition
        if (entityMeta.isFlyingWithElytra()) {
            // Appelle une méthode
            setPose(EntityPose.FALL_FLYING);
        // Embranchement : vérifie une condition
        } else if (entityMeta.isSwimming()) {
            // Appelle une méthode
            setPose(EntityPose.SWIMMING);
        // Embranchement : vérifie une condition
        } else if (entityMeta instanceof LivingEntityMeta livingMeta && livingMeta.isInRiptideSpinAttack()) {
            // Appelle une méthode
            setPose(EntityPose.SPIN_ATTACK);
        // Embranchement : vérifie une condition
        } else if (entityMeta.isSneaking()) {
            // Appelle une méthode
            setPose(EntityPose.SNEAKING);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            setPose(EntityPose.STANDING);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity custom name.
     *
     * @return the custom name of the entity, null if there is not
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public @Nullable Component getCustomName() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.getCustomName();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the entity custom name.
     *
     * @param customName the custom name of the entity, null to remove it
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setCustomName(@Nullable Component customName) {
        // Accès à l'objet courant/parent
        this.entityMeta.setCustomName(customName);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the custom name visible metadata field.
     *
     * @return true if the custom name is visible, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isCustomNameVisible() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isCustomNameVisible();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the internal custom name visible field and send a {@link EntityMetaDataPacket}
     * to update the entity state to its viewers.
     *
     * @param customNameVisible true to make the custom name visible, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void setCustomNameVisible(boolean customNameVisible) {
        // Accès à l'objet courant/parent
        this.entityMeta.setCustomNameVisible(customNameVisible);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSilent() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isSilent();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSilent(boolean silent) {
        // Accès à l'objet courant/parent
        this.entityMeta.setSilent(silent);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the noGravity metadata field.
     *
     * @return true if the entity ignore gravity, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean hasNoGravity() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isHasNoGravity();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the noGravity metadata field and change the gravity behaviour accordingly.
     *
     * @param noGravity should the entity ignore gravity
     */
    // Début d'une méthode/d'un bloc
    public void setNoGravity(boolean noGravity) {
        // Accès à l'objet courant/parent
        this.entityMeta.setHasNoGravity(noGravity);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates internal fields and sends updates.
     *
     * @param newPosition the new position
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void refreshPosition(final Pos newPosition, boolean ignoreView, boolean sendPackets) {
        // Affecte une valeur
        final var previousPosition = this.position;
        // Appelle une méthode
        final Pos position = ignoreView ? previousPosition.withCoord(newPosition) : newPosition;
        // Affecte une valeur
        final Pos lastSyncedPosition = this.lastSyncedPosition;
        // Embranchement : vérifie une condition
        if (position.equals(lastSyncedPosition)) return;
        // Appelle une méthode
        setPositionInternal(position, ignoreView ? headRotation : position.yaw());
        // Accès à l'objet courant/parent
        this.previousPosition = previousPosition;
        // Embranchement : vérifie une condition
        if (!position.samePoint(previousPosition)) refreshCoordinate(position);
        // Embranchement : vérifie une condition
        if (nextSynchronizationTick <= ticks + 1 || !sendPackets) {
            // The entity will be synchronized at the end of its tick
            // not returning here will duplicate position packets
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Update viewers
        // Appelle une méthode
        final boolean viewChange = !position.sameView(lastSyncedPosition);
        // Appelle une méthode
        final double distanceX = Math.abs(position.x() - lastSyncedPosition.x());
        // Appelle une méthode
        final double distanceY = Math.abs(position.y() - lastSyncedPosition.y());
        // Appelle une méthode
        final double distanceZ = Math.abs(position.z() - lastSyncedPosition.z());
        // Appelle une méthode
        final boolean positionChange = (distanceX + distanceY + distanceZ) > 0;

        // Appelle une méthode
        final Chunk chunk = getChunk();
        // Instruction de code
        assert chunk != null;
        // Embranchement : vérifie une condition
        if (distanceX > 8 || distanceY > 8 || distanceZ > 8) {
            // Send relative 0 velocity to avoid affecting it in this case
            // Instruction de code
            PacketViewableUtils.prepareViewablePacket(chunk, new EntityTeleportPacket(getEntityId(), position,
                    // Appelle une méthode
                    Vec.ZERO, RelativeFlags.DELTA_COORD, isOnGround()), this);
            // Affecte une valeur
            nextSynchronizationTick = synchronizationTicks + 1;
        // Embranchement : vérifie une condition
        } else if (positionChange && viewChange) {
            // Instruction de code
            PacketViewableUtils.prepareViewablePacket(chunk, EntityPositionAndRotationPacket.getPacket(getEntityId(), position,
                    // Appelle une méthode
                    lastSyncedPosition, isOnGround()), this);
            // Fix head rotation
            // Appelle une méthode
            PacketViewableUtils.prepareViewablePacket(chunk, new EntityHeadLookPacket(getEntityId(), headRotation), this);
        // Embranchement : vérifie une condition
        } else if (positionChange) {
            // This is a confusing fix for a confusing issue. If rotation is only sent when the entity actually changes, then spawning an entity
            // on the ground causes the entity not to update its rotation correctly. It works fine if the entity is spawned in the air. Very weird.
            // Instruction de code
            PacketViewableUtils.prepareViewablePacket(chunk, EntityPositionAndRotationPacket.getPacket(getEntityId(), position,
                    // Instruction de code
                    lastSyncedPosition, onGround), this);
        // Embranchement : vérifie une condition
        } else if (viewChange) {
            // Appelle une méthode
            PacketViewableUtils.prepareViewablePacket(chunk, new EntityHeadLookPacket(getEntityId(), headRotation), this);
            // Instruction de code
            PacketViewableUtils.prepareViewablePacket(chunk, EntityPositionAndRotationPacket.getPacket(getEntityId(), position,
                    // Appelle une méthode
                    lastSyncedPosition, isOnGround()), this);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.lastSyncedPosition = position;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void refreshPosition(final Pos newPosition, boolean ignoreView) {
        // Appelle une méthode
        refreshPosition(newPosition, ignoreView, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void refreshPosition(final Pos newPosition) {
        // Appelle une méthode
        refreshPosition(newPosition, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the coordinates of the passenger to the coordinates of this vehicle + {@link EntityUtils#getPassengerHeightOffset(Entity, Entity)}
     *
     * @param newPosition the new position of this vehicle
     * @param passenger   the passenger to be moved
     */
    // Début d'une méthode/d'un bloc
    private void updatePassengerPosition(Point newPosition, Entity passenger) {
        // Affecte une valeur
        final Pos oldPassengerPos = passenger.position;
        // Affecte une valeur
        final Pos newPassengerPos = oldPassengerPos.withCoord(newPosition.x(),
                // Instruction de code
                newPosition.y() + EntityUtils.getPassengerHeightOffset(this, passenger),
                // Appelle une méthode
                newPosition.z());
        // Appelle une méthode
        passenger.setPositionInternal(newPassengerPos, newPassengerPos.yaw());
        // Affecte une valeur
        passenger.previousPosition = oldPassengerPos;
        // Appelle une méthode
        passenger.refreshCoordinate(newPassengerPos);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected void refreshCoordinate(Point newPosition) {
        // Passengers update
        // Appelle une méthode
        final Set<Entity> passengers = getPassengers();
        // Embranchement : vérifie une condition
        if (!passengers.isEmpty()) {
            // Boucle : répète un bloc
            for (Entity passenger : passengers) {
                // Appelle une méthode
                updatePassengerPosition(newPosition, passenger);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Handle chunk switch
        // Appelle une méthode
        final Instance instance = getInstance();
        // Instruction de code
        assert instance != null;
        // Appelle une méthode
        instance.getEntityTracker().move(this, newPosition, trackingTarget, trackingUpdate);
        // Appelle une méthode
        final int lastChunkX = currentChunk.getChunkX();
        // Appelle une méthode
        final int lastChunkZ = currentChunk.getChunkZ();
        // Appelle une méthode
        final int newChunkX = newPosition.chunkX();
        // Appelle une méthode
        final int newChunkZ = newPosition.chunkZ();
        // Embranchement : vérifie une condition
        if (lastChunkX != newChunkX || lastChunkZ != newChunkZ) {
            // Entity moved in a new chunk
            // Appelle une méthode
            final Chunk newChunk = instance.getChunk(newChunkX, newChunkZ);
            // Appelle une méthode
            Check.notNull(newChunk, "The entity {0} tried to move in an unloaded chunk at {1}", getEntityId(), newPosition);
            // Embranchement : vérifie une condition
            if (this instanceof Player player) player.sendChunkUpdates(newChunk);
            // Appelle une méthode
            refreshCurrentChunk(newChunk);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity position.
     *
     * @return the current position of the entity
     */
    // Début d'une méthode/d'un bloc
    public Pos getPosition() {
        // Renvoie une valeur à l'appelant
        return position;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public float getHeadRotation() {
        // Renvoie une valeur à l'appelant
        return headRotation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the previous entity position.
     *
     * @return the previous position of the entity
     */
    // Début d'une méthode/d'un bloc
    public Pos getPreviousPosition() {
        // Renvoie une valeur à l'appelant
        return previousPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity eye height.
     *
     * @return the entity eye height
     */
    // Début d'une méthode/d'un bloc
    public double getEyeHeight() {
        // Renvoie une valeur à l'appelant
        return getPose() == EntityPose.SLEEPING ? 0.2 : entityType.registry().eyeHeight();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the potion effect of this entity.
     *
     * @return an unmodifiable list of all this entity effects
     */
    // Début d'une méthode/d'un bloc
    public List<TimedPotion> getActiveEffects() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableList(effects);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds an effect to an entity.
     *
     * @param potion The potion to add
     */
    // Début d'une méthode/d'un bloc
    public void addEffect(Potion potion) {
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(new EntityPotionAddEvent(this, potion), () -> {
            // Appelle une méthode
            removeEffect(potion.effect());
            // Accès à l'objet courant/parent
            this.effects.add(new TimedPotion(potion, getAliveTicks()));
            // Appelle une méthode
            potion.sendAddPacket(this);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes effect from entity, if it has it.
     *
     * @param effect The effect to remove
     */
    // Début d'une méthode/d'un bloc
    public void removeEffect(PotionEffect effect) {
        // Accès à l'objet courant/parent
        this.effects.removeIf(timedPotion -> {
            // Embranchement : vérifie une condition
            if (timedPotion.potion().effect() == effect) {
                // Appelle une méthode
                timedPotion.potion().sendRemovePacket(this);
                // Appelle une méthode
                EventDispatcher.call(new EntityPotionRemoveEvent(this, timedPotion.potion()));
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * If the entity has the specified effect.
     *
     * @param effect the effect to check
     */
    // Début d'une méthode/d'un bloc
    public boolean hasEffect(PotionEffect effect) {
        // Renvoie une valeur à l'appelant
        return this.effects.stream().anyMatch(timedPotion -> timedPotion.potion().effect() == effect);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the TimedPotion of the specified effect.
     *
     * @param effect the effect type
     * @return the effect, null if not found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable TimedPotion getEffect(PotionEffect effect) {
        // Renvoie une valeur à l'appelant
        return this.effects.stream().filter(timedPotion -> timedPotion.potion().effect() == effect).findFirst().orElse(null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the level of the specified effect.
     *
     * @param effect the effect type
     * @return the effect level, -1 if not found
     */
    // Début d'une méthode/d'un bloc
    public int getEffectLevel(PotionEffect effect) {
        // Appelle une méthode
        TimedPotion timedPotion = getEffect(effect);
        // Renvoie une valeur à l'appelant
        return timedPotion == null ? -1 : timedPotion.potion().amplifier();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes all the effects currently applied to the entity.
     */
    // Début d'une méthode/d'un bloc
    public void clearEffects() {
        // Boucle : répète un bloc
        for (TimedPotion timedPotion : effects) {
            // Appelle une méthode
            timedPotion.potion().sendRemovePacket(this);
            // Appelle une méthode
            EventDispatcher.call(new EntityPotionRemoveEvent(this, timedPotion.potion()));
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.effects.clear();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes the entity from the server immediately.
     * <p>
     * WARNING: this does not trigger {@link EntityDeathEvent}.
     */
    // Début d'une méthode/d'un bloc
    public void remove() {
        // Appelle une méthode
        remove(true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void remove(boolean permanent) {
        // Embranchement : vérifie une condition
        if (isRemoved()) return;
        // Appelle une méthode
        EventDispatcher.call(new EntityDespawnEvent(this));
        // Gestion des exceptions
        try {
            // Appelle une méthode
            despawn();
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(t);
        // Fin d'un bloc/d'une expression
        }

        // Remove passengers if any (also done with LivingEntity#kill)
        // Appelle une méthode
        Set<Entity> passengers = getPassengers();
        // Embranchement : vérifie une condition
        if (!passengers.isEmpty()) passengers.forEach(this::removePassenger);
        // Affecte une valeur
        final Entity vehicle = this.vehicle;
        // Embranchement : vérifie une condition
        if (vehicle != null) vehicle.removePassenger(this);

        // Appelle une méthode
        Set<Entity> leashedEntities = getLeashedEntities();
        // Appelle une méthode
        leashedEntities.forEach(entity -> entity.setLeashHolder(null));

        // Appelle une méthode
        MinecraftServer.process().dispatcher().removeElement(this);
        // Accès à l'objet courant/parent
        this.removed = true;
        // Embranchement : vérifie une condition
        if (!permanent) {
            // Reset some state to be ready for re-use
            // Appelle une méthode
            setPositionInternal(Pos.ZERO, 0);
            // Accès à l'objet courant/parent
            this.previousPosition = Pos.ZERO;
            // Accès à l'objet courant/parent
            this.lastSyncedPosition = Pos.ZERO;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        Instance currentInstance = this.instance;
        // Embranchement : vérifie une condition
        if (currentInstance != null) {
            // Appelle une méthode
            removeFromInstance(currentInstance);
            // Accès à l'objet courant/parent
            this.instance = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this entity has been removed.
     *
     * @return true if this entity is removed
     */
    // Début d'une méthode/d'un bloc
    public boolean isRemoved() {
        // Renvoie une valeur à l'appelant
        return removed;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Triggers {@link #remove()} after the specified time.
     *
     * @param delay        the time before removing the entity,
     *                     0 to cancel the removing
     * @param temporalUnit the unit of the delay
     */
    // Début d'une méthode/d'un bloc
    public void scheduleRemove(long delay, TemporalUnit temporalUnit) {
        // Embranchement : vérifie une condition
        if (temporalUnit == TimeUnit.SERVER_TICK) {
            // Appelle une méthode
            scheduleRemove(TaskSchedule.tick((int) delay));
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            scheduleRemove(Duration.of(delay, temporalUnit));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Triggers {@link #remove()} after the specified time.
     *
     * @param delay the time before removing the entity
     */
    // Début d'une méthode/d'un bloc
    public void scheduleRemove(Duration delay) {
        // Appelle une méthode
        scheduleRemove(TaskSchedule.duration(delay));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void scheduleRemove(TaskSchedule schedule) {
        // Accès à l'objet courant/parent
        this.scheduler.buildTask(this::remove).delay(schedule).schedule();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected Vec getVelocityForPacket() {
        // Renvoie une valeur à l'appelant
        return this.velocity.div(ServerFlag.SERVER_TICKS_PER_SECOND);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected SpawnEntityPacket getSpawnPacket() {
        // Affecte une valeur
        int data = 0;
        // Affecte une valeur
        Vec velocity = Vec.ZERO;
        // Embranchement : vérifie une condition
        if (getEntityMeta() instanceof ObjectDataProvider objectDataProvider) {
            // Appelle une méthode
            data = objectDataProvider.getObjectData();
            // Embranchement : vérifie une condition
            if (objectDataProvider.requiresVelocityPacketAtSpawn()) {
                // Appelle une méthode
                velocity = getVelocityForPacket();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Pos position = getPosition();
        // Renvoie une valeur à l'appelant
        return new SpawnEntityPacket(getEntityId(), getUuid(), getEntityType(),
                // Appelle une méthode
                position, position.yaw(), data, velocity);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected EntityVelocityPacket getVelocityPacket() {
        // Renvoie une valeur à l'appelant
        return new EntityVelocityPacket(getEntityId(), getVelocityForPacket());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an {@link EntityMetaDataPacket} sent when adding viewers. Used for synchronization.
     *
     * @return The {@link EntityMetaDataPacket} related to this entity
     */
    // Début d'une méthode/d'un bloc
    public EntityMetaDataPacket getMetadataPacket() {
        // Renvoie une valeur à l'appelant
        return new EntityMetaDataPacket(getEntityId(), metadata.getEntries());
    // Fin d'un bloc/d'une expression
    }

    // Currently file-private so it can be used in MetadataHolder, planned to be private.
    // Début d'une méthode/d'un bloc
    void notifyMetadataChanges(Map<Integer, Metadata.Entry<?>> changes) {
        // Embranchement : vérifie une condition
        if (!isActive()) return;
        // Appelle une méthode
        sendPacketToViewersAndSelf(new EntityMetaDataPacket(getEntityId(), changes));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to synchronize entity position with viewers by sending a full
     * {@link EntityPositionSyncPacket} to viewers.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected void synchronizePosition() {
        // Affecte une valeur
        final Pos posCache = this.position;
        // Appelle une méthode
        final Pos delta = posCache.sub(lastSyncedPosition);
        // Appelle une méthode
        PacketViewableUtils.prepareViewablePacket(currentChunk, new EntityPositionSyncPacket(getEntityId(), posCache, delta, posCache.yaw(), posCache.pitch(), isOnGround()), this);
        // Affecte une valeur
        nextSynchronizationTick = ticks + synchronizationTicks;
        // Accès à l'objet courant/parent
        this.lastSyncedPosition = posCache;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to synchronize the head position and rotation of the entity
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    protected void synchronizeView() {
        // Affecte une valeur
        final Pos position = this.position;
        // Appelle une méthode
        sendPacketToViewers(new EntityHeadLookPacket(getEntityId(), headRotation));
        // Appelle une méthode
        sendPacketToViewers(new EntityRotationPacket(getEntityId(), position.yaw(), position.pitch(), onGround));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Asks for a position synchronization to happen during next entity tick.
     */
    // Début d'une méthode/d'un bloc
    public void synchronizeNextTick() {
        // Accès à l'objet courant/parent
        this.nextSynchronizationTick = 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the current synchronization interval. The default value is {@link ServerFlag#ENTITY_SYNCHRONIZATION_TICKS}
     * but can be overridden per entity with {@link #setSynchronizationTicks(long)}.
     *
     * @return The current synchronization ticks
     */
    // Début d'une méthode/d'un bloc
    public long getSynchronizationTicks() {
        // Renvoie une valeur à l'appelant
        return this.synchronizationTicks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Set the tick period until this entity's position is synchronized.
     *
     * @param ticks the new synchronization tick period
     */
    // Début d'une méthode/d'un bloc
    public void setSynchronizationTicks(long ticks) {
        // Accès à l'objet courant/parent
        this.synchronizationTicks = ticks;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public HoverEvent<ShowEntity> asHoverEvent(UnaryOperator<ShowEntity> op) {
        // Renvoie une valeur à l'appelant
        return HoverEvent.showEntity(ShowEntity.showEntity(this.entityType, this.uuid));
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
    // Début d'une méthode/d'un bloc
    public EntitySnapshot updateSnapshot(SnapshotUpdater updater) {
        // Affecte une valeur
        final Chunk chunk = currentChunk;
        // Appelle une méthode
        final int[] viewersId = this.viewEngine.viewableOption.bitSet.toIntArray();
        // Appelle une méthode
        final int[] passengersId = ArrayUtils.mapToIntArray(passengers, Entity::getEntityId);
        // Affecte une valeur
        final Entity vehicle = this.vehicle;
        // Renvoie une valeur à l'appelant
        return new SnapshotImpl.Entity(entityType, uuid, id, position, velocity,
                // Instruction de code
                updater.reference(instance), chunk.getChunkX(), chunk.getChunkZ(),
                // Instruction de code
                viewersId, passengersId, vehicle == null ? -1 : vehicle.getEntityId(),
                // Appelle une méthode
                tagHandler.readableCopy());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<EntityEvent> eventNode() {
        // Renvoie une valeur à l'appelant
        return eventNode;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies knockback to the entity
     *
     * @param strength the strength of the knockback, 0.4 is the vanilla value for a bare hand hit
     * @param x        knockback on x axle, for default knockback use the following formula <pre>sin(attacker.yaw * (pi/180))</pre>
     * @param z        knockback on z axle, for default knockback use the following formula <pre>-cos(attacker.yaw * (pi/180))</pre>
     */
    // Début d'une méthode/d'un bloc
    public void takeKnockback(float strength, final double x, final double z) {
        // Embranchement : vérifie une condition
        if (strength > 0) {
            //TODO check possible side effects of unnatural TPS (other than 20TPS)
            // Instruction de code
            strength *= ServerFlag.SERVER_TICKS_PER_SECOND;
            // Appelle une méthode
            final Vec velocityModifier = new Vec(x, z).normalize().mul(strength);
            // Affecte une valeur
            final double verticalLimit = .4d * ServerFlag.SERVER_TICKS_PER_SECOND;

            // Instruction de code
            setVelocity(new Vec(velocity.x() / 2d - velocityModifier.x(),
                    // Instruction de code
                    onGround ? Math.min(verticalLimit, velocity.y() / 2d + strength) : velocity.y(),
                    // Instruction de code
                    velocity.z() / 2d - velocityModifier.z()
            // Instruction de code
            ));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the line of sight of the entity.
     *
     * @param maxDistance The max distance to scan
     * @return A list of {@link Point points} in this entities line of sight
     */
    // Début d'une méthode/d'un bloc
    public List<Point> getLineOfSight(int maxDistance) {
        // Appelle une méthode
        Instance instance = getInstance();
        // Embranchement : vérifie une condition
        if (instance == null) {
            // Renvoie une valeur à l'appelant
            return List.of();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        List<Point> blocks = new ArrayList<>();
        // Appelle une méthode
        var it = new BlockIterator(this, maxDistance);
        // Boucle : répète un bloc
        while (it.hasNext()) {
            // Appelle une méthode
            final Point position = it.next();
            // Embranchement : vérifie une condition
            if (!instance.getBlock(position).isAir()) blocks.add(position);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return blocks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Raycasts current entity's eye position to target eye position.
     *
     * @param entity    the entity to be checked.
     * @param exactView if set to TRUE, checks whether target is IN the line of sight of the current one;
     *                  otherwise checks if the current entity can rotate so that target will be in its line of sight.
     * @return true if the ray reaches the target bounding box before hitting a block.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasLineOfSight(Entity entity, boolean exactView) {
        // Appelle une méthode
        Instance instance = getInstance();
        // Embranchement : vérifie une condition
        if (instance == null) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Pos start = position.withY(position.y() + getEyeHeight());
        // Appelle une méthode
        final Pos end = entity.position.withY(entity.position.y() + entity.getEyeHeight());
        // Appelle une méthode
        final Vec direction = exactView ? position.direction() : end.sub(start).asVec().normalize();
        // Embranchement : vérifie une condition
        if (!entity.boundingBox.boundingBoxRayIntersectionCheck(start.asVec(), direction, entity.position)) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return CollisionUtils.isLineOfSightReachingShape(instance, currentChunk, start, end, entity.boundingBox, entity.position);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entity the entity to be checked.
     * @return if the current entity has line of sight to the given one.
     * @see Entity#hasLineOfSight(Entity, boolean)
     */
    // Début d'une méthode/d'un bloc
    public boolean hasLineOfSight(Entity entity) {
        // Renvoie une valeur à l'appelant
        return hasLineOfSight(entity, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets first entity on the line of sight of the current one that matches the given predicate.
     *
     * @param range     max length of the line of sight of the current entity to be checked.
     * @param predicate optional predicate
     * @return resulting entity whether there're any, null otherwise.
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getLineOfSightEntity(double range, Predicate<? super Entity> predicate) {
        // Appelle une méthode
        Instance instance = getInstance();
        // Embranchement : vérifie une condition
        if (instance == null) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Pos start = position.withY(position.y() + getEyeHeight());
        // Appelle une méthode
        final Vec startAsVec = start.asVec();
        // Affecte une valeur
        final Predicate<Entity> finalPredicate = e -> e != this
                // Instruction de code
                && e.boundingBox.boundingBoxRayIntersectionCheck(startAsVec, position.direction(), e.position)
                // Instruction de code
                && predicate.test(e)
                // Instruction de code
                && CollisionUtils.isLineOfSightReachingShape(instance, currentChunk, start,
                // Appelle une méthode
                e.position.withY(e.position.y() + e.getEyeHeight()), e.boundingBox, e.position);

        // Affecte une valeur
        Optional<Entity> nearby = instance.getNearbyEntities(position, range).stream()
                // Instruction de code
                .filter(finalPredicate)
                // Appelle une méthode
                .min(Comparator.comparingDouble(e -> e.getDistanceSquared(this)));

        // Renvoie une valeur à l'appelant
        return nearby.orElse(null);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isOccluded(Shape shape, BlockFace face) {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean intersectBox(Point positionRelative, BoundingBox boundingBox) {
        // Renvoie une valeur à l'appelant
        return this.boundingBox.intersectBox(positionRelative, boundingBox);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean intersectBoxSwept(Point rayStart, Point rayDirection, Point shapePos, BoundingBox moving, SweepResult finalResult) {
        // Renvoie une valeur à l'appelant
        return boundingBox.intersectBoxSwept(rayStart, rayDirection, shapePos, moving, finalResult);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point relativeStart() {
        // Renvoie une valeur à l'appelant
        return boundingBox.relativeStart();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point relativeEnd() {
        // Renvoie une valeur à l'appelant
        return boundingBox.relativeEnd();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasEntityCollision() {
        // Renvoie une valeur à l'appelant
        return collidesWithEntities;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean preventBlockPlacement() {
        // EntityMeta can change at any time, so initializing this during #initCollisions is not an option
        // Can be overridden to allow for custom behaviour
        // Embranchement : vérifie une condition
        if (entityMeta instanceof ArmorStandMeta armorStandMeta && armorStandMeta.isMarker()) return false;
        // Renvoie une valeur à l'appelant
        return preventBlockPlacement;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void updateCollisions() {
        // Appelle une méthode
        preventBlockPlacement = !ALLOW_BLOCK_PLACEMENT_ENTITIES.contains(entityType);
        // Appelle une méthode
        collidesWithEntities = !NO_ENTITY_COLLISION_ENTITIES.contains(entityType);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Acquires this entity.
     *
     * @param <T> the type of object to be acquired
     * @return the acquirable for this entity
     * @deprecated It's preferred to use {@link AcquirableSource#acquirable()} instead, as it is overridden by
     * subclasses
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public <T extends Entity> Acquirable<T> getAcquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<T>) acquirable;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends Entity> acquirable() {
        // Renvoie une valeur à l'appelant
        return acquirable;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Identity identity() {
        // Renvoie une valeur à l'appelant
        return Identity.identity(this.uuid); // Unfortunate pollution, if we extended Identity (contains UUID static)
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pointers pointers() {
        // Renvoie une valeur à l'appelant
        return ENTITY_POINTERS_SUPPLIER.view(this);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
