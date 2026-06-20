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
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.zip.DataFormatException;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

/**
 * Tools to read packets from a {@link NetworkBuffer} for network processing.
 * <p>
 * Fairly internal and performance sensitive.
 */
// Annotation for the following element
@SuppressWarnings("ALL")
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class PacketReading {
    // Calls a method
    private final static Logger LOGGER = LoggerFactory.getLogger(PacketReading.class);

    // Assigns a value
    private static final int MAX_VAR_INT_SIZE = 5;
    // Calls a method
    private static final Result.Empty EMPTY_CLIENT_PACKET = new Result.Empty<>();

    // Type declaration (class/interface/enum/record)
    public sealed interface Result<T> {

        /**
         * At least one packet was read.
         * The buffer may still contain half-read packets and should therefore be compacted for next read.
         */
        // Type declaration (class/interface/enum/record)
        record Success<T>(List<ParsedPacket<T>> packets) implements Result<T> {
            // Start of a method/block
            public Success {
                // Branch: checks a condition
                if (packets.isEmpty()) {
                    // Throws an exception
                    throw new IllegalArgumentException("Empty packets");
                // End of a block/expression
                }
                // Calls a method
                packets = List.copyOf(packets);
            // End of a block/expression
            }

            // Start of a method/block
            public Success(ParsedPacket<T> packet) {
                // Calls a method
                this(List.of(packet));
            // End of a block/expression
            }
        // End of a block/expression
        }

        /**
         * Represents no packet to read. Can generally be ignored.
         * <p>
         * Happens when a packet length or payload couldn't be read, but the buffer has enough capacity.
         */
        // Type declaration (class/interface/enum/record)
        record Empty<T>() implements Result<T> {
        // End of a block/expression
        }

        /**
         * Represents a failure to read a packet due to insufficient buffer capacity.
         * <p>
         * Buffer should be expanded to at least {@code requiredCapacity} bytes.
         * <p>
         * If the buffer does not allow to read the packet length, max var-int length is returned.
         */
        // Type declaration (class/interface/enum/record)
        record Failure<T>(long requiredCapacity) implements Result<T> {
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record ParsedPacket<T>(ConnectionState nextState, T packet) {
    // End of a block/expression
    }

    // Code statement
    public static Result<ClientPacket> readClients(
            // Code statement
            NetworkBuffer buffer,
            // Code statement
            ConnectionState state,
            // Code statement
            boolean compressed
    // Start of a method/block
    ) throws DataFormatException {
        // Returns a value to the caller
        return readPackets(buffer, PacketVanilla.CLIENT_PACKET_PARSER, state, PacketVanilla::nextClientState, compressed);
    // End of a block/expression
    }

    // Code statement
    public static Result<ServerPacket> readServers(
            // Code statement
            NetworkBuffer buffer,
            // Code statement
            ConnectionState state,
            // Code statement
            boolean compressed
    // Start of a method/block
    ) throws DataFormatException {
        // Returns a value to the caller
        return readPackets(buffer, PacketVanilla.SERVER_PACKET_PARSER, state, PacketVanilla::nextServerState, compressed);
    // End of a block/expression
    }

    // Code statement
    public static <T> Result<T> readPackets(
            // Code statement
            NetworkBuffer buffer,
            // Code statement
            PacketParser<T> parser,
            // Code statement
            ConnectionState state,
            // Code statement
            BiFunction<T, ConnectionState, ConnectionState> stateUpdater,
            // Code statement
            boolean compressed
    // Start of a method/block
    ) throws DataFormatException {
        // Calls a method
        List<ParsedPacket<T>> packets = new ArrayList<>();
        // Code statement
        readLoop:
        // Loop: repeats a block
        while (buffer.readableBytes() > 0) {
            // Calls a method
            final Result<T> result = readPacket(buffer, parser, state, stateUpdater, compressed);
            // Branch: checks a condition
            if (buffer.readableBytes() == 0 && packets.isEmpty()) return result;
            // Multiple branching (switch/case)
            switch (result) {
                // Multiple branching (switch/case)
                case Result.Success<T> success -> {
                    // Calls a method
                    assert success.packets().size() == 1;
                    // Calls a method
                    final ParsedPacket<T> parsedPacket = success.packets().getFirst();
                    // Calls a method
                    packets.add(parsedPacket);
                    // Calls a method
                    state = parsedPacket.nextState();
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case Result.Empty<T> ignored -> {
                    // Breaks out of the loop/block
                    break readLoop;
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case Result.Failure<T> failure -> {
                    // Returns a value to the caller
                    return packets.isEmpty() ? failure : new Result.Success<>(packets);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return !packets.isEmpty() ? new Result.Success<>(packets) : EMPTY_CLIENT_PACKET;
    // End of a block/expression
    }

    // Code statement
    public static Result<ClientPacket> readClient(
            // Code statement
            NetworkBuffer buffer,
            // Code statement
            ConnectionState state,
            // Code statement
            boolean compressed
    // Start of a method/block
    ) throws DataFormatException {
        // Returns a value to the caller
        return readPacket(buffer, PacketVanilla.CLIENT_PACKET_PARSER, state, PacketVanilla::nextClientState, compressed);
    // End of a block/expression
    }

    // Code statement
    public static Result<ServerPacket> readServer(
            // Code statement
            NetworkBuffer buffer,
            // Code statement
            ConnectionState state,
            // Code statement
            boolean compressed
    // Start of a method/block
    ) throws DataFormatException {
        // Returns a value to the caller
        return readPacket(buffer, PacketVanilla.SERVER_PACKET_PARSER, state, PacketVanilla::nextServerState, compressed);
    // End of a block/expression
    }

    // Code statement
    public static <T> Result<T> readPacket(
            // Code statement
            NetworkBuffer buffer,
            // Code statement
            PacketParser<T> parser,
            // Code statement
            ConnectionState state,
            // Code statement
            BiFunction<T, ConnectionState, ConnectionState> stateUpdater,
            // Code statement
            boolean compressed
    // Start of a method/block
    ) throws DataFormatException {
        // Calls a method
        final long beginMark = buffer.readIndex();
        // READ PACKET LENGTH
        // Code statement
        final int packetLength;
        // Exception handling
        try {
            // Calls a method
            packetLength = buffer.read(VAR_INT);
        // Start of a method/block
        } catch (IndexOutOfBoundsException e) {
            // Couldn't read a single var-int
            // Returns a value to the caller
            return new Result.Failure<>(MAX_VAR_INT_SIZE);
        // End of a block/expression
        }
        // Calls a method
        final long readerStart = buffer.readIndex();
        // Branch: checks a condition
        if (readerStart > buffer.writeIndex()) {
            // Can't read the packet length, buffer has enough capacity
            // Calls a method
            buffer.readIndex(beginMark);
            // Returns a value to the caller
            return EMPTY_CLIENT_PACKET;
        // End of a block/expression
        }
        // Calls a method
        final int maxPacketSize = maxPacketSize(state);
        // Branch: checks a condition
        if (packetLength < 0) throw new DataFormatException("Packet length negative: " + packetLength);
        // Branch: checks a condition
        if (packetLength > maxPacketSize) throw new DataFormatException("Packet too large: " + packetLength);
        // READ PAYLOAD https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Packet_format
        // Branch: checks a condition
        if (buffer.readableBytes() < packetLength) {
            // Can't read the full packet
            // Calls a method
            buffer.readIndex(beginMark);
            // Assigns a value
            final long packetLengthVarIntSize = readerStart - beginMark;
            // Assigns a value
            final long requiredCapacity = packetLengthVarIntSize + packetLength;
            // Must return a failure if the buffer is too small
            // Otherwise do nothing, and hope to read the packet remains next time
            // Branch: checks a condition
            if (requiredCapacity > buffer.capacity()) return new Result.Failure<>(requiredCapacity);
            // Alternative branch of the condition
            else return EMPTY_CLIENT_PACKET;
        // End of a block/expression
        }
        // Assigns a value
        final long readerEnd = readerStart + packetLength;
        // Calls a method
        final long writerEnd = buffer.writeIndex();
        // Calls a method
        buffer.writeIndex(readerEnd);
        // Calls a method
        final PacketRegistry<? extends T> registry = parser.stateRegistry(state);
        // Calls a method
        final T packet = readFramedPacket(buffer, registry, compressed, maxPacketSize);
        // Calls a method
        final ConnectionState nextState = stateUpdater.apply(packet, state);
        // Calls a method
        buffer.index(readerEnd, writerEnd);
        // Returns a value to the caller
        return new Result.Success<>(new ParsedPacket<>(nextState, packet));
    // End of a block/expression
    }

    // Code statement
    private static <T> T readFramedPacket(NetworkBuffer buffer,
                                          // Code statement
                                          PacketRegistry<T> registry,
                                          // Code statement
                                          boolean compressed,
                                          // Start of a method/block
                                          int maxPacketSize) throws DataFormatException {
        // Branch: checks a condition
        if (!compressed) {
            // No compression format
            // Returns a value to the caller
            return readPayload(buffer, registry);
        // End of a block/expression
        }

        // Calls a method
        final int dataLength = buffer.read(VAR_INT);
        // Branch: checks a condition
        if (dataLength == 0) {
            // Uncompressed packet
            // Returns a value to the caller
            return readPayload(buffer, registry);
        // End of a block/expression
        }
        // Branch: checks a condition
        if (dataLength < 0 || dataLength > maxPacketSize) {
            // Throws an exception
            throw new DataFormatException("Invalid decompressed length: " + dataLength);
        // End of a block/expression
        }

        // Decompress the packet into the pooled buffer and read the uncompressed packet from it
        // Calls a method
        NetworkBuffer decompressed = PacketVanilla.PACKET_POOL.get();
        // Exception handling
        try {
            // Branch: checks a condition
            if (decompressed.capacity() < dataLength) decompressed.resize(dataLength);
            // Calls a method
            decompressed.registries(buffer.registries());
            // Calls a method
            final long written = buffer.decompress(buffer.readIndex(), buffer.readableBytes(), decompressed);
            // Branch: checks a condition
            if (written != dataLength) {
                // Throws an exception
                throw new DataFormatException("Decompressed length mismatch: expected " + dataLength + ", got " + written);
            // End of a block/expression
            }
            // Returns a value to the caller
            return readPayload(decompressed, registry);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // Start of a method/block
        } finally {
            // Calls a method
            PacketVanilla.PACKET_POOL.add(decompressed);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> T readPayload(NetworkBuffer buffer, PacketRegistry<T> registry) {
        // Calls a method
        final int packetId = buffer.read(VAR_INT);
        // Calls a method
        final PacketRegistry.PacketInfo<T> packetInfo = registry.packetInfo(packetId);
        // Calls a method
        final NetworkBuffer.Type<T> serializer = packetInfo.serializer();
        // Exception handling
        try {
            // Calls a method
            final T packet = serializer.read(buffer);
            // Branch: checks a condition
            if (buffer.readableBytes() != 0) {
                // Code statement
                LOGGER.warn("WARNING: Packet ({}) 0x{} not fully read ({})",
                        // Calls a method
                        packetInfo.packetClass().getSimpleName(), Integer.toHexString(packetId), buffer);
            // End of a block/expression
            }
            // Returns a value to the caller
            return packet;
        // Start of a method/block
        } catch (Exception e) {
            // Throws an exception
            throw new RuntimeException("failed to read packet " + packetInfo.packetClass(), e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static int maxPacketSize(ConnectionState state) {
        // Returns a value to the caller
        return switch (state) {
            // Multiple branching (switch/case)
            case HANDSHAKE, LOGIN -> ServerFlag.MAX_PACKET_SIZE_PRE_AUTH;
            // Multiple branching (switch/case)
            default -> ServerFlag.MAX_PACKET_SIZE;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
