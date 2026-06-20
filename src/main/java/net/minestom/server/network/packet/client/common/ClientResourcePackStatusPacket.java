// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.kyori.adventure.resource.ResourcePackStatus;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.UUID;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientResourcePackStatusPacket(
        // Code statement
        UUID id,
        // Code statement
        ResourcePackStatus status
// Start of a method/block
) implements ClientPacket.Configuration, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientResourcePackStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            UUID, ClientResourcePackStatusPacket::id,
            // Code statement
            VAR_INT.transform(ClientResourcePackStatusPacket::readStatus, ClientResourcePackStatusPacket::statusId), ClientResourcePackStatusPacket::status,
            // Code statement
            ClientResourcePackStatusPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    private static ResourcePackStatus readStatus(int id) {
        // Returns a value to the caller
        return switch (id) {
            // Multiple branching (switch/case)
            case 0 -> ResourcePackStatus.SUCCESSFULLY_LOADED;
            // Multiple branching (switch/case)
            case 1 -> ResourcePackStatus.DECLINED;
            // Multiple branching (switch/case)
            case 2 -> ResourcePackStatus.FAILED_DOWNLOAD;
            // Multiple branching (switch/case)
            case 3 -> ResourcePackStatus.ACCEPTED;
            // Multiple branching (switch/case)
            case 4 -> ResourcePackStatus.DOWNLOADED;
            // Multiple branching (switch/case)
            case 5 -> ResourcePackStatus.INVALID_URL;
            // Multiple branching (switch/case)
            case 6 -> ResourcePackStatus.FAILED_RELOAD;
            // Multiple branching (switch/case)
            case 7 -> ResourcePackStatus.DISCARDED;
            // Multiple branching (switch/case)
            default -> throw new IllegalStateException("Unexpected resource pack status: " + id);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static int statusId(ResourcePackStatus status) {
        // Returns a value to the caller
        return switch (status) {
            // Multiple branching (switch/case)
            case SUCCESSFULLY_LOADED -> 0;
            // Multiple branching (switch/case)
            case DECLINED -> 1;
            // Multiple branching (switch/case)
            case FAILED_DOWNLOAD -> 2;
            // Multiple branching (switch/case)
            case ACCEPTED -> 3;
            // Multiple branching (switch/case)
            case DOWNLOADED -> 4;
            // Multiple branching (switch/case)
            case INVALID_URL -> 5;
            // Multiple branching (switch/case)
            case FAILED_RELOAD -> 6;
            // Multiple branching (switch/case)
            case DISCARDED -> 7;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
