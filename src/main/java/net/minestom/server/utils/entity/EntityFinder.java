// Package declaration for this file
package net.minestom.server.utils.entity;

// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.ConnectionManager;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.Range;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ThreadLocalRandom;

// TODO

/**
 * Represents a query which can be call to find one or multiple entities.
 * It is based on the target selectors used in commands.
 */
// Type declaration (class/interface/enum/record)
public class EntityFinder {
    // Calls a method
    private static final ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();

    // Code statement
    private TargetSelector targetSelector;

    // Assigns a value
    private EntitySort entitySort = EntitySort.ARBITRARY;

    // Position
    // Code statement
    private Point startPosition;
    // Code statement
    private Float dx, dy, dz;
    // Code statement
    private Range.Int distance;

    // By traits
    // Code statement
    private Integer limit;
    // Calls a method
    private final ToggleableMap<EntityType> entityTypes = new ToggleableMap<>();
    // Code statement
    private String constantName;
    // Code statement
    private UUID constantUuid;
    // Calls a method
    private final ToggleableMap<String> names = new ToggleableMap<>();
    // Calls a method
    private final ToggleableMap<UUID> uuids = new ToggleableMap<>();


    // Players specific
    // Calls a method
    private final ToggleableMap<GameMode> gameModes = new ToggleableMap<>();
    // Code statement
    private Range.Int level;

