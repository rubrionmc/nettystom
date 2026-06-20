// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record ClearDialogPacket() implements ServerPacket.Configuration, ServerPacket.Play {
    // Calls a method
    public static final NetworkBuffer.Type<ClearDialogPacket> SERIALIZER = NetworkBufferTemplate.template(new ClearDialogPacket());
// End of a block/expression
}
