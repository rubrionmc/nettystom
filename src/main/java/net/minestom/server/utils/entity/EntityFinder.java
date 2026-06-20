// Déclaration du paquet de ce fichier
package net.minestom.server.utils.entity;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionManager;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.Range;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ThreadLocalRandom;

// TODO

/**
 * Represents a query which can be call to find one or multiple entities.
 * It is based on the target selectors used in commands.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityFinder {
    // Appelle une méthode
    private static final ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();

    // Instruction de code
    private TargetSelector targetSelector;

    // Affecte une valeur
    private EntitySort entitySort = EntitySort.ARBITRARY;

    // Position
    // Instruction de code
    private Point startPosition;
    // Instruction de code
    private Float dx, dy, dz;
    // Instruction de code
    private Range.Int distance;

    // By traits
    // Instruction de code
    private Integer limit;
    // Affecte une valeur
    private final ToggleableMap<EntityType> entityTypes = new ToggleableMap<>();
    // Instruction de code
    private String constantName;
    // Instruction de code
    private UUID constantUuid;
    // Affecte une valeur
    private final ToggleableMap<String> names = new ToggleableMap<>();
    // Affecte une valeur
    private final ToggleableMap<UUID> uuids = new ToggleableMap<>();


    // Players specific
    // Affecte une valeur
    private final ToggleableMap<GameMode> gameModes = new ToggleableMap<>();
    // Instruction de code
    private Range.Int level;

    // Début d'une méthode/d'un bloc
    public EntityFinder setTargetSelector(TargetSelector targetSelector) {
        // Accès à l'objet courant/parent
        this.targetSelector = targetSelector;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setEntitySort(EntitySort entitySort) {
        // Accès à l'objet courant/parent
        this.entitySort = entitySort;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setStartPosition(Point startPosition) {
        // Accès à l'objet courant/parent
        this.startPosition = startPosition;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setDistance(Range.Int distance) {
        // Accès à l'objet courant/parent
        this.distance = distance;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setLimit(int limit) {
        // Accès à l'objet courant/parent
        this.limit = limit;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setLevel(Range.Int level) {
        // Accès à l'objet courant/parent
        this.level = level;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setEntity(EntityType entityType, ToggleableType toggleableType) {
        // Accès à l'objet courant/parent
        this.entityTypes.put(entityType, toggleableType.getValue());
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setConstantName(String constantName) {
        // Accès à l'objet courant/parent
        this.constantName = constantName;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setConstantUuid(UUID constantUuid) {
        // Accès à l'objet courant/parent
        this.constantUuid = constantUuid;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setName(String name, ToggleableType toggleableType) {
        // Accès à l'objet courant/parent
        this.names.put(name, toggleableType.getValue());
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setUuid(UUID uuid, ToggleableType toggleableType) {
        // Accès à l'objet courant/parent
        this.uuids.put(uuid, toggleableType.getValue());
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setGameMode(GameMode gameMode, ToggleableType toggleableType) {
        // Accès à l'objet courant/parent
        this.gameModes.put(gameMode, toggleableType.getValue());
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityFinder setDifference(float dx, float dy, float dz) {
        // Accès à l'objet courant/parent
        this.dx = dx;
        // Accès à l'objet courant/parent
        this.dy = dy;
        // Accès à l'objet courant/parent
        this.dz = dz;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Find a list of entities (could be empty) based on the conditions
     *
     * @param instance the instance to search from,
     *                 null if the query can be executed using global data (all online players)
     * @param self     the source of the query, null if not any
     * @return all entities validating the conditions, can be empty
     */
    // Début d'une méthode/d'un bloc
    public List<Entity> find(@Nullable Instance instance, @Nullable Entity self) {
        // Embranchement : vérifie une condition
        if (targetSelector == TargetSelector.MINESTOM_USERNAME) {
            // Appelle une méthode
            Check.notNull(constantName, "The player name should not be null when searching for it");
            // Appelle une méthode
            final Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(constantName);
            // Renvoie une valeur à l'appelant
            return player != null ? List.of(player) : List.of();
        // Embranchement : vérifie une condition
        } else if (targetSelector == TargetSelector.MINESTOM_UUID) {
            // Appelle une méthode
            Check.notNull(constantUuid, "The UUID should not be null when searching for it");
            // Appelle une méthode
            Check.notNull(instance, "The instance should not be null when searching by UUID");
            // Appelle une méthode
            final Entity entity = instance.getEntityByUuid(constantUuid);
            // Renvoie une valeur à l'appelant
            return entity != null ? List.of(entity) : List.of();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Point pos = startPosition != null ? startPosition : (self != null ? self.getPosition() : Vec.ZERO);

        // Appelle une méthode
        List<Entity> result = findTarget(instance, targetSelector, pos, self);
        // Fast exit if there is nothing to process
        // Embranchement : vérifie une condition
        if (result.isEmpty())
            // Renvoie une valeur à l'appelant
            return result;

        // Distance argument
        // Embranchement : vérifie une condition
        if (distance != null) {
            // Appelle une méthode
            final int minDistance = distance.min();
            // Appelle une méthode
            final int maxDistance = distance.max();
            // Affecte une valeur
            result = result.stream()
                    // Instruction de code
                    .filter(entity -> MathUtils.isBetween(entity.getPosition().distanceSquared(pos), minDistance * minDistance, maxDistance * maxDistance))
                    // Appelle une méthode
                    .toList();
        // Fin d'un bloc/d'une expression
        }

        // Diff X/Y/Z
        // Embranchement : vérifie une condition
        if (dx != null || dy != null || dz != null) {
            // Affecte une valeur
            result = result.stream().filter(entity -> {
                // Appelle une méthode
                final var entityPosition = entity.getPosition();
                // Embranchement : vérifie une condition
                if (dx != null && !MathUtils.isBetweenUnordered(
                        // Instruction de code
                        entityPosition.x(),
                        // Instruction de code
                        pos.x(), dx))
                    // Renvoie une valeur à l'appelant
                    return false;

                // Embranchement : vérifie une condition
                if (dy != null && !MathUtils.isBetweenUnordered(
                        // Instruction de code
                        entityPosition.y(),
                        // Instruction de code
                        pos.y(), dy))
                    // Renvoie une valeur à l'appelant
                    return false;

                // Embranchement : vérifie une condition
                if (dz != null && !MathUtils.isBetweenUnordered(
                        // Instruction de code
                        entityPosition.z(),
                        // Instruction de code
                        pos.z(), dz))
                    // Renvoie une valeur à l'appelant
                    return false;

                // Renvoie une valeur à l'appelant
                return true;
            // Appelle une méthode
            }).toList();
        // Fin d'un bloc/d'une expression
        }

        // Entity type
        // Embranchement : vérifie une condition
        if (!entityTypes.isEmpty()) {
            // Affecte une valeur
            result = result.stream()
                    // Instruction de code
                    .filter(entity -> filterToggleableMap(entity.getEntityType(), entityTypes))
                    // Appelle une méthode
                    .toList();
        // Fin d'un bloc/d'une expression
        }

        // GameMode
        // Embranchement : vérifie une condition
        if (!gameModes.isEmpty()) {
            // Affecte une valeur
            result = result.stream()
                    // Instruction de code
                    .filter(Player.class::isInstance)
                    // Instruction de code
                    .filter(entity -> filterToggleableMap(((Player) entity).getGameMode(), gameModes))
                    // Appelle une méthode
                    .toList();
        // Fin d'un bloc/d'une expression
        }

        // Level
        // Embranchement : vérifie une condition
        if (level != null) {
            // Appelle une méthode
            final int minLevel = level.min();
            // Appelle une méthode
            final int maxLevel = level.max();
            // Affecte une valeur
            result = result.stream()
                    // Instruction de code
                    .filter(Player.class::isInstance)
                    // Instruction de code
                    .filter(entity -> MathUtils.isBetween(((Player) entity).getLevel(), minLevel, maxLevel))
                    // Appelle une méthode
                    .toList();
        // Fin d'un bloc/d'une expression
        }

        // Name
        // Embranchement : vérifie une condition
        if (!names.isEmpty()) {
            // Affecte une valeur
            result = result.stream()
                    // Instruction de code
                    .filter(Player.class::isInstance)
                    // Instruction de code
                    .filter(entity -> filterToggleableMap(((Player) entity).getUsername(), names))
                    // Appelle une méthode
                    .toList();
        // Fin d'un bloc/d'une expression
        }

        // UUID
        // Embranchement : vérifie une condition
        if (!uuids.isEmpty()) {
            // Affecte une valeur
            result = result.stream()
                    // Instruction de code
                    .filter(entity -> filterToggleableMap(entity.getUuid(), uuids))
                    // Appelle une méthode
                    .toList();
        // Fin d'un bloc/d'une expression
        }


        // Sort & limit
        // Embranchement : vérifie une condition
        if (entitySort != EntitySort.ARBITRARY || limit != null) {
            // Affecte une valeur
            result = result.stream()
                    // Début d'une méthode/d'un bloc
                    .sorted((ent1, ent2) -> switch (entitySort) {
                        // Embranchement multiple (switch/case)
                        case ARBITRARY, RANDOM ->
                            // RANDOM is handled below
                                // Instruction de code
                                1;
                        // Embranchement multiple (switch/case)
                        case FURTHEST -> pos.distanceSquared(ent1.getPosition()) >
                                // Instruction de code
                                pos.distanceSquared(ent2.getPosition()) ?
                                // Instruction de code
                                1 : 0;
                        // Embranchement multiple (switch/case)
                        case NEAREST -> pos.distanceSquared(ent1.getPosition()) <
                                // Instruction de code
                                pos.distanceSquared(ent2.getPosition()) ?
                                // Instruction de code
                                1 : 0;
                    // Instruction de code
                    })
                    // Instruction de code
                    .limit(limit != null ? limit : Integer.MAX_VALUE)
                    // Appelle une méthode
                    .toList();

            // Embranchement : vérifie une condition
            if (entitySort == EntitySort.RANDOM) {
                // Appelle une méthode
                Collections.shuffle(result);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Entity> find(CommandSender sender) {
        // Renvoie une valeur à l'appelant
        return sender instanceof Player player ?
                // Appelle une méthode
                find(player.getInstance(), player) : find(null, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shortcut of {@link #find(Instance, Entity)} to retrieve the first
     * player element in the list.
     *
     * @return the first player returned by {@link #find(Instance, Entity)}
     * @see #find(Instance, Entity)
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Player findFirstPlayer(@Nullable Instance instance, @Nullable Entity self) {
        // Appelle une méthode
        final List<Entity> entities = find(instance, self);
        // Boucle : répète un bloc
        for (Entity entity : entities) {
            // Embranchement : vérifie une condition
            if (entity instanceof Player player) {
                // Renvoie une valeur à l'appelant
                return player;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Player findFirstPlayer(CommandSender sender) {
        // Renvoie une valeur à l'appelant
        return sender instanceof Player player ?
                // Instruction de code
                findFirstPlayer(player.getInstance(), player) :
                // Appelle une méthode
                findFirstPlayer(null, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Entity findFirstEntity(@Nullable Instance instance, @Nullable Entity self) {
        // Appelle une méthode
        final List<Entity> entities = find(instance, self);
        // Renvoie une valeur à l'appelant
        return entities.isEmpty() ? null : entities.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Entity findFirstEntity(CommandSender sender) {
        // Renvoie une valeur à l'appelant
        return sender instanceof Player player ?
                // Appelle une méthode
                findFirstEntity(player.getInstance(), player) : findFirstEntity(null, null);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum TargetSelector {
        // Instruction de code
        NEAREST_PLAYER, RANDOM_PLAYER, ALL_PLAYERS, ALL_ENTITIES, SELF, MINESTOM_USERNAME, MINESTOM_UUID, NEAREST_ENTITY
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum EntitySort {
        // Instruction de code
        ARBITRARY, FURTHEST, NEAREST, RANDOM
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum ToggleableType {
        // Appelle une méthode
        INCLUDE(true), EXCLUDE(false);

        // Instruction de code
        private final boolean value;

        // Début d'une méthode/d'un bloc
        ToggleableType(boolean value) {
            // Accès à l'objet courant/parent
            this.value = value;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean getValue() {
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private static class ToggleableMap<T> extends Object2BooleanOpenHashMap<T> {
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static List<Entity> findTarget(@Nullable Instance instance,
                                                             // Instruction de code
                                                             TargetSelector targetSelector,
                                                             // Début d'une méthode/d'un bloc
                                                             Point startPosition, @Nullable Entity self) {
        // Appelle une méthode
        final var players = instance != null ? instance.getPlayers() : CONNECTION_MANAGER.getOnlinePlayers();
        // Embranchement : vérifie une condition
        if (targetSelector == TargetSelector.NEAREST_PLAYER) {
            // Renvoie une valeur à l'appelant
            return players.stream()
                    // Instruction de code
                    .min(Comparator.comparingDouble(p -> p.getPosition().distanceSquared(startPosition)))
                    // Appelle une méthode
                    .<List<Entity>>map(Collections::singletonList).orElse(List.of());
        // Embranchement : vérifie une condition
        } else if (targetSelector == TargetSelector.NEAREST_ENTITY) {
            // Appelle une méthode
            List<Entity> entities = findTarget(instance, TargetSelector.ALL_ENTITIES, startPosition, self);

            // Renvoie une valeur à l'appelant
            return entities.stream()
                    // Instruction de code
                    .min(Comparator.comparingDouble(p -> p.getPosition().distanceSquared(startPosition)))
                    // Appelle une méthode
                    .map(Collections::singletonList).orElse(List.of());
        // Embranchement : vérifie une condition
        } else if (targetSelector == TargetSelector.RANDOM_PLAYER) {
            // Appelle une méthode
            final int index = ThreadLocalRandom.current().nextInt(players.size());
            // Appelle une méthode
            final Player player = players.stream().skip(index).findFirst().orElseThrow();
            // Renvoie une valeur à l'appelant
            return List.of(player);
        // Embranchement : vérifie une condition
        } else if (targetSelector == TargetSelector.ALL_PLAYERS) {
            // Renvoie une valeur à l'appelant
            return List.copyOf(players);
        // Embranchement : vérifie une condition
        } else if (targetSelector == TargetSelector.ALL_ENTITIES) {
            // Embranchement : vérifie une condition
            if (instance != null) {
                // Renvoie une valeur à l'appelant
                return List.copyOf(instance.getEntities());
            // Fin d'un bloc/d'une expression
            }
            // Get entities from every instance
            // Appelle une méthode
            var instances = MinecraftServer.getInstanceManager().getInstances();
            // Affecte une valeur
            List<Entity> entities = new ArrayList<>();
            // Boucle : répète un bloc
            for (Instance inst : instances) {
                // Appelle une méthode
                entities.addAll(inst.getEntities());
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return entities;
        // Embranchement : vérifie une condition
        } else if (targetSelector == TargetSelector.SELF) {
            // Renvoie une valeur à l'appelant
            return self != null ? List.of(self) : List.of();
        // Fin d'un bloc/d'une expression
        }
        // Lève une exception
        throw new IllegalStateException("Weird thing happened: " + targetSelector);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> boolean filterToggleableMap(T value, ToggleableMap<T> map) {
        // Boucle : répète un bloc
        for (var entry : Object2BooleanMaps.fastIterable(map)) {
            // Embranchement : vérifie une condition
            if (entry.getBooleanValue() != Objects.equals(value, entry.getKey())) {
                // Renvoie une valeur à l'appelant
                return false;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
