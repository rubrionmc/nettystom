// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntIterator;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntSet;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.EntityTracker;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Predicate;

// Type declaration (class/interface/enum/record)
final class EntityView {
    // Assigns a value
    private static final int RANGE = ServerFlag.ENTITY_VIEW_DISTANCE;
    // Code statement
    private final Entity entity;
    // Calls a method
    private final Set<Player> manualViewers = new HashSet<>();

    // Decide if this entity should be viewable to X players
    // Code statement
    public final Option<Player> viewableOption;
    // Decide if this entity should view X entities
    // Code statement
    public final Option<Entity> viewerOption;

    // Calls a method
    final Set<Player> set = new SetImpl();
    // Assigns a value
    private final Object mutex = this;

    // Code statement
    private volatile @Nullable TrackedLocation trackedLocation;

    // Start of a method/block
    public EntityView(Entity entity) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.viewableOption = new Option<>(EntityTracker.Target.PLAYERS, Entity::autoViewEntities,
                // Code statement
                player -> showEntityToPlayer(this.entity, player),
                // Code statement
                player -> hideEntityFromPlayer(this.entity, player)
        // End of a block/expression
        );
        // Access to the current/parent object
        this.viewerOption = new Option<>(EntityTracker.Target.ENTITIES, Entity::isAutoViewable,
                // Code statement
                entity instanceof Player player ? e -> e.viewEngine.viewableOption.addition.accept(player) : null,
                // Calls a method
                entity instanceof Player player ? e -> e.viewEngine.viewableOption.removal.accept(player) : null);
    // End of a block/expression
    }

    // Start of a method/block
    private static void showEntityToPlayer(Entity entity, Player player) {
        // Collects the chain of entities, including the vehicle and all passengers, that should be visible to the player.
        // Calls a method
        List<Entity> visibleChain = new ArrayList<>();
        // Calls a method
        collectEntityChain(entity, player, visibleChain);

        // Branch: checks a condition
        if (visibleChain.isEmpty()) return;

        // Send spawn packets
        // Loop: repeats a block
        for (Entity e : visibleChain) {
            // Calls a method
            e.updateNewViewer(player);
        // End of a block/expression
        }

        // Send passenger packets (in reverse order)
        // Loop: repeats a block
        for (int i = visibleChain.size() - 1; i >= 0; i--) {
            // Calls a method
            Entity e = visibleChain.get(i);
            // Branch: checks a condition
            if (e.hasPassenger() && e.getPassengers().stream().anyMatch(visibleChain::contains)) {
                // Calls a method
                player.sendPacket(e.getPassengersPacket());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void collectEntityChain(Entity entity, Player player, List<Entity> chain) {
        // Calls a method
        var lock1 = player.getEntityId() < entity.getEntityId() ? player : entity;
        // Assigns a value
        var lock2 = lock1 == entity ? player : entity;
        // Assigns a value
        boolean shouldAdd = false;
        // Start of a method/block
        synchronized (lock1.viewEngine.mutex) {
            // Start of a method/block
            synchronized (lock2.viewEngine.mutex) {
                // Branch: checks a condition
                if (!entity.isViewer(player) &&
                        // Code statement
                        player.getVehicle() != entity &&
                        // Code statement
                        entity.viewEngine.viewableOption.predicate(player) &&
                        // Start of a method/block
                        player.viewEngine.viewerOption.predicate(entity)) {

                    // Calls a method
                    entity.viewEngine.viewableOption.register(player);
                    // Calls a method
                    player.viewEngine.viewerOption.register(entity);
                    // Assigns a value
                    shouldAdd = true;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (shouldAdd) {
            // Calls a method
            chain.add(entity);
            // Loop: repeats a block
            for (Entity passenger : entity.getPassengers()) {
                // Calls a method
                collectEntityChain(passenger, player, chain);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void hideEntityFromPlayer(Entity entity, Player player) {
        // Calls a method
        var lock1 = player.getEntityId() < entity.getEntityId() ? player : entity;
        // Assigns a value
        var lock2 = lock1 == entity ? player : entity;
        // Start of a method/block
        synchronized (lock1.viewEngine.mutex) {
            // Start of a method/block
            synchronized (lock2.viewEngine.mutex) {
                // Calls a method
                entity.viewEngine.viewableOption.unregister(player);
                // Calls a method
                player.viewEngine.viewerOption.unregister(entity);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        entity.updateOldViewer(player);
        // Calls a method
        final Set<Entity> passengers = entity.getPassengers();
        // Branch: checks a condition
        if (!passengers.isEmpty()) {
            // Loop: repeats a block
            for (Entity passenger : passengers) {
                // Branch: checks a condition
                if (passenger != player) hideEntityFromPlayer(passenger, player);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void updateTracker(@Nullable Instance instance, Point point) {
        // Access to the current/parent object
        this.trackedLocation = instance != null ? new TrackedLocation(instance, point) : null;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TrackedLocation(Instance instance, Point point) {
    // End of a block/expression
    }

    // Start of a method/block
    public boolean manualAdd(Player player) {
        // Branch: checks a condition
        if (player == this.entity) return false;
        // Start of a method/block
        synchronized (mutex) {
            // Branch: checks a condition
            if (manualViewers.add(player)) {
                // Calls a method
                viewableOption.bitSet.add(player.getEntityId());
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public boolean manualRemove(Player player) {
        // Branch: checks a condition
        if (player == this.entity) return false;
        // Start of a method/block
        synchronized (mutex) {
            // Branch: checks a condition
            if (manualViewers.remove(player)) {
                // Calls a method
                viewableOption.bitSet.remove(player.getEntityId());
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void forManuals(Consumer<? super Player> consumer) {
        // Start of a method/block
        synchronized (mutex) {
            // Calls a method
            Set<Player> manualViewersCopy = Set.copyOf(this.manualViewers);
            // Calls a method
            manualViewersCopy.forEach(consumer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasPredictableViewers() {
        // Verify if this entity's viewers can be predicted from surrounding entities
        // Start of a method/block
        synchronized (mutex) {
            // Returns a value to the caller
            return viewableOption.isAuto() && viewableOption.predicate == null && manualViewers.isEmpty();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void handleAutoViewAddition(Entity entity) {
        // Calls a method
        handleAutoView(entity, viewerOption.addition, viewableOption.addition);
    // End of a block/expression
    }

    // Start of a method/block
    public void handleAutoViewRemoval(Entity entity) {
        // Calls a method
        handleAutoView(entity, viewerOption.removal, viewableOption.removal);
    // End of a block/expression
    }

    // Start of a method/block
    private void handleAutoView(Entity entity, @Nullable Consumer<Entity> viewer, @Nullable Consumer<Player> viewable) {
        // Branch: checks a condition
        if (this.entity instanceof Player && viewerOption.isAuto() && entity.isAutoViewable()) {
            // Branch: checks a condition
            if (viewer != null) viewer.accept(entity); // Send packet to this player
        // End of a block/expression
        }
        // Branch: checks a condition
        if (entity instanceof Player player && player.autoViewEntities() && viewableOption.isAuto()) {
            // Branch: checks a condition
            if (viewable != null) viewable.accept(player); // Send packet to the range-visible player
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public final class Option<T extends Entity> {
        // Annotation for the following element
        @SuppressWarnings("rawtypes")
        // Calls a method
        private static final AtomicIntegerFieldUpdater<EntityView.Option> UPDATER = AtomicIntegerFieldUpdater.newUpdater(EntityView.Option.class, "auto");
        // Entities that should be tracked from this option
        // Code statement
        private final EntityTracker.Target<T> target;
        // The condition that must be met for this option to be considered auto.
        // Code statement
        private final Predicate<T> loopPredicate;
        // The consumers to be called when an entity is added/removed.
        // Code statement
        public final @Nullable Consumer<T> addition, removal;
        // Contains all the auto-entity ids that are viewable by this option.
        // Calls a method
        public final IntSet bitSet = new IntOpenHashSet();
        // 1 if auto, 0 if manual
        // Assigns a value
        private volatile int auto = 1;
        // The custom rule used to determine if an entity is viewable.
        // null if auto-viewable
        // Assigns a value
        private @Nullable Predicate<? super T> predicate = null;

        // Code statement
        public Option(EntityTracker.Target<T> target, Predicate<T> loopPredicate,
                      // Annotation for the following element
                      @Nullable Consumer<T> addition, @Nullable Consumer<T> removal) {
            // Access to the current/parent object
            this.target = target;
            // Access to the current/parent object
            this.loopPredicate = loopPredicate;
            // Access to the current/parent object
            this.addition = addition;
            // Access to the current/parent object
            this.removal = removal;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isAuto() {
            // Returns a value to the caller
            return auto == 1;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean predicate(T entity) {
            // Assigns a value
            final Predicate<? super T> predicate = this.predicate;
            // Returns a value to the caller
            return predicate == null || predicate.test(entity);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isRegistered(T entity) {
            // Returns a value to the caller
            return bitSet.contains(entity.getEntityId());
        // End of a block/expression
        }

        // Start of a method/block
        public void register(T entity) {
            // Calls a method
            assert entity.getInstance() != null : "Instance-less entity shouldn't be registered as viewer";
            // Access to the current/parent object
            this.bitSet.add(entity.getEntityId());
        // End of a block/expression
        }

        // Start of a method/block
        public void unregister(T entity) {
            // Access to the current/parent object
            this.bitSet.remove(entity.getEntityId());
        // End of a block/expression
        }

        // Start of a method/block
        public void updateAuto(boolean autoViewable) {
            // Calls a method
            final boolean previous = UPDATER.getAndSet(this, autoViewable ? 1 : 0) == 1;
            // Branch: checks a condition
            if (previous != autoViewable) {
                // Start of a method/block
                synchronized (mutex) {
                    // Branch: checks a condition
                    if (autoViewable) update(loopPredicate, addition);
                    // Alternative branch of the condition
                    else update(this::isRegistered, removal);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public void updateRule(@Nullable Predicate<? super T> predicate) {
            // Start of a method/block
            synchronized (mutex) {
                // Access to the current/parent object
                this.predicate = predicate;
                // Calls a method
                updateRule0(predicate);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public void updateRule() {
            // Start of a method/block
            synchronized (mutex) {
                // Calls a method
                updateRule0(predicate);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        void updateRule0(@Nullable Predicate<? super T> predicate) {
            // Branch: checks a condition
            if (predicate == null) {
                // Start of a method/block
                update(loopPredicate, entity -> {
                    // Branch: checks a condition
                    if (!isRegistered(entity)) addition.accept(entity);
                // End of a block/expression
                });
            // Alternative branch of the condition
            } else {
                // Start of a method/block
                update(loopPredicate, entity -> {
                    // Calls a method
                    final boolean result = predicate.test(entity);
                    // Branch: checks a condition
                    if (result != isRegistered(entity)) {
                        // Branch: checks a condition
                        if (result) addition.accept(entity);
                        // Alternative branch of the condition
                        else removal.accept(entity);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Code statement
        private void update(Predicate<? super T> visibilityPredicate,
                            // Start of a method/block
                            Consumer<T> action) {
            // Start of a method/block
            references().forEach(entity -> {
                // Branch: checks a condition
                if (entity == EntityView.this.entity || !visibilityPredicate.test(entity)) return;
                // Branch: checks a condition
                if (entity instanceof Player player && manualViewers.contains(player)) return;
                // Branch: checks a condition
                if (entity.getVehicle() != null) return;
                // Calls a method
                action.accept(entity);
            // End of a block/expression
            });
        // End of a block/expression
        }

        // Code statement
        private int lastSize;

        // Start of a method/block
        private Collection<T> references() {
            // Assigns a value
            final TrackedLocation trackedLocation = EntityView.this.trackedLocation;
            // Branch: checks a condition
            if (trackedLocation == null) return List.of();
            // Calls a method
            final Instance instance = trackedLocation.instance();
            // Calls a method
            final Point point = trackedLocation.point();

            // Calls a method
            Int2ObjectOpenHashMap<T> entityMap = new Int2ObjectOpenHashMap<>(lastSize);
            // Code statement
            instance.getEntityTracker().nearbyEntitiesByChunkRange(point, RANGE, target,
                    // Calls a method
                    (entity) -> entityMap.putIfAbsent(entity.getEntityId(), entity));
            // Access to the current/parent object
            this.lastSize = entityMap.size();
            // Returns a value to the caller
            return entityMap.values();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class SetImpl extends AbstractSet<Player> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<Player> iterator() {
            // Code statement
            List<Player> players;
            // Start of a method/block
            synchronized (mutex) {
                // Assigns a value
                var bitSet = viewableOption.bitSet;
                // Branch: checks a condition
                if (bitSet.isEmpty()) return Collections.emptyIterator();
                // Calls a method
                Instance instance = entity.getInstance();
                // Branch: checks a condition
                if (instance == null) return Collections.emptyIterator();
                // Calls a method
                players = new ArrayList<>(bitSet.size());
                // Loop: repeats a block
                for (IntIterator it = bitSet.intIterator(); it.hasNext(); ) {
                    // Calls a method
                    final int id = it.nextInt();
                    // Calls a method
                    final Player player = (Player) instance.getEntityById(id);
                    // Branch: checks a condition
                    if (player != null) players.add(player);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return players.iterator();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int size() {
            // Start of a method/block
            synchronized (mutex) {
                // Calls a method
                Instance instance = entity.getInstance();
                // Branch: checks a condition
                if (instance == null) return 0;
                // Assigns a value
                int count = 0;
                // Loop: repeats a block
                for (IntIterator it = viewableOption.bitSet.intIterator(); it.hasNext(); ) {
                    // Branch: checks a condition
                    if (instance.getEntityById(it.nextInt()) != null) count++;
                // End of a block/expression
                }
                // Returns a value to the caller
                return count;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean isEmpty() {
            // Start of a method/block
            synchronized (mutex) {
                // Returns a value to the caller
                return viewableOption.bitSet.isEmpty();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(Object o) {
            // Branch: checks a condition
            if (!(o instanceof Player player)) return false;
            // Start of a method/block
            synchronized (mutex) {
                // Returns a value to the caller
                return viewableOption.isRegistered(player);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}