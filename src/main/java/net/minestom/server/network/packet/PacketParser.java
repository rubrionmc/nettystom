// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

/**
 * Responsible for parsing client and server packets.
 * <p>
 * You can retrieve the different packets per state (status/login/play)
 * from the {@link PacketRegistry} classes.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface PacketParser<T> {

    // Appelle une méthode
    PacketRegistry<? extends T> handshake();

    // Appelle une méthode
    PacketRegistry<? extends T> status();

    // Appelle une méthode
    PacketRegistry<? extends T> login();

    // Appelle une méthode
    PacketRegistry<? extends T> configuration();

    // Appelle une méthode
    PacketRegistry<? extends T> play();

    // Instruction de code
    default T parse(ConnectionState connectionState,
                             // Début d'une méthode/d'un bloc
                             int packetId, NetworkBuffer buffer) {
        // Appelle une méthode
        final PacketRegistry<? extends T> registry = stateRegistry(connectionState);
        // Renvoie une valeur à l'appelant
        return registry.create(packetId, buffer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default PacketRegistry<? extends T> stateRegistry(ConnectionState connectionState) {
        // Renvoie une valeur à l'appelant
        return switch (connectionState) {
            // Embranchement multiple (switch/case)
            case HANDSHAKE -> handshake();
            // Embranchement multiple (switch/case)
            case STATUS -> status();
            // Embranchement multiple (switch/case)
            case LOGIN -> login();
            // Embranchement multiple (switch/case)
            case CONFIGURATION -> configuration();
            // Embranchement multiple (switch/case)
            case PLAY -> play();
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Client(
            // Instruction de code
            PacketRegistry<ClientPacket.Handshake> handshake,
            // Instruction de code
            PacketRegistry<ClientPacket.Status> status,
            // Instruction de code
            PacketRegistry<ClientPacket.Login> login,
            // Instruction de code
            PacketRegistry<ClientPacket.Configuration> configuration,
            // Instruction de code
            PacketRegistry<ClientPacket.Play> play
    // Début d'une méthode/d'un bloc
    ) implements PacketParser<ClientPacket> {
        // Début d'une méthode/d'un bloc
        public Client() {
            // Instruction de code
            this(
                    // Crée un nouvel objet
                    new PacketRegistry.ClientHandshake(),
                    // Crée un nouvel objet
                    new PacketRegistry.ClientStatus(),
                    // Crée un nouvel objet
                    new PacketRegistry.ClientLogin(),
                    // Crée un nouvel objet
                    new PacketRegistry.ClientConfiguration(),
                    // Crée un nouvel objet
                    new PacketRegistry.ClientPlay()
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Server(
            // Instruction de code
            PacketRegistry<ServerPacket.Handshake> handshake,
            // Instruction de code
            PacketRegistry<ServerPacket.Status> status,
            // Instruction de code
            PacketRegistry<ServerPacket.Login> login,
            // Instruction de code
            PacketRegistry<ServerPacket.Configuration> configuration,
            // Instruction de code
            PacketRegistry<ServerPacket.Play> play
    // Début d'une méthode/d'un bloc
    ) implements PacketParser<ServerPacket> {
        // Début d'une méthode/d'un bloc
        public Server() {
            // Instruction de code
            this(
                    // Crée un nouvel objet
                    new PacketRegistry.ServerHandshake(),
                    // Crée un nouvel objet
                    new PacketRegistry.ServerStatus(),
                    // Crée un nouvel objet
                    new PacketRegistry.ServerLogin(),
                    // Crée un nouvel objet
                    new PacketRegistry.ServerConfiguration(),
                    // Crée un nouvel objet
                    new PacketRegistry.ServerPlay()
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
