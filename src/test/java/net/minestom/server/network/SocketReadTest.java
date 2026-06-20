// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.minestom.server.network.packet.PacketReading;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.PacketWriting;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.ValueSource;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.zip.DataFormatException;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertSame;

// Type declaration (class/interface/enum/record)
public class SocketReadTest {

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void complete(boolean compressed) throws DataFormatException {
        // Calls a method
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);

        // Calls a method
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Code statement
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Start of a method/block
        ))) {
            // Throws an exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // End of a block/expression
        }
        // Calls a method
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Calls a method
        assertEquals(List.of(packet), packets);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void completeTwo(boolean compressed) throws DataFormatException {
        // Calls a method
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);

        // Calls a method
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Code statement
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Start of a method/block
        ))) {
            // Throws an exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // End of a block/expression
        }
        // Calls a method
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Calls a method
        assertEquals(List.of(packet, packet), packets);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void insufficientLength(boolean compressed) throws DataFormatException {
        // Write a complete packet then the next packet length without any payload

        // Calls a method
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Code statement
        buffer.write(NetworkBuffer.VAR_INT, 200); // incomplete 200 bytes packet

        // Calls a method
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Code statement
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Start of a method/block
        ))) {
            // Throws an exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // End of a block/expression
        }
        // Calls a method
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Calls a method
        assertEquals(List.of(packet), packets);

        // Calls a method
        readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Empty<ClientPacket>)) {
            // Throws an exception
            throw new AssertionError("Expected an empty result, got " + readResult);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void incomplete(boolean compressed) throws DataFormatException {
        // Write a complete packet and incomplete var-int length for the next packet

        // Calls a method
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Code statement
        buffer.write(NetworkBuffer.BYTE, (byte) -85); // incomplete var-int length

        // Calls a method
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Success<ClientPacket>(
                // Code statement
                List<PacketReading.ParsedPacket<ClientPacket>> packets1
        // Start of a method/block
        ))) {
            // Throws an exception
            throw new AssertionError("Expected a success result, got " + readResult);
        // End of a block/expression
        }
        // Calls a method
        List<ClientPacket> packets = packets1.stream().map(PacketReading.ParsedPacket::packet).toList();
        // Calls a method
        assertEquals(1, buffer.readableBytes());

        // Calls a method
        assertEquals(List.of(packet), packets);

        // Try to read the next packet
        // Calls a method
        readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Empty<ClientPacket>)) {
            // Throws an exception
            throw new AssertionError("Expected an empty result, got " + readResult);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void resize(boolean compressed) throws DataFormatException {
        // Write a complete packet that is larger than the buffer capacity

        // Calls a method
        var packet = new ClientPluginMessagePacket("channel", new byte[2000]);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, packet, compressed ? 256 : 0);
        // Calls a method
        final long packetLength = buffer.writeIndex();
        // Calls a method
        buffer = buffer.copy(0, packetLength / 2).index(0, packetLength / 2);

        // Calls a method
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Failure<ClientPacket>(long requiredCapacity))) {
            // Throws an exception
            throw new AssertionError("Expected a failure result, got " + readResult);
        // End of a block/expression
        }
        // Calls a method
        assertEquals(packetLength, requiredCapacity);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void resizeHeader(boolean compressed) throws DataFormatException {
        // Write a buffer where you cannot read the packet length

        // Calls a method
        var buffer = NetworkBuffer.staticBuffer(1);
        // Code statement
        buffer.write(NetworkBuffer.BYTE, (byte) -85); // incomplete var-int length

        // Calls a method
        var readResult = PacketReading.readClients(buffer, ConnectionState.PLAY, compressed);
        // Branch: checks a condition
        if (!(readResult instanceof PacketReading.Result.Failure<ClientPacket>(long requiredCapacity))) {
            // Throws an exception
            throw new AssertionError("Expected a failure result, got " + readResult);
        // End of a block/expression
        }
        // 5 = max var-int size
        // Calls a method
        assertEquals(5, requiredCapacity);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void compressedReadInheritsSourceBufferRegistries() throws DataFormatException {
        // Encode a framed packet large enough to actually compress (threshold = 256), keeping
        // the pool untouched by the test setup so the read is the only relevant consumer.
        // Calls a method
        final var packet = new ClientPluginMessagePacket("ch", new byte[2000]);
        // Calls a method
        final var encoded = NetworkBuffer.resizableBuffer();
        // Calls a method
        PacketWriting.writeFramedPacket(encoded, ConnectionState.PLAY, packet, 256);
        // Calls a method
        final int length = (int) encoded.writeIndex();
        // Assigns a value
        final byte[] framed = new byte[length];
        // Calls a method
        encoded.copyTo(0, framed, 0, length);

        // Drain the pool so any buffer we inspect afterwards must have been used by the read.
        // Loop: repeats a block
        while (PacketVanilla.PACKET_POOL.count() > 0) PacketVanilla.PACKET_POOL.get();

        // Calls a method
        final Registries sourceRegistries = Registries.vanilla();
        // Calls a method
        final var source = NetworkBuffer.wrap(framed, 0, framed.length, sourceRegistries);
        // Calls a method
        PacketReading.readClients(source, ConnectionState.PLAY, true);

        // Calls a method
        final NetworkBuffer pooled = PacketVanilla.PACKET_POOL.get();
        // Exception handling
        try {
            // Code statement
            assertSame(sourceRegistries, pooled.registries(),
                    // Code statement
                    "Decompressed pool buffer must inherit the source buffer's registries");
        // Start of a method/block
        } finally {
            // Calls a method
            PacketVanilla.PACKET_POOL.add(pooled);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static int getVarIntSize(int input) {
        // Returns a value to the caller
        return (input & 0xFFFFFF80) == 0
                // Code statement
                ? 1 : (input & 0xFFFFC000) == 0
                // Code statement
                ? 2 : (input & 0xFFE00000) == 0
                // Code statement
                ? 3 : (input & 0xF0000000) == 0
                // Code statement
                ? 4 : 5;
    // End of a block/expression
    }
// End of a block/expression
}
