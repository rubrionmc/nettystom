// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.ChunkRange;
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
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnmodifiableView;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;
// Import d'une classe nécessaire
import space.vectrix.flare.fastutil.Int2ObjectSyncMap;
// Import d'une classe nécessaire
import space.vectrix.flare.fastutil.Long2ObjectSyncMap;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_X;
// Import statique d'un membre
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_Z;

// Déclaration de type (classe/interface/enum/record)
final class EntityTrackerImpl implements EntityTracker {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityTrackerImpl.class);

    // Appelle une méthode
    static final AtomicInteger TARGET_COUNTER = new AtomicInteger();

    // Store all data associated to a Target
    // The array index is the Target enum ordinal
    // Appelle une méthode
    final TargetEntry<Entity>[] targetEntries = EntityTracker.Target.TARGETS.stream().map((Function<Target<?>, TargetEntry>) TargetEntry::new).toArray(TargetEntry[]::new);

    // Appelle une méthode
    private final Int2ObjectSyncMap<EntityTrackerEntry> entriesByEntityId = Int2ObjectSyncMap.hashmap();
    // Affecte une valeur
    private final Map<UUID, EntityTrackerEntry> entriesByEntityUuid = new ConcurrentHashMap<>();

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public <T extends Entity> void register(Entity entity, Point point,
                                            // Début d'une méthode/d'un bloc
                                            Target<T> target, @Nullable Update<T> update) {
        // Appelle une méthode
        EntityTrackerEntry newEntry = new EntityTrackerEntry(entity, point);

        // Appelle une méthode
        EntityTrackerEntry prevEntryWithId = entriesByEntityId.putIfAbsent(entity.getEntityId(), newEntry);
        // Appelle une méthode
        Check.isTrue(prevEntryWithId == null, "There is already an entity registered with id {0}", entity.getEntityId());
        // Appelle une méthode
        EntityTrackerEntry prevEntryWithUuid = entriesByEntityUuid.putIfAbsent(entity.getUuid(), newEntry);
        // Appelle une méthode
        Check.isTrue(prevEntryWithUuid == null, "There is already an entity registered with uuid {0}", entity.getUuid());

        // Appelle une méthode
        final long index = CoordConversion.chunkIndex(point);
        // Boucle : répète un bloc
        for (TargetEntry<Entity> targetEntry : targetEntries) {
            // Embranchement : vérifie une condition
            if (targetEntry.target.type().isInstance(entity)) {
                // Appelle une méthode
                targetEntry.entities.add(entity);
                // Appelle une méthode
                targetEntry.addToChunk(index, entity);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (update != null) {
            // Appelle une méthode
            update.referenceUpdate(point, this);
            // Début d'une méthode/d'un bloc
            nearbyEntitiesByChunkRange(point, ServerFlag.ENTITY_VIEW_DISTANCE, target, newEntity -> {
                // Embranchement : vérifie une condition
                if (newEntity == entity) return;
                // Appelle une méthode
                update.add(newEntity);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public <T extends Entity> void unregister(Entity entity,
                                              // Début d'une méthode/d'un bloc
                                              Target<T> target, @Nullable Update<T> update) {
        // Appelle une méthode
        EntityTrackerEntry entry = entriesByEntityId.remove(entity.getEntityId());
        // Appelle une méthode
        entriesByEntityUuid.remove(entity.getUuid());
        // Appelle une méthode
        final Point point = entry == null ? null : entry.getLastPosition();
        // Embranchement : vérifie une condition
        if (point == null) return;

        // Appelle une méthode
        final long index = CoordConversion.chunkIndex(point);
        // Boucle : répète un bloc
        for (TargetEntry<Entity> targetEntry : targetEntries) {
            // Embranchement : vérifie une condition
            if (targetEntry.target.type().isInstance(entity)) {
                // Appelle une méthode
                targetEntry.entities.remove(entity);
                // Appelle une méthode
                targetEntry.removeFromChunk(index, entity);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (update != null) {
            // Appelle une méthode
            update.referenceUpdate(point, null);
            // Début d'une méthode/d'un bloc
            nearbyEntitiesByChunkRange(point, ServerFlag.ENTITY_VIEW_DISTANCE, target, newEntity -> {
                // Embranchement : vérifie une condition
                if (newEntity == entity) return;
                // Appelle une méthode
                update.remove(newEntity);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getEntityById(int id) {
        // Appelle une méthode
        EntityTrackerEntry entry = entriesByEntityId.get(id);
        // Renvoie une valeur à l'appelant
        return entry == null ? null : entry.getEntity();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getEntityByUuid(UUID uuid) {
        // Appelle une méthode
        EntityTrackerEntry entry = entriesByEntityUuid.get(uuid);
        // Renvoie une valeur à l'appelant
        return entry == null ? null : entry.getEntity();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public <T extends Entity> void move(Entity entity, Point newPoint,
                                        // Début d'une méthode/d'un bloc
                                        Target<T> target, @Nullable Update<T> update) {
        // Appelle une méthode
        EntityTrackerEntry entry = entriesByEntityId.get(entity.getEntityId());
        // Embranchement : vérifie une condition
        if (entry == null) {
            // Appelle une méthode
            LOGGER.warn("Attempted to move unregistered entity {} in the entity tracker", entity.getEntityId());
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Point oldPoint = entry.getLastPosition();
        // Appelle une méthode
        entry.setLastPosition(newPoint);
        // Embranchement : vérifie une condition
        if (oldPoint == null || oldPoint.sameChunk(newPoint)) return;
        // Appelle une méthode
        final long oldIndex = CoordConversion.chunkIndex(oldPoint);
        // Appelle une méthode
        final long newIndex = CoordConversion.chunkIndex(newPoint);
        // Boucle : répète un bloc
        for (TargetEntry<Entity> targetEntry : targetEntries) {
            // Embranchement : vérifie une condition
            if (targetEntry.target.type().isInstance(entity)) {
                // Appelle une méthode
                targetEntry.addToChunk(newIndex, entity);
                // Appelle une méthode
                targetEntry.removeFromChunk(oldIndex, entity);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (update != null) {
            // Début d'une méthode/d'un bloc
            difference(oldPoint, newPoint, target, new Update<>() {
                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public void add(T added) {
                    // Embranchement : vérifie une condition
                    if (entity != added) update.add(added);
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public void remove(T removed) {
                    // Embranchement : vérifie une condition
                    if (entity != removed) update.remove(removed);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            update.referenceUpdate(newPoint, this);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Unmodifiable <T extends Entity> Collection<T> chunkEntities(int chunkX, int chunkZ, Target<T> target) {
        // Appelle une méthode
        final TargetEntry<Entity> entry = targetEntries[target.ordinal()];
        //noinspection unchecked
        // Appelle une méthode
        var chunkEntities = (List<T>) entry.chunkEntities(CoordConversion.chunkIndex(chunkX, chunkZ));
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableList(chunkEntities);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T extends Entity> void nearbyEntitiesByChunkRange(Point point, int chunkRange, Target<T> target, Consumer<T> query) {
        // Appelle une méthode
        final Long2ObjectSyncMap<List<Entity>> entities = targetEntries[target.ordinal()].chunkEntities;
        // Embranchement : vérifie une condition
        if (chunkRange == 0) {
            // Single chunk
            // Appelle une méthode
            final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(point));
            // Embranchement : vérifie une condition
            if (chunkEntities != null && !chunkEntities.isEmpty()) {
                // Appelle une méthode
                chunkEntities.forEach(query);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Multiple chunks
            // Début d'une méthode/d'un bloc
            ChunkRange.chunksInRange(point, chunkRange, (chunkX, chunkZ) -> {
                // Appelle une méthode
                final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                // Embranchement : vérifie une condition
                if (chunkEntities == null || chunkEntities.isEmpty()) return;
                // Appelle une méthode
                chunkEntities.forEach(query);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T extends Entity> void nearbyEntities(Point point, double range, Target<T> target, Consumer<T> query) {
        // Appelle une méthode
        final Long2ObjectSyncMap<List<Entity>> entities = targetEntries[target.ordinal()].chunkEntities;
        // Appelle une méthode
        final int minChunkX = CoordConversion.globalToChunk(point.x() - range);
        // Appelle une méthode
        final int minChunkZ = CoordConversion.globalToChunk(point.z() - range);
        // Appelle une méthode
        final int maxChunkX = CoordConversion.globalToChunk(point.x() + range);
        // Appelle une méthode
        final int maxChunkZ = CoordConversion.globalToChunk(point.z() + range);
        // Affecte une valeur
        final double squaredRange = range * range;
        // Embranchement : vérifie une condition
        if (minChunkX == maxChunkX && minChunkZ == maxChunkZ) {
            // Single chunk
            // Appelle une méthode
            final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(point));
            // Embranchement : vérifie une condition
            if (chunkEntities != null && !chunkEntities.isEmpty()) {
                // Début d'une méthode/d'un bloc
                chunkEntities.forEach(entity -> {
                    // Appelle une méthode
                    final Point position = entriesByEntityId.get(entity.getEntityId()).getLastPosition();
                    // Embranchement : vérifie une condition
                    if (point.distanceSquared(position) <= squaredRange) query.accept(entity);
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Multiple chunks
            // Affecte une valeur
            final int chunkRange = (int) (range / Chunk.CHUNK_SECTION_SIZE) + 1;
            // Début d'une méthode/d'un bloc
            ChunkRange.chunksInRange(point, chunkRange, (chunkX, chunkZ) -> {
                // Appelle une méthode
                final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                // Embranchement : vérifie une condition
                if (chunkEntities == null || chunkEntities.isEmpty()) return;
                // Début d'une méthode/d'un bloc
                chunkEntities.forEach(entity -> {
                    // Appelle une méthode
                    final Point position = entriesByEntityId.get(entity.getEntityId()).getLastPosition();
                    // Embranchement : vérifie une condition
                    if (point.distanceSquared(position) <= squaredRange) {
                        // Appelle une méthode
                        query.accept(entity);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @UnmodifiableView <T extends Entity> Set<T> entities(Target<T> target) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (Set<T>) targetEntries[target.ordinal()].entitiesView;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Viewable viewable(List<SharedInstance> sharedInstances, int chunkX, int chunkZ) {
        // Appelle une méthode
        var entry = targetEntries[Target.PLAYERS.ordinal()];
        // Renvoie une valeur à l'appelant
        return entry.viewers.computeIfAbsent(new ChunkViewKey(sharedInstances, chunkX, chunkZ), ChunkView::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private static class EntityTrackerEntry {
        // Instruction de code
        private final Entity entity;
        // Instruction de code
        private Point lastPosition;

        // Début d'une méthode/d'un bloc
        private EntityTrackerEntry(Entity entity, @Nullable Point lastPosition) {
            // Accès à l'objet courant/parent
            this.entity = entity;
            // Accès à l'objet courant/parent
            this.lastPosition = lastPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Entity getEntity() {
            // Renvoie une valeur à l'appelant
            return entity;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Nullable
        // Début d'une méthode/d'un bloc
        public Point getLastPosition() {
            // Renvoie une valeur à l'appelant
            return lastPosition;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void setLastPosition(Point lastPosition) {
            // Accès à l'objet courant/parent
            this.lastPosition = lastPosition;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private <T extends Entity> void difference(Point oldPoint, Point newPoint,
                                               // Début d'une méthode/d'un bloc
                                               Target<T> target, Update<T> update) {
        // Appelle une méthode
        final TargetEntry<Entity> entry = targetEntries[target.ordinal()];
        // Instruction de code
        ChunkRange.chunksInRangeDiffering(newPoint.chunkX(), newPoint.chunkZ(), oldPoint.chunkX(), oldPoint.chunkZ(),
                // Début d'une méthode/d'un bloc
                ServerFlag.ENTITY_VIEW_DISTANCE, (chunkX, chunkZ) -> {
                    // Add
                    // Appelle une méthode
                    final List<Entity> entities = entry.chunkEntities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                    // Embranchement : vérifie une condition
                    if (entities == null || entities.isEmpty()) return;
                    // Boucle : répète un bloc
                    for (Entity entity : entities) update.add((T) entity);
                // Début d'une méthode/d'un bloc
                }, (chunkX, chunkZ) -> {
                    // Remove
                    // Appelle une méthode
                    final List<Entity> entities = entry.chunkEntities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                    // Embranchement : vérifie une condition
                    if (entities == null || entities.isEmpty()) return;
                    // Boucle : répète un bloc
                    for (Entity entity : entities) update.remove((T) entity);
                // Fin d'un bloc/d'une expression
                });
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ChunkViewKey(List<SharedInstance> sharedInstances, int chunkX, int chunkZ) {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean equals(Object obj) {
            // Embranchement : vérifie une condition
            if (this == obj) return true;
            // Embranchement : vérifie une condition
            if (!(obj instanceof ChunkViewKey key)) return false;
            // Renvoie une valeur à l'appelant
            return sharedInstances == key.sharedInstances &&
                    // Instruction de code
                    chunkX == key.chunkX &&
                    // Instruction de code
                    chunkZ == key.chunkZ;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class TargetEntry<T extends Entity> {
        // Instruction de code
        private final EntityTracker.Target<T> target;
        // Affecte une valeur
        private final Set<T> entities = ConcurrentHashMap.newKeySet(); // Thread-safe since exposed
        // Appelle une méthode
        private final Set<T> entitiesView = Collections.unmodifiableSet(entities);
        // Chunk index -> entities inside it
        // Appelle une méthode
        final Long2ObjectSyncMap<List<T>> chunkEntities = Long2ObjectSyncMap.hashmap();
        // Affecte une valeur
        final Map<ChunkViewKey, ChunkView> viewers = new ConcurrentHashMap<>();

        // Début d'une méthode/d'un bloc
        TargetEntry(Target<T> target) {
            // Accès à l'objet courant/parent
            this.target = target;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        List<T> chunkEntities(long index) {
            // Renvoie une valeur à l'appelant
            return chunkEntities.computeIfAbsent(index, i -> (List<T>) new CopyOnWriteArrayList());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void addToChunk(long index, T entity) {
            // Appelle une méthode
            chunkEntities(index).add(entity);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void removeFromChunk(long index, T entity) {
            // Appelle une méthode
            List<T> entities = chunkEntities.get(index);
            // Embranchement : vérifie une condition
            if (entities != null) entities.remove(entity);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private final class ChunkView implements Viewable {
        // Instruction de code
        private final ChunkViewKey key;
        // Instruction de code
        private final int chunkX, chunkZ;
        // Instruction de code
        private final Point point;
        // Appelle une méthode
        final Set<Player> set = new SetImpl();
        // Instruction de code
        private int lastReferenceCount;

        // Début d'une méthode/d'un bloc
        private ChunkView(ChunkViewKey key) {
            // Accès à l'objet courant/parent
            this.key = key;

            // Accès à l'objet courant/parent
            this.chunkX = key.chunkX;
            // Accès à l'objet courant/parent
            this.chunkZ = key.chunkZ;

            // Accès à l'objet courant/parent
            this.point = new Vec(CHUNK_SIZE_X * chunkX, 0, CHUNK_SIZE_Z * chunkZ);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean addViewer(Player player) {
            // Lève une exception
            throw new UnsupportedOperationException("Chunk does not support manual viewers");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean removeViewer(Player player) {
            // Lève une exception
            throw new UnsupportedOperationException("Chunk does not support manual viewers");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Set<Player> getViewers() {
            // Renvoie une valeur à l'appelant
            return set;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private Collection<Player> references() {
            // Affecte une valeur
            Int2ObjectOpenHashMap<Player> entityMap = new Int2ObjectOpenHashMap<>(lastReferenceCount);
            // Appelle une méthode
            collectPlayers(EntityTrackerImpl.this, entityMap);
            // Embranchement : vérifie une condition
            if (!key.sharedInstances.isEmpty()) {
                // Boucle : répète un bloc
                for (SharedInstance instance : key.sharedInstances) {
                    // Appelle une méthode
                    collectPlayers(instance.getEntityTracker(), entityMap);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.lastReferenceCount = entityMap.size();
            // Renvoie une valeur à l'appelant
            return entityMap.values();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void collectPlayers(EntityTracker tracker, Int2ObjectOpenHashMap<Player> map) {
            // Instruction de code
            tracker.nearbyEntitiesByChunkRange(point, ServerFlag.CHUNK_VIEW_DISTANCE,
                    // Appelle une méthode
                    EntityTracker.Target.PLAYERS, (player) -> map.putIfAbsent(player.getEntityId(), player));
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        final class SetImpl extends AbstractSet<Player> {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Iterator<Player> iterator() {
                // Renvoie une valeur à l'appelant
                return references().iterator();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int size() {
                // Renvoie une valeur à l'appelant
                return references().size();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void forEach(Consumer<? super Player> action) {
                // Appelle une méthode
                references().forEach(action);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
