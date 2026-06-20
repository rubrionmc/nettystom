// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.Rotation;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientUpdateStructureBlockPacket(
        // Instruction de code
        Point location, Action action,
        // Instruction de code
        Mode mode, String name,
        // Instruction de code
        Point offset, Point size,
        // Instruction de code
        Mirror mirror, Rotation rotation,
        // Instruction de code
        String metadata, float integrity,
        // Instruction de code
        long seed, byte flags
// Début d'une méthode/d'un bloc
) implements ClientPacket {

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientUpdateStructureBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, ClientUpdateStructureBlockPacket::location,
            // Instruction de code
            NetworkBuffer.Enum(Action.class), ClientUpdateStructureBlockPacket::action,
            // Instruction de code
            NetworkBuffer.Enum(Mode.class), ClientUpdateStructureBlockPacket::mode,
            // Instruction de code
            STRING, ClientUpdateStructureBlockPacket::name,
            // Instruction de code
            VECTOR3B, ClientUpdateStructureBlockPacket::offset,
            // Instruction de code
            VECTOR3B, ClientUpdateStructureBlockPacket::size,
            // Instruction de code
            Enum(Mirror.class), ClientUpdateStructureBlockPacket::mirror,
            // Instruction de code
            VAR_INT.transform(ClientUpdateStructureBlockPacket::fromRestrictedRotation, ClientUpdateStructureBlockPacket::toRestrictedRotation), ClientUpdateStructureBlockPacket::rotation,
            // Instruction de code
            STRING, ClientUpdateStructureBlockPacket::metadata,
            // Instruction de code
            FLOAT, ClientUpdateStructureBlockPacket::integrity,
            // Instruction de code
            LONG, ClientUpdateStructureBlockPacket::seed,
            // Instruction de code
            BYTE, ClientUpdateStructureBlockPacket::flags,
            // Instruction de code
            ClientUpdateStructureBlockPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Flag values
    // Affecte une valeur
    public static final byte IGNORE_ENTITIES = 0x1;
    // Affecte une valeur
    public static final byte SHOW_AIR = 0x2;
    /**
     * Requires the player to be in creative and have a permission level higher than 2.
     */
    // Affecte une valeur
    public static final byte SHOW_BOUNDING_BOX = 0x4;
    // Affecte une valeur
    public static final byte STRICT = 0x8;

    /**
     * Update action, <code>UPDATE_DATA</code> indicates nothing special.
     */
    // Déclaration de type (classe/interface/enum/record)
    public enum Action {
        // Instruction de code
        UPDATE_DATA, SAVE, LOAD, DETECT_SIZE
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Mode {
        // Instruction de code
        SAVE, LOAD, CORNER, DATA
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Mirror {
        // Instruction de code
        NONE, LEFT_RIGHT, FRONT_BACK
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int toRestrictedRotation(Rotation rotation) {
        // Renvoie une valeur à l'appelant
        return switch (rotation) {
            // Embranchement multiple (switch/case)
            case NONE -> 0;
            // Embranchement multiple (switch/case)
            case CLOCKWISE -> 1;
            // Embranchement multiple (switch/case)
            case FLIPPED -> 2;
            // Embranchement multiple (switch/case)
            case COUNTER_CLOCKWISE -> 3;
            // Instruction de code
            default ->
                    // Lève une exception
                    throw new IllegalArgumentException("ClientUpdateStructurePacket#rotation must be a valid 90-degree rotation.");
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Rotation fromRestrictedRotation(int rotation) {
        // Renvoie une valeur à l'appelant
        return switch (rotation) {
            // Embranchement multiple (switch/case)
            case 0 -> Rotation.NONE;
            // Embranchement multiple (switch/case)
            case 1 -> Rotation.CLOCKWISE;
            // Embranchement multiple (switch/case)
            case 2 -> Rotation.FLIPPED;
            // Embranchement multiple (switch/case)
            case 3 -> Rotation.COUNTER_CLOCKWISE;
            // Instruction de code
            default ->
                    // Lève une exception
                    throw new IllegalArgumentException("ClientUpdateStructurePacket#rotation must be a valid 90-degree rotation.");
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
