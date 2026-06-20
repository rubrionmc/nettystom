// Package declaration for this file
package net.minestom.server.network.packet.server.configuration;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record SelectKnownPacksPacket(
        // Code statement
        List<Entry> entries
// Start of a method/block
) implements ServerPacket.Configuration {
    // Assigns a value
    private static final int MAX_ENTRIES = 64;
    // Calls a method
    public static final Entry MINECRAFT_CORE = new Entry("minecraft", "core", MinecraftServer.VERSION_NAME);

    // Assigns a value
    public static final NetworkBuffer.Type<SelectKnownPacksPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Entry.SERIALIZER.list(MAX_ENTRIES), SelectKnownPacksPacket::entries,
            // Code statement
            SelectKnownPacksPacket::new);

    // Start of a method/block
    public SelectKnownPacksPacket {
        // Calls a method
        Check.argCondition(entries.size() > MAX_ENTRIES, "Too many known packs: {0} > {1}", entries.size(), MAX_ENTRIES);
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry(
            // Code statement
            String namespace,
            // Code statement
            String id,
            // Code statement
            String version
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING, Entry::namespace,
                // Code statement
                NetworkBuffer.STRING, Entry::id,
                // Code statement
                NetworkBuffer.STRING, Entry::version,
                // Code statement
                Entry::new);
    // End of a block/expression
    }
// End of a block/expression
}
