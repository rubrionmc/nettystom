// Package declaration for this file
package net.minestom.server.network.packet.client;

// Import of a required class
import net.minestom.server.network.packet.Packet;

/**
 * Represents a packet received from a client.
 * <p>
 * Packets are value-based, and should therefore not be reliant on identity.
 */
// Type declaration (class/interface/enum/record)
public sealed interface ClientPacket extends Packet {
    // Type declaration (class/interface/enum/record)
    non-sealed interface Handshake extends ClientPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Status extends ClientPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Login extends ClientPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Configuration extends ClientPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Play extends ClientPacket {
    // End of a block/expression
    }
// End of a block/expression
}