// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public record DebugBrainDump(
        // Code statement
        String name,
        // Code statement
        String profession,
        // Code statement
        int xp,
        // Code statement
        float health,
        // Code statement
        float maxHealth,
        // Code statement
        String inventory,
        // Code statement
        boolean wantsGolen,
        // Code statement
        int angerLevel,
        // Code statement
        List<String> activities,
        // Code statement
        List<String> behaviors,
        // Code statement
        List<String> memories,
        // Code statement
        List<String> gossips,
        // Code statement
        Set<Point> pois,
        // Code statement
        Set<Point> potentialPois
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugBrainDump> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING, DebugBrainDump::name,
            // Code statement
            NetworkBuffer.STRING, DebugBrainDump::profession,
            // Code statement
            NetworkBuffer.INT, DebugBrainDump::xp,
            // Code statement
            NetworkBuffer.FLOAT, DebugBrainDump::health,
            // Code statement
            NetworkBuffer.FLOAT, DebugBrainDump::maxHealth,
            // Code statement
            NetworkBuffer.STRING, DebugBrainDump::inventory,
            // Code statement
            NetworkBuffer.BOOLEAN, DebugBrainDump::wantsGolen,
            // Code statement
            NetworkBuffer.INT, DebugBrainDump::angerLevel,
            // Code statement
            NetworkBuffer.STRING.list(), DebugBrainDump::activities,
            // Code statement
            NetworkBuffer.STRING.list(), DebugBrainDump::behaviors,
            // Code statement
            NetworkBuffer.STRING.list(), DebugBrainDump::memories,
            // Code statement
            NetworkBuffer.STRING.list(), DebugBrainDump::gossips,
            // Code statement
            NetworkBuffer.BLOCK_POSITION.set(), DebugBrainDump::pois,
            // Code statement
            NetworkBuffer.BLOCK_POSITION.set(), DebugBrainDump::potentialPois,
            // Code statement
            DebugBrainDump::new);

    // Start of a method/block
    public DebugBrainDump {
        // Calls a method
        activities = List.copyOf(activities);
        // Calls a method
        behaviors = List.copyOf(behaviors);
        // Calls a method
        memories = List.copyOf(memories);
        // Calls a method
        gossips = List.copyOf(gossips);
        // Calls a method
        pois = Set.copyOf(pois);
        // Calls a method
        potentialPois = Set.copyOf(potentialPois);
    // End of a block/expression
    }
// End of a block/expression
}
