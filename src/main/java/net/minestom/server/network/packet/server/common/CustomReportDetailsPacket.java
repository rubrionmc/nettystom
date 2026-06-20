// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record CustomReportDetailsPacket(
        // Code statement
        Map<String, String> details
// Start of a method/block
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    private static final int MAX_DETAILS = 32;

    // Assigns a value
    public static final NetworkBuffer.Type<CustomReportDetailsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING.mapValue(NetworkBuffer.STRING, MAX_DETAILS), CustomReportDetailsPacket::details,
            // Code statement
            CustomReportDetailsPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public CustomReportDetailsPacket {
        // Calls a method
        details = Map.copyOf(details);
    // End of a block/expression
    }
// End of a block/expression
}
