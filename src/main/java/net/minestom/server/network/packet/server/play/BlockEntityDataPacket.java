// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record BlockEntityDataPacket(
        // Code statement
        Point blockPosition,
        // Code statement
        BlockEntityType type,
        // Annotation for the following element
        @Nullable CompoundBinaryTag data
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<BlockEntityDataPacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, BlockEntityDataPacket value) {
            // Calls a method
            buffer.write(BLOCK_POSITION, value.blockPosition);
            // Calls a method
            buffer.write(BlockEntityType.NETWORK_TYPE, value.type);
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
        public BlockEntityDataPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new BlockEntityDataPacket(buffer.read(BLOCK_POSITION), buffer.read(BlockEntityType.NETWORK_TYPE), buffer.read(NBT_COMPOUND));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public BlockEntityDataPacket(Point blockPosition, int action, @Nullable CompoundBinaryTag data) {
        // Calls a method
        this(blockPosition, Objects.requireNonNull(BlockEntityType.fromId(action), "Unknown block entity type"), data);
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int action() {
        // Returns a value to the caller
        return type.id();
    // End of a block/expression
    }
// End of a block/expression
}
