// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientConfigurationAckPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.FinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.server.login.LoginSuccessPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.StartConfigurationPacket;
// Import of a required class
import net.minestom.server.utils.ObjectPool;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Constants and utilities for vanilla packets.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class PacketVanilla {
    // Calls a method
    public static final PacketParser.Client CLIENT_PACKET_PARSER = new PacketParser.Client();
    // Calls a method
    public static final PacketParser.Server SERVER_PACKET_PARSER = new PacketParser.Server();

    /**
     * Pool containing a buffer able to hold the largest packet.
     * <p>
     * Size starts with {@link ServerFlag#POOLED_BUFFER_SIZE} and doubles until {@link ServerFlag#MAX_PACKET_SIZE}.
     */
    // Assigns a value
    public static final ObjectPool<NetworkBuffer> PACKET_POOL = ObjectPool.pool(
            // Code statement
            () -> NetworkBuffer.staticBuffer(ServerFlag.POOLED_BUFFER_SIZE, MinecraftServer.process()),
            // Code statement
            NetworkBuffer::clear);

    // Start of a method/block
    public static ConnectionState nextClientState(ClientPacket packet, ConnectionState currentState) {
        // Returns a value to the caller
        return switch (packet) {
            // Multiple branching (switch/case)
            case ClientHandshakePacket handshakePacket -> switch (handshakePacket.intent()) {
                // Multiple branching (switch/case)
                case STATUS -> ConnectionState.STATUS;
                // Multiple branching (switch/case)
                case LOGIN, TRANSFER -> ConnectionState.LOGIN;
            // End of a block/expression
            };
            // Multiple branching (switch/case)
            case ClientLoginAcknowledgedPacket ignored -> ConnectionState.CONFIGURATION;
            // Multiple branching (switch/case)
            case ClientConfigurationAckPacket ignored -> ConnectionState.CONFIGURATION;
            // Multiple branching (switch/case)
            case ClientFinishConfigurationPacket ignored -> ConnectionState.PLAY;
            // Multiple branching (switch/case)
            default -> currentState;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public static ConnectionState nextServerState(ServerPacket packet, ConnectionState currentState) {
        // Client chooses between STATUS or LOGIN state directly after the first handshake packet
        // Branch: checks a condition
        if (currentState == ConnectionState.HANDSHAKE)
            // Throws an exception
            throw new IllegalStateException("No server Handshake packet exists");
        // Returns a value to the caller
        return switch (packet) {
            // Multiple branching (switch/case)
            case LoginSuccessPacket ignored -> ConnectionState.CONFIGURATION;
            // Multiple branching (switch/case)
            case StartConfigurationPacket ignored -> ConnectionState.CONFIGURATION;
            // Multiple branching (switch/case)
            case FinishConfigurationPacket ignored -> ConnectionState.PLAY;
            // Multiple branching (switch/case)
            default -> currentState;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
