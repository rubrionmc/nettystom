// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.handshake;

// Import d'une classe nécessaire
import net.minestom.server.Auth;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
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
                                    int serverPort, Intent intent) implements ClientPacket {

    // Début d'une méthode/d'un bloc
    public ClientHandshakePacket {
        // Embranchement : vérifie une condition
        if (serverAddress.length() > maxHandshakeLength()) {
            // Lève une exception
            throw new IllegalArgumentException("Server address too long: " + serverAddress.length());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

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

    // Début d'une méthode/d'un bloc
    private static int maxHandshakeLength() {
        // BungeeGuard limits handshake length to 2500 characters, while vanilla limits it to 255
        // Renvoie une valeur à l'appelant
        return MinecraftServer.process().auth() instanceof Auth.Bungee bungee ? (bungee.guard() ? 2500 : Short.MAX_VALUE) : 255;
    // Fin d'un bloc/d'une expression
    }

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
                // Appelle une méthode
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
