// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.Enum;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientEntityActionPacket(int playerId, Action action,
                                       // Start of a method/block
                                       int horseJumpBoost) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientEntityActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientEntityActionPacket::playerId,
            // Code statement
            Enum(Action.class), ClientEntityActionPacket::action,
            // Code statement
            VAR_INT, ClientEntityActionPacket::horseJumpBoost,
            // Code statement
            ClientEntityActionPacket::new);

    // Type declaration (class/interface/enum/record)
    public enum Action {
        // Code statement
        LEAVE_BED,
        // Code statement
        START_SPRINTING,
        // Code statement
        STOP_SPRINTING,
        // Code statement
        START_JUMP_HORSE,
        // Code statement
        STOP_JUMP_HORSE,
        // Code statement
        OPEN_HORSE_INVENTORY,
        // Code statement
        START_FLYING_ELYTRA
    // End of a block/expression
    }
// End of a block/expression
}
