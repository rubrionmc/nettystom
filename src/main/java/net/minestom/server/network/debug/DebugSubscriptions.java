// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.info.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;

// Import d'une classe nécessaire
import java.util.List;

// Annotation pour l'élément suivant
@SuppressWarnings("unused")
// Déclaration de type (classe/interface/enum/record)
sealed interface DebugSubscriptions permits DebugSubscription {
    // Appelle une méthode
    DebugSubscription<Unit> DEDICATED_SERVER_TICK_TIME = register("dedicated_server_tick_time");
    // Appelle une méthode
    DebugSubscription<DebugBeeInfo> BEES = register("bees", DebugBeeInfo.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<DebugBrainDump> BRAINS = register("brains", DebugBrainDump.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<DebugBreezeInfo> BREEZES = register("breezes", DebugBreezeInfo.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<List<DebugGoalInfo>> GOAL_SELECTORS = register("goal_selectors", DebugGoalInfo.SERIALIZER.list());
    // Appelle une méthode
    DebugSubscription<DebugPathInfo> ENTITY_PATHS = register("entity_paths", DebugPathInfo.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<DebugEntityBlockIntersection> ENTITY_BLOCK_INTERSECTIONS = register("entity_block_intersections", DebugEntityBlockIntersection.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<DebugHiveInfo> BEE_HIVES = register("bee_hives", DebugHiveInfo.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<DebugPoiInfo> POIS = register("pois", DebugPoiInfo.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<Integer> REDSTONE_WIRE_ORIENTATIONS = register("redstone_wire_orientations", NetworkBuffer.VAR_INT);
    // Appelle une méthode
    DebugSubscription<Unit> VILLAGE_SECTIONS = register("village_sections");
    // Appelle une méthode
    DebugSubscription<List<Point>> RAIDS = register("raids", NetworkBuffer.BLOCK_POSITION.list());
    // Appelle une méthode
    DebugSubscription<List<DebugStructureInfo>> STRUCTURES = register("structures", DebugStructureInfo.SERIALIZER.list());
    // Appelle une méthode
    DebugSubscription<DebugGameEventListenerInfo> GAME_EVENT_LISTENERS = register("game_event_listeners", DebugGameEventListenerInfo.SERIALIZER);
    // Appelle une méthode
    DebugSubscription<Point> NEIGHBOR_UPDATES = register("neighbor_updates", NetworkBuffer.BLOCK_POSITION);
    // Appelle une méthode
    DebugSubscription<DebugGameEventInfo> GAME_EVENTS = register("game_events", DebugGameEventInfo.SERIALIZER);

    // Début d'une méthode/d'un bloc
    private static DebugSubscription<Unit> register(String name) {
        // Renvoie une valeur à l'appelant
        return register(name, NetworkBuffer.UNIT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> DebugSubscription<T> register(String name, NetworkBuffer.Type<T> networkType) {
        // Appelle une méthode
        DebugSubscription<T> impl = new DebugSubscriptionImpl<>(DebugSubscriptionImpl.NAMESPACES.size(), Key.key(name), networkType);
        // Appelle une méthode
        DebugSubscriptionImpl.NAMESPACES.put(impl.name(), impl);
        // Appelle une méthode
        DebugSubscriptionImpl.IDS.set(impl.id(), impl);
        // Renvoie une valeur à l'appelant
        return impl;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
