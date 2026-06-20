// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientUpdateCommandBlockMinecartPacket(int entityId, String command,
                                                     // Start of a method/block
                                                     boolean trackOutput) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientUpdateCommandBlockMinecartPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientUpdateCommandBlockMinecartPacket::entityId,
            // Code statement
            STRING, ClientUpdateCommandBlockMinecartPacket::command,
            // Code statement
            BOOLEAN, ClientUpdateCommandBlockMinecartPacket::trackOutput,
            // Code statement
            ClientUpdateCommandBlockMinecartPacket::new);

    // Start of a method/block
    public ClientUpdateCommandBlockMinecartPacket {
        // Calls a method
        Check.argCondition(command.length() > Short.MAX_VALUE, "Command length cannot be greater than Short.MAX_VALUE");
    // End of a block/expression
    }
// End of a block/expression
}
