// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public sealed interface Packet permits ClientPacket, ServerPacket {
// End of a block/expression
}
