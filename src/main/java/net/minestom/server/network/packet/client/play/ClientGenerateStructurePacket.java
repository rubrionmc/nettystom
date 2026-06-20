// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientGenerateStructurePacket(Point blockPosition,
                                            // Start of a method/block
                                            int level, boolean keepJigsaws) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientGenerateStructurePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, ClientGenerateStructurePacket::blockPosition,
            // Code statement
            VAR_INT, ClientGenerateStructurePacket::level,
            // Code statement
            BOOLEAN, ClientGenerateStructurePacket::keepJigsaws,
            // Code statement
            ClientGenerateStructurePacket::new);
// End of a block/expression
}
