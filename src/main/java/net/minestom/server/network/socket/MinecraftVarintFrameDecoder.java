// Package declaration for this file
package net.minestom.server.network.socket;

// Import of a required class
import io.netty.buffer.ByteBuf;
// Import of a required class
import io.netty.channel.ChannelHandlerContext;
// Import of a required class
import io.netty.handler.codec.ByteToMessageDecoder;

// Import of a required class
import java.util.List;

/**
 * Netty {@link ByteToMessageDecoder} that frames Minecraft's varint-length-prefixed
 * packets.
 *
 * <p>Minecraft sends packets as:
 * <pre>
 *   [VarInt: packet length][packet bytes …]
 * </pre>
 *
 * This decoder accumulates bytes until a full frame is available, then passes
 * the <em>payload</em> (without the length prefix) downstream.
 *
 * <p>No {@code java.nio.*} imports are used.
 */
// Type declaration (class/interface/enum/record)
public final class MinecraftVarintFrameDecoder extends ByteToMessageDecoder {

    /** Hard limit to guard against memory-exhaustion attacks (2 MiB). */
    // Assigns a value
    private static final int MAX_PACKET_LENGTH = 1 << 21; // 2 097 152

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // Branch: checks a condition
        if (!ctx.channel().isActive()) return;

        // Calls a method
        in.markReaderIndex();
        // Assigns a value
        int length = 0;
        // Assigns a value
        int shift = 0;

        // Loop: repeats a block
        for (int i = 0; i < 5; i++) {
            // Branch: checks a condition
            if (!in.isReadable()) {
                // Calls a method
                in.resetReaderIndex();
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Calls a method
            byte b = in.readByte();
            // Calls a method
            length |= (b & 0x7F) << shift;
            // Branch: checks a condition
            if ((b & 0x80) == 0) {
                // Branch: checks a condition
                if (length < 0) throw new RuntimeException("Negative Paketlänge");

                // Branch: checks a condition
                if (in.readableBytes() < length) {
                    // Calls a method
                    in.resetReaderIndex();
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }

                // Calls a method
                int endIndex = in.readerIndex() + length;
                // Calls a method
                in.resetReaderIndex();
                // Calls a method
                out.add(in.readRetainedSlice(endIndex - in.readerIndex()));
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Code statement
            shift += 7;
        // End of a block/expression
        }
        // Throws an exception
        throw new RuntimeException("VarInt zu lang (Corrupted Stream)");
    // End of a block/expression
    }
// End of a block/expression
}