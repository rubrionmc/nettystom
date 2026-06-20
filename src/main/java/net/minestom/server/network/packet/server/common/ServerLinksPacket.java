// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
public record ServerLinksPacket(List<Entry> entries) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    private static final int MAX_ENTRIES = 100;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ServerLinksPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Entry.NETWORK_TYPE.list(MAX_ENTRIES), ServerLinksPacket::entries,
            // Instruction de code
            ServerLinksPacket::new);

    // Début d'une méthode/d'un bloc
    public ServerLinksPacket {
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ServerLinksPacket(Entry... entries) {
        // Appelle une méthode
        this(List.of(entries));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(Either<KnownLinkType, Component> linkType, String link) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.Either(KnownLinkType.NETWORK_TYPE, NetworkBuffer.COMPONENT), Entry::linkType,
                // Instruction de code
                NetworkBuffer.STRING, Entry::link,
                // Instruction de code
                Entry::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public Entry {
            // Appelle une méthode
            Objects.requireNonNull(linkType, "linkType");
            // Appelle une méthode
            Objects.requireNonNull(link, "link");
        // Fin d'un bloc/d'une expression
        }

        /**
         * @deprecated Use {@link #Entry(KnownLinkType, String)} or {@link #Entry(Component, String)} instead.
         */
        // Annotation pour l'élément suivant
        @Deprecated(forRemoval = true)
        // Début d'une méthode/d'un bloc
        public Entry(@Nullable KnownLinkType knownType, @Nullable Component customType, String link) {
            // Appelle une méthode
            this(knownType != null ? Either.left(knownType) : Either.right(customType), link);
        // Fin d'un bloc/d'une expression
        }

        /**
         * @deprecated Use {@link #linkType()} instead.
         */
        // Annotation pour l'élément suivant
        @Deprecated(forRemoval = true)
        // Début d'une méthode/d'un bloc
        public @Nullable KnownLinkType knownType() {
            // Renvoie une valeur à l'appelant
            return linkType.unify(Function.identity(), _ -> null);
        // Fin d'un bloc/d'une expression
        }

        /**
         * @deprecated Use {@link #linkType()} instead.
         */
        // Annotation pour l'élément suivant
        @Deprecated(forRemoval = true)
        // Début d'une méthode/d'un bloc
        public @Nullable Component customType() {
            // Renvoie une valeur à l'appelant
            return linkType.unify(_ -> null, Function.identity());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Entry(KnownLinkType type, String link) {
            // Appelle une méthode
            this(type, null, link);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Entry(Component type, String link) {
            // Appelle une méthode
            this(null, type, link);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum KnownLinkType {
        // Instruction de code
        BUG_REPORT,
        // Instruction de code
        COMMUNITY_GUIDELINES,
        // Instruction de code
        SUPPORT,
        // Instruction de code
        STATUS,
        // Instruction de code
        FEEDBACK,
        // Instruction de code
        COMMUNITY,
        // Instruction de code
        WEBSITE,
        // Instruction de code
        FORUMS,
        // Instruction de code
        NEWS,
        // Instruction de code
        ANNOUNCEMENTS;

        // Appelle une méthode
        public static final NetworkBuffer.Type<KnownLinkType> NETWORK_TYPE = NetworkBuffer.Enum(KnownLinkType.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
