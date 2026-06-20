// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntIterator;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntSet;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.EntityTracker;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Déclaration de type (classe/interface/enum/record)
final class EntityView {
    // Affecte une valeur
    private static final int RANGE = ServerFlag.ENTITY_VIEW_DISTANCE;
    // Instruction de code
    private final Entity entity;
    // Appelle une méthode
    private final Set<Player> manualViewers = new HashSet<>();

    // Decide if this entity should be viewable to X players
    // Instruction de code
    public final Option<Player> viewableOption;
    // Decide if this entity should view X entities
    // Instruction de code
    public final Option<Entity> viewerOption;

    // Appelle une méthode
    final Set<Player> set = new SetImpl();
    // Affecte une valeur
    private final Object mutex = this;

    // Instruction de code
    private volatile @Nullable TrackedLocation trackedLocation;

    // Début d'une méthode/d'un bloc
    public EntityView(Entity entity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.viewableOption = new Option<>(EntityTracker.Target.PLAYERS, Entity::autoViewEntities,
                // Instruction de code
                player -> showEntityToPlayer(this.entity, player),
                // Instruction de code
                player -> hideEntityFromPlayer(this.entity, player)
        // Fin d'un bloc/d'une expression
        );
        // Accès à l'objet courant/parent
        this.viewerOption = new Option<>(EntityTracker.Target.ENTITIES, Entity::isAutoViewable,
                // Instruction de code
                entity instanceof Player player ? e -> e.viewEngine.viewableOption.addition.accept(player) : null,
                // Appelle une méthode
                entity instanceof Player player ? e -> e.viewEngine.viewableOption.removal.accept(player) : null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void showEntityToPlayer(Entity entity, Player player) {
        // Collects the chain of entities, including the vehicle and all passengers, that should be visible to the player.
        // Appelle une méthode
        List<Entity> visibleChain = new ArrayList<>();
        // Appelle une méthode
        collectEntityChain(entity, player, visibleChain);

        // Embranchement : vérifie une condition
        if (visibleChain.isEmpty()) return;

        // Send spawn packets
        // Boucle : répète un bloc
        for (Entity e : visibleChain) {
            // Appelle une méthode
            e.updateNewViewer(player);
        // Fin d'un bloc/d'une expression
        }

        // Send passenger packets (in reverse order)
        // Boucle : répète un bloc
        for (int i = visibleChain.size() - 1; i >= 0; i--) {
            // Appelle une méthode
            Entity e = visibleChain.get(i);
            // Embranchement : vérifie une condition
            if (e.hasPassenger() && e.getPassengers().stream().anyMatch(visibleChain::contains)) {
                // Appelle une méthode
                player.sendPacket(e.getPassengersPacket());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void collectEntityChain(Entity entity, Player player, List<Entity> chain) {
        // Appelle une méthode
        var lock1 = player.getEntityId() < entity.getEntityId() ? player : entity;
        // Affecte une valeur
        var lock2 = lock1 == entity ? player : entity;
        // Affecte une valeur
        boolean shouldAdd = false;
        // Début d'une méthode/d'un bloc
        synchronized (lock1.viewEngine.mutex) {
            // Début d'une méthode/d'un bloc
            synchronized (lock2.viewEngine.mutex) {
                // Embranchement : vérifie une condition
                if (!entity.isViewer(player) &&
                        // Instruction de code
                        player.getVehicle() != entity &&
                        // Instruction de code
                        entity.viewEngine.viewableOption.predicate(player) &&
                        // Début d'une méthode/d'un bloc
                        player.viewEngine.viewerOption.predicate(entity)) {

                    // Appelle une méthode
                    entity.viewEngine.viewableOption.register(player);
                    // Appelle une méthode
                    player.viewEngine.viewerOption.register(entity);
                    // Affecte une valeur
                    shouldAdd = true;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (shouldAdd) {
            // Appelle une méthode
            chain.add(entity);
            // Boucle : répète un bloc
            for (Entity passenger : entity.getPassengers()) {
                // Appelle une méthode
                collectEntityChain(passenger, player, chain);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void hideEntityFromPlayer(Entity entity, Player player) {
        // Appelle une méthode
        var lock1 = player.getEntityId() < entity.getEntityId() ? player : entity;
        // Affecte une valeur
        var lock2 = lock1 == entity ? player : entity;
        // Début d'une méthode/d'un bloc
        synchronized (lock1.viewEngine.mutex) {
            // Début d'une méthode/d'un bloc
            synchronized (lock2.viewEngine.mutex) {
                // Appelle une méthode
                entity.viewEngine.viewableOption.unregister(player);
                // Appelle une méthode
                player.viewEngine.viewerOption.unregister(entity);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        entity.updateOldViewer(player);
        // Appelle une méthode
        final Set<Entity> passengers = entity.getPassengers();
        // Embranchement : vérifie une condition
        if (!passengers.isEmpty()) {
            // Boucle : répète un bloc
            for (Entity passenger : passengers) {
                // Embranchement : vérifie une condition
                if (passenger != player) hideEntityFromPlayer(passenger, player);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateTracker(@Nullable Instance instance, Point point) {
        // Accès à l'objet courant/parent
        this.trackedLocation = instance != null ? new TrackedLocation(instance, point) : null;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TrackedLocation(Instance instance, Point point) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean manualAdd(Player player) {
        // Embranchement : vérifie une condition
        if (player == this.entity) return false;
        // Début d'une méthode/d'un bloc
        synchronized (mutex) {
            // Embranchement : vérifie une condition
            if (manualViewers.add(player)) {
                // Appelle une méthode
                viewableOption.bitSet.add(player.getEntityId());
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean manualRemove(Player player) {
        // Embranchement : vérifie une condition
        if (player == this.entity) return false;
        // Début d'une méthode/d'un bloc
        synchronized (mutex) {
            // Embranchement : vérifie une condition
            if (manualViewers.remove(player)) {
                // Appelle une méthode
                viewableOption.bitSet.remove(player.getEntityId());
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void forManuals(Consumer<? super Player> consumer) {
        // Début d'une méthode/d'un bloc
        synchronized (mutex) {
            // Appelle une méthode
            Set<Player> manualViewersCopy = Set.copyOf(this.manualViewers);
            // Appelle une méthode
            manualViewersCopy.forEach(consumer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasPredictableViewers() {
        // Verify if this entity's viewers can be predicted from surrounding entities
        // Début d'une méthode/d'un bloc
        synchronized (mutex) {
            // Renvoie une valeur à l'appelant
            return viewableOption.isAuto() && viewableOption.predicate == null && manualViewers.isEmpty();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void handleAutoViewAddition(Entity entity) {
        // Appelle une méthode
        handleAutoView(entity, viewerOption.addition, viewableOption.addition);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void handleAutoViewRemoval(Entity entity) {
        // Appelle une méthode
        handleAutoView(entity, viewerOption.removal, viewableOption.removal);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleAutoView(Entity entity, @Nullable Consumer<Entity> viewer, @Nullable Consumer<Player> viewable) {
        // Embranchement : vérifie une condition
        if (this.entity instanceof Player && viewerOption.isAuto() && entity.isAutoViewable()) {
            // Embranchement : vérifie une condition
            if (viewer != null) viewer.accept(entity); // Send packet to this player
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (entity instanceof Player player && player.autoViewEntities() && viewableOption.isAuto()) {
            // Embranchement : vérifie une condition
            if (viewable != null) viewable.accept(player); // Send packet to the range-visible player
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public final class Option<T extends Entity> {
        // Annotation pour l'élément suivant
        @SuppressWarnings("rawtypes")
        // Appelle une méthode
        private static final AtomicIntegerFieldUpdater<EntityView.Option> UPDATER = AtomicIntegerFieldUpdater.newUpdater(EntityView.Option.class, "auto");
        // Entities that should be tracked from this option
        // Instruction de code
        private final EntityTracker.Target<T> target;
        // The condition that must be met for this option to be considered auto.
        // Instruction de code
        private final Predicate<T> loopPredicate;
        // The consumers to be called when an entity is added/removed.
        // Instruction de code
        public final @Nullable Consumer<T> addition, removal;
        // Contains all the auto-entity ids that are viewable by this option.
        // Appelle une méthode
        public final IntSet bitSet = new IntOpenHashSet();
        // 1 if auto, 0 if manual
        // Affecte une valeur
        private volatile int auto = 1;
        // The custom rule used to determine if an entity is viewable.
        // null if auto-viewable
        // Affecte une valeur
        private @Nullable Predicate<? super T> predicate = null;

        // Instruction de code
        public Option(EntityTracker.Target<T> target, Predicate<T> loopPredicate,
                      // Annotation pour l'élément suivant
                      @Nullable Consumer<T> addition, @Nullable Consumer<T> removal) {
            // Accès à l'objet courant/parent
            this.target = target;
            // Accès à l'objet courant/parent
            this.loopPredicate = loopPredicate;
            // Accès à l'objet courant/parent
            this.addition = addition;
            // Accès à l'objet courant/parent
            this.removal = removal;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isAuto() {
            // Renvoie une valeur à l'appelant
            return auto == 1;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean predicate(T entity) {
            // Affecte une valeur
            final Predicate<? super T> predicate = this.predicate;
            // Renvoie une valeur à l'appelant
            return predicate == null || predicate.test(entity);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isRegistered(T entity) {
            // Renvoie une valeur à l'appelant
            return bitSet.contains(entity.getEntityId());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void register(T entity) {
            // Appelle une méthode
            assert entity.getInstance() != null : "Instance-less entity shouldn't be registered as viewer";
            // Accès à l'objet courant/parent
            this.bitSet.add(entity.getEntityId());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void unregister(T entity) {
            // Accès à l'objet courant/parent
            this.bitSet.remove(entity.getEntityId());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void updateAuto(boolean autoViewable) {
            // Appelle une méthode
            final boolean previous = UPDATER.getAndSet(this, autoViewable ? 1 : 0) == 1;
            // Embranchement : vérifie une condition
            if (previous != autoViewable) {
                // Début d'une méthode/d'un bloc
                synchronized (mutex) {
                    // Embranchement : vérifie une condition
                    if (autoViewable) update(loopPredicate, addition);
                    // Branche alternative de la condition
                    else update(this::isRegistered, removal);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void updateRule(@Nullable Predicate<? super T> predicate) {
            // Début d'une méthode/d'un bloc
            synchronized (mutex) {
                // Accès à l'objet courant/parent
                this.predicate = predicate;
                // Appelle une méthode
                updateRule0(predicate);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void updateRule() {
            // Début d'une méthode/d'un bloc
            synchronized (mutex) {
                // Appelle une méthode
                updateRule0(predicate);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void updateRule0(@Nullable Predicate<? super T> predicate) {
            // Embranchement : vérifie une condition
            if (predicate == null) {
                // Début d'une méthode/d'un bloc
                update(loopPredicate, entity -> {
                    // Embranchement : vérifie une condition
                    if (!isRegistered(entity)) addition.accept(entity);
                // Fin d'un bloc/d'une expression
                });
            // Branche alternative de la condition
            } else {
                // Début d'une méthode/d'un bloc
                update(loopPredicate, entity -> {
                    // Appelle une méthode
                    final boolean result = predicate.test(entity);
                    // Embranchement : vérifie une condition
                    if (result != isRegistered(entity)) {
                        // Embranchement : vérifie une condition
                        if (result) addition.accept(entity);
                        // Branche alternative de la condition
                        else removal.accept(entity);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private void update(Predicate<? super T> visibilityPredicate,
                            // Début d'une méthode/d'un bloc
                            Consumer<T> action) {
            // Début d'une méthode/d'un bloc
            references().forEach(entity -> {
                // Embranchement : vérifie une condition
                if (entity == EntityView.this.entity || !visibilityPredicate.test(entity)) return;
                // Embranchement : vérifie une condition
                if (entity instanceof Player player && manualViewers.contains(player)) return;
                // Embranchement : vérifie une condition
                if (entity.getVehicle() != null) return;
                // Appelle une méthode
                action.accept(entity);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private int lastSize;

        // Début d'une méthode/d'un bloc
        private Collection<T> references() {
            // Affecte une valeur
            final TrackedLocation trackedLocation = EntityView.this.trackedLocation;
            // Embranchement : vérifie une condition
            if (trackedLocation == null) return List.of();
            // Appelle une méthode
            final Instance instance = trackedLocation.instance();
            // Appelle une méthode
            final Point point = trackedLocation.point();

            // Appelle une méthode
            Int2ObjectOpenHashMap<T> entityMap = new Int2ObjectOpenHashMap<>(lastSize);
            // Instruction de code
            instance.getEntityTracker().nearbyEntitiesByChunkRange(point, RANGE, target,
                    // Appelle une méthode
                    (entity) -> entityMap.putIfAbsent(entity.getEntityId(), entity));
            // Accès à l'objet courant/parent
            this.lastSize = entityMap.size();
            // Renvoie une valeur à l'appelant
            return entityMap.values();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class SetImpl extends AbstractSet<Player> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<Player> iterator() {
            // Instruction de code
            List<Player> players;
            // Début d'une méthode/d'un bloc
            synchronized (mutex) {
                // Affecte une valeur
                var bitSet = viewableOption.bitSet;
                // Embranchement : vérifie une condition
                if (bitSet.isEmpty()) return Collections.emptyIterator();
                // Appelle une méthode
                Instance instance = entity.getInstance();
                // Embranchement : vérifie une condition
                if (instance == null) return Collections.emptyIterator();
                // Appelle une méthode
                players = new ArrayList<>(bitSet.size());
                // Boucle : répète un bloc
                for (IntIterator it = bitSet.intIterator(); it.hasNext(); ) {
                    // Appelle une méthode
                    final int id = it.nextInt();
                    // Appelle une méthode
                    final Player player = (Player) instance.getEntityById(id);
                    // Embranchement : vérifie une condition
                    if (player != null) players.add(player);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return players.iterator();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int size() {
            // Début d'une méthode/d'un bloc
            synchronized (mutex) {
                // Appelle une méthode
                Instance instance = entity.getInstance();
                // Embranchement : vérifie une condition
                if (instance == null) return 0;
                // Affecte une valeur
                int count = 0;
                // Boucle : répète un bloc
                for (IntIterator it = viewableOption.bitSet.intIterator(); it.hasNext(); ) {
                    // Embranchement : vérifie une condition
                    if (instance.getEntityById(it.nextInt()) != null) count++;
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return count;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean isEmpty() {
            // Début d'une méthode/d'un bloc
            synchronized (mutex) {
                // Renvoie une valeur à l'appelant
                return viewableOption.bitSet.isEmpty();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(Object o) {
            // Embranchement : vérifie une condition
            if (!(o instanceof Player player)) return false;
            // Début d'une méthode/d'un bloc
            synchronized (mutex) {
                // Renvoie une valeur à l'appelant
                return viewableOption.isRegistered(player);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}