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
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

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
    public record Entry(@Nullable KnownLinkType knownType, @Nullable Component customType, String link) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> NETWORK_TYPE = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Entry value) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.BOOLEAN, value.knownType != null);
                // Embranchement : vérifie une condition
                if (value.knownType != null) {
                    // Appelle une méthode
                    buffer.write(KnownLinkType.NETWORK_TYPE, value.knownType);
                // Branche alternative de la condition
                } else {
                    // Instruction de code
                    assert value.customType != null;
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.COMPONENT, value.customType);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                buffer.write(NetworkBuffer.STRING, value.link);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Entry read(NetworkBuffer buffer) {
                // Appelle une méthode
                boolean known = buffer.read(NetworkBuffer.BOOLEAN);
                // Embranchement : vérifie une condition
                if (known) {
                    // Renvoie une valeur à l'appelant
                    return new Entry(buffer.read(KnownLinkType.NETWORK_TYPE), buffer.read(NetworkBuffer.STRING));
                // Branche alternative de la condition
                } else {
                    // Renvoie une valeur à l'appelant
                    return new Entry(buffer.read(NetworkBuffer.COMPONENT), buffer.read(NetworkBuffer.STRING));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Début d'une méthode/d'un bloc
        public Entry {
            // Appelle une méthode
            Check.argCondition(knownType == null && customType == null, "One of knownType and customType must be present");
            // Appelle une méthode
            Check.argCondition(knownType != null && customType != null, "Only one of knownType and customType may be present");
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
