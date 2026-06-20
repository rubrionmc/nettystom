// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public record ServerLinksPacket(List<Entry> entries) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    private static final int MAX_ENTRIES = 100;

    // Assigns a value
    public static final NetworkBuffer.Type<ServerLinksPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Entry.NETWORK_TYPE.list(MAX_ENTRIES), ServerLinksPacket::entries,
            // Code statement
            ServerLinksPacket::new);

    // Start of a method/block
    public ServerLinksPacket {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Start of a method/block
    public ServerLinksPacket(Entry... entries) {
        // Calls a method
        this(List.of(entries));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry(Either<KnownLinkType, Component> linkType, String link) {
        // Assigns a value
        public static final NetworkBuffer.Type<Entry> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.Either(KnownLinkType.NETWORK_TYPE, NetworkBuffer.COMPONENT), Entry::linkType,
                // Code statement
                NetworkBuffer.STRING, Entry::link,
                // Code statement
                Entry::new
        // End of a block/expression
        );

        // Start of a method/block
        public Entry {
            // Calls a method
            Objects.requireNonNull(linkType, "linkType");
            // Calls a method
            Objects.requireNonNull(link, "link");
        // End of a block/expression
        }

        /**
         * @deprecated Use {@link #Entry(KnownLinkType, String)} or {@link #Entry(Component, String)} instead.
         */
        // Annotation for the following element
        @Deprecated(forRemoval = true)
        // Start of a method/block
        public Entry(@Nullable KnownLinkType knownType, @Nullable Component customType, String link) {
            // Calls a method
            this(knownType != null ? Either.left(knownType) : Either.right(customType), link);
        // End of a block/expression
        }

        /**
         * @deprecated Use {@link #linkType()} instead.
         */
        // Annotation for the following element
        @Deprecated(forRemoval = true)
        // Start of a method/block
        public @Nullable KnownLinkType knownType() {
            // Returns a value to the caller
            return linkType.unify(Function.identity(), _ -> null);
        // End of a block/expression
        }

        /**
         * @deprecated Use {@link #linkType()} instead.
         */
        // Annotation for the following element
        @Deprecated(forRemoval = true)
        // Start of a method/block
        public @Nullable Component customType() {
            // Returns a value to the caller
            return linkType.unify(_ -> null, Function.identity());
        // End of a block/expression
        }

        // Start of a method/block
        public Entry(KnownLinkType type, String link) {
            // Calls a method
            this(type, null, link);
        // End of a block/expression
        }

        // Start of a method/block
        public Entry(Component type, String link) {
            // Calls a method
            this(null, type, link);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum KnownLinkType {
        // Code statement
        BUG_REPORT,
        // Code statement
        COMMUNITY_GUIDELINES,
        // Code statement
        SUPPORT,
        // Code statement
        STATUS,
        // Code statement
        FEEDBACK,
        // Code statement
        COMMUNITY,
        // Code statement
        WEBSITE,
        // Code statement
        FORUMS,
        // Code statement
        NEWS,
        // Code statement
        ANNOUNCEMENTS;

        // Calls a method
        public static final NetworkBuffer.Type<KnownLinkType> NETWORK_TYPE = NetworkBuffer.Enum(KnownLinkType.class);
    // End of a block/expression
    }
// End of a block/expression
}
