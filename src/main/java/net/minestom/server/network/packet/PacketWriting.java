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
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.function.BiPredicate;

/**
 * Tools to write packets into a {@link NetworkBuffer} for network processing.
 * <p>
 * Fairly internal and performance sensitive.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class PacketWriting {
    // Instruction de code
    public static void writeFramedPacket(NetworkBuffer buffer,
                                         // Instruction de code
                                         ConnectionState state,
                                         // Instruction de code
                                         ClientPacket packet,
                                         // Début d'une méthode/d'un bloc
                                         int compressionThreshold) throws IndexOutOfBoundsException {
        // Appelle une méthode
        writeFramedPacket(buffer, PacketVanilla.CLIENT_PACKET_PARSER, state, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static void writeFramedPacket(NetworkBuffer buffer,
                                         // Instruction de code
                                         ConnectionState state,
                                         // Instruction de code
                                         ServerPacket packet,
                                         // Début d'une méthode/d'un bloc
                                         int compressionThreshold) throws IndexOutOfBoundsException {
        // Appelle une méthode
        writeFramedPacket(buffer, PacketVanilla.SERVER_PACKET_PARSER, state, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Instruction de code
                                             PacketParser<? super T> parser,
                                             // Instruction de code
                                             ConnectionState state,
                                             // Instruction de code
                                             T packet,
                                             // Début d'une méthode/d'un bloc
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked") // We assume ConnectionState and PacketRegistry are in sync
        // Appelle une méthode
        final PacketRegistry<? super T> registry = (PacketRegistry<? super T>) parser.stateRegistry(state);
        // Appelle une méthode
        writeFramedPacket(buffer, registry, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Instruction de code
                                             PacketRegistry<? super T> registry,
                                             // Instruction de code
                                             T packet,
                                             // Début d'une méthode/d'un bloc
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Appelle une méthode
        final PacketRegistry.PacketInfo<? super T> packetInfo = registry.packetInfo(packet);
        // Instruction de code
        writeFramedPacket(
                // Instruction de code
                buffer,
                // Instruction de code
                packetInfo, packet,
                // Instruction de code
                compressionThreshold
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Instruction de code
                                             PacketRegistry.PacketInfo<? super T> packetInfo,
                                             // Instruction de code
                                             T packet,
                                             // Début d'une méthode/d'un bloc
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Appelle une méthode
        final int id = packetInfo.id();
        // Appelle une méthode
        final NetworkBuffer.Type<? super T> serializer = packetInfo.serializer();
        // Instruction de code
        writeFramedPacket(
                // Instruction de code
                buffer, serializer,
                // Instruction de code
                id, packet,
                // Instruction de code
                compressionThreshold
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> void writeFramedPacket(NetworkBuffer buffer,
                                             // Instruction de code
                                             NetworkBuffer.Type<? super T> type,
                                             // Instruction de code
                                             int id, T packet,
                                             // Début d'une méthode/d'un bloc
                                             int compressionThreshold) throws IndexOutOfBoundsException {
        // Embranchement : vérifie une condition
        if (compressionThreshold <= 0) writeUncompressedFormat(buffer, type, id, packet);
        // Branche alternative de la condition
        else writeCompressedFormat(buffer, type, id, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static <T> void writeUncompressedFormat(NetworkBuffer buffer,
                                                    // Instruction de code
                                                    NetworkBuffer.Type<? super T> type,
                                                    // Début d'une méthode/d'un bloc
                                                    int id, T packet) throws IndexOutOfBoundsException {
        // Uncompressed format https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Without_compression
        // Appelle une méthode
        final long lengthIndex = buffer.advanceWrite(3);
        // Appelle une méthode
        buffer.write(NetworkBuffer.VAR_INT, id);
        // Appelle une méthode
        buffer.write(type, packet);
        // Appelle une méthode
        final long finalSize = buffer.writeIndex() - (lengthIndex + 3);
        // Appelle une méthode
        buffer.writeAt(lengthIndex, NetworkBuffer.VAR_INT_3, (int) finalSize);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static <T> void writeCompressedFormat(NetworkBuffer buffer,
                                                  // Instruction de code
                                                  NetworkBuffer.Type<? super T> type,
                                                  // Instruction de code
                                                  int id, T packet,
                                                  // Début d'une méthode/d'un bloc
                                                  int compressionThreshold) throws IndexOutOfBoundsException {
        // Compressed format https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#With_compression
        // Appelle une méthode
        final long compressedIndex = buffer.advanceWrite(3);
        // Appelle une méthode
        final long uncompressedIndex = buffer.advanceWrite(3);
        // Appelle une méthode
        final long contentStart = buffer.writeIndex();
        // Appelle une méthode
        buffer.write(NetworkBuffer.VAR_INT, id);
        // Appelle une méthode
        buffer.write(type, packet);
        // Appelle une méthode
        final long packetSize = buffer.writeIndex() - contentStart;
        // Affecte une valeur
        final boolean compressed = packetSize >= compressionThreshold;
        // Embranchement : vérifie une condition
        if (compressed) {
            // Write the compressed content into the pooled buffer
            // and compress it into the current buffer
            // Appelle une méthode
            NetworkBuffer input = PacketVanilla.PACKET_POOL.get();
            // Gestion des exceptions
            try {
                // Embranchement : vérifie une condition
                if (input.capacity() < packetSize) input.resize(packetSize);
                // Appelle une méthode
                NetworkBuffer.copy(buffer, contentStart, input, 0, packetSize);
                // Appelle une méthode
                buffer.writeIndex(contentStart);
                // Appelle une méthode
                input.compress(0, packetSize, buffer);
            // Début d'une méthode/d'un bloc
            } catch (IOException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Début d'une méthode/d'un bloc
            } finally {
                // Appelle une méthode
                PacketVanilla.PACKET_POOL.add(input);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Packet header (Packet + Data Length)
        // Appelle une méthode
        buffer.writeAt(compressedIndex, NetworkBuffer.VAR_INT_3, (int) (buffer.writeIndex() - uncompressedIndex));
        // Appelle une méthode
        buffer.writeAt(uncompressedIndex, NetworkBuffer.VAR_INT_3, compressed ? (int) packetSize : 0);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static NetworkBuffer allocateTrimmedPacket(ConnectionState state,
                                                      // Instruction de code
                                                      ClientPacket packet,
                                                      // Début d'une méthode/d'un bloc
                                                      int compressionThreshold) {
        // Renvoie une valeur à l'appelant
        return allocateTrimmedPacket(PacketVanilla.CLIENT_PACKET_PARSER, state, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static NetworkBuffer allocateTrimmedPacket(ConnectionState state,
                                                      // Instruction de code
                                                      ServerPacket packet,
                                                      // Début d'une méthode/d'un bloc
                                                      int compressionThreshold) {
        // Renvoie une valeur à l'appelant
        return allocateTrimmedPacket(PacketVanilla.SERVER_PACKET_PARSER, state, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> NetworkBuffer allocateTrimmedPacket(
            // Instruction de code
            PacketParser<T> parser,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            T packet,
            // Début d'une méthode/d'un bloc
            int compressionThreshold) {
        // Appelle une méthode
        NetworkBuffer buffer = PacketVanilla.PACKET_POOL.get();
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return allocateTrimmedPacket(buffer, parser, state, packet, compressionThreshold);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            PacketVanilla.PACKET_POOL.add(buffer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> NetworkBuffer allocateTrimmedPacket(
            // Instruction de code
            NetworkBuffer tmpBuffer,
            // Instruction de code
            PacketParser<? super T> parser,
            // Instruction de code
            ConnectionState state,
            // Instruction de code
            T packet,
            // Début d'une méthode/d'un bloc
            int compressionThreshold) {
        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked") // We assume ConnectionState and PacketRegistry are in sync
        // Appelle une méthode
        final PacketRegistry<? super T> registry = (PacketRegistry<? super T>) parser.stateRegistry(state);
        // Renvoie une valeur à l'appelant
        return allocateTrimmedPacket(tmpBuffer, registry, packet, compressionThreshold);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> NetworkBuffer allocateTrimmedPacket(
            // Instruction de code
            NetworkBuffer tmpBuffer,
            // Instruction de code
            PacketRegistry<? super T> registry,
            // Instruction de code
            T packet,
            // Début d'une méthode/d'un bloc
            int compressionThreshold) {
        // Appelle une méthode
        final PacketRegistry.PacketInfo<? super T> packetInfo = registry.packetInfo(packet);
        // Appelle une méthode
        final int id = packetInfo.id();
        // Appelle une méthode
        final NetworkBuffer.Type<? super T> serializer = packetInfo.serializer();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            writeFramedPacket(tmpBuffer, serializer, id, packet, compressionThreshold);
            // Renvoie une valeur à l'appelant
            return tmpBuffer.copy(0, tmpBuffer.writeIndex());
        // Début d'une méthode/d'un bloc
        } catch (IndexOutOfBoundsException e) {
            // Appelle une méthode
            final long sizeOf = serializer.sizeOf(packet, tmpBuffer.registries());
            // Embranchement : vérifie une condition
            if (sizeOf > ServerFlag.MAX_PACKET_SIZE) {
                // Lève une exception
                throw new IllegalStateException("Packet too large: " + sizeOf);
            // Fin d'un bloc/d'une expression
            }
            // Add 15 bytes to account for the 3 potential varints in the packet header
            // Packet Length - Data Length - Packet ID
            // Appelle une méthode
            tmpBuffer.resize(sizeOf + 15);
            // Appelle une méthode
            tmpBuffer.writeIndex(0);
            // Appelle une méthode
            writeFramedPacket(tmpBuffer, serializer, id, packet, compressionThreshold);
            // Renvoie une valeur à l'appelant
            return tmpBuffer.copy(0, tmpBuffer.writeIndex());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <T> void writeQueue(NetworkBuffer buffer, MessagePassingQueue<T> queue, int minWrite,
                                      // Début d'une méthode/d'un bloc
                                      BiPredicate<NetworkBuffer, T> writer) {
        // The goal of this method is to write at the very least `minWrite` packets if the queue permits it.
        // The buffer is resized if it cannot hold this minimum.
        // Appelle une méthode
        final int size = queue.size();
        // Appelle une méthode
        minWrite = Math.min(minWrite, size);
        // Instruction de code
        T packet;
        // Affecte une valeur
        int written = 0;
        // Boucle : répète un bloc
        while ((packet = queue.peek()) != null) {
            // Appelle une méthode
            final long index = buffer.writeIndex();
            // Instruction de code
            boolean success;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                success = writer.test(buffer, packet);
            // Début d'une méthode/d'un bloc
            } catch (IndexOutOfBoundsException e) {
                // Affecte une valeur
                success = false;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            assert !success || buffer.writeIndex() > 0;
            // Poll the packet only if fully written
            // Embranchement : vérifie une condition
            if (success) {
                // Packet fully written
                // Appelle une méthode
                queue.poll();
                // Instruction de code
                written++;
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                buffer.writeIndex(index);
                // Embranchement : vérifie une condition
                if (written < minWrite) {
                    // Try again with a bigger buffer
                    // Appelle une méthode
                    final long newSize = Math.min(buffer.capacity() * 2, ServerFlag.MAX_PACKET_SIZE);
                    // Embranchement : vérifie une condition
                    if (newSize == buffer.capacity()) break; // We reached the maximum size
                    // Appelle une méthode
                    buffer.resize(newSize);
                // Branche alternative de la condition
                } else {
                    // At least one packet has been written
                    // Not worth resizing to fit more, we'll try again next flush
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
