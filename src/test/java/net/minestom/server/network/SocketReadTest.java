// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketReading;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketWriting;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.zip.DataFormatException;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertSame;

// Déclaration de type (classe/interface/enum/record)
public class SocketReadTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void complete(boolean compressed) throws DataFormatException {
        // Appelle une méthode
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);

        // Appelle une méthode
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Instruction de code
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Début d'une méthode/d'un bloc
        ))) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Appelle une méthode
        assertEquals(List.of(packet), packets);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void completeTwo(boolean compressed) throws DataFormatException {
        // Appelle une méthode
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);

        // Appelle une méthode
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Instruction de code
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Début d'une méthode/d'un bloc
        ))) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Appelle une méthode
        assertEquals(List.of(packet, packet), packets);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void insufficientLength(boolean compressed) throws DataFormatException {
        // Write a complete packet then the next packet length without any payload

        // Appelle une méthode
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Instruction de code
        buffer.write(NetworkBuffer.VAR_INT, 200); // incomplete 200 bytes packet

        // Appelle une méthode
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Instruction de code
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Début d'une méthode/d'un bloc
        ))) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Appelle une méthode
        assertEquals(List.of(packet), packets);

        // Appelle une méthode
        readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Empty<ClientPacket>)) {
            // Lève une exception
            throw new AssertionError("Expected an empty result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void incomplete(boolean compressed) throws DataFormatException {
        // Write a complete packet and incomplete var-int length for the next packet

        // Appelle une méthode
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Instruction de code
        buffer.write(NetworkBuffer.BYTE, (byte) -85); // incomplete var-int length

        // Appelle une méthode
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Instruction de code
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Début d'une méthode/d'un bloc
        ))) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Appelle une méthode
        assertEquals(1, buffer.readableBytes());

        // Appelle une méthode
        assertEquals(List.of(packet), packets);

        // Try to read the next packet
        // Appelle une méthode
        readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Empty<ClientPacket>)) {
            // Lève une exception
            throw new AssertionError("Expected an empty result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void resize(boolean compressed) throws DataFormatException {
        // Write a complete packet that is larger than the buffer capacity

        // Appelle une méthode
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Appelle une méthode
        final long packetLength = buffer.writeIndex();
        // Appelle une méthode
        buffer = buffer.copy(0, packetLength / 2).index(0, packetLength / 2);

        // Appelle une méthode
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Failure<ClientPacket>(long requiredCapacity))) {
            // Lève une exception
            throw new AssertionError("Expected a failure result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(packetLength, requiredCapacity);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void resizeHeader(boolean compressed) throws DataFormatException {
        // Write a buffer where you cannot read the packet length

        // Appelle une méthode
        var buffer = NetworkBuffer.staticBuffer(1);
        // Instruction de code
        buffer.write(NetworkBuffer.BYTE, (byte) -85); // incomplete var-int length

        // Appelle une méthode
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Embranchement : vérifie une condition
        if (!(readResult instanceof PacketReading.Result.Failure<ClientPacket>(long requiredCapacity))) {
            // Lève une exception
            throw new AssertionError("Expected a failure result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // 5 = max var-int size
        // Appelle une méthode
        assertEquals(5, requiredCapacity);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void compressedReadInheritsSourceBufferRegistries() throws DataFormatException {
        // Encode a framed packet large enough to actually compress (threshold = 256), keeping
        // the pool untouched by the test setup so the read is the only relevant consumer.
        // Appelle une méthode
        final var packet = new ClientPluginMessagePacket("ch", new byte[2000]);
        // Appelle une méthode
        final var encoded = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(encoded, ConnectionState.PLAY, packet, 256);
        // Appelle une méthode
        final int length = (int) encoded.writeIndex();
        // Affecte une valeur
        final byte[] framed = new byte[length];
        // Appelle une méthode
        encoded.copyTo(0, framed, 0, length);

        // Drain the pool so any buffer we inspect afterwards must have been used by the read.
        // Boucle : répète un bloc
        while (PacketVanilla.PACKET_POOL.count() > 0) PacketVanilla.PACKET_POOL.get();

        // Appelle une méthode
        final Registries sourceRegistries = Registries.vanilla();
        // Appelle une méthode
        final var source = NetworkBuffer.wrap(framed, 0, framed.length, sourceRegistries);
        // Appelle une méthode
        PacketReading.readClients(source, ConnectionState.PLAY, true);

        // Appelle une méthode
        final NetworkBuffer pooled = PacketVanilla.PACKET_POOL.get();
        // Gestion des exceptions
        try {
            // Instruction de code
            assertSame(sourceRegistries, pooled.registries(),
                    // Instruction de code
                    "Decompressed pool buffer must inherit the source buffer's registries");
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            PacketVanilla.PACKET_POOL.add(pooled);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int getVarIntSize(int input) {
        // Renvoie une valeur à l'appelant
        return (input & 0xFFFFFF80) == 0
                // Instruction de code
                ? 1 : (input & 0xFFFFC000) == 0
                // Instruction de code
                ? 2 : (input & 0xFFE00000) == 0
                // Instruction de code
                ? 3 : (input & 0xF0000000) == 0
                // Instruction de code
                ? 4 : 5;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
