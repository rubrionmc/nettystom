// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record SetPassengersPacket(int vehicleEntityId,
                                  // Start of a method/block
                                  List<Integer> passengersId) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_PASSENGERS = 16384;

    // Assigns a value
    public static final NetworkBuffer.Type<SetPassengersPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, SetPassengersPacket::vehicleEntityId,
            // Code statement
            VAR_INT.list(MAX_PASSENGERS), SetPassengersPacket::passengersId,
            // Code statement
            SetPassengersPacket::new);

    // Start of a method/block
    public SetPassengersPacket {
        // Calls a method
        passengersId = List.copyOf(passengersId);
    // End of a block/expression
    }
// End of a block/expression
}
