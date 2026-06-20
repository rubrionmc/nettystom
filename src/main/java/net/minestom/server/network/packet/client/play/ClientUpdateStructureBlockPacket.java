// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.Rotation;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientUpdateStructureBlockPacket(
        // Code statement
        Point location, Action action,
        // Code statement
        Mode mode, String name,
        // Code statement
        Point offset, Point size,
        // Code statement
        Mirror mirror, Rotation rotation,
        // Code statement
        String metadata, float integrity,
        // Code statement
        long seed, byte flags
// Start of a method/block
) implements ClientPacket.Play {

    // Assigns a value
    public static final NetworkBuffer.Type<ClientUpdateStructureBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, ClientUpdateStructureBlockPacket::location,
            // Code statement
            NetworkBuffer.Enum(Action.class), ClientUpdateStructureBlockPacket::action,
            // Code statement
            NetworkBuffer.Enum(Mode.class), ClientUpdateStructureBlockPacket::mode,
            // Code statement
            STRING, ClientUpdateStructureBlockPacket::name,
            // Code statement
            VECTOR3B, ClientUpdateStructureBlockPacket::offset,
            // Code statement
            VECTOR3B, ClientUpdateStructureBlockPacket::size,
            // Code statement
            Enum(Mirror.class), ClientUpdateStructureBlockPacket::mirror,
            // Code statement
            VAR_INT.transform(ClientUpdateStructureBlockPacket::fromRestrictedRotation, ClientUpdateStructureBlockPacket::toRestrictedRotation), ClientUpdateStructureBlockPacket::rotation,
            // Code statement
            STRING, ClientUpdateStructureBlockPacket::metadata,
            // Code statement
            FLOAT, ClientUpdateStructureBlockPacket::integrity,
            // Code statement
            LONG, ClientUpdateStructureBlockPacket::seed,
            // Code statement
            BYTE, ClientUpdateStructureBlockPacket::flags,
            // Code statement
            ClientUpdateStructureBlockPacket::new
    // End of a block/expression
    );

    // Flag values
    // Assigns a value
    public static final byte IGNORE_ENTITIES = 0x1;
    // Assigns a value
    public static final byte SHOW_AIR = 0x2;
    /**
     * Requires the player to be in creative and have a permission level higher than 2.
     */
    // Assigns a value
    public static final byte SHOW_BOUNDING_BOX = 0x4;
    // Assigns a value
    public static final byte STRICT = 0x8;

    // Start of a method/block
    public ClientUpdateStructureBlockPacket {
        // Calls a method
        Check.argCondition(name.length() > Short.MAX_VALUE, "Name length cannot be greater than Short.MAX_VALUE");
        // Calls a method
        Check.argCondition(metadata.length() > 128, "Metadata length cannot be greater than 128");
    // End of a block/expression
    }

    /**
     * Update action, <code>UPDATE_DATA</code> indicates nothing special.
     */
    // Type declaration (class/interface/enum/record)
    public enum Action {
        // Code statement
        UPDATE_DATA, SAVE, LOAD, DETECT_SIZE
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Mode {
        // Code statement
        SAVE, LOAD, CORNER, DATA
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Mirror {
        // Code statement
        NONE, LEFT_RIGHT, FRONT_BACK
    // End of a block/expression
    }

    // Start of a method/block
    private static int toRestrictedRotation(Rotation rotation) {
        // Returns a value to the caller
        return switch (rotation) {
            // Multiple branching (switch/case)
            case NONE -> 0;
            // Multiple branching (switch/case)
            case CLOCKWISE -> 1;
            // Multiple branching (switch/case)
            case FLIPPED -> 2;
            // Multiple branching (switch/case)
            case COUNTER_CLOCKWISE -> 3;
            // Multiple branching (switch/case)
            default ->
                    // Throws an exception
                    throw new IllegalArgumentException("ClientUpdateStructurePacket#rotation must be a valid 90-degree rotation.");
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static Rotation fromRestrictedRotation(int rotation) {
        // Returns a value to the caller
        return switch (rotation) {
            // Multiple branching (switch/case)
            case 0 -> Rotation.NONE;
            // Multiple branching (switch/case)
            case 1 -> Rotation.CLOCKWISE;
            // Multiple branching (switch/case)
            case 2 -> Rotation.FLIPPED;
            // Multiple branching (switch/case)
            case 3 -> Rotation.COUNTER_CLOCKWISE;
            // Multiple branching (switch/case)
            default ->
                    // Throws an exception
                    throw new IllegalArgumentException("ClientUpdateStructurePacket#rotation must be a valid 90-degree rotation.");
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
