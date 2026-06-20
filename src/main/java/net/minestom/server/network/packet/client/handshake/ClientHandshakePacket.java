// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.handshake;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientHandshakePacket(int protocolVersion, String serverAddress,
                                    // Début d'une méthode/d'un bloc
                                    int serverPort, Intent intent) implements ClientPacket.Handshake {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientHandshakePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientHandshakePacket::protocolVersion,
            // Instruction de code
            STRING, ClientHandshakePacket::serverAddress,
            // Instruction de code
            UNSIGNED_SHORT, ClientHandshakePacket::serverPort,
            // Instruction de code
            VAR_INT.transform(Intent::fromId, Intent::id), ClientHandshakePacket::intent,
            // Instruction de code
            ClientHandshakePacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Intent {
        // Instruction de code
        STATUS,
        // Instruction de code
        LOGIN,
        // Instruction de code
        TRANSFER;

        // Début d'une méthode/d'un bloc
        public static Intent fromId(int id) {
            // Renvoie une valeur à l'appelant
            return switch (id) {
                // Embranchement multiple (switch/case)
                case 1 -> STATUS;
                // Embranchement multiple (switch/case)
                case 2 -> LOGIN;
                // Embranchement multiple (switch/case)
                case 3 -> TRANSFER;
                // Embranchement multiple (switch/case)
                default -> throw new IllegalArgumentException("Unknown connection intent: " + id);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return ordinal() + 1;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
