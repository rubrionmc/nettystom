// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record LowDiskSpaceWarningPacket() implements ServerPacket.Play {
    // Calls a method
    public static final NetworkBuffer.Type<LowDiskSpaceWarningPacket> SERIALIZER = NetworkBufferTemplate.template(new LowDiskSpaceWarningPacket());
// End of a block/expression
}
