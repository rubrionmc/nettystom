// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.coordinate.Point;
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
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public record TrackedWaypointPacket(
        // Code statement
        Operation operation,
        // Code statement
        Waypoint waypoint
// Start of a method/block
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<TrackedWaypointPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Operation.NETWORK_TYPE, TrackedWaypointPacket::operation,
            // Code statement
            Waypoint.NETWORK_TYPE, TrackedWaypointPacket::waypoint,
            // Code statement
            TrackedWaypointPacket::new);

    // Type declaration (class/interface/enum/record)
    public enum Operation {
        // Code statement
        TRACK,
        // Code statement
        UNTRACK,
        // Code statement
        UPDATE;

        // Calls a method
        public static final NetworkBuffer.Type<Operation> NETWORK_TYPE = NetworkBuffer.Enum(Operation.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Waypoint(
            // Code statement
            Either<UUID, String> id,
            // Code statement
            Icon icon,
            // Code statement
            Target target
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Waypoint> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.Either(NetworkBuffer.UUID, NetworkBuffer.STRING), Waypoint::id,
                // Code statement
                Icon.NETWORK_TYPE, Waypoint::icon,
                // Code statement
                Target.NETWORK_TYPE, Waypoint::target,
                // Code statement
                Waypoint::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Icon(
            // Code statement
            Key style,
            // Annotation for the following element
            @Nullable RGBLike color
    // Start of a method/block
    ) {
        // Calls a method
        public static final Key DEFAULT_STYLE = Key.key("default");
        // Calls a method
        public static final Icon DEFAULT = new Icon(DEFAULT_STYLE, null);

        // Assigns a value
        public static final NetworkBuffer.Type<Icon> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.KEY, Icon::style,
                // Code statement
                Color.RGB_BYTE_NETWORK_TYPE.optional(),
                // Code statement
                Icon::color,
                // Code statement
                Icon::new);

        // Start of a method/block
        public Icon(Key style) {
            // Calls a method
            this(style, null);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public sealed interface Target {
        // Assigns a value
        NetworkBuffer.Type<Target> NETWORK_TYPE = Type.NETWORK_TYPE
                // Calls a method
                .unionType(Target::dataSerializer, Target::targetToType);

        // Type declaration (class/interface/enum/record)
        record Empty() implements Target {
            // Calls a method
            public static final NetworkBuffer.Type<Empty> NETWORK_TYPE = NetworkBufferTemplate.template(new Empty());
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record Vec3i(Point point) implements Target {
            // Assigns a value
            public static final NetworkBuffer.Type<Vec3i> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Code statement
                    NetworkBuffer.VECTOR3I, Vec3i::point,
                    // Code statement
                    Vec3i::new);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record Chunk(int chunkX, int chunkZ) implements Target {
            // Assigns a value
            public static final NetworkBuffer.Type<Chunk> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Code statement
                    NetworkBuffer.VAR_INT, Chunk::chunkX,
                    // Code statement
                    NetworkBuffer.VAR_INT, Chunk::chunkZ,
                    // Code statement
                    Chunk::new);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record Azimuth(float angle) implements Target {
            // Assigns a value
            public static final NetworkBuffer.Type<Azimuth> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Code statement
                    NetworkBuffer.FLOAT, Azimuth::angle,
                    // Code statement
                    Azimuth::new);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        enum Type {
            // Code statement
            EMPTY, VEC3I, CHUNK, AZIMUTH;

            // Calls a method
            public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
        // End of a block/expression
        }

        // Start of a method/block
        private static NetworkBuffer.Type<? extends Target> dataSerializer(Type type) {
            // Returns a value to the caller
            return switch (type) {
                // Multiple branching (switch/case)
                case EMPTY -> Empty.NETWORK_TYPE;
                // Multiple branching (switch/case)
                case VEC3I -> Vec3i.NETWORK_TYPE;
                // Multiple branching (switch/case)
                case CHUNK -> Chunk.NETWORK_TYPE;
                // Multiple branching (switch/case)
                case AZIMUTH -> Azimuth.NETWORK_TYPE;
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        private static Type targetToType(Target target) {
            // Returns a value to the caller
            return switch (target) {
                // Multiple branching (switch/case)
                case Empty ignored -> Type.EMPTY;
                // Multiple branching (switch/case)
                case Vec3i ignored -> Type.VEC3I;
                // Multiple branching (switch/case)
                case Chunk ignored -> Type.CHUNK;
                // Multiple branching (switch/case)
                case Azimuth ignored -> Type.AZIMUTH;
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}