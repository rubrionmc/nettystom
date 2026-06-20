// Package declaration for this file
package net.minestom.server.network.debug;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.debug.info.*;
// Import of a required class
import net.minestom.server.utils.Unit;

// Import of a required class
import java.util.List;

// Annotation for the following element
@SuppressWarnings("unused")
// Type declaration (class/interface/enum/record)
sealed interface DebugSubscriptions permits DebugSubscription {
    // Calls a method
    DebugSubscription<Unit> DEDICATED_SERVER_TICK_TIME = register("dedicated_server_tick_time");
    // Calls a method
    DebugSubscription<DebugBeeInfo> BEES = register("bees", DebugBeeInfo.SERIALIZER);
    // Calls a method
    DebugSubscription<DebugBrainDump> BRAINS = register("brains", DebugBrainDump.SERIALIZER);
    // Calls a method
    DebugSubscription<DebugBreezeInfo> BREEZES = register("breezes", DebugBreezeInfo.SERIALIZER);
    // Calls a method
    DebugSubscription<List<DebugGoalInfo>> GOAL_SELECTORS = register("goal_selectors", DebugGoalInfo.SERIALIZER.list());
    // Calls a method
    DebugSubscription<DebugPathInfo> ENTITY_PATHS = register("entity_paths", DebugPathInfo.SERIALIZER);
    // Calls a method
    DebugSubscription<DebugEntityBlockIntersection> ENTITY_BLOCK_INTERSECTIONS = register("entity_block_intersections", DebugEntityBlockIntersection.SERIALIZER);
    // Calls a method
    DebugSubscription<DebugHiveInfo> BEE_HIVES = register("bee_hives", DebugHiveInfo.SERIALIZER);
    // Calls a method
    DebugSubscription<DebugPoiInfo> POIS = register("pois", DebugPoiInfo.SERIALIZER);
    // Calls a method
    DebugSubscription<Integer> REDSTONE_WIRE_ORIENTATIONS = register("redstone_wire_orientations", NetworkBuffer.VAR_INT);
    // Calls a method
    DebugSubscription<Unit> VILLAGE_SECTIONS = register("village_sections");
    // Calls a method
    DebugSubscription<List<Point>> RAIDS = register("raids", NetworkBuffer.BLOCK_POSITION.list());
    // Calls a method
    DebugSubscription<List<DebugStructureInfo>> STRUCTURES = register("structures", DebugStructureInfo.SERIALIZER.list());
    // Calls a method
    DebugSubscription<DebugGameEventListenerInfo> GAME_EVENT_LISTENERS = register("game_event_listeners", DebugGameEventListenerInfo.SERIALIZER);
    // Calls a method
    DebugSubscription<Point> NEIGHBOR_UPDATES = register("neighbor_updates", NetworkBuffer.BLOCK_POSITION);
    // Calls a method
    DebugSubscription<DebugGameEventInfo> GAME_EVENTS = register("game_events", DebugGameEventInfo.SERIALIZER);

    // Start of a method/block
    private static DebugSubscription<Unit> register(String name) {
        // Returns a value to the caller
        return register(name, NetworkBuffer.UNIT);
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> DebugSubscription<T> register(String name, NetworkBuffer.Type<T> networkType) {
        // Calls a method
        DebugSubscription<T> impl = new DebugSubscriptionImpl<>(DebugSubscriptionImpl.NAMESPACES.size(), Key.key(name), networkType);
        // Calls a method
        DebugSubscriptionImpl.NAMESPACES.put(impl.name(), impl);
        // Calls a method
        DebugSubscriptionImpl.IDS.set(impl.id(), impl);
        // Returns a value to the caller
        return impl;
    // End of a block/expression
    }
// End of a block/expression
}
