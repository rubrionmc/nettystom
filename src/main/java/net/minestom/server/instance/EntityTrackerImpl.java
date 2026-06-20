// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.coordinate.ChunkRange;
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
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;
// Import of a required class
import org.jetbrains.annotations.UnmodifiableView;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;
// Import of a required class
import space.vectrix.flare.fastutil.Int2ObjectSyncMap;
// Import of a required class
import space.vectrix.flare.fastutil.Long2ObjectSyncMap;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_X;
// Static import of a member
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_Z;

// Type declaration (class/interface/enum/record)
final class EntityTrackerImpl implements EntityTracker {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityTrackerImpl.class);

    // Calls a method
    static final AtomicInteger TARGET_COUNTER = new AtomicInteger();

    // Store all data associated to a Target
    // The array index is the Target enum ordinal
    // Calls a method
    final TargetEntry<Entity>[] targetEntries = EntityTracker.Target.TARGETS.stream().map((Function<Target<?>, TargetEntry>) TargetEntry::new).toArray(TargetEntry[]::new);

    // Calls a method
    private final Int2ObjectSyncMap<EntityTrackerEntry> entriesByEntityId = Int2ObjectSyncMap.hashmap();
    // Calls a method
    private final Map<UUID, EntityTrackerEntry> entriesByEntityUuid = new ConcurrentHashMap<>();

    // Annotation for the following element
    @Override
    // Code statement
    public <T extends Entity> void register(Entity entity, Point point,
                                            // Start of a method/block
                                            Target<T> target, @Nullable Update<T> update) {
        // Calls a method
        EntityTrackerEntry newEntry = new EntityTrackerEntry(entity, point);

        // Calls a method
        EntityTrackerEntry prevEntryWithId = entriesByEntityId.putIfAbsent(entity.getEntityId(), newEntry);
        // Calls a method
        Check.isTrue(prevEntryWithId == null, "There is already an entity registered with id {0}", entity.getEntityId());
        // Calls a method
        EntityTrackerEntry prevEntryWithUuid = entriesByEntityUuid.putIfAbsent(entity.getUuid(), newEntry);
        // Calls a method
        Check.isTrue(prevEntryWithUuid == null, "There is already an entity registered with uuid {0}", entity.getUuid());

        // Calls a method
        final long index = CoordConversion.chunkIndex(point);
        // Loop: repeats a block
        for (TargetEntry<Entity> targetEntry : targetEntries) {
            // Branch: checks a condition
            if (targetEntry.target.type().isInstance(entity)) {
                // Calls a method
                targetEntry.entities.add(entity);
                // Calls a method
                targetEntry.addToChunk(index, entity);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (update != null) {
            // Calls a method
            update.referenceUpdate(point, this);
            // Start of a method/block
            nearbyEntitiesByChunkRange(point, ServerFlag.ENTITY_VIEW_DISTANCE, target, newEntity -> {
                // Branch: checks a condition
                if (newEntity == entity) return;
                // Calls a method
                update.add(newEntity);
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public <T extends Entity> void unregister(Entity entity,
                                              // Start of a method/block
                                              Target<T> target, @Nullable Update<T> update) {
        // Calls a method
        EntityTrackerEntry entry = entriesByEntityId.remove(entity.getEntityId());
        // Calls a method
        entriesByEntityUuid.remove(entity.getUuid());
        // Calls a method
        final Point point = entry == null ? null : entry.getLastPosition();
        // Branch: checks a condition
        if (point == null) return;

        // Calls a method
        final long index = CoordConversion.chunkIndex(point);
        // Loop: repeats a block
        for (TargetEntry<Entity> targetEntry : targetEntries) {
            // Branch: checks a condition
            if (targetEntry.target.type().isInstance(entity)) {
                // Calls a method
                targetEntry.entities.remove(entity);
                // Calls a method
                targetEntry.removeFromChunk(index, entity);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (update != null) {
            // Calls a method
            update.referenceUpdate(point, null);
            // Start of a method/block
            nearbyEntitiesByChunkRange(point, ServerFlag.ENTITY_VIEW_DISTANCE, target, newEntity -> {
                // Branch: checks a condition
                if (newEntity == entity) return;
                // Calls a method
                update.remove(newEntity);
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Entity getEntityById(int id) {
        // Calls a method
        EntityTrackerEntry entry = entriesByEntityId.get(id);
        // Returns a value to the caller
        return entry == null ? null : entry.getEntity();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Entity getEntityByUuid(UUID uuid) {
        // Calls a method
        EntityTrackerEntry entry = entriesByEntityUuid.get(uuid);
        // Returns a value to the caller
        return entry == null ? null : entry.getEntity();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public <T extends Entity> void move(Entity entity, Point newPoint,
                                        // Start of a method/block
                                        Target<T> target, @Nullable Update<T> update) {
        // Calls a method
        EntityTrackerEntry entry = entriesByEntityId.get(entity.getEntityId());
        // Branch: checks a condition
        if (entry == null) {
            // Calls a method
            LOGGER.warn("Attempted to move unregistered entity {} in the entity tracker", entity.getEntityId());
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        Point oldPoint = entry.getLastPosition();
        // Calls a method
        entry.setLastPosition(newPoint);
        // Branch: checks a condition
        if (oldPoint == null || oldPoint.sameChunk(newPoint)) return;
        // Calls a method
        final long oldIndex = CoordConversion.chunkIndex(oldPoint);
        // Calls a method
        final long newIndex = CoordConversion.chunkIndex(newPoint);
        // Loop: repeats a block
        for (TargetEntry<Entity> targetEntry : targetEntries) {
            // Branch: checks a condition
            if (targetEntry.target.type().isInstance(entity)) {
                // Calls a method
                targetEntry.addToChunk(newIndex, entity);
                // Calls a method
                targetEntry.removeFromChunk(oldIndex, entity);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (update != null) {
            // Start of a method/block
            difference(oldPoint, newPoint, target, new Update<>() {
                // Annotation for the following element
                @Override
                // Start of a method/block
                public void add(T added) {
                    // Branch: checks a condition
                    if (entity != added) update.add(added);
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public void remove(T removed) {
                    // Branch: checks a condition
                    if (entity != removed) update.remove(removed);
                // End of a block/expression
                }
            // End of a block/expression
            });
            // Calls a method
            update.referenceUpdate(newPoint, this);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Unmodifiable <T extends Entity> Collection<T> chunkEntities(int chunkX, int chunkZ, Target<T> target) {
        // Calls a method
        final TargetEntry<Entity> entry = targetEntries[target.ordinal()];
        //noinspection unchecked
        // Calls a method
        var chunkEntities = (List<T>) entry.chunkEntities(CoordConversion.chunkIndex(chunkX, chunkZ));
        // Returns a value to the caller
        return Collections.unmodifiableList(chunkEntities);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T extends Entity> void nearbyEntitiesByChunkRange(Point point, int chunkRange, Target<T> target, Consumer<T> query) {
        // Calls a method
        final Long2ObjectSyncMap<List<Entity>> entities = targetEntries[target.ordinal()].chunkEntities;
        // Branch: checks a condition
        if (chunkRange == 0) {
            // Single chunk
            // Calls a method
            final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(point));
            // Branch: checks a condition
            if (chunkEntities != null && !chunkEntities.isEmpty()) {
                // Calls a method
                chunkEntities.forEach(query);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Multiple chunks
            // Start of a method/block
            ChunkRange.chunksInRange(point, chunkRange, (chunkX, chunkZ) -> {
                // Calls a method
                final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                // Branch: checks a condition
                if (chunkEntities == null || chunkEntities.isEmpty()) return;
                // Calls a method
                chunkEntities.forEach(query);
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T extends Entity> void nearbyEntities(Point point, double range, Target<T> target, Consumer<T> query) {
        // Calls a method
        final Long2ObjectSyncMap<List<Entity>> entities = targetEntries[target.ordinal()].chunkEntities;
        // Calls a method
        final int minChunkX = CoordConversion.globalToChunk(point.x() - range);
        // Calls a method
        final int minChunkZ = CoordConversion.globalToChunk(point.z() - range);
        // Calls a method
        final int maxChunkX = CoordConversion.globalToChunk(point.x() + range);
        // Calls a method
        final int maxChunkZ = CoordConversion.globalToChunk(point.z() + range);
        // Assigns a value
        final double squaredRange = range * range;
        // Branch: checks a condition
        if (minChunkX == maxChunkX && minChunkZ == maxChunkZ) {
            // Single chunk
            // Calls a method
            final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(point));
            // Branch: checks a condition
            if (chunkEntities != null && !chunkEntities.isEmpty()) {
                // Start of a method/block
                chunkEntities.forEach(entity -> {
                    // Calls a method
                    final Point position = entriesByEntityId.get(entity.getEntityId()).getLastPosition();
                    // Branch: checks a condition
                    if (point.distanceSquared(position) <= squaredRange) query.accept(entity);
                // End of a block/expression
                });
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Multiple chunks
            // Calls a method
            final int chunkRange = (int) (range / Chunk.CHUNK_SECTION_SIZE) + 1;
            // Start of a method/block
            ChunkRange.chunksInRange(point, chunkRange, (chunkX, chunkZ) -> {
                // Calls a method
                final var chunkEntities = (List<T>) entities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                // Branch: checks a condition
                if (chunkEntities == null || chunkEntities.isEmpty()) return;
                // Start of a method/block
                chunkEntities.forEach(entity -> {
                    // Calls a method
                    final Point position = entriesByEntityId.get(entity.getEntityId()).getLastPosition();
                    // Branch: checks a condition
                    if (point.distanceSquared(position) <= squaredRange) {
                        // Calls a method
                        query.accept(entity);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @UnmodifiableView <T extends Entity> Set<T> entities(Target<T> target) {
        //noinspection unchecked
        // Returns a value to the caller
        return (Set<T>) targetEntries[target.ordinal()].entitiesView;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Viewable viewable(List<SharedInstance> sharedInstances, int chunkX, int chunkZ) {
        // Calls a method
        var entry = targetEntries[Target.PLAYERS.ordinal()];
        // Returns a value to the caller
        return entry.viewers.computeIfAbsent(new ChunkViewKey(sharedInstances, chunkX, chunkZ), ChunkView::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private static class EntityTrackerEntry {
        // Code statement
        private final Entity entity;
        // Code statement
        private Point lastPosition;

        // Start of a method/block
        private EntityTrackerEntry(Entity entity, @Nullable Point lastPosition) {
            // Access to the current/parent object
            this.entity = entity;
            // Access to the current/parent object
            this.lastPosition = lastPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public Entity getEntity() {
            // Returns a value to the caller
            return entity;
        // End of a block/expression
        }

        // Annotation for the following element
        @Nullable
        // Start of a method/block
        public Point getLastPosition() {
            // Returns a value to the caller
            return lastPosition;
        // End of a block/expression
        }

        // Start of a method/block
        public void setLastPosition(Point lastPosition) {
            // Access to the current/parent object
            this.lastPosition = lastPosition;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private <T extends Entity> void difference(Point oldPoint, Point newPoint,
                                               // Start of a method/block
                                               Target<T> target, Update<T> update) {
        // Calls a method
        final TargetEntry<Entity> entry = targetEntries[target.ordinal()];
        // Code statement
        ChunkRange.chunksInRangeDiffering(newPoint.chunkX(), newPoint.chunkZ(), oldPoint.chunkX(), oldPoint.chunkZ(),
                // Start of a method/block
                ServerFlag.ENTITY_VIEW_DISTANCE, (chunkX, chunkZ) -> {
                    // Add
                    // Calls a method
                    final List<Entity> entities = entry.chunkEntities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                    // Branch: checks a condition
                    if (entities == null || entities.isEmpty()) return;
                    // Loop: repeats a block
                    for (Entity entity : entities) update.add((T) entity);
                // Start of a method/block
                }, (chunkX, chunkZ) -> {
                    // Remove
                    // Calls a method
                    final List<Entity> entities = entry.chunkEntities.get(CoordConversion.chunkIndex(chunkX, chunkZ));
                    // Branch: checks a condition
                    if (entities == null || entities.isEmpty()) return;
                    // Loop: repeats a block
                    for (Entity entity : entities) update.remove((T) entity);
                // End of a block/expression
                });
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ChunkViewKey(List<SharedInstance> sharedInstances, int chunkX, int chunkZ) {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean equals(Object obj) {
            // Branch: checks a condition
            if (this == obj) return true;
            // Branch: checks a condition
            if (!(obj instanceof ChunkViewKey(List<SharedInstance> instances, int x, int z))) return false;
            // Returns a value to the caller
            return sharedInstances == instances &&
                    // Code statement
                    chunkX == x &&
                    // Code statement
                    chunkZ == z;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class TargetEntry<T extends Entity> {
        // Code statement
        private final EntityTracker.Target<T> target;
        // Assigns a value
        private final Set<T> entities = ConcurrentHashMap.newKeySet(); // Thread-safe since exposed
        // Calls a method
        private final Set<T> entitiesView = Collections.unmodifiableSet(entities);
        // Chunk index -> entities inside it
        // Calls a method
        final Long2ObjectSyncMap<List<T>> chunkEntities = Long2ObjectSyncMap.hashmap();
        // Calls a method
        final Map<ChunkViewKey, ChunkView> viewers = new ConcurrentHashMap<>();

        // Start of a method/block
        TargetEntry(Target<T> target) {
            // Access to the current/parent object
            this.target = target;
        // End of a block/expression
        }

        // Start of a method/block
        List<T> chunkEntities(long index) {
            // Returns a value to the caller
            return chunkEntities.computeIfAbsent(index, i -> (List<T>) new CopyOnWriteArrayList());
        // End of a block/expression
        }

        // Start of a method/block
        void addToChunk(long index, T entity) {
            // Calls a method
            chunkEntities(index).add(entity);
        // End of a block/expression
        }

        // Start of a method/block
        void removeFromChunk(long index, T entity) {
            // Calls a method
            List<T> entities = chunkEntities.get(index);
            // Branch: checks a condition
            if (entities != null) entities.remove(entity);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private final class ChunkView implements Viewable {
        // Code statement
        private final ChunkViewKey key;
        // Code statement
        private final int chunkX, chunkZ;
        // Code statement
        private final Point point;
        // Calls a method
        final Set<Player> set = new SetImpl();
        // Code statement
        private int lastReferenceCount;

        // Start of a method/block
        private ChunkView(ChunkViewKey key) {
            // Access to the current/parent object
            this.key = key;

            // Access to the current/parent object
            this.chunkX = key.chunkX;
            // Access to the current/parent object
            this.chunkZ = key.chunkZ;

            // Access to the current/parent object
            this.point = new Vec(CHUNK_SIZE_X * chunkX, 0, CHUNK_SIZE_Z * chunkZ);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean addViewer(Player player) {
            // Throws an exception
            throw new UnsupportedOperationException("Chunk does not support manual viewers");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean removeViewer(Player player) {
            // Throws an exception
            throw new UnsupportedOperationException("Chunk does not support manual viewers");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Set<? extends Player> getViewers() {
            // Returns a value to the caller
            return set;
        // End of a block/expression
        }

        // Start of a method/block
        private Collection<Player> references() {
            // Calls a method
            Int2ObjectOpenHashMap<Player> entityMap = new Int2ObjectOpenHashMap<>(lastReferenceCount);
            // Calls a method
            collectPlayers(EntityTrackerImpl.this, entityMap);
            // Branch: checks a condition
            if (!key.sharedInstances.isEmpty()) {
                // Loop: repeats a block
                for (SharedInstance instance : key.sharedInstances) {
                    // Calls a method
                    collectPlayers(instance.getEntityTracker(), entityMap);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Access to the current/parent object
            this.lastReferenceCount = entityMap.size();
            // Returns a value to the caller
            return entityMap.values();
        // End of a block/expression
        }

        // Start of a method/block
        private void collectPlayers(EntityTracker tracker, Int2ObjectOpenHashMap<Player> map) {
            // Code statement
            tracker.nearbyEntitiesByChunkRange(point, ServerFlag.CHUNK_VIEW_DISTANCE,
                    // Calls a method
                    EntityTracker.Target.PLAYERS, (player) -> map.putIfAbsent(player.getEntityId(), player));
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        final class SetImpl extends AbstractSet<Player> {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Iterator<Player> iterator() {
                // Returns a value to the caller
                return references().iterator();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public int size() {
                // Returns a value to the caller
                return references().size();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void forEach(Consumer<? super Player> action) {
                // Calls a method
                references().forEach(action);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
