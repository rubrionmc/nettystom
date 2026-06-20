// Package declaration for this file
package net.minestom.server.network.packet.client.configuration;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record ClientSelectKnownPacksPacket(
        // Code statement
        List<SelectKnownPacksPacket.Entry> entries
// Start of a method/block
) implements ClientPacket.Configuration {
    // Assigns a value
    private static final int MAX_ENTRIES = 64;

    // Assigns a value
    public static final NetworkBuffer.Type<ClientSelectKnownPacksPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            SelectKnownPacksPacket.Entry.SERIALIZER.list(MAX_ENTRIES), ClientSelectKnownPacksPacket::entries,
            // Code statement
            ClientSelectKnownPacksPacket::new);

    // Start of a method/block
    public ClientSelectKnownPacksPacket {
        // Calls a method
        Check.argCondition(entries.size() > MAX_ENTRIES, "Too many known packs: {0} > {1}", entries.size(), MAX_ENTRIES);
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }
// End of a block/expression
}
