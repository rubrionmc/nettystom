// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public record DebugPathInfo(Path path, float maxNodeDistance) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugPathInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Path.SERIALIZER, DebugPathInfo::path,
            // Code statement
            NetworkBuffer.FLOAT, DebugPathInfo::maxNodeDistance,
            // Code statement
            DebugPathInfo::new);

    // Type declaration (class/interface/enum/record)
    public record Path(boolean reached, int nextNodeIndex, Point target, List<Node> nodes, Data data) {
        // Assigns a value
        public static final NetworkBuffer.Type<Path> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.BOOLEAN, Path::reached,
                // Code statement
                NetworkBuffer.INT, Path::nextNodeIndex,
                // Code statement
                NetworkBuffer.BLOCK_POSITION, Path::target,
                // Code statement
                Node.SERIALIZER.list(), Path::nodes,
                // Code statement
                Data.SERIALIZER, Path::data,
                // Code statement
                Path::new);

        // Start of a method/block
        public Path {
            // Calls a method
            nodes = List.copyOf(nodes);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum NodeType {
        // Code statement
        BLOCKED,
        // Code statement
        OPEN,
        // Code statement
        WALKABLE,
        // Code statement
        WALKABLE_DOOR,
        // Code statement
        TRAPDOOR,
        // Code statement
        POWDER_SNOW,
        // Code statement
        DANGER_POWDER_SNOW,
        // Code statement
        FENCE,
        // Code statement
        LAVA,
        // Code statement
        WATER,
        // Code statement
        WATER_BORDER,
        // Code statement
        RAIL,
        // Code statement
        UNPASSABLE_RAIL,
        // Code statement
        DANGER_FIRE,
        // Code statement
        DAMAGE_FIRE,
        // Code statement
        DANGER_OTHER,
        // Code statement
        DAMAGE_OTHER,
        // Code statement
        DOOR_OPEN,
        // Code statement
        DOOR_WOOD_CLOSED,
        // Code statement
        DOOR_IRON_CLOSED,
        // Code statement
        BREACH,
        // Code statement
        LEAVES,
        // Code statement
        STICKY_HONEY,
        // Code statement
        COCOA,
        // Code statement
        DAMAGE_CAUTIOUS,
        // Code statement
        DANGER_TRAPDOOR;

        // Calls a method
        public static final NetworkBuffer.Type<NodeType> SERIALIZER = NetworkBuffer.Enum(NodeType.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Node(int x, int y, int z, float walkedDistance, float costMalus, boolean closed, NodeType type,
                       // Start of a method/block
                       float f) {
            // Assigns a value
            public static final NetworkBuffer.Type<Node> SERIALIZER = NetworkBufferTemplate.template(
                    // Code statement
                    NetworkBuffer.INT, Node::x,
                    // Code statement
                    NetworkBuffer.INT, Node::y,
                    // Code statement
                    NetworkBuffer.INT, Node::z,
                    // Code statement
                    NetworkBuffer.FLOAT, Node::walkedDistance,
                    // Code statement
                    NetworkBuffer.FLOAT, Node::costMalus,
                    // Code statement
                    NetworkBuffer.BOOLEAN, Node::closed,
                    // Code statement
                    NodeType.SERIALIZER, Node::type,
                    // Code statement
                    NetworkBuffer.FLOAT, Node::f,
                    // Code statement
                    Node::new);

    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Data(Set<Node> targetNodes, List<Node> openSet, List<Node> closedSet) {
        // Assigns a value
        public static final NetworkBuffer.Type<Data> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                Node.SERIALIZER.set(), Data::targetNodes,
                // Code statement
                Node.SERIALIZER.list(), Data::openSet,
                // Code statement
                Node.SERIALIZER.list(), Data::closedSet,
                // Code statement
                Data::new);

        // Start of a method/block
        public Data {
            // Calls a method
            targetNodes = Set.copyOf(targetNodes);
            // Calls a method
            openSet = List.copyOf(openSet);
            // Calls a method
            closedSet = List.copyOf(closedSet);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
