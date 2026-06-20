// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.ExperienceOrb;
// Import of a required class
import net.minestom.server.entity.ItemEntity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnmodifiableView;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.Consumer;

/**
 * Defines how {@link Entity entities} are tracked within an {@link Instance instance}.
 * <p>
 * Implementations are expected to be thread-safe.
 */
// Type declaration (class/interface/enum/record)
public sealed interface EntityTracker permits EntityTrackerImpl {
    // Start of a method/block
    static EntityTracker newTracker() {
        // Returns a value to the caller
        return new EntityTrackerImpl();
    // End of a block/expression
    }

    /**
     * Register an entity to be tracked.
     */
    // Code statement
    <T extends Entity> void register(Entity entity, Point point,
                                     // Code statement
                                     Target<T> target, @Nullable Update<T> update);

    /**
     * Unregister an entity tracking.
     */
    // Calls a method
    <T extends Entity> void unregister(Entity entity, Target<T> target, @Nullable Update<T> update);

    /**
     * Gets an entity based on its id (from {@link Entity#getEntityId()}).
     *
     * @param id the entity id
     * @return the entity having the specified id, null if not found
     */
    // Annotation for the following element
    @Nullable Entity getEntityById(int id);

    /**
     * Gets an entity based on its UUID (from {@link Entity#getUuid()}).
     *
     * @param uuid the entity UUID
     * @return the entity having the specified uuid, null if not found
     */
    // Annotation for the following element
    @Nullable Entity getEntityByUuid(UUID uuid);

    /**
     * Called every time an entity move, you may want to verify if the new
     * position is in a different chunk.
     */
    // Code statement
    <T extends Entity> void move(Entity entity, Point newPoint,
                                 // Code statement
                                 Target<T> target, @Nullable Update<T> update);

    // Annotation for the following element
    @UnmodifiableView <T extends Entity> Collection<T> chunkEntities(int chunkX, int chunkZ, Target<T> target);

    // Annotation for the following element
    @UnmodifiableView
    // Start of a method/block
    default <T extends Entity> Collection<T> chunkEntities(Point point, Target<T> target) {
        // Returns a value to the caller
        return chunkEntities(point.chunkX(), point.chunkZ(), target);
    // End of a block/expression
    }

    /**
     * Gets the entities within a chunk range.
     */
    // Code statement
    <T extends Entity> void nearbyEntitiesByChunkRange(Point point, int chunkRange,
                                                       // Code statement
                                                       Target<T> target, Consumer<T> query);

    /**
     * Gets the entities within a range.
     */
    // Code statement
    <T extends Entity> void nearbyEntities(Point point, double range,
                                           // Code statement
                                           Target<T> target, Consumer<T> query);

    /**
     * Gets all the entities tracked by this class.
     */
    // Annotation for the following element
    @UnmodifiableView
    // Calls a method
    <T extends Entity> Set<T> entities(Target<T> target);

    // Annotation for the following element
    @UnmodifiableView
    // Start of a method/block
    default Set<Entity> entities() {
        // Returns a value to the caller
        return entities(Target.ENTITIES);
    // End of a block/expression
    }

    // Calls a method
    Viewable viewable(List<SharedInstance> sharedInstances, int chunkX, int chunkZ);

    // Start of a method/block
    default Viewable viewable(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return viewable(List.of(), chunkX, chunkZ);
    // End of a block/expression
    }

    /**
     * Represents the type of entity you want to retrieve.
     *
     * @param <E> the entity type
     */
    // Annotation for the following element
    @ApiStatus.NonExtendable
    // Type declaration (class/interface/enum/record)
    interface Target<E extends Entity> {
        // Calls a method
        Target<Entity> ENTITIES = create(Entity.class);
        // Calls a method
        Target<Player> PLAYERS = create(Player.class);
        // Calls a method
        Target<ItemEntity> ITEMS = create(ItemEntity.class);
        // Calls a method
        Target<ExperienceOrb> EXPERIENCE_ORBS = create(ExperienceOrb.class);

        // Calls a method
        List<EntityTracker.Target<? extends Entity>> TARGETS = List.of(EntityTracker.Target.ENTITIES, EntityTracker.Target.PLAYERS, EntityTracker.Target.ITEMS, EntityTracker.Target.EXPERIENCE_ORBS);

        // Calls a method
        Class<E> type();

        // Calls a method
        int ordinal();

        // Start of a method/block
        private static <T extends Entity> EntityTracker.Target<T> create(Class<T> type) {
            // Calls a method
            final int ordinal = EntityTrackerImpl.TARGET_COUNTER.getAndIncrement();
            // Returns a value to the caller
            return new Target<>() {
                // Annotation for the following element
                @Override
                // Start of a method/block
                public Class<T> type() {
                    // Returns a value to the caller
                    return type;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public int ordinal() {
                    // Returns a value to the caller
                    return ordinal;
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Callback to know the newly visible entities and those to remove.
     */
    // Type declaration (class/interface/enum/record)
    interface Update<E extends Entity> {
        // Calls a method
        void add(E entity);

        // Calls a method
        void remove(E entity);

        // Start of a method/block
        default void referenceUpdate(Point point, @Nullable EntityTracker tracker) {
            // Empty
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
