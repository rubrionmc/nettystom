// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record DebugGoalInfo(int priority, boolean isRunning, String name) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugGoalInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, DebugGoalInfo::priority,
            // Code statement
            NetworkBuffer.BOOLEAN, DebugGoalInfo::isRunning,
            // Code statement
            NetworkBuffer.STRING, DebugGoalInfo::name,
            // Code statement
            DebugGoalInfo::new);
// End of a block/expression
}
