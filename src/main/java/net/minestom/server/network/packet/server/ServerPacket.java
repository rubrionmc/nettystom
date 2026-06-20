// Package declaration for this file
package net.minestom.server.network.packet.server;

// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.network.packet.Packet;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;

/**
 * Represents a packet which can be sent to a player using {@link PlayerConnection#sendPacket(SendablePacket)}.
 * <p>
 * Packets are value-based, and should therefore not be reliant on identity.
 */
// Type declaration (class/interface/enum/record)
public sealed interface ServerPacket extends Packet, SendablePacket {

    // By default, this isn't used
    // Type declaration (class/interface/enum/record)
    non-sealed interface Handshake extends ServerPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Status extends ServerPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Login extends ServerPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Configuration extends ServerPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    non-sealed interface Play extends ServerPacket {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    interface ComponentHolding extends ComponentHolder<ServerPacket> {
    // End of a block/expression
    }
// End of a block/expression
}
