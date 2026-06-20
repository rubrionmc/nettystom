// Package declaration for this file
package net.minestom.server.network.packet.server.configuration;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.NBT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record RegistryDataPacket(
        // Code statement
        String registryId,
        // Code statement
        List<Entry> entries
// Start of a method/block
) implements ServerPacket.Configuration {
    // Assigns a value
    public static final NetworkBuffer.Type<RegistryDataPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, RegistryDataPacket::registryId,
            // Code statement
            Entry.SERIALIZER.list(Integer.MAX_VALUE), RegistryDataPacket::entries,
            // Code statement
            RegistryDataPacket::new);

    // Start of a method/block
    public RegistryDataPacket {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry(
            // Code statement
            String id,
            // Annotation for the following element
            @Nullable BinaryTag data
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Calls a method
                STRING, Entry::id, NBT.optional(), Entry::data, Entry::new);
    // End of a block/expression
    }
// End of a block/expression
}
