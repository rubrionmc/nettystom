// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record DeclareCommandsPacket(List<Node> nodes,
                                    // Start of a method/block
                                    int rootIndex) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_NODES = Short.MAX_VALUE;

    // Start of a method/block
    public DeclareCommandsPacket {
        // Calls a method
        nodes = List.copyOf(nodes);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<DeclareCommandsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Node.SERIALIZER.list(MAX_NODES), DeclareCommandsPacket::nodes,
            // Code statement
            VAR_INT, DeclareCommandsPacket::rootIndex,
            // Code statement
            DeclareCommandsPacket::new
    // End of a block/expression
    );

    // Assigns a value
    public static final int NODE_TYPE = 0x03;
    // Assigns a value
    public static final int IS_EXECUTABLE = 0x04;
    // Assigns a value
    public static final int HAS_REDIRECT = 0x08;
    // Assigns a value
    public static final int HAS_SUGGESTION_TYPE = 0x10;

    // Start of a method/block
    public static final class Node {
        // Code statement
        public byte flags;
        // Assigns a value
        public int[] children = new int[0];
        // Code statement
        public int redirectedNode; // Only if flags & 0x08
        // Assigns a value
        public String name = ""; // Only for literal and argument
        // Code statement
        public ArgumentParserType parser; // Only for argument
        // Code statement
        public byte[] properties; // Only for argument
        // Assigns a value
        public String suggestionsType = ""; // Only if flags 0x10

        // Assigns a value
        public static final NetworkBuffer.Type<Node> SERIALIZER = new Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer writer, Node value) {
                // Calls a method
                writer.write(BYTE, value.flags);

                // Branch: checks a condition
                if (value.children != null && value.children.length > 262114) {
                    // Throws an exception
                    throw new RuntimeException("Children length " + value.children.length + " is bigger than the maximum allowed " + 262114);
                // End of a block/expression
                }
                // Calls a method
                writer.write(VAR_INT_ARRAY, value.children);

                // Branch: checks a condition
                if ((value.flags & HAS_REDIRECT) != 0) {
                    // Calls a method
                    writer.write(VAR_INT, value.redirectedNode);
                // End of a block/expression
                }

                // Branch: checks a condition
                if (value.isLiteral() || value.isArgument()) {
                    // Calls a method
                    writer.write(STRING, value.name);
                // End of a block/expression
                }

                // Branch: checks a condition
                if (value.isArgument()) {
                    // Calls a method
                    writer.write(ArgumentParserType.NETWORK_TYPE, value.parser);
                    // Branch: checks a condition
                    if (value.properties != null) {
                        // Calls a method
                        writer.write(RAW_BYTES, value.properties);
                    // End of a block/expression
                    }
                // End of a block/expression
                }

                // Branch: checks a condition
                if ((value.flags & HAS_SUGGESTION_TYPE) != 0) {
                    // Calls a method
                    writer.write(STRING, value.suggestionsType);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Start of a method/block
            public Node read(NetworkBuffer reader) {
                // Calls a method
                Node node = new Node();
                // Calls a method
                node.flags = reader.read(BYTE);
                // Calls a method
                node.children = reader.read(VAR_INT_ARRAY);
                // Branch: checks a condition
                if ((node.flags & HAS_REDIRECT) != 0) {
                    // Calls a method
                    node.redirectedNode = reader.read(VAR_INT);
                // End of a block/expression
                }

                // Branch: checks a condition
                if (node.isLiteral() || node.isArgument()) {
                    // Calls a method
                    node.name = reader.read(STRING);
                // End of a block/expression
                }

                // Branch: checks a condition
                if (node.isArgument()) {
                    // Calls a method
                    node.parser = reader.read(ArgumentParserType.NETWORK_TYPE);
                    // Calls a method
                    node.properties = node.getProperties(reader, node.parser);
                // End of a block/expression
                }

                // Branch: checks a condition
                if ((node.flags & HAS_SUGGESTION_TYPE) != 0) {
                    // Calls a method
                    node.suggestionsType = reader.read(STRING);
                // End of a block/expression
                }
                // Returns a value to the caller
                return node;
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Start of a method/block
        private byte[] getProperties(NetworkBuffer reader, ArgumentParserType parser) {
            // Assigns a value
            final Function<Function<NetworkBuffer, ?>, byte[]> minMaxExtractor = (via) -> reader.extractBytes((extractor) -> {
                // Calls a method
                byte flags = extractor.read(BYTE);
                // Branch: checks a condition
                if ((flags & 0x01) == 0x01) {
                    // Code statement
                    via.apply(extractor); // min
                // End of a block/expression
                }
                // Branch: checks a condition
                if ((flags & 0x02) == 0x02) {
                    // Code statement
                    via.apply(extractor); // max
                // End of a block/expression
                }
            // End of a block/expression
            });
            // Returns a value to the caller
            return switch (parser) {
                // Multiple branching (switch/case)
                case DOUBLE -> minMaxExtractor.apply(b -> b.read(DOUBLE));
                // Multiple branching (switch/case)
                case INTEGER -> minMaxExtractor.apply(b -> b.read(INT));
                // Multiple branching (switch/case)
                case FLOAT -> minMaxExtractor.apply(b -> b.read(FLOAT));
                // Multiple branching (switch/case)
                case LONG -> minMaxExtractor.apply(b -> b.read(LONG));
                // Multiple branching (switch/case)
                case STRING -> reader.extractBytes(b -> b.read(VAR_INT));
                // Multiple branching (switch/case)
                case ENTITY, SCORE_HOLDER -> reader.extractBytes(b -> b.read(BYTE));
                // Multiple branching (switch/case)
                case TIME -> reader.extractBytes(b -> b.read(INT));
                // Multiple branching (switch/case)
                case RESOURCE_OR_TAG, RESOURCE_OR_TAG_KEY, RESOURCE, RESOURCE_KEY, RESOURCE_SELECTOR -> reader.extractBytes(b -> b.read(STRING));
                // Multiple branching (switch/case)
                default -> new byte[0]; // unknown
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        private boolean isLiteral() {
            // Returns a value to the caller
            return (flags & 0b1) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        private boolean isArgument() {
            // Returns a value to the caller
            return (flags & 0b10) != 0;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static byte getFlag(NodeType type, boolean executable, boolean redirect, boolean suggestionType) {
        // Calls a method
        byte result = (byte) type.ordinal();
        // Branch: checks a condition
        if (executable) result |= 0x04;
        // Branch: checks a condition
        if (redirect) result |= 0x08;
        // Branch: checks a condition
        if (suggestionType) result |= 0x10;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum NodeType {
        // Code statement
        ROOT, LITERAL, ARGUMENT, NONE
    // End of a block/expression
    }
// End of a block/expression
}
