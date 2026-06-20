// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record NbtQueryResponsePacket(int transactionId, CompoundBinaryTag data) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<NbtQueryResponsePacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, NbtQueryResponsePacket value) {
            // Calls a method
            buffer.write(VAR_INT, value.transactionId);
            // Branch: checks a condition
            if (value.data != null) {
                // Calls a method
                buffer.write(NBT_COMPOUND, value.data);
            // Alternative branch of the condition
            } else {
                // TAG_End
                // Calls a method
                buffer.write(BYTE, (byte) 0x00);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public NbtQueryResponsePacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new NbtQueryResponsePacket(buffer.read(VAR_INT), buffer.read(NBT_COMPOUND));
        // End of a block/expression
        }
    // End of a block/expression
    };
// End of a block/expression
}
