// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT_ARRAY;

// Type declaration (class/interface/enum/record)
public record TagsPacket(List<Registry> registries) implements ServerPacket.Configuration, ServerPacket.Play {
    // Start of a method/block
    public TagsPacket {
        // Calls a method
        registries = List.copyOf(registries);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<TagsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Registry.SERIALIZER.list(), TagsPacket::registries,
            // Code statement
            TagsPacket::new
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    public record Registry(String registry, List<Tag> tags) {
        // Assigns a value
        public static final NetworkBuffer.Type<Registry> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, Registry::registry,
                // Code statement
                Tag.SERIALIZER.list(), Registry::tags,
                // Code statement
                Registry::new
        // End of a block/expression
        );

        // Start of a method/block
        public Registry {
            // Calls a method
            tags = List.copyOf(tags);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Tag(String identifier, int[] entries) {
        // Assigns a value
        public static final NetworkBuffer.Type<Tag> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, Tag::identifier,
                // Code statement
                VAR_INT_ARRAY, Tag::entries,
                // Code statement
                Tag::new
        // End of a block/expression
        );

        // Start of a method/block
        public Tag {
            // Calls a method
            entries = entries.clone();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean equals(Object object) {
            // Branch: checks a condition
            if (!(object instanceof Tag(String identifier1, int[] entries1))) return false;
            // Returns a value to the caller
            return Arrays.equals(entries(), entries1) && Objects.equals(identifier(), identifier1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int hashCode() {
            // Calls a method
            int result = Objects.hashCode(identifier());
            // Calls a method
            result = 31 * result + Arrays.hashCode(entries());
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
