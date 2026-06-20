// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.ExperienceOrb;
// Import d'une classe nécessaire
import net.minestom.server.entity.ItemEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnmodifiableView;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * Defines how {@link Entity entities} are tracked within an {@link Instance instance}.
 * <p>
 * Implementations are expected to be thread-safe.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface EntityTracker permits EntityTrackerImpl {
    // Début d'une méthode/d'un bloc
    static EntityTracker newTracker() {
        // Renvoie une valeur à l'appelant
        return new EntityTrackerImpl();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Register an entity to be tracked.
     */
    // Instruction de code
    <T extends Entity> void register(Entity entity, Point point,
                                     // Instruction de code
                                     Target<T> target, @Nullable Update<T> update);

    /**
     * Unregister an entity tracking.
     */
    // Appelle une méthode
    <T extends Entity> void unregister(Entity entity, Target<T> target, @Nullable Update<T> update);

    /**
     * Gets an entity based on its id (from {@link Entity#getEntityId()}).
     *
     * @param id the entity id
     * @return the entity having the specified id, null if not found
     */
    // Annotation pour l'élément suivant
    @Nullable Entity getEntityById(int id);

    /**
     * Gets an entity based on its UUID (from {@link Entity#getUuid()}).
     *
     * @param uuid the entity UUID
     * @return the entity having the specified uuid, null if not found
     */
    // Annotation pour l'élément suivant
    @Nullable Entity getEntityByUuid(UUID uuid);

    /**
     * Called every time an entity move, you may want to verify if the new
     * position is in a different chunk.
     */
    // Instruction de code
    <T extends Entity> void move(Entity entity, Point newPoint,
                                 // Instruction de code
                                 Target<T> target, @Nullable Update<T> update);

    // Annotation pour l'élément suivant
    @UnmodifiableView <T extends Entity> Collection<T> chunkEntities(int chunkX, int chunkZ, Target<T> target);

    // Annotation pour l'élément suivant
    @UnmodifiableView
    // Début d'une méthode/d'un bloc
    default <T extends Entity> Collection<T> chunkEntities(Point point, Target<T> target) {
        // Renvoie une valeur à l'appelant
        return chunkEntities(point.chunkX(), point.chunkZ(), target);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entities within a chunk range.
     */
    // Instruction de code
    <T extends Entity> void nearbyEntitiesByChunkRange(Point point, int chunkRange,
                                                       // Instruction de code
                                                       Target<T> target, Consumer<T> query);

    /**
     * Gets the entities within a range.
     */
    // Instruction de code
    <T extends Entity> void nearbyEntities(Point point, double range,
                                           // Instruction de code
                                           Target<T> target, Consumer<T> query);

    /**
     * Gets all the entities tracked by this class.
     */
    // Annotation pour l'élément suivant
    @UnmodifiableView
    // Appelle une méthode
    <T extends Entity> Set<T> entities(Target<T> target);

    // Annotation pour l'élément suivant
    @UnmodifiableView
    // Début d'une méthode/d'un bloc
    default Set<Entity> entities() {
        // Renvoie une valeur à l'appelant
        return entities(Target.ENTITIES);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Viewable viewable(List<SharedInstance> sharedInstances, int chunkX, int chunkZ);

    // Début d'une méthode/d'un bloc
    default Viewable viewable(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return viewable(List.of(), chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents the type of entity you want to retrieve.
     *
     * @param <E> the entity type
     */
    // Annotation pour l'élément suivant
    @ApiStatus.NonExtendable
    // Déclaration de type (classe/interface/enum/record)
    interface Target<E extends Entity> {
        // Appelle une méthode
        Target<Entity> ENTITIES = create(Entity.class);
        // Appelle une méthode
        Target<Player> PLAYERS = create(Player.class);
        // Appelle une méthode
        Target<ItemEntity> ITEMS = create(ItemEntity.class);
        // Appelle une méthode
        Target<ExperienceOrb> EXPERIENCE_ORBS = create(ExperienceOrb.class);

        // Appelle une méthode
        List<EntityTracker.Target<? extends Entity>> TARGETS = List.of(EntityTracker.Target.ENTITIES, EntityTracker.Target.PLAYERS, EntityTracker.Target.ITEMS, EntityTracker.Target.EXPERIENCE_ORBS);

        // Appelle une méthode
        Class<E> type();

        // Appelle une méthode
        int ordinal();

        // Début d'une méthode/d'un bloc
        private static <T extends Entity> EntityTracker.Target<T> create(Class<T> type) {
            // Appelle une méthode
            final int ordinal = EntityTrackerImpl.TARGET_COUNTER.getAndIncrement();
            // Renvoie une valeur à l'appelant
            return new Target<>() {
                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public Class<T> type() {
                    // Renvoie une valeur à l'appelant
                    return type;
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public int ordinal() {
                    // Renvoie une valeur à l'appelant
                    return ordinal;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Callback to know the newly visible entities and those to remove.
     */
    // Déclaration de type (classe/interface/enum/record)
    interface Update<E extends Entity> {
        // Appelle une méthode
        void add(E entity);

        // Appelle une méthode
        void remove(E entity);

        // Début d'une méthode/d'un bloc
        default void referenceUpdate(Point point, @Nullable EntityTracker tracker) {
            // Empty
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
