// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public record DebugPathInfo(Path path, float maxNodeDistance) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugPathInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Path.SERIALIZER, DebugPathInfo::path,
            // Instruction de code
            NetworkBuffer.FLOAT, DebugPathInfo::maxNodeDistance,
            // Instruction de code
            DebugPathInfo::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Path(boolean reached, int nextNodeIndex, Point target, List<Node> nodes, Data data) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Path> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.BOOLEAN, Path::reached,
                // Instruction de code
                NetworkBuffer.INT, Path::nextNodeIndex,
                // Instruction de code
                NetworkBuffer.BLOCK_POSITION, Path::target,
                // Instruction de code
                Node.SERIALIZER.list(), Path::nodes,
                // Instruction de code
                Data.SERIALIZER, Path::data,
                // Instruction de code
                Path::new);

        // Début d'une méthode/d'un bloc
        public Path {
            // Appelle une méthode
            nodes = List.copyOf(nodes);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum NodeType {
        // Instruction de code
        BLOCKED,
        // Instruction de code
        OPEN,
        // Instruction de code
        WALKABLE,
        // Instruction de code
        WALKABLE_DOOR,
        // Instruction de code
        TRAPDOOR,
        // Instruction de code
        POWDER_SNOW,
        // Instruction de code
        DANGER_POWDER_SNOW,
        // Instruction de code
        FENCE,
        // Instruction de code
        LAVA,
        // Instruction de code
        WATER,
        // Instruction de code
        WATER_BORDER,
        // Instruction de code
        RAIL,
        // Instruction de code
        UNPASSABLE_RAIL,
        // Instruction de code
        DANGER_FIRE,
        // Instruction de code
        DAMAGE_FIRE,
        // Instruction de code
        DANGER_OTHER,
        // Instruction de code
        DAMAGE_OTHER,
        // Instruction de code
        DOOR_OPEN,
        // Instruction de code
        DOOR_WOOD_CLOSED,
        // Instruction de code
        DOOR_IRON_CLOSED,
        // Instruction de code
        BREACH,
        // Instruction de code
        LEAVES,
        // Instruction de code
        STICKY_HONEY,
        // Instruction de code
        COCOA,
        // Instruction de code
        DAMAGE_CAUTIOUS,
        // Instruction de code
        DANGER_TRAPDOOR;

        // Appelle une méthode
        public static final NetworkBuffer.Type<NodeType> SERIALIZER = NetworkBuffer.Enum(NodeType.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Node(int x, int y, int z, float walkedDistance, float costMalus, boolean closed, NodeType type,
                       // Début d'une méthode/d'un bloc
                       float f) {
            // Affecte une valeur
            public static final NetworkBuffer.Type<Node> SERIALIZER = NetworkBufferTemplate.template(
                    // Instruction de code
                    NetworkBuffer.INT, Node::x,
                    // Instruction de code
                    NetworkBuffer.INT, Node::y,
                    // Instruction de code
                    NetworkBuffer.INT, Node::z,
                    // Instruction de code
                    NetworkBuffer.FLOAT, Node::walkedDistance,
                    // Instruction de code
                    NetworkBuffer.FLOAT, Node::costMalus,
                    // Instruction de code
                    NetworkBuffer.BOOLEAN, Node::closed,
                    // Instruction de code
                    NodeType.SERIALIZER, Node::type,
                    // Instruction de code
                    NetworkBuffer.FLOAT, Node::f,
                    // Instruction de code
                    Node::new);

    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Data(Set<Node> targetNodes, List<Node> openSet, List<Node> closedSet) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Data> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                Node.SERIALIZER.set(), Data::targetNodes,
                // Instruction de code
                Node.SERIALIZER.list(), Data::openSet,
                // Instruction de code
                Node.SERIALIZER.list(), Data::closedSet,
                // Instruction de code
                Data::new);

        // Début d'une méthode/d'un bloc
        public Data {
            // Appelle une méthode
            targetNodes = Set.copyOf(targetNodes);
            // Appelle une méthode
            openSet = List.copyOf(openSet);
            // Appelle une méthode
            closedSet = List.copyOf(closedSet);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