    // Start of a method/block
    public EntityFinder setTargetSelector(TargetSelector targetSelector) {
        // Access to the current/parent object
        this.targetSelector = targetSelector;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setEntitySort(EntitySort entitySort) {
        // Access to the current/parent object
        this.entitySort = entitySort;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setStartPosition(Point startPosition) {
        // Access to the current/parent object
        this.startPosition = startPosition;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setDistance(Range.Int distance) {
        // Access to the current/parent object
        this.distance = distance;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setLimit(int limit) {
        // Access to the current/parent object
        this.limit = limit;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setLevel(Range.Int level) {
        // Access to the current/parent object
        this.level = level;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setEntity(EntityType entityType, ToggleableType toggleableType) {
        // Access to the current/parent object
        this.entityTypes.put(entityType, toggleableType.getValue());
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setConstantName(String constantName) {
        // Access to the current/parent object
        this.constantName = constantName;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setConstantUuid(UUID constantUuid) {
        // Access to the current/parent object
        this.constantUuid = constantUuid;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setName(String name, ToggleableType toggleableType) {
        // Access to the current/parent object
        this.names.put(name, toggleableType.getValue());
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setUuid(UUID uuid, ToggleableType toggleableType) {
        // Access to the current/parent object
        this.uuids.put(uuid, toggleableType.getValue());
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setGameMode(GameMode gameMode, ToggleableType toggleableType) {
        // Access to the current/parent object
        this.gameModes.put(gameMode, toggleableType.getValue());
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public EntityFinder setDifference(float dx, float dy, float dz) {
        // Access to the current/parent object
        this.dx = dx;
        // Access to the current/parent object
        this.dy = dy;
        // Access to the current/parent object
        this.dz = dz;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Find a list of entities (could be empty) based on the conditions
     *
     * @param instance the instance to search from,
     *                 null if the query can be executed using global data (all online players)
     * @param self     the source of the query, null if not any
     * @return all entities validating the conditions, can be empty
     */
    // Start of a method/block
    public List<Entity> find(@Nullable Instance instance, @Nullable Entity self) {
        // Branch: checks a condition
        if (targetSelector == TargetSelector.MINESTOM_USERNAME) {
            // Calls a method
            Objects.requireNonNull(constantName, "The player name should not be null when searching for it");
            // Calls a method
            final Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(constantName);
            // Returns a value to the caller
            return player != null ? List.of(player) : List.of();
        // Branch: checks a condition
        } else if (targetSelector == TargetSelector.MINESTOM_UUID) {
            // Calls a method
            Objects.requireNonNull(constantUuid, "The UUID should not be null when searching for it");
            // Calls a method
            Objects.requireNonNull(instance, "The instance should not be null when searching by UUID");
            // Calls a method
            final Entity entity = instance.getEntityByUuid(constantUuid);
            // Returns a value to the caller
            return entity != null ? List.of(entity) : List.of();
        // End of a block/expression
        }

        // Calls a method
        final Point pos = startPosition != null ? startPosition : (self != null ? self.getPosition() : Vec.ZERO);

        // Calls a method
        List<Entity> result = findTarget(instance, targetSelector, pos, self);
        // Fast exit if there is nothing to process
        // Branch: checks a condition
        if (result.isEmpty())
            // Returns a value to the caller
            return result;

        // Distance argument
        // Branch: checks a condition
        if (distance != null) {
            // Calls a method
            final int minDistance = distance.min();
            // Calls a method
            final int maxDistance = distance.max();
            // Assigns a value
            result = result.stream()
                    // Code statement
                    .filter(entity -> MathUtils.isBetween(entity.getPosition().distanceSquared(pos), minDistance * minDistance, maxDistance * maxDistance))
                    // Calls a method
                    .toList();
        // End of a block/expression
        }

        // Diff X/Y/Z
        // Branch: checks a condition
        if (dx != null || dy != null || dz != null) {
            // Assigns a value
            result = result.stream().filter(entity -> {
                // Calls a method
                final var entityPosition = entity.getPosition();
                // Branch: checks a condition
                if (dx != null && !MathUtils.isBetweenUnordered(
                        // Code statement
                        entityPosition.x(),
                        // Code statement
                        pos.x(), dx))
                    // Returns a value to the caller
                    return false;

                // Branch: checks a condition
                if (dy != null && !MathUtils.isBetweenUnordered(
                        // Code statement
                        entityPosition.y(),
                        // Code statement
                        pos.y(), dy))
                    // Returns a value to the caller
                    return false;

                // Branch: checks a condition
                if (dz != null && !MathUtils.isBetweenUnordered(
                        // Code statement
                        entityPosition.z(),
                        // Code statement
                        pos.z(), dz))
                    // Returns a value to the caller
                    return false;

                // Returns a value to the caller
                return true;
            // Calls a method
            }).toList();
        // End of a block/expression
        }

        // Entity type
        // Branch: checks a condition
        if (!entityTypes.isEmpty()) {
            // Assigns a value
            result = result.stream()
                    // Code statement
                    .filter(entity -> filterToggleableMap(entity.getEntityType(), entityTypes))
                    // Calls a method
                    .toList();
        // End of a block/expression
        }

        // GameMode
        // Branch: checks a condition
        if (!gameModes.isEmpty()) {
            // Assigns a value
            result = result.stream()
                    // Code statement
                    .filter(Player.class::isInstance)
                    // Code statement
                    .filter(entity -> filterToggleableMap(((Player) entity).getGameMode(), gameModes))
                    // Calls a method
                    .toList();
        // End of a block/expression
        }

        // Level
        // Branch: checks a condition
        if (level != null) {
            // Calls a method
            final int minLevel = level.min();
            // Calls a method
            final int maxLevel = level.max();
            // Assigns a value
            result = result.stream()
                    // Code statement
                    .filter(Player.class::isInstance)
                    // Code statement
                    .filter(entity -> MathUtils.isBetween(((Player) entity).getLevel(), minLevel, maxLevel))
                    // Calls a method
                    .toList();
        // End of a block/expression
        }

        // Name
        // Branch: checks a condition
        if (!names.isEmpty()) {
            // Assigns a value
            result = result.stream()
                    // Code statement
                    .filter(Player.class::isInstance)
                    // Code statement
                    .filter(entity -> filterToggleableMap(((Player) entity).getUsername(), names))
                    // Calls a method
                    .toList();
        // End of a block/expression
        }

        // UUID
        // Branch: checks a condition
        if (!uuids.isEmpty()) {
            // Assigns a value
            result = result.stream()
                    // Code statement
                    .filter(entity -> filterToggleableMap(entity.getUuid(), uuids))
                    // Calls a method
                    .toList();
        // End of a block/expression
        }


        // Sort & limit
        // Branch: checks a condition
        if (entitySort != EntitySort.ARBITRARY || limit != null) {
            // Assigns a value
            result = result.stream()
                    // Start of a method/block
                    .sorted((ent1, ent2) -> switch (entitySort) {
                        // Multiple branching (switch/case)
                        case ARBITRARY, RANDOM ->
                            // RANDOM is handled below
                                // Code statement
                                1;
                        // Multiple branching (switch/case)
                        case FURTHEST -> pos.distanceSquared(ent1.getPosition()) >
                                // Code statement
                                pos.distanceSquared(ent2.getPosition()) ?
                                // Code statement
                                1 : 0;
                        // Multiple branching (switch/case)
                        case NEAREST -> pos.distanceSquared(ent1.getPosition()) <
                                // Code statement
                                pos.distanceSquared(ent2.getPosition()) ?
                                // Code statement
                                1 : 0;
                    // Code statement
                    })
                    // Code statement
                    .limit(limit != null ? limit : Integer.MAX_VALUE)
                    // Calls a method
                    .toList();

            // Branch: checks a condition
            if (entitySort == EntitySort.RANDOM) {
                // Calls a method
                Collections.shuffle(result);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    public List<Entity> find(CommandSender sender) {
        // Returns a value to the caller
        return sender instanceof Player player ?
                // Calls a method
                find(player.getInstance(), player) : find(null, null);
    // End of a block/expression
    }

    /**
     * Shortcut of {@link #find(Instance, Entity)} to retrieve the first
     * player element in the list.
     *
     * @return the first player returned by {@link #find(Instance, Entity)}
     * @see #find(Instance, Entity)
     */
    // Start of a method/block
    public @Nullable Player findFirstPlayer(@Nullable Instance instance, @Nullable Entity self) {
        // Calls a method
        final List<Entity> entities = find(instance, self);
        // Loop: repeats a block
        for (Entity entity : entities) {
            // Branch: checks a condition
            if (entity instanceof Player player) {
                // Returns a value to the caller
                return player;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Player findFirstPlayer(CommandSender sender) {
        // Returns a value to the caller
        return sender instanceof Player player ?
                // Code statement
                findFirstPlayer(player.getInstance(), player) :
                // Calls a method
                findFirstPlayer(null, null);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Entity findFirstEntity(@Nullable Instance instance, @Nullable Entity self) {
        // Calls a method
        final List<Entity> entities = find(instance, self);
        // Returns a value to the caller
        return entities.isEmpty() ? null : entities.getFirst();
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Entity findFirstEntity(CommandSender sender) {
        // Returns a value to the caller
        return sender instanceof Player player ?
                // Calls a method
                findFirstEntity(player.getInstance(), player) : findFirstEntity(null, null);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum TargetSelector {
        // Code statement
        NEAREST_PLAYER, RANDOM_PLAYER, ALL_PLAYERS, ALL_ENTITIES, SELF, MINESTOM_USERNAME, MINESTOM_UUID, NEAREST_ENTITY
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum EntitySort {
        // Code statement
        ARBITRARY, FURTHEST, NEAREST, RANDOM
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum ToggleableType {
        // Calls a method
        INCLUDE(true), EXCLUDE(false);

        // Code statement
        private final boolean value;

        // Start of a method/block
        ToggleableType(boolean value) {
            // Access to the current/parent object
            this.value = value;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean getValue() {
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private static class ToggleableMap<T> extends Object2BooleanOpenHashMap<T> {
    // End of a block/expression
    }

    // Code statement
    private static List<Entity> findTarget(@Nullable Instance instance,
                                                             // Code statement
                                                             TargetSelector targetSelector,
                                                             // Start of a method/block
                                                             Point startPosition, @Nullable Entity self) {
        // Calls a method
        final var players = instance != null ? instance.getPlayers() : CONNECTION_MANAGER.getOnlinePlayers();
        // Branch: checks a condition
        if (targetSelector == TargetSelector.NEAREST_PLAYER) {
            // Returns a value to the caller
            return players.stream()
                    // Code statement
                    .min(Comparator.comparingDouble(p -> p.getPosition().distanceSquared(startPosition)))
                    // Calls a method
                    .<List<Entity>>map(Collections::singletonList).orElse(List.of());
        // Branch: checks a condition
        } else if (targetSelector == TargetSelector.NEAREST_ENTITY) {
            // Calls a method
            List<Entity> entities = findTarget(instance, TargetSelector.ALL_ENTITIES, startPosition, self);

            // Returns a value to the caller
            return entities.stream()
                    // Code statement
                    .min(Comparator.comparingDouble(p -> p.getPosition().distanceSquared(startPosition)))
                    // Calls a method
                    .map(Collections::singletonList).orElse(List.of());
        // Branch: checks a condition
        } else if (targetSelector == TargetSelector.RANDOM_PLAYER) {
            // Calls a method
            final int index = ThreadLocalRandom.current().nextInt(players.size());
            // Calls a method
            final Player player = players.stream().skip(index).findFirst().orElseThrow();
            // Returns a value to the caller
            return List.of(player);
        // Branch: checks a condition
        } else if (targetSelector == TargetSelector.ALL_PLAYERS) {
            // Returns a value to the caller
            return List.copyOf(players);
        // Branch: checks a condition
        } else if (targetSelector == TargetSelector.ALL_ENTITIES) {
            // Branch: checks a condition
            if (instance != null) {
                // Returns a value to the caller
                return List.copyOf(instance.getEntities());
            // End of a block/expression
            }
            // Get entities from every instance
            // Calls a method
            var instances = MinecraftServer.getInstanceManager().getInstances();
            // Calls a method
            List<Entity> entities = new ArrayList<>();
            // Loop: repeats a block
            for (Instance inst : instances) {
                // Calls a method
                entities.addAll(inst.getEntities());
            // End of a block/expression
            }
            // Returns a value to the caller
            return entities;
        // Branch: checks a condition
        } else if (targetSelector == TargetSelector.SELF) {
            // Returns a value to the caller
            return self != null ? List.of(self) : List.of();
        // End of a block/expression
        }
        // Throws an exception
        throw new IllegalStateException("Weird thing happened: " + targetSelector);
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> boolean filterToggleableMap(T value, ToggleableMap<T> map) {
        // Loop: repeats a block
        for (var entry : Object2BooleanMaps.fastIterable(map)) {
            // Branch: checks a condition
            if (entry.getBooleanValue() != Objects.equals(value, entry.getKey())) {
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }
// End of a block/expression
}
