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
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientUpdateJigsawBlockPacket(
        // Code statement
        Point location,
        // Code statement
        String name,
        // Code statement
        String target,
        // Code statement
        String pool,
        // Code statement
        String finalState,
        // Code statement
        String jointType,
        // Code statement
        int selectionPriority,
        // Code statement
        int placementPriority
// Start of a method/block
) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientUpdateJigsawBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, ClientUpdateJigsawBlockPacket::location,
            // Code statement
            STRING, ClientUpdateJigsawBlockPacket::name,
            // Code statement
            STRING, ClientUpdateJigsawBlockPacket::target,
            // Code statement
            STRING, ClientUpdateJigsawBlockPacket::pool,
            // Code statement
            STRING, ClientUpdateJigsawBlockPacket::finalState,
            // Code statement
            STRING, ClientUpdateJigsawBlockPacket::jointType,
            // Code statement
            VAR_INT, ClientUpdateJigsawBlockPacket::selectionPriority,
            // Code statement
            VAR_INT, ClientUpdateJigsawBlockPacket::placementPriority,
            // Code statement
            ClientUpdateJigsawBlockPacket::new);

    // Start of a method/block
    public ClientUpdateJigsawBlockPacket {
        // Calls a method
        Check.argCondition(name.length() > Short.MAX_VALUE, "Name length cannot be greater than Short.MAX_VALUE");
        // Calls a method
        Check.argCondition(target.length() > Short.MAX_VALUE, "Target length cannot be greater than Short.MAX_VALUE");
        // Calls a method
        Check.argCondition(pool.length() > Short.MAX_VALUE, "Pool length cannot be greater than Short.MAX_VALUE");
        // Calls a method
        Check.argCondition(finalState.length() > Short.MAX_VALUE, "Final state length cannot be greater than Short.MAX_VALUE");
        // Calls a method
        Check.argCondition(jointType.length() > Short.MAX_VALUE, "Joint type length cannot be greater than Short.MAX_VALUE");
    // End of a block/expression
    }
// End of a block/expression
}
