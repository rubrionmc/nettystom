// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientConfigurationAckPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.FinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.LoginSuccessPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.StartConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.ObjectPool;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * Constants and utilities for vanilla packets.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class PacketVanilla {
    // Appelle une méthode
    public static final PacketParser.Client CLIENT_PACKET_PARSER = new PacketParser.Client();
    // Appelle une méthode
    public static final PacketParser.Server SERVER_PACKET_PARSER = new PacketParser.Server();

    /**
     * Pool containing a buffer able to hold the largest packet.
     * <p>
     * Size starts with {@link ServerFlag#POOLED_BUFFER_SIZE} and doubles until {@link ServerFlag#MAX_PACKET_SIZE}.
     */
    // Affecte une valeur
    public static final ObjectPool<NetworkBuffer> PACKET_POOL = ObjectPool.pool(
            // Instruction de code
            () -> NetworkBuffer.staticBuffer(ServerFlag.POOLED_BUFFER_SIZE, MinecraftServer.process()),
            // Instruction de code
            NetworkBuffer::clear);

    // Début d'une méthode/d'un bloc
    public static ConnectionState nextClientState(ClientPacket packet, ConnectionState currentState) {
        // Renvoie une valeur à l'appelant
        return switch (packet) {
            // Embranchement multiple (switch/case)
            case ClientHandshakePacket handshakePacket -> switch (handshakePacket.intent()) {
                // Embranchement multiple (switch/case)
                case STATUS -> ConnectionState.STATUS;
                // Embranchement multiple (switch/case)
                case LOGIN, TRANSFER -> ConnectionState.LOGIN;
            // Fin d'un bloc/d'une expression
            };
            // Embranchement multiple (switch/case)
            case ClientLoginAcknowledgedPacket ignored -> ConnectionState.CONFIGURATION;
            // Embranchement multiple (switch/case)
            case ClientConfigurationAckPacket ignored -> ConnectionState.CONFIGURATION;
            // Embranchement multiple (switch/case)
            case ClientFinishConfigurationPacket ignored -> ConnectionState.PLAY;
            // Embranchement multiple (switch/case)
            default -> currentState;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ConnectionState nextServerState(ServerPacket packet, ConnectionState currentState) {
        // Client chooses between STATUS or LOGIN state directly after the first handshake packet
        // Embranchement : vérifie une condition
        if (currentState == ConnectionState.HANDSHAKE)
            // Lève une exception
            throw new IllegalStateException("No server Handshake packet exists");
        // Renvoie une valeur à l'appelant
        return switch (packet) {
            // Embranchement multiple (switch/case)
            case LoginSuccessPacket ignored -> ConnectionState.CONFIGURATION;
            // Embranchement multiple (switch/case)
            case StartConfigurationPacket ignored -> ConnectionState.CONFIGURATION;
            // Embranchement multiple (switch/case)
            case FinishConfigurationPacket ignored -> ConnectionState.PLAY;
            // Embranchement multiple (switch/case)
            default -> currentState;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
