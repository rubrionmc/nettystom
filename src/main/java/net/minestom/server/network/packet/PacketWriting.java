// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.function.BiPredicate;

/**
 * Tools to write packets into a {@link NetworkBuffer} for network processing.
 * <p>
 * Fairly internal and performance sensitive.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class PacketWriting {
    // Code statement
    public static void writeFramedPacket(NetworkBuffer buffer,
                                         // Code statement
                                         ConnectionState state,
                                         // Code statement
                                         ClientPacket packet,
                                         // Start of a method/block
                                         int compressionThreshold) throws IndexOutOfBoundsException {
        // Calls a method
        writeFramedPacket(buffer, PacketVanilla.CLIENT_PACKET_PARSER, state, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    public static void writeFramedPacket(NetworkBuffer buffer,
                                         // Code statement
                                         ConnectionState state,
                                         // Code statement
                                         ServerPacket packet,
                                         // Start of a method/block
                                         int compressionThreshold) throws IndexOutOfBoundsException {
        // Calls a method
        writeFramedPacket(buffer, PacketVanilla.SERVER_PACKET_PARSER, state, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Code statement
                                             PacketParser<? super T> parser,
                                             // Code statement
                                             ConnectionState state,
                                             // Code statement
                                             T packet,
                                             // Start of a method/block
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Annotation for the following element
        @SuppressWarnings("unchecked") // We assume ConnectionState and PacketRegistry are in sync
        // Calls a method
        final PacketRegistry<? super T> registry = (PacketRegistry<? super T>) parser.stateRegistry(state);
        // Calls a method
        writeFramedPacket(buffer, registry, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Code statement
                                             PacketRegistry<? super T> registry,
                                             // Code statement
                                             T packet,
                                             // Start of a method/block
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Calls a method
        final PacketRegistry.PacketInfo<? super T> packetInfo = registry.packetInfo(packet);
        // Code statement
        writeFramedPacket(
                // Code statement
                buffer,
                // Code statement
                packetInfo, packet,
                // Code statement
                compressionThreshold
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Code statement
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Code statement
                                             PacketRegistry.PacketInfo<? super T> packetInfo,
                                             // Code statement
                                             T packet,
                                             // Start of a method/block
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Calls a method
        final int id = packetInfo.id();
        // Calls a method
        final NetworkBuffer.Type<? super T> serializer = packetInfo.serializer();
        // Code statement
        writeFramedPacket(
                // Code statement
                buffer, serializer,
                // Code statement
                id, packet,
                // Code statement
                compressionThreshold
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Code statement
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Code statement
                                             NetworkBuffer.Type<? super T> type,
                                             // Code statement
                                             int id, T packet,
                                             // Start of a method/block
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Branch: checks a condition
        if (compressionThreshold <= 0) writeUncompressedFormat(buffer, type, id, packet);
        // Alternative branch of the condition
        else writeCompressedFormat(buffer, type, id, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    private static <T> void writeUncompressedFormat(NetworkBuffer buffer,
                                                    // Code statement
                                                    NetworkBuffer.Type<? super T> type,
                                                    // Start of a method/block
                                                    int id, T packet) throws IndexOutOfBoundsException {
        // Uncompressed format https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Without_compression
        // Calls a method
        final long lengthIndex = buffer.advanceWrite(3);
        // Calls a method
        buffer.write(NetworkBuffer.VAR_INT, id);
        // Calls a method
        buffer.write(type, packet);
        // Calls a method
        final long finalSize = buffer.writeIndex() - (lengthIndex + 3);
        // Calls a method
        buffer.writeAt(lengthIndex, NetworkBuffer.VAR_INT_3, (int) finalSize);
    // End of a block/expression
    }

    // Code statement
    private static <T> void writeCompressedFormat(NetworkBuffer buffer,
                                                  // Code statement
                                                  NetworkBuffer.Type<? super T> type,
                                                  // Code statement
                                                  int id, T packet,
                                                  // Start of a method/block
                                                  int compressionThreshold) throws IndexOutOfBoundsException {
        // Compressed format https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#With_compression
        // Calls a method
        final long compressedIndex = buffer.advanceWrite(3);
        // Calls a method
        final long uncompressedIndex = buffer.advanceWrite(3);
        // Calls a method
        final long contentStart = buffer.writeIndex();
        // Calls a method
        buffer.write(NetworkBuffer.VAR_INT, id);
        // Calls a method
        buffer.write(type, packet);
        // Calls a method
        final long packetSize = buffer.writeIndex() - contentStart;
        // Assigns a value
        final boolean compressed = packetSize >= compressionThreshold;
        // Branch: checks a condition
        if (compressed) {
            // Write the compressed content into the pooled buffer
            // and compress it into the current buffer
            // Calls a method
            NetworkBuffer input = PacketVanilla.PACKET_POOL.get();
            // Exception handling
            try {
                // Branch: checks a condition
                if (input.capacity() < packetSize) input.resize(packetSize);
                // Calls a method
                NetworkBuffer.copy(buffer, contentStart, input, 0, packetSize);
                // Calls a method
                buffer.writeIndex(contentStart);
                // Calls a method
                input.compress(0, packetSize, buffer);
            // Start of a method/block
            } catch (IOException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // Start of a method/block
            } finally {
                // Calls a method
                PacketVanilla.PACKET_POOL.add(input);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Packet header (Packet + Data Length)
        // Calls a method
        buffer.writeAt(compressedIndex, NetworkBuffer.VAR_INT_3, (int) (buffer.writeIndex() - uncompressedIndex));
        // Calls a method
        buffer.writeAt(uncompressedIndex, NetworkBuffer.VAR_INT_3, compressed ? (int) packetSize : 0);
    // End of a block/expression
    }

    // Code statement
    public static NetworkBuffer allocateTrimmedPacket(ConnectionState state,
                                                      // Code statement
                                                      ClientPacket packet,
                                                      // Start of a method/block
                                                      int compressionThreshold) {
        // Returns a value to the caller
        return allocateTrimmedPacket(PacketVanilla.CLIENT_PACKET_PARSER, state, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    public static NetworkBuffer allocateTrimmedPacket(ConnectionState state,
                                                      // Code statement
                                                      ServerPacket packet,
                                                      // Start of a method/block
                                                      int compressionThreshold) {
        // Returns a value to the caller
        return allocateTrimmedPacket(PacketVanilla.SERVER_PACKET_PARSER, state, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    public static <T> NetworkBuffer allocateTrimmedPacket(
            // Code statement
            PacketParser<T> parser,
            // Code statement
            ConnectionState state,
            // Code statement
            T packet,
            // Start of a method/block
            int compressionThreshold) {
        // Calls a method
        NetworkBuffer buffer = PacketVanilla.PACKET_POOL.get();
        // Exception handling
        try {
            // Returns a value to the caller
            return allocateTrimmedPacket(buffer, parser, state, packet, compressionThreshold);
        // Start of a method/block
        } finally {
            // Calls a method
            PacketVanilla.PACKET_POOL.add(buffer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    public static <T> NetworkBuffer allocateTrimmedPacket(
            // Code statement
            NetworkBuffer tmpBuffer,
            // Code statement
            PacketParser<? super T> parser,
            // Code statement
            ConnectionState state,
            // Code statement
            T packet,
            // Start of a method/block
            int compressionThreshold) {
        // Annotation for the following element
        @SuppressWarnings("unchecked") // We assume ConnectionState and PacketRegistry are in sync
        // Calls a method
        final PacketRegistry<? super T> registry = (PacketRegistry<? super T>) parser.stateRegistry(state);
        // Returns a value to the caller
        return allocateTrimmedPacket(tmpBuffer, registry, packet, compressionThreshold);
    // End of a block/expression
    }

    // Code statement
    public static <T> NetworkBuffer allocateTrimmedPacket(
            // Code statement
            NetworkBuffer tmpBuffer,
            // Code statement
            PacketRegistry<? super T> registry,
            // Code statement
            T packet,
            // Start of a method/block
            int compressionThreshold) {
        // Calls a method
        final PacketRegistry.PacketInfo<? super T> packetInfo = registry.packetInfo(packet);
        // Calls a method
        final int id = packetInfo.id();
        // Calls a method
        final NetworkBuffer.Type<? super T> serializer = packetInfo.serializer();
        // Exception handling
        try {
            // Calls a method
            writeFramedPacket(tmpBuffer, serializer, id, packet, compressionThreshold);
            // Returns a value to the caller
            return tmpBuffer.copy(0, tmpBuffer.writeIndex());
        // Start of a method/block
        } catch (IndexOutOfBoundsException e) {
            // Calls a method
            final long sizeOf = serializer.sizeOf(packet, tmpBuffer.registries());
            // Branch: checks a condition
            if (sizeOf > ServerFlag.MAX_PACKET_SIZE) {
                // Throws an exception
                throw new IllegalStateException("Packet too large: " + sizeOf);
            // End of a block/expression
            }
            // Add 15 bytes to account for the 3 potential varints in the packet header
            // Packet Length - Data Length - Packet ID
            // Calls a method
            tmpBuffer.resize(sizeOf + 15);
            // Calls a method
            tmpBuffer.writeIndex(0);
            // Calls a method
            writeFramedPacket(tmpBuffer, serializer, id, packet, compressionThreshold);
            // Returns a value to the caller
            return tmpBuffer.copy(0, tmpBuffer.writeIndex());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    public static <T> void writeQueue(NetworkBuffer buffer, MessagePassingQueue<T> queue, int minWrite,
                                      // Start of a method/block
                                      BiPredicate<NetworkBuffer, T> writer) {
        // The goal of this method is to write at the very least `minWrite` packets if the queue permits it.
        // The buffer is resized if it cannot hold this minimum.
        // Calls a method
        final int size = queue.size();
        // Calls a method
        minWrite = Math.min(minWrite, size);
        // Code statement
        T packet;
        // Assigns a value
        int written = 0;
        // Loop: repeats a block
        while ((packet = queue.peek()) != null) {
            // Calls a method
            final long index = buffer.writeIndex();
            // Code statement
            boolean success;
            // Exception handling
            try {
                // Calls a method
                success = writer.test(buffer, packet);
            // Start of a method/block
            } catch (IndexOutOfBoundsException e) {
                // Assigns a value
                success = false;
            // End of a block/expression
            }
            // Calls a method
            assert !success || buffer.writeIndex() > 0;
            // Poll the packet only if fully written
            // Branch: checks a condition
            if (success) {
                // Packet fully written
                // Calls a method
                queue.poll();
                // Code statement
                written++;
            // Alternative branch of the condition
            } else {
                // Calls a method
                buffer.writeIndex(index);
                // Branch: checks a condition
                if (written < minWrite) {
                    // Try again with a bigger buffer
                    // Calls a method
                    final long newSize = Math.min(buffer.capacity() * 2, ServerFlag.MAX_PACKET_SIZE);
                    // Branch: checks a condition
                    if (newSize == buffer.capacity()) break; // We reached the maximum size
                    // Calls a method
                    buffer.resize(newSize);
                // Alternative branch of the condition
                } else {
                    // At least one packet has been written
                    // Not worth resizing to fit more, we'll try again next flush
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
