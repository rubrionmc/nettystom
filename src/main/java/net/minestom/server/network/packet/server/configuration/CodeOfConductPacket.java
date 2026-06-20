// Package declaration for this file
package net.minestom.server.network.packet.server.configuration;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record CodeOfConductPacket(
        // Code statement
        String codeOfConduct
// Start of a method/block
) implements ServerPacket.Configuration {
    // Assigns a value
    public static final NetworkBuffer.Type<CodeOfConductPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING, CodeOfConductPacket::codeOfConduct,
            // Code statement
            CodeOfConductPacket::new);
// End of a block/expression
}
