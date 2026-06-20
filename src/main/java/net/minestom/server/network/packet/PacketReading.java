// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.zip.DataFormatException;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

/**
 * Tools to read packets from a {@link NetworkBuffer} for network processing.
 * <p>
 * Fairly internal and performance sensitive.
 */
// Annotation pour l'élément suivant
@SuppressWarnings("ALL")
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class PacketReading {
    // Appelle une méthode
    private final static Logger LOGGER = LoggerFactory.getLogger(PacketReading.class);

    // Affecte une valeur
    private static final int MAX_VAR_INT_SIZE = 5;
    // Appelle une méthode
    private static final Result.Empty EMPTY_CLIENT_PACKET = new Result.Empty<>();

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Result<T> {

        /**
         * At least one packet was read.
         * The buffer may still contain half-read packets and should therefore be compacted for next read.
         */
        // Déclaration de type (classe/interface/enum/record)
        record Success<T>(List<ParsedPacket<T>> packets) implements Result<T> {
            // Début d'une méthode/d'un bloc
            public Success {
                // Embranchement : vérifie une condition
                if (packets.isEmpty()) {
                    // Lève une exception
                    throw new IllegalArgumentException("Empty packets");
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                packets = List.copyOf(packets);
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public Success(ParsedPacket<T> packet) {
                // Appelle une méthode
                this(List.of(packet));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        /**
         * Represents no packet to read. Can generally be ignored.
         * <p>
         * Happens when a packet length or payload couldn't be read, but the buffer has enough capacity.
         */
        // Déclaration de type (classe/interface/enum/record)
        record Empty<T>() implements Result<T> {
        // Fin d'un bloc/d'une expression
        }

        /**
         * Represents a failure to read a packet due to insufficient buffer capacity.
         * <p>
         * Buffer should be expanded to at least {@code requiredCapacity} bytes.
         * <p>
         * If the buffer does not allow to read the packet length, max var-int length is returned.
         */
        // Déclaration de type (classe/interface/enum/record)
        record Failure<T>(long requiredCapacity) implements Result<T> {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ParsedPacket<T>(ConnectionState nextState, T packet) {
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static Result<ClientPacket> readClients(
            // Instruction de code
            NetworkBuffer buffer,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            boolean compressed
    // Début d'une méthode/d'un bloc
    ) throws DataFormatException {
        // Renvoie une valeur à l'appelant
        return readPackets(buffer, PacketVanilla.CLIENT_PACKET_PARSER, state, PacketVanilla::nextClientState, compressed);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static Result<ServerPacket> readServers(
            // Instruction de code
            NetworkBuffer buffer,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            boolean compressed
    // Début d'une méthode/d'un bloc
    ) throws DataFormatException {
        // Renvoie une valeur à l'appelant
        return readPackets(buffer, PacketVanilla.SERVER_PACKET_PARSER, state, PacketVanilla::nextServerState, compressed);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> Result<T> readPackets(
            // Instruction de code
            NetworkBuffer buffer,
            // Instruction de code
            PacketParser<T> parser,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            BiFunction<T, ConnectionState, ConnectionState> stateUpdater,
            // Instruction de code
            boolean compressed
    // Début d'une méthode/d'un bloc
    ) throws DataFormatException {
        // Appelle une méthode
        List<ParsedPacket<T>> packets = new ArrayList<>();
        // Instruction de code
        readLoop:
        // Boucle : répète un bloc
        while (buffer.readableBytes() > 0) {
            // Appelle une méthode
            final Result<T> result = readPacket(buffer, parser, state, stateUpdater, compressed);
            // Embranchement : vérifie une condition
            if (buffer.readableBytes() == 0 && packets.isEmpty()) return result;
            // Embranchement multiple (switch/case)
            switch (result) {
                // Embranchement multiple (switch/case)
                case Result.Success<T> success -> {
                    // Appelle une méthode
                    assert success.packets().size() == 1;
                    // Appelle une méthode
                    final ParsedPacket<T> parsedPacket = success.packets().getFirst();
                    // Appelle une méthode
                    packets.add(parsedPacket);
                    // Appelle une méthode
                    state = parsedPacket.nextState();
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case Result.Empty<T> ignored -> {
                    // Interrompt la boucle/le bloc
                    break readLoop;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case Result.Failure<T> failure -> {
                    // Renvoie une valeur à l'appelant
                    return packets.isEmpty() ? failure : new Result.Success<>(packets);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return !packets.isEmpty() ? new Result.Success<>(packets) : EMPTY_CLIENT_PACKET;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static Result<ClientPacket> readClient(
            // Instruction de code
            NetworkBuffer buffer,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            boolean compressed
    // Début d'une méthode/d'un bloc
    ) throws DataFormatException {
        // Renvoie une valeur à l'appelant
        return readPacket(buffer, PacketVanilla.CLIENT_PACKET_PARSER, state, PacketVanilla::nextClientState, compressed);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static Result<ServerPacket> readServer(
            // Instruction de code
            NetworkBuffer buffer,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            boolean compressed
    // Début d'une méthode/d'un bloc
    ) throws DataFormatException {
        // Renvoie une valeur à l'appelant
        return readPacket(buffer, PacketVanilla.SERVER_PACKET_PARSER, state, PacketVanilla::nextServerState, compressed);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> Result<T> readPacket(
            // Instruction de code
            NetworkBuffer buffer,
            // Instruction de code
            PacketParser<T> parser,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            BiFunction<T, ConnectionState, ConnectionState> stateUpdater,
            // Instruction de code
            boolean compressed
    // Début d'une méthode/d'un bloc
    ) throws DataFormatException {
        // Appelle une méthode
        final long beginMark = buffer.readIndex();
        // READ PACKET LENGTH
        // Instruction de code
        final int packetLength;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            packetLength = buffer.read(VAR_INT);
        // Début d'une méthode/d'un bloc
        } catch (IndexOutOfBoundsException e) {
            // Couldn't read a single var-int
            // Renvoie une valeur à l'appelant
            return new Result.Failure<>(MAX_VAR_INT_SIZE);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final long readerStart = buffer.readIndex();
        // Embranchement : vérifie une condition
        if (readerStart > buffer.writeIndex()) {
            // Can't read the packet length, buffer has enough capacity
            // Appelle une méthode
            buffer.readIndex(beginMark);
            // Renvoie une valeur à l'appelant
            return EMPTY_CLIENT_PACKET;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final int maxPacketSize = maxPacketSize(state);
        // Embranchement : vérifie une condition
        if (packetLength < 0) throw new DataFormatException("Packet length negative: " + packetLength);
        // Embranchement : vérifie une condition
        if (packetLength > maxPacketSize) throw new DataFormatException("Packet too large: " + packetLength);
        // READ PAYLOAD https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Packet_format
        // Embranchement : vérifie une condition
        if (buffer.readableBytes() < packetLength) {
            // Can't read the full packet
            // Appelle une méthode
            buffer.readIndex(beginMark);
            // Affecte une valeur
            final long packetLengthVarIntSize = readerStart - beginMark;
            // Affecte une valeur
            final long requiredCapacity = packetLengthVarIntSize + packetLength;
            // Must return a failure if the buffer is too small
            // Otherwise do nothing, and hope to read the packet remains next time
            // Embranchement : vérifie une condition
            if (requiredCapacity > buffer.capacity()) return new Result.Failure<>(requiredCapacity);
            // Branche alternative de la condition
            else return EMPTY_CLIENT_PACKET;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final long readerEnd = readerStart + packetLength;
        // Appelle une méthode
        final long writerEnd = buffer.writeIndex();
        // Appelle une méthode
        buffer.writeIndex(readerEnd);
        // Appelle une méthode
        final PacketRegistry<? extends T> registry = parser.stateRegistry(state);
        // Appelle une méthode
        final T packet = readFramedPacket(buffer, registry, compressed, maxPacketSize);
        // Appelle une méthode
        final ConnectionState nextState = stateUpdater.apply(packet, state);
        // Appelle une méthode
        buffer.index(readerEnd, writerEnd);
        // Renvoie une valeur à l'appelant
        return new Result.Success<>(new ParsedPacket<>(nextState, packet));
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static <T> T readFramedPacket(NetworkBuffer buffer,
                                          // Instruction de code
                                          PacketRegistry<T> registry,
                                          // Instruction de code
                                          boolean compressed,
                                          // Début d'une méthode/d'un bloc
                                          int maxPacketSize) throws DataFormatException {
        // Embranchement : vérifie une condition
        if (!compressed) {
            // No compression format
            // Renvoie une valeur à l'appelant
            return readPayload(buffer, registry);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final int dataLength = buffer.read(VAR_INT);
        // Embranchement : vérifie une condition
        if (dataLength == 0) {
            // Uncompressed packet
            // Renvoie une valeur à l'appelant
            return readPayload(buffer, registry);
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (dataLength < 0 || dataLength > maxPacketSize) {
            // Lève une exception
            throw new DataFormatException("Invalid decompressed length: " + dataLength);
        // Fin d'un bloc/d'une expression
        }

        // Decompress the packet into the pooled buffer and read the uncompressed packet from it
        // Appelle une méthode
        NetworkBuffer decompressed = PacketVanilla.PACKET_POOL.get();
        // Gestion des exceptions
        try {
            // Embranchement : vérifie une condition
            if (decompressed.capacity() < dataLength) decompressed.resize(dataLength);
            // Appelle une méthode
            decompressed.registries(buffer.registries());
            // Appelle une méthode
            final long written = buffer.decompress(buffer.readIndex(), buffer.readableBytes(), decompressed);
            // Embranchement : vérifie une condition
            if (written != dataLength) {
                // Lève une exception
                throw new DataFormatException("Decompressed length mismatch: expected " + dataLength + ", got " + written);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return readPayload(decompressed, registry);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            PacketVanilla.PACKET_POOL.add(decompressed);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> T readPayload(NetworkBuffer buffer, PacketRegistry<T> registry) {
        // Appelle une méthode
        final int packetId = buffer.read(VAR_INT);
        // Appelle une méthode
        final PacketRegistry.PacketInfo<T> packetInfo = registry.packetInfo(packetId);
        // Appelle une méthode
        final NetworkBuffer.Type<T> serializer = packetInfo.serializer();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final T packet = serializer.read(buffer);
            // Embranchement : vérifie une condition
            if (buffer.readableBytes() != 0) {
                // Instruction de code
                LOGGER.warn("WARNING: Packet ({}) 0x{} not fully read ({})",
                        // Appelle une méthode
                        packetInfo.packetClass().getSimpleName(), Integer.toHexString(packetId), buffer);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return packet;
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Lève une exception
            throw new RuntimeException("failed to read packet " + packetInfo.packetClass(), e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int maxPacketSize(ConnectionState state) {
        // Renvoie une valeur à l'appelant
        return switch (state) {
            // Embranchement multiple (switch/case)
            case HANDSHAKE, LOGIN -> ServerFlag.MAX_PACKET_SIZE_PRE_AUTH;
            // Embranchement multiple (switch/case)
            default -> ServerFlag.MAX_PACKET_SIZE;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
