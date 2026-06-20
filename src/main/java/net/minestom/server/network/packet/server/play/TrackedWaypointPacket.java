// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
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
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public record TrackedWaypointPacket(
        // Instruction de code
        Operation operation,
        // Instruction de code
        Waypoint waypoint
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<TrackedWaypointPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Operation.NETWORK_TYPE, TrackedWaypointPacket::operation,
            // Instruction de code
            Waypoint.NETWORK_TYPE, TrackedWaypointPacket::waypoint,
            // Instruction de code
            TrackedWaypointPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Operation {
        // Instruction de code
        TRACK,
        // Instruction de code
        UNTRACK,
        // Instruction de code
        UPDATE;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Operation> NETWORK_TYPE = NetworkBuffer.Enum(Operation.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Waypoint(
            // Instruction de code
            Either<UUID, String> id,
            // Instruction de code
            Icon icon,
            // Instruction de code
            Target target
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Waypoint> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.Either(NetworkBuffer.UUID, NetworkBuffer.STRING), Waypoint::id,
                // Instruction de code
                Icon.NETWORK_TYPE, Waypoint::icon,
                // Instruction de code
                Target.NETWORK_TYPE, Waypoint::target,
                // Instruction de code
                Waypoint::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Icon(
            // Instruction de code
            Key style,
            // Annotation pour l'élément suivant
            @Nullable RGBLike color
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        public static final Key DEFAULT_STYLE = Key.key("default");
        // Appelle une méthode
        public static final Icon DEFAULT = new Icon(DEFAULT_STYLE, null);

        // Affecte une valeur
        public static final NetworkBuffer.Type<Icon> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.KEY, Icon::style,
                // Instruction de code
                Color.RGB_BYTE_NETWORK_TYPE.optional(),
                // Instruction de code
                Icon::color,
                // Instruction de code
                Icon::new);

        // Début d'une méthode/d'un bloc
        public Icon(Key style) {
            // Appelle une méthode
            this(style, null);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Target {
        // Affecte une valeur
        NetworkBuffer.Type<Target> NETWORK_TYPE = Type.NETWORK_TYPE
                // Appelle une méthode
                .unionType(Target::dataSerializer, Target::targetToType);

        // Déclaration de type (classe/interface/enum/record)
        record Empty() implements Target {
            // Appelle une méthode
            public static final NetworkBuffer.Type<Empty> NETWORK_TYPE = NetworkBufferTemplate.template(new Empty());
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record Vec3i(Point point) implements Target {
            // Affecte une valeur
            public static final NetworkBuffer.Type<Vec3i> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Instruction de code
                    NetworkBuffer.VECTOR3I, Vec3i::point,
                    // Instruction de code
                    Vec3i::new);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record Chunk(int chunkX, int chunkZ) implements Target {
            // Affecte une valeur
            public static final NetworkBuffer.Type<Chunk> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Instruction de code
                    NetworkBuffer.VAR_INT, Chunk::chunkX,
                    // Instruction de code
                    NetworkBuffer.VAR_INT, Chunk::chunkZ,
                    // Instruction de code
                    Chunk::new);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record Azimuth(float angle) implements Target {
            // Affecte une valeur
            public static final NetworkBuffer.Type<Azimuth> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Instruction de code
                    NetworkBuffer.FLOAT, Azimuth::angle,
                    // Instruction de code
                    Azimuth::new);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        enum Type {
            // Instruction de code
            EMPTY, VEC3I, CHUNK, AZIMUTH;

            // Appelle une méthode
            public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static NetworkBuffer.Type<? extends Target> dataSerializer(Type type) {
            // Renvoie une valeur à l'appelant
            return switch (type) {
                // Embranchement multiple (switch/case)
                case EMPTY -> Empty.NETWORK_TYPE;
                // Embranchement multiple (switch/case)
                case VEC3I -> Vec3i.NETWORK_TYPE;
                // Embranchement multiple (switch/case)
                case CHUNK -> Chunk.NETWORK_TYPE;
                // Embranchement multiple (switch/case)
                case AZIMUTH -> Azimuth.NETWORK_TYPE;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static Type targetToType(Target target) {
            // Renvoie une valeur à l'appelant
            return switch (target) {
                // Embranchement multiple (switch/case)
                case Empty ignored -> Type.EMPTY;
                // Embranchement multiple (switch/case)
                case Vec3i ignored -> Type.VEC3I;
                // Embranchement multiple (switch/case)
                case Chunk ignored -> Type.CHUNK;
                // Embranchement multiple (switch/case)
                case Azimuth ignored -> Type.AZIMUTH;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}