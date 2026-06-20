// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record FacePlayerPacket(FacePosition facePosition,
                               // Code statement
                               Point target, int entityId,
                               // Start of a method/block
                               FacePosition entityFacePosition) implements ServerPacket.Play {

    // Assigns a value
    public static final NetworkBuffer.Type<FacePlayerPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, FacePlayerPacket value) {
            // Calls a method
            buffer.write(Enum(FacePosition.class), value.facePosition);
            // Calls a method
            buffer.write(VECTOR3D, value.target);
            // Assigns a value
            final boolean isEntity = value.entityId > 0;
            // Calls a method
            buffer.write(BOOLEAN, isEntity);
            // Branch: checks a condition
            if (isEntity) {
                // Calls a method
                buffer.write(VAR_INT, value.entityId);
                // Calls a method
                buffer.write(Enum(FacePosition.class), value.entityFacePosition);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public FacePlayerPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new FacePlayerPacket(buffer.read(Enum(FacePosition.class)),
                    // Code statement
                    buffer.read(VECTOR3D), buffer.read(BOOLEAN) ? buffer.read(VAR_INT) : 0,
                    // Calls a method
                    buffer.readableBytes() > 0 ? buffer.read(Enum(FacePosition.class)) : null);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Type declaration (class/interface/enum/record)
    public enum FacePosition {
        // Code statement
        FEET, EYES
    // End of a block/expression
    }
// End of a block/expression
}
