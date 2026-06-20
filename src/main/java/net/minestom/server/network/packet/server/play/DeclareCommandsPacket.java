// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record DeclareCommandsPacket(List<Node> nodes,
                                    // Début d'une méthode/d'un bloc
                                    int rootIndex) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_NODES = Short.MAX_VALUE;

    // Début d'une méthode/d'un bloc
    public DeclareCommandsPacket {
        // Appelle une méthode
        nodes = List.copyOf(nodes);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<DeclareCommandsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Node.SERIALIZER.list(MAX_NODES), DeclareCommandsPacket::nodes,
            // Instruction de code
            VAR_INT, DeclareCommandsPacket::rootIndex,
            // Instruction de code
            DeclareCommandsPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    public static final int NODE_TYPE = 0x03;
    // Affecte une valeur
    public static final int IS_EXECUTABLE = 0x04;
    // Affecte une valeur
    public static final int HAS_REDIRECT = 0x08;
    // Affecte une valeur
    public static final int HAS_SUGGESTION_TYPE = 0x10;

    // Début d'une méthode/d'un bloc
    public static final class Node {
        // Instruction de code
        public byte flags;
        // Affecte une valeur
        public int[] children = new int[0];
        // Instruction de code
        public int redirectedNode; // Only if flags & 0x08
        // Affecte une valeur
        public String name = ""; // Only for literal and argument
        // Instruction de code
        public ArgumentParserType parser; // Only for argument
        // Instruction de code
        public byte[] properties; // Only for argument
        // Affecte une valeur
        public String suggestionsType = ""; // Only if flags 0x10

        // Affecte une valeur
        public static final NetworkBuffer.Type<Node> SERIALIZER = new Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer writer, Node value) {
                // Appelle une méthode
                writer.write(BYTE, value.flags);

                // Embranchement : vérifie une condition
                if (value.children != null && value.children.length > 262114) {
                    // Lève une exception
                    throw new RuntimeException("Children length " + value.children.length + " is bigger than the maximum allowed " + 262114);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                writer.write(VAR_INT_ARRAY, value.children);

                // Embranchement : vérifie une condition
                if ((value.flags & HAS_REDIRECT) != 0) {
                    // Appelle une méthode
                    writer.write(VAR_INT, value.redirectedNode);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (value.isLiteral() || value.isArgument()) {
                    // Appelle une méthode
                    writer.write(STRING, value.name);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (value.isArgument()) {
                    // Appelle une méthode
                    writer.write(ArgumentParserType.NETWORK_TYPE, value.parser);
                    // Embranchement : vérifie une condition
                    if (value.properties != null) {
                        // Appelle une méthode
                        writer.write(RAW_BYTES, value.properties);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if ((value.flags & HAS_SUGGESTION_TYPE) != 0) {
                    // Appelle une méthode
                    writer.write(STRING, value.suggestionsType);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public Node read(NetworkBuffer reader) {
                // Appelle une méthode
                Node node = new Node();
                // Appelle une méthode
                node.flags = reader.read(BYTE);
                // Appelle une méthode
                node.children = reader.read(VAR_INT_ARRAY);
                // Embranchement : vérifie une condition
                if ((node.flags & HAS_REDIRECT) != 0) {
                    // Appelle une méthode
                    node.redirectedNode = reader.read(VAR_INT);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (node.isLiteral() || node.isArgument()) {
                    // Appelle une méthode
                    node.name = reader.read(STRING);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (node.isArgument()) {
                    // Appelle une méthode
                    node.parser = reader.read(ArgumentParserType.NETWORK_TYPE);
                    // Appelle une méthode
                    node.properties = node.getProperties(reader, node.parser);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if ((node.flags & HAS_SUGGESTION_TYPE) != 0) {
                    // Appelle une méthode
                    node.suggestionsType = reader.read(STRING);
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return node;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Début d'une méthode/d'un bloc
        private byte[] getProperties(NetworkBuffer reader, ArgumentParserType parser) {
            // Affecte une valeur
            final Function<Function<NetworkBuffer, ?>, byte[]> minMaxExtractor = (via) -> reader.extractBytes((extractor) -> {
                // Appelle une méthode
                byte flags = extractor.read(BYTE);
                // Embranchement : vérifie une condition
                if ((flags & 0x01) == 0x01) {
                    // Instruction de code
                    via.apply(extractor); // min
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if ((flags & 0x02) == 0x02) {
                    // Instruction de code
                    via.apply(extractor); // max
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
            // Renvoie une valeur à l'appelant
            return switch (parser) {
                // Embranchement multiple (switch/case)
                case DOUBLE -> minMaxExtractor.apply(b -> b.read(DOUBLE));
                // Embranchement multiple (switch/case)
                case INTEGER -> minMaxExtractor.apply(b -> b.read(INT));
                // Embranchement multiple (switch/case)
                case FLOAT -> minMaxExtractor.apply(b -> b.read(FLOAT));
                // Embranchement multiple (switch/case)
                case LONG -> minMaxExtractor.apply(b -> b.read(LONG));
                // Embranchement multiple (switch/case)
                case STRING -> reader.extractBytes(b -> b.read(VAR_INT));
                // Embranchement multiple (switch/case)
                case ENTITY, SCORE_HOLDER -> reader.extractBytes(b -> b.read(BYTE));
                // Embranchement multiple (switch/case)
                case TIME -> reader.extractBytes(b -> b.read(INT));
                // Embranchement multiple (switch/case)
                case RESOURCE_OR_TAG, RESOURCE_OR_TAG_KEY, RESOURCE, RESOURCE_KEY -> reader.extractBytes(b -> b.read(STRING));
                // Instruction de code
                default -> new byte[0]; // unknown
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private boolean isLiteral() {
            // Renvoie une valeur à l'appelant
            return (flags & 0b1) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private boolean isArgument() {
            // Renvoie une valeur à l'appelant
            return (flags & 0b10) != 0;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static byte getFlag(NodeType type, boolean executable, boolean redirect, boolean suggestionType) {
        // Appelle une méthode
        byte result = (byte) type.ordinal();
        // Embranchement : vérifie une condition
        if (executable) result |= 0x04;
        // Embranchement : vérifie une condition
        if (redirect) result |= 0x08;
        // Embranchement : vérifie une condition
        if (suggestionType) result |= 0x10;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum NodeType {
        // Instruction de code
        ROOT, LITERAL, ARGUMENT, NONE;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
