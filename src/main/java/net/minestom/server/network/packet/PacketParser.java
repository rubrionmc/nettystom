// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

/**
 * Responsible for parsing client and server packets.
 * <p>
 * You can retrieve the different packets per state (status/login/play)
 * from the {@link PacketRegistry} classes.
 */
// Type declaration (class/interface/enum/record)
public sealed interface PacketParser<T> {

    // Calls a method
    PacketRegistry<? extends T> handshake();

    // Calls a method
    PacketRegistry<? extends T> status();

    // Calls a method
    PacketRegistry<? extends T> login();

    // Calls a method
    PacketRegistry<? extends T> configuration();

    // Calls a method
    PacketRegistry<? extends T> play();

    // Code statement
    default T parse(ConnectionState connectionState,
                             // Start of a method/block
                             int packetId, NetworkBuffer buffer) {
        // Calls a method
        final PacketRegistry<? extends T> registry = stateRegistry(connectionState);
        // Returns a value to the caller
        return registry.create(packetId, buffer);
    // End of a block/expression
    }

    // Start of a method/block
    default PacketRegistry<? extends T> stateRegistry(ConnectionState connectionState) {
        // Returns a value to the caller
        return switch (connectionState) {
            // Multiple branching (switch/case)
            case HANDSHAKE -> handshake();
            // Multiple branching (switch/case)
            case STATUS -> status();
            // Multiple branching (switch/case)
            case LOGIN -> login();
            // Multiple branching (switch/case)
            case CONFIGURATION -> configuration();
            // Multiple branching (switch/case)
            case PLAY -> play();
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Client(
            // Code statement
            PacketRegistry<ClientPacket.Handshake> handshake,
            // Code statement
            PacketRegistry<ClientPacket.Status> status,
            // Code statement
            PacketRegistry<ClientPacket.Login> login,
            // Code statement
            PacketRegistry<ClientPacket.Configuration> configuration,
            // Code statement
            PacketRegistry<ClientPacket.Play> play
    // Start of a method/block
    ) implements PacketParser<ClientPacket> {
        // Start of a method/block
        public Client() {
            // Code statement
            this(
                    // Creates a new object
                    new PacketRegistry.ClientHandshake(),
                    // Creates a new object
                    new PacketRegistry.ClientStatus(),
                    // Creates a new object
                    new PacketRegistry.ClientLogin(),
                    // Creates a new object
                    new PacketRegistry.ClientConfiguration(),
                    // Creates a new object
                    new PacketRegistry.ClientPlay()
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Server(
            // Code statement
            PacketRegistry<ServerPacket.Handshake> handshake,
            // Code statement
            PacketRegistry<ServerPacket.Status> status,
            // Code statement
            PacketRegistry<ServerPacket.Login> login,
            // Code statement
            PacketRegistry<ServerPacket.Configuration> configuration,
            // Code statement
            PacketRegistry<ServerPacket.Play> play
    // Start of a method/block
    ) implements PacketParser<ServerPacket> {
        // Start of a method/block
        public Server() {
            // Code statement
            this(
                    // Creates a new object
                    new PacketRegistry.ServerHandshake(),
                    // Creates a new object
                    new PacketRegistry.ServerStatus(),
                    // Creates a new object
                    new PacketRegistry.ServerLogin(),
                    // Creates a new object
                    new PacketRegistry.ServerConfiguration(),
                    // Creates a new object
                    new PacketRegistry.ServerPlay()
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
