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
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.zip.DataFormatException;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket> success)) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = success.packets().stream().map(PacketReading.ParsedPacket::packet).toList();
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
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket> success)) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = success.packets().stream().map(PacketReading.ParsedPacket::packet).toList();
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
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket> success)) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = success.packets().stream().map(PacketReading.ParsedPacket::packet).toList();
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
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket> success)) {
            // Lève une exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<ClientPacket> packets = success.packets().stream().map(PacketReading.ParsedPacket::packet).toList();
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
        if (!(readResult instanceof PacketReading.Result.Failure<ClientPacket> failure)) {
            // Lève une exception
            throw new AssertionError("Expected a failure result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(packetLength, failure.requiredCapacity());
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
        if (!(readResult instanceof PacketReading.Result.Failure<ClientPacket> failure)) {
            // Lève une exception
            throw new AssertionError("Expected a failure result, got " + readResult);
        // Fin d'un bloc/d'une expression
        }
        // 5 = max var-int size
        // Appelle une méthode
        assertEquals(5, failure.requiredCapacity());
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
